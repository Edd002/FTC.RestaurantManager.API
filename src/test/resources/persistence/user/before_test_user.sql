INSERT INTO public.t_user
(id, created_in, created_by, deleted, deleted_in, deleted_by, hash_id, updated_in, updated_by, email, login, name, password, role, fk_address)
VALUES(nextval('SQ_USER'), '2025-05-17 15:42:22.945', NULL, false, NULL, NULL, 'ed3a9d7639d84a20a57ecf20d27176da', '2025-05-17 15:42:22.945', NULL, 'admin@email.com', 'admin', 'Admin', '7htg2CZCvaY=', 'OWNER', 1);
INSERT INTO public.t_user
(id, created_in, created_by, deleted, deleted_in, deleted_by, hash_id, updated_in, updated_by, email, login, name, password, role, fk_address)
VALUES(nextval('SQ_USER'), '2025-05-17 15:42:22.945', NULL, false, NULL, NULL, 'd49690a919944be58fbe55b4f729bc3e', '2025-05-17 15:42:22.945', NULL, 'client@email.com', 'client', 'Client', 'f4wA3Co8ApI=', 'CLIENT', 2);