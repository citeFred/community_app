// 예시: FileResponseDto.java
package com.metaverse.community_app.file.dto;

import com.metaverse.community_app.file.domain.File; // File 엔티티 임포트
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileResponseDto {
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private String filePath;

    public FileResponseDto(File file) {
        this.id = file.getId();
        this.originalFileName = file.getOriginalFileName();
        this.storedFileName = file.getStoredFileName();
        this.filePath = file.getFilePath();
    }
}