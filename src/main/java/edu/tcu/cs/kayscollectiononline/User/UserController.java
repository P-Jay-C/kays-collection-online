package edu.tcu.cs.kayscollectiononline.User;

import edu.tcu.cs.kayscollectiononline.User.Converter.UserDtoToUserConverter;
import edu.tcu.cs.kayscollectiononline.User.Converter.UserToUserDtoConverter;
import edu.tcu.cs.kayscollectiononline.system.Result;
import edu.tcu.cs.kayscollectiononline.system.StatusCode;
import edu.tcu.cs.kayscollectiononline.User.Dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {
    private final UserService userService;
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final UserDtoToUserConverter userDtoToUserConverter;

    public UserController(UserService userService, UserToUserDtoConverter userToUserDtoConverter, UserDtoToUserConverter userDtoToUserConverter) {
        this.userService = userService;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
    }

    @GetMapping("/{userId}")
    public Result findUserById(@PathVariable Long userId) {

        AppUser user = userService.findById(userId);
        UserDto userDto = userToUserDtoConverter.convert(user);

        return new Result(true, StatusCode.SUCCESS, "Find Success", userDto);
    }


    @GetMapping
    public Result findAllUsers() {

        List<AppUser> wizards = userService.findAll();

        List<UserDto> wizardDtos = wizards.stream().map(
                userToUserDtoConverter::convert
        ).toList();

        return new Result(true, StatusCode.SUCCESS, "Find All Success", wizardDtos);
    }

    @PostMapping
    public Result addUser(@Valid @RequestBody AppUser user) {


        AppUser savedUser = userService.save(user);

        UserDto savedUserDto = userToUserDtoConverter.convert(savedUser);

        return new Result(true, StatusCode.SUCCESS, "Add Success", savedUserDto);
    }

    @PutMapping("/{userId}")
    public Result updateUpdateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {

        AppUser update = userDtoToUserConverter.convert(userDto);

        AppUser updatedUser = userService.update(userId, update);

        UserDto updatedUserDto = userToUserDtoConverter.convert(updatedUser);

        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public Result deleteWizard(@PathVariable Long userId) {

        userService.delete(userId);

        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
