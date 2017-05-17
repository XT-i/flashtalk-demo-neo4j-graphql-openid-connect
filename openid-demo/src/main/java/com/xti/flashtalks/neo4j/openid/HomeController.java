package com.xti.flashtalks.neo4j.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Object id_token = oAuth2ClientContext.getAccessToken().getAdditionalInformation().get("id_token");

        Jwt result = JwtHelper.decode(id_token.toString());

        return "Welcome, " + username + " your OpenID token is " + id_token + "<br/>Claims are " + result.getClaims();
    }
}
