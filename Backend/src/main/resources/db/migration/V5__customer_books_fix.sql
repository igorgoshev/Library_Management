alter table customer_book
    add column available boolean;

update customer_book
set available = false;

alter table customer_book
    add constraint available_not_null CHECK (available is not null);

alter table trade
    add column date_from date,
    add column date_to date;

create materialized view if not exists popular_authors as
select a.id, a.name, a.last_name, count(distinct bb.id)
from borrow_book bb
         join public.book_in_library bil on bb.book_in_library_id = bil.id
         join public.book b on bil.book_id = b.id
         join public.book_has on b.id = book_has.book_id
         join public.author a on a.id = book_has.author_id
group by a.id, a.name, a.last_name
order by count(distinct bb.id) desc;

create materialized view if not exists popular_categories as
select c.id, c.name, count(distinct bb.id)
from borrow_book bb
         join public.book_in_library bil on bb.book_in_library_id = bil.id
         join public.book b on bil.book_id = b.id
         join public.belongs on b.id = belongs.book_id
         join category c on belongs.category_id = c.id
group by c.id, c.name
order by count(distinct bb.id) desc;

create materialized view if not exists popular_stores as
select ls.id, ls.name, count(distinct bb.id)
from borrow_book bb
         join public.book_in_library bil on bb.book_in_library_id = bil.id
         join public.library_store ls on bil.library_store_id = ls.id
group by ls.id, ls.name
order by count(distinct bb.id) desc;

create materialized view if not exists loans_in_last_days as
select ls.id, ls.name, count(distinct bb.id) filter ( where bb.date_from = current_date ) day0,
       count(distinct bb.id) filter ( where bb.date_from = current_date - 1 ) day1,
       count(distinct bb.id) filter ( where bb.date_from = current_date - 2 ) day2,
       count(distinct bb.id) filter ( where bb.date_from = current_date - 3 ) day3,
       count(distinct bb.id) filter ( where bb.date_from = current_date - 4 ) day4
from borrow_book bb
         join public.book_in_library bil on bb.book_in_library_id = bil.id
         join public.library_store ls on bil.library_store_id = ls.id
group by ls.id, ls.name;

create materialized view if not exists reservations_in_last_days as
select ls.id,
       ls.name,
       count(distinct bb.id) filter ( where bb.date_from = current_date )     day0,
       count(distinct bb.id) filter ( where bb.date_from = current_date - 1 ) day1,
       count(distinct bb.id) filter ( where bb.date_from = current_date - 2 ) day2,
       count(distinct bb.id) filter ( where bb.date_from = current_date - 3 ) day3,
       count(distinct bb.id) filter ( where bb.date_from = current_date - 4 ) day4
from library_store ls
         left join public.book_in_library bil on bil.library_store_id = ls.id
         left join public.reserve_book bb on bil.id = bb.book_in_library_id
group by ls.id, ls.name;

create materialized view if not exists reservations_thorugh_months as
select ls.id,
       ls.name,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 1) january,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 2) february,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from)= 3) march,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 4) april,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 5) may,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 6) june,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 7) july,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 8) august,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 9) september,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 10) october,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 11) november,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 12) december
from library_store ls
         left join public.book_in_library bil on bil.library_store_id = ls.id
         left join public.borrow_book bb on bil.id = bb.book_in_library_id
group by ls.id, ls.name;

create materialized view if not exists borrows_thorugh_months as
select ls.id,
       ls.name,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 1) january,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 2) february,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from)= 3) march,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 4) april,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 5) may,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 6) june,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 7) july,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 8) august,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 9) september,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 10) october,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 11) november,
       count(distinct bb.id) filter ( where extract('month' from bb.date_from) = 12) december
from library_store ls
         left join public.book_in_library bil on bil.library_store_id = ls.id
         left join public.reserve_book bb on bil.id = bb.book_in_library_id
group by ls.id, ls.name;


