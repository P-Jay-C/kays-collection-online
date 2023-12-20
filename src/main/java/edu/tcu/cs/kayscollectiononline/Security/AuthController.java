package edu.tcu.cs.kayscollectiononline.Security;

import edu.tcu.cs.kayscollectiononline.system.Result;
import edu.tcu.cs.kayscollectiononline.system.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class AuthController {

    @Autowired
    private AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public Result getLoginInfo(Authentication authentication){

        LOGGER.debug("Authenticated user: '{}'", authentication.getName());
        return new Result(true, StatusCode.SUCCESS, "User Info and Json Web Token", authService.createLoginInfo(authentication));
    }

}
