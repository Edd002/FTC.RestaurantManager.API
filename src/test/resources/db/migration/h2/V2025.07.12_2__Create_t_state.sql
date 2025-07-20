create table public.t_state
(
    id         int8         not null,
    created_in timestamp(6) not null,
    created_by varchar(255),
    deleted    boolean      not null,
    deleted_in timestamp(6),
    deleted_by varchar(255),
    hash_id    varchar(255) not null,
    updated_in timestamp(6),
    updated_by varchar(255),
    name       varchar(255) not null,
    uf         varchar(2)   not null,
    primary key (id)
);

create sequence public.sq_state start with 1 increment by 1;

ALTER TABLE public.t_state ADD NAME_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE name END);
ALTER TABLE public.t_state ADD UF_UK_FIELD VARCHAR(2) AS (CASE deleted WHEN TRUE THEN NULL ELSE uf END);
CREATE UNIQUE INDEX T_STATE__HASH_ID_UK ON public.t_state(hash_id);
CREATE UNIQUE INDEX T_STATE__NAME_UK ON public.t_state(NAME_UK_FIELD);
CREATE UNIQUE INDEX T_STATE__UF_UK ON public.t_state(UF_UK_FIELD);