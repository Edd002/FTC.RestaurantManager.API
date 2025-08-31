CREATE TABLE public.t_menu_item_order
(
    id            int8         NOT NULL,
    created_by    varchar(255) NULL,
    created_in    timestamp(6) NOT NULL,
    deleted       bool         NOT NULL,
    deleted_by    varchar(255) NULL,
    deleted_in    timestamp(6) NULL,
    hash_id       varchar(255) NOT NULL,
    updated_by    varchar(255) NULL,
    updated_in    timestamp(6) NULL,
    fk_menu_item  int8         NOT NULL,
    fk_order      int8         NOT NULL,
    primary key (id)
);

CREATE SEQUENCE public.sq_menu_item_order START WITH 1 INCREMENT BY 1;

ALTER TABLE public.t_menu_item_order ADD CONSTRAINT t_menu_item_order__fk_menu_item FOREIGN KEY (fk_menu_item) REFERENCES public.t_menu_item (id);
ALTER TABLE public.t_menu_item_order ADD CONSTRAINT t_menu_item_order__fk_order FOREIGN KEY (fk_order) REFERENCES public.t_order (id);

CREATE UNIQUE INDEX T_MENU_ITEM_ORDER__HASH_ID_UK ON public.t_menu_item_order (hash_id);