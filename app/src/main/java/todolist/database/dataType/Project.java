package todolist.database.dataType;

import java.time.LocalDateTime;
import java.util.Map;

public class Project {

    public Project(int projectId, String name, String description, LocalDateTime creationDate, User creator) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.creator = creator;
    }

    public Project(Map<String, String> data) {
        this.projectId = Integer.parseInt(data.get("project_id"));
        this.name = data.get("name");
        this.description = data.get("description");
        this.creationDate = LocalDateTime.parse(data.get("creation_date").replace(" ", "T"));
        this.creator = new User(Integer.parseInt(data.get("creator")), "", "", "", "");
    }

    public int projectId;

    public String name;
    public String description;
    public LocalDateTime creationDate;
    public User creator;
}
