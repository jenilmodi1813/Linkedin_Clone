package com.linkedin.user_service.repositories;

import com.linkedin.user_service.constance.RequestStatus;
import com.linkedin.user_service.entities.FriendRequest;
import com.linkedin.user_service.entities.Users;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {
//    Optional<FriendRequest> findByEmail(@NotEmpty String senderEmail);


    Optional<FriendRequest> findBySenderAndReceiver(Users senderEmail,Users receiverEmail);

    List<FriendRequest> findBySenderOrReceiverAndStatus(Users users, Users users1, RequestStatus requestStatus);

    List<FriendRequest> findByReceiverAndStatus(Users byEmail, RequestStatus requestStatus);
}
