package cn.lsieun.knowthyself.util.weixin;

import java.util.Date;

public class SignUtilTest {
    public static void main(String[] args) {
        String signature = "02602063A8B43EADBE849818EDAB995D1DC70E64";
        long currentTimeMillis = System.currentTimeMillis();
        //String timestamp = String.valueOf(currentTimeMillis);
        String timestamp = String.valueOf(1524636246016L);
        String nounce = "HelloWorld";

        boolean flag = SignUtil.checkSignature(signature,timestamp,nounce);
        System.out.println("signature = " + signature);
        System.out.println("currentTimeMillis = " + currentTimeMillis);
        System.out.println("timestamp = " + timestamp);
        System.out.println("nounce = " + nounce);
        System.out.println("flag = " + flag);
    }
}