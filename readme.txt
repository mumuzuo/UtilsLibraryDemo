   //1.---显示选择的每张图片时 设置每个imageView 的大小
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = ScreenUtils.getScreenWidth(mContext) / 3;
        params.width = ScreenUtils.getScreenWidth(mContext) / 3;
        holder.itemView.setLayoutParams(params);


   //2.---在GalleryPickActivity中添加去掉选择的方法
   //adapter---去掉选中的图片
    holder.deleteImg.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mData.remove(mData.get(position));
                   Message message = new Message();
                   message.what = 1102;
                   handler.sendMessage(message);
               }
           });
    //Activity---去掉已选图片同时要更新galleryConfig中选中的图片集合
    if (photoAdapter!=null)photoAdapter.notifyDataSetChanged();
    if (galleryConfig!=null)galleryConfig.getBuilder().pathList(path).build();


    //3.---使用封装的微信支付的时候
    weiXinConfig = new WeiXinConfig.Builder()
                   .appID("wx51a63dc4c2571961")            //微信开放平台创建应用获得的APP_ID
                   .appSecret("10c5dba23092441d6d76b64874a9ae3b")               //微信开放平台创建应用获得的AppSecret
                   .mchID("1426344202")            //获取应用支付功能后对应的商户号
                   .apiKEY("0123456789fengyedainzi1426344202")         //API密钥，在商户平台设置
                   .notifyUrl("")                //接收微信支付异步通知回调的地址，后台提供
                   .build();

    findViewById(R.id.wx_pay).setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                 WXPayUtil.getInstance(WXPayActivity.this)
                           .setWeiXinConfig(weiXinConfig)
                           .startPay("微信支付", "201304566464" , "0.01");
                 }
    });

    不要忘记在调用微信支付的activity里的属性<data android:scheme="APP_ID"/>，APP_ID同上方配置文件。

    //4.-----日历控件的颜色配置在CalendarViewPager类中

    //5.-----常用工具类中包含下列方法

    CommUtils--通用工具类：
    1.base64加密---getBase64(String str)
    2.MD5加密---md5(String rawPass)
    3.检查URl是否有效(子线程中执行)---checkURL(String url)
    4.检查密码是否有效，英文字母大小写加数字6-16位---isPassword(String pwd)
    5.设置EditText是否可编辑---setEditTextEditable(EditText editText, boolean value)
    6.检查电话号码是否有效---isPhoneValid(String phone)
    7.检查网络是否连接---isNetWorkConnected(Context context)
    8.隐藏软键盘---hideKeyboard(Activity context, View view)
    9.切换软键盘---hideKeyboard(Activity context, View view)
    10.检测Sdcard是否存在---isExitsSdcard()
    11.获取sd卡路径---getSDPath()
    12.计算比率(保留一位小数，可修改)---mathRate(double num, double total)

    WindowsBgAlpha--背景透明度：
    1.添加屏幕的背景透明度,在给定的时间内渐变---
           backgroundAlpha(float startBgAlpha, final float endBgAlpha, long time)

    ZTimeUtils--时间工具类：
    1.获取系统的当前时间以yyyy-MM-dd HH:mm:ss格式返回---getTimeYMDHMS()
    2.获取系统的当前日期以yyyy-MM-dd格式返回---getTodayYMD()
    3.将已有long型时间以yyyy-MM-dd HH:mmss格式返回---long2TimeYMDHMS(Long time)
    4.将已有long型时间以yyyy-MM-dd格式返回---long2DateYMD(Long time)
    5.将已有long型时间以mm:ss格式返回---long2TimeMS(Long time)
    6.把HH:mm:ss格式的字符串转化为long型时间，秒为单位---time2Long(String timeStr)
    7.把yyyy-MM-dd格式的字符串转化为long型时间，天为单位---day2Long(String dateStr)
    8.把yyyy-MM-dd格式的字符串转化为long型时间，秒为单位---dayTime2Long(String dateTimeStr)


    //--上传Github密码123456
