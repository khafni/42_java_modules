import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class Similarity {
   Set<String> dictionary;
   List<Integer> A;
   List<Integer> B;
   String file1Path;
   String file2Path;


   public Similarity (String file1Path, String file2Path) {
    dictionary = new TreeSet<>();
    A = new ArrayList<>();
    B = new ArrayList<>();
    this.file1Path = file1Path;
    this.file2Path = file2Path;
   }

   public void fillDict() {
    try (BufferedReader reader1 = new BufferedReader(new FileReader(file1Path));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2Path))) {
        String line;
        while ((line = reader1.readLine()) != null) {
            String[] words = line.split(" ");
            for (String word: words) {
                dictionary.add(word);
            }
        }   
        while ((line = reader2.readLine()) != null) {
            String[] words = line.split(" ");
            for (String word: words) {
                dictionary.add(word);
            }
        }
    } catch (IOException io) {
        System.out.println(io.getMessage());
        System.exit(1);
    }
}

   public void fillVectors() {
    List<String> dictAsList = new ArrayList<>(dictionary);
    for (int i = 0; i < dictionary.size(); i++) {
        A.add(0);
        B.add(0);
    }
    try (BufferedReader reader1 = new BufferedReader(new FileReader(file1Path));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2Path))) {
        String line;
        while ((line = reader1.readLine()) != null) {
            String[] words = line.split(" ");
            for (String word : words) {
                int index = dictAsList.indexOf(word);
                A.set(index, A.get(index) + 1);
            }
        }
        while ((line = reader2.readLine()) != null) {
            String[] words = line.split(" ");
            for (String word : words) {
                int index = dictAsList.indexOf(word);
                B.set(index, B.get(index) + 1);
            }
        }
    } catch(IOException io) {
        System.out.println(io.getMessage());
    }
   }

   public void writeSimilarityDict() {
    try(BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"))) {
        for (String word : dictionary) {
            writer.append(word);
            writer.newLine();
        }
    } catch (IOException io) {
        System.out.println(io.getMessage()); 
    }
   }

   public double calculateSimilarity() {
    int scalarProduct = IntStream.range(0, A.size())
        .mapToObj(i -> A.get(i) * B.get(i))
        .mapToInt(Integer::intValue)
        .sum();

    double vectorAMaganatude = Math.sqrt(A.stream()
                                 .map(x -> x * x)
                                 .mapToInt(Integer::intValue)
                                .sum());
    double vectorBMaganatude = Math.sqrt(B.stream()
                                 .map(x -> x * x)
                                 .mapToInt(Integer::intValue)
                                .sum());
    if (vectorAMaganatude == 0 || vectorBMaganatude == 0)
        return 0;
    return scalarProduct / (vectorAMaganatude * vectorBMaganatude);
   }

}
