package com.adstart.domain_monitor.rest.webhooks.services;

import com.adstart.domain_monitor.domain.contracts.notification.INotificationService;
import com.adstart.domain_monitor.domain.contracts.persistence.INotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class NotificationService implements INotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private final ObjectMapper              objectMapper;
    private final INotificationRepository   notificationRepository;

    @Override
    public void notifyAllRegisters(final String domain,
                                   final LocalDateTime expirationDate,
                                   final int threshold) {
        final List<String> registeredUrls = notificationRepository.getAllUrls();

        final Notification notification = new Notification(domain, expirationDate, threshold);
        for(String registeredUrl : registeredUrls) {
            sendNotification(registeredUrl, notification);
        }
    }

    @Override
    public void save(String callbackUrl) {
        notificationRepository.save(callbackUrl);
    }

    private void sendNotification(final String url, Notification notification) {
      try(HttpClient client = HttpClient.newHttpClient()){
          String requestBody = objectMapper.writeValueAsString(notification);

          HttpRequest request = HttpRequest.newBuilder()
                  .version(HttpClient.Version.HTTP_1_1)
                  .uri(URI.create("http://" + url))
                  .header("Content-Type", "application/json")
                  .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                  .build();

          HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
          if(response.statusCode() > 200) {
              LOGGER.error("Notification request failed for URL {} Status Code: {}", url, response.statusCode());
          }

      } catch (IOException | InterruptedException e) {
            LOGGER.error("Exception occurred while sending notification to {}", url, e);
      }
    }

    private record Notification(String domain, LocalDateTime expirationDate, int threshold) {}
}
