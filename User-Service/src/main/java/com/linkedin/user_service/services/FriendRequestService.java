package com.linkedin.user_service.services;

import com.linkedin.user_service.dtos.friend.SendRequestDto;
import com.linkedin.user_service.dtos.friend.UserFriendDto;
import com.linkedin.user_service.entities.Users;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FriendRequestService {
    ResponseEntity<String> sendFriendReq(@Valid SendRequestDto sendRequestDto);


    ResponseEntity<String> respondToRequest(Long requestId, boolean accept);

    ResponseEntity<List<UserFriendDto>> getFriends(String email);

    ResponseEntity<String> removeFriend(String email, String email1);

    ResponseEntity<List<UserFriendDto>> getPendingRequest(String email);
}
