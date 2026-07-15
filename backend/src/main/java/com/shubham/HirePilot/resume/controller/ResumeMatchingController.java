package com.shubham.HirePilot.resume.controller;

import com.shubham.HirePilot.resume.dto.JobMatchResponse;
import com.shubham.HirePilot.resume.service.RequestMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/match")
@RequiredArgsConstructor
public class ResumeMatchingController {

    private final RequestMatchingService requestMatchingService;

    public ResponseEntity<List<JobMatchResponse>> findMatchingResume(
            @RequestParam String jobDescription,
            @RequestParam(defaultValue = "5") int topK){

        List<JobMatchResponse> matches = requestMatchingService.findMatchingResumes(jobDescription, topK);
        return ResponseEntity.ok(matches);
    }
}
