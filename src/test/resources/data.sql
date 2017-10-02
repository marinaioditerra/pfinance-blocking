INSERT INTO DEPOSIT (id, version, name, currency) VALUES (12345, 0, 'Kraken', 'BITCOIN');
INSERT INTO DEPOSIT (id, version, name, currency) VALUES (99999, 0, 'Coinbase', 'BITCOIN');

INSERT INTO MOVEMENT (id, version, type, movement_date, amount) VALUES (100, 0, 'IN', CURRENT_TIMESTAMP, 10);
INSERT INTO MOVEMENT (id, version, type, movement_date, amount) VALUES (101, 0, 'OUT', CURRENT_TIMESTAMP, 1);
INSERT INTO MOVEMENT (id, version, type, movement_date, amount, fk_to_deposit) VALUES (200, 0, 'TRANSFER', CURRENT_TIMESTAMP, 2, 99999);

INSERT INTO DEPOSIT_MOVEMENTS (fk_movement, fk_deposit) VALUES (100,12345);
INSERT INTO DEPOSIT_MOVEMENTS (fk_movement, fk_deposit) VALUES (101,12345);
INSERT INTO DEPOSIT_MOVEMENTS (fk_movement, fk_deposit) VALUES (200,99999);