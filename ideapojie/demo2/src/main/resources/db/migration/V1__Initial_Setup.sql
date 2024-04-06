CREATE TABLE customer
(
    id    BIGSERIAL  PRIMARY KEY,
    name  VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    password TEXT not null ,
    age   INT     NOT NULL,
    gender text not null
);
CREATE TABLE supplier (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR UNIQUE NOT NULL,
                          address VARCHAR,
                          contact VARCHAR
);

CREATE TABLE medicine (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR NOT NULL,
                          mid VARCHAR NOT NULL,
                          date text NOT NULL,
                          stock INTEGER NOT NULL,
                          price INTEGER NOT NULL,
                          supplier_id INTEGER REFERENCES supplier(id) ON DELETE CASCADE
);
