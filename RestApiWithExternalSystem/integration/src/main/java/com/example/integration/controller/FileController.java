package com.example.integration.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
    @PostMapping("/upload")
    @SneakyThrows
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file)
    {
        if (file.isEmpty())
        {
            return ResponseEntity.badRequest().body("File is empty");
        }
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        log.info(content);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filename)
    {
        String filePath = "files/" + filename;
        Resource fileResourse = new ClassPathResource(filename);
        if (!fileResourse.exists())
        {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filePath);
        headers.setContentType(MediaType.TEXT_PLAIN);
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResourse);
    }
}
