insert into users (id, username)
values (10, 'u1'),
       (11, 'u2');

insert into product (id, name, visible, is_commentable, is_ratable,
                     commentable_to_public, ratable_to_public)
values (20, 'p1', true, true, true, true, true),
       (21, 'p2', true, false, true, false, true),
       (22, 'p3', true, true, true, true, true),
       (23, 'p4', false, true, true, true, true);

insert into comment (id, content, date, status, product_id, user_id)
values (30, 'Good product', '2021-03-19 14:33:40', 1, 20, 10),
       (31, 'Not bad', '2021-02-19 15:33:40', 1, 20, 10),
       (32, 'Terrible', '2020-05-10 07:30:00', 1, 20, 11),
       (33, 'not-readable', '2020-02-19 15:33:40', 2, 20, 10),
       (34, 'Hello', '2020-05-10 07:30:00', 0, 20, 10);

