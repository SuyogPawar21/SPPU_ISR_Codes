import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        String[] docNames = {"Document1", "Document2", "Document3", "Document4", "Document5"};

        BufferedReader[] readersList = new BufferedReader[5];
        ArrayList<ArrayList<String>> documents = new ArrayList<>();
        HashSet<String> uniqueWords = new HashSet<>();

        for (int i = 0; i < docNames.length; i++) {
            readersList[i] = new BufferedReader(new FileReader(docNames[i]));
            documents.add(new ArrayList<>(Arrays.asList(readersList[i].readLine()
                    .toLowerCase().split("[\\s.]+"))));
            uniqueWords.addAll(documents.get(i));
        }

        ArrayList<ArrayList<Double>> normalizedTermFrequencies = getNormalizedTermFrequencies(uniqueWords, documents);
        ArrayList<ArrayList<ArrayList<Double>>> clusters = singlePassAlgorithm(normalizedTermFrequencies, 0.13);
    }

    private static ArrayList<ArrayList<Double>> getNormalizedTermFrequencies(HashSet<String> uniqueWords, ArrayList<ArrayList<String>> documents) {
        ArrayList<String> vocabulary = new ArrayList<>(uniqueWords);
        ArrayList<ArrayList<Double>> normalizedTermFrequencies = new ArrayList<>();

        for (List<String> document : documents) {
            List<Integer> frequencyArray = new ArrayList<>(Collections.nCopies(vocabulary.size(), 0));
            int totalTerms = document.size();

            for (String word : document) {
                int index = vocabulary.indexOf(word);
                if (index != -1) {
                    frequencyArray.set(index, frequencyArray.get(index) + 1);
                }
            }

            ArrayList<Double> normalizedFrequencies = new ArrayList<>();
            for (int count : frequencyArray) {
                normalizedFrequencies.add((double) count / totalTerms);
            }
            normalizedTermFrequencies.add(normalizedFrequencies);
        }
        return normalizedTermFrequencies;
    }

    private static double euclideanDistance(List<Double> a, List<Double> b) {
        double distance = 0;
        for (int i = 0; i < a.size(); i++) {
            distance += Math.pow(a.get(i) - b.get(i), 2);
        }
        return Math.sqrt(distance);
    }

    private static ArrayList<Double> calculateCentroid(ArrayList<ArrayList<Double>> cluster) {
        ArrayList<Double> centroid = new ArrayList<>();
        int clusterSize = cluster.size();
        int clusterDocumentSize= cluster.get(0).size();

        for (int j = 0; j < clusterDocumentSize; j++) {
            double sum = 0;
            for (int i = 0; i < clusterSize; i++) {
                sum += cluster.get(i).get(j);
            }
            centroid.add(sum / clusterSize);
        }

        return centroid;
    }

    private static ArrayList<ArrayList<ArrayList<Double>>> singlePassAlgorithm(ArrayList<ArrayList<Double>> normalizedTermFrequencies,
                                                                               double threshold) {
        ArrayList<ArrayList<ArrayList<Double>>> clusters = new ArrayList<>();
        ArrayList<ArrayList<Integer>> clusterInfo = new ArrayList<>();
        int documentCount = normalizedTermFrequencies.size();

        clusters.add(new ArrayList<>());
        clusters.get(0).add(new ArrayList<>(normalizedTermFrequencies.get(0)));
        clusterInfo.add(new ArrayList<>());
        clusterInfo.get(0).add(1);

        for (int i = 1; i < documentCount; i++) {
            ArrayList<Double> document = normalizedTermFrequencies.get(i);
            boolean clusterAssigned = false;
            
            for (int j = 0; j < clusters.size(); j++) {
                ArrayList<Double> clusterCentroid = calculateCentroid(clusters.get(j));
                
                if (euclideanDistance(clusterCentroid, document) <= threshold) {
                    clusters.get(j).add(document);
                    clusterAssigned = true;
                    clusterInfo.get(j).add(i+1);
                    break;
                }
            }

            if (!clusterAssigned) {
                clusters.add(new ArrayList<>());
                clusters.get(clusters.size() - 1).add(new ArrayList<>(document));
                clusterInfo.add(new ArrayList<>());
                clusterInfo.get(clusterInfo.size()-1).add(i+1);
            }
        }
        for (int i = 0; i < clusterInfo.size(); i++) {
            System.out.println("Cluster" + (i+1) + ": " + clusterInfo.get(i));
        }
        return clusters;
    }
}