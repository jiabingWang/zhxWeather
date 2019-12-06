package com.zhx.weather.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * 描述：生活指数
 */
public class LifestyBean {

    private List<HeWeather6Bean> HeWeather6;

    public static LifestyBean objectFromData(String str) {

        return new Gson().fromJson(str, LifestyBean.class);
    }

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101020300","location":"宝山","parent_city":"上海","admin_area":"上海","cnty":"中国","lat":"31.39889526","lon":"121.48993683","tz":"+8.00"}
         * update : {"loc":"2019-12-06 16:55","utc":"2019-12-06 08:55"}
         * status : ok
         * lifestyle : [{"type":"comf","brf":"很不舒适","txt":"白天天阴风大，且天气凉，在这种天气条件下，会感觉很冷，不舒适，外出不宜过久，并注意防风保暖。"},{"type":"drsg","brf":"冷","txt":"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。"},{"type":"flu","brf":"较易发","txt":"虽然温度适宜但风力较大，仍较易发生感冒，体质较弱的朋友请注意适当防护。"},{"type":"sport","brf":"较不宜","txt":"有降水，且风力很强，推荐您选择室内运动；若坚持户外运动，请注意保暖并携带雨具。"},{"type":"trav","brf":"一般","txt":"天气较好，温度适宜，但风比较大，旅游指数一般，外出请注意防暑防风，尽量避免水上活动。"},{"type":"uv","brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"},{"type":"cw","brf":"适宜","txt":"适宜洗车，未来持续两天无雨天气较好，适合擦洗汽车，蓝天白云、风和日丽将伴您的车子连日洁净。"},{"type":"air","brf":"优","txt":"气象条件非常有利于空气污染物稀释、扩散和清除。"}]
         */

        private BasicBean basic;
        private UpdateBean update;
        private String status;
        private List<LifestyleBean> lifestyle;

        public static HeWeather6Bean objectFromData(String str) {

            return new Gson().fromJson(str, HeWeather6Bean.class);
        }

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<LifestyleBean> getLifestyle() {
            return lifestyle;
        }

        public void setLifestyle(List<LifestyleBean> lifestyle) {
            this.lifestyle = lifestyle;
        }

        public static class BasicBean {
            /**
             * cid : CN101020300
             * location : 宝山
             * parent_city : 上海
             * admin_area : 上海
             * cnty : 中国
             * lat : 31.39889526
             * lon : 121.48993683
             * tz : +8.00
             */

            private String cid;
            private String location;
            private String parent_city;
            private String admin_area;
            private String cnty;
            private String lat;
            private String lon;
            private String tz;

            public static BasicBean objectFromData(String str) {

                return new Gson().fromJson(str, BasicBean.class);
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParent_city() {
                return parent_city;
            }

            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }

            public String getAdmin_area() {
                return admin_area;
            }

            public void setAdmin_area(String admin_area) {
                this.admin_area = admin_area;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getTz() {
                return tz;
            }

            public void setTz(String tz) {
                this.tz = tz;
            }
        }

        public static class UpdateBean {
            /**
             * loc : 2019-12-06 16:55
             * utc : 2019-12-06 08:55
             */

            private String loc;
            private String utc;

            public static UpdateBean objectFromData(String str) {

                return new Gson().fromJson(str, UpdateBean.class);
            }

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getUtc() {
                return utc;
            }

            public void setUtc(String utc) {
                this.utc = utc;
            }
        }

        public static class LifestyleBean {
            /**
             * type : comf
             * brf : 很不舒适
             * txt : 白天天阴风大，且天气凉，在这种天气条件下，会感觉很冷，不舒适，外出不宜过久，并注意防风保暖。
             */

            private String type;
            private String brf;
            private String txt;

            public static LifestyleBean objectFromData(String str) {

                return new Gson().fromJson(str, LifestyleBean.class);
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }
    }
}
