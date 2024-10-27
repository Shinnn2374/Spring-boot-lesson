package com.example.integration_app.controller;

import com.example.integration_app.clients.OkhttpClientSender;
import com.example.integration_app.clients.RestTemplateClient;
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
    private final RestTemplateClient client;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file)
    {
        client.uploadFile(file);
        return ResponseEntity.ok("File was upload");
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename)
    {
        Resource resource = client.downloadFile(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        headers.setContentType(MediaType.TEXT_PLAIN);
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
