package kr.desponline.desp_backend.repository.mongodb;

import kr.desponline.desp_backend.entity.mongodb.PlayerVersusEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerVersusRepository extends
    ReactiveMongoRepository<PlayerVersusEntity, String> {

    @Query(value = "{}", fields = "{'nickname': 1,'record': 1}", sort = "{'record.score': -1}")
    Flux<PlayerVersusEntity> findPvpOrderByScore();

    @Query(value = "{ 'nickname' : ?0 }", fields = "{'nickname': 1,'record': 1}")
    Mono<PlayerVersusEntity> findPvpByNickname(String nickname);
}
