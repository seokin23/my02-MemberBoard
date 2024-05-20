drop table if exists member CASCADE;
create table member
(
    member_id bigint generated by default as identity,
    login_id varchar(10),
    password varchar(10),
    member_name varchar(10),
    age integer,
    gender_type varchar(10),
    region_type_code varchar(10),
    primary key (member_id)
);

drop table if exists board CASCADE;
create table board
(
    id bigint generated by default as identity,
    author varchar(10),
    title varchar(10),
    contents varchar(30),
    board_type_code varchar(10),
    primary key (id)
);