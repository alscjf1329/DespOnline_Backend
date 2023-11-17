package kr.desponline.desp_backend.mongodb_repository;

import kr.desponline.desp_backend.dto.PlayerVersusDTO;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PlayerVersusRepository extends ReactiveMongoRepository<PlayerVersusDTO, String> {

    @Query(value = "{}", sort = "{'record.score': -1}")
    Flux<PlayerVersusDTO> findPvpOrderByScore();
}
