package kr.desponline.desp_backend.mongodb_repository;

import java.time.LocalDateTime;
import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface WebEventRepository extends ReactiveMongoRepository<WebEventEntity, String> {

    @Query("{'startDate': {'$lte': ?0}, 'endDate': {'$gte': ?0}}")
    Flux<WebEventEntity> findAllInPeriod(LocalDateTime today);

    @Query(value = "{ '_id' : ?0 }")
    Mono<WebEventEntity> findById(String id);
}
