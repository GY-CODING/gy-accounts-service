package org.gycoding.accounts.controller;

import org.gycoding.accounts.model.entities.ServerStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        Resource resource = new ClassPathResource("static/index.html");

        try {
            return new String(Files.readAllBytes(Paths.get(resource.getURI())));
        } catch (IOException e) {
            e.printStackTrace();
            return ServerStatus.NOT_FOUND.toString();
        }
    }
}
