package tom.lenormand.todolist.db;

/**
 * Created by tomle on 07/12/2017.
 */

public class Task {
    // Notez que l'identifiant est un long
    private long id;
    private String intitule;
    private String task;

    public Task(long id, String intitule, String task)
    {
        this.id = id;
        this.intitule = intitule;
        this.task = task;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getIntitule()
    {
        return intitule;
    }

    public void setIntitule(String intitule)
    {
        this.intitule = intitule;
    }

    public String getTask()
    {
        return task;
    }

    public void setTask(String task)
    {
        this.task = task;
    }
}

