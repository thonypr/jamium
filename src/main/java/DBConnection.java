import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;

public class DBConnection {

    private static Connection getConnection() throws URISyntaxException, SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        return DriverManager.getConnection(dbUrl);
    }

    public static void addUser(Long userId, State state) {
        if (userId == null || state == null) {
            Notificator.sendToAdmin("addUser - ERROR: userId = " + userId + "; state = " + state);
        } else {
            PreparedStatement stm = null;
            try {
                stm = getConnection().prepareStatement("INSERT INTO tg_user VALUES (?,?);");
                stm.setLong(1, userId);
                stm.setString(2, state.toString());
                stm.execute();
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                Notificator.sendToAdmin("addUser - ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void updateUser(Long userId, State state) {
        if (userId == null || state == null) {
            Notificator.sendToAdmin("updateUser - ERROR: userId = " + userId + "; state = " + state);
        } else {
            PreparedStatement stm = null;
            try {
                stm = getConnection().prepareStatement("UPDATE tg_user SET state = ? WHERE id = ?");
                stm.setLong(2, userId);
                stm.setString(1, state.toString());
                stm.execute();
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                Notificator.sendToAdmin("updateUser - ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static HashMap<Long, User> getUsers() {

        HashMap<Long, User> users = new HashMap<>();
        PreparedStatement stm = null;
        try {
            stm = getConnection().prepareStatement("SELECT * FROM tg_user;");
            ResultSet resultSet = stm.executeQuery();
            // process the results
            while (resultSet.next()) {
                User user = new User(resultSet.getLong(1), State.valueOf(resultSet.getString(2)));
                users.put(user.getUserId(), user);
            }
            resultSet.close();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            Notificator.sendToAdmin("getUsers - ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public static HashMap<Long, TaskDB> getTasks() {
        HashMap<Long, TaskDB> tasks = new HashMap<Long, TaskDB>();
        PreparedStatement stm = null;
        try {
            stm = getConnection().prepareStatement("SELECT * FROM task;");
            ResultSet resultSet = stm.executeQuery();
            // process the results
            while (resultSet.next()) {
//                TaskDB task = new TaskDB(resultSet.getInt(1), resultSet.getString(2),
//                        resultSet.getBoolean(3));
                TaskDB task = new TaskDB(resultSet.getInt(1), resultSet.getBoolean(3));
                tasks.put(task.getId(), task);
            }
            resultSet.close();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            Notificator.sendToAdmin("getTasks - ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return tasks;
    }

    public static void updateTask(Long taskId, boolean state) {
        if (taskId == null) {
            Notificator.sendToAdmin("updateState - ERROR: userId = " + taskId + "; state = " + state);
        } else {
            PreparedStatement stm = null;
            try {
                stm = getConnection().prepareStatement("UPDATE task SET is_active = ? WHERE id = ?");
                stm.setLong(2, taskId);
                stm.setBoolean(1, state);
                stm.execute();
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                Notificator.sendToAdmin("updateTask - ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void addTask(Long taskId, boolean state) {
        if (taskId == null) {
            Notificator.sendToAdmin("addTask - ERROR: userId = " + taskId + "; state = " + state);
        } else {
            PreparedStatement stm = null;
            try {
                stm = getConnection().prepareStatement(String.format("INSERT INTO task VALUES (?, 'task%s', ?)", taskId));
                stm.setBoolean(2, state);
                stm.setLong(1, taskId);
                stm.execute();
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                Notificator.sendToAdmin("addTask - ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
