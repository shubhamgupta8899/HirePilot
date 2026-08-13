package com.shubham.HirePilot.resume.service;

import com.shubham.HirePilot.jobdescription.entity.JobDescription;
import com.shubham.HirePilot.jobdescription.repository.JobDescriptionRepository;
import com.shubham.HirePilot.resume.dto.JobMatchResponse;
import com.shubham.HirePilot.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestMatchingService {

    private final EmbeddingService embeddingService;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final ResumeRerankingService resumeRerankingService;
    public List<JobMatchResponse> findMatchingResumes(String jobDescription, int topK){

        List<Document> similarDocs = embeddingService.searchResumes(jobDescription, topK);

        log.info("Found {} Matching resumes for job Description", similarDocs.size());

        return similarDocs.stream()
                .map(this::documentToResponse)
                .collect(Collectors.toList());
    }

    // Naya method — jobId se hi kaam chal jayega
    public List<JobMatchResponse> findMatchingResumesByJobId(UUID jobId, int topK) {

        // Step 1: jobId se JobDescription row uthao
        JobDescription job = jobDescriptionRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found: " + jobId));

        // Step 2: uska description column hi "query text" ban jayega
        String queryText = job.getDescription();

        log.info("Matching resumes for job '{}' (id={})", job.getTitle(), jobId);

        // Step 3: wahi purana embedding-search logic reuse karo
        List<Document> similarDocs = embeddingService.searchResumes(queryText, Math.max(topK * 2, 20));

        List<JobMatchResponse> initialMatches = similarDocs.stream()
                .map(this::documentToResponse)
                .collect(Collectors.toList());

        List<JobMatchResponse> reranked = resumeRerankingService.rerank(job.getDescription(), initialMatches);

        return reranked.stream().limit(topK).collect(Collectors.toList());
    }

    private JobMatchResponse documentToResponse(Document doc){

        return JobMatchResponse.builder()
                .resumeId(UUID.fromString(doc.getMetadata().get("resumeId").toString()))
                .fileName((String) doc.getMetadata().get("fileName"))
                .userId(UUID.fromString(doc.getMetadata().get("userId").toString()))
                // Score comes from Document.getScore(), NOT from metadata
                .matchScore(doc.getScore() != null ? doc.getScore() : 0.0)
                .resumeContent(doc.getText())
                .build();

    }
}
