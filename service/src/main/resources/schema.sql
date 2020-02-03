CREATE TABLE IF NOT EXISTS translate_requests
(
    id         UUID PRIMARY KEY,
    text_from   VARCHAR(4096) NOT NULL,
    text_to     VARCHAR(4096),
    from_lang   VARCHAR(255) NOT NULL,
    to_lang     VARCHAR(255) NOT NULL,
    client_ip   varchar(50) NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    finished_at TIMESTAMP WITH TIME ZONE
);