insert into users (id, username)
values (10, 'u1'),
       (11, 'u2');

insert into product (id, name, visible, is_commentable, is_ratable,
                     commentable_to_public, ratable_to_public)
values (20, 'p1', true, true, true, true, true),
       (21, 'p2', true, false, true, false, true),
       (22, 'p3', true, true, true, true, true),
       (23, 'p4', false, true, true, true, true);

insert into comment (id, content, date, status, user_id)
values (30, 'Good product', '2021-03-19 14:33:40', 'VERIFIED', 10),
       (31, 'Not bad', '2021-02-19 15:33:40', 'VERIFIED', 10),
       (32, 'Terrible', '2020-05-10 07:30:00', 'VERIFIED', 11),
       (33, 'not-readable', '2020-02-19 15:33:40', 'REJECTED', 10),
       (34, 'Hello', '2020-05-10 07:30:00', 'PENDING', 10);

insert into product_comments(id, comment_id)
values (20, 30),
       (20, 31),
       (21, 32),
       (22, 33),
       (22, 34);

