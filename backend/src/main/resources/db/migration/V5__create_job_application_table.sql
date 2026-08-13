CREATE TABLE IF NOT EXISTS job_application (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL,
    resume_id UUID NOT NULL,
    reviewed_by UUID,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    match_score DOUBLE PRECISION,
    hr_notes TEXT,
    applied_at TIMESTAMP,
    shortlisted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_job_application_job_id
        FOREIGN KEY (job_id) REFERENCES job_descriptions(id) ON DELETE CASCADE,
    CONSTRAINT fk_job_application_resume_id
        FOREIGN KEY (resume_id) REFERENCES resumes(id) ON DELETE CASCADE,
    CONSTRAINT fk_job_application_reviewed_by
        FOREIGN KEY (reviewed_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_job_application_job_id ON job_application(job_id);
CREATE INDEX idx_job_application_resume_id ON job_application(resume_id);
CREATE INDEX idx_job_application_status ON job_application(status);
CREATE INDEX idx_job_application_job_resume ON job_application(job_id, resume_id);
CREATE INDEX idx_job_application_created_at ON job_application(created_at DESC);

COMMENT ON TABLE job_application IS 'Tracks HR shortlisting/rejection decisions for candidates applying to jobs';
COMMENT ON COLUMN job_application.status IS 'PENDING, SHORTLISTED, or REJECTED';
COMMENT ON COLUMN job_application.match_score IS 'AI calculated resume match score (0.0-1.0)';
COMMENT ON COLUMN job_application.hr_notes IS 'HR notes explaining their decision';
COMMENT ON COLUMN job_application.shortlisted_at IS 'When HR made the decision';