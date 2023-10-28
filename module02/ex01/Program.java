public class Program {
   public static void main(String[] args) {
    Similarity similarity = new Similarity(args[0], args[1]);
    similarity.fillDict();
    similarity.fillVectors();
    System.out.printf("Similarity = %.4f\n", similarity.calculateSimilarity());
    similarity.writeSimilarityDict();
   } 
}
