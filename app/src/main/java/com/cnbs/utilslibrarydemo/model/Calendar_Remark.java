package com.cnbs.utilslibrarydemo.model;


import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/3/6.
 */

public class Calendar_Remark extends DataSupport {
    private String time;        //  记录的时间
    private String remark;      //  记录的内容

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
