create table t_address
(
    id           bigint       not null,
    created_in   timestamp(6) not null,
    created_by   varchar(255),
    deleted      boolean      not null,
    deleted_in   timestamp(6),
    deleted_by   varchar(255),
    hash_id      varchar(255) not null,
    updated_in   timestamp(6),
    updated_by   varchar(255),
    cep          varchar(255) not null,
    complement   varchar(255),
    description  varchar(255) not null,
    neighborhood varchar(255) not null,
    number       varchar(255) not null,
    postal_code  varchar(255) not null,
    fk_city      bigint       not null,
    primary key (id)
);
create table t_city
(
    id         bigint       not null,
    created_in timestamp(6) not null,
    created_by varchar(255),
    deleted    boolean      not null,
    deleted_in timestamp(6),
    deleted_by varchar(255),
    hash_id    varchar(255) not null,
    updated_in timestamp(6),
    updated_by varchar(255),
    name       varchar(255) not null,
    fk_state   bigint       not null,
    primary key (id)
);
create table t_jwt
(
    id           bigint       not null,
    created_in   timestamp(6) not null,
    created_by   varchar(255),
    deleted      boolean      not null,
    deleted_in   timestamp(6),
    deleted_by   varchar(255),
    hash_id      varchar(255) not null,
    updated_in   timestamp(6),
    updated_by   varchar(255),
    bearer_token text not null,
    fk_user      bigint       not null,
    primary key (id)
);
create table t_load_table
(
    id          bigint       not null,
    created_in  timestamp(6) not null,
    created_by  varchar(255),
    deleted     boolean      not null,
    deleted_in  timestamp(6),
    deleted_by  varchar(255),
    hash_id     varchar(255) not null,
    updated_in  timestamp(6),
    updated_by  varchar(255),
    entity_load_enabled boolean      not null,
    entity_name varchar(255) not null,
    primary key (id)
);
create table t_state
(
    id         bigint       not null,
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
create table t_user
(
    id         bigint       not null,
    created_in timestamp(6) not null,
    created_by varchar(255),
    deleted    boolean      not null,
    deleted_in timestamp(6),
    deleted_by varchar(255),
    hash_id    varchar(255) not null,
    updated_in timestamp(6),
    updated_by varchar(255),
    email      varchar(255) not null,
    login      varchar(255) not null,
    name       varchar(255) not null,
    password   varchar(255) not null,
    role       varchar(255) not null,
    fk_address bigint       not null,
    primary key (id)
);

create sequence public.sq_address start with 1 increment by 1;
create sequence public.sq_city start with 1 increment by 1;
create sequence public.sq_jwt start with 1 increment by 1;
create sequence public.sq_load_table start with 1 increment by 1;
create sequence public.sq_state start with 1 increment by 1;
create sequence public.sq_user start with 1 increment by 1;

alter table if exists t_address add constraint t_address_fk_city foreign key (fk_city) references t_city;;
alter table if exists t_city add constraint t_city_fk_state foreign key (fk_state) references t_state;
alter table if exists t_jwt add constraint t_jwt_fk_user foreign key (fk_user) references t_user;
alter table if exists t_user add constraint t_user_fk_address foreign key (fk_address) references t_address;

CREATE UNIQUE INDEX T_ADDRESS_HASH_ID_UK ON public.t_address (hash_id);
ALTER TABLE public.t_city ADD FK_STATE_UK_FIELD INT8 AS (CASE deleted WHEN TRUE THEN NULL ELSE fk_state END);
ALTER TABLE public.t_city ADD NAME_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE name END);
CREATE UNIQUE INDEX T_CITY_HASH_ID_UK ON public.t_city (hash_id);
CREATE UNIQUE INDEX T_CITY_FK_STATE_AND_NAME_UK ON public.t_city (FK_STATE_UK_FIELD, NAME_UK_FIELD);
ALTER TABLE public.t_jwt ADD BEARER_TOKEN_UK_FIELD VARCHAR(1024) AS (CASE deleted WHEN TRUE THEN NULL ELSE bearer_token END);
CREATE UNIQUE INDEX T_JWT_HASH_ID_UK ON public.t_jwt(hash_id);
CREATE UNIQUE INDEX T_JWT_BEARER_TOKEN_UK ON public.t_jwt(BEARER_TOKEN_UK_FIELD);
ALTER TABLE public.t_load_table ADD ENTITY_NAME_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE entity_name END);
CREATE UNIQUE INDEX T_LOAD_TABLE_HASH_ID_UK ON public.t_load_table(hash_id);
CREATE UNIQUE INDEX T_LOAD_TABLE_ENTITY_NAME_UK ON public.t_load_table(ENTITY_NAME_UK_FIELD);
ALTER TABLE public.t_state ADD NAME_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE name END);
ALTER TABLE public.t_state ADD UF_UK_FIELD VARCHAR(2) AS (CASE deleted WHEN TRUE THEN NULL ELSE uf END);
CREATE UNIQUE INDEX T_STATE_HASH_ID_UK ON public.t_state(hash_id);
CREATE UNIQUE INDEX T_STATE_NAME_UK ON public.t_state(NAME_UK_FIELD);
CREATE UNIQUE INDEX T_STATE_UF_UK ON public.t_state(UF_UK_FIELD);
ALTER TABLE public.t_user ADD EMAIL_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE email END);
ALTER TABLE public.t_user ADD LOGIN_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE login END);
ALTER TABLE public.t_user ADD FK_ADDRESS_UK_FIELD INT8 AS (CASE deleted WHEN TRUE THEN NULL ELSE fk_address END);
CREATE UNIQUE INDEX T_USER_HASH_ID_UK ON public.t_user (hash_id);
CREATE UNIQUE INDEX T_USER_EMAIL_UK ON public.t_user(EMAIL_UK_FIELD);
CREATE UNIQUE INDEX T_USER_LOGIN_UK ON public.t_user(LOGIN_UK_FIELD);
CREATE UNIQUE INDEX T_USER_FK_ADDRESS_UK ON public.t_user(FK_ADDRESS_UK_FIELD);

ALTER TABLE public.t_user ADD CONSTRAINT T_USER_ROLE_CHECK CHECK (role IN ('OWNER', 'CLIENT'));