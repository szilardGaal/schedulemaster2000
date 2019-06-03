DROP TABLE IF EXISTS slots CASCADE;
DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS schedule_columns CASCADE;
DROP TABLE IF EXISTS schedules CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    password text NOT NULL,
    isAdmin boolean DEFAULT false,
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

CREATE TABLE slots(
    column_id int,
    task_id int DEFAULT null,
    time varchar(5),
    FOREIGN KEY (column_id) REFERENCES schedule_columns(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE ON UPDATE CASCADE
);

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

CREATE OR REPLACE FUNCTION create_slots_when_task_added() RETURNS TRIGGER AS '
BEGIN
    INSERT INTO slots (column_id, task_id, time) VALUES (NEW.column_id, NEW.task_id, NEW.begins||'':00'');
    RETURN NEW;
END; '
    LANGUAGE plpgsql;

INSERT INTO users(name, password) VALUES ('test', '1000:7cf3de71fad3e947c667e7a44764cd8b:94659ae984bb90e8179245a5e9cd92a2b3625572d53ae8d9e98127e152ab26892e6b5921cf87248c6f7e775c6279b5578c7b6e197d2f9e17fe28bab722b5a9a3');
INSERT INTO users(name, password) VALUES ('test2', '1000:73e81d9c6e4ec625975f0cf24a5155c4:84a75cd7084cc1deb644626030f541036e0e992bd2a7c6eb513c20c7f1fa3669b1e23ae426ada699bc7a9d1a6ee5f505c9b09940b0a693209c1c59d64d615339');
INSERT INTO users(name, password, isAdmin) VALUES ('admin', '1000:ef57da2a955e1c60a25e256a2eba5c9f:8a63c1cfdc980394b167952d24389d579a4420071c903a42b12133b9b3a19d6d27eaa74d26292625fc8017b20892e1595e9d10426067818d3b1a0d3a59146791', true);

INSERT INTO schedules(user_id, title, numOfCol, isPublic) VALUES (1, 'chores', '2', true);
INSERT INTO schedules(user_id, title, numOfCol) VALUES (1, 'training', '3');
INSERT INTO schedules(user_id, title, numOfCol) VALUES (2, 'office', 5);
INSERT INTO schedules(user_id, title, numOfCol, isPublic) VALUES (2, 'Jehova''s harassment', 6, true);
INSERT INTO schedules(user_id, title, numOfCol, isPublic) VALUES (3, 'DOOM-plan', 7, true);

INSERT INTO tasks(user_id, title, content) VALUES (1, 'flex', 'looking good');
INSERT INTO tasks(user_id, title, content) VALUES (1, 'running', 'like Forest');
INSERT INTO tasks(user_id, title, content) VALUES (1, 'do the dishes', '''cause they''r dirty');
INSERT INTO tasks(user_id, title, content) VALUES (1, 'hoovering', 'the ceiling too!');
INSERT INTO tasks(user_id, title, content) VALUES (1, 'watch TV', 'SpongeBob on full volume');
INSERT INTO tasks(user_id, title, content) VALUES (2, 'phase #1', 'drink coffee');
INSERT INTO tasks(user_id, title, content) VALUES (2, 'working hard', 'actually browsing reddit');
INSERT INTO tasks(user_id, title, content) VALUES (2, 'house #1', 'do you have...');
INSERT INTO tasks(user_id, title, content) VALUES (2, 'house #2', '...minute for...');
INSERT INTO tasks(user_id, title, content) VALUES (2, 'house #3', '...our Lord and Saviour SpongeBob?');
INSERT INTO tasks(user_id, title, content) VALUES (3, 'ni!', 'NI!');
INSERT INTO tasks(user_id, title, content) VALUEs (3, 'thinking about your favourite color', 'either blue or yellow');
