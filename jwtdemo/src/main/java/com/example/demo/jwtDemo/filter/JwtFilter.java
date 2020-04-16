package com.example.demo.jwtDemo.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *    
 * Title         [title]
 * Author:       [..]
 * CreateDate:   [2019-09-29--10:56]  @_@ ~~
 * Version:      [v1.0]
 * Description:  [..]
 * <p></p>
 *  
 */
public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) resp;

        final String authHeader = request.getHeader("authorization");

        if("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(req, resp);
        }else {

            //check the authorization, check if the token id started by "Bearer "
            if(authHeader == null ){
                throw new ServletException("Missing or invalid Authorization header");
            }

            final String token = authHeader.substring(7);

            try {
                //use JET parser to check if the signature in valid with the key "secretkey"
                final Claims claims = Jwts.parser().setSigningKey("12345qwert12345qwert12345qwert12345qwert12345qwert").parseClaimsJws(token).getBody();

                //add the claims to request header
                request.setAttribute("claims", claims);
            }  catch (SignatureException e) {
//                e.printStackTrace();
                throw new ServletException("Invalid token");
            }

            filterChain.doFilter(req, resp);

        }

    }
}
