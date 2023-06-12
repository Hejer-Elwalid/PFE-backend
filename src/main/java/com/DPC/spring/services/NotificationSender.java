package com.DPC.spring.services;
import com.DPC.spring.entities.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class NotificationSender {
    @Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;

    public static void sendNotificationToProducer(Notification notification) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://localhost:8082/notification/send");

        // Convert the Notification object to JSON
        String jsonNotification = convertToJson(notification);

        // Set the JSON content as the request body
        StringEntity entity = new StringEntity(jsonNotification, "UTF-8");
        entity.setContentType("application/json");
        request.setEntity(entity);

        try {
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            if (statusCode == 200) {
                // Notification sent successfully
                System.out.println("Notification sent successfully");
            } else {
                // Handle error response
                System.out.println("Error sending notification: " + responseBody);
            }
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    private static String convertToJson(Notification notification) {
        // Convert the Notification object to JSON using a JSON library of your choice
        // For example, you can use the Jackson library:
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(notification);
        } catch (JsonProcessingException e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }
    //une méthode pour envoyer une notification à Kafka

    public void sendNotification(Notification notification) {
        kafkaTemplate.send("congee", notification);
    }
}


