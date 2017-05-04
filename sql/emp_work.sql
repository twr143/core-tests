-- select * from emp order by hiredate DESC;
-- select ename,job,deptno from emp order by deptno ASC
-- select ename,job,hiredate,sal,max(sal) over (ORDER BY hiredate) as m_sal from emp order by hiredate ASC
-- select * from (select ename,job,hiredate,sal,row_number() over (partition by job ORDER BY sal desc) as r_num from emp) where r_num <4 order by job ASC
-- select * from (select ename,job,hiredate,sal,rank() over (partition by job order by sal) as rank from emp) where rank <4 order by job ASC
-- SELECT ename,t.title,listagg(ename,',') WITHIN GROUP (ORDER BY ename) OVER (PARTITION BY job) as "same title" from emp e join title t on e.job=t.title_id order by job


-- select DISTINCT deptno,count(DISTINCT ename) over (PARTITION BY deptno) as dep_size from emp order by deptno ASC;
select deptno,count(DISTINCT ename) as dep_size from emp GROUP BY deptno order by deptno ASC;
