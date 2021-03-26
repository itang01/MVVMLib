package com.itang.mvvm.exception;

public class ApiException extends RuntimeException {

    //对应HTTP的状态码
    public static final int UNAUTHORIZED = 401;//没有验证信息或者验证失败
    public static final int FORBIDDEN = 403;//理解该请求，但不被接受。相应的描述信息会说明原因。
    public static final int NOT_FOUND = 404;//资源不存在，请求的用户的不存在，请求的格式不被支持。
    public static final int REQUEST_TIMEOUT = 408;//请求超时
    public static final int INTERNAL_SERVER_ERROR = 500;//服务器内部出错了。请联系我们尽快解决问题。
    public static final int BAD_GATEWAY = 502;//业务服务器下线了或者正在升级。请稍后重试。
    public static final int SERVICE_UNAVAILABLE = 503;//服务器无法响应请求。请稍后重试。
    public static final int GATEWAY_TIMEOUT = 504;//服务器在运行，但是无法响应请求。请稍后重试。

    public static final int SUCCESS = 200;

    //出错提示
    public static String userNotExitMsg = "该用户不存在";
    public static String pswErrorMsg = "密码错误";
    public static String permissionExpireErrorMsg = "权限错误";
    public static String networkMsg = "暂时未连接上网络！请连接后重试~";
    public static String parseMsg = "解析错误";
    public static String paramMsg = "参数错误";//操作失败
    public static String unknownMsg = "未知错误";
    public static String permissionErrorMsg = "没有权限";
    public static String normalError = "服务器打瞌睡了，再试一次吧^_^";
    public static String nopermissionErrorMsg = "对不起，没有权限";
    //业务错误（视具体而定）
    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;
    public static final int APP_CODE_LOGIN_CONFLICT = 10001;//登录冲突

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;
    public static final int CAST_ERROR = 1003;
    public static final int PARAM_ERROR = 9999;//参数错误

    //提示类型：TIP_VIEW_DIALOG：提示对话框，TIP_VIEW_TOAST：吐司
    public static final int TIP_VIEW_DIALOG = 1;
    public static final int TIP_VIEW_TOAST = 2;

    private int code;
    private int error_type;//提示样式的类型： 1=弹窗提示  2=气泡提示

    public ApiException(int code, int error_type, String msg) {
        this(getApiExceptionMessage(code, msg));
        this.code = code;
        this.error_type = error_type;
    }

    public ApiException(String detail) {
        super(detail);
    }

    public int getCode() {
        return code;
    }

    public int getTipViewType() {
        return error_type;
    }

    public String getMsg() {
        return getMessage();
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @param msg
     * @return
     */
    private static String getApiExceptionMessage(int code, String msg) {
        String message;
        switch (code) {
            case USER_NOT_EXIST:
                message = userNotExitMsg;
                break;
            case WRONG_PASSWORD:
                message = pswErrorMsg;
                break;
            case FORBIDDEN:
                message = permissionErrorMsg;
                break;
            case PARSE_ERROR:
                message = parseMsg;
                break;
            case PARAM_ERROR:
                message = paramMsg;
                break;
            default:
                message = msg;

        }
        return message;
    }
}