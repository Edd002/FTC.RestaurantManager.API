create table t_city
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
    fk_state   int8         not null,
    primary key (id)
);

create sequence public.sq_city start with 1 increment by 1;

alter table if exists t_city add constraint t_city_fk_state foreign key (fk_state) references t_state;

ALTER TABLE public.t_city ADD FK_STATE_UK_FIELD INT8 AS (CASE deleted WHEN TRUE THEN NULL ELSE fk_state END);
ALTER TABLE public.t_city ADD NAME_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE name END);
CREATE UNIQUE INDEX T_CITY_HASH_ID_UK ON public.t_city (hash_id);
CREATE UNIQUE INDEX T_CITY_FK_STATE_AND_NAME_UK ON public.t_city (FK_STATE_UK_FIELD, NAME_UK_FIELD);