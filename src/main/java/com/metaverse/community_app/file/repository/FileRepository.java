package com.metaverse.community_app.file.repository;

import com.metaverse.community_app.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}