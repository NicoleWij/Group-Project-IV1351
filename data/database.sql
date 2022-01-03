CREATE DATABASE soundgoodmusicschool;


CREATE TABLE address (
 id INT NOT NULL,
 home_address VARCHAR(50) NOT NULL
);

ALTER TABLE address ADD CONSTRAINT PK_address PRIMARY KEY (id);


CREATE TABLE person (
 id INT NOT NULL,
 name VARCHAR(50) NOT NULL,
 age INT NOT NULL,
 person_number VARCHAR(15) NOT NULL UNIQUE,
 email VARCHAR(50) NOT NULL UNIQUE
);

ALTER TABLE person ADD CONSTRAINT PK_person PRIMARY KEY (id);


CREATE TABLE person_address (
 address_id INT NOT NULL REFERENCES address ON DELETE CASCADE,
 person_id INT NOT NULL REFERENCES person ON DELETE CASCADE
);

ALTER TABLE person_address ADD CONSTRAINT PK_person_address PRIMARY KEY (address_id,person_id);


CREATE TABLE phone_number (
 id INT NOT NULL ,
 phone_no VARCHAR(50) NOT NULL UNIQUE
);

ALTER TABLE phone_number ADD CONSTRAINT PK_phone_number PRIMARY KEY (id);


CREATE TABLE price_list (
 id INT NOT NULL,
 price_type INT NOT NULL,
 price_skill INT NOT NULL,
 discount_sibling INT NOT NULL,
 name VARCHAR(50) NOT NULL
);

ALTER TABLE price_list ADD CONSTRAINT PK_price_list PRIMARY KEY (id);


CREATE TABLE student (
 id INT NOT NULL,
 person_id INT NOT NULL,
 parent_contact_details VARCHAR(500),
 has_sibling BOOLEAN
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (id);


CREATE TABLE instructor (
 id INT NOT NULL,
 teach_ensemble BOOLEAN NOT NULL,
 person_id INT NOT NULL
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (id);


CREATE TABLE lesson (
 id INT NOT NULL,
 time TIMESTAMP NOT NULL,
 skill_level VARCHAR(50) NOT NULL,
 lesson_type VARCHAR(50) NOT NULL,
 room_number VARCHAR(50) NOT NULL,
 address VARCHAR(50) NOT NULL,
 zipcode VARCHAR(50) NOT NULL,
 instrument_type VARCHAR(50),
 genre VARCHAR(50),
 max_spots INT,
 min_spots INT,
 number_of_students INT,
 instructor_id INT NOT NULL,
 price_list_id INT NOT NULL
);

ALTER TABLE lesson ADD CONSTRAINT PK_lesson PRIMARY KEY (id);


CREATE TABLE person_phone_number (
 person_id INT NOT NULL,
 phone_number_id INT NOT NULL
);

ALTER TABLE person_phone_number ADD CONSTRAINT PK_person_phone_number PRIMARY KEY (person_id,phone_number_id);


CREATE TABLE rented_instrument (
 id INT NOT NULL UNIQUE,
 time_of_rental TIMESTAMP NOT NULL,
 instrument_id INT NOT NULL,
 student_id INT NOT NULL,
 previously_rented BOOLEAN NOT NULL
);

ALTER TABLE rented_instrument ADD CONSTRAINT PK_rented_instrument PRIMARY KEY (id);

CREATE TABLE known_instrument (
 id int NOT NULL,
 type VARCHAR(500) NOT NULL
);

ALTER TABLE known_instrument ADD CONSTRAINT PK_known_instrument PRIMARY KEY (id);

CREATE TABLE instructor_known_instrument (
 instructor_id INT NOT NULL  REFERENCES instructor ON DELETE CASCADE,
 known_instrument_id INT NOT NULL  REFERENCES known_instrument ON DELETE CASCADE
);

ALTER TABLE instructor_known_instrument ADD CONSTRAINT PK_instructor_known_instrument PRIMARY KEY (instructor_id,known_instrument_id);


CREATE TABLE instrument(
 id INT NOT NULL UNIQUE,
 type VARCHAR(50) NOT NULL,
 brand VARCHAR(50),
 price INT NOT NULL,
 is_avalible BOOLEAN NOT NULL
);

ALTER TABLE instrument ADD CONSTRAINT PK_instrument PRIMARY KEY (id);


CREATE TABLE student_lesson (
 lesson_id INT NOT NULL REFERENCES lesson ON DELETE CASCADE,
 student_id INT NOT NULL REFERENCES student ON DELETE CASCADE
);

ALTER TABLE student_lesson ADD CONSTRAINT PK_student_lesson PRIMARY KEY (student_id,lesson_id);


ALTER TABLE person_address ADD CONSTRAINT FK_person_address_0 FOREIGN KEY (address_id) REFERENCES address (id);
ALTER TABLE person_address ADD CONSTRAINT FK_person_address_1 FOREIGN KEY (person_id) REFERENCES person (id);


ALTER TABLE student ADD CONSTRAINT FK_student_0 FOREIGN KEY (person_id) REFERENCES person (id);


ALTER TABLE instructor ADD CONSTRAINT FK_instructor_0 FOREIGN KEY (person_id) REFERENCES person (id);


ALTER TABLE lesson ADD CONSTRAINT FK_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (id);
ALTER TABLE lesson ADD CONSTRAINT FK_lesson_1 FOREIGN KEY (price_list_id) REFERENCES price_list (id);


ALTER TABLE person_phone_number ADD CONSTRAINT FK_person_phone_number_0 FOREIGN KEY (person_id) REFERENCES person (id);
ALTER TABLE person_phone_number ADD CONSTRAINT FK_person_phone_number_1 FOREIGN KEY (phone_number_id) REFERENCES phone_number (id);


ALTER TABLE rented_instrument ADD CONSTRAINT FK_rented_instrument_0 FOREIGN KEY (student_id) REFERENCES student (id);
ALTER TABLE rented_instrument ADD CONSTRAINT FK_rented_instrument_1 FOREIGN KEY (instrument_id) REFERENCES instrument (id);


ALTER TABLE student_lesson ADD CONSTRAINT FK_student_lesson_0 FOREIGN KEY (student_id) REFERENCES student (id);
ALTER TABLE student_lesson ADD CONSTRAINT FK_student_lesson_1 FOREIGN KEY (lesson_id) REFERENCES lesson (id);


