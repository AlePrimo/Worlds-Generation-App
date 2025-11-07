-- ==========================================
-- V2__seed_data.sql
-- Datos iniciales para prueba
-- ==========================================

-- Mundos
INSERT INTO worlds (name, created_at, ticks) VALUES
('Zyrion', NOW(), 0),
('Aurelia', NOW(), 0);

-- Regiones
INSERT INTO regions (name, lat, lon, population, water, food, minerals, alive, world_id) VALUES
('Bosque Norte', -34.56, -58.45, 500, 75.5, 60.0, 120.0, TRUE, 1),
('Desierto del Este', -20.00, 35.00, 200, 10.0, 20.0, 50.0, TRUE, 1),
('Llanuras Centrales', -15.50, 20.50, 350, 50.0, 70.0, 90.0, TRUE, 2);

-- Facciones
INSERT INTO factions (name, aggression, expansionism, size, region_id) VALUES
('Clan del Norte', 0.7, 0.5, 120, 1),
('Tribu del Este', 0.4, 0.6, 80, 2),
('Pueblo del Centro', 0.5, 0.4, 100, 3);

-- Eventos
INSERT INTO events (type, started_at, description, severity, active, region_id) VALUES
('SEQUIA', NOW(), 'Sequía severa que reduce la población', 7, TRUE, 2),
('INUNDACION', NOW(), 'Inundación que afecta los cultivos y viviendas', 5, TRUE, 1),
('PLAGA', NOW(), 'Plaga de insectos que destruye las cosechas', 6, TRUE, 3);
