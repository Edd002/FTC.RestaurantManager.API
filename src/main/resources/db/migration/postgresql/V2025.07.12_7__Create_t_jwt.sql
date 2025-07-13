create table t_jwt
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

alter table if exists t_jwt add constraint t_jwt_fk_user foreign key (fk_user) references t_user;

CREATE UNIQUE INDEX T_JWT_HASH_ID_UK ON public.t_jwt (hash_id);
CREATE UNIQUE INDEX T_JWT_BEARER_TOKEN_UK ON public.t_jwt (bearer_token, deleted) WHERE deleted IS NULL OR deleted = false;