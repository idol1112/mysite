-------------------guestbook ���̺� �ʱ�ȭ------------------------
--���̺� ����
drop table guestbook;

--������ ����
drop sequence seq_no;

--���̺� ����
create table guestbook(
    no       NUMBER,
    name     VARCHAR2(20)   UNIQUE not null,
    password VARCHAR2(20)   not null,
    content  VARCHAR2(2000),
    reg_date date,
    PRIMARY KEY (no)
);

--������ ����
create sequence seq_no
increment by 1
start with 1
nocache;

--Insert ��
insert into guestbook
values (seq_no.nextval, '������', '1234', 'ù��° ����', sysdate);

select *
from guestbook;

rollback;

-----------------------users ���̺� �ʱ�ȭ---------------------------
--���̺� ����
drop table users;

--������ ����
drop sequence seq_user_no;

--���̺� ����
create table users(
    no       NUMBER,
    id       VARCHAR2(20)   UNIQUE not null,
    password VARCHAR2(20)   not null,
    name     VARCHAR2(20),
    gender   VARCHAR2(10),
    PRIMARY KEY (no)
);

--������ ����
create sequence seq_user_no
increment by 1
start with 1
nocache;

--Insert ��
insert into users
values (seq_user_no.nextval, 'idol1112', '1234', '������', 'male');

select *
from users;

rollback;

-----------------------board ���̺� �ʱ�ȭ---------------------------
--���̺� ����
drop table board;

--������ ����
drop sequence seq_user_no;

--���̺� ����
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

--������ ����
CREATE SEQUENCE seq_board_no
INCREMENT BY 1
START WITH 1
NOCACHE ;

--Insert ��
INSERT INTO board
values(seq_board_no.nextval,
       'ù �Խù��Դϴ�.',
       'ó�� �ø��� �Խù��Դϴ�.',
       0,
       sysdate,
       1
       );
INSERT INTO board
values(seq_board_no.nextval,
       '�ߺ�Ź�����',
       '�ȳ��ϼ��� �ߺ�Ź�帳�ϴ٤�',
       0,
       sysdate,
       2
       );
       
--Update ��
update board
set    title = 'ù ���� �Խù��Դϴ�',
       content = 'ó�� �ø��� ���� �Խù��Դϴ�'
where  no = 1; 

--hit �ø���
UPDATE board
set hit = hit + 1
where no = 1;

--board ����Ʈ
select b.no as bNo,
       b.title as bTitle,
       u.name as uName,
       b.user_no as userNo,
       b.hit as bHit,
       to_char(b.reg_date,'yy-mm-dd hh24:mi') as  bDate
from  users u ,  board b
where u.no = b.user_no
order by b.no desc;

--board �б�
select b.title as title,
       b.content as content,
       b.hit as hit,
       b.user_no no,
       b.reg_date as reg_date,
       u.name as name
from board b,users u
where u.no = b.user_no
and b.no=1;

--board ������
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

--board ����
DELETE board
where no = 1;

select *
from users;
commit;
rollback;