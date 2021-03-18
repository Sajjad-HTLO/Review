INSERT INTO users (id, username)
VALUES (10, 'u1'),
       (11, 'u2');

INSERT INTO product (id, name, is_visible, is_commentable, is_votable,
                     commentable_to_public, votable_to_public)
VALUES (20, 'p1', true, true, true, true, true),
       (21, 'p2', true, false, true, false, true),
       (22, 'p3', false, true, true, true, true),
       (23, 'p4', false, true, true, true, true);