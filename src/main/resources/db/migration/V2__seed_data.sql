INSERT INTO worlds (name, created_at, ticks) VALUES
('Zyrion', NOW(), 0),
('Aurelia', NOW(), 0);

INSERT INTO regions (name, lat, lon, population, water, food, minerals, alive, world_id) VALUES
('Bosque Norte', -34.56, -58.45, 500, 75.5, 60.0, 120.0, TRUE, 1),
('Desierto del Este', -20.0, 35.0, 200, 10.0, 20.0, 50.0, TRUE, 1);

INSERT INTO factions (name, aggression, expansionism, size, region_id) VALUES
('Clan del Norte', 0.7, 0.5, 120, 1),
('Tribu Este', 0.4, 0.6, 80, 2);

INSERT INTO events (type, started_at, description, severity, active, region_id) VALUES
('SEQUIA', NOW(), 'Sequía severa que reduce la población', 7, TRUE, 2),
('INUNDACION', NOW(), 'Inundación que afecta cultivos', 5, TRUE, 1);
