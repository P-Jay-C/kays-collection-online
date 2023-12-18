package edu.tcu.cs.kayscollectiononline.User.Converter;

import edu.tcu.cs.kayscollectiononline.User.AppUser;
import edu.tcu.cs.kayscollectiononline.User.Dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<AppUser, UserDto> {
    @Override
    public UserDto convert(AppUser source) {
        return new UserDto(
                source.getId(),
                source.getUsername(),
                source.isEnabled(),
                source.getRoles()
        ) ;
    }
}
