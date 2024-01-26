package com.docker.httpServer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class DataController {

    @GetMapping("/data")
    public String getData(@RequestParam(required = false) String n, @RequestParam(required = false) Integer m) {
        if (n == null || n.isEmpty()) {
            return "File name must be provided.";
        }

        try (Stream<String> lines = Files.lines(Paths.get("/tmp/data/" + n + ".txt"))) {
            if (m != null) {
                return lines.skip(m - 1).findFirst().orElse("No line found at " + m);
            } else {
                return lines.collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }
}
