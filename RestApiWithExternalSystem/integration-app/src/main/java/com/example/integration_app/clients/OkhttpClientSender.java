package com.example.integration_app.clients;

import com.example.integration_app.model.EntityModel;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class OkhttpClientSender
{
    private OkHttpClient client;
    private ObjectMapper objectMapper;
    @Value("${app.integration.base-url}")
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
                .header("Content-Type", "multipart/from-data")
                .post(builder.build())
                .build();
        try(Response response = client.newCall(request).execute())
        {
            if (!response.isSuccessful())
            {
                log.error("Error trying to request");
                return "Error!";
            }
            return new String(response.body().bytes());
        }
    }

    public Resource downLoadFile(String fileName)
    {
        Request request = new Request.Builder()
                .url(baseUrl + "api/v1/file/download/" + fileName)
                .header("Accept", "application/octet-stream")
                .get()
                .build();

        try(Response response = client.newCall(request).execute())
        {
            if (!response.isSuccessful())
            {
                log.error("Error trying to download file");
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

    @SneakyThrows
    public
}
