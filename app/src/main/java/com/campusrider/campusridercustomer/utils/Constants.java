package com.campusrider.campusridercustomer.utils;

public class Constants {
    public  static  String FCM_API_BASE_URL="https://fcm.googleapis.com/v1/projects/campus-rider-d1c27/messages:send";
    public static String API_BASE_URL = "http://campusriderbd.com/Customer/customer/";
    public static String GET_FOOD_CATEGORIES_URL = API_BASE_URL + "food_category.php";
    public static String LOGIN_URL = API_BASE_URL + "login.php";

    public static String IMAGE_URL = "http://campusriderbd.com/media/";
    public static String Registration_URL = API_BASE_URL + "registration.php";
    public static String UPDATE_USER_URL = API_BASE_URL + "update_user.php?id=";
    public static String GET_VENDORS_URL = API_BASE_URL + "vendors.php?area=";
    public static String UPDATE_USER_TOKEN_URL = API_BASE_URL + "update_token.php?id=";
    public static String GET_VARIATION_URL=API_BASE_URL+"view_variation.php?product_id=";
    public static String GET_VARIATION_DETAILS_URL=API_BASE_URL+"view_variation_details.php?variation_id=";
    public static String GET_VENDOR_PRODUCTS_URL = API_BASE_URL + "vendor_food_items.php?vendor_id=";
    public static String GET_VENDOR_PRODUCTS_DETAIL_URL = API_BASE_URL + "vendor_food_detail.php?product_id=";
    public static String POST_ORDER_URL = API_BASE_URL + "place_order.php";
    public static String GET_ORDER_LIST_URL = API_BASE_URL + "view_orders.php?type=food&customer_id=";
    public static String POST_ORDER_DETAILS_URL = API_BASE_URL + "place_order_detail.php";
    public static String GET_LOCATION_URL = API_BASE_URL + "view_location.php";
    public static String GET_delivery_fee_URL = API_BASE_URL + "delivery_fee.php?name=";
    public static String GET_FOOD_ORDER_DETAILS_URL = API_BASE_URL + "order_details.php?type=food&order_id=";
    public static String GET_CAT_WISE_SHOP_URL = API_BASE_URL + "cat_vendor.php?cat=";
    public static String GET_PRODUCT_CAT_URL=API_BASE_URL+"product_cat.php?id=";
    public static String SEARCH_VENDOR_URL=API_BASE_URL+"search.php?type=food&word=";


    ///Grocery
    public static String GET_CATEGORY_URL = API_BASE_URL + "grocery_cat.php";
    public static String GET_GROCERY_ITEM_URL=API_BASE_URL+"grocery_item_list.php";
    public static String GET_GROCERY_ITEM_DETAIL_URL=API_BASE_URL+"grocery_item_detail.php?id=";
    public static String GET_CAT_WISE_ITEM_URL = API_BASE_URL + "cat_item.php?cat=";
    public static String POST_WISHLIST_URL = API_BASE_URL + "add_shooping_list.php";
    public static String REMOVE_ITEM_URL = API_BASE_URL + "remove.php?id=";
    public static String GET_WISHLIST_URL = API_BASE_URL + "shopping_list.php?id=";
    public static String POST_GROCERY_ORDER_URL = API_BASE_URL + "grocery_place_order.php";
    public static String POST_GROCERY_ORDER_DETAILS_URL = API_BASE_URL + "grocery_place_order_detail.php";
    public static String GET_GROCERY_ORDER_LIST_URL = API_BASE_URL + "view_orders.php?type=grocery&customer_id=";
    public static String GET_GROCERY_ORDER_DETAILS_URL = API_BASE_URL + "order_details.php?type=grocery&order_id=";
    public static String SEARCH_ITEM_URL=API_BASE_URL+"search.php?type=grocery&word=";
}
