CREATE TABLE IF NOT EXISTS app_users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS content (
    id SERIAL PRIMARY KEY,
    content_type VARCHAR(20) NOT NULL CHECK (content_type IN ('MOVIE', 'SERIES')),
    title VARCHAR(255) NOT NULL,
    release_year INTEGER NOT NULL,
    director VARCHAR(255) NOT NULL,
    duration_minutes INTEGER,
    number_of_seasons INTEGER,
    is_ongoing BOOLEAN,
    average_rating NUMERIC(3, 1) NOT NULL DEFAULT 0.0,
    rating_count INTEGER NOT NULL DEFAULT 0,
    CHECK (
        (content_type = 'MOVIE' AND duration_minutes IS NOT NULL)
        OR
        (content_type = 'SERIES' AND number_of_seasons IS NOT NULL AND is_ongoing IS NOT NULL)
    )
);

CREATE TABLE IF NOT EXISTS content_genres (
    content_id INTEGER NOT NULL REFERENCES content(id) ON DELETE CASCADE,
    genre VARCHAR(50) NOT NULL,
    PRIMARY KEY (content_id, genre)
);

CREATE TABLE IF NOT EXISTS episodes (
    id SERIAL PRIMARY KEY,
    series_id INTEGER NOT NULL REFERENCES content(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    season_number INTEGER NOT NULL,
    episode_number INTEGER NOT NULL,
    duration_minutes INTEGER NOT NULL,
    description TEXT,
    UNIQUE (series_id, season_number, episode_number)
);

CREATE TABLE IF NOT EXISTS watchlist_items (
    user_id INTEGER NOT NULL REFERENCES app_users(id) ON DELETE CASCADE,
    content_id INTEGER NOT NULL REFERENCES content(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, content_id)
);

CREATE TABLE IF NOT EXISTS watched_content (
    user_id INTEGER NOT NULL REFERENCES app_users(id) ON DELETE CASCADE,
    content_id INTEGER NOT NULL REFERENCES content(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, content_id)
);

CREATE TABLE IF NOT EXISTS reviews (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES app_users(id) ON DELETE CASCADE,
    content_id INTEGER NOT NULL REFERENCES content(id) ON DELETE CASCADE,
    text TEXT NOT NULL,
    contains_spoiler BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ratings (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES app_users(id) ON DELETE CASCADE,
    content_id INTEGER NOT NULL REFERENCES content(id) ON DELETE CASCADE,
    score NUMERIC(3, 1) NOT NULL CHECK (score >= 1.0 AND score <= 10.0),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, content_id)
);
