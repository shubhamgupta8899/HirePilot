package com.shubham.HirePilot.resume.service;

import com.shubham.HirePilot.resume.entity.Resume;
import com.shubham.HirePilot.resume.repository.ResumeRepository;
import com.shubham.HirePilot.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final EmbeddingService embeddingService;
    private final ResumeRepository resumeRepository;

    public Resume saveResume(Resume resume){

        Resume saved = resumeRepository.save(resume);

        embeddingService.embedResume(saved);

        return saved;
    }
}
