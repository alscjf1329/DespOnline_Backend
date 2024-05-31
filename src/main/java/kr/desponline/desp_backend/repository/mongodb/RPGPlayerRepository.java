package kr.desponline.desp_backend.repository.mongodb;

import kr.desponline.desp_backend.entity.mongodb.RPGPlayerEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RPGPlayerRepository extends ReactiveMongoRepository<RPGPlayerEntity, String> {

    @Query(value = "{ 'uuid' : ?0 }", fields = "")
    Mono<RPGPlayerEntity> findByUuid(String uuid);
}
