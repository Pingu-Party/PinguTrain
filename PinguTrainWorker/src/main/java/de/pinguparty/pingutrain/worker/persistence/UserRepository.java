package de.pinguparty.pingutrain.worker.persistence;

import de.pinguparty.pingutrain.worker.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

}
