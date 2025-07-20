create table public.t_user
(
    id           int8         not null,
    created_in   timestamp(6) not null,
    created_by   varchar(255),
    deleted      boolean      not null,
    deleted_in   timestamp(6),
    deleted_by   varchar(255),
    hash_id      varchar(255) not null,
    updated_in   timestamp(6),
    updated_by   varchar(255),
    email        varchar(255) not null,
    login        varchar(255) not null,
    name         varchar(255) not null,
    password     varchar(255) not null,
    fk_user_type int8         not null,
    fk_address   int8         not null,
    primary key (id)
);

create sequence public.sq_user start with 1 increment by 1;

alter table if exists public.t_user add constraint t_user__fk_user_type foreign key (fk_user_type) references t_user_type;
alter table if exists public.t_user add constraint t_user__fk_address foreign key (fk_address) references t_address;

ALTER TABLE public.t_user ADD EMAIL_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE email END);
ALTER TABLE public.t_user ADD LOGIN_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE login END);
ALTER TABLE public.t_user ADD FK_ADDRESS_UK_FIELD INT8 AS (CASE deleted WHEN TRUE THEN NULL ELSE fk_address END);

CREATE UNIQUE INDEX T_USER__HASH_ID_UK ON public.t_user (hash_id);
CREATE UNIQUE INDEX T_USER__EMAIL_UK ON public.t_user (EMAIL_UK_FIELD);
CREATE UNIQUE INDEX T_USER__LOGIN_UK ON public.t_user (LOGIN_UK_FIELD);
CREATE UNIQUE INDEX T_USER__FK_ADDRESS_UK ON public.t_user (FK_ADDRESS_UK_FIELD);