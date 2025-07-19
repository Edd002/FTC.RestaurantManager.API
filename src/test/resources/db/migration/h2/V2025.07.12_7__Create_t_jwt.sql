create table public.t_jwt
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
    bearer_token text         not null,
    fk_user      int8         not null,
    primary key (id)
);

create sequence public.sq_jwt start with 1 increment by 1;

alter table if exists public.t_jwt add constraint t_jwt__fk_user foreign key (fk_user) references t_user;

ALTER TABLE public.t_jwt ADD BEARER_TOKEN_UK_FIELD VARCHAR(1024) AS (CASE deleted WHEN TRUE THEN NULL ELSE bearer_token END);
CREATE UNIQUE INDEX T_JWT__HASH_ID_UK ON public.t_jwt(hash_id);
CREATE UNIQUE INDEX T_JWT__BEARER_TOKEN_UK ON public.t_jwt(BEARER_TOKEN_UK_FIELD);