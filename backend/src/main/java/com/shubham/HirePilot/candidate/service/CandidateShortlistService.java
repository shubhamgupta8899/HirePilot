package com.shubham.HirePilot.candidate.service;

import com.shubham.HirePilot.candidate.dto.ShortlistResponse;
import com.shubham.HirePilot.candidate.entity.CandidateShortlist;
import com.shubham.HirePilot.candidate.entity.ShortlistStatus;
import com.shubham.HirePilot.candidate.repository.CandidateShortlistRepository;
import com.shubham.HirePilot.jobdescription.entity.JobDescription;
import com.shubham.HirePilot.jobdescription.repository.JobDescriptionRepository;
import com.shubham.HirePilot.resume.entity.Resume;
import com.shubham.HirePilot.resume.repository.ResumeRepository;
import com.shubham.HirePilot.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateShortlistService {

    private final CandidateShortlistRepository shortlistRepository;
    private final JobDescriptionRepository jobRepository;
    private final ResumeRepository resumeRepository;

    /**
     * ═════════════════════════════════════════════════════════════
     * STEP 3: HR SHORTLISTS a Candidate
     * ═════════════════════════════════════════════════════════════
     *
     * What happens:
     * 1. HR sees AI's match score (0.95, 0.87, etc)
     * 2. HR reads the resume
     * 3. HR clicks "SHORTLIST" button
     * 4. System records HR's decision
     *
     * Input: Job ID, Resume ID, AI Score, HR's reason
     * Output: Shortlist record with HR info
     */
    @Transactional
    public CandidateShortlist shortlistCandidate(
            UUID jobId,                    // Which job?
            UUID resumeId,                 // Which resume?
            Double aiMatchScore,           // ← AI gave this score (0.95)
            String hrNotes,                // ← HR's reason for shortlist
            User hr) {                     // ← Which HR made this decision?

        log.info("🎯 HR SHORTLISTING: Job={}, Resume={}, AI_Score={}, HR={}",
                jobId, resumeId, aiMatchScore, hr.getFirstName());

        // Validate job exists
        JobDescription job = jobRepository.findById(jobId)
                .orElseThrow(() -> {
                    log.error("❌ Job not found: {}", jobId);
                    return new RuntimeException("Job not found");
                });

        // Validate resume exists
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> {
                    log.error("❌ Resume not found: {}", resumeId);
                    return new RuntimeException("Resume not found");
                });

        // Check if decision already made for this candidate
        if (shortlistRepository.findByJobIdAndResumeId(jobId, resumeId).isPresent()) {
            log.warn("⚠️ Decision already made for this candidate");
            throw new RuntimeException("Decision already made for this candidate job combination");
        }

        // Create shortlist record
        CandidateShortlist shortlist = CandidateShortlist.builder()
                .job(job)
                .resume(resume)
                .status(ShortlistStatus.SHORTLISTED)      // ← HR's Decision
                .matchScore(aiMatchScore)                  // ← AI's Score (for reference)
                .hrNotes(hrNotes)                          // ← HR's reason
                .shortlistedBy(hr)                         // ← Which HR decided
                .shortlistedAt(LocalDateTime.now())        // ← When decided
                .build();

        CandidateShortlist saved = shortlistRepository.save(shortlist);
        log.info("✅ SHORTLISTED: Resume {} for Job {} by {}", resumeId, jobId, hr.getFirstName());

        return saved;
    }

    /**
     * ═════════════════════════════════════════════════════════════
     * STEP 3B: HR REJECTS a Candidate
     * ═════════════════════════════════════════════════════════════
     *
     * What happens:
     * 1. HR sees AI's match score (0.50, 0.35, etc)
     * 2. HR reads the resume
     * 3. HR clicks "REJECT" button
     * 4. System records HR's decision + reason
     *
     * Input: Job ID, Resume ID, HR's rejection reason
     * Output: Rejection record with HR info
     */
    @Transactional
    public CandidateShortlist rejectCandidate(
            UUID jobId,                    // Which job?
            UUID resumeId,                 // Which resume?
            String rejectionReason,        // ← HR's reason for rejection
            User hr) {                     // ← Which HR made this decision?

        log.info("❌ HR REJECTING: Job={}, Resume={}, HR={}", jobId, resumeId, hr.getFirstName());

        // Validate job exists
        JobDescription job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        // Validate resume exists
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        // Check if decision already made for this candidate
        if (shortlistRepository.findByJobIdAndResumeId(jobId, resumeId).isPresent()) {
            log.warn("⚠️ Decision already made for this candidate");
            throw new RuntimeException("Decision already made for this candidate job combination");
        }

        // Create rejection record
        CandidateShortlist shortlist = CandidateShortlist.builder()
                .job(job)
                .resume(resume)
                .status(ShortlistStatus.REJECTED)         // ← HR's Decision
                .hrNotes(rejectionReason)                 // ← Why rejected?
                .shortlistedBy(hr)                        // ← Which HR decided
                .shortlistedAt(LocalDateTime.now())       // ← When decided
                .build();

        CandidateShortlist saved = shortlistRepository.save(shortlist);
        log.info("✅ REJECTED: Resume {} for Job {} by {}", resumeId, jobId, hr.getFirstName());

        return saved;
    }

    /**
     * ═════════════════════════════════════════════════════════════
     * STEP 4: GET ALL DECISIONS for a Job
     * ═════════════════════════════════════════════════════════════
     * Shows which candidates HR shortlisted vs rejected
     */
    public List<ShortlistResponse> getJobCandidateDecisions(UUID jobId) {

        log.info("📊 Fetching all decisions for job: {}", jobId);

        List<CandidateShortlist> decisions = shortlistRepository.findByJobId(jobId);

        List<ShortlistResponse> responses = decisions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        log.info("✅ Found {} decisions", responses.size());
        return responses;
    }

    /**
     * Get only SHORTLISTED candidates
     */
    public List<ShortlistResponse> getShortlistedCandidates(UUID jobId) {

        log.info("✅ Fetching shortlisted candidates for job: {}", jobId);

        List<CandidateShortlist> shortlisted = shortlistRepository
                .findByJobIdAndStatus(jobId, ShortlistStatus.SHORTLISTED);

        return shortlisted.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get only REJECTED candidates
     */
    public List<ShortlistResponse> getRejectedCandidates(UUID jobId) {

        log.info("❌ Fetching rejected candidates for job: {}", jobId);

        List<CandidateShortlist> rejected = shortlistRepository
                .findByJobIdAndStatus(jobId, ShortlistStatus.REJECTED);

        return rejected.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convert to response DTO
     */
    private ShortlistResponse toResponse(CandidateShortlist shortlist) {
        return ShortlistResponse.builder()
                .shortlistId(shortlist.getId())
                .jobId(shortlist.getJob().getId())
                .jobTitle(shortlist.getJob().getTitle())
                .resumeId(shortlist.getResume().getId())
                .resumeFileName(shortlist.getResume().getFileName())
                .status(shortlist.getStatus())
                .matchScore(shortlist.getMatchScore())    // ← AI's score
                .hrNotes(shortlist.getHrNotes())          // ← HR's decision reason
                .shortlistedAt(shortlist.getShortlistedAt())
                .shortlistedBy(shortlist.getShortlistedBy() != null ?
                        shortlist.getShortlistedBy().getFirstName() + " " +
                                shortlist.getShortlistedBy().getLastName() : null)
                .build();
    }
}