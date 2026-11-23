package geneticalgorithm;

public class CommonUtils {

    public static void decode(KnapsackProblem kp, Individual individual) {

        Boolean[] chromosome = (individual != null) ? individual.getChromosome().clone() : null;
        decode(kp, chromosome);
    }

    public static void decode(KnapsackProblem kp, Boolean[] chromosome) {
        
        if (kp == null || chromosome == null || kp.getWeight() == null) {
            System.out.println("[]");
            return;
        }

        int[] weight = kp.getWeight();
        int n = Math.min(weight.length, chromosome.length);

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(Boolean.TRUE.equals(chromosome[i]) ? weight[i] : 0);
        }
        sb.append("]");

        System.out.println(sb.toString());
    }
}