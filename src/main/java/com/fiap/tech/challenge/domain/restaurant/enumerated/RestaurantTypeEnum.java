package com.fiap.tech.challenge.domain.restaurant.enumerated;

import lombok.Getter;

@Getter
public enum RestaurantTypeEnum {

    QUICK_SERVICE_RESTAURANTS_OR_FAST_FOOD("Quick Service Restaurants (Fast Food)"),
    FAST_CASUAL_CONCEPTS("Fast Casual Concepts"),
    CASUAL_DINING_RESTAURANTS("Casual Dining Restaurants"),
    CONTEMPORARY_CASUAL("Contemporary Casual"),
    PREMIUM_CASUAL("Premium Casual"),
    FINE_DINING("Fine Dining"),
    FAMILY_STYLE_DINING("Family-Style Dining"),
    DINER_SOMETIMES_KNOWN_AS_A_GREASY_SPOON("Diner (Sometimes Known as a Greasy Spoon)"),
    CAFES_AND_COFFEE_SHOPS("Cafés and Coffee Shops"),
    BAKERY("Bakery"),
    DRINK_SHOP("Drink Shop"),
    BAR_OR_PUB("Bar or Pub"),
    FOOD_TRUCKS_AND_MOBILE_EATERIES("Food Trucks and Mobile Eateries"),
    POP_UP_RESTAURANTS("Pop-Up Restaurants"),
    GHOST_OR_DELIVERY_ONLY_KITCHENS("Ghost or Delivery-Only Kitchens"),
    DELIVERY_ONLY_CONCEPTS("Delivery-Only Concepts"),
    DRIVE_IN_DINING_EXPERIENCES("Drive-In Dining Experiences"),
    CONCESSION_STAND("Concession Stand"),
    STEAKHOUSE("Steakhouse"),
    SUSHI_BAR("Sushi Bar"),
    BBQ_RESTAURANT("BBQ Restaurant"),
    TAPAS_BAR("Tapas Bar"),
    ROTISSERIE("Rotisserie"),
    NOODLE_BAR("Noodle Bar"),
    DESSERT_CAFE("Dessert Café"),
    ICE_CREAM_PARLORS_AND_FROZEN_DESSERT_SHOPS("Ice Cream Parlors and Frozen Dessert Shops"),
    BISTRO("Bistro"),
    PIZZERIA("Pizzeria"),
    BUFFET("Buffet"),
    THEMED_RESTAURANTS("Themed Restaurants"),
    ETHNIC_RESTAURANTS("Ethnic Restaurants"),
    BRASSERIE("Brasserie"),
    CAFETERIA("Cafeteria"),
    PASTA_RESTAURANT("Pasta Restaurant"),
    TABLE_SERVICE("Table Service"),
    COUNTER_SERVICE("Counter Service"),
    TABLETOP_COOKING("Tabletop Cooking"),
    FULL_SERVICE("Full Service"),
    FARM_TO_TABLE_RESTAURANTS("Farm-to-Table Restaurants"),
    FUSION_CONCEPTS_RESTAURANTS("Fusion Concepts Restaurants"),
    FOOD_HALLS_AND_SHARED_DINING_SPACES("Food Halls and Shared Dining Spaces"),
    EMERGING_AND_INNOVATIVE_DINING_MODELS("Emerging and Innovative Dining Models");

    private final String description;

    RestaurantTypeEnum(String description) {
        this.description = description;
    }
}