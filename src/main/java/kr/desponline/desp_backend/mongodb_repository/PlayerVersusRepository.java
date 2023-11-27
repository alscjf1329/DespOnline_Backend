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

    @Query(value = "{}", fields = "{'nickname': 1,'record': 1}", sort = "{'record.score': -1}")
    Flux<PlayerVersusDTO> findPvpOrderByScore();

    @Query(value = "{ 'nickname' : ?0 }", fields = "{'nickname': 1,'record': 1}")
    Mono<PlayerVersusDTO> findPvpByNickname(String nickname);
}
