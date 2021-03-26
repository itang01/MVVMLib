package com.itang.mvvm.utils;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @des 字符串处理工具类
 * ---1.手机号码正则
 * ---2.邮箱正则
 * ---3.去掉金钱后面的两个零，如果小数点后两位为“00”，则去掉 “00”，如果小数点后两位为“0”，则去掉 “0”， 反之，保留
 * ---4.从字符串中截取连续6位数字 用于从短信中获取动态密码
 * ---5.截取字符串 中间某一串字符，截取startStr，endStr之间的值
 */
public class StringUtils {
    private StringUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 手机号码正则
     */
    public static final String REG_PHONE_CHINA = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\dd{8}$";

    /**
     * 邮箱正则
     */
    public static final String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 去掉金钱后面的两个零，如果小数点后两位为“00”，则去掉 “00”，如果小数点后两位为“0”，则去掉 “0”， 反之，保留
     */
    public static void subMoneyZero(TextView textView, String moneyStr) {
        if ("0".equals(moneyStr)) {
            textView.setText("￥" + moneyStr);
        }
        int dotIndex = moneyStr.indexOf(".");
        String behindStr = moneyStr.substring(dotIndex + 1);
        String behindStr2 = moneyStr.substring(dotIndex + 2);
        if ("00".equals(behindStr) || "0".equals(behindStr)) {
            textView.setText("￥" + moneyStr.substring(0, dotIndex));
        } else if ("0".equals(behindStr2)) {
            textView.setText("￥" + moneyStr.substring(0, dotIndex + 2));
        } else {
            textView.setText("￥" + moneyStr);
        }
    }

    public static String subMoneyZero(String moneyStr) {
        if ("0".equals(moneyStr)) {
            return moneyStr;
        }
        int dotIndex = moneyStr.indexOf(".");
        String behindStr = moneyStr.substring(dotIndex + 1);
        String behindStr2 = moneyStr.substring(dotIndex + 2);
        if ("00".equals(behindStr) || "0".equals(behindStr)) {
            return moneyStr.substring(0, dotIndex);
        } else if ("0".equals(behindStr2)) {
            return moneyStr.substring(0, dotIndex + 2);
        } else {
            return moneyStr;
        }
    }

    /**
     * 从字符串中截取连续6位数字 用于从短信中获取动态密码
     *
     * @param str 短信内容
     * @return 截取得到的6位动态密码
     */
    public static String getDynamicPassword(String str) {
        Pattern continuousNumberPattern = Pattern.compile("[0-9\\.]+");
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while (m.find()) {
            if (m.group().length() == 6) {
                System.out.print(m.group());
                dynamicPassword = m.group();
            }
        }

        return dynamicPassword;
    }

    /**
     * 将备注图片链接拆分--获取图片url
     *
     * @param remarkPhotos
     * @return
     */
    public static List<String> remarkPhotoUrl2List(String remarkPhotos) {
        if (TextUtils.isEmpty(remarkPhotos)) return null;
        String[] split = remarkPhotos.split(",");
        return Arrays.asList(split);
    }

    /**
     * 将图片链接和图片名称分离
     *
     * @param str
     * @return
     */
    public static String splitPhotoUri(String str) {
        return str.split("\\|")[0];
    }

    public static String splitPhotoName(String str) {
        return str.split("\\|")[1];
    }

    /**
     * 替换手机后面中间四位
     *
     * @param phoneNumber
     * @return
     */
    public static String convertPhoneNum(String phoneNumber) {
        return convertPhoneNum(phoneNumber, "");
    }

    public static String convertPhoneNum(String phoneNumber, String def) {
        if (TextUtils.isEmpty(phoneNumber)) return def;
        String num3 = phoneNumber.substring(0, 3);
        String num4 = phoneNumber.substring(7, phoneNumber.length());
        return num3 + "****" + num4;
    }

    /**
     * 验证文本是否包含指定的特殊字符
     *
     * @param msg
     * @return
     */
    public static boolean isContainSpecialCharacter(String msg) {
        String regEx = "[`【】☺♛✔♞]";//&=|*+?!！<>：—。，.、~()（）—+|':;',
        Pattern p = Pattern.compile(regEx);
        Matcher m1 = p.matcher(msg);
        return m1.find();
    }

    /**
     * 判断是否是中文、字母、数字和指定字符
     *
     * @param content
     * @return
     */
    public static boolean isChinessCharNum(String content) {
        String regex = "^[a-zA-Z0-9\u4E00-\u9FA5,。.\\+\\-\\*/_=!?]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(content);
        return match.matches();
    }

    /**
     * 判断是否是中文
     */
    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;

