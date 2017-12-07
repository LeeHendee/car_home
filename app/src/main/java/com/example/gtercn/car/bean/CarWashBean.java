package com.example.gtercn.car.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class CarWashBean implements Serializable {


    /**
     * result : [{"id":"0085d2df49b14ef695a8c1cb8ab45451","rescue_service":"2100","repair_service":"5100","clean_service":"4100","maintain_service":"6100","tyre_service":"7100","shop_pic_url":"http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\14","shop_name":"奥奔汽车维修公司","shop_score":"8.5","longitude":"121.632713","latitude":"38.922375","distance":13047.8,"province":"辽宁省","city":"大连市","district":"高新园区","detail_address":"软件园","tel_number_list":"0412-5235121,0412-62534120","display_pic_url_list":"\\078d29ae03b54f06be3ffc0cf701dfa5\\1477964914836.png","shop_description":"奥奔汽车维修公司是一家以汽车现场救援，专业拖车为实体汽车服务机构.......","district_list":["西岗","中山","沙河口","甘井子","高新园"],"tel_num_list":["0412-5235121","0412-62534120"],"display_pic_list":["http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\1477964914836.png"],"service_type_list":{"2100":"紧急救援","4100":"洗车","5100":"修车","6100":"保养","7100":"轮胎"}},{"id":"0085d2df49b14ef695a8c1cb8ab45124","rescue_service":"2100","repair_service":"5100","clean_service":"4100","maintain_service":"6100","tyre_service":"7100","shop_pic_url":"http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\14","shop_name":"一路平安汽车救援服务有限公司","shop_score":"9","longitude":"121.615776","latitude":"38.915233","distance":13048.5,"province":"辽宁省","city":"大连市","district":"西岗区","detail_address":"珠江国际大厦","tel_number_list":"0412-5235236,0412-62534120","display_pic_url_list":"\\078d29ae03b54f06be3ffc0cf701dfa5\\1477964914836.png","shop_description":"一路平安汽车救援服务有限公司是一家以汽车现场救援，专业拖车为实体汽车服务机构.......","tel_num_list":["0412-5235236","0412-62534120"],"display_pic_list":["http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\1477964914836.png"],"service_brands_url":"","service_brands_url_list":["http://192.168.1.71/"],"service_type_list":{"2100":"紧急救援","4100":"洗车","5100":"修车","6100":"保养","7100":"轮胎"}},{"id":"0085d2df49b14ef695a8c1cb8ab45145","rescue_service":"2100","repair_service":"5100","clean_service":"4100","maintain_service":"6100","tyre_service":"7100","shop_pic_url":"http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\14","shop_name":"平安汽车救援服务有限公司","shop_score":"8","longitude":"121.586373","latitude":"38.912412","distance":13048.6,"province":"辽宁省","city":"大连市","district":"沙河口区","detail_address":"罗斯福","tel_number_list":"0412-5235236,0412-62534120","display_pic_url_list":"\\078d29ae03b54f06be3ffc0cf701dfa5\\1477964914836.png","shop_description":"平安汽车救援服务有限公司是一家以汽车现场救援，专业拖车为实体汽车服务机构.......","tel_num_list":["0412-5235236","0412-62534120"],"display_pic_list":["http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\1477964914836.png"],"service_type_list":{"2100":"紧急救援","4100":"洗车","5100":"修车","6100":"保养","7100":"轮胎"}}]
     * err_code : 0
     * err_message : OK
     * message : 查询洗车公司成功
     */

    private String err_code;
    private String err_message;
    private String message;
    /**
     * id : 0085d2df49b14ef695a8c1cb8ab45451
     * rescue_service : 2100
     * repair_service : 5100
     * clean_service : 4100
     * maintain_service : 6100
     * tyre_service : 7100
     * shop_pic_url : http://192.168.1.71/\078d29ae03b54f06be3ffc0cf701dfa5\14
     * shop_name : 奥奔汽车维修公司
     * shop_score : 8.5
     * longitude : 121.632713
     * latitude : 38.922375
     * distance : 13047.8
     * province : 辽宁省
     * city : 大连市
     * district : 高新园区
     * detail_address : 软件园
     * tel_number_list : 0412-5235121,0412-62534120
     * display_pic_url_list : \078d29ae03b54f06be3ffc0cf701dfa5\1477964914836.png
     * shop_description : 奥奔汽车维修公司是一家以汽车现场救援，专业拖车为实体汽车服务机构.......
     * district_list : ["西岗","中山","沙河口","甘井子","高新园"]
     * tel_num_list : ["0412-5235121","0412-62534120"]
     * display_pic_list : ["http://192.168.1.71/\\078d29ae03b54f06be3ffc0cf701dfa5\\1477964914836.png"]
     * service_type_list : {"2100":"紧急救援","4100":"洗车","5100":"修车","6100":"保养","7100":"轮胎"}
     */

    private List<ResultBean> result;

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_message() {
        return err_message;
    }

    public void setErr_message(String err_message) {
        this.err_message = err_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        private String id;
        private String rescue_service;
        private String repair_service;
        private String clean_service;
        private String maintain_service;
        private String tyre_service;
        private String shop_pic_url;
        private String shop_name;
        private String shop_score;
        private String shop_id;
        private String longitude;
        private String latitude;
        private double distance;
        private String province;
        private String city;
        private String district;
        private String detail_address;
        private String tel_number_list;
        private String display_pic_url_list;
        private String shop_description;
        /**
         * 2100 : 紧急救援
         * 4100 : 洗车
         * 5100 : 修车
         * 6100 : 保养
         * 7100 : 轮胎
         */

        private ServiceTypeListBean service_type_list;
        private List<String> district_list;
        private List<String> tel_num_list;
        private List<String> display_pic_list;
        private int is_favored;

        public int getIs_favored() {
            return is_favored;
        }

        public void setIs_favored(int is_favored) {
            this.is_favored = is_favored;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRescue_service() {
            return rescue_service;
        }

        public void setRescue_service(String rescue_service) {
            this.rescue_service = rescue_service;
        }

        public String getRepair_service() {
            return repair_service;
        }

        public void setRepair_service(String repair_service) {
            this.repair_service = repair_service;
        }

        public String getClean_service() {
            return clean_service;
        }

        public void setClean_service(String clean_service) {
            this.clean_service = clean_service;
        }

        public String getMaintain_service() {
            return maintain_service;
        }

        public void setMaintain_service(String maintain_service) {
            this.maintain_service = maintain_service;
        }

        public String getTyre_service() {
            return tyre_service;
        }

        public void setTyre_service(String tyre_service) {
            this.tyre_service = tyre_service;
        }

        public String getShop_pic_url() {
            return shop_pic_url;
        }

        public void setShop_pic_url(String shop_pic_url) {
            this.shop_pic_url = shop_pic_url;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_score() {
            return shop_score;
        }

        public void setShop_score(String shop_score) {
            this.shop_score = shop_score;
        }

        public String getShop_id(){
            return shop_id;
        }

        public void setShop_id(String shop_id){
            this.shop_id =shop_id;
        }

        public String getLongtitude() {
            return longitude;
        }

        public void setLongtitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDetail_address() {
            return detail_address;
        }

        public void setDetail_address(String detail_address) {
            this.detail_address = detail_address;
        }

        public String getTel_number_list() {
            return tel_number_list;
        }

        public void setTel_number_list(String tel_number_list) {
            this.tel_number_list = tel_number_list;
        }

        public String getDisplay_pic_url_list() {
            return display_pic_url_list;
        }

        public void setDisplay_pic_url_list(String display_pic_url_list) {
            this.display_pic_url_list = display_pic_url_list;
        }

        public String getShop_description() {
            return shop_description;
        }

        public void setShop_description(String shop_description) {
            this.shop_description = shop_description;
        }

        public List<String> getDistrict_list() {
            return district_list;
        }

        public void setDistrict_list(List<String> district_list) {
            this.district_list = district_list;
        }

        public List<String> getTel_num_list() {
            return tel_num_list;
        }

        public void setTel_num_list(List<String> tel_num_list) {
            this.tel_num_list = tel_num_list;
        }

        public List<String> getDisplay_pic_list() {
            return display_pic_list;
        }

        public void setDisplay_pic_list(List<String> display_pic_list) {
            this.display_pic_list = display_pic_list;
        }

        public ServiceTypeListBean getService_type_list() {
            return service_type_list;
        }

        public void setService_type_list(ServiceTypeListBean service_type_list) {
            this.service_type_list = service_type_list;
        }

        public static class ServiceTypeListBean implements Serializable {
            @SerializedName("2100")
            private String value2100;
            @SerializedName("4100")
            private String value4100;
            @SerializedName("5100")
            private String value5100;
            @SerializedName("6100")
            private String value6100;
            @SerializedName("7100")
            private String value7100;

            public String getValue2100() {
                return value2100;
            }

            public void setValue2100(String value2100) {
                this.value2100 = value2100;
            }

            public String getValue4100() {
                return value4100;
            }

            public void setValue4100(String value4100) {
                this.value4100 = value4100;
            }

            public String getValue5100() {
                return value5100;
            }

            public void setValue5100(String value5100) {
                this.value5100 = value5100;
            }

            public String getValue6100() {
                return value6100;
            }

            public void setValue6100(String value6100) {
                this.value6100 = value6100;
            }

            public String getValue7100() {
                return value7100;
            }

            public void setValue7100(String value7100) {
                this.value7100 = value7100;
            }
        }
    }
}
