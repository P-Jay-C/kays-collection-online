package edu.tcu.cs.kayscollectiononline.Security;

import edu.tcu.cs.kayscollectiononline.User.AppUser;
import edu.tcu.cs.kayscollectiononline.User.Converter.UserToUserDtoConverter;
import edu.tcu.cs.kayscollectiononline.User.Dto.UserDto;
import edu.tcu.cs.kayscollectiononline.User.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserToUserDtoConverter userToUserDtoConverter;

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        Map<String, Object> loginResultMap = new HashMap<>();

        // Check if the principal is an instance of MyUserPrincipal
        if (authentication.getPrincipal() instanceof MyUserPrincipal principal) {
            AppUser appUser = principal.getAppUser();
            UserDto userDto = userToUserDtoConverter.convert(appUser);

            loginResultMap.put("userInfo", userDto);
        }

        String token = jwtProvider.createToken(authentication);
        loginResultMap.put("token", token);

        return loginResultMap;
    }


}
