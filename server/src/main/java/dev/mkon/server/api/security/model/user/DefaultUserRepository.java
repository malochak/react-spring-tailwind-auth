package dev.mkon.server.api.security.model.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultUserRepository extends UserRepository, MongoRepository<User, String> {

}
