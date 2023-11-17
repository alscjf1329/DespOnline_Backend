package kr.desponline.desp_backend.mongodb_repository;

import java.util.List;
import kr.desponline.desp_backend.dto.RPGPlayerDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RPGSharpRepository extends MongoRepository<RPGPlayerDTO, String> {
    @Query(value = "{}", fields = "{ 'id': 1, 'uuid': 1, 'nickname': 1,'job' : 1, 'level': 1, 'exp': 1 }", sort = "{ 'level' : -1, 'exp' : -1 }")
    List<RPGPlayerDTO> findPlayersByLevelDescExpDesc();
}
