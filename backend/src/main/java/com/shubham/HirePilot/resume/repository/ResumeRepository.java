package com.shubham.HirePilot.resume.repository;

import com.shubham.HirePilot.resume.entity.ParseStatus;
import com.shubham.HirePilot.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {

    List<Resume> findByUploadedById(UUID userId);

    Optional<Resume> findByIdAndUploadedById(UUID id, UUID userId);

    List<Resume> findByParseStatus(ParseStatus status);

    Long countByUploadedById(UUID userId);

    boolean existsByIdAndUploadedById(UUID id, UUID userId);
}
