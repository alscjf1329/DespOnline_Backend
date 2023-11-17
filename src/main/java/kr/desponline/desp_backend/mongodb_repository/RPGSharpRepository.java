package kr.desponline.desp_backend.mongodb_repository;

import kr.desponline.desp_backend.dto.RPGPlayerDTO;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RPGSharpRepository extends ReactiveMongoRepository<RPGPlayerDTO, String> {
    @Query(value = "{}", fields="{ 'id': 1, 'uuid': 1, 'nickname': 1, 'level': 1, 'exp': 1 }", sort="{ 'level' : -1, 'exp' : -1 }")
    Flux<RPGPlayerDTO> findPlayerOrderByLevel();
}
