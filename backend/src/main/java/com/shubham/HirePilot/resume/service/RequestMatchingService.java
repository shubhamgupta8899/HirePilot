package com.shubham.HirePilot.resume.service;

import com.shubham.HirePilot.jobdescription.entity.JobDescription;
import com.shubham.HirePilot.resume.dto.JobMatchResponse;
import com.shubham.HirePilot.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestMatchingService {

    private final EmbeddingService embeddingService;

    public List<JobMatchResponse> findMatchingResumes(String jobDescription, int topK){

        List<Document> similarDocs = embeddingService.searchResumes(jobDescription, topK);

        log.info("Found {} Matching resumes for job Description", similarDocs.size());

        return similarDocs.stream()
                .map(this::documentToResponse)
                .collect(Collectors.toList());
    }

    private JobMatchResponse documentToResponse(Document doc){

        return JobMatchResponse.builder()
                .resumeId(Long.valueOf(doc.getMetadata().get("resumeId").toString()))
                .fileName((String) doc.getMetadata().get("fileName"))
                .userId(Long.valueOf(doc.getMetadata().get("userId").toString()))
                .matchScore(doc.getMetadata().get("score") != null ?
                        Double.valueOf(doc.getMetadata().get("score").toString()) : 0.0)

                .resumeContent(doc.getText())
                .build();

    }
}
