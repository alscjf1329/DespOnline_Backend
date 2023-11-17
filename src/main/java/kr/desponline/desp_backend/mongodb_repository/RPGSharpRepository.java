package kr.desponline.desp_backend.mongodb_repository;

import kr.desponline.desp_backend.dto.LevelRankDTO;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RPGSharpRepository extends ReactiveMongoRepository<LevelRankDTO, String> {
    @Query(value = "{}", fields = "{'uuid': 1, 'nickname': 1,'job': 1, 'level': 1, 'exp': 1 , 'maxExp': 1,'lastPlayTime': 1}", sort = "{ 'level' : -1, 'exp' : -1 }")
    Flux<LevelRankDTO> readPlayerOrderByLevel();
}
