package cn.daenx.myadmin.test;


import cn.daenx.myadmin.common.utils.MyUtil;

public class testRun {
    public static void main(String[] args) {
        String data = "test中文";
        String key = "123123123";
        String encrypt = MyUtil.sm4Encrypt(data, key);
        System.out.println(encrypt);
        String decrypt = MyUtil.sm4Decrypt(encrypt, key);
        System.out.println(decrypt);
    }


}
