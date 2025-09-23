INSERT INTO public.t_reservation (id, fk_restaurant, fk_user, created_in, created_by, deleted, deleted_in, deleted_by, hash_id, updated_in, updated_by, booking_status, booking_time, booking_date, booking_quantity)
VALUES (nextval('SQ_RESERVATION'), 1, (SELECT id FROM public.t_user WHERE hash_id = 'ab15a4s1a5qa7af15a41s8a4sa15d1fa'), '2025-05-17 15:42:22.945', NULL, false, NULL, NULL, 'a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6', '2025-05-17 15:42:22.945', NULL, 'REQUESTED', 'BREAKFAST', NOW(), 2);

INSERT INTO public.t_reservation (id, fk_restaurant, fk_user, created_in, created_by, deleted, deleted_in, deleted_by, hash_id, updated_in, updated_by, booking_status, booking_time, booking_date, booking_quantity)
VALUES (nextval('SQ_RESERVATION'), 1, (SELECT id FROM public.t_user WHERE hash_id = 'd49690a919944be58fbe55b4f729bc3e'), '2025-05-17 15:42:22.945', NULL, false, NULL, NULL, 'b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7', '2025-05-17 15:42:22.945', NULL, 'REQUESTED', 'LUNCH', NOW(), 4);

INSERT INTO public.t_reservation (id, fk_restaurant, fk_user, created_in, created_by, deleted, deleted_in, deleted_by, hash_id, updated_in, updated_by, booking_status, booking_time, booking_date, booking_quantity)
VALUES (nextval('SQ_RESERVATION'), 1, (SELECT id FROM public.t_user WHERE hash_id = 'ed3a9d7639d84a20a57ecf20d27176da'), '2025-05-17 15:42:22.945', NULL, false, NULL, NULL, 'c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8', '2025-05-17 15:42:22.945', NULL, 'ACCEPTED', 'DINNER', NOW(), 3);

INSERT INTO public.t_reservation (id, fk_restaurant, fk_user, created_in, created_by, deleted, deleted_in, deleted_by, hash_id, updated_in, updated_by, booking_status, booking_time, booking_date, booking_quantity)
VALUES (nextval('SQ_RESERVATION'), 1, (SELECT id FROM public.t_user WHERE hash_id = 'eff4as15as6ae4as1s4d7df1a5s54fea'), '2025-05-17 15:42:22.945', NULL, false, NULL, NULL, 'd4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9', '2025-05-17 15:42:22.945', NULL, 'ACCEPTED', 'BREAKFAST', NOW(), 2);