package com.metaverse.community_app.file.controller;

import com.metaverse.community_app.file.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("boards/{boardId}/articles/{articleId}/files")
    public ResponseEntity<String> uploadFile(
            @PathVariable Long boardId,
            @PathVariable Long articleId,
            @RequestParam("file") MultipartFile file) {
        fileService.uploadFile(boardId, articleId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("파일 업로드 성공");
    }
}