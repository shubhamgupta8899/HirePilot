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

        if (resume.getParsedText() == null || resume.getParsedText().isEmpty()) {
            log.warn("Resume ID: {} me koi text nahi hai", resume.getId());
            return;
        }

        // Document banao — Qdrant ko ye format chahiye
        Document doc = new Document(
                resume.getParsedText(),  // Content
                java.util.Map.of(
                        "resumeId", resume.getId().toString(),
                        "userId", resume.getUploadedBy().getId().toString(),
                        "fileName", resume.getFileName(),
                        "uploadDate", resume.getCreatedAt().toString()
                )
        );

        // Qdrant me store kar
        vectorStore.add(List.of(doc));

        log.info("Resume {} embedded successfully in Qdrant", resume.getId());
    }

    /**
     * Job description ke basis pe matching resumes find karo
     */
    public List<Document> searchResumes(String jobDescription, int topK) {

        SearchRequest request = SearchRequest.query(jobDescription)
                .withTopK(topK);

        return vectorStore.similaritySearch(request);
    }
}