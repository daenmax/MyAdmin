package cn.daenx.myadmin.test;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.bouncycastle.util.encoders.Hex;

public class testRun {
    public static void main(String[] args) {
        String content = "test中文";
        SymmetricCrypto sm4 = new SymmetricCrypto("SM4", Hex.decode(MD5.create().digestHex("123456")));

        String encryptHex = sm4.encryptHex(content);
        System.out.println(encryptHex);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println(decryptStr);

    }




}
