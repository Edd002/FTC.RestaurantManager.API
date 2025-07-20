create table public.t_address
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
    cep          varchar(255) not null,
    complement   varchar(255),
    description  varchar(255) not null,
    neighborhood varchar(255) not null,
    number       varchar(255) not null,
    postal_code  varchar(255) not null,
    fk_city      int8         not null,
    primary key (id)
);

create sequence public.sq_address start with 1 increment by 1;

alter table if exists public.t_address add constraint t_address__fk_city foreign key (fk_city) references t_city;

CREATE UNIQUE INDEX T_ADDRESS__HASH_ID_UK ON public.t_address (hash_id);