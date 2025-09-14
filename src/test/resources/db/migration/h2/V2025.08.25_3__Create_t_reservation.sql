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

ALTER TABLE public.t_reservation ADD FK_RESTAURANT_UK_FIELD INT8 AS (CASE deleted WHEN TRUE THEN NULL ELSE fk_restaurant END);
ALTER TABLE public.t_reservation ADD FK_USER_UK_FIELD INT8 AS (CASE deleted WHEN TRUE THEN NULL ELSE fk_user END);
ALTER TABLE public.t_reservation ADD BOOKING_STATUS_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE booking_status END);
ALTER TABLE public.t_reservation ADD BOOKING_TIME_UK_FIELD VARCHAR(255) AS (CASE deleted WHEN TRUE THEN NULL ELSE booking_time END);
CREATE UNIQUE INDEX T_RESERVATION__HASH_ID_UK ON public.t_reservation (hash_id);
CREATE UNIQUE INDEX T_RESERVATION__FK_RESTAURANT_AND_FK_USER_AND_B_TIM_AND_B_DAT_UK ON public.t_reservation (FK_RESTAURANT_UK_FIELD, FK_USER_UK_FIELD, BOOKING_STATUS_UK_FIELD, BOOKING_TIME_UK_FIELD);

ALTER TABLE public.t_reservation
    ADD CONSTRAINT T_RESERVATION__BOOKING_STATUS_CHECK CHECK (booking_status IN ('REQUESTED', 'ACCEPTED', 'REJECTED', 'CANCELED'));
ALTER TABLE public.t_reservation
    ADD CONSTRAINT T_RESERVATION__BOOKING_TIME_CHECK CHECK (booking_time IN ('BREAKFAST', 'LUNCH', 'DINNER'));