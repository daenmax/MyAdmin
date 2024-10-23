package cn.daenx.framework.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

/**
 * AES加解密工具类
 *
 * @author DaenMax
 */
public class AesUtil {

    /**
     * ECB模式每次它需要一个固定长度为128位的密钥，生成固定密文。
     * CBC模式既需要一个固定长度为256位的密钥，又需要一个随机数作为IV参数，这样对于同一份明文，每次生成的密文都不同。
     * 在线测试：https://the-x.cn/cryptography/Aes.aspx
     * <p>
     * 由于用到了pkcs7填充，所以需要添加依赖
     * <dependency>
     * <groupId>org.bouncycastle</groupId>
     * <artifactId>bcprov-jdk15to18</artifactId>
     * <version>1.78.1</version>
     * </dependency>
     *
     * @param args
     */

    public static void main(String[] args) {
        String key = genkey();
//        String key = "40e9cb4a0b7b778c881ca3e48416a5bc";
        System.out.println(key.substring(0, 16));
        System.out.println(key);
        String enc = encrypt("你好啊", key, true);
        System.out.println(enc);
        String dec = decrypt(enc, key);
        System.out.println(dec);

    }

    /**
     * 创建一个秘钥
     * 256bits，即32个字符
     *
     * @return
     */
    public static String genkey() {
        byte[] key = SecureUtil.generateKey("AES/CBC/PKCS7Padding").getEncoded();
        return HexUtil.encodeHexStr(key);
    }

    /**
     * 加密
     * AES/CBC/PKCS7Padding
     * IV是秘钥前16位
     *
     * @param content
     * @param key
     * @param isBase64 是否输出base64格式，否则输出16进制
     * @return
     */
    public static String encrypt(String content, String key, Boolean isBase64) {
        AES aes = new AES("CBC", "PKCS7Padding", key.getBytes(), key.substring(0, 16).getBytes());
        if (isBase64) {
            return aes.encryptBase64(content);
        }
        return aes.encryptHex(content);
    }

    /**
     * 解密
     * AES/CBC/PKCS7Padding
     * IV是秘钥前16位
     *
     * @param content 支持base64格式、16进制
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {
        AES aes = new AES("CBC", "PKCS7Padding", key.getBytes(), key.substring(0, 16).getBytes());
        String decrypt = aes.decryptStr(Base64.decode(content));
        return decrypt;
    }
}
