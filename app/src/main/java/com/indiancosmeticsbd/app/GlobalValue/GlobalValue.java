package com.indiancosmeticsbd.app.GlobalValue;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class GlobalValue {
    public static final String BASE_URL = "https://indiancosmeticsbd.com";
    public static final String END_POINT = "/graph/api/v2/";

    public static final String API_TOKEN = "aHR0cHN+aW5kaWFuY29zbWV0aWNzYmQuY29tfmFwaQ";

    public static final String WEBSITE_URL = "https://www.indiancosmeticsbd.com/";
    public static final String PRODUCT_IMAGE_BASE_URL = "https://indiancosmeticsbd.com/proimg/";

    /*Banner Slider*/
    public static final String BANNER_SLIDER = "bannerSlider";
    public static final String HOME_PAGE_TOP = "home_page_top";
    public static final String HOME_PAGE_MIDDLE = "home_page_middle_banner";
    public static final String HOME_PAGE_BOTTOM = "home_page_bottom";

    /*SharedPrefName*/
    public static final String SHARED_PREF_NAME = "indiancosmeticsbd";
    public static final String CART = "cart";
    public static final String WISHLIST = "wishlist";

    /*Contact Info*/
    public static final String CONTACT_INFO = "contactInfo";

    /*User Info*/
    public static final String user_id = "user_id";
    public static final String user_username = "user_username";
    public static final String user_password = "user_password";
    public static final String user_token = "user_token";
    public static final String user_first_name = "user_first_name";
    public static final String user_last_name = "user_last_name";
    public static final String user_email = "user_email";
    public static final String user_address = "user_address";
    public static final String user_city = "user_city";
    public static final String user_district = "user_district";
    public static final String user_country = "user_country";
    public static final String user_postalcode = "user_postalcode";
    public static final String user_mobile_number = "user_mobile_number";
    public static final String user_previous_notification_size = "user_previous_notification_size";
    public static final String user_after_notification_size = "user_after_notification_size";
    public static final String user_notification = "user_notification";
    public static final String user_orders = "user_orders";
    public static final String user_date = "user_date";

    /*Company*/
    public static final String COMPANY_ADDRESS = "company_address";
    public static final String COMPANY_ADDRESS2 = "company_address2";
    public static final String COMPANY_MOBILE_1 = "company_mobile1";
    public static final String COMPANY_MOBILE_2 = "company_mobile2";
    public static final String COMPANY_MOBILE_3 = "company_mobile3";
    public static final String COMPANY_PHONE = "company_phone";
    public static final String COMPANY_EMAIL = "company_email";
    public static final String COMPANY_FACEBOOK = "company_facebook";
    public static final String COMPANY_TWITTER = "company_twitter";
    public static final String COMPANY_INSTAGRAM = "company_instagram";
    public static final String COMPANY_LINKEDIN = "company_linkedin";
    public static final String COMPANY_GMAIL = "company_gmail";
    public static final String COMPANY_YOUTUBE = "company_youtube";
    public static final String COMPANY_YAHOO = "company_yahoo";
    public static final String COMPANY_SKYPE = "company_skype";

    /*User Info and User Validate*/
    public static final String USER_INFO = "userInfo";
    public static final String COLUMN_TYPE = "username";
    public static final String USER_VALIDATE = "userValidate";

    /*Product Categories*/
    public static final String PRODUCT_CATEGORIES = "productCategories";

    /*Product List*/
    public static final String PRODUCT_LIST = "products";

    /*Product Info*/
    public static final String PRODUCT_INFO = "productInfo";

    /*Forgot Password*/
    public static final String FORGOT_PASSWORD = "forgotPassword";

    /*COD Order*/
    public static final String ORDER_SUBMIT = "orderSubmit";
    public static final String COD = "cod";

    public static void SHOWBADGE(Activity activity, String sharedPrefName, int menuId, BottomNavigationView bottomNavigationView){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = activity.getSharedPreferences(sharedPrefName, MODE_PRIVATE);
        String json = sharedPreferences.getString(sharedPrefName, "");
        Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
        ArrayList<Cart> carts = gson.fromJson(json, type);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(menuId);
        badgeDrawable.setVisible(true);
        if (json.equals(""))
        {
            //return 0;
            badgeDrawable.setVisible(false);
            //badgeDrawable.setNumber(0);
        }
        else{
            if (carts.isEmpty()){
                //return 0;
                badgeDrawable.setVisible(false);
                //badgeDrawable.setNumber(0);
            }
            else{
                badgeDrawable.setNumber(carts.size());
                //return carts.size();
            }
        }
    }

    public static void NOTIFICATION_SHOW(Activity activity, BottomNavigationView bottomNavigationView){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        int previousNotificationSize = sharedPreferences.getInt(user_previous_notification_size, 0);
        int afterNotificationSize = sharedPreferences.getInt(user_after_notification_size, 0);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.bottom_nav_account);
        badgeDrawable.setVisible(previousNotificationSize<afterNotificationSize);
    }
}
