package todolist.database.dataType;

import java.time.LocalDateTime;

public class Task {

    public Task(int taskId, String name, String description,
                LocalDateTime creationDateTime, User creator, List list
    ) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.creationDateTime = creationDateTime;
        this.creator = creator;
        this.list = list;
    }

    public int taskId;
    public String name;
    public String description;
    public LocalDateTime creationDateTime;
    public User creator;
    public List list;
}
