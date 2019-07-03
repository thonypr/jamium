import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Seeker {

    static List<String> wordsInfo = new ArrayList<>();
    static List<Verb> verbs = new ArrayList<>();


    public static void readFile() throws IOException, URISyntaxException {
        URL res = Seeker.class.getClassLoader().getResource("vocabulary.txt");
        final Map<String, String> env = new HashMap<>();
        final String[] array = res.toString().split("!");
        final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);
        final Path path = fs.getPath(array[1]);
        System.out.println("path = " + path.toString());
//        File file = path.toFile();
//        String absolutePath = file.getAbsolutePath();
        FileReader fr = new FileReader(path.toString());
        Scanner scan = new Scanner(fr);

        while (scan.hasNextLine()) {
            wordsInfo.add(scan.nextLine());
        }

        fr.close();
    }

    public static void parseFileInfo() {

        for (String wordInfo : wordsInfo) {
            String[] splitResult = wordInfo.split(";");
            Verb verb = new Verb(splitResult[0].charAt(0), splitResult[1], splitResult[2], splitResult[3], splitResult[4]);
            verbs.add(verb);
        }
    }

    public static List<Verb> findFormsByVerb(String verb) {
        char letter = verb.charAt(0);
        List<Verb> filteredVerbsByLetter = verbs.stream().filter(v -> v.getLetter() == letter).collect(Collectors.toList());
        return filteredVerbsByLetter.stream().filter(v -> v.getInfinitive().equals(verb)).collect(Collectors.toList());
    }
}
