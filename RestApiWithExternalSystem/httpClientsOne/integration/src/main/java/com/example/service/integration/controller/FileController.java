package com.example.service.integration.controller;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
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
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file)
    {
        if(!file.isEmpty())
        {
            return ResponseEntity.badRequest().body("File is empty");
        }
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        log.info("File content is {}", content);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename)
    {

    }
}

