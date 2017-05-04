drop table emp;
drop table dept;
drop table title;
create table dept(
  deptno number(2,0),
  dname  varchar2(14),
  loc    varchar2(13),
  constraint pk_dept primary key (deptno)
);
create table title(
  title_id number(2,0),
  title  varchar2(14),
  constraint pk_title primary key (title_id)
);


create table emp(
  empno    number(4,0),
  ename    varchar2(10) not NULL,
  job      number(2,0)  not NULL,
  mgr      number(4,0),
  hiredate date,
  sal      number(7,2)  not NULL,
  comm     number(7,2),
  deptno   number(2,0)  not NULL,
  constraint pk_emp primary key (empno),
  constraint fk_deptno foreign key (deptno) references dept (deptno),
  constraint fk_title foreign key (job) references title (title_id)
);

/*
create table bonus(
  ename varchar2(10),
  job   varchar2(9),
  sal   number,
  comm  number
);

create table salgrade(
  grade number,
  losal number,
  hisal number
);
*/

insert into dept
values(10, 'ACCOUNTING', 'NEW YORK');
insert into dept
values(20, 'RESEARCH', 'DALLAS');
insert into dept
values(30, 'SALES', 'CHICAGO');
insert into dept
values(40, 'OPERATIONS', 'BOSTON');

insert into title
values(10, 'CLERK');
insert into title
values(20, 'SALESMAN');
insert into title
values(30, 'ANALYST');
insert into title
values(40, 'MANAGER');
insert into title
values(50, 'PRESIDENT');

insert into emp
values(
 7839, 'KING', 50, null,
 to_date('17-11-1981','dd-mm-yyyy'),
 5000, null, 10
);
insert into emp
values(
 7698, 'BLAKE', 40, 7839,
 to_date('1-5-1981','dd-mm-yyyy'),
 2850, null, 30
);
insert into emp
values(
 7782, 'CLARK', 40, 7839,
 to_date('9-6-1981','dd-mm-yyyy'),
 2450, null, 10
);
insert into emp
values(
 7566, 'JONES', 40, 7839,
 to_date('2-4-1981','dd-mm-yyyy'),
 2975, null, 20
);
insert into emp
values(
 7788, 'SCOTT', 30, 7566,
 to_date('13-07-87','dd-mm-yy') - 85,
 3000, null, 20
);
insert into emp
values(
 7902, 'FORD', 30, 7566,
 to_date('3-12-1981','dd-mm-yyyy'),
 3000, null, 20
);
insert into emp
values(
 7369, 'SMITH', 10, 7902,
 to_date('17-12-1980','dd-mm-yyyy'),
 800, null, 20
);
insert into emp
values(
 7499, 'ALLEN', 20, 7698,
 to_date('20-2-1981','dd-mm-yyyy'),
 1600, 300, 30
);
insert into emp
values(
 7521, 'WARD', 20, 7698,
 to_date('22-2-1981','dd-mm-yyyy'),
 1250, 500, 30
);
insert into emp
values(
 7654, 'MARTIN', 20, 7698,
 to_date('28-9-1981','dd-mm-yyyy'),
 1250, 1400, 30
);
insert into emp
values(
 7844, 'TURNER', 20, 7698,
 to_date('8-9-1981','dd-mm-yyyy'),
 1500, 0, 30
);
insert into emp
values(
 7876, 'ADAMS', 10, 7788,
 to_date('13-07-87', 'dd-mm-yy') - 51,
 1100, null, 20
);
insert into emp
values(
 7900, 'JAMES', 10, 7698,
 to_date('3-12-1981','dd-mm-yyyy'),
 950, null, 30
);
insert into emp
values(
 7934, 'MILLER', 10, 7782,
 to_date('23-1-1982','dd-mm-yyyy'),
 1300, null, 10
);
insert into emp
values(
 7836, 'VOLY', 30, 7788,
 to_date('13-07-2017', 'dd-mm-yyyy') - 51,
 2700, null, 20
);

/*
insert into salgrade
values (1, 700, 1200);
insert into salgrade
values (2, 1201, 1400);
insert into salgrade
values (3, 1401, 2000);
insert into salgrade
values (4, 2001, 3000);
insert into salgrade
values (5, 3001, 9999);
*/

commit;
