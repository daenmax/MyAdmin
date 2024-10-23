package cn.daenx.framework.common.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SM4;

/**
 * SM4加解密工具类
 *
 * @author DaenMax
 */
public class Sm4Util {

    /**
     * 在线测试：https://the-x.cn/cryptography/Sm4.aspx
     *
     * @param args
     */
    public static void main(String[] args) {
        String key = genkey();
//        String key = "40e9cb4a0b7b778c881ca3e48416a5bc";
//        System.out.println(key.substring(0, 16));
        System.out.println(key);
        String enc = encrypt("你好啊", key, true);
        System.out.println(enc);
        String dec = decrypt(enc, key);
        System.out.println(dec);

    }

    /**
     * 创建一个秘钥
     * 128bits，即16个字符
     *
     * @return
     */
    public static String genkey() {
        byte[] key = SecureUtil.generateKey("SM4/CBC/PKCS7Padding", 64).getEncoded();
        return HexUtil.encodeHexStr(key);
    }

    /**
     * 加密
     * SM4/CBC/PKCS7Padding
     * IV和秘钥一致
     *
     * @param content
     * @param key
     * @param isBase64 是否输出base64格式，否则输出16进制
     * @return
     */
    public static String encrypt(String content, String key, Boolean isBase64) {
        SM4 sm4 = new SM4("CBC", "PKCS7Padding", key.getBytes(), key.substring(0, 16).getBytes());
        if (isBase64) {
            return sm4.encryptBase64(content);
        }
        return sm4.encryptHex(content);
    }

    /**
     * 解密
     * SM4/CBC/PKCS7Padding
     * IV和秘钥一致
     *
     * @param content 支持base64格式、16进制
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {
        SM4 sm4 = new SM4("CBC", "PKCS7Padding", key.getBytes(), key.substring(0, 16).getBytes());
        String decryptStr = sm4.decryptStr(content, CharsetUtil.CHARSET_UTF_8);
        return decryptStr;
    }
}
