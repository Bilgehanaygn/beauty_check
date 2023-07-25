create table "_product"(
    id bigserial not null,
    name varchar(50) not null,
    primary key (id)
);

create table "_label"(
    id bigserial not null,
    description varchar(20),
    primary key (id)
);

create table "product_attach_label"(
    product_id bigserial not null,
    label_id bigserial not null
);

create table "_user"(
    id bigserial not null,
    phone_num varchar(20) not null,
    otp varchar(255),
    otp_requested_time date,
    primary key (id)
);

CREATE SEQUENCE _user_seq
    AS BiGINT
    START WITH 1000
    INCREMENT BY 50
    NO CYCLE
    CACHE 10;

create table "_token"(
  id BIGSERIAL NOT NULL,
  token VARCHAR(255),
  revoked BOOLEAN,
  expired BOOLEAN,
  user_id BIGSERIAL NOT NULL,
  FOREIGN KEY(user_id) REFERENCES _user (id),
  primary key (id)
);

CREATE SEQUENCE _token_seq
    AS BiGINT
    START WITH 1000
    INCREMENT BY 50
    NO CYCLE
    CACHE 10;