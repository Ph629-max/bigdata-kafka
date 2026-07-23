package edu.nanzhi.IDIdenfier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份验证的工具类
 */
public class ValidateIDCardUtil {
    //定义区域
    final static Map<Integer,String> zoneNum = new HashMap<Integer,String>();
    //静态身份证前2位和地区的映射
    static {
        zoneNum.put(11,"北京");
        zoneNum.put(12,"天津");
        zoneNum.put(13,"河北");
        zoneNum.put(14,"山西");
        zoneNum.put(15,"内蒙古");
        zoneNum.put(21,"辽宁");
        zoneNum.put(22,"吉林");
        zoneNum.put(23,"黑龙江");
        zoneNum.put(31,"上海");
        zoneNum.put(32,"江苏");
        zoneNum.put(33,"浙江");
        zoneNum.put(34,"安徽");
        zoneNum.put(35,"福建");
        zoneNum.put(36,"江西");
        zoneNum.put(37,"山东");
        zoneNum.put(41,"河南");
        zoneNum.put(42,"湖北");
        zoneNum.put(43,"湖南");
        zoneNum.put(44,"广东");
        zoneNum.put(45,"广西");
        zoneNum.put(46,"海南");
        zoneNum.put(50,"重庆");
        zoneNum.put(51,"四川");
        zoneNum.put(52,"贵州");
        zoneNum.put(53,"云南");
        zoneNum.put(54,"西藏");
        zoneNum.put(61,"陕西");
        zoneNum.put(62,"甘肃");
        zoneNum.put(63,"青海");
        zoneNum.put(64,"新疆");
        zoneNum.put(71,"台湾");
        zoneNum.put(81,"香港");
        zoneNum.put(82,"澳门");
        zoneNum.put(91,"外国");
    }
    final static int [] PARITYBIT = {'1','0','X','9','8','7','6','5','4','3','2'};
    final static int [] POWER_LIST = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};

    /**
     * 身份证验证
     * @param cerNum
     * @return
     */
    public static boolean isIDCard(String cerNum) {
        //判断位数
        if(cerNum == null || (cerNum.length() !=15 && cerNum.length()!=18))
            return false;
        //将身份证号转大写，同时转数组
        char[] cs = cerNum.toUpperCase().toCharArray();
        //定义一个身份证号中没个字符和0的差值的累加器
        int power = 0;
        //校验位数
        for (int i=0;i<cs.length;i++){
            if(i==cs.length-1 && cs[i] == 'X')
                break;
            if (cs[i] < '0' || cs[i] > '9')
                return false;
            if (i < cs.length -1){  //正常情况
                //POWER_LIST = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
                power+=(cs[i]-'0')*POWER_LIST[i];
            }
        }
        //区位校验 --->取前2位和zoneNum进行key判断
        if(!zoneNum.containsKey(Integer.valueOf(cerNum.substring(0,2)))){
            return false;
        }
        //检验年份
        String year = null;
        year = cerNum.length() == 15 ? getIdcardCalendar(cerNum):cerNum.substring(6,10);
        final int iyear = Integer.parseInt(year);
        //判断年份是否正确
        if(iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR)){
            return false; //身份证不能大于1900年，不能超过今年的值
        }
        //检验月份
        String month = cerNum.length() == 15 ? cerNum.substring(8,10) : cerNum.substring(10,12);
        int imonth = Integer.parseInt(month);
        //判断month是否正确
        if(imonth < 1 || imonth > 12){
            return false;
        }
        //校验日期
        String day = cerNum.length() == 15 ? cerNum.substring(10,12) : cerNum.substring(12,14);
        int iday = Integer.parseInt(day);
        //判断日期
        if(iday < 1 || iday > 31){
            return false;
        }
        //如果15位则直接返回
        if(cerNum.length() == 15)
            return true;
        return cs[cs.length-1] == PARITYBIT[power%11];  //PARITYBIT的长度是11位
    }

    private static String getIdcardCalendar(String cerNO){
        //获取出生年月日
        String birthday = cerNO.substring(6, 12);
        SimpleDateFormat ft = new SimpleDateFormat("yyMMdd");
        Date birthdate = null;
        try {
            birthdate = ft.parse(birthday);
        } catch (ParseException e){
            e.printStackTrace();
        }
        Calendar cday = Calendar.getInstance();
        cday.setTime(birthdate);
        String year = String.valueOf(cday.get(Calendar.YEAR));
        return year;
    }

    public static void main(String[] args) {

    }
}