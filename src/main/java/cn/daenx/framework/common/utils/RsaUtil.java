package cn.daenx.framework.common.utils;

import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.util.HashMap;
import java.util.Map;

/**
 * RSA加解密工具类
 *
 * @author DaenMax
 */
public class RsaUtil {
    /**
     * 在线测试：https://the-x.cn/cryptography/Rsa.aspx
     *
     * @param args
     */
    public static void main(String[] args) {
        Map<String, String> map = genKey();
        String content = "你好啊";
        String publickeyobj = map.get("publicKey");
        String privatekeyobj = map.get("privateKey");
        System.out.println(publickeyobj);
        System.out.println(privatekeyobj);
        String encrypt = encrypt(content, publickeyobj, true);
        System.out.println("加密后:" + encrypt);
        System.out.println("解密后:" + decrypt(encrypt, privatekeyobj));
    }

    /**
     * 创建一对公私钥
     * 1024bits
     */
    public static Map<String, String> genKey() {
        RSA rsa = new RSA(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue());
        Map<String, String> map = new HashMap<>();
        map.put("privateKey", rsa.getPrivateKeyBase64());
        map.put("publicKey", rsa.getPublicKeyBase64());
        return map;
    }

    /**
     * 加密
     * RSA/ECB/PKCS1Padding
     *
     * @param content
     * @param publicKey
     * @param isBase64  是否输出base64格式，否则输出16进制
     * @return
     */
    public static String encrypt(String content, String publicKey, Boolean isBase64) {
        RSA rsa = new RSA(null, publicKey);
        if (isBase64) {
            return rsa.encryptBase64(content, KeyType.PublicKey);
        }
        return rsa.encryptHex(content, KeyType.PublicKey);
    }

    /**
     * 解密
     * RSA/ECB/PKCS1Padding
     *
     * @param content    支持base64格式、16进制
     * @param privateKey
     * @return
     */
    public static String decrypt(String content, String privateKey) {
        RSA rsa = new RSA(privateKey, null);
        return rsa.decryptStr(content, KeyType.PrivateKey);
    }
}
