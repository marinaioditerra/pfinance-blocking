INSERT INTO USERS (id, version, username, password) VALUES (1, 0, 'john', 'pinball');
INSERT INTO USERS (id, version, username, password) VALUES (2, 0, 'nick', 'football');

INSERT INTO DEPOSITS (id, version, name, currency, fk_user) VALUES (12345, 0, 'Kraken', 'BITCOIN', 1);
INSERT INTO DEPOSITS (id, version, name, currency, fk_user) VALUES (55555, 0, 'MyBank', 'EURO', 1);
INSERT INTO DEPOSITS (id, version, name, currency, fk_user) VALUES (99999, 0, 'Coinbase', 'BITCOIN', 2);

INSERT INTO MOVEMENTS (id, version, type, movement_date, amount, fk_deposit) VALUES (100, 0, 'IN', CURRENT_TIMESTAMP, 10, 12345);
INSERT INTO MOVEMENTS (id, version, type, movement_date, amount, fk_deposit) VALUES (101, 0, 'OUT', CURRENT_TIMESTAMP, 1, 12345);
INSERT INTO MOVEMENTS (id, version, type, movement_date, amount, fk_deposit) VALUES (102, 0, 'IN', CURRENT_TIMESTAMP, 2, 12345);
INSERT INTO MOVEMENTS (id, version, type, movement_date, amount, fk_deposit, fk_to_deposit) VALUES (200, 0, 'TRANSFER', CURRENT_TIMESTAMP, 2, 99999, 12345);
