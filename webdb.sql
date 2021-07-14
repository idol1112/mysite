-------------------guestbook 테이블 초기화------------------------
--테이블 삭제
drop table guestbook;

--시퀀스 삭제
drop sequence seq_no;

--테이블 생성
create table guestbook(
    no       NUMBER,
    name     VARCHAR2(20)   UNIQUE not null,
    password VARCHAR2(20)   not null,
    content  VARCHAR2(2000),
    reg_date date,
    PRIMARY KEY (no)
);

--시퀀스 생성
create sequence seq_no
increment by 1
start with 1
nocache;

--Insert 문
insert into guestbook
values (seq_no.nextval, '김윤형', '1234', '첫번째 방명록', sysdate);

select *
from guestbook;

rollback;

-----------------------users 테이블 초기화---------------------------
--테이블 삭제
drop table users;

--시퀀스 삭제
drop sequence seq_user_no;

--테이블 생성
create table users(
    no       NUMBER,
    id       VARCHAR2(20)   UNIQUE not null,
    password VARCHAR2(20)   not null,
    name     VARCHAR2(20),
    gender   VARCHAR2(10),
    PRIMARY KEY (no)
);

--시퀀스 생성
create sequence seq_user_no
increment by 1
start with 1
nocache;

--Insert 문
insert into users
values (seq_user_no.nextval, 'idol1112', '1234', '김윤형', 'male');

select *
from users;

rollback;