package edu.nanzhi.IDIdenfier;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份验证工具类
 */
public class ValidateIDCardUtil1 {
    // 定义地区区域映射
    final static Map<Integer, String> zoneNum = new HashMap<>();

    // 静态初始化地区映射
    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "外国");
    }

    final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 身份证验证
     *
     * @param cerNum 身份证号码
     * @return 是否合法
     */
    public static boolean isIDCard(String cerNum) {
        // 判断位数
        if (cerNum == null || (cerNum.length() != 15 && cerNum.length() != 18))
            return false;

        // 将身份证号转大写，同时转换为字符数组
        char[] cs = cerNum.toUpperCase().toCharArray();
        // 定义一个身份证号中每个字符和0的差值的累加器
        int power = 0;
        // 校验位数
        for (int i = 0; i < cs.length; i++) {
            if (i == cs.length - 1 && cs[i] == 'X')
                break;
            if (cs[i] < '0' || cs[i] > '9')
                return false;
            if (i < cs.length - 1) {  // 正常情况
                //power += (cs[i] - '0') * POWER_LIST[i];
                power += Integer.parseInt(String.valueOf(cs[i]))+((int)Math.pow(2,17-i)%11);
            }
        }
        //522121198810106736
        System.out.println(cs[cs.length - 1]+"-"+power+"-"+power % 11+"-"+PARITYBIT[power % 11]);
        // 区位校验
        if (!zoneNum.containsKey(Integer.valueOf(cerNum.substring(0, 2)))) {
            return false;
        }
        // 检验年份
        String year = cerNum.length() == 15 ? "19" + cerNum.substring(6, 8) : cerNum.substring(6, 10);
        int iyear = Integer.parseInt(year);
        if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR)) {
            return false; // 身份证不能小于1900年，不能超过今年的值
        }
        // 检验月份
        String month = cerNum.length() == 15 ? cerNum.substring(8, 10) : cerNum.substring(10, 12);
        int imonth = Integer.parseInt(month);
        if (imonth < 1 || imonth > 12) {
            return false;
        }
        // 检验日期
        String day = cerNum.length() == 15 ? cerNum.substring(10, 12) : cerNum.substring(12, 14);
        int iday = Integer.parseInt(day);
        if (iday < 1 || iday > 31) {
            return false;
        }
        // 如果是15位身份证号，则直接返回true
        if (cerNum.length() == 15)
            return true;

        // 计算校验位
        return cs[cs.length - 1] == PARITYBIT[power % 11];
    }

    public static void main(String[] args) {
        System.out.println(ValidateIDCardUtil1.isIDCard("522121198810106736")); // 测试不对
        System.out.println(ValidateIDCardUtil1.isIDCard("362330199410107628"));
        System.out.println(ValidateIDCardUtil1.isIDCard("362330200102233919"));
        System.out.println(ValidateIDCardUtil1.isIDCard("360732199905163392"));
        System.out.println(ValidateIDCardUtil1.isIDCard("36073219990516339X"));
        System.out.println(ValidateIDCardUtil1.isIDCard("360732199806183392"));
    }
}