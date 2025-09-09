/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.domain.api;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class HmmCBApiController {

  @Value("${daptalk.file-path}")
  private String FILE_DIRECTORY;

  @PostMapping("/email/send")
  public Mono<String> hello() {
    return Mono.just("Hello, WebFlux!");
  }

  @PostMapping("/notice/birthDay")
  public Mono<String> noticeBirthDay() {
    return Mono.just("Happy  Birthday!");
  }

  @GetMapping("/numbers")
  public Flux<Integer> numbers() {
    return Flux.range(1, 10);
  }

  @GetMapping("/download/{filename}")
  public Mono<ResponseEntity<Resource>> downloadFile(@PathVariable String filename) {
    Path filePath = Paths.get(FILE_DIRECTORY).resolve(filename).normalize();
    File file = filePath.toFile();

    if (!file.exists() || !file.isFile()) {
      return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    Resource resource = new FileSystemResource(file);

    return Mono.just(
        ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource));
  }
}
