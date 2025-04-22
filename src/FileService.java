import Model.Entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class FileService {

    public List<Entity> loadData(String filename) {
        List<Entity> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length != 5) {
                    System.out.println("Invalid line: " + line);
                    continue;
                }

                Entity data = new Entity(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim()
                );

                dataList.add(data);
            }

        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return dataList;
    }
}
