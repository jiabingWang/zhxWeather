package com.zhx.weather.dynamic.util;


import com.zhx.weather.R;
import com.zhx.weather.WeatherApp;

/**
 * Created by liyu on 2016/11/18.
 */

public class SettingsUtil {

    public static final String WEATHER_SRC = "weather_src";//天气源
    public static final String WEATHER_SHARE_TYPE = "weather_share_type";//天气分享形式
    public static final String WEATHER_KEY = "weather_key";//天气 key
    public static final String WEATHER_DATE_TYPE = "weather_date_type";//天气日期显示样式，日期 or 星期
    public static final String THEME = "theme_color";//主题
    public static final String CLEAR_CACHE = "clean_cache";//清空缓存
    public static final String BUS_REFRESH_FREQ = "bus_refresh_freq";//公交自动刷新频率

    public static final int WEATHER_DATE_TYPE_WEEK = 0;

    public static final int WEATHER_DATE_TYPE_DATE = 1;

    public static final int WEATHER_SRC_HEFENG = 0;

    public static final int WEATHER_SRC_XIAOMI = 1;

    public static void setWeatherShareType(String type) {
        SPUtil.put(WeatherApp.app, WEATHER_SHARE_TYPE, type);
    }

    public static String getWeatherShareType() {
        return (String) SPUtil.get(WeatherApp.app, WEATHER_SHARE_TYPE, WeatherApp.app.getResources().getStringArray(R.array.share_type)[0]);
    }

    public static void setWeatherSrc(int src) {
        SPUtil.put(WeatherApp.app, WEATHER_SRC, src);
    }

    public static int getWeatherSrc() {
        return (int) SPUtil.get(WeatherApp.app, WEATHER_SRC, WEATHER_SRC_HEFENG);
    }

    public static void setWeatherDateType(int type) {
        SPUtil.put(WeatherApp.app, WEATHER_DATE_TYPE, type);
    }

    public static int getWeatherDateType() {
        return (int) SPUtil.get(WeatherApp.app, WEATHER_DATE_TYPE, 0);
    }

    public static void setWeatherKey(String key) {
        SPUtil.put(WeatherApp.app, WEATHER_KEY, key);
    }

    public static String getWeatherKey() {
        return (String) SPUtil.get(WeatherApp.app, WEATHER_KEY, "");
    }

    public static void setTheme(int themeIndex) {
        SPUtil.put(WeatherApp.app, THEME, themeIndex);
    }

    public static int getTheme() {
        return (int) SPUtil.get(WeatherApp.app, THEME, 0);
    }

    public static void setBusRefreshFreq(int freq) {
        SPUtil.put(WeatherApp.app, BUS_REFRESH_FREQ, freq);
    }

    public static int getBusRefreshFreq() {
        return (int) SPUtil.get(WeatherApp.app, BUS_REFRESH_FREQ, 10);
    }

}
