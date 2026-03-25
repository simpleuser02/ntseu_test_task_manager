CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX idx_users_email ON users(email);

CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(4000),
    status VARCHAR(50) NOT NULL,
    priority VARCHAR(50) NOT NULL,
    author_id UUID NOT NULL,
    assignee_id UUID,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_tasks_author
        FOREIGN KEY (author_id) REFERENCES users(id),
    CONSTRAINT fk_tasks_assignee
        FOREIGN KEY (assignee_id) REFERENCES users(id)
);
CREATE INDEX idx_tasks_author ON tasks(author_id);
CREATE INDEX idx_tasks_assignee ON tasks(assignee_id);
CREATE INDEX idx_tasks_status ON tasks(status);
