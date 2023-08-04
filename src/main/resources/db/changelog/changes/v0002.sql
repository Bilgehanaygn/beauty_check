create table "_image"(
    id bigserial not null,
    name varchar(50) not null,
    description varchar(20),
    point INTEGER,
    user_id bigserial not null,
    FOREIGN KEY(user_id) REFERENCES _user (id),
    primary key (id)
);

CREATE SEQUENCE _image_seq
    AS BiGINT
    START WITH 1000
    INCREMENT BY 50
    NO CYCLE
    CACHE 10;