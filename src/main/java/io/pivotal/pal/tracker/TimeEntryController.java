package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntriesRepo ;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntriesRepo = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry item = timeEntriesRepo.create(timeEntryToCreate);
        actionCounter.increment();
        timeEntrySummary.record(this.timeEntriesRepo.list().size());
        return  new ResponseEntity(item, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(name = "id") long timeEntryId) {
        TimeEntry item = timeEntriesRepo.find(timeEntryId);
        if( item == null)
            return  new ResponseEntity(item, HttpStatus.NOT_FOUND);
        actionCounter.increment();
        return  new ResponseEntity(item, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
        TimeEntry  item = timeEntriesRepo.update(id, expected);
        if( item == null)
            return  new ResponseEntity(item, HttpStatus.NOT_FOUND);
        actionCounter.increment();
        return  new ResponseEntity(item, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable(name = "id") long timeEntryId) {
        boolean removed = timeEntriesRepo.delete(timeEntryId);
        actionCounter.increment();
        timeEntrySummary.record(this.timeEntriesRepo.list().size());
        if (!removed)  return  new ResponseEntity(HttpStatus.NO_CONTENT);
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> items = timeEntriesRepo.list();
        actionCounter.increment();
        return  new ResponseEntity(items, HttpStatus.OK);
    }
}
