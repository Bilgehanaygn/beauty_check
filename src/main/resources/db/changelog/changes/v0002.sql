create table "_image"(
    id bigint not null,
    name varchar(50) not null,
    point varchar(20),
    user_id bigint not null,
    status INTEGER,
    reviewer_id INTEGER,
    FOREIGN KEY(reviewer_id) REFERENCES _user (id) MATCH SIMPLE,
    FOREIGN KEY(user_id) REFERENCES _user (id) MATCH FULL,
    primary key (id)
);

CREATE SEQUENCE _image_seq
    AS BiGINT
    START WITH 1000
    INCREMENT BY 50
    NO CYCLE
    CACHE 10;

create table "_image_tags"(
    image_id bigint not null,
    tag varchar(20),
    FOREIGN KEY(image_id) REFERENCES _image(id) MATCH FULL
)