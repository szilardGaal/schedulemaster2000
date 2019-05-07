/*
    Database initialization script that runs on every web-application redeployment.
*/

DROP TABLE IF EXISTS slots CASCADE;
DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS tasks_schedules CASCADE;
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
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT title_not_empty CHECK ( title <> '' )
);

CREATE TABLE tasks_schedules(
    task_id int,
    schedule_id int,
    column_id int,
    begins int,
    duration int,
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (schedule_id) REFERENCES schedules(id),
    FOREIGN KEY (column_id) REFERENCES schedule_columns(id),
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

CREATE OR REPLACE FUNCTION create_columns_on_schedule() RETURNS TRIGGER AS '
    DECLARE
        count int := 0;
    BEGIN
        LOOP
            INSERT INTO schedule_columns (schedule_id, title) VALUES (NEW.id, ''Day ''||count+1);
            count := count + 1;
            EXIT WHEN count = NEW.numOfCol;
        end loop;
        RETURN NEW;
    END; '
    LANGUAGE plpgsql;


CREATE TRIGGER columns_on_schedule
    AFTER INSERT
    ON schedules
    FOR EACH ROW
EXECUTE PROCEDURE create_columns_on_schedule();



/*Update on slots only necessary once, called on the beginning date of the task added to the schedule, the following
  slots will be added automatically according to the previously defined duration*/


CREATE OR REPLACE FUNCTION add_tasks_to_slots() RETURNS TRIGGER AS '
BEGIN
    IF NEW.time != ((SELECT begins FROM tasks_schedules WHERE task_id=NEW.task_id)+(SELECT duration FROM tasks_schedules WHERE task_id=NEW.task_id)-1)||'':00'' THEN
        INSERT INTO slots (task_id, column_id, time) VALUES (NEW.task_id, NEW.column_id, ((split_part(NEW.time, '':'', 1))::int)+1||'':00'');
    end if;
    RETURN NEW;
END; '
    LANGUAGE plpgsql;

CREATE TRIGGER task_on_slots
    AFTER UPDATE
    ON slots
    FOR EACH ROW
EXECUTE PROCEDURE  add_tasks_to_slots();

CREATE TRIGGER task_on_slots_insert
    AFTER INSERT
    ON slots
    FOR EACH ROW
EXECUTE PROCEDURE add_tasks_to_slots();


/*When task added to a schedule, execute insert on tasks_schedules, this trigger automatically insert it to slots*/

CREATE OR REPLACE FUNCTION create_slots_when_task_added() RETURNS TRIGGER AS '
BEGIN
    INSERT INTO slots (column_id, task_id, time) VALUES (NEW.column_id, NEW.task_id, NEW.begins||'':00'');
    RETURN NEW;
END; '
    LANGUAGE plpgsql;

CREATE TRIGGER create_first_slot
    AFTER INSERT
    ON tasks_schedules
    FOR EACH ROW
EXECUTE PROCEDURE create_slots_when_task_added();



INSERT INTO users(name, password) VALUES ('test', 'test');

INSERT INTO schedules(user_id, title, numOfCol) VALUES (1, 'title', '2');
INSERT INTO schedules(user_id, title, numOfCol) VALUES (1, 'title', '3');

INSERT INTO tasks(user_id, title, content) VALUES (1, 'task title', 'task content');
INSERT INTO tasks(user_id, title, content) VALUES (1, 'task title', 'task content');
INSERT INTO tasks(user_id, title, content) VALUES (1, 'task title', 'task content');

INSERT INTO tasks_schedules(task_id, schedule_id, column_id, begins, duration) VALUES (1, 1, 1, 11, 3);
INSERT INTO tasks_schedules(task_id, schedule_id, column_id, begins, duration) VALUES (2, 1, 1, 17, 3);

/*ADD before trigger to check if tasks already exists in column!*/








