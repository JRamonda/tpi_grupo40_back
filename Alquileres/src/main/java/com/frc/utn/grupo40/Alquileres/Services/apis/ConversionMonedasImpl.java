package com.frc.utn.grupo40.Alquileres.Services.apis;


import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ConversionMonedasImpl implements IconversionMonedas {
    public void deStringAArray(String[] conversion, String respuesta) {
        String[] parts = respuesta.split(",");
        conversion[0] = parts[0].split(":")[1].replace("\"", "");
        conversion[1] = parts[1].split(":")[1].replace("\"", "").replace("}", "");
    }
    @Override
    public String[] conversion(String moneda_destino, double importe) {
        String[] conversion = new String[2];
        String jsonResponse = "";
        try {
            // URL de la API que deseas consultar
            String apiUrl = "http://34.82.105.125:8080/convertir";

            // Datos que quieres enviar en el cuerpo de la solicitud
            String requestBody = "{\"moneda_destino\":\""+ moneda_destino +"\",\"importe\":"+ Double.toString( importe) +"}";

            // Realizar la solicitud POST con un cuerpo
            jsonResponse = sendPostRequest(apiUrl, requestBody);
        } catch (IOException e) {
            String[] error = new String[2];
            error[0] = "error";
            error[1] = "0";
            return error;
        }
        deStringAArray(conversion, jsonResponse);
        return conversion;
    }

    private static String sendPostRequest(String apiUrl, String requestBody) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Configurar la solicitud
        connection.setRequestMethod("POST");
        connection.setDoOutput(true); // Permitir la escritura en el cuerpo de la solicitud

        // Especificar el tipo de contenido del cuerpo (en este caso, JSON)
        connection.setRequestProperty("Content-Type", "application/json");

        // Escribir los datos en el cuerpo de la solicitud
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(requestBody);
            wr.flush();
        }

        // Obtener la respuesta
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Leer la respuesta
            connection.getResponseMessage();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            return response.toString();
        } else {
            // Manejar el error, si es necesario
            throw new IOException("Error en la solicitud. Código de respuesta: " + responseCode);
        }
    }
}


