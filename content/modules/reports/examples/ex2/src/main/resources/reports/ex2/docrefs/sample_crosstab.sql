-- tag::master-data-dataset[]
select name as name, id as department_id
from JMXRPR_LIBRARY_DEPARTMENT
where id in (${selected_departments})
-- end::master-data-dataset[]

-- tag::bi-dataset[]
select bi.library_department_id as bi_master_data@department_id,
       month(bi.created_date) as bi_dynamic_header@header_id,
       sum(bi.book_count) as "amount"
from JMXRPR_BOOK_INSTANCE bi
where bi.created_date >= ${start_date} and bi.created_date<= ${end_date}
and bi.library_department_id in (${bi_master_data@department_id})
and month(bi.created_date) in (${bi_dynamic_header@header_id})
group by bi.library_department_id,month(bi.created_date)
order by bi.library_department_id,month(bi.created_date)
-- end::bi-dataset[]
