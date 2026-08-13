package com.shubham.HirePilot.candidate.repository;

import com.shubham.HirePilot.candidate.entity.CandidateShortlist;
import com.shubham.HirePilot.candidate.entity.ShortlistStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateShortlistRepository extends JpaRepository<CandidateShortlist, UUID> {

    List<CandidateShortlist> findByJobId(UUID jobId);

    List<CandidateShortlist> findByJobIdAndStatus(UUID jobId, ShortlistStatus status);

    List<CandidateShortlist> findByResumeId(UUID resumeId);

    Optional<CandidateShortlist> findByJobIdAndResumeId(UUID jobId, UUID resumeId);

    Long countByJobIdAndStatus(UUID jobId, ShortlistStatus status);
}