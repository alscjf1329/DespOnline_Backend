package kr.desponline.desp_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CertificationResultDTO {
    private final String uuid;
    private final int remainingAttempts;
    private final boolean isValid;
}
