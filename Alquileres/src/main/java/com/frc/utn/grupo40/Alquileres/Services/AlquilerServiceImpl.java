package com.frc.utn.grupo40.Alquileres.Services;

import com.frc.utn.grupo40.Alquileres.Entities.Alquiler;
import com.frc.utn.grupo40.Alquileres.Entities.DTOS.AlquilerDTO;
import com.frc.utn.grupo40.Alquileres.Entities.DTOS.CrearAlquilerDTO;
import com.frc.utn.grupo40.Alquileres.Entities.Estacion;
import com.frc.utn.grupo40.Alquileres.Entities.Tarifa;
import com.frc.utn.grupo40.Alquileres.Repositories.IAlquilerRepository;
import com.frc.utn.grupo40.Alquileres.Services.apis.IconversionMonedas;
import com.frc.utn.grupo40.Alquileres.Utilidades.Distancia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class AlquilerServiceImpl implements IAlquilerService {

    @Autowired
    private IAlquilerRepository repository;
    @Autowired
    private IconversionMonedas conversor;

    @Autowired
    private IEstacionService servicioEstaciones;

    @Autowired
    private ITarifasService servicioTarifas ;

    private Distancia calculadorDistancia;

    public List<Alquiler> FindAll() {
        return repository.findAll();
    }

    public List<Alquiler> FindAllById(List<Integer> ids) {
        return repository.findAllById(ids);
    }

    public Alquiler FindById(int id)
    {
        return repository.findById(id) ;
    };


    @Override
    public Alquiler terminarAlquiler(AlquilerDTO alquiler) {

        // validar que exista un alquiler a terminar

        Alquiler error = new Alquiler();
        Distancia calculadorDistan= new Distancia();

        String[] monedas = {"EUR", "CLP", "BRL", "COP", "PEN", "GBP", "USD"};
        String[] conversion = new String[2];

        Alquiler terminar = repository.findById(alquiler.getId());

        if (terminar == null || !terminar.getIdCliente().equals(alquiler.getIdCliente()) )
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proporcione Id valido para alquiler");
        }

        if(terminar.getEstado() == 2)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El alquiler ya está terminado ");
        }

        terminar.setFechaHoraDevolucion(LocalDateTime.now());
        Estacion estacion = servicioEstaciones.findById(alquiler.getIdPuntoEntrega());

        if( estacion == null ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe dicha estacion");

        }
        terminar.setEstacionDevolucion(estacion);

        Tarifa tarifa = elegirTarifa(terminar);


        Double monto = calculateMonto(terminar,tarifa ,calculadorDistan).doubleValue();

// si la moneda no es pesos
        if (alquiler.getMoneda() != null) {
            // si la moneda se puede convertir dentro de las permitidas

            if (Arrays.stream(monedas).anyMatch(moneda -> alquiler.getMoneda().equals( moneda))) {
                conversion = conversor.conversion(alquiler.getMoneda(), monto);
            }

            // si la moneda no está dentro de las permitidas
            else
            {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "la moneda ingresada no corresponde con las permitidas en el sistema ");
            }

            // si el conversor pudo convertir bien a la moneda
            if (Double.parseDouble(conversion[1]) != 0) {

                terminar(terminar, tarifa, Double.parseDouble(conversion[1]) );

            }
            // si el conversor tuvo un problema
            else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se encuentra disponile la API conversora");
            }
        }
        /// si la moneda es en pesos
        else {
            terminar(terminar, tarifa, monto) ;
        }

        return terminar;
    }

    private void terminar(Alquiler finalizar, Tarifa tarifa, Double monto ) {
        finalizar.setEstado(2);
        finalizar.setIdTarifa(tarifa);
        finalizar.setMonto(monto);
        repository.save(finalizar);

    }

    //// primero calcular el monto
//       El precio del alquiler se calcula en el momento de la devolución de la bicicleta y este
//        precio se calcula bajo las siguientes reglas:

