package net.engineeringdigest.journalapp.Controller;

import net.engineeringdigest.journalapp.Entry.JournalEntry;
import net.engineeringdigest.journalapp.Entry.Users;
import net.engineeringdigest.journalapp.HelperClasses.GenerateEmployeeID;
import net.engineeringdigest.journalapp.Services.JournalEntryService;
import net.engineeringdigest.journalapp.Services.UserLoginService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JavaEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private GenerateEmployeeID generateEmployeeID;
    @Autowired
    private  UserLoginService service;

    @GetMapping("{username}")
    public ResponseEntity<?> getAllEntryofUsers(@PathVariable String username, String password){
        Users users = service.findByUsername(username, password);
        List<JournalEntry> all = users.getJournalEntry();
        if(all!=null && !all.isEmpty()) {
            return new ResponseEntity<>(journalEntryService.getAllEntries(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("{username}")
    public ResponseEntity<?> CreateEntry(@RequestBody JournalEntry myEntry, @PathVariable String username, String password){
        try{
            myEntry.setDate(LocalDate.now());
            journalEntryService.saveEntry(myEntry,username, password);
            return new ResponseEntity<>("Journal entry created successfully with ID: " + myEntry.getId(), HttpStatus.CREATED);
        }catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllEntries() {
        journalEntryService.DeleteAllEntries();
        return new ResponseEntity<>("All journal entries deleted successfully.", HttpStatus.NO_CONTENT);
    }
    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myid) {
    Optional<JournalEntry>entry = journalEntryService.findEntryById(myid);
        return entry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null,HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid) {
        Optional<JournalEntry> entry = journalEntryService.findEntryById(myid);
            if(entry.isPresent()) {
                journalEntryService.DeleteEntryById(myid);
                return new ResponseEntity<>("Journal entry with ID: " + myid, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @PutMapping("id/{ID}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId ID, String username, String password, @RequestBody JournalEntry myEntry) {
        Users user = service.findByUsername(username, password);
       JournalEntry old = journalEntryService.findEntryById(ID).orElseGet(null);
       if(old!=null){
           old.setTitle(myEntry.getTitle()!=null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
           old.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("") ? myEntry.getContent() : old.getContent());
           journalEntryService.saveEntry(old,username, password);
           return  new ResponseEntity<>(old, HttpStatus.OK);
       }
       return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
