package com.technical.test.meli.challenge.Infrastructure.Adapter.GetCategories;

import com.technical.test.meli.challenge.Application.Dto.categories.CategoriesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class ExternalGetCategoriesPort implements IExternalGetCategories {

    private static final Logger logger = LoggerFactory.getLogger(ExternalGetCategoriesPort.class);

    private final RestTemplate restTemplate;

    private final String authToken;

    @Value("${external.api.url}")
    private String hostMercadoLibre;

    @Value("${external.api.path.categories}")
    private String pathCategories;

    public ExternalGetCategoriesPort(RestTemplate restTemplate, @Value("${external.api.auth.token}") String authToken) {
        this.restTemplate = restTemplate;
        this.authToken = authToken;
    }

    @Override
    public CategoriesResponse getCategories(String id) {
        try {
            String path = pathCategories.replace("{categoryId}", id);
            String url = hostMercadoLibre.concat(path);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + authToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<CategoriesResponse> result = restTemplate.exchange(url, HttpMethod.GET, entity, CategoriesResponse.class);

            if (result.getStatusCode() == HttpStatus.OK) {
                return result.getBody();
            } else {
                logger.error("Error fetching categories: " + result.getStatusCode());
                return null;
            }


        } catch (Exception e) {
            logger.error("Error in ExternalGetCategoriesPort getCategories", e);
            return null;
        }

    }

}