//○ Hay un costo fijo por realizar el alquiler y un costo por hora completa (Cuenta como hora completa a partir del minuto 31, antes de eso se tarifa fraccionado
//        por minuto). Existe una tabla en la base de datos que se provee que indica cuáles son estos costos por cada día de la semana.

    private BigDecimal calculateMonto(Alquiler alquiler, Tarifa tarifa, Distancia calculadorDistancia ) {


        BigDecimal monto = BigDecimal.valueOf((0));
        monto = monto.add(BigDecimal.valueOf(tarifa.getMontoFijoAlquiler()) );
        Duration duration = Duration.between(alquiler.getFechaHoraRetiro(), alquiler.getFechaHoraDevolucion());
        long horas;
        long minutes;
        try {
            horas = duration.toHours();
            minutes = duration.toMinutes() - horas*60;
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("FechaHoraDevolucion es muy lejana de FechaHoraRetiro");
        }

        if(minutes <= 31) {
            monto = monto.add(BigDecimal.valueOf(tarifa.getMontoMinutoFraccion()).multiply(BigDecimal.valueOf((minutes))));
        }
        else {
            horas++;
        }
        monto = monto.add(BigDecimal.valueOf(tarifa.getMontoHora()).multiply(BigDecimal.valueOf(horas)));

        //○ Se cobra un monto adicional por cada KM que separe la estación de retiro de
        // la estación de devolución. La base de datos provista contiene el precio adicional por KM.
        // El cálculo de esta distancia se explica en las aclaraciones finales

        double distanciaKm = calculadorDistancia.calcularDistancia(alquiler.getEstacionDevolucion().getLatitud(),
                alquiler.getEstacionDevolucion().getLongitud(), alquiler.getEstacionRetiro().getLatitud(),
                alquiler.getEstacionRetiro().getLongitud()) / 1000;

        monto = monto.add(BigDecimal.valueOf(tarifa.getMontoKm()).multiply(BigDecimal.valueOf((distanciaKm))));

        return monto;
    }


//○ Para los días promocionales configurados en el sistema, se aplica un
//        porcentaje de descuento sobre el monto total del alquiler. Para que este
//        descuento se aplique, se considera que el retiro de la bicicleta se hizo en el
//        día promocional. La base de datos provista contiene los días que se
//        consideran de descuento y el descuento aplicable.

    private Tarifa elegirTarifa(Alquiler alquiler) {


        int diaMes = alquiler.getFechaHoraRetiro().getDayOfMonth();
        int mes = alquiler.getFechaHoraRetiro().getMonthValue();
        int anio = alquiler.getFechaHoraRetiro().getYear();

        Tarifa tarifa = servicioTarifas.BuscarTarifaFecha('C', diaMes, mes, anio);

        if(tarifa == null ) {

            int diaSemana = alquiler.getFechaHoraDevolucion().getDayOfWeek().getValue();

            tarifa = servicioTarifas.BuscarTarifaDiaSemana(diaSemana);
        }

        if(tarifa == null ) { throw new IllegalArgumentException("Tarifa no encontrada"); }

        return tarifa;
    }

    @Override
    public Alquiler crear(CrearAlquilerDTO crear) {
        Alquiler nuevo = new Alquiler();

        if(crear.getIdCliente() == null || crear.getIdCliente().length() > 250 ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proporcione Id para cliente ");
        }

        if(crear.getIdEstacionRetiro() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proporcione una Estacion");
        }

        Estacion estacion = servicioEstaciones.findById(crear.getIdEstacionRetiro());

        if( estacion == null ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe dicha estacion");

        }

        nuevo.setIdCliente(crear.getIdCliente());
        nuevo.setEstado(1);


        nuevo.setEstacionRetiro(estacion);

        nuevo.setFechaHoraRetiro(LocalDateTime.now());


        Alquiler creado = repository.save(nuevo);

        return creado;
    }

    @Override
    public int maxId() {
        int id = repository.getMaxId();
        return id;
    }


}
