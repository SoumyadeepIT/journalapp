package net.engineeringdigest.journalapp.Services;


import net.engineeringdigest.journalapp.Entry.Users;
import net.engineeringdigest.journalapp.repository.UserLoginRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserLoginService {
    @Autowired
    private UserLoginRepo userLoginRepo;

    public void saveEntry(Users user){
        if (user != null) {
            userLoginRepo.save(user);
        } else {
            throw new IllegalArgumentException("Entry title cannot be null or empty");
        }
    }

    public List<Users> getAll(){
        List<Users> user = userLoginRepo.findAll();
        if (user.isEmpty()) {
            throw new IllegalArgumentException("No entries found");
        }
        return user;
    }

    public Optional<Users> showEntryById(ObjectId id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return userLoginRepo.findById(id);
    }

    public Users updateEntryById(ObjectId my_id, Users user) {
        if (my_id != null) {
            if (userLoginRepo.existsById(my_id)) {
                user.setId(my_id);
                return userLoginRepo.save(user);
            } else {
                throw new IllegalArgumentException("Entry not found with ID: " + my_id);
            }
        } else {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
    }

    public void DeleteAllEntries() {
            userLoginRepo.deleteAll();
    }


    public void DeleteEntryById(ObjectId id) {
        if (id != null) {
            userLoginRepo.deleteById(id);
        } else {
            throw new IllegalArgumentException("ID cannot be null");
        }
    }

    public Users findByUsername(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        Users user = userLoginRepo.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
        return user;
    }
}
