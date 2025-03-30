package com.technical.test.meli.challenge.Infrastructure.Adapter.GetItems;

import com.technical.test.meli.challenge.Application.Dto.ItemsResponse.ItemsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalGetItemsPort implements IExternalGetItems {

    private static final Logger logger = LoggerFactory.getLogger(ExternalGetItemsPort.class);
    private final RestTemplate restTemplate;
    private final String authToken;

    @Value("${external.api.url}")
    private String hostMercadoLibre;

    @Value("${external.api.path.items}")
    private String pathItems;

    public ExternalGetItemsPort(RestTemplate restTemplate, @Value("${external.api.auth.token}") String authToken) {
        this.restTemplate = restTemplate;
        this.authToken = authToken;
    }

    @Override
    public ItemsResponse[] getItems(String ids) {
        try {
            String url = hostMercadoLibre.concat(pathItems).concat(ids);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + authToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ItemsResponse[]> result = restTemplate.exchange(url, HttpMethod.GET, entity, ItemsResponse[].class);

            if (result.getStatusCode() == HttpStatus.OK) {
                return result.getBody();
            } else {
                logger.error("Error fetching items: " + result.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error in ExternalGetItemsPort getItems", e);
            return null;
        }
    }
}