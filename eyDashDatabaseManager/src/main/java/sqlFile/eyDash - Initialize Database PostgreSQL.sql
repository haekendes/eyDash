CREATE TABLE users(
userid INTEGER,
firstname TEXT,
lastname TEXT,
token BYTEA,
bluetoothname TEXT,
bluetoothadress TEXT,
PRIMARY KEY(userid)
);

CREATE TABLE appointments(
userid INTEGER,
appointmentid INTEGER,
message TEXT,
appointmentdate DATE,
PRIMARY KEY(appointmentid)
);

CREATE SEQUENCE public.hibernate_sequence INCREMENT 1 START 1 MINVALUE 1;