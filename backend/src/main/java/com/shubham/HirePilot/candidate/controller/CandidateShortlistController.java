package com.shubham.HirePilot.candidate.controller;

import com.shubham.HirePilot.candidate.dto.RejectCandidateRequest;
import com.shubham.HirePilot.candidate.dto.ShortlistCandidateRequest;
import com.shubham.HirePilot.candidate.dto.ShortlistResponse;
import com.shubham.HirePilot.candidate.entity.CandidateShortlist;
import com.shubham.HirePilot.candidate.service.CandidateShortlistService;
import com.shubham.HirePilot.user.entity.User;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateShortlistController {

    private final CandidateShortlistService candidateShortlistService;

    @PostMapping("/shortlist")
    public ResponseEntity<CandidateShortlist> shortlistCandidate(@Valid @RequestBody ShortlistCandidateRequest request, Authentication authentication){

        User hr = (User) authentication.getPrincipal();

        CandidateShortlist saved = candidateShortlistService.shortlistCandidate(
                request.getJobId(),
                request.getResumeId(),
                request.getMatchScore(),
                request.getHrNotes(),
                hr
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/reject")
    public ResponseEntity<CandidateShortlist> rejectCandidate(@Valid @RequestBody RejectCandidateRequest request, Authentication authentication){

        User hr = (User) authentication.getPrincipal();
        CandidateShortlist saved =  candidateShortlistService.rejectCandidate(
                request.getJobId(),
                request.getResumeId(),
                request.getReason(),
                hr
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ShortlistResponse>> getAllDecisions(@PathVariable UUID jobId){

        return ResponseEntity.ok(candidateShortlistService.getJobCandidateDecisions(jobId));
    }

    @GetMapping("/job/{jobId}/shortlisted")
    public ResponseEntity<List<ShortlistResponse>> getShortlisted(@PathVariable UUID jobId) {
        return ResponseEntity.ok(candidateShortlistService.getShortlistedCandidates(jobId));
    }

    @GetMapping("/job/{jobId}/rejected")
    public ResponseEntity<List<ShortlistResponse>> getRejected(@PathVariable UUID jobId) {
        return ResponseEntity.ok(candidateShortlistService.getRejectedCandidates(jobId));
    }
}
