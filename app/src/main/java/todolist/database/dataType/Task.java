package todolist.database.dataType;

import java.time.LocalDateTime;
import java.util.Map;

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

    public Task(Map<String, String> data) {
        this.taskId = Integer.parseInt(data.get("task_id"));
        this.name = data.get("name");
        this.description = data.get("description");
        this.creationDateTime = LocalDateTime.parse(data.get("creation_date").replace(" ", "T"));
        this.creator = new User(Integer.parseInt(data.get("creator")), "", "", "", "");
        this.list = new List(Integer.parseInt(data.get("list")), "", "", null, null, null);
    }

    public int taskId;
    public String name;
    public String description;
    public LocalDateTime creationDateTime;
    public User creator;
    public List list;
}
