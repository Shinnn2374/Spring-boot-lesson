package com.example.integration_app.clients;

import com.example.integration_app.model.EntityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RestTemplateClient
{
    private final RestTemplate restTemplate;
    @Value("${app.integration.base-url}")
    private String baseUrl;

    public void uploadFile(MultipartFile file)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String,Object>> requestEntity = new HttpEntity<>(body, headers);

        restTemplate.postForObject(baseUrl + "/api/v1/file/upload", requestEntity, String.class);
    }

    public Resource downloadFile(String filename)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        ResponseEntity<Resource> response = restTemplate.exchange(
                baseUrl+"/api/v1/file/download/{filename}",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Resource.class,
                filename
        );
        return response.getBody();
    }

    public List<EntityModel> getEntityList()
    {
        ResponseEntity<List<EntityModel>> response = restTemplate.exchange(
                baseUrl+"/api/v1/entity",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<EntityModel>>() {
                    
                }
        )
    }
}
