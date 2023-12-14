package todolist.database.dataType;

import java.time.LocalDateTime;

public class Project {

    public Project(int projectId, String name, String description, LocalDateTime creationDate, User creator) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.creator = creator;
    }

    public int projectId;

    public String name;
    public String description;
    public LocalDateTime creationDate;
    public User creator;
}
