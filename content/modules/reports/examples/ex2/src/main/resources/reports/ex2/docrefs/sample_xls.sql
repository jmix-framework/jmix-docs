-- tag::book-dataset[]
select b.name as book_name, b.id as book_id
from JMXRPR_BOOK b
    join JMXRPR_AUTHORS_BOOKS ba on ba.book_id = b.id
    join JMXRPR_AUTHOR a on a.id = ba.author_id
where a.id = ${author}
-- end::book-dataset[]

-- tag::publisher-dataset[]
select p.name as publisher, bp.year_, p.id as publisher_id
from JMXRPR_BOOK_PUBLICATION bp
    join JMXRPR_PUBLISHER p on p.id = bp.publisher_id
where bp.book_id = ${book.book_id}
-- end::publisher-dataset[]

-- tag::publication-dataset[]
select ld.name as department, sum(bi.book_count) as amount
from JMXRPR_BOOK_INSTANCE bi
    join JMXRPR_BOOK_PUBLICATION bp on bp.id = bi.book_publication_id
    join JMXRPR_LIBRARY_DEPARTMENT ld on ld.id = bi.library_department_id
where bp.publisher_id = ${publisher.publisher_id} and bp.book_id = ${book.book_id}
group by ld.name
-- end::publication-dataset[]
