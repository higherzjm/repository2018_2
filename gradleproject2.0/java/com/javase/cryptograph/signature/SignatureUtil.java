package com.javase.cryptograph.signature;

import com.javase.cryptograph.RSA.PublicKeyReader;

import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;  
import java.security.KeyPairGenerator;  
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 数据签名
 */
public class SignatureUtil{  

    /**得到产生的私钥/公钥对 
     * @return KeyPair
     */  
    public static KeyPair getKeypair(){  
      //产生RSA密钥对(myKeyPair)  
        KeyPairGenerator myKeyGen = null;  
        try {  
            myKeyGen = KeyPairGenerator.getInstance("RSA");  
            myKeyGen.initialize(1024);              
        } catch (NoSuchAlgorithmException e) {             
            e.printStackTrace();  
        }  
        KeyPair myKeyPair = myKeyGen.generateKeyPair();  
        return myKeyPair;  
    }  
    /**根据私钥和信息生成签名 
     * @param privateKey 
     * @param data 
     * @return 签名的Base64编码
     */  
    public static String getSignature(PrivateKey privateKey,String data){  
        Signature sign; 
        String res = "";
        try {
            sign = Signature.getInstance("MD5WithRSA");
            sign.initSign(privateKey);
            sign.update(data.getBytes());
            byte[] signSequ = sign.sign();
            res = Base64.getEncoder().encodeToString(signSequ);
        }catch(NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SignatureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;  
    }  

    /**验证签名 
     * @param publicKey 公钥的Base64编码 
     * @param sign 签名的Base64编码 
     * @param data 生成签名的原数据
     * @return 
     */  
    public static boolean verify(String publicKey, String sign, String data){  
        boolean res = true;
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicK = keyFactory.generatePublic(keySpec);

            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicK);
            signature.update(data.getBytes());
            res = signature.verify(Base64.getDecoder().decode(sign));
        }catch(NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SignatureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return res;  
    }



    public static void main(String[] args) {
        String data = "给我签名吧123abc！";
        /*(1)生成公钥和私钥对*/
        KeyPair keyPair = getKeypair();
        PublicKey publicKeyOrgin=keyPair.getPublic();
        PrivateKey privateKeyOrgin=keyPair.getPrivate();
        String publicKey = Base64.getEncoder().encodeToString(publicKeyOrgin.getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(privateKeyOrgin.getEncoded());
        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);
        /*(2)用私钥生成签名*/
        String signStr = getSignature(privateKeyOrgin,data);
        System.out.println("签名是：" + signStr);

        /*(3)验证签名*/
        System.out.println("验证签名的结果是：" + verify(publicKey,signStr,data));

    }
} 