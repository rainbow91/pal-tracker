package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping
public class TimeEntryController {

    private  TimeEntryRepository timeEntryRepository ;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }


    public ResponseEntity create(TimeEntry timeEntryToCreate) {
        TimeEntry item = timeEntryRepository.create(timeEntryToCreate);
        return  new ResponseEntity(item, HttpStatus.CREATED);
    }

    public ResponseEntity<TimeEntry> read(long timeEntryId) {
        TimeEntry item = timeEntryRepository.find(timeEntryId);
        if( item ==null)  return  new ResponseEntity(item, HttpStatus.NOT_FOUND);
        return  new ResponseEntity(item, HttpStatus.OK);
    }

    public ResponseEntity update(long timeEntryId, TimeEntry expected) {
        TimeEntry  item = timeEntryRepository.update(timeEntryId, expected);

        if( item ==null)  return  new ResponseEntity(item, HttpStatus.NOT_FOUND);
        return  new ResponseEntity(item, HttpStatus.OK);

    }

    public ResponseEntity<TimeEntry> delete(long timeEntryId) {
        boolean removed = timeEntryRepository.delete(timeEntryId);

        if (!removed)  return  new ResponseEntity(HttpStatus.NO_CONTENT);
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> items = timeEntryRepository.list();
        if( items.isEmpty())  return  new ResponseEntity(items, HttpStatus.NOT_FOUND);
        return  new ResponseEntity(items, HttpStatus.OK);
    }
}
