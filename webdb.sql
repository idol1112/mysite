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