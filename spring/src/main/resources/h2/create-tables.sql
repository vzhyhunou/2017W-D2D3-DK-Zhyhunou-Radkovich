CREATE TABLE customers (
    customerId INTEGER PRIMARY KEY,
    firstName VARCHAR(30),
    lastName VARCHAR(30)
);

CREATE TABLE flights (
    flightId INTEGER PRIMARY KEY,
    dataOfFlight DATE
);

CREATE TABLE tickets (
    flightId INTEGER,
    customerId INTEGER,
    ticketId INTEGER PRIMARY KEY,
    place INTEGER,
    isBooked BOOLEAN
);