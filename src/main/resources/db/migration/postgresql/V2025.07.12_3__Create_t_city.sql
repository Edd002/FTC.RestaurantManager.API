create table public.t_city
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

alter table if exists public.t_city add constraint t_city__fk_state foreign key (fk_state) references t_state;

CREATE UNIQUE INDEX T_CITY__HASH_ID_UK ON public.t_city (hash_id);
CREATE UNIQUE INDEX T_CITY__FK_STATE_AND_NAME_UK ON public.t_city (fk_state, name, deleted) WHERE deleted IS NULL OR deleted = false;