package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.entity.UserEntity;
import kr.desponline.desp_backend.mysql_repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findUserEntityByUuid(String uuid) {
        return userRepository.findUserEntityByUuid(uuid);
    }
}
