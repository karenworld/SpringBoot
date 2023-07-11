-- Create the Customer table
CREATE TABLE customer (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ic VARCHAR(255) NOT NULL
);

-- Create the Transaction table
CREATE TABLE transaction (
    id INT PRIMARY KEY,
    custId INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (custId) REFERENCES customer (id)
);
