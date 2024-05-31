package kr.desponline.desp_backend.mongodb_repository;

import kr.desponline.desp_backend.entity.mongodb.RewardMailBoxEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RewardMailRepository extends ReactiveMongoRepository<RewardMailBoxEntity, String> {

    Mono<RewardMailBoxEntity> findByUuid(String uuid);
}
