
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

--users 테이블 데이터 삽입
insert into users
values (seq_user_no.nextval, 'idol1112', '1234', '김윤형', 'male');

select *
from users;

rollback;