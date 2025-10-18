package com.yupi.kafka.tools;


import org.apache.commons.codec.digest.DigestUtils;
/**
 * @Description TODO
 * @Author canyon.zhao
 * @Date 2025/10/17 17:30
 */
public class PasswordTools {
    public static void main(String[] args) {
//        String psd = DigestUtils.md5DigestAsHex("123456".getBytes());
//        System.out.println(psd);

        String md5 = DigestUtils.md5Hex("123456".getBytes());
        System.out.println(md5.length());
        System.out.println(md5);

        int hashCode = md5.toUpperCase().hashCode();
        int bucket = hashCode % 100;
        System.out.println("hashCode:" + hashCode + " bucket:" + bucket);
    }
}
