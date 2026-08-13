package com.shubham.HirePilot.jobdescription.repository;

import com.shubham.HirePilot.jobdescription.entity.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobDescriptionRepository extends JpaRepository<JobDescription, UUID> {

    List<JobDescription> findByCreatedById(UUID userId);
    List<JobDescription> findByIsActiveTrue();

    Optional<JobDescription> findByIdAndCreatedById(UUID id, UUID userId);

    Long countByCreatedById(UUID userId);

}
