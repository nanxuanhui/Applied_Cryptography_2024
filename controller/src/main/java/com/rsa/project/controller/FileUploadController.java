package com.rsa.project.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
public class FileUploadController {

    private final String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Cannot upload empty file");
        }

        try {
            File directory = new File(uploadDirectory);
            System.out.println(directory.getName());
            if (!directory.exists()) directory.mkdirs(); // Create the directory if it doesn't exist

            String filePath = Paths.get(uploadDirectory, file.getOriginalFilename()).toString();
            file.transferTo(new File(filePath)); // Save the file
            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Could not upload the file: " + file.getOriginalFilename());
        }



    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(uploadDirectory).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/filelist")
    public ResponseEntity<List<Map<String, String>>> listFiles() {
        List<Map<String, String>> fileList = new ArrayList<>();
        File directory = new File(uploadDirectory);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    Map<String, String> fileInfo = new HashMap<>();
                    String fileName = file.getName();
                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/files/")
                            .path(fileName)
                            .toUriString();

                    fileInfo.put("name", fileName);
                    fileInfo.put("url", fileDownloadUri);
                    fileList.add(fileInfo);
                }
            }
        }

        return ResponseEntity.ok(fileList);
    }
}
