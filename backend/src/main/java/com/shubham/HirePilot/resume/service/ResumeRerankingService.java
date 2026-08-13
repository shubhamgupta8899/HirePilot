package com.shubham.HirePilot.resume.service;

import com.shubham.HirePilot.resume.dto.JobMatchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j  // it provide logger so can eaisly track what happen inside
@RequiredArgsConstructor
public class ResumeRerankingService {

    private final ChatClient.Builder chatClientBuilder;

    public List<JobMatchResponse> rerank(String jobDescription, List<JobMatchResponse> candidates){

        ChatClient chatClient = chatClientBuilder.build();

        for(JobMatchResponse candidate: candidates){

            String prompt = """
                    
                    Job Description:
                    %s
                    Candidate Resume:
                    %s
                    
                    According to Job Description give score from 0 to 100 of each candidate.
                    And return only number no text only integer.
                    At the time judging: Match the required skills, Years of experience, TechStack relevent Projects and work history.
                    """.formatted(jobDescription, candidate.getResumeContent());

            try{

                String response = chatClient.prompt()
                        .user(prompt)
                        .call()
                        .content();

                double llmScore = Double.parseDouble(response.trim().replaceAll("[^0-9.]", ""))/100.0;
                candidate.setMatchScore(llmScore); // override vectore score to llm score.
            }catch (Exception e){
                log.warn("Reranking failed for resume {}, fetch vector score {}",
                        candidate.getResumeId(), e.getMessage());
            }
        }

        return candidates.stream()
                .sorted(Comparator.comparing(JobMatchResponse::getMatchScore).reversed())
                .collect(Collectors.toList());
    }
}
