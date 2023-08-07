create table "_user"(
    id bigint not null,
    phone_num varchar(20) not null UNIQUE,
    name varchar(20),
    age INTEGER,
    otp varchar(255),
    otp_requested_time date,
    role varchar(20),
    primary key (id)
);

CREATE SEQUENCE _user_seq
    AS BiGINT
    START WITH 1000
    INCREMENT BY 50
    NO CYCLE
    CACHE 10;

create table "_token"(
  id bigint NOT NULL,
  token VARCHAR(255),
  revoked BOOLEAN,
  expired BOOLEAN,
  user_id bigint NOT NULL,
  FOREIGN KEY(user_id) REFERENCES _user (id),
  primary key (id)
);

CREATE SEQUENCE _token_seq
    AS BiGINT
    START WITH 1000
    INCREMENT BY 50
    NO CYCLE
    CACHE 10;