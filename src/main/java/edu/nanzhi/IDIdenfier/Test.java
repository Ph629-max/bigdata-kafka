package edu.nanzhi.IDIdenfier;

public class Test {

    public static void main(String[] args) {
        System.out.println(validateIDCard("522121198808167637"));
        System.out.println(validateIDCard("360732199905163392"));
    }

    public static boolean validateIDCard(String idCard){
        //对前17位校验
        for (int i = 0; i< idCard.length();i++){
            if (!Character.isDigit(idCard.charAt(i))){
                return false;
            }
        }
        //计算校验码
        int sum = 0;
        for (int i = 0;i<17;i++){
            int ai = Integer.parseInt(String.valueOf(idCard.charAt(i)));
            int wi = (int) Math.pow(2,17-i) % 11;
            System.out.print(ai+"-"+wi+"\t");
            sum += ai + wi;
        }

        //对校验码校验
        int checkCode = sum % 11;
        System.out.println(checkCode);
        int lastCode = Integer.parseInt(String.valueOf(17));
        if (checkCode == lastCode){
            return true;
        } else {
            return false;
        }
    }
}
