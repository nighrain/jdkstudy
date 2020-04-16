package com.example.demo.jwtDemo.util;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.DigestUtils;
import sun.misc.BASE64Encoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *    
 * Title         [title]
 * Author:       [..]
 * CreateDate:   [2019-09-29--16:11]  @_@ ~~
 * Version:      [v1.0]
 * Description:  [..]
 * <p>
 *     参考  https://blog.csdn.net/wh8_2011/article/details/80414204
 *     //https://www.cnblogs.com/demodashi/p/8512847.html
 *     //https://blog.csdn.net/weixin_42873937/article/details/82460997
 * </p>
 *  
 */
public class JwtUtil {

    public static String createJWT(String jwtId, String subject,long ttlMillis){
        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);
        HashMap<String, Object> claims = new HashMap<>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("uid", "uid2213");
        claims.put("username", "xiaoming");
        claims.put("nickname", "小明");
//        SecretKey key = generalKey();//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        String key = generalKey();
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder jwtBuilder = Jwts.builder()  //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)              //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(jwtId)                   //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)               //iat: jwt的签发时间
                .setSubject(subject)            //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm,key);//设置签名使用的签名算法和签名使用的秘钥
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            jwtBuilder.setExpiration(exp);      //设置过期时间
        }
        return jwtBuilder.compact();            //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
    }


    public static Claims parseJWT(String jwt){
//        SecretKey key = generalKey();
        String key = generalKey();
        Claims body = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();
        return body;
    }


//    private static SecretKey generalKey() {
    private static String generalKey() {
        final String jwt_secret = "cXdlcmFzZGZ6eGN2";
//        byte[] encode = Base64.getEncoder().encode("qwerasdfzxcv".getBytes());
//        System.out.println(new String(encode));
        byte[] decodeKey = Base64.getDecoder().decode(jwt_secret);
        String md5 = DigestUtils.md5DigestAsHex(decodeKey);
        String key = jwt_secret + md5;
        System.out.println(key);
        byte[] encodekey = Base64.getUrlEncoder().encode(key.getBytes());
        return new String(encodekey);
//        System.out.println(new String(encodekey));
        //根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。
//        SecretKeySpec aesKey = new SecretKeySpec(encodekey, 0, encodekey.length, "AES");
//        return aesKey;
    }

    public static void main(String[] args) {
        String jwt = createJWT("9999", "{id:'123',role:['admin','comm','agent']}", (long) (1000 * 60 * 10));
        System.out.println(jwt);
        Claims claims = parseJWT(jwt);
        Date expiration = claims.getExpiration();
        System.out.println(new SimpleDateFormat("yyyyMMdd HHmmss").format(expiration));

    }

}
