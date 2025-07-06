CREATE TABLE public.t_menu_item (
    id int8 NOT NULL,
    created_by varchar(255) NULL,
    created_in timestamp(6) NOT NULL,
    deleted bool NOT NULL,
    deleted_by varchar(255) NULL,
    deleted_in timestamp(6) NULL,
    hash_id varchar(255) NOT NULL,
    updated_by varchar(255) NULL,
    updated_in timestamp(6) NULL,
    availability bool NOT NULL,
    description varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    photo_url varchar(255) NOT NULL,
    price numeric(7, 2) NOT NULL,
    fk_menu int8 NOT NULL,
    primary key (id)
);

CREATE SEQUENCE public.sq_menu_item START WITH 1 INCREMENT BY 1;

ALTER TABLE public.t_menu_item ADD NAME_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE name END);
ALTER TABLE public.t_menu_item ADD FK_MENU_UK_FIELD INT8 AS (CASE deleted WHEN TRUE THEN NULL ELSE fk_menu END);
CREATE UNIQUE INDEX T_MENU_ITEM_HASH_ID_UK ON public.t_menu_item (hash_id);
CREATE UNIQUE INDEX T_MENU_ITEM_NAME_AND_FK_MENU_UK ON public.t_menu_item (NAME_UK_FIELD, FK_MENU_UK_FIELD);