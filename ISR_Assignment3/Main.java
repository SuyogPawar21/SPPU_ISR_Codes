import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        String[] fileNames = {"Document1", "Document2", "Document3", "Document4", "Document5"};
        ArrayList<ArrayList<String>> corpseWords = new ArrayList<>();
        BufferedReader reader;

        for (String fileName : fileNames) {
            reader = new BufferedReader(new FileReader("src/" + fileName));
            corpseWords.add(new ArrayList<>(List.of(reader.readLine().toLowerCase().split("[ .,()'\"]+"))));
            reader.close();
        }

        HashSet<String> stopWords = new HashSet<>();
        reader = new BufferedReader(new FileReader("src/StopWords"));
        while (reader.ready()) {
            stopWords.add(reader.readLine());
        }
        reader.close();

        ArrayList<HashMap<String, ArrayList<Integer>>> invertedIndex = new ArrayList<>();

        for (int i = 0; i < fileNames.length; i++) {
            invertedIndex.add(new HashMap<>());
            for (int j = 0; j < corpseWords.get(i).size(); j++) {
                String word = corpseWords.get(i).get(j);
                if (stopWords.contains(word)) continue;
                if (invertedIndex.get(i).containsKey(word)) {
                    invertedIndex.get(i).get(word).add(j);
                } else invertedIndex.get(i).put(word, new ArrayList<>(List.of(j)));
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Query: ");
        String query = scanner.next();
        scanner.close();

        System.out.println("Result");
        for (int i = 0; i < fileNames.length; i++) {
            System.out.print("D"+(i+1)+": ");
            if (invertedIndex.get(i).containsKey(query)) {
                System.out.println(invertedIndex.get(i).get(query));
            } else System.out.println("Not Found");
        }

    }
}