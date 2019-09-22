import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InlineResponses {

    private static ArrayList<InlineKeyboardButton> tasks = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Jam #1").setCallbackData("t_1"),
//                    new InlineKeyboardButton().setText("Jam #3").setCallbackData("t_3_1"),
                    new InlineKeyboardButton().setText("Jam #2").setCallbackData("t_2")
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
//                    new InlineKeyboardButton().setText("Task 3").setCallbackData("t_3_1")
            ));

    private static ArrayList<InlineKeyboardButton> attempt1 = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Ну как, есть идеи?").setCallbackData("at_1")
            ));

    private static ArrayList<InlineKeyboardButton> admins = new ArrayList<>(
            Arrays.asList(
                    new InlineKeyboardButton().setText("Users").setCallbackData("a_g")
            ));

    public static List<InlineKeyboardButton> getTasksInlineButtons() {
        return tasks;
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
}