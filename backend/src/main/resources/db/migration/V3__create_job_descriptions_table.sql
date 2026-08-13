CREATE TABLE job_descriptions (
    id              UUID PRIMARY KEY,
    title           VARCHAR(200) NOT NULL,
    description     TEXT         NOT NULL,
    department      VARCHAR(100),
    location        VARCHAR(100),
    created_by      UUID         NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT now(),
    CONSTRAINT fk_jd_created_by FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE INDEX idx_jd_created_by ON job_descriptions(created_by);