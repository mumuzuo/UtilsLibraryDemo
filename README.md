# UtilsLibraryDemo
开发常用工具及相关库代码整

# 工具库UtilsLibrary的引用：
>compile 'com.cnbs.library:UtilsLibrary:0.0.1'

## 1、支付宝、微信支付封装
## 2、兼容Android7.0 相机相册选择
## 3、自定义记事日历
## 4、常用自定义控件、工具方法

、、、
/**
 * 常用工具类
 * Created by Administrator on 2017/2/28.
 */

public final class CommUtils {

    /**
     * 1.base64加密
     * @param str
     * @return
     */
    public static String getBase64(String str) {
        String result = "";
        if( str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 2.对请求参数进行MD5加密
     * @param rawPass
     * @return
     */

    public static String md5(String rawPass) {
        byte[] hash;
        String saltedPass = mergePasswordAndSalt(rawPass, "CNBSEXAM", false);
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            hash = messageDigest.digest(saltedPass.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported!");
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    protected static String mergePasswordAndSalt(String password, Object salt,
                                                 boolean strict) {
        if (password == null) {
            password = "";
        }
        if (strict && (salt != null)) {
            if ((salt.toString().lastIndexOf("{") != -1)
                    || (salt.toString().lastIndexOf("}") != -1)) {
                throw new IllegalArgumentException(
                        "Cannot use { or } in salt.toString()");
            }
        }
        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }

    /**
     * 3.检查URl是否有效,注意该方法要在子线程中执行
     * @param url
     * @return
     */
    public static boolean checkURL(String url){
        boolean value=false;
        try {
            HttpURLConnection conn=(HttpURLConnection)new URL(url).openConnection();
            int code=conn.getResponseCode();
            if(code!=200){
                value=false;
            }else{
                value=true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 4.检查密码是否有效，英文字母大小写加数字6-16位
     * @param pwd
     * @return
     */
    public static boolean isPassword(String pwd){
        Pattern p = Pattern.compile("^[A-Za-z0-9]{6,16}");
        Matcher m;
        try {
            m = p.matcher(pwd);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return m.matches();
    }

    /**
     * 5.设置edittext是否可编辑
     * @param editText
     * @param value
     */
    public static void setEditTextEditable(EditText editText, boolean value){
        if (value) {
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            editText.setGravity(Gravity.LEFT);
        }else {
            editText.setFocusableInTouchMode(false);
            editText.clearFocus();
            editText.setGravity(Gravity.CENTER);
        }
    }

    /**
     * 6.检查电话号码是否有效
     * @param phone
     * @return
     */
    public static boolean isPhoneValid(String phone) {
        Pattern p = Pattern.compile("^(1[3,4,5,7,8][0-9])\\d{8}$");
        Matcher m;
        try {
            m = p.matcher(phone);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return m.matches();
    }

    /**
     * 7.检查网络是否连接
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 8.隐藏软键盘
     * @param context
     */
    public static void hideKeyboard(Activity context, View view) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 9.切换软键盘
     * @param context
     */
    public static void switchKeyboardShowHide(Activity context , View view) {
        ((InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE))
                .toggleSoftInputFromWindow(view.getWindowToken(),0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 10.检测Sdcard是否存在
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 11.获取sd卡路径
     * @return
     */
    public static String getSDPath() {
        String sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();// 获取跟目录
        }
        return sdDir.toString();
    }


    /**
     * 12.计算比率
     * @param num
     * @param total
     * @return
     */
    public static String mathRate(double num, double total){
        DecimalFormat df = new DecimalFormat("0.0%");
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = num / total;
        return df.format(accuracy_num);
    }

    /**
     * 13.身份证号验证
     * @param mobiles
     * @return
     */
    public static boolean isIdCardNumber(String mobiles) {
        Pattern p = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher m;
        try {
            m = p.matcher(mobiles);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return m.matches();
    }

}
、、、

