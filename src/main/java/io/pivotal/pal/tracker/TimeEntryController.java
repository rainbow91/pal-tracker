package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private  TimeEntryRepository timeEntryRepository ;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry item = timeEntryRepository.create(timeEntryToCreate);
        return  new ResponseEntity(item, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(name = "id") long timeEntryId) {
        TimeEntry item = timeEntryRepository.find(timeEntryId);
        if( item ==null)  return  new ResponseEntity(item, HttpStatus.NOT_FOUND);
        return  new ResponseEntity(item, HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
        TimeEntry  item = timeEntryRepository.update(id, expected);

        if( item ==null)  return  new ResponseEntity(item, HttpStatus.NOT_FOUND);
        return  new ResponseEntity(item, HttpStatus.OK);

    }
    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable(name = "id") long timeEntryId) {
        boolean removed = timeEntryRepository.delete(timeEntryId);

        if (!removed)  return  new ResponseEntity(HttpStatus.NO_CONTENT);
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> items = timeEntryRepository.list();
//        if( items.isEmpty())  return  new ResponseEntity(items, HttpStatus.NOT_FOUND);
        return  new ResponseEntity(items, HttpStatus.OK);
    }
}
