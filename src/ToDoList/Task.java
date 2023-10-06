package ToDoList;
import java.io.Serializable;

public class Task implements Serializable{
    String description;

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
