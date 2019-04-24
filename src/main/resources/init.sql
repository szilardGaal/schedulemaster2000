/*
    Database initialization script that runs on every web-application redeployment.
*/
DO $$ BEGIN

DROP TABLE IF EXISTS slots CASCADE;
DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS schedule_columns CASCADE;
DROP TABLE IF EXISTS schedules CASCADE;
DROP TABLE IF EXISTS users CASCADE;

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
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT limited_num_of_col CHECK ( numOfCol > 0 AND numOfCol <= 7 ),
    CONSTRAINT title_not_empty CHECK ( title <> '' )
);

CREATE TABLE schedule_columns(
    id SERIAL PRIMARY KEY,
    schedule_id int,
    title varchar(150) NOT NULL,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT title_not_empty CHECK ( title <> '' )
);

CREATE TABLE tasks(
    id SERIAL PRIMARY KEY,
    user_id int,
    title varchar(150) NOT NULL,
    content text,
    begins int,
    duration int,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT title_not_empty CHECK ( title <> '' ),
    CONSTRAINT limit_begins CHECK ( begins >= 1 AND begins <= 24 ),
    CONSTRAINT limit_ends CHECK ( duration >= 1 AND duration <= 24-begins )
);

CREATE TABLE slots(
    column_id int,
    task_id int DEFAULT null,
    time varchar(5),
    FOREIGN KEY (column_id) REFERENCES schedule_columns(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE ON UPDATE CASCADE
);


/*Creates the given number of columns previously defined for the specific schedule by user*/

CREATE OR REPLACE FUNCTION create_columns_on_schedule() RETURNS TRIGGER AS $A$
    DECLARE
        count int := 0;
    BEGIN
        LOOP
            INSERT INTO schedule_columns (schedule_id, title) VALUES (NEW.id, 'Day '||count+1);
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

/*Automatically creates 24 hour slots for each column (day) in the schedule)*/

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

/*Update on slots only necessary once, called on the beginning date of the task added to the schedule, the following
  slots will be added automatically according to the previously defined duration*/

CREATE OR REPLACE FUNCTION add_tasks_to_slots() RETURNS TRIGGER AS $A$
BEGIN
    IF NEW.time != ((SELECT begins FROM tasks WHERE id=NEW.task_id)+(SELECT duration FROM tasks WHERE id=NEW.task_id)-1)||':00' THEN
        UPDATE slots SET task_id=NEW.task_id WHERE column_id = NEW.column_id AND time = ((split_part(NEW.time, ':', 1))::int)+1||':00';
    end if;
    RETURN NEW;
END; $A$
    LANGUAGE plpgsql;

CREATE TRIGGER task_on_slots
    AFTER UPDATE
    ON slots
    FOR EACH ROW
EXECUTE PROCEDURE  add_tasks_to_slots();






INSERT INTO users(name, password) VALUES ('test', 'test');

INSERT INTO schedules(user_id, title, numOfCol) VALUES (1, 'title', '2');
INSERT INTO schedules(user_id, title, numOfCol) VALUES (1, 'title', '3');

INSERT INTO tasks(user_id, title, content, begins, duration) VALUES (1, 'task title', 'task content', 4, 2);
INSERT INTO tasks(user_id, title, content, begins, duration) VALUES (1, 'task title', 'task content', 7, 3);
INSERT INTO tasks(user_id, title, content, begins, duration) VALUES (1, 'task title', 'task content', 13, 4);

UPDATE slots SET task_id = 1 WHERE column_id = 1 AND time = (SELECT begins FROM tasks WHERE id = 1)||':00';
UPDATE slots SET task_id = 2 WHERE column_id = 1 AND time = (SELECT begins FROM tasks WHERE id = 2)||':00';
UPDATE slots SET task_id = 3 WHERE column_id = 1 AND time = (SELECT begins FROM tasks WHERE id = 3)||':00';



END $$



