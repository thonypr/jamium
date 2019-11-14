public class TaskDB {
    private long id;
//    private String name;
    private boolean is_active;

    public TaskDB(long id, boolean is_active) {
        this.id = id;
//        this.name = name;
        this.is_active = is_active;
    }

//    public String getName() {
//        return name;
//    }

    public boolean getIsActive() {
        return is_active;
    }

    public long getId() {
        return id;
    }
}
