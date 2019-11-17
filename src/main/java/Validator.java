import java.util.*;

public class Validator {

    private static ArrayList<String> nopes = new ArrayList<String>() {
        {
            add("Нет :( Попробуй ещё!");
            add("К сожалению, нет.. Попробуй ещё!");
            add("Нет :(\nЕсли нужна помощь по заданию - просто отправь боту сообщение вида: /help и свой вопрос");
            add("Нет :(\nЕсли прям срочно нужно задать вопрос - пиши мне @aprimako");
        }
    };

    public static String task1(String answer) {
        String result = "";
        Random r = new Random();

        String correctAnswer = "1000003";
        List<String> closeAnswers = new ArrayList<>();
        closeAnswers.add("1000000");
        closeAnswers.add("FIRSTPRIMEAFTERMILLION");

        if(answer.equals(correctAnswer)) {
            result = Responses.CONGRAT_1;
        }
        else if (closeAnswers.contains(answer)) {
            result = Responses.CLOSE_1;
        }
        else {
            try{
                result = nopes.get(r.nextInt(nopes.size()));
            }
            catch (IndexOutOfBoundsException iob)
            {
                result = nopes.get(0);
            }
        }
        return result;
    }

    public static String task2(String answer) {
        String result = "";
        Random r = new Random();

        String correctAnswer = System.getenv("TG_JAM_2_ANSWER");
        List<String> closeAnswers = new ArrayList<>();
        closeAnswers.add(System.getenv("TG_JAM_2_CLOSE"));

        if(answer.equals(correctAnswer)) {
            result = Responses.CONGRAT_2;
        }
        else if (closeAnswers.contains(answer)) {
            result = Responses.CLOSE_2;
        }
        else {
            try{
                result = nopes.get(r.nextInt(nopes.size()));
            }
            catch (IndexOutOfBoundsException iob)
            {
                result = nopes.get(0);
            }
        }
        return result;
    }

    public static String task3_1(String answer) {
        String result = "";
        Random r = new Random();

        String correctAnswer = System.getenv("TG_JAM_3_1_ANSWER");
        List<String> closeAnswers = new ArrayList<>();
        closeAnswers.add(System.getenv("TG_JAM_3_1_CLOSE"));

        if(answer.equals(correctAnswer)) {
//            result = Responses.CONGRAT_3_1;
            result = Responses.TASK_3_2;
        }
        else if (closeAnswers.contains(answer.toLowerCase())) {
            result = Responses.CLOSE_3_1;
        }
        else {
            try{
                result = nopes.get(r.nextInt(nopes.size()));
            }
            catch (IndexOutOfBoundsException iob)
            {
                result = nopes.get(0);
            }
        }
        return result;
    }

