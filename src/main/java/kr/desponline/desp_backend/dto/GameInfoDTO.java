package kr.desponline.desp_backend.dto;

import kr.desponline.desp_backend.entity.GameInfoEntity;
import kr.desponline.desp_backend.entity.GameInfoEntity.ProcessedGameInfoEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameInfoDTO {

    private final String errorMessage;
    private final ProcessedGameInfoEntity gameInfo;
}
