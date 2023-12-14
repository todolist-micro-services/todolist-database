package todolist.database.dataType;

import java.time.LocalDateTime;

public class Event {

    public Event(int eventId, String name, String description,
                 LocalDateTime startDateTime, LocalDateTime endDateTime,
                 User creator, Project project
    ) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.creator = creator;
        this.project = project;
    }
    public int eventId;
    public String name;
    public String description;
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
    public User creator;
    public Project project;
}
