package com.javase.cryptograph;

import java.security.MessageDigest;
  
import javax.crypto.Mac; 
import javax.crypto.SecretKey; 
import javax.crypto.spec.SecretKeySpec; 
  
import org.apache.commons.codec.binary.Base64; 
  
/** 
 * 单向加密（非可逆加密） 
 * @author lenovo 
 * 
 */
public class OneWayEncryption { 
  static final String ALGORITHM_MD5 = "MD5"; 
  static final String ALGORITHM_SHA = "SHA"; 
  /** 
   * MAC算法可选以下多种算法 
   * <pre> 
   * HmacMD5 
   * HmacSHA1 
   * HmacSHA256 
   * HmacSHA384 
   * HmacSHA512 
   * </pre> 
   */
  static final String ALGORITHM_MAC = "HmacMD5"; 
  /** 密钥 **/
  static final String MAC_KEY = "abcdef"; 
    
  public static void main(String[] args) throws Exception { 
    String source = "我是程序猿！我很骄傲！"; 
    // MD5加密 
    printBase64(encryptionMD5(source)); 
    // SHA加密 
    printBase64(encryptionSHA(source)); 
    // HMAC加密 
    printBase64(encryptionHMAC(source)); 
  } 
  
  static void printBase64(byte[] out) throws Exception { 
    System.out.println(encodeBase64(out)); 
  } 
  
  /** 
   * MD5加密 
   * @param source 
   * @return 
   * @throws Exception 
   */
  static byte[] encryptionMD5(String source) throws Exception { 
    MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5); 
    md.update(source.getBytes("UTF-8")); 
    return md.digest(); 
  } 
    
  /** 
   * SHA加密 
   * @param source 
   * @return 
   * @throws Exception 
   */
  static byte[] encryptionSHA(String source) throws Exception { 
    MessageDigest md = MessageDigest.getInstance(ALGORITHM_SHA); 
    md.update(source.getBytes("UTF-8")); 
    return md.digest(); 
  } 
    
  /** 
   * HMAC加密 
   * @return 
   * @throws Exception 
   */
  static byte[] encryptionHMAC(String source) throws Exception { 
    SecretKey secretKey = new SecretKeySpec(MAC_KEY.getBytes("UTF-8"), ALGORITHM_MAC); 
    Mac mac = Mac.getInstance(ALGORITHM_MAC); 
    mac.init(secretKey); 
    mac.update(source.getBytes("UTF-8")); 
    return mac.doFinal(); 
  } 
    
  /** 
   * base64编码 
   * @param source 
   * @return 
   * @throws Exception 
   */
  static String encodeBase64(byte[] source) throws Exception{ 
    return new String(Base64.encodeBase64(source), "UTF-8"); 
  } 
  
} 