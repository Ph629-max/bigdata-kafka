package edu.nanzhi;

/**
 * 用户实体
 */
public class User {
    public int uid;
    public String uname;
    public int age;
    public String sex;
    //生成构造器
    public User(){

    }
    public User(int uid, String uname, int age, String sex) {
        this.uid = uid;
        this.uname = uname;
        this.age = age;
        this.sex = sex;
    }
    //生成4个字段的getter和setter方法
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    //生成toString方法

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
