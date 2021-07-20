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

-----------------------board 테이블 초기화---------------------------
--테이블 삭제
drop table board;

--시퀀스 삭제
drop sequence seq_user_no;

--테이블 생성
CREATE TABLE board(
    no number,
    title varchar2(500) not null,
    content varchar2(4000),
    hit number,
    reg_date date not null,
    user_no number not null,
    primary key(no),
    constraint c_board_fk foreign key (user_no)
    references users(no)
);

--시퀀스 생성
CREATE SEQUENCE seq_board_no
INCREMENT BY 1
START WITH 1
NOCACHE ;

--Insert 문
INSERT INTO board
values(seq_board_no.nextval,
       '첫 게시물입니다.',
       '처음 올리는 게시물입니다.',
       0,
       sysdate,
       1
       );
INSERT INTO board
values(seq_board_no.nextval,
       '잘부탁드려요',
       '안녕하세요 잘부탁드립니다ㅎ',
       0,
       sysdate,
       2
       );
       
--Update 문
update board
set    title = '첫 수정 게시물입니다',
       content = '처음 올리는 수정 게시물입니다'
where  no = 1; 

--hit 올리기
UPDATE board
set hit = hit + 1
where no = 1;

--board 리스트
select b.no as bNo,
       b.title as bTitle,
       u.name as uName,
       b.user_no as userNo,
       b.hit as bHit,
       to_char(b.reg_date,'yy-mm-dd hh24:mi') as  bDate
from  users u ,  board b
where u.no = b.user_no
order by b.no desc;

--board 읽기
select b.title as title,
       b.content as content,
       b.hit as hit,
       b.user_no no,
       b.reg_date as reg_date,
       u.name as name
from board b,users u
where u.no = b.user_no
and b.no=1;

--board 수정폼
select u.name as name,
       b.hit as hit,
       b.reg_date as reg_date,
       b.title as title,
       b.no as boardNo,
       b.content as content
from   board b , users u
where b.user_no = u.no
and u.no = 1
and b.no = 1;

--board 삭제
DELETE board
where no = 1;

select *
from users;
commit;
rollback;