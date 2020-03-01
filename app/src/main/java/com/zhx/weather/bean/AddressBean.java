package com.zhx.weather.bean;

import java.util.List;

/**
 * 路漫漫其修远兮，吾将上下而求索
 *
 * @author 服装学院的IT男
 * 时间: on 2020-02-28
 * 包名 com.zhx.weather.bean
 * 描述：
 */
public class AddressBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name :
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}