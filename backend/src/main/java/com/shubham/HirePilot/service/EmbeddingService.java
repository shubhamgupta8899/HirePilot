package com.shubham.HirePilot.service;

import com.shubham.HirePilot.resume.entity.Resume;
import com.shubham.HirePilot.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmbeddingService {

    private final VectorStore vectorStore;
    private final ResumeRepository resumeRepository;

    /**
     * Resume ke parsedText ko vector me convert karke Qdrant me store karo
     */
    public void embedResume(Resume resume) {

        log.info(" Starting embedding process for resume: {}", resume.getId());

        if (resume.getParsedText() == null || resume.getParsedText().isEmpty()) {
            log.warn("Resume ID: {} me koi text nahi hai", resume.getId());
            return;
        }

        // Document banao — Qdrant ko ye format chahiye
        try {
            // Document banao
            Document doc = new Document(
                    resume.getParsedText(),
                    java.util.Map.of(
                            "resumeId", resume.getId().toString(),
                            "userId", resume.getUploadedBy().getId().toString(),
                            "fileName", resume.getFileName(),
                            "uploadDate", resume.getCreatedAt().toString()
                    )
            );

            log.info(" Document created. Text length: {} chars", resume.getParsedText().length());

            // Qdrant me store kar
            vectorStore.add(List.of(doc));

            log.info(" Resume {} embedded successfully in Qdrant", resume.getId());

        } catch (Exception e) {
            log.error(" Embedding error for resume {}: {}", resume.getId(), e.getMessage(), e);
            throw new RuntimeException("Failed to embed resume: " + e.getMessage(), e);
        }

    }

    /**
     * Job description ke basis pe matching resumes find karo
     */
    public List<Document> searchResumes(String jobDescription, int topK) {

        SearchRequest request = SearchRequest.builder()
                .query(jobDescription)
                .topK(topK)
                .build();

        return vectorStore.similaritySearch(request);
    }
}