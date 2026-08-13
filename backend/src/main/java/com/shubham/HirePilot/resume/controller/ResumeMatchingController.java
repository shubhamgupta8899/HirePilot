package com.shubham.HirePilot.resume.controller;

import com.shubham.HirePilot.resume.dto.JobMatchResponse;
import com.shubham.HirePilot.resume.service.RequestMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resumes/match")
@RequiredArgsConstructor
public class ResumeMatchingController {

    private final RequestMatchingService requestMatchingService;

    @GetMapping("/search")
    public ResponseEntity<List<JobMatchResponse>> findMatchingResume(
            @RequestParam String jobDescription,
            @RequestParam(defaultValue = "5") int topK){

        List<JobMatchResponse> matches = requestMatchingService.findMatchingResumes(jobDescription, topK);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/by-job/{jobId}")
    public ResponseEntity<List<JobMatchResponse>> findMatchingResumeByJobId(
            @PathVariable UUID jobId,
            @RequestParam(defaultValue = "10") int topK) {

        List<JobMatchResponse> matches = requestMatchingService.findMatchingResumesByJobId(jobId, topK);
        return ResponseEntity.ok(matches);
    }
}
