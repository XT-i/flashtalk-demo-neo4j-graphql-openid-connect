package com.xti.flashtalks.neo4j.openid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssoresult")
public class SsoController {
    // @Autowired
    //private OAuth2ClientContext oAuth2ClientContext;

    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Welcome, " + username;//+ " your OpenID token is " + oAuth2ClientContext.getAccessToken().getAdditionalInformation().get("id_token");
    }
}