    public static String task3_2(String answer, String correctAnswer) {
        String result = "";
        Random r = new Random();

//        String correctAnswer = System.getenv("TG_JAM_3_2_ANSWER");
        List<String> closeAnswers = new ArrayList<>();
        closeAnswers.add(System.getenv("TG_JAM_3_2_CLOSE"));

        if(answer.toLowerCase().equals(correctAnswer.toLowerCase())) {
            result = Responses.CONGRAT_3_2;
        }
        else if (closeAnswers.contains(answer.toLowerCase())) {
            result = Responses.CLOSE_3_2;
        }
        else {
            try{
                result = nopes.get(r.nextInt(nopes.size()));
            }
            catch (IndexOutOfBoundsException iob)
            {
                result = nopes.get(0);
            }
        }
        return result;
    }
    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }
    public static String task4_1(String answer, String firstname) {
        String result = "";
        Random r = new Random();

        String correctAnswer = System.getenv("TG_JAM_4_1_ANSWER");
        String[] closeAnswers = {System.getenv("TG_JAM_4_1_CLOSE"), "трамваи"};

        String[] cerceau = {"просо", "мясо", "колесо", "двинятин", "лассо", "крупье", "серсо"};
        String[] poets = {"блок", "пушкин", "есенин", "цветаева", "ахматова"};
        String[] berlios = {"берлиоз", "берлиос"};
        String[] master = {"мастер", "маргарита", "мастер и маргарита", "булгаков", "аннушка"};
        String[] rhyme = {"рифма", "стихи", "стих", "поэт", "поэты", "литература", "рифмуются", "ямб"};

        System.out.println("answer = " + answer + "; need = " + correctAnswer);

        if(answer.toLowerCase().equals(correctAnswer.toLowerCase())) {
//            result = Responses.CONGRAT_4_1;
            result = Responses.TASK_4_2;
        }
        else if (stringContainsItemFromList(answer.toLowerCase(), closeAnswers)) {
            result = Responses.CLOSE_4_1 + "\n" + Responses.TASK_4_1;
        }
        else if (Arrays.asList(cerceau).contains(answer.toLowerCase()) ||
        stringContainsItemFromList(answer.toLowerCase(), cerceau)) {
            String[] responses = {firstname + ".\n" + firstname.toUpperCase() + "!\nЭто я не вам :)",
                    "Нужна вам подачка - берите!",
                    "Да заберите вы себе это очко!"};
            int length = 3;

            try{
                result = responses[(r.nextInt(length))];
            }
            catch (IndexOutOfBoundsException iob)
            {
                result = responses[0] + "\n" + Responses.TASK_4_1;
            }
        }
        else if (Arrays.asList(poets).contains(answer.toLowerCase()) ||
        stringContainsItemFromList(answer.toLowerCase(), poets)) {
            result = "Направление правильное - речь о русских поэтах, осталось выбрать нужного!" + "\n" + Responses.TASK_4_1;
        }
        else if (Arrays.asList(rhyme).contains(answer.toLowerCase()) ||
                stringContainsItemFromList(answer.toLowerCase(), rhyme)) {
            result = "Отлично подмечено - строчки и правда рифмуются!\nЗдорово, да?\nНо что с этим делать дальше?\nВспомнить условие :)" + "\n" + Responses.TASK_4_1;
        }
        else if (Arrays.asList(berlios).contains(answer.toLowerCase()) ||
        stringContainsItemFromList(answer.toLowerCase(), berlios)) {
            result = "Направление верное!\n" +
                    "Осталось понять, с чем чаще всего ассоциируется Берлиоз (не забывай про текстовую подсказку в задании!)" + "\n" + Responses.TASK_4_1;
        }
        else if (Arrays.asList(master).contains(answer.toLowerCase()) ||
        stringContainsItemFromList(answer.toLowerCase(), master)) {
            result = "Направление верное!\n" +
                    "Нас интересует персонаж из Мастера и Маргариты, но какой?" + "\n" + Responses.TASK_4_1;
        }
        else {
            try{
                result = nopes.get(r.nextInt(nopes.size()));
            }
            catch (IndexOutOfBoundsException iob)
            {
                result = nopes.get(0) + "\n" + Responses.TASK_4_1;
            }
        }
        return result;
    }

    public static String task4_2(String answer) {
        String result = "";
        Random r = new Random();

        String correctAnswer = System.getenv("TG_JAM_4_2_ANSWER");
        List<String> closeAnswers = new ArrayList<>();
        closeAnswers.add(System.getenv("TG_JAM_4_2_CLOSE"));
        String[] close = {"т9"};
        String[] word = {"хорошо"};
        String[] metro = {"метро", "метрополитен"};
        String[] mayak = {"маяковск"};

        if(answer.toLowerCase().equals(correctAnswer.toLowerCase())) {
            result = Responses.CONGRAT_4_2;
        }
        else if (Arrays.asList(close).contains(answer.toLowerCase()) || stringContainsItemFromList(answer.toLowerCase(), close)) {
            result = "Очень близко!" + "\n" + Responses.TASK_4_2;
        }
        else if (Arrays.asList(word).contains(answer.toLowerCase()) || stringContainsItemFromList(answer.toLowerCase(), word)) {
            result = "Ну разве ж это число?\nКонечно нет, \nно да, с этим словом надо что-то сделать :)" + "\n" + Responses.TASK_4_2;
        }
        else if (Arrays.asList(metro).contains(answer.toLowerCase()) || stringContainsItemFromList(answer.toLowerCase(), metro)) {
            result = "Да, теперь речь идёт о метрополитене, причём в том же городе, \nгде ходят трамваи из первой части :)" + "\n" + Responses.TASK_4_2;
        }
        else if (Arrays.asList(mayak).contains(answer.toLowerCase()) || stringContainsItemFromList(answer.toLowerCase(), mayak)) {
            result = "Верно, речь всё ещё о Маяковском!" + "\n" + Responses.TASK_4_2;
        }
        else {
            try{
                result = nopes.get(r.nextInt(nopes.size()));
            }
            catch (IndexOutOfBoundsException iob)
            {
                result = nopes.get(0) + "\n" + Responses.TASK_4_2;
            }
        }
        return result;
    }

}
