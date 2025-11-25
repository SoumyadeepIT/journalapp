package net.engineeringdigest.journalapp.Controller;

import net.engineeringdigest.journalapp.Entry.Users;
import net.engineeringdigest.journalapp.Services.UserLoginService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
        @Autowired
    private UserLoginService userservice;

        @GetMapping
        public List<Users> getAllUsers(){
        return userservice.getAll();
        }


        @PostMapping
        public void createUser(@RequestBody Users user) {
            userservice.saveEntry(user);
        }


        @PutMapping
        public ResponseEntity<?> updateUser(@RequestParam("id") ObjectId id, @RequestBody Users user) {
            Users userInDB = userservice.findByUsername(user.getUsername(), user.getPassword());
            if (userInDB != null && !Objects.equals(userInDB.getId(), id)) {
                if(userInDB.getUsername().equals(user.getUsername()))
                    return ResponseEntity.badRequest().body("Username already exists");
                else userInDB.setUsername(user.getUsername());
                if(userInDB.getPassword().equals(user.getPassword()))
                    return ResponseEntity.badRequest().body("Password already exists");
                else userInDB.setPassword(user.getPassword());
                userservice.saveEntry(userInDB);
            }
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }

        @DeleteMapping
        public ResponseEntity<?> deleteAllUsers() {
            userservice.DeleteAllEntries();
            return new ResponseEntity<>("All users deleted successfully.", HttpStatus.NO_CONTENT);
        }


}
