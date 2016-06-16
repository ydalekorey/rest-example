# --- !Ups

CREATE TABLE products (
	code CHAR(5) NOT NULL,
	name VARCHAR(20) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (code)
);

# --- !Downs

DROP TABLE products;