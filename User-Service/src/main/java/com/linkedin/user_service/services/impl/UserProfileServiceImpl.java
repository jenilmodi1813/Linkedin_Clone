package com.linkedin.user_service.services.impl;

import com.linkedin.user_service.dtos.UpdateUserInfoRequest;
import com.linkedin.user_service.dtos.UserProfileDto;
import com.linkedin.user_service.entities.UserProfile;
import com.linkedin.user_service.entities.Users;
import com.linkedin.user_service.exception.ApiException;
import com.linkedin.user_service.repositories.UserProfileRepository;
import com.linkedin.user_service.repositories.UserRepository;
import com.linkedin.user_service.services.UserProfileService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final String USER_DOES_NOT_EXIST = "user does not exist";
    private final String USER_ID_NOT_EXIST = "user id not exist";
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<UserProfileDto> getProfile(Long id) {
//        userRepository.findById(id);
        UserProfile byUserid = userProfileRepository.findByUserId(id).orElseThrow(()-> new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setAddress(byUserid.getAddress());
        userProfileDto.setCity(byUserid.getCity());
        userProfileDto.setCountry(byUserid.getCountry());
        userProfileDto.setZipCode(byUserid.getZipcode());
        userProfileDto.setWebsite(byUserid.getWebsite());
        userProfileDto.setId(byUserid.getUser().getId());
        userProfileDto.setUsername(byUserid.getUser().getUsername());
        userProfileDto.setFirstName(byUserid.getUser().getFirstname());
        userProfileDto.setLastName(byUserid.getUser().getLastname());
        userProfileDto.setEmail(byUserid.getUser().getEmail());
        userProfileDto.setProfessionalSummary(byUserid.getProfessionalSummary());
        userProfileDto.setHeadLine(byUserid.getHeadline());
        userProfileDto.setDob(byUserid.getDob());
        return ResponseEntity.ok().body(userProfileDto);
    }

    @Override
    public ResponseEntity<Void> addProfile(UserProfileDto userProfileDto,Long id) {
        Users user = userRepository.findById(id).orElseThrow(()-> new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));

        UserProfile saveInfo = new UserProfile();
        saveInfo.setUser(user);
        saveInfo.setAddress(userProfileDto.getAddress());
        saveInfo.setZipcode(userProfileDto.getZipCode());
        saveInfo.setCity(userProfileDto.getCity());
        saveInfo.setCountry(userProfileDto.getCountry());
        saveInfo.setWebsite(userProfileDto.getWebsite());
        saveInfo.setProfessionalSummary(userProfileDto.getProfessionalSummary());
        saveInfo.setHeadline(userProfileDto.getHeadLine());
        saveInfo.setDob(userProfileDto.getDob());

        UserProfile userInfo = userProfileRepository.save(saveInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public UserProfileDto updateById(Long id, UpdateUserInfoRequest updateUserInfoRequest) {
        if(id == null) throw new ApiException(USER_ID_NOT_EXIST,HttpStatus.BAD_REQUEST);
        UserProfile userInfo = userProfileRepository.findByUserId(id).orElseThrow(() -> new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));

        //use common method
        userInfo.setAddress(updateUserInfoRequest.getAddress());
        userInfo.setZipcode(updateUserInfoRequest.getZipCode());
        userInfo.setCity(updateUserInfoRequest.getCity());
        userInfo.setCountry(updateUserInfoRequest.getCountry());
        userInfo.setWebsite(updateUserInfoRequest.getWebsite());
        userInfo.setProfessionalSummary(updateUserInfoRequest.getProfessionalSummary());
        userInfo.setHeadline(updateUserInfoRequest.getHeadLine());
        userInfo.setDob(updateUserInfoRequest.getDob());

        UserProfile updateInfo = userProfileRepository.save(userInfo);

        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setAddress(userInfo.getAddress());
        userProfileDto.setZipCode(userInfo.getZipcode());
        userProfileDto.setCity(userInfo.getCity());
        userProfileDto.setCountry(userInfo.getCountry());
        userProfileDto.setWebsite(userInfo.getWebsite());
        userProfileDto.setProfessionalSummary(userInfo.getProfessionalSummary());
        userProfileDto.setHeadLine(userInfo.getHeadline());
        userProfileDto.setDob(userInfo.getDob());
        return userProfileDto;
    }
}
