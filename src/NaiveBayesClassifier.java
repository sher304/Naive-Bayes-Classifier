import Model.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class NaiveBayesClassifier {
    private boolean applySmoothingAll;
    private List<Entity> trainDataset;

    private int yesCount = 0;
    private int noCount = 0;

    private Map<String, Integer> outlookYes = new HashMap<>();
    private Map<String, Integer> tempYes = new HashMap<>();
    private Map<String, Integer> humidityYes = new HashMap<>();
    private Map<String, Integer> windyYes = new HashMap<>();
    private Map<String, Integer> outlookNo = new HashMap<>();
    private Map<String, Integer> tempNo = new HashMap<>();
    private Map<String, Integer> humidityNo = new HashMap<>();
    private Map<String, Integer> windyNo = new HashMap<>();

    public NaiveBayesClassifier(List<Entity> trainDataset) {
        this.trainDataset = trainDataset;
    }

    public void train() {
        for (Entity data : trainDataset) {
            if (data.getPlay().equals("yes")) {
                yesCount++;
                outlookYes.merge(data.getOutlook(), 1, Integer::sum);
                tempYes.merge(data.getTemperature(), 1, Integer::sum);
                humidityYes.merge(data.getHumidity(), 1, Integer::sum);
                windyYes.merge(data.getWindy(), 1, Integer::sum);
            } else {
                noCount++;
                outlookNo.merge(data.getOutlook(), 1, Integer::sum);
                tempNo.merge(data.getTemperature(), 1, Integer::sum);
                humidityNo.merge(data.getHumidity(), 1, Integer::sum);
                windyNo.merge(data.getWindy(), 1, Integer::sum);
            }
        }
    }
    public String predict(Entity input) {
        int total = yesCount + noCount;

        double probYes = (double) yesCount / total;
        double probNo = (double) noCount / total;

        probYes *= getProbability(outlookYes, input.getOutlook(), yesCount, 3);
        probYes *= getProbability(tempYes, input.getTemperature(), yesCount, 3);
        probYes *= getProbability(humidityYes, input.getHumidity(), yesCount, 2);
        probYes *= getProbability(windyYes, input.getWindy(), yesCount, 2);

        probNo *= getProbability(outlookNo, input.getOutlook(), noCount, 3);
        probNo *= getProbability(tempNo, input.getTemperature(), noCount, 3);
        probNo *= getProbability(humidityNo, input.getHumidity(), noCount, 2);
        probNo *= getProbability(windyNo, input.getWindy(), noCount, 2);

        return (probYes > probNo) ? "yes" : "no";
    }

    private double getProbability(Map<String, Integer> attributeMap, String value, int classCount, int amountAtrb) {
        int count = attributeMap.getOrDefault(value, 0);

        if (applySmoothingAll) {
            return (count + 1.0) / (classCount + amountAtrb);
        } else {
            if (count == 0) {
                return 1.0 / (classCount + amountAtrb);
            } else {
                return (double) count / classCount;
            }
        }
    }

    public void setApplySmoothingAll(boolean applySmoothingAll) {
        this.applySmoothingAll = applySmoothingAll;
    }
}
