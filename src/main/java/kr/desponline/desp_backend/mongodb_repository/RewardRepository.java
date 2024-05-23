package kr.desponline.desp_backend.mongodb_repository;

import kr.desponline.desp_backend.entity.mongodb.RewardEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RewardRepository extends ReactiveMongoRepository<RewardEntity, String> {

    Mono<RewardEntity> findByName(String name);
}
