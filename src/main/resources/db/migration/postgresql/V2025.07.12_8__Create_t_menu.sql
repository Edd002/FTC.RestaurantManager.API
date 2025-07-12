CREATE TABLE public.t_menu
(
    id         int8         NOT NULL,
    created_by varchar(255) NULL,
    created_in timestamp(6) NOT NULL,
    deleted    bool         NOT NULL,
    deleted_by varchar(255) NULL,
    deleted_in timestamp(6) NULL,
    hash_id    varchar(255) NOT NULL,
    updated_by varchar(255) NULL,
    updated_in timestamp(6) NULL,
    primary key (id)
);

CREATE SEQUENCE public.sq_menu START WITH 1 INCREMENT BY 1;

CREATE UNIQUE INDEX T_MENU_HASH_ID_UK ON public.t_menu (hash_id);