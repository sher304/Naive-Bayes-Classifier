import Model.Entity;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        List<Entity> entities = fileService.loadData("src/entyties.txt");

        Collections.shuffle(entities);
        List<Entity> testSet = entities.subList(0, 2);

        NaiveBayesClassifier naiveBayesClassifier = new NaiveBayesClassifier(testSet);
        naiveBayesClassifier.train();
        List<Entity> trainSet = entities.subList(2, entities.size());

        for (Entity test : trainSet) {
            String actual = test.getPlay();
            String predicted = naiveBayesClassifier.predict(test);
            System.out.println("Predicted: " + predicted + " | Actual: " + actual);
        }

        calculateMeasures(trainSet, naiveBayesClassifier);
        naiveBayesClassifier.setApplySmoothingAll(true);
    }

    public static void calculateMeasures(List<Entity> testSet, NaiveBayesClassifier naiveBayesClassifier) {
        int tpYes = 0, fpYes = 0, fnYes = 0;
        int tpNo = 0, fpNo = 0, fnNo = 0;
        int correct = 0;

        for (int i = 0; i < testSet.size(); i++) {
            Entity test = testSet.get(i);
            String actual = test.getPlay();
            String predicted = naiveBayesClassifier.predict(test);

            if (actual.equals(predicted)) correct++;

            if (predicted.equals("yes")) {
                if (actual.equals("yes")) tpYes++;
                else fpYes++;
            }
            if (predicted.equals("no")) {
                if (actual.equals("no")) tpNo++;
                else fpNo++;
            }
            if (actual.equals("yes") && !predicted.equals("yes")) fnYes++;
            if (actual.equals("no") && !predicted.equals("no")) fnNo++;
        }

        double accuracy = (double) correct / testSet.size();
        System.out.println("Accuracy: " + accuracy);

        double precYes = tpYes + fpYes == 0 ? 0 : (double) tpYes / (tpYes + fpYes);
        double recYes = tpYes + fnYes == 0 ? 0 : (double) tpYes / (tpYes + fnYes);
        double f1Yes = (precYes + recYes) == 0 ? 0 : 2 * precYes * recYes / (precYes + recYes);


        double precNo = tpNo + fpNo == 0 ? 0 : (double) tpNo / (tpNo + fpNo);
        double recNo = tpNo + fnNo == 0 ? 0 : (double) tpNo / (tpNo + fnNo);
        double f1No = (precNo + recNo) == 0 ? 0 : 2 * precNo * recNo / (precNo + recNo);

        System.out.println("YES Precision: " + precYes + "Recall: " + recYes +" F1: " + f1Yes);
        System.out.println("NO Precision: " + precNo + "Recall: " + recNo +" F1: " + f1No);
    }
}