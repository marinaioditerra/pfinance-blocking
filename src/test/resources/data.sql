INSERT INTO DEPOSITS (id, version, name, currency) VALUES (12345, 0, 'Kraken', 'BITCOIN');
INSERT INTO DEPOSITS (id, version, name, currency) VALUES (55555, 0, 'MyBank', 'EURO');
INSERT INTO DEPOSITS (id, version, name, currency) VALUES (99999, 0, 'Coinbase', 'BITCOIN');

INSERT INTO MOVEMENTS (id, version, type, movement_date, amount, fk_deposit) VALUES (100, 0, 'IN', CURRENT_TIMESTAMP, 10, 12345);
INSERT INTO MOVEMENTS (id, version, type, movement_date, amount, fk_deposit) VALUES (101, 0, 'OUT', CURRENT_TIMESTAMP, 1, 12345);
INSERT INTO MOVEMENTS (id, version, type, movement_date, amount, fk_deposit) VALUES (102, 0, 'IN', CURRENT_TIMESTAMP, 2, 12345);
INSERT INTO MOVEMENTS (id, version, type, movement_date, amount, fk_deposit, fk_to_deposit) VALUES (200, 0, 'TRANSFER', CURRENT_TIMESTAMP, 2, 99999, 12345);
