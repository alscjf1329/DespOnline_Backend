package kr.desponline.desp_backend.mongodb_repository;

import kr.desponline.desp_backend.dto.PlayerVersusDTO;
import kr.desponline.desp_backend.dto.PlayerVersusRecordDTO;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerVersusRepository extends ReactiveMongoRepository<PlayerVersusDTO, String> {

    @Query(value = "{}", sort = "{'record.score': -1}")
    Flux<PlayerVersusDTO> findPvpOrderByScore();

    @Query(value = "{ 'uuid' : ?0 }", fields = "{'record': 1}")
    Mono<PlayerVersusRecordDTO> findPlayerVersusRecordByNickname(String uuid);
}
