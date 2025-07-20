create table public.t_load_table
(
    id                  int8         not null,
    created_in          timestamp(6) not null,
    created_by          varchar(255),
    deleted             boolean      not null,
    deleted_in          timestamp(6),
    deleted_by          varchar(255),
    hash_id             varchar(255) not null,
    updated_in          timestamp(6),
    updated_by          varchar(255),
    entity_load_enabled boolean      not null,
    entity_name         varchar(255) not null,
    primary key (id)
);

create sequence public.sq_load_table start with 1 increment by 1;

ALTER TABLE public.t_load_table ADD ENTITY_NAME_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE entity_name END);
CREATE UNIQUE INDEX T_LOAD_TABLE__HASH_ID_UK ON public.t_load_table(hash_id);
CREATE UNIQUE INDEX T_LOAD_TABLE__ENTITY_NAME_UK ON public.t_load_table(ENTITY_NAME_UK_FIELD);