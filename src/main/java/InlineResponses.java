import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InlineResponses {

    private static ArrayList<InlineKeyboardButton> tasks = new ArrayList<>(
            Arrays.asList(
                    //TODO:вернуть номер 2
                    new InlineKeyboardButton().setText("Jam #1").setCallbackData("t_1"),
                    new InlineKeyboardButton().setText("Jam #2").setCallbackData("t_2"),
//                    new InlineKeyboardButton().setText("mock").setCallbackData("mock")
                    new InlineKeyboardButton().setText("Jam #3").setCallbackData("t_3_1"),
                    new InlineKeyboardButton().setText("Jam #4").setCallbackData("t_4_1"),
                    new InlineKeyboardButton().setText("Jam #5 \uD83C\uDF84").setCallbackData("t_5"),
                    new InlineKeyboardButton().setText("Jam #5_1 \uD83C\uDF81").setCallbackData("t_5_1"),
                    new InlineKeyboardButton().setText("Jam #5_2 \uD83C\uDF85").setCallbackData("t_5_2"),
                    new InlineKeyboardButton().setText("Jam #5_3 ✨").setCallbackData("t_5_3")
            ));

    private static ArrayList<InlineKeyboardButton> medias = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("audio").setCallbackData("u_a"),
                    new InlineKeyboardButton().setText("photo").setCallbackData("u_p"),
                    new InlineKeyboardButton().setText("file").setCallbackData("u_f")
            ));

    private static ArrayList<InlineKeyboardButton> task1 = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Task 1").setCallbackData("t_1")
            ));

    private static ArrayList<InlineKeyboardButton> task2 = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Task 2").setCallbackData("t_2")
            ));

    private static ArrayList<InlineKeyboardButton> task3 = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Task 3").setCallbackData("t_3_1")
            ));

    private static ArrayList<InlineKeyboardButton> task4 = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Task 4").setCallbackData("t_4_1")
            ));

    private static ArrayList<InlineKeyboardButton> task5 = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Task 5").setCallbackData("t_5")
            ));

    private static ArrayList<InlineKeyboardButton> task5_1 = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Task 5_1").setCallbackData("t_5_1")
            ));

    private static ArrayList<InlineKeyboardButton> task5_2 = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Task 5_2").setCallbackData("t_5_2")
            ));

    private static ArrayList<InlineKeyboardButton> task5_3 = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Task 5_3").setCallbackData("t_5_3")
            ));

    private static ArrayList<InlineKeyboardButton> attempt1 = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Ну как, есть идеи?").setCallbackData("at_1")
            ));

    private static ArrayList<InlineKeyboardButton> admins = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Users").setCallbackData("a_g"),
                    new InlineKeyboardButton().setText("Send text").setCallbackData("a_t")
            ));

    public static List<InlineKeyboardButton> getTasksInlineButtons() {
        //get available tasks from db
        List<InlineKeyboardButton> filtered = new ArrayList<>();
        for(int i = 0; i < tasks.size(); i++) {
            System.out.println("i = " + i);
            System.out.println(String.format("tasks RT = %s; tasksDB = %s", tasks.toString(), TaskDBController.getTasks()));
            if(TaskDBController.getTask((long) i + 1).getIsActive()) {
                filtered.add(tasks.get(i));
            }
        }

        return filtered;
    }

    public static List<InlineKeyboardButton> getMediaButtons() {
        return medias;
    }

    public static List<InlineKeyboardButton> getAdminButtons() {
        return admins;
    }

    public static List<InlineKeyboardButton> getAttempt1InlineButton() {
        return attempt1;
    }

    public static List<InlineKeyboardButton> getTask1InlineButtons() {
        return task1;
    }

    public static List<InlineKeyboardButton> getTask2InlineButtons() {
        return task2;
    }

    public static List<InlineKeyboardButton> getTask3InlineButtons() {
        return task3;
    }

    public static List<InlineKeyboardButton> getTask4InlineButtons() {
        return task4;
    }

    public static List<InlineKeyboardButton> getTask5InlineButtons() {
        return task5;
    }

    public static List<InlineKeyboardButton> getTask5_1InlineButtons() {
        return task5_1;
    }

    public static List<InlineKeyboardButton> getTask5_2InlineButtons() {
        return task5_2;
    }

    public static List<InlineKeyboardButton> getTask5_3InlineButtons() {
        return task5_3;
    }

}