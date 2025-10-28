CREATE TABLE worlds (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    ticks BIGINT DEFAULT 0
);

CREATE TABLE regions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    lat DOUBLE PRECISION,
    lon DOUBLE PRECISION,
    population INT,
    water DOUBLE PRECISION,
    food DOUBLE PRECISION,
    minerals DOUBLE PRECISION,
    alive BOOLEAN DEFAULT TRUE,
    world_id BIGINT NOT NULL,
    CONSTRAINT fk_regions_world FOREIGN KEY(world_id) REFERENCES worlds(id) ON DELETE CASCADE
);

CREATE TABLE factions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    aggression DOUBLE PRECISION,
    expansionism DOUBLE PRECISION,
    size INT,
    region_id BIGINT NOT NULL,
    CONSTRAINT fk_factions_region FOREIGN KEY(region_id) REFERENCES regions(id) ON DELETE CASCADE
);

CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    started_at TIMESTAMP NOT NULL,
    description TEXT NOT NULL,
    severity INT,
    active BOOLEAN DEFAULT TRUE,
    region_id BIGINT NOT NULL,
    CONSTRAINT fk_events_region FOREIGN KEY(region_id) REFERENCES regions(id) ON DELETE CASCADE
);

