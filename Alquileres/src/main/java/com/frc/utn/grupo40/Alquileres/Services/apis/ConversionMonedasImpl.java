package com.frc.utn.grupo40.Alquileres.Services.apis;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class ConversionMonedasImpl implements IconversionMonedas {
    @Override
    public String[] conversion(String aMoneda, double cantidad) {
        String[] retornar = new String[2];

        String apiUrl = "http://34.82.105.125:8080/convertir";
        String requestData = String.format("{\"moneda_destino\":\"%s\",\"importe\":%f}", aMoneda, cantidad);


        try {
            // Crear una URL
            URL url = new URL(apiUrl);

            // Abrir una conexión HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Enviar los datos de la solicitud
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(requestData);
                wr.flush();
            }

            // Obtener la respuesta
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Almacenar la respuesta en una variable
                String respuestaApi = response.toString();

                JSONObject jsonResponse = new JSONObject(respuestaApi);

                // Obtener los valores deseados
                String moneda = jsonResponse.getString("moneda");
                double importeConvertido = jsonResponse.getDouble("importe");

                retornar[0] = moneda;
                retornar[1] = Double.toString(importeConvertido);


            } else {
                System.out.println("La solicitud no fue exitosa. Código de respuesta: " + responseCode);

                retornar[0] = "error al realizar la conversión";
                retornar[1] = Double.toString(0.0);
            }

            // Cerrar la conexión
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            retornar[0] = "error al realizar la conexión con base de datos";
            retornar[1] = Double.toString(0.0);
        }





        return retornar;
    }
}


