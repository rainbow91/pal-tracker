package io.pivotal.pal.tracker;

import java.util.ArrayList;

import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private List<TimeEntry> timeEntries;
    private long localId = 0L;

    public InMemoryTimeEntryRepository() {
        localId = 0l;
        timeEntries = new ArrayList<TimeEntry>();
    }
    public  List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }

    public void setTimeEntries(List<TimeEntry> timeEntries) {
        timeEntries = timeEntries;
    }

    public  long getLocalId() {
        return localId;
    }

    public  long nextLocalId() {
        return ++localId;
    }

    @Override
    public TimeEntry create(TimeEntry any) {
        if ( any.getId() == 0) any.setId(nextLocalId());
        this.list().add(any);
        return any;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        for(TimeEntry item: this.list()) {
            if(item.getId() == timeEntryId){
                return item;
            }
        }

        return null;
    }

    @Override
    public TimeEntry update(long id, TimeEntry any) {
        TimeEntry item = this.find(id);
        if (item != null) {
            item.setDate(any.getDate());
            item.setHours(any.getHours());
            item.setProjectId(any.getProjectId());
            item.setUserId(any.getUserId());

        }
        return item;
    }

    @Override
    public boolean delete(long timeEntryId) {
        TimeEntry item = this.find(timeEntryId);
        boolean deleted = false;
        if (item != null) {
            deleted = list().remove(item);
        }
        return deleted;
    }

    @Override
    public List<TimeEntry> list() {

      return  this.timeEntries;
    }

}
