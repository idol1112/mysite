
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

--users ���̺� ������ ����
insert into users
values (seq_user_no.nextval, 'idol1112', '1234', '������', 'male');

select *
from users;

rollback;