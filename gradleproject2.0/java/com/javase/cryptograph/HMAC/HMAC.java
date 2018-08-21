package com.javase.cryptograph.HMAC;
/**
 HMAC(Hash Message Authentication Code，散列消息鉴别码，基于密钥的Hash算法的认证协议。
 消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个 标识鉴别消息的完整性。
 使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，然后传输。接收方利用与发送方共享的密钥进行鉴别认证 等。
HMAC(Hash Message Authentication Code，散列消息鉴别码，基于密钥的Hash算法的认证协议。
消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。
使用一个密钥生成一个固定大小的小数据块，
即MAC，并将其加入到消息中，然后传输。接收方利用与发送方共享的密钥进行鉴别认证等。
 */


import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 基础加密组件
 */  
public  class HMAC {
    public String secret = "ndE2jdZNFixH9G6Aidsfyf7lYT3PxW";//密钥
    public String message="i am  中国人 123";
    /**
     * sha256_HMAC加密
     */
    @Test
    public void sha256_HMAC() {
        String hash = "";
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKey secret_key = new SecretKeySpec(secret.getBytes(), "hmacSHA256");
            mac.init(secret_key);
            byte[] bytes = mac.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
            System.out.println("HmacSHA256:"+hash);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
    }
    /**
     * md5_HMAC加密
     */
    @Test
    public  void md5_HMAC() {
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "hmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(secretKey);
            mac.update(message.getBytes("UTF-8"));
            byte[] bytes=mac.doFinal();
            System.out.print("HmacMD5:"+encodeBase64(bytes));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  String encodeBase64(byte[] source) throws Exception{
        return new String(Base64.encodeBase64(source), "UTF-8");
    }


    public  String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

}