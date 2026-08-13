package com.shubham.HirePilot.jobdescription.controller;

import com.shubham.HirePilot.jobdescription.entity.JobDescription;
import com.shubham.HirePilot.jobdescription.repository.JobDescriptionRepository;
import com.shubham.HirePilot.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobDescriptionController {

    private final JobDescriptionRepository jobDescriptionRepository;

    // HR bas title + description type karega, kahi publicly list nahi hoga
    @PostMapping
    public ResponseEntity<JobDescription> createJob(
            @RequestBody CreateJobRequest request,
            Authentication authentication) {

        User hr = (User) authentication.getPrincipal();

        JobDescription job = JobDescription.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .department(request.getDepartment())
                .location(request.getLocation())
                .createdBy(hr)
                .build();

        JobDescription saved = jobDescriptionRepository.save(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Getter
    @Setter
    public static class CreateJobRequest {
        @NotBlank
        private String title;

        @NotBlank
        private String description;

        private String department;
        private String location;
    }
}