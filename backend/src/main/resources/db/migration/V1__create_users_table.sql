CREATE TABLE users (
    id              UUID PRIMARY KEY,
    first_name      VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    role            VARCHAR(20)  NOT NULL,
    enabled         BOOLEAN      NOT NULL DEFAULT TRUE,
    account_locked  BOOLEAN      NOT NULL DEFAULT FALSE,
    email_verified  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE INDEX idx_users_email ON users(email);