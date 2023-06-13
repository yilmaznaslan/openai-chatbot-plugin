package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@CrossOrigin(origins = "https://chat.openai.com")
@RestController
public class Controller {

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping(value = "/.well-known/ai-plugin.json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getAiPluginJson() throws IOException {
        logger.info("GET AiPluginJson is called");
        Resource resource = new ClassPathResource("ai-plugin.json");
        Path filePath = resource.getFile().toPath();
        String fileContent = Files.readString(filePath, StandardCharsets.UTF_8);
        return ResponseEntity.ok().body(fileContent);
    }

    @GetMapping(value = "/logo.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> getPluginLogo() {
        logger.info("GET logo.png is called");
        Resource resource = new ClassPathResource("logo.png");
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
    }

    @GetMapping(value = "/openapi.yaml", produces = "text/yaml")
    public ResponseEntity<String> openApiSpec() throws IOException {
        logger.info("GET openApiSpec is called");
        Resource resource = new ClassPathResource("openapi.yaml");
        String content = Files.readString(Path.of(resource.getURI()));
        return ResponseEntity.ok(content);
    }

}
