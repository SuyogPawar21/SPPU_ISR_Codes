import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {

        FileReader fileReader = new FileReader("Document");
        FileReader fileReader1 = new FileReader("StopWords");
        FileWriter fileWriter = new FileWriter("Output");

        BufferedReader documentReader = new BufferedReader(fileReader);
        BufferedReader stopWordsReader = new BufferedReader(fileReader1);
        BufferedWriter  outputWriter = new BufferedWriter(fileWriter);

        ArrayList<String> documentWords = new ArrayList<>();
        ArrayList<String> stopWords = new ArrayList<>();

        for (String line; (line = documentReader.readLine()) != null;) {
            documentWords.addAll(List.of(line.split("[\\s,.()]+")));
        }

        for (String line; (line = stopWordsReader.readLine()) != null;) {
            stopWords.addAll(List.of(line.split("[\\s,.()]+")));
        }

        documentWords.replaceAll(String::toLowerCase);

        for (String stopWord : stopWords) {
            documentWords.removeIf(s -> Objects.equals(s, stopWord));
        }

        HashMap<String, String> suffixDict = new HashMap<>();
        suffixDict.put("encompassing", "encompass");
        suffixDict.put("development", "develop");
        suffixDict.put("systems", "system");
        suffixDict.put("performing", "perform");
        suffixDict.put("tasks", "task");
        suffixDict.put("understanding", "understand");
        suffixDict.put("recognizing", "recognize");
        suffixDict.put("patterns", "pattern");
        suffixDict.put("solving", "solve");
        suffixDict.put("problems", "problem");
        suffixDict.put("learning", "learn");
        suffixDict.put("designed", "design");
        suffixDict.put("amounts", "amount");
        suffixDict.put("insights", "insight");
        suffixDict.put("autonomous", "auto");
        suffixDict.put("decision", "decide");
        suffixDict.put("algorithms", "algorithm");
        suffixDict.put("programming", "program");
        suffixDict.put("applications", "application");
        suffixDict.put("fields", "field");
        suffixDict.put("transportation", "transport");
        suffixDict.put("entertainment", "entertain");
        suffixDict.put("transformation", "transform");
        suffixDict.put("ethical", "ethic");
        suffixDict.put("societal", "society");
        suffixDict.put("implications", "implicate");


        HashMap<String, Integer> output = new HashMap<>();

        for (String documentWord : documentWords) {
            documentWord = suffixDict.getOrDefault(documentWord, documentWord);
            if (output.containsKey(documentWord)) {
                output.put(documentWord, output.get(documentWord) + 1);
            } else output.put(documentWord, 1);
        }

        outputWriter.write(output + "\n");

        outputWriter.close();
        documentReader.close();
        stopWordsReader.close();
    }
}