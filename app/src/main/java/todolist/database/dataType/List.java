package todolist.database.dataType;

public class List {

    public List(int listId, String name, String description, List parent, User creator, Project project) {
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.creator = creator;
        this.project = project;
    }

    public  int listId;
    public String name;
    public String description;
    public List parent;
    public User creator;
    public Project project;
}
