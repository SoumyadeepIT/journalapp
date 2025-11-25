package net.engineeringdigest.journalapp.repository;

import net.engineeringdigest.journalapp.Entry.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserLoginRepo extends MongoRepository<Users, ObjectId> {
    Users findByUsername(String username);
}

