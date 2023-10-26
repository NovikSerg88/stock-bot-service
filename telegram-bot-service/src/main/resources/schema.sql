drop table if exists chats;

CREATE TABLE IF NOT EXISTS chats (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    chat_id BIGINT unique NOT NULL,
    bot_state VARCHAR(16) NOT NULL
);