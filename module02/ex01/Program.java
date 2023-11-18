public class Program {
   public static void main(String[] args) {
      if (args.length != 2) {
         System.err.println("Usage: java Program <file1> <file2>");
         System.exit(1);
      }
    Similarity similarity = new Similarity(args[0], args[1]);
    similarity.fillDict();
    similarity.fillVectors();
    System.out.printf("Similarity = %.4f\n", similarity.calculateSimilarity());
    similarity.writeSimilarityDict();
   } 
}
