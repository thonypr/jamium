import java.util.HashMap;

public class TaskDBController {

    private static HashMap<Long, TaskDB> tasks = new HashMap<>();

    public static void setTasks(HashMap<Long, TaskDB> dbTasks) {
        HashMap<Long, TaskDB> tmp = new HashMap<>();
        tmp.putAll(tasks);
        tmp.putAll(dbTasks);
        tasks = tmp;
        Notificator.sendToAdmin("setTasks - successfully loaded " + tasks.values().size() + " tasks!");
    }

    public static String getTasks() {
        StringBuilder result = new StringBuilder();
        for(TaskDB task : tasks.values()) {
            result.append("task = ").append(task.getId()).append("; state = ").append(task.getIsActive()).append("\n");
        }
        return result.toString();
    }

    public static TaskDB getTask(Long taskId) {
        if(tasks.containsKey(taskId)) {
            return tasks.get(taskId);
        }
        //TODO: npe
        return null;
    }

    public static boolean hasTask(Long taskId) {
        return tasks.containsKey(taskId);
    }

    public static void addtask(Long taskId) {
        if(tasks.containsKey(taskId)) {
            // we have him here
            JamiumBot.log(taskId.toString(), "We got it!", "");
        }
        else {
            TaskDB newTask = new TaskDB(taskId, false);
            tasks.put(taskId, newTask);
            JamiumBot.log(taskId.toString(), "Collected!", "");
        }
    }

    public static void updateTaskState(Long taskId, boolean isActive) {
        if(tasks.containsKey(taskId)) {
            TaskDB newTask = new TaskDB(taskId, isActive);
            tasks.replace(taskId, newTask);
            JamiumBot.log(taskId.toString(), "Changed state to " + isActive, "");
        }
        else
        {
            JamiumBot.log(taskId.toString(), "Was not found!", "");
        }

    }
}
