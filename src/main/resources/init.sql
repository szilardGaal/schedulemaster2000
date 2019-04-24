/*
    Database initialization script that runs on every web-application redeployment.
*/
DO $$ BEGIN

DROP TABLE IF EXISTS slots;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS schedule_columns;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    password varchar(20) NOT NULL,
    CONSTRAINT name_not_empty CHECK (name <> ''),
    CONSTRAINT password_not_empty CHECK (password <> '')
);

CREATE TABLE schedules(
    id SERIAL PRIMARY KEY,
    user_id int,
    title text NOT NULL,
    numOfCol int NOT NULL,
    isPublic boolean DEFAULT false,
    FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT limited_num_of_col CHECK ( numOfCol > 0 AND numOfCol <= 7 ),
    CONSTRAINT title_not_empty CHECK ( title <> '' )
);

CREATE TABLE schedule_columns(
    id SERIAL PRIMARY KEY,
    schedule_id int,
    title varchar(150) NOT NULL,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id),
    CONSTRAINT title_not_empty CHECK ( title <> '' )
);

CREATE TABLE tasks(
    id SERIAL PRIMARY KEY,
    user_id int,
    title varchar(150) NOT NULL,
    content text,
    begins int,
    ends int,
    FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT title_not_empty CHECK ( title <> '' ),
    CONSTRAINT limit_begins CHECK ( begins >= 1 AND begins <= 24 ),
    CONSTRAINT limit_ends CHECK ( ends >= 1 AND ends <= 24 AND ends > begins )
);

CREATE TABLE slots(
    column_id int,
    task_id int DEFAULT null,
    time varchar(5),
    FOREIGN KEY (column_id) REFERENCES schedule_columns(id),
    FOREIGN KEY (task_id) REFERENCES tasks(id)
);

CREATE OR REPLACE FUNCTION create_columns_on_schedule() RETURNS TRIGGER AS $A$
    DECLARE
        count int := 1;
    BEGIN
        LOOP
            INSERT INTO schedule_columns (schedule_id, title) VALUES (NEW.id, 'Day '||count);
            count := count + 1;
            EXIT WHEN count = NEW.numOfCol;
        end loop;
        RETURN NEW;
    END; $A$
    LANGUAGE plpgsql;


CREATE TRIGGER columns_on_schedule
    AFTER INSERT
    ON schedules
    FOR EACH ROW
EXECUTE PROCEDURE create_columns_on_schedule();



CREATE OR REPLACE FUNCTION create_slots_on_columns() RETURNS TRIGGER AS $A$
DECLARE
    count int := 1;
BEGIN
    LOOP
        INSERT INTO slots (column_id, time) VALUES (NEW.id, count||':00');
        count := count + 1;
        EXIT WHEN count = 25;
    end loop;
    RETURN NEW;
END; $A$
    LANGUAGE plpgsql;


CREATE TRIGGER slots_on_columns
    AFTER INSERT
    ON schedule_columns
    FOR EACH ROW
EXECUTE PROCEDURE create_slots_on_columns();


INSERT INTO users(name, password) VALUES ('test', 'test');

INSERT INTO schedules(user_id, title, numOfCol) VALUES (1, 'title', '2');
INSERT INTO schedules(user_id, title, numOfCol) VALUES (1, 'title', '3');

INSERT INTO tasks(user_id, title, content) VALUES (1, 'task title', 'task content');


END $$



