package com.headout.globetrotter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationResponseDto {
    private String invitationLink;
    private String imageUrl;
}
