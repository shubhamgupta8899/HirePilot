package com.shubham.HirePilot.resume.repository;

import com.shubham.HirePilot.resume.entity.ParseStatus;
import com.shubham.HirePilot.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByUploadedById(Long userId);

    Optional<Resume> findByIdAndUploadedById(Long id, Long userId);

    List<Resume> findByParseStatus(ParseStatus status);

    Long countByUploadedId(Long userId);
}