        return flg;
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    public static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 将图片连接和名字拼接
     *
     * @param galleryUrls
     * @param photoNames
     * @return
     */
    public static String connectUrlAndPhotoName(ArrayList<String> galleryUrls, ArrayList<String> photoNames) {
        int size = galleryUrls.size();
        if (galleryUrls != null && photoNames != null && size > 0 && photoNames.size() > 0 && size == photoNames.size()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                if (i < size - 1) {
                    sb.append(galleryUrls.get(i)).append("|").append(photoNames.get(i)).append(",");
                } else if (i == size - 1) {
                    sb.append(galleryUrls.get(i)).append("|").append(photoNames.get(i));
                }
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * 格式化金额
     *
     * @param amount
     * @return
     */
    public static String formatAmount(double amount) {
        double a = amount / 100;
        return new DecimalFormat(",##0.00").format(a);
    }

    public static String calPercent(double y, double z) {
        String percent = "";// 接受百分比的值
        double baiy = y * 1.0;
        double baiz = z * 1.0;
        double fen = baiy / baiz;
// NumberFormat nf = NumberFormat.getPercentInstance();注释掉的也是一种方法
// nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
        DecimalFormat df1 = new DecimalFormat("##0.0%");
// ##.00%
// 百分比格式，后面不足2位的用0补齐
// percent=nf.format(fen);
        percent = df1.format(fen);
        //System.out.println(fen + "---" + percent);
        return percent;
    }

    //--------------- comment ---------------------
    /**
     * Desction:String工具类
     * Author:pengjianbo
     * Date:15/9/17 下午4:22
     */
    /**
     * is null or its length is 0 or it is made by space
     * <p/>
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * get length of CharSequence
     * <p/>
     * <pre>
     * length(null) = 0;
     * length(\"\") = 0;
     * length(\"abc\") = 3;
     * </pre>
     *
     * @param str
     * @return if str is null or empty, return 0, else return {@link CharSequence#length()}.
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * null object to empty string
     * <p/>
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param str
     * @return
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    /**
     * capitalize first letter
     * <p/>
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
                .append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * encoded in utf-8
     * <p/>
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     *
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * get innerHtml from href
     * <p/>
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     *
     * @param href
     * @return <ul>
     * <li>if href is null, return ""</li>
     * <li>if not match regx, return source</li>
     * <li>return the last string that match regx</li>
     * </ul>
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    /**
     * process special char in html
     * <p/>
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     *
     * @param source
     * @return
     */
    public static String htmlEscapeCharsToString(String source) {
        return StringUtils.isEmpty(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * transform half width char to full width char
     * <p/>
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * transform full width char to half width char
     * <p/>
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * 数据库字符转义
     *
     * @param keyWord
     * @return
     */
    public static String sqliteEscape(String keyWord) {
        keyWord = keyWord.replace("/", "//");
        keyWord = keyWord.replace("'", "''");
        keyWord = keyWord.replace("[", "/[");
        keyWord = keyWord.replace("]", "/]");
        keyWord = keyWord.replace("%", "/%");
        keyWord = keyWord.replace("&", "/&");
        keyWord = keyWord.replace("_", "/_");
        keyWord = keyWord.replace("(", "/(");
        keyWord = keyWord.replace(")", "/)");
        return keyWord;
    }

    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        return str.matches("[0-9]+");
    }

    /**
     * 判断字符串是否含有日文
     *
     * @param mixed
     * @return
     */
    public static boolean isJapan(String mixed) {
        // write your code here
        Set<Character.UnicodeBlock> japaneseUnicodeBlocks = new HashSet<Character.UnicodeBlock>() {{
            add(Character.UnicodeBlock.HIRAGANA);
            add(Character.UnicodeBlock.KATAKANA);
            add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
        }};

        for (char c : mixed.toCharArray()) {
            if (japaneseUnicodeBlocks.contains(Character.UnicodeBlock.of(c))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 对象转小数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static float toFloat(String obj) {
        try {
            return Float.parseFloat(obj);
        } catch (Exception e) {
        }
        return 0f;
    }


    /**
     * 对象转小数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line + "<br>");
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    /***
     * 截取字符串
     *
     * @param start 从那里开始，0算起
     * @param num   截取多少个
     * @param str   截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int leng = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > leng) {
            start = leng;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > leng) {
            end = leng;
        }
        return str.substring(start, end);
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @return
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }

    /**
     * 获取年月日的数组
     *
     * @return
     */
    public static int[] getCurrentDate() {
        int[] dateBundle = new int[3];
        String[] temp = getDataTime("yyyy-MM-dd").split("-");

        for (int i = 0; i < 3; i++) {
            try {
                dateBundle[i] = Integer.parseInt(temp[i]);
            } catch (Exception e) {
                dateBundle[i] = 0;
            }
        }
        return dateBundle;
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 文字复制
     *
     * @param mContext
     * @param stripped
     */
    public static void copy(Context mContext, String stripped) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard =
                    (android.text.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(stripped);
        } else {
            android.content.ClipboardManager clipboard =
                    (android.content.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("content", stripped);
            clipboard.setPrimaryClip(clip);
        }
        //TUtils.showToast("复制成功");
    }


    /**
     * 计算TextView中每个字符的宽度
     *
     * @param tv
     * @return
     */
    public static float getCharacterWidth(TextView tv) {
        if (null == tv) return 0f;
        return getCharacterWidth(tv.getText().toString(), tv.getTextSize()) * tv.getScaleX();
    }

    //获取每个字符的宽度主方法：
    private static float getCharacterWidth(String text, float size) {
        if (null == text || "".equals(text))
            return 0;
        float width = 0;
        Paint paint = new Paint();
        paint.setTextSize(size);
        float text_width = paint.measureText(text);//得到总体长度
        width = text_width / text.length();//每一个字符的长度
        return width;
    }

    public static String getStringFromInteger(int source) {
        return String.valueOf(source);
    }

    //----------------------start---------------------

    /**
     * 截取字符串 中间某一串字符，截取startStr，endStr之间的值
     *
     * @param source
     * @return
     */
    public static String getResultStr(String source, String startStr, String endStr) {
        String goods_sn = "";

        int startIndex = 0;
        int endIndex = 0;
        if (!TextUtils.isEmpty(source)) {

            startIndex = getGoodsSnStartIndex(source, startStr);
            endIndex = getGoodsSnEndIndex(source, endStr);

            if (startIndex < endIndex) {
                goods_sn = source.substring(startIndex, endIndex);
            }

        }

        return goods_sn;
    }

    public static int getGoodsSnEndIndex(String msg, String endStr) {
        int i = msg.indexOf(endStr);
        return i;
    }

    public static int getGoodsSnStartIndex(String msg, String startStr) {

        int i = msg.indexOf(startStr);

        return i + startStr.length();
    }

    public static String combineStr(StringBuilder sb, String... str) {

        if (sb == null) return "";

        sb.delete(0, sb.length());

        for (int i = 0; i < str.length; i++) {
            sb.append(str[i]);
        }

        return sb.toString();
    }

    //----------------------end---------------------


    //----------------------start---------------------


    public static String getDaysEn(String day) {
        return toEnglish(StringUtils.toInt(day));
    }

    private static String toEnglish(int num) {
        if (num == 0) {
            return "zero";
        }
        StringBuffer buffer = new StringBuffer();
        if (num >= 100) {
            buffer.append(pickHunder(num));
            if (num % 100 != 0) {
                buffer.append(" and ");
            }
            num -= (num / 100) * 100;
        }
        boolean largerThan20 = false;
        if (num >= 20) {
            largerThan20 = true;
            buffer.append(pickTies(num));
            num -= (num / 10) * 10;
        }
        if (!largerThan20 && num > 10) {
            buffer.append(pickTeens(num));
            num = 0;
        }
        if (num > 0) {
            String bit = pickBits(num);
            if (largerThan20) {
                buffer.append(" ");
            }
            buffer.append(bit);
        }
        return buffer.toString();
    }

    private static String pickHunder(int num) {
        int hunder = num / 100;

        int reuslt = hunder - 1 > BITS.length ? BITS.length : hunder - 1;

        return BITS[reuslt] + " hunder";
    }

    private static String pickTies(int num) {
        int ties = num / 10;
        return TIES[ties - 2];
    }

    private static String pickTeens(int num) {
        return TEENS[num - 11];
    }

    private static String pickBits(int num) {
        return BITS[num - 1];
    }

    private static final String[] BITS = {"one", "two", "three", "four", "five",
            "six", "seven", "eight,", "nine", "ten"};

    private static final String[] TEENS = {"eleven", "twelf", "thirteen",
            "fourteen", "fifteen", "sixteen", "seveteen", "eighteen", "nighteen"};

    private static final String[] TIES = {"twenty", "thrity", "forty", "fifty",
            "sixty", "seventy", "eighty", "ninety"};
    //----------------------end---------------------

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    public static String star4middle(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 判断某一旧值与新值是否变化
     *
     * @return
     */
    public static boolean isChange(String lastInput, String currentInput) {
        boolean isChange = false;
        if (StringUtils.isEmpty(lastInput) && !StringUtils.isEmpty(currentInput)) {
            isChange = true;
        }
        if (!StringUtils.isEmpty(lastInput) && !lastInput.equals(currentInput)) {
            isChange = true;
        }
        return isChange;
    }
}
