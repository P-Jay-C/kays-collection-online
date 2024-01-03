package edu.tcu.cs.kayscollectiononline.user.converter;

import edu.tcu.cs.kayscollectiononline.user.AppUser;
import edu.tcu.cs.kayscollectiononline.user.dto.UserDto;
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
