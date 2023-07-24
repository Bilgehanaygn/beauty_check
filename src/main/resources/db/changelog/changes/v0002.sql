create table "_token"(
  id BIGSERIAL NOT NULL,
  token VARCHAR(255),
  revoked BIT,
  expired BIT,
  user_id BIGSERIAL NOT NULL,
  FOREIGN KEY(user_id) REFERENCES _user (id),
  primary key (id)
);

CREATE SEQUENCE _user_seq
    AS BiGINT
    START WITH 1000
    INCREMENT BY 50
    NO CYCLE
    CACHE 10;