CREATE TABLE public.t_reservation
(
    id               int8         NOT NULL,
    created_by       varchar(255) NULL,
    created_in       timestamp(6) NOT NULL,
    deleted          bool         NOT NULL,
    deleted_by       varchar(255) NULL,
    deleted_in       timestamp(6) NULL,
    hash_id          varchar(255) NOT NULL,
    updated_by       varchar(255) NULL,
    updated_in       timestamp(6) NULL,
    booking_status   varchar(255) NOT NULL,
    booking_time     varchar(255) NOT NULL,
    booking_date     date         NOT NULL,
    booking_quantity int8         NOT NULL,
    fk_restaurant    int8         NOT NULL,
    fk_user          int8         NOT NULL,
    primary key (id)
);

CREATE SEQUENCE public.sq_reservation START WITH 1 INCREMENT BY 1;

ALTER TABLE public.t_reservation
    ADD CONSTRAINT t_reservation__fk_restaurant FOREIGN KEY (fk_restaurant) REFERENCES public.t_restaurant (id);
ALTER TABLE public.t_reservation
    ADD CONSTRAINT t_reservation__fk_user FOREIGN KEY (fk_user) REFERENCES public.t_user (id);

CREATE UNIQUE INDEX T_RESERVATION__HASH_ID_UK ON public.t_reservation (hash_id);
CREATE UNIQUE INDEX T_RESERVATION__FK_RESTAURANT_AND_FK_USER_AND_B_TIM_AND_B_DAT_UK ON public.t_reservation (fk_restaurant, fk_user, booking_time, booking_date, deleted) WHERE deleted IS NULL OR deleted = false;