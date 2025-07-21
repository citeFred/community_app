package com.metaverse.community_app.file.service;

import com.metaverse.community_app.article.domain.Article;
import com.metaverse.community_app.article.repository.ArticleRepository;
import com.metaverse.community_app.file.domain.File;
import com.metaverse.community_app.file.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final ArticleRepository articleRepository;

    public FileService(FileRepository fileRepository, ArticleRepository articleRepository) {
        this.fileRepository = fileRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public void uploadFile(Long boardId, Long articleId, MultipartFile multipartFile) {
        Article article = articleRepository.findByIdAndBoardId(articleId, boardId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 게시판(ID: " + boardId + ")에서 게시글(ID: " + articleId + ")을 찾을 수 없습니다.")
                );

        String originalFileName = multipartFile.getOriginalFilename();
        String storedFileName = UUID.randomUUID() + "_" + originalFileName;
        String filePath = "uploads/" + storedFileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }

        File file = new File(originalFileName, storedFileName, filePath, article);
        fileRepository.save(file);
    }
}