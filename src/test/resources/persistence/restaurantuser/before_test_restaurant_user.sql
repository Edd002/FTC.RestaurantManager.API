INSERT INTO public.t_restaurant_user (id, fk_restaurant, fk_user, created_in, created_by, deleted, deleted_in, deleted_by, hash_id, updated_in, updated_by)
VALUES (nextval('SQ_RESTAURANT_USER'), (SELECT MAX(id) FROM public.t_restaurant), (SELECT id FROM public.t_user WHERE hash_id = 'ab15a4s1a5qa7af15a41s8a4sa15d1fa'), '2025-05-17 15:42:22.945', NULL, false, NULL, NULL, '8d6ab84ca2af9fccd4e4048694176ebf', '2025-05-17 15:42:22.945', NULL);

INSERT INTO public.t_restaurant_user (id, fk_restaurant, fk_user, created_in, created_by, deleted, deleted_in, deleted_by, hash_id, updated_in, updated_by)
VALUES (nextval('SQ_RESTAURANT_USER'), (SELECT MAX(id) FROM public.t_restaurant), (SELECT id FROM public.t_user WHERE hash_id = 'd49690a919944be58fbe55b4f729bc3e'), '2025-05-17 15:42:22.945', NULL, false, NULL, NULL, 'bd7e15fcd3f1083ccd3e6e447e4d956e', '2025-05-17 15:42:22.945', NULL);

INSERT INTO public.t_restaurant_user (id, fk_restaurant, fk_user, created_in, created_by, deleted, deleted_in, deleted_by, hash_id, updated_in, updated_by)
VALUES (nextval('SQ_RESTAURANT_USER'), (SELECT MAX(id) FROM public.t_restaurant), (SELECT id FROM public.t_user WHERE hash_id = 'ed3a9d7639d84a20a57ecf20d27176da'), '2025-05-17 15:42:22.945', NULL, false, NULL, NULL, '202d227187ff7a6d2b2e3371a81fa633', '2025-05-17 15:42:22.945', NULL);