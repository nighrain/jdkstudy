package com.example.demo.jwtDemo.access.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *    
 * Title         [title]
 * Author:       [..]
 * CreateDate:   [2019-09-29--13:36]  @_@ ~~
 * Version:      [v1.0]
 * Description:  [..]
 * <p></p>
 *  
 */
@RestController
public class SecureController {
    @RequestMapping(value = "/secure",method = {RequestMethod.GET,RequestMethod.POST})
    public String loginSuccess(){
        return "Login Successful!";
    }
}
