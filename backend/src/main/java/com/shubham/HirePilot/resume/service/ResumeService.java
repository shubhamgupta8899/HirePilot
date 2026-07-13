package com.shubham.HirePilot.resume.service;

import com.shubham.HirePilot.resume.entity.ParseStatus;
import com.shubham.HirePilot.resume.entity.Resume;
import com.shubham.HirePilot.resume.repository.ResumeRepository;
import com.shubham.HirePilot.service.EmbeddingService;
import com.shubham.HirePilot.service.TextExtractionService;
import com.shubham.HirePilot.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeService {

    private final EmbeddingService embeddingService;
    private final ResumeRepository resumeRepository;
    private final TextExtractionService textExtractionService;

    public Resume uploadResume(MultipartFile file, User uploadedBy) throws IOException {

        Resume resume = Resume.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .fileData(file.getBytes())  // BYTEA me store
                .uploadedBy(uploadedBy)
                .parseStatus(ParseStatus.PENDING)
                .build();

        resume = resumeRepository.save(resume);
        log.info("Resume saved with ID: {}", resume.getId());

        try{

            String extractedText = textExtractionService.extractText(

                    file.getBytes(),
                    file.getContentType()
            );

            resume.setParsedText(extractedText);
            resume.setParseStatus(ParseStatus.FAILED);
        }catch (Exception e){

            log.error("Text extraction failed for resume {}: {}", resume.getId(), e.getMessage());
            resume.setParseStatus(ParseStatus.FAILED);
        }

        resume = resumeRepository.save(resume);

        if(resume.getParseStatus() == ParseStatus.COMPLETED){

            try{

                embeddingService.embedResume(resume);

            }catch (Exception e){
                log.error("Embeddng faild for resume {} : {}", resume.getId(), e.getMessage());
            }
        }

        return resume;
    }

    public List<Resume> getUserResume(Long userId){

        return resumeRepository.findByUploadedById(userId);
    }

    public Resume getResume(Long resumeId, Long userId){

        return resumeRepository.findByIdAndUploadedById(resumeId, userId)
                .orElseThrow(()-> new RuntimeException("Resume not found"));
    }

    public void deleteResume(Long resumeId, Long userId){

        Resume resume = getResume(resumeId, userId);
        resumeRepository.delete(resume);
        log.info("Resume {} deleted", resumeId);
    }
}
