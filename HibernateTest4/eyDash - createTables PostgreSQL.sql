CREATE TABLE users(
userid INTEGER,
firstname TEXT,
lastname TEXT,
phonenumber INTEGER,
PRIMARY KEY(userid, phonenumber)
);

CREATE TABLE appointments(
userid INTEGER,
appointmentid INTEGER,
message TEXT,
appointmentdate DATE,
appointmenttime TIME,
PRIMARY KEY(appointmentid)
);