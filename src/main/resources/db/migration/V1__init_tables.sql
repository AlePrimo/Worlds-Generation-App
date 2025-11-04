CREATE TABLE worlds (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    ticks BIGINT DEFAULT 0
);

CREATE TABLE regions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    lat DOUBLE PRECISION,
    lon DOUBLE PRECISION,
    population INT CHECK (population >= 0),
    water DOUBLE PRECISION CHECK (water BETWEEN 0 AND 100),
    food DOUBLE PRECISION CHECK (food BETWEEN 0 AND 100),
    minerals DOUBLE PRECISION CHECK (minerals >= 0),
    alive BOOLEAN DEFAULT TRUE,
    world_id BIGINT NOT NULL,
    CONSTRAINT fk_regions_world FOREIGN KEY(world_id)
        REFERENCES worlds(id) ON DELETE CASCADE
);

CREATE TABLE factions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    aggression DOUBLE PRECISION CHECK (aggression BETWEEN 0 AND 1),
    expansionism DOUBLE PRECISION CHECK (expansionism BETWEEN 0 AND 1),
    size INT CHECK (size >= 0),
    region_id BIGINT NOT NULL,
    CONSTRAINT fk_factions_region FOREIGN KEY(region_id)
        REFERENCES regions(id) ON DELETE CASCADE
);

CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    started_at TIMESTAMP NOT NULL,
    description TEXT NOT NULL,
    severity INT CHECK (severity BETWEEN 1 AND 10),
    active BOOLEAN DEFAULT TRUE,
    region_id BIGINT NOT NULL,
    CONSTRAINT fk_events_region FOREIGN KEY(region_id)
        REFERENCES regions(id) ON DELETE CASCADE
);
