package com.linkedin.user_service.dtos.friend;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendRequestDto {

    @NotEmpty
    private String senderEmail;

    @NotEmpty
    private String receiverEmail;

}
