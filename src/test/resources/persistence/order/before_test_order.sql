INSERT INTO public.t_order (id, created_by, created_in, deleted, deleted_by, deleted_in, hash_id, updated_by, updated_in, status, type, fk_restaurant, fk_user)
VALUES(nextval('SQ_ORDER'), 'owner', '2025-09-14 14:58:09.944', false, NULL, NULL, '5c679ea52a6743b5b08b43cbb43ae9a7', 'owner', '2025-09-14 16:03:18.717', 'CONFIRMED', 'DELIVERY', 1, 3);
INSERT INTO public.t_order (id, created_by, created_in, deleted, deleted_by, deleted_in, hash_id, updated_by, updated_in, status, type, fk_restaurant, fk_user)
VALUES(nextval('SQ_ORDER'), 'client', '2025-09-21 18:00:32.434', false, NULL, NULL, '150def6c26ae40c9ba776fef8eea6382', 'client', '2025-09-21 18:00:32.434', 'REQUESTED', 'DELIVERY', 1, 3);
INSERT INTO public.t_order (id, created_by, created_in, deleted, deleted_by, deleted_in, hash_id, updated_by, updated_in, status, type, fk_restaurant, fk_user)
VALUES(nextval('SQ_ORDER'), 'client', '2025-09-21 18:00:32.434', false, NULL, NULL, 'c3db1768ee29420cb4eb58e8324943ce', 'client', '2025-09-21 18:00:32.434', 'DELIVERED', 'DELIVERY', 1, 3);
INSERT INTO public.t_order (id, created_by, created_in, deleted, deleted_by, deleted_in, hash_id, updated_by, updated_in, status, type, fk_restaurant, fk_user)
VALUES(nextval('SQ_ORDER'), 'client', '2025-09-21 18:00:32.434', false, NULL, NULL, 'd5d306b4a027436fa31f01dd493c52ad', 'client', '2025-09-21 18:00:32.434', 'CANCELED', 'DELIVERY', 1, 3);
INSERT INTO public.t_order (id, created_by, created_in, deleted, deleted_by, deleted_in, hash_id, updated_by, updated_in, status, type, fk_restaurant, fk_user)
VALUES(nextval('SQ_ORDER'), 'client', '2025-09-21 18:00:32.434', false, NULL, NULL, '900afe639d574545a3c2d1f30388e7a6', 'client', '2025-09-21 18:00:32.434', 'ON_DELIVERY_ROUTE', 'DELIVERY', 1, 3);