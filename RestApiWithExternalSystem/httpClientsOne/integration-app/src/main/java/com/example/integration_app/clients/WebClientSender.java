package com.example.integration_app.clients;

import com.example.integration_app.model.EntityModel;
import com.example.integration_app.model.UpsertEntityRequest;
import lombok.RequiredArgsConstructor;
import okhttp3.MultipartBody;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebClientSender
{
    public final WebClient webClient;

    public void uploadFile(MultipartFile file)
    {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file",file.getResource())
                .filename(file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM);
        webClient.post()
                .uri("/api/v1/file/upload")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public Resource downloadFile(String fileName)
    {
        return webClient.get()
                .uri("/api/v1/file/download/{filename}",fileName)
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .bodyToMono(Resource.class)
                .block();
    }

    public List<EntityModel> getEntityList()
    {
        return webClient.get()
                .uri("/api/v1/client/entity")
                .retrieve()
                .bodyToFlux(EntityModel.class)
                .collectList()
                .block();
    }

    public EntityModel getEntityByName(String entityName){
        return webClient.get()
                .uri("/api/v1/entity/{entityName}",entityName)
                .retrieve()
                .bodyToMono(EntityModel.class)
                .block();
    }

    public EntityModel createEntity(UpsertEntityRequest request)
    {
        return webClient.post()
                .uri("/api/v1/entity")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(EntityModel.class)
                .block();
    }

    public EntityModel updateEntity(UUID id, UpsertEntityRequest request)
    {
        return webClient.put()
                .uri("/api/v1/entity/{id}",id)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(EntityModel.class)
                .block();
    }

    public void deleteEntityById(UUID id)
    {
        webClient.delete()
                .uri("/api/v1/entity/{id}",id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
