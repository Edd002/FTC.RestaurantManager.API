create table t_user
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
    fk_user_type varchar(255) not null,
    fk_address   int8         not null,
    primary key (id)
);

create sequence public.sq_user start with 1 increment by 1;

alter table if exists t_user add constraint t_user_fk_user_type foreign key (fk_user_type) references t_user_type;
alter table if exists t_user add constraint t_user_fk_address foreign key (fk_address) references t_address;

CREATE UNIQUE INDEX T_USER_HASH_ID_UK ON public.t_user (hash_id);
CREATE UNIQUE INDEX T_USER_EMAIL_UK ON public.t_user (email, deleted) WHERE deleted IS NULL OR deleted = false;
CREATE UNIQUE INDEX T_USER_LOGIN_UK ON public.t_user (login, deleted) WHERE deleted IS NULL OR deleted = false;
CREATE UNIQUE INDEX T_USER_FK_ADDRESS_UK ON public.t_user (fk_address, deleted) WHERE deleted IS NULL OR deleted = false;