package com.example.demo.jwtDemo.access.auth;

import com.example.demo.jwtDemo.entity.Person;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

/**
 *    
 * Title         [title]
 * Author:       [..]
 * CreateDate:   [2019-09-29--13:17]  @_@ ~~
 * Version:      [v1.0]
 * Description:  [..]
 * <p></p>
 *  
 */
@Controller
public class UserController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(String username,String password) throws ServletException {
        if(Objects.isNull(username)){
            throw new ServletException("username is invalid");
        }
        if(Objects.isNull(password)){
            throw new ServletException("password is invalid");
        }

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .claim("roles", "member")
                .setIssuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256, "secretkey")
                .signWith(SignatureAlgorithm.HS256, "12345qwert12345qwert12345qwert12345qwert12345qwert")
//                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
        log.info(jwtToken);
        System.out.println("-----");
//        System.out.println(jwtToken);
        return "Bearer " + jwtToken;
//        return username+"--"+password;
    }

    private static Key getKeyInstance() {
        // We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("bankglbankglbankglbankglbankglbankglbankglbankglbankglbankglbankgl");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }
}
