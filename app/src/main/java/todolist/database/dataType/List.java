package todolist.database.dataType;

import java.util.Map;

public class List {

    public List(int listId, String name, String description, List parent, User creator, Project project) {
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.creator = creator;
        this.project = project;
    }

    public List(Map<String, String> data) {
        this.listId = Integer.parseInt(data.get("list_id"));
        this.name = data.get("name");
        this.description = data.get("description");
        this.parent = data.get("parent") == null ? null : new List(Integer.parseInt(data.get("parent")), "", "", null, null, null);
        this.creator = new User(Integer.parseInt(data.get("creator")), "", "", "", "");
        this.project = new Project(Integer.parseInt(data.get("project")), "", "", null, null);
    }

    public  int listId;
    public String name;
    public String description;
    public List parent;
    public User creator;
    public Project project;
}
