package edu.nanzhi.IDIdenfier;

/**
 * 申请信息实体
 */
public class ApplicationInfo {
    private int id;
    private String idcard;
    private long applayTime;
    //生成getter和setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public long getApplayTime() {
        return applayTime;
    }

    public void setApplayTime(long applayTime) {
        this.applayTime = applayTime;
    }
}
