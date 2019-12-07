import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Audio;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class JamiumBot extends TelegramLongPollingBot {


    public static void log(String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if (chat_id == 235486635 && message_text.equals("shikaka")) {
                log(String.valueOf(chat_id), "admin!", "");
//                    SendPhoto photo = new SendPhoto();
                SendMessage msg = new SendMessage()
                        .setText("admin!")
//                            .setMessageId(update.getMessage().getMessageId())
                        .setReplyMarkup(InlineKeyboardResponses.getShowUsersKeyboard())
                        .setChatId(chat_id);
//                    photo.setPhoto("AgADAgAD6qcxGwnPsUgOp7-MvnQ8GecvSw0ABGvTl7ObQNPNX7UEAAEC");
                try {
                    execute(msg); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else if (chat_id == 235486635 && message_text.contains("send")) {
                log(String.valueOf(chat_id), "admin!", "");
//                    SendPhoto photo = new SendPhoto();
                // send;id;text
                String[] parts = message_text.split(";");
                String id = parts[1];
                String text = parts[2];
                try {
                    Notificator.sendDirectPost(text, id, getBotToken());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SendMessage msg = new SendMessage()
                        .setText(String.format("sending directly to: %s message: %s", id, text))
//                            .setMessageId(update.getMessage().getMessageId())
                        .setChatId(chat_id);
//                    photo.setPhoto("AgADAgAD6qcxGwnPsUgOp7-MvnQ8GecvSw0ABGvTl7ObQNPNX7UEAAEC");
                try {
                    execute(msg); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }


            }

            else if (chat_id == 235486635 && message_text.contains("taska")) {
                log(String.valueOf(chat_id), "admin!", "");
//                    SendPhoto photo = new SendPhoto();
                // taska;id
                String[] parts = message_text.split(";");
                String id = parts[1];
                TaskDBController.addTask(Long.valueOf(id));

            }

            else if (chat_id == 235486635 && message_text.contains("tasku")) {
                log(String.valueOf(chat_id), "admin!", "");
//                    SendPhoto photo = new SendPhoto();
                // tasku;id;state
                String[] parts = message_text.split(";");
                String id = parts[1];
                boolean state = parts[2].equals("1");
                TaskDBController.updateTaskState(Long.valueOf(id), state);

            }

            else if (chat_id == 235486635 && message_text.contains("tasks")) {
                log(String.valueOf(chat_id), "admin!", "");
//                    SendPhoto photo = new SendPhoto();
                // tasku;id;state
                Notificator.sendToAdmin(TaskDBController.getTasks());

            }

            else if (message_text.equals("/start")) {

                if(!UsersController.hasUser(chat_id)) {
                    try {
                        Notificator.sendDebug(String.format("User %s started", update.getMessage().getFrom().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //process user
                UsersController.addUser(chat_id);
                DBConnection.addUser(chat_id, State.WELCOME);
                //show welcome screen
                SendMessage message = new SendMessage();
                message.setChatId(chat_id);
                String userName = update.getMessage().getFrom().getFirstName();
                message.setText(Responses.WELCOME.replace("X", userName));

                //TODO: flexible tasks list
//                List<TaskDB> tasks = DBConnection.getTasks().stream().filter(t -> t.getIsActive()).collect(Collectors.toList());
                message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
//                message.setReplyMarkup(InlineKeyboardResponses.getKeyboardFromTasks(tasks));
//                Keyboards.setWelcomeButtons(message);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
//            else if (message_text.equals("/start") && UsersController.hasUser(chat_id)){
//                 /start and hasUser
//                 we need to set state of user to WELCOME
//            }
            else if (UsersController.hasUser(chat_id)) {
                // check current state of user
                //response should be 2
                Long chatId = update.getMessage().getChatId();
                State userState = UsersController.getUser(chatId).getUserState();
                try {
                    Notificator.sendDebug(String.format("User %s \nin state = %s says\n%s\n%s",
                            update.getMessage().getFrom().toString(),
                            userState,
                            update.getMessage().getText(),
                            "@" + update.getMessage().getFrom().getUserName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String response = "";

                switch (userState) {
                    case VIEW_TASK_1: {
                        SendMessage message = new SendMessage();
                        response = Validator.task1(update.getMessage().getText());
                        if (response.equals(Responses.CONGRAT_1)) {
                            UsersController.updateUserState(chatId, State.SOLVED_TASK_1);
                            DBConnection.updateUser(chatId, State.SOLVED_TASK_1);
                            message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
                            //TODO: add notification to admin
                            try{
                                Notificator.sendPost("ooo whee! jam#1 captured by " + update.getMessage().getFrom().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
                            response = response + "\n" + Responses.TASK_1;
                        }
                        message.setChatId(chatId);
                        message.setText(response);
                        try {
                            execute(message); // Sending our message object to user
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case VIEW_TASK_2: {
                        response = Validator.task2(update.getMessage().getText());
                        if (response.equals(Responses.CONGRAT_2)) {
                            SendMessage message = new SendMessage();
                            UsersController.updateUserState(chatId, State.SOLVED_TASK_2);
                            DBConnection.updateUser(chatId, State.SOLVED_TASK_2);
                            message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
                            message.setChatId(chatId);
                            message.setText(response);
                            try {
                                execute(message); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            //TODO: add notification to admin
                            try{
                                Notificator.sendPost("oo whee! jam#2 captured by " + update.getMessage().getFrom().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            //and show Task 2
                            SendAudio messageAudio = new SendAudio();
                            messageAudio.setChatId(chat_id)
                                    .setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard())
                                    .setChatId(chatId)
                                    .setAudio("CQADAgADOwMAAnrMCUmeOhUVbsby9QI")
                                    .setCaption(response + "\n" + Responses.TASK_2);
                            try {
                                execute(messageAudio);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case VIEW_TASK_3_1: {
                        response = Validator.task3_1(update.getMessage().getText());
                        if (response.equals(Responses.TASK_3_2)) {
                            SendDocument message = new SendDocument();
                            UsersController.updateUserState(chatId, State.VIEW_TASK_3_2);
                            DBConnection.updateUser(chatId, State.VIEW_TASK_3_2);
//                            message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
                            message.setChatId(chatId);
                            message.setCaption(response);
                            //and show Task 3_2
                            message.setChatId(chat_id)
                                    .setChatId(chatId)
                                    .setDocument("CgADAgADQAQAAvY6SEhoXr_uno9atxYE")
                                    .setCaption("Take my treasure. Mine is yours\n" + response);
                                    //TODO: fix
//                                  //prod: 3_2  .setDocument("CgADAgADgwMAAlHYOUiEuWvIN2EqJhYE")
                            try {
                                execute(message); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            //TODO: add notification to admin
                            try{
                                Notificator.sendPost("oo whee! jam#3_1 captured by " + update.getMessage().getFrom().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            //and show Task 3_1
                            SendDocument messageAudio = new SendDocument();
                            messageAudio.setChatId(chat_id)
                                    .setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard())
                                    .setChatId(chatId)
                                    .setDocument("BQADAgADbQMAAuOBOUinrjE2XKmIXxYE")
                                    //TODO: fix
                                    //prod chat 3_1: .setDocument("BQADAgADgQMAAlHYOUg-U-bUGDyUDRYE")
                                    .setCaption(response + "\n" + Responses.TASK_3_1);
                            try {
                                execute(messageAudio);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case VIEW_TASK_3_2: {
                        response = Validator.task3_2(update.getMessage().getText(), update.getMessage().getFrom().getFirstName().toString());
                        if (response.equals(Responses.CONGRAT_3_2)) {
                            SendMessage message = new SendMessage();
                            UsersController.updateUserState(chatId, State.SOLVED_TASK_3_2);
                            DBConnection.updateUser(chatId, State.SOLVED_TASK_3_2);
                            message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
                            message.setChatId(chatId);
                            message.setText(response);
                            try {
                                execute(message); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            //TODO: add notification to admin
                            try{
                                Notificator.sendPost("oo whee! jam#3_2 captured by " + update.getMessage().getFrom().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            //and show Task 3_2
                            SendDocument messageAudio = new SendDocument();
                            messageAudio.setChatId(chat_id)
                                    .setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard())
                                    .setChatId(chatId)
                                    .setDocument("CgADAgADQAQAAvY6SEhoXr_uno9atxYE")
                                    .setCaption("Take my treasure. Mine is yours\n" + response + "\n" + Responses.TASK_3_2);
                                    //TODO: fix
//                                  //prod chat 3_2  .setDocument("CgADAgADgwMAAlHYOUiEuWvIN2EqJhYE")
                            try {
                                execute(messageAudio);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case VIEW_TASK_4_1: {
                        response = Validator.task4_1(update.getMessage().getText(), update.getMessage().getFrom().getFirstName());
                        if (response.equals(Responses.TASK_4_2)) {
                            SendMessage message = new SendMessage();
                            UsersController.updateUserState(chatId, State.VIEW_TASK_4_2);
                            DBConnection.updateUser(chatId, State.VIEW_TASK_4_2);
//                            message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
                            message.setChatId(chatId);
                            //and show Task 4_2
                            message.setChatId(chat_id)
                                    .setText("Ответ очень близко -- ну же,\n" +
                                            "\t\tмеж строк, товарищ, гляди!\n" +
                                            "\t\t\t\tИ в зной, и в зимнюю стужу,\n" +
                                            "\t\t\t\t\t\tзайди туда -- и найди!\n" +
                                            "Нет ответа -- ну что ж,\n" +
                                            "\t\tбудет и здесь праздник,\n" +
                                            "\t\t\t\tесть ответ -- хорошо!\n" +
                                            "\t\t\t\t\t\tзначит и ты соучастник!\n" +
                                            "Не нужно здесь много писать\n" +
                                            "\t\tНе нужно быть умным старцем\n" +
                                            "\t\t\t\tвсего-то хватит того,\n" +
                                            "\t\t\t\t\t\tчтоб по кнопкам \n" +
                                            "\t\t\t\t\t\t\t\tтелефона \n" +
                                            "\t\t\t\t\t\t\t\tпотыкать \n" +
                                            "\t\t\t\t\t\t\t\tпальцем!\n\n" + response);
                            //TODO: fix
//                                  //prod: 4_2  .setDocument("BLABLA")
                            try {
                                execute(message); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            //TODO: add notification to admin
                            try{
                                Notificator.sendPost("oo whee! jam#4_1 captured by " + update.getMessage().getFrom().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            //and show Task 4_1
                            SendPhoto message = new SendPhoto();
                            UsersController.updateUserState(chatId, State.VIEW_TASK_4_1);
                            DBConnection.updateUser(chatId, State.VIEW_TASK_4_1);
//                            message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
                            message.setChatId(chatId);
                            message.setCaption(response);
                            //and show Task 4_1
                            message.setChatId(chat_id)
                                    .setPhoto("AgADAgADs6sxG6jMQEpFvLHLkCFrxF97XA8ABAEAAwIAA3kAA65BAQABFgQ")
                                    .setCaption("Для того, чтобы решить это задание,\nВам не нужно знать иностранные языки.\n" +
                                            "Знаний русского языка и русской литературы будет вполне достаточно\n\n" + response);

                            try {
                                execute(message); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            SendAudio audio = new SendAudio();
                            audio.setChatId(chatId);
                            audio.setAudio("CQADAgADSAQAApC2GUrvPWnDtO5gQBYE");
                            try {
                                execute(audio); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case VIEW_TASK_4_2: {
                        response = Validator.task4_2(update.getMessage().getText());
                        if (response.equals(Responses.CONGRAT_4_2)) {
                            SendMessage message = new SendMessage();
                            UsersController.updateUserState(chatId, State.SOLVED_TASK_4_2);
                            DBConnection.updateUser(chatId, State.SOLVED_TASK_4_2);
                            message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
                            message.setChatId(chatId);
                            message.setText(response);
                            try {
                                execute(message); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            //TODO: add notification to admin
                            try{
                                Notificator.sendPost("oo whee! jam#4_2 captured by " + update.getMessage().getFrom().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            //and show Task 4_2
                            SendMessage message = new SendMessage();
                            message.setChatId(chat_id)
                                    .setText("Ответ очень близко -- ну же,\n" +
                                            "\t\tмеж строк, товарищ, гляди!\n" +
                                            "\t\t\t\tИ в зной, и в зимнюю стужу,\n" +
                                            "\t\t\t\t\t\tзайди туда -- и найди!\n" +
                                            "Нет ответа -- ну что ж,\n" +
                                            "\t\tбудет и здесь праздник,\n" +
                                            "\t\t\t\tесть ответ -- хорошо!\n" +
                                            "\t\t\t\t\t\tзначит и ты соучастник!\n" +
                                            "Не нужно здесь много писать\n" +
                                            "\t\tНе нужно быть умным старцем\n" +
                                            "\t\t\t\tвсего-то хватит того,\n" +
                                            "\t\t\t\t\t\tчтоб по кнопкам \n" +
                                            "\t\t\t\t\t\t\t\tтелефона \n" +
                                            "\t\t\t\t\t\t\t\tпотыкать \n" +
                                            "\t\t\t\t\t\t\t\tпальцем!\n\n" + response);
                            try {
                                execute(message); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case VIEW_TASK_5: {
                        response = Validator.task5(update.getMessage().getText());
                        if (response.equals(Responses.CONGRAT_5)) {
                            SendMessage message = new SendMessage();
                            UsersController.updateUserState(chatId, State.SOLVED_TASK_5);
                            DBConnection.updateUser(chatId, State.SOLVED_TASK_5);
                            message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
                            message.setChatId(chatId);
                            message.setText(response);
                            try {
                                execute(message); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            //TODO: add notification to admin
                            try{
                                Notificator.sendPost("oo whee! jam#5 captured by " + update.getMessage().getFrom().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            //and show Task 5
                            SendPhoto message = new SendPhoto();
                            UsersController.updateUserState(chatId, State.VIEW_TASK_5);
                            DBConnection.updateUser(chatId, State.VIEW_TASK_5);
//                            message.setReplyMarkup(InlineKeyboardResponses.getTasksKeyboard());
                            message.setChatId(chatId);
                            message.setCaption(response);
                            //and show Task 5
                            message.setChatId(chat_id)
                                    .setCaption("Задание 5" + response);

                            try {
                                execute(message); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                }

            }
        }
        if (update.hasMessage() && update.getMessage().hasDocument()) {
            log(String.valueOf(update.getMessage().getChatId()), "file!!", "");
            // Message contains photo
            // Set variables
            // Array with photo objects with different sizes
            // We will get the biggest photo from that array
            Document document = update.getMessage().getDocument();
            String f_id = document.getFileId();
            // Know file_id
            log(String.valueOf(update.getMessage().getChatId()), "fid = " + f_id, "");
            SendDocument msg = new SendDocument()
                    .setChatId(update.getMessage().getChatId())
                    .setDocument(f_id)
                    .setCaption("формат ответа: ");
            log(String.valueOf(update.getMessage().getChatId()), "sent!", "");
            try {
                execute(msg); // Call method to send the photo with caption
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            log(String.valueOf(update.getMessage().getChatId()), "photo!", "");
            // Message contains photo
            // Set variables
            // Array with photo objects with different sizes
            // We will get the biggest photo from that array
            List<PhotoSize> photos = update.getMessage().getPhoto();
            // Know file_id
            String f_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();
            log(String.valueOf(update.getMessage().getChatId()), "fid = " + f_id, "");
            SendPhoto msg = new SendPhoto()
                    .setChatId(update.getMessage().getChatId())
                    .setPhoto(f_id)
                    .setCaption(f_id);
            log(String.valueOf(update.getMessage().getChatId()), "sent!", "");
            try {
                execute(msg); // Call method to send the photo with caption
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (update.hasMessage() && update.getMessage().getAudio() != null) {
            log(String.valueOf(update.getMessage().getChatId()), "file!", "");
            // Message contains photo
            // Set variables
            // Array with photo objects with different sizes
            // We will get the biggest photo from that array
            Audio document = update.getMessage().getAudio();
            // Know file_id
            String f_id = document.getFileId();
            log(String.valueOf(update.getMessage().getChatId()), "fid = " + f_id, "");
            SendAudio msg = new SendAudio()
                    .setChatId(update.getMessage().getChatId())
                    .setAudio(f_id)
                    .setCaption(f_id);
            log(String.valueOf(update.getMessage().getChatId()), "sent!", "");
            try {
                execute(msg); // Call method to send the photo with caption
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            if (call_data.equals("t_1")) {
                //process user
                UsersController.updateUserState(chat_id, State.VIEW_TASK_1);
                DBConnection.updateUser(chat_id, State.VIEW_TASK_1);
                String answer = "I'm task 1 " + chat_id;
                AnswerCallbackQuery callBack = new AnswerCallbackQuery()
                        .setCallbackQueryId(update.getCallbackQuery().getId());
//                        .setMessageId(Integer.valueOf(String.valueOf(message_id)))
//                        .setText(answer);
                try {
                    execute(callBack);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                //and show Task 1
                SendMessage message = new SendMessage()
//                        .setReplyMarkup(InlineKeyboardResponses.getAttemptKeyboard(1))
                        .setChatId(chat_id)
                        .setText(Responses.TASK_1);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else if (call_data.equals("a_g")) {
                String answer = UsersController.getUsers();
                AnswerCallbackQuery callBack = new AnswerCallbackQuery()
                        .setText("get users called")
                        .setCallbackQueryId(update.getCallbackQuery().getId());
//                        .setChatId(chat_id)
//                        .setMessageId(Integer.valueOf(String.valueOf(message_id)))
//                        .setText(answer);
                Notificator.sendToAdmin(answer);
                try {
                    execute(callBack);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else if (call_data.equals("t_2")) {
                UsersController.updateUserState(chat_id, State.VIEW_TASK_2);
                DBConnection.updateUser(chat_id, State.VIEW_TASK_2);
                String answer = "And I'm task 2! " + chat_id;
                AnswerCallbackQuery callBack = new AnswerCallbackQuery()
                        .setCallbackQueryId(update.getCallbackQuery().getId());
//                        .setChatId(chat_id)
//                        .setMessageId(Integer.valueOf(String.valueOf(message_id)))
//                        .setText(answer);
                try {
                    execute(callBack);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                //and show Task 2
                SendAudio message = new SendAudio()
                        .setChatId(chat_id)
                        .setAudio("CQADAgADOwMAAnrMCUmeOhUVbsby9QI")
                        .setCaption(Responses.TASK_2);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            else if (call_data.equals("t_3_1")) {
                //and show Task 3_2
                UsersController.updateUserState(chat_id, State.VIEW_TASK_3_1);
                DBConnection.updateUser(chat_id, State.VIEW_TASK_3_1);
                String answer = "And I'm task 3_1! " + chat_id;
                AnswerCallbackQuery callBack = new AnswerCallbackQuery()
                        .setCallbackQueryId(update.getCallbackQuery().getId());
//                        .setChatId(chat_id)
//                        .setMessageId(Integer.valueOf(String.valueOf(message_id)))
//                        .setText(answer);
                try {
                    execute(callBack);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                //and show Task 2
                SendDocument message = new SendDocument()
                        .setChatId(chat_id)
                        //TODO: fix
                        //prod chat 3_1  .setDocument("CgADAgADgwMAAlHYOUiEuWvIN2EqJhYE")
                        .setDocument("BQADAgADbQMAAuOBOUinrjE2XKmIXxYE")
                        .setCaption(Responses.TASK_3_1);
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            else if (call_data.equals("t_4_1")) {
                //and show Task 4_1
                UsersController.updateUserState(chat_id, State.VIEW_TASK_4_1);
                DBConnection.updateUser(chat_id, State.VIEW_TASK_4_1);
                String answer = "And I'm task 4_1! " + chat_id;
                AnswerCallbackQuery callBack = new AnswerCallbackQuery()
                        .setCallbackQueryId(update.getCallbackQuery().getId());
//                        .setChatId(chat_id)
//                        .setMessageId(Integer.valueOf(String.valueOf(message_id)))
//                        .setText(answer);
                try {
                    execute(callBack);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                //and show Task 4_1
                SendPhoto message = new SendPhoto();
                message.setChatId(chat_id);
                message.setCaption(Responses.TASK_4_1);
                //and show Task 4_1
                message.setChatId(chat_id)
                        .setPhoto("AgADAgADs6sxG6jMQEpFvLHLkCFrxF97XA8ABAEAAwIAA3kAA65BAQABFgQ")
                        .setCaption("Для того, чтобы решить это задание,\nВам не нужно знать иностранные языки.\n" +
                                "Знаний русского языка и русской литературы будет вполне достаточно\n\n" + Responses.TASK_4_1);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                SendAudio audio = new SendAudio();
                audio.setChatId(chat_id);
                audio.setAudio("CQADAgADSAQAApC2GUrvPWnDtO5gQBYE");
                try {
                    execute(audio); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            else if (call_data.equals("t_5")) {
                //and show Task 5
                UsersController.updateUserState(chat_id, State.VIEW_TASK_5);
                DBConnection.updateUser(chat_id, State.VIEW_TASK_5);
                AnswerCallbackQuery callBack = new AnswerCallbackQuery()
                        .setCallbackQueryId(update.getCallbackQuery().getId());
//                        .setChatId(chat_id)
//                        .setMessageId(Integer.valueOf(String.valueOf(message_id)))
//                        .setText(answer);
                try {
                    execute(callBack);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                //and show Task 5
                SendPhoto message = new SendPhoto();
                message.setChatId(chat_id);
                message.setCaption(Responses.TASK_5);
                //and show Task 5
                message.setChatId(chat_id)
                        .setCaption("Задание 5" + Responses.TASK_5);
                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public String getBotUsername() {
        return System.getenv("TG_JAM_BOT_NAME");
    }

    public String getBotToken() {
        return System.getenv("TG_JAM_BOT_ID");
    }
}
