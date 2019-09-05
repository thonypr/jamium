import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Validator {

    private static ArrayList<String> nopes = new ArrayList<String>() {
        {
            add("Нет :( Попробуй ещё!");
            add("К сожалению, нет.. Попробуй ещё!");
            add("Хорошая попытка, но нет.. Попробуй ещё!");
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

    public static String task3(String answer) {
        String result = "";
        Random r = new Random();

        String correctAnswer = System.getenv("TG_JAM_3_ANSWER");
        List<String> closeAnswers = new ArrayList<>();
        closeAnswers.add(System.getenv("TG_JAM_3_CLOSE"));

        if(answer.equals(correctAnswer)) {
            result = Responses.CONGRAT_3;
        }
        else if (closeAnswers.contains(answer)) {
            result = Responses.CLOSE_3;
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
}
