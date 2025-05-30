package com.linkedin.user_service.controller;

import com.linkedin.user_service.config.AuthClient;
import com.linkedin.user_service.dtos.UpdateUserInfoRequest;
import com.linkedin.user_service.dtos.UpdateUserRequest;
import com.linkedin.user_service.dtos.UserDto;
import com.linkedin.user_service.dtos.UserProfileDto;
import com.linkedin.user_service.dtos.experience.CreateExperienceDto;
import com.linkedin.user_service.dtos.experience.ExperienceDto;
import com.linkedin.user_service.dtos.experience.UpdateExperienceDto;
import com.linkedin.user_service.dtos.friend.SendRequestDto;
import com.linkedin.user_service.dtos.friend.UserFriendDto;
import com.linkedin.user_service.entities.Users;
import com.linkedin.user_service.exception.ApiException;
import com.linkedin.user_service.services.ExperienceService;
import com.linkedin.user_service.services.FriendRequestService;
import com.linkedin.user_service.services.UserProfileService;
import com.linkedin.user_service.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {

    private final String LOGIN_PLEASE = "User not Login";
    @Autowired
    private AuthClient authClient;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private FriendRequestService friendRequestService;

    @GetMapping("getUserById/{id}")
    private ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return userService.getUserById(id);
    }

    @PutMapping("/updateUser/{email}")
    private ResponseEntity<Void> updateUser(@RequestParam Long id,@PathVariable String email ,@RequestBody UpdateUserRequest updateUserRequest){
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return userService.updateUser(email,updateUserRequest);
    }

    @GetMapping("profile/{id}")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable Long id) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return userProfileService.getProfile(id);
    }

    @PostMapping("/addProfile")
    public ResponseEntity<Void> addProfile(@Valid @RequestBody UserProfileDto userProfileDto, @RequestParam Long id) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return userProfileService.addProfile(userProfileDto, id);
    }

    @PutMapping("/updateProfileById")
    public ResponseEntity<UserProfileDto> updateById(
            @Valid
            @RequestParam Long id, @RequestBody UpdateUserInfoRequest updateUserInfoRequest
    ) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        UserProfileDto userProfileDto = userProfileService.updateById(id, updateUserInfoRequest);
        return new ResponseEntity<>(userProfileDto, HttpStatus.FOUND);
    }

    @GetMapping("getExpiById/{exid}")
    public ResponseEntity<ExperienceDto> getExperienceById(@PathVariable Long exid, @RequestParam Long id) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        ExperienceDto experienceDto = experienceService.getExperienceById(exid);

        return ResponseEntity.ok(experienceDto);
    }

    @PostMapping("/addExpi")
    public ResponseEntity<Void> createExperience(@Valid @RequestBody CreateExperienceDto createExperienceDto, @RequestParam Long id) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return experienceService.createExperience(createExperienceDto, id);
    }

    @PutMapping("updateExpiById/{exId}")
    public ResponseEntity<Void> updateExperience(@Valid @RequestBody UpdateExperienceDto updateExperienceDto, @PathVariable Long exId, @RequestParam Long id) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return experienceService.updateExperienceDto(updateExperienceDto, exId, id);
    }

    @DeleteMapping("deleteExpiById/{exid}")
    public ResponseEntity<Void> deleteExperienceById(@PathVariable Long exid, @RequestParam Long id) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return experienceService.deleteExperienceById(exid, id);
    }

    @PostMapping("/sendFriendReq")
    public ResponseEntity<String> sendFriendReq(@Valid @RequestBody SendRequestDto sendRequestDto, @RequestParam Long id) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return friendRequestService.sendFriendReq(sendRequestDto);
    }

    @PostMapping("/respondRequest")
    public ResponseEntity<String> respondRequest(@RequestParam Long id, @RequestParam Long requestId, @RequestParam boolean accept) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return friendRequestService.respondToRequest(requestId, accept);
    }

    @GetMapping("/getFriends")
    public ResponseEntity<List<UserFriendDto>> getFriends(@RequestParam Long id, @RequestParam String email) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return friendRequestService.getFriends(email);
    }

    @DeleteMapping("/removeFriend")
    public ResponseEntity<String> removeFriend(@RequestParam Long id, @RequestParam String email,@RequestParam String email1) {
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return friendRequestService.removeFriend(email,email1);
    }

    @GetMapping("/PendingFriendReq")
    public ResponseEntity<List<UserFriendDto>> getPendingRequest(@RequestParam Long id,@RequestParam String email){
        boolean loggedIn = authClient.isLoggedIn(id);
        if (!loggedIn) {
            throw new ApiException(LOGIN_PLEASE, HttpStatus.BAD_REQUEST);
        }
        return friendRequestService.getPendingRequest(email);
    }
}
