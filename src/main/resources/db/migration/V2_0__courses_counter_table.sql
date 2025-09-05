create table if not exists courses_counter(
    id varchar(64) not null primary key,
    total int not null,
    existing_courses json not null,
    created_at TIMESTAMP not null
);
