package com.linkedin.user_service.services.impl;

import com.linkedin.user_service.constance.RequestStatus;
import com.linkedin.user_service.dtos.friend.SendRequestDto;
import com.linkedin.user_service.dtos.friend.UserFriendDto;
import com.linkedin.user_service.entities.FriendRequest;
import com.linkedin.user_service.entities.Users;
import com.linkedin.user_service.exception.ApiException;
import com.linkedin.user_service.repositories.FriendRequestRepository;
import com.linkedin.user_service.repositories.UserRepository;
import com.linkedin.user_service.services.FriendRequestService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    private final String FRIEND_REQUEST_EXIST = "Friend Already Send";
    private final String FRIEND_REQUEST_NOT_EXIST = "Send Request ";
    private final String INVALID_SENDER_EMAIL = "Invalid Sender Email";
    private final String INVALID_RECEIVER_EMAIL = "Invalid Receiver Email";
    private final String USER_DOES_NOT_EXIST = "user does not exist";
    private final String NO_PENDING_REQUEST = "No Pending Request";

    @Override
    public ResponseEntity<String> sendFriendReq(SendRequestDto sendRequestDto) {
        Users sender = userRepository.findByEmail(sendRequestDto.getSenderEmail()).orElseThrow(() -> new ApiException(INVALID_SENDER_EMAIL, HttpStatus.NOT_FOUND));
        Users receiver = userRepository.findByEmail(sendRequestDto.getReceiverEmail()).orElseThrow(() -> new ApiException(INVALID_RECEIVER_EMAIL, HttpStatus.NOT_FOUND));

        if (friendRequestRepository.findBySenderAndReceiver(sender, receiver).isPresent()) {
            throw new ApiException(FRIEND_REQUEST_EXIST, HttpStatus.FOUND);
        }
        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(RequestStatus.PENDING);
        friendRequestRepository.save(request);

        return ResponseEntity.ok().body("Friend Request Send :");
    }

    @Override
    public ResponseEntity<String> respondToRequest(Long requestId, boolean accept) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId).orElseThrow(() -> new ApiException(FRIEND_REQUEST_NOT_EXIST, HttpStatus.NOT_FOUND));
        if (accept) {
            friendRequest.setStatus(RequestStatus.ACCEPTED);
            friendRequestRepository.save(friendRequest);
            return ResponseEntity.ok().body("Friend Request Accept : ");
        } else {
            friendRequest.setStatus(RequestStatus.REJECTED);
            friendRequestRepository.save(friendRequest);
            return ResponseEntity.ok().body("Friend Request Rejected : ");
        }
    }

    @Override
    public ResponseEntity<List<UserFriendDto>> getFriends(String email) {
        Users users = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        List<FriendRequest> acceptedRequests = friendRequestRepository.findBySenderOrReceiverAndStatus(users, users, RequestStatus.ACCEPTED);

        List<Users> friends = acceptedRequests.stream()
                .map(req -> req.getSender().equals(users) ? req.getReceiver() : req.getSender())
                .toList();
        List<UserFriendDto> userFriendDtos = new ArrayList<>();
        for (Users u : friends) {
            UserFriendDto userFriendDto = new UserFriendDto();
            userFriendDto.setUsername(u.getUsername());
            userFriendDto.setFirstName(u.getFirstname());
            userFriendDto.setLastName(u.getLastname());
            userFriendDtos.add(userFriendDto);
        }

        return ResponseEntity.ok(userFriendDtos);
    }

    @Override
    public ResponseEntity<String> removeFriend(String email, String email1) {
        Users users = userRepository.findByEmail(email).orElseThrow(() -> new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        Users users1 = userRepository.findByEmail(email1).orElseThrow(() -> new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));

        Optional<FriendRequest> request = friendRequestRepository.findBySenderAndReceiver(users, users1);
        if (request.isEmpty()) {
            request = friendRequestRepository.findBySenderAndReceiver(users1, users);
        }
        if (request.isPresent() && request.get().getStatus() == RequestStatus.ACCEPTED) {
            friendRequestRepository.delete(request.get());
            return ResponseEntity.ok().body("Remove Friend : ");
        } else {
            return ResponseEntity.badRequest().body("FriendShip Not Exists ");
        }
    }

    @Override
    public ResponseEntity<List<UserFriendDto>> getPendingRequest(String email) {
        Users byEmail = userRepository.findByEmail(email).orElseThrow(()->new ApiException(USER_DOES_NOT_EXIST, HttpStatus.NOT_FOUND));
        List<FriendRequest> requests = friendRequestRepository.findByReceiverAndStatus(byEmail, RequestStatus.PENDING);
        if(requests.isEmpty()){throw new ApiException(NO_PENDING_REQUEST,HttpStatus.NOT_FOUND);}
        //no need to throw the exception
        List<Users> friends = requests.stream()
                .map(req -> req.getSender().equals(byEmail) ? req.getReceiver() : req.getSender())
                .toList();

        List<UserFriendDto> userFriendDtos = new ArrayList<>();
        for (Users u : friends) {
            UserFriendDto userFriendDto = new UserFriendDto();
            userFriendDto.setUsername(u.getUsername());
            userFriendDto.setFirstName(u.getFirstname());
            userFriendDto.setLastName(u.getLastname());
            userFriendDtos.add(userFriendDto);
        }
        return ResponseEntity.ok(userFriendDtos);
    }


}
