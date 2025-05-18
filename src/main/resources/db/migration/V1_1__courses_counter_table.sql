create table if not exists courses_counter
(
    id               varchar(64) primary key,
    total            integer   not null,
    existing_courses jsonb     not null,
    created_at       TIMESTAMP not null
);
