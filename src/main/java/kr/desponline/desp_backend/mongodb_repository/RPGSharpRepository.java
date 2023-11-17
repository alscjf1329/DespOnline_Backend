package kr.desponline.desp_backend.mongodb_repository;

import kr.desponline.desp_backend.dto.PlayerDTO;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RPGSharpRepository extends ReactiveMongoRepository<PlayerDTO, String> {
    @Query(fields = "{'uuid': 1, 'nickname': 1,'job': 1, 'level': 1, 'exp': 1 , 'maxExp': 1,'lastPlayTime': 1}", sort = "{ 'level' : -1, 'exp' : -1 }")
    Flux<PlayerDTO> readPlayerOrderByLevel();

    @Query(value = "{ 'nickname' : ?0 }", fields = "{'uuid': 1, 'nickname': 1,'job': 1, 'level': 1, 'exp': 1 , 'maxExp': 1,'lastPlayTime': 1}")
    Mono<PlayerDTO> findPlayerByNickname(String nickname);
}
