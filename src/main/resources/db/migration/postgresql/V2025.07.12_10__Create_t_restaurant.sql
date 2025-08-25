CREATE TABLE public.t_restaurant
(
    id                           int8         NOT NULL,
    created_by                   varchar(255) NULL,
    created_in                   timestamp(6) NOT NULL,
    deleted                      bool         NOT NULL,
    deleted_by                   varchar(255) NULL,
    deleted_in                   timestamp(6) NULL,
    hash_id                      varchar(255) NOT NULL,
    updated_by                   varchar(255) NULL,
    updated_in                   timestamp(6) NULL,
    breakfast_closing_hours      time(6)      NOT NULL,
    breakfast_opening_hours      time(6)      NOT NULL,
    breakfast_limit_reservations int8         NOT NULL,
    dinner_closing_hours         time(6)      NOT NULL,
    dinner_opening_hours         time(6)      NOT NULL,
    dinner_limit_reservations    int8         NOT NULL,
    lunch_closing_hours          time(6)      NOT NULL,
    lunch_opening_hours          time(6)      NOT NULL,
    lunch_limit_reservations     int8         NOT NULL,
    name                         varchar(255) NOT NULL,
    type                         varchar(255) NOT NULL,
    fk_menu                      int8         NOT NULL,
    fk_address                   int8         NOT NULL,
    primary key (id)
);

CREATE SEQUENCE public.sq_restaurant START WITH 1 INCREMENT BY 1;

ALTER TABLE public.t_restaurant
    ADD CONSTRAINT t_restaurant__fk_menu FOREIGN KEY (fk_menu) REFERENCES public.t_menu (id);
ALTER TABLE public.t_restaurant
    ADD CONSTRAINT t_restaurant__fk_address FOREIGN KEY (fk_address) REFERENCES public.t_address (id);

CREATE UNIQUE INDEX T_RESTAURANT__HASH_ID_UK ON public.t_restaurant (hash_id);
CREATE UNIQUE INDEX T_RESTAURANT__FK_MENU_UK ON public.t_restaurant (fk_menu, deleted) WHERE deleted IS NULL OR deleted = false;
CREATE UNIQUE INDEX T_RESTAURANT__FK_ADDRESS_UK ON public.t_restaurant (fk_address, deleted) WHERE deleted IS NULL OR deleted = false;

ALTER TABLE public.t_restaurant
    ADD CONSTRAINT T_RESTAURANT__TYPE_CHECK CHECK (type IN
                                                   ('QUICK_SERVICE_RESTAURANTS_OR_FAST_FOOD', 'FAST_CASUAL_CONCEPTS',
                                                    'CASUAL_DINING_RESTAURANTS', 'CONTEMPORARY_CASUAL',
                                                    'PREMIUM_CASUAL',
                                                    'FINE_DINING', 'FAMILY_STYLE_DINING',
                                                    'DINER_SOMETIMES_KNOWN_AS_A_GREASY_SPOON', 'CAFES_AND_COFFEE_SHOPS',
                                                    'BAKERY', 'DRINK_SHOP', 'BAR_OR_PUB',
                                                    'FOOD_TRUCKS_AND_MOBILE_EATERIES', 'POP_UP_RESTAURANTS',
                                                    'GHOST_OR_DELIVERY_ONLY_KITCHENS', 'DELIVERY_ONLY_CONCEPTS',
                                                    'DRIVE_IN_DINING_EXPERIENCES', 'CONCESSION_STAND', 'STEAKHOUSE',
                                                    'SUSHI_BAR', 'BBQ_RESTAURANT', 'TAPAS_BAR', 'ROTISSERIE',
                                                    'NOODLE_BAR', 'DESSERT_CAFE',
                                                    'ICE_CREAM_PARLORS_AND_FROZEN_DESSERT_SHOPS', 'BISTRO', 'PIZZERIA',
                                                    'BUFFET', 'THEMED_RESTAURANTS', 'ETHNIC_RESTAURANTS', 'BRASSERIE',
                                                    'CAFETERIA', 'PASTA_RESTAURANT', 'TABLE_SERVICE', 'COUNTER_SERVICE',
                                                    'TABLETOP_COOKING', 'FULL_SERVICE', 'FARM_TO_TABLE_RESTAURANTS',
                                                    'FUSION_CONCEPTS_RESTAURANTS',
                                                    'FOOD_HALLS_AND_SHARED_DINING_SPACES',
                                                    'EMERGING_AND_INNOVATIVE_DINING_MODELS'));