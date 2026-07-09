CREATE TABLE resumes (
    id              UUID PRIMARY KEY,
    file_name       VARCHAR(255) NOT NULL,
    content_type    VARCHAR(100) NOT NULL,
    file_size       BIGINT       NOT NULL,
    file_data       BYTEA        NOT NULL,
    parsed_text     TEXT,
    parse_status    VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    uploaded_by     UUID         NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT now(),
    CONSTRAINT fk_resumes_uploaded_by FOREIGN KEY (uploaded_by) REFERENCES users(id)
);

CREATE INDEX idx_resumes_uploaded_by ON resumes(uploaded_by);
CREATE INDEX idx_resumes_parse_status ON resumes(parse_status);