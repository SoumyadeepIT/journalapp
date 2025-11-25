package net.engineeringdigest.journalapp.Services;

import net.engineeringdigest.journalapp.Entry.JournalEntry;
import net.engineeringdigest.journalapp.Entry.Users;
import net.engineeringdigest.journalapp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserLoginService service;


    public void saveEntry(JournalEntry entry, String username, String password){
        if (entry != null && !entry.getTitle().isEmpty()) {
            Users users = service.findByUsername(username, password);
            entry.setDate(LocalDate.now());
            JournalEntry saved = journalEntryRepo.save(entry); // press option and enter key together to get this done.
            users.getJournalEntry().add(saved);
            service.saveEntry(users);
        } else {
            throw new IllegalArgumentException("Entry title cannot be null or empty");
        }
    }

    public List<JournalEntry> getAllEntries(){
        List<JournalEntry> entries = journalEntryRepo.findAll();
        if (entries.isEmpty()) {
            throw new IllegalArgumentException("No entries found");
        }
        return entries;
    }

    public Optional<JournalEntry> findEntryById(ObjectId id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return journalEntryRepo.findById(id);
    }

    public JournalEntry updateEntryById(ObjectId my_id, JournalEntry entry) {
        if (my_id != null) {
            if (journalEntryRepo.existsById(my_id)) {
                entry.setId(my_id);
                return journalEntryRepo.save(entry);
            } else {
                throw new IllegalArgumentException("Entry not found with ID: " + my_id);
            }
        } else {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
    }

    public void DeleteAllEntries() {
            journalEntryRepo.deleteAll();
    }


    public void DeleteEntryById(ObjectId id) {
        if (id != null) {
            journalEntryRepo.deleteById(id);
        } else {
            throw new IllegalArgumentException("ID cannot be null");
        }
    }
}
