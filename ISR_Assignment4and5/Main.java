import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Query(q): ");
        String query = scanner.nextLine();

        ArrayList<Integer> answerSet = new ArrayList<>();
        System.out.print("Size of Answer Set(A): ");
        int answerSetSize = scanner.nextInt();

        for (int i = 0; i < answerSetSize; i++) {
            System.out.print("Answer[" + (i + 1) + "]: ");
            int answer = scanner.nextInt();
            answerSet.add(answer);
        }

        ArrayList<Integer> relevantSet = new ArrayList<>();
        System.out.print("Size of Relevant Set(Rq): ");
        int relevantSetSize = scanner.nextInt();

        for (int i = 0; i < relevantSetSize; i++) {
            System.out.print("Relevant[" + (i + 1) + "]: ");
            int relevant = scanner.nextInt();
            relevantSet.add(relevant);
        }

        System.out.println("\n\nMetrics");
        System.out.println("Precision: " + calculatePrecision(relevantSet, answerSet));
        System.out.println("Recall: " + calculateRecall(relevantSet, answerSet));
        System.out.println("FMeasure: " + calculateFMeasure(relevantSet, answerSet));
        System.out.println("EMeasure: " + calculateEMeasure(answerSet, relevantSet));

    }

    private static int countRelevantDocsRetrieved(ArrayList<Integer> relevantSet, ArrayList<Integer> answerSet) {
        int count = 0;
        for (Integer integer : answerSet) {
            if (relevantSet.contains(integer)) {
                count++;
            }
        }
        return count;
    }

    private static double calculatePrecision(ArrayList<Integer> relevantSet, ArrayList<Integer> answerSet) {
        return countRelevantDocsRetrieved(relevantSet, answerSet) / (double) answerSet.size();
    }

    private static double calculateRecall(ArrayList<Integer> relevantSet, ArrayList<Integer> answerSet) {
        return countRelevantDocsRetrieved(relevantSet, answerSet) / (double) relevantSet.size();
    }

    private static double calculateFMeasure(ArrayList<Integer> relevantSet, ArrayList<Integer> answerSet) {
        double precision = calculatePrecision(relevantSet, answerSet);
        double recall = calculateRecall(relevantSet, answerSet);
        return (2 * precision * recall) / (precision + recall);
    }

    private static double calculateEMeasure(ArrayList<Integer> relevantSet, ArrayList<Integer> answerSet) {
        double precision = calculatePrecision(relevantSet, answerSet);
        double recall = calculateRecall(relevantSet, answerSet);
        return (precision * recall) / (precision + recall + 1e-6);
    }
}