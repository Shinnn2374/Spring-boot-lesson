package com.example.integration_app.client;

import com.example.integration_app.model.EntityModel;
import com.example.integration_app.model.UpsertEntityRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OkHttpClientSender
{
    private final OkHttpClient client;

    private final ObjectMapper objectMapper;

    @Value("${app.integration}")
    private String baseUrl;

    @SneakyThrows
    public String uploadFile(MultipartFile file)
    {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), file.getBytes()));
        Request request = new Request.Builder()
                .url(baseUrl + "/api/v1/file/upload")
                .header("Content-Type", "multipart/form-data")
                .post(builder.build())
                .build();
        try(Response response = client.newCall(request).execute())
        {
            if (!response.isSuccessful())
            {
                log.error("Error trying to request to upload file");
                return "Error!";
            }
            return new String(response.body().bytes());
        }
    }

    public Resource downLoadFile(String fileName)
    {
        Request request = new Request.Builder()
                .url(baseUrl + "/api/v1/file/download/" + fileName)
                .header("Accept","application/octet-stream")
                .get().build();
        try(Response response = client.newCall(request).execute())
        {
            if (!response.isSuccessful())
            {
                log.error("Error trying to downloadfile");
                return null;
            }
            return new ByteArrayResource(response.body().bytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<EntityModel> getEntityList()
    {
        Request request = new Request.Builder()
                .url(baseUrl + "/api/v1/entity")
                .build();

        return processResponse(request, new TypeReference<>(){});
    }
    public EntityModel getEntityByName(String name)
    {
        Request request = new Request.Builder()
                .url(baseUrl + "/api/v1/entityName")
                .build();
        return processResponse(request, new TypeReference<>(){});
    }

    @SneakyThrows
    public EntityModel createEntity(UpsertEntityRequest upsertEntityRequest)
    {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String requestBoyde = objectMapper.writeValueAsString(upsertEntityRequest);
        RequestBody requestBody = RequestBody.create(JSON, requestBoyde);

        Request request = new Request.Builder()
                .url(baseUrl + "/api/v1/entity")
                .post(requestBody)
                .build();
        return processResponse(request, new TypeReference<>(){});
    }

    @SneakyThrows
    public EntityModel updateEntity(UUID id, UpsertEntityRequest upsertEntityRequest)
    {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String requestBoyde = objectMapper.writeValueAsString(upsertEntityRequest);
        RequestBody requestBody = RequestBody.create(JSON, requestBoyde);

        Request request = new Request.Builder()
                .url(baseUrl + "/api/v1/entity/" + id)
                .put(requestBody)
                .build();
        return processResponse(request, new TypeReference<>(){});
    }

    @SneakyThrows
    public void deleteEntityById(UUID id)
    {
        Request request = new Request.Builder()
                .url(baseUrl + "api/v1/entity/" + id)
                .delete()
                .build();
        try(Response response = client.newCall(request).execute())
        {
            if (!response.isSuccessful())
            {
                throw new RuntimeException("Unexpected response code:  " + response);
            }
        }
    }

    @SneakyThrows
    public <T> T processResponse(Request request, TypeReference<T> typeReference)
    {
        try(Response response = client.newCall(request).execute())
        {
            if (!response.isSuccessful())
            {
                throw new RuntimeException("Unexpected code " + response);
            }
            ResponseBody responseBody = response.body();
            if (response != null)
            {
                String stringBody = responseBody.string();
                return objectMapper.readValue(stringBody, typeReference);
            }
            else
            {
                throw new RuntimeException("response body is empty");
            }
        }
    }
}
