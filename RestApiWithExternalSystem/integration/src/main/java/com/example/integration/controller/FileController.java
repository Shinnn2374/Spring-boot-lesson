package com.example.integration.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/file")
@Slf4j
public class FileController
{
    @SneakyThrows
    @PostMapping
    public ResponseEntity<String> upLoadFile(@RequestPart MultipartFile file)
    {
        if (file.isEmpty())
        {
            return ResponseEntity.badRequest().body("file is empty");
        }
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        log.info("File content {}",content);
        return ResponseEntity.ok("File upload is successful");
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downLoadFile(@PathVariable String fileName)
    {
        String filePath = "files/" + fileName;
        Resource resource = new ClassPathResource(filePath);
        if (!resource.exists())
        {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName);
        headers.setContentType(MediaType.TEXT_PLAIN);
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
