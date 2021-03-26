package com.itang.mvvm.jetpack.t4model.bean.params;

import com.itang.mvvm.utils.L;
import com.itang.mvvm.utils.MD5Utils;
import com.itang.mvvm.utils.SecurityUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 构建网络请求所需参数（这个跟具后台规定写的方法）
 *
 * @author itang
 * @date 2016/1/31
 */
public class ParamsBuilder {

    public static final String MEDIA_TYPE_JSON = "application/json";
    public static final String MEDIA_TYPE_IMAGE = "image/*";
    public static final String KEY_AES_4_PARAMS = "FLYER_CREDITCARD_API";

    public static final String KEY_API_SERVER = "server";
    public static final String KEY_API_ACTION = "action";
    public static final String KEY_API_METHOD = "method";
    public static final String KEY_API_VER = "ver";
    public static final String KEY_API_TOKEN = "token";
    public static final String KEY_API_JSON = "json";

    private String server = "";
    private String action = "";
    private String method = "";
    private String ver = "";
    private String token = "";
    private String sign = "";
    private JSONObject jsonObject = new JSONObject();
    private String jsonResult = "";

    public ParamsBuilder() {
    }

    /**
     * 构建Get方式的参数
     *
     * @param params
     * @return
     */
    public JSONObject buildJson(Param... params) {
        JSONObject jsonObject = new JSONObject();
        for (Param param : params) {
            try {
                if (param.getJsonArray() != null) {
                    jsonObject.put(param.getKey(), param.getJsonArray());
                } else if (param.getJsonObject() != null) {
                    jsonObject.put(param.getKey(), param.getJsonObject());
                } else {
                    jsonObject.put(param.getKey(), param.getValue());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     * 构建Post方式的请求体
     *
     * @return
     */
    public RequestBody buildRequestBody() {
        return buildRequestBody(true);
    }

    public RequestBody buildRequestBody(boolean needEncrypt) {
        return createJsonBody(buildResult(true, needEncrypt));
    }

    @NotNull
    public static RequestBody createJsonBody(String json) {
        return RequestBody.create(MediaType.parse(ParamsBuilder.MEDIA_TYPE_JSON), json);
    }

    @NotNull
    public static RequestBody createImageBody(File file) {
        return RequestBody.create(MediaType.parse(ParamsBuilder.MEDIA_TYPE_IMAGE), file);
    }

    /**
     * @return
     */
    public RequestBody buildFromBody() {
        String json = buildResult();
        L.json(json);
        RequestBody formBody = new FormBody.Builder()
                .add("json", json)
                .add("sign", sign)
                .build();
        return formBody;
    }

    public String buildResult() {
        return buildResult(true, false);
    }

    private String buildResult(boolean needToken, boolean needEncrypt) {
        JSONObject result = new JSONObject();
        String jsonNew = "";
        try {
            //构造事务ID
            //JSONObject jsonObjectTransition = new JSONObject();
            //String deviceIMEI = SystemUtils.getDeviceIMEI(App.getAppContext());
            //String timeStamp = System.currentTimeMillis() + "";
            //jsonObjectTransition.put("deviceIMEI", deviceIMEI);
            //jsonObjectTransition.put("timeStamp", timeStamp);
            //String transactionId = SecurityUtil.encrypt(jsonObjectTransition.toString(), KEY_AES_4_PARAMS);
            //添加事务ID
            //result.put("transationId", transactionId);
            //添加请求参数
            result.put(KEY_API_SERVER, server);
            result.put(KEY_API_ACTION, action);
            result.put(KEY_API_METHOD, method);
            result.put(KEY_API_VER, ver);
            result.put(KEY_API_TOKEN, token);
            result.put(KEY_API_JSON, jsonObject);
            if (needToken) {
                //result.put(TOKEN, DbUtil.getToken());
            }
            jsonResult = result.toString();

            //这里设置false是为了方便测试，需要加密时去掉
            needEncrypt = false;

            if (needEncrypt) {
                jsonNew = SecurityUtil.encrypt(jsonResult, KEY_AES_4_PARAMS);
            } else {
                jsonNew = jsonResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //去除\,不然传递到后台，后台会挂掉
        if (jsonNew.contains("\\")) {
            jsonNew = jsonNew.replace("\\", "");
        }
        return jsonNew;
    }


    public void addJson(JSONObject jsonObject, Param param) throws JSONException {
        if (param.getValueList() == null) {
            jsonObject.put(param.getKey(), param.getValue());
        } else {
            JSONArray jsonArray = new JSONArray();
            for (Param[] array : param.getValueList()) {
                JSONObject jsonItem = new JSONObject();
                for (Param item : array) {
                    addJson(jsonItem, item);
                }
                jsonArray.put(jsonItem);
            }
            jsonObject.put(param.getKey(), jsonArray);
        }
    }

    public ParamsBuilder setServer(String server) {
        this.server = server;
        return this;
    }

    public ParamsBuilder setAction(String action) {
        this.action = action;
        return this;
    }

    public ParamsBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public ParamsBuilder setVer(String ver) {
        this.ver = ver;
        return this;
    }

    public ParamsBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public ParamsBuilder setJson(Param... params) {
        this.jsonObject = buildJson(params);
        this.sign = Helper.genericSign(params);
        return this;
    }

    public String getSign() {
        return sign;
    }

    public static final String KEY_SIGN_PARAM = "key_sign_param";

    /**
     * 生成 sign 帮助类
     */
    public static class Helper {

        /**
         * 生成 sign 参数
         *
         * @param params
         * @return
         */
        public static String genericSign(Param[] params) {
            List<Param> paramList = new ArrayList<>(Arrays.asList(params));
            String combine = Helper.combine(Helper.sortKeys(paramList));
            L.d("combine: " + combine);
            return MD5Utils.MD5(combine);
        }

        /**
         * 拼接参数
         *
         * @param paramList
         * @return
         */
        public static String combine(List<Param> paramList) {
            String result = "";
            try {
                StringBuilder sb = new StringBuilder();
                for (Param param : paramList) {
                    if (param.getJsonArray() != null) {
                        sb.append(param.getKey()).append("=").append(param.getJsonArray());
                    } else if (param.getJsonObject() != null) {
                        sb.append(param.getKey()).append("=").append(param.getJsonObject());
                    } else {
                        sb.append(param.getKey()).append("=").append(param.getValue());
                    }
                    sb.append("&");
                }
                sb.append("key").append("=").append("");//TODO 后台定义的key值
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }


        /**
         * 按字段首字母排序 Param 集合
         *
         * @param parameter
         * @return
         */
        public static List<Param> sortKeys(List<Param> parameter) {
            for (int i = 1; i < parameter.size(); i++) {
                for (int j = i; j > 0; j--) {
                    Param p1 = parameter.get(j - 1);
                    Param p2 = parameter.get(j);
                    if (compare(p1.getKey(), p2.getKey())) {
                        // 交互p1和p2这两个对象，写的超级恶心
                        String name = p1.getKey();
                        String value = p1.getValue();

                        p1.setKey(p2.getKey());
                        p1.setValue(p2.getValue());

                        p2.setKey(name);
                        p2.setValue(value);
                    }
                }
            }
            return parameter;
        }

        /**
         * 返回true说明str1大，返回false说明str2大
         *
         * @param str1
         * @param str2
         * @return
         */
        public static boolean compare(String str1, String str2) {
            boolean str1IsLonger;
            int minLen;

            if (str1.length() < str2.length()) {
                minLen = str1.length();
                str1IsLonger = false;
            } else {
                minLen = str2.length();
                str1IsLonger = true;
            }

            for (int index = 0; index < minLen; index++) {
                char ch1 = str1.charAt(index);
                char ch2 = str2.charAt(index);
                if (ch1 != ch2) {
                    if (ch1 > ch2) {
                        // str1大
                        return true;
                    } else {
                        // str2大
                        return false;
                    }
                }
            }
            return str1IsLonger;
        }
    }
}
