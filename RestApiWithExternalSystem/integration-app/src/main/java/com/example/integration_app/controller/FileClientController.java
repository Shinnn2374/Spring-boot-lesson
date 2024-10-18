package com.example.integration_app.controller;

import com.example.integration_app.clients.OkHttpClientSender;
import com.example.integration_app.clients.RestTemplateClient;
import com.example.integration_app.clients.WebClientSender;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/client/file")
@RequiredArgsConstructor
public class FileClientController
{
    private final WebClientSender client;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestPart MultipartFile file)
    {
        client.uploadFile(file);
        return ResponseEntity.ok("File was upload");
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName)
    {
        Resource resource = client.downloadFile(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        headers.setContentType(MediaType.TEXT_PLAIN);
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
