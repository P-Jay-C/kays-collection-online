package edu.tcu.cs.kayscollectiononline.User.Converter;

import edu.tcu.cs.kayscollectiononline.User.AppUser;
import edu.tcu.cs.kayscollectiononline.User.Dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, AppUser> {
    @Override
    public AppUser convert(UserDto source) {

        AppUser user = new AppUser();

        user.setUsername(source.username());
        user.setEnabled(source.enabled());
        user.setRoles(source.roles());

        return  user;
    }
}
