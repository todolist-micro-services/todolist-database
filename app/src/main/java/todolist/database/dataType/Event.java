package todolist.database.dataType;

import java.time.LocalDateTime;
import java.util.Map;

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

    public Event(Map<String, String> data) {
        this.eventId = Integer.parseInt(data.get("event_id"));
        this.name = data.get("name");
        this.description = data.get("description");
        this.startDateTime = LocalDateTime.parse(data.get("start_date").replace(" ", "T"));
        this.endDateTime = LocalDateTime.parse(data.get("end_date").replace(" ", "T"));
        this.creator = new User(Integer.parseInt(data.get("creator")), "", "", "", "");
        this.project = new Project(Integer.parseInt(data.get("project")), "", "", null, null);
    }

    public int eventId;
    public String name;
    public String description;
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
    public User creator;
    public Project project;
}
