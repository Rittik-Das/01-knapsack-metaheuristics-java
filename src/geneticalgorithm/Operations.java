package geneticalgorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Operations {

    static Random random = new Random();

    private static int populationSize;
    private static int noOfParents;
    private static int noOfOffsprings;

    private static int tournamentSize;

    public static int getPopulationSize() {
        return populationSize;
    }

    public static void setPopulationSize(int populationSize) throws IllegalArgumentException {

        if (populationSize < 4)
            throw new IllegalArgumentException("Population size must be at least 4.");

        Operations.populationSize = populationSize;

        int half = populationSize / 2;

        noOfParents = (half % 2 == 0) ? half : half + 1;

        noOfOffsprings = noOfParents;
    }

    public static int getNoOfParents() {
        return noOfParents;
    }

    public static int getNoOfOffsprings() {
        return noOfOffsprings;
    }

    public static int getTournamentSize() {
        return tournamentSize;
    }

    public static void setTournamentSize(int tournamentSize) throws IllegalArgumentException {

        if (tournamentSize < 1 || tournamentSize > populationSize)
            throw new IllegalArgumentException("Tournament size must be between 1 and population size.");

        Operations.tournamentSize = tournamentSize;
    }

    public static void initializePopulation(KnapsackProblem kp, Individual[] population) {

        int[] weight = kp.getWeight();
        int[] profit = kp.getProfit();
        int noOfElements = kp.getNoOfElements();
        int capacity = kp.getCapacity();

        int count = 0;

        Set<List<Boolean>> uniqueSamples = new HashSet<>();

        while (count < populationSize) {
            Boolean[] sampleChromosome = new Boolean[noOfElements];
            int totalWeightOfSample = 0;
            int fitnessOfSample = 0;

            for (int i = 0; i < noOfElements; i++) {
                Boolean isSelected = random.nextBoolean();

                sampleChromosome[i] = isSelected;
                if (isSelected) {
                    totalWeightOfSample += weight[i];
                    fitnessOfSample += profit[i];
                }
            }

            if (totalWeightOfSample > capacity)
                continue;

            List<Boolean> currentChromosome = Arrays.asList(sampleChromosome);

            if (!uniqueSamples.add(currentChromosome))
                continue;

            population[count] = new Individual(sampleChromosome, fitnessOfSample);

            if (fitnessOfSample > kp.getMaxProfit()) {
                kp.setMaxProfit(fitnessOfSample);
                kp.setMaxValueCombination(sampleChromosome.clone());
            }
            count++;
        }
    }

    public static void selectParent(Individual[] population, Individual[] parents) {

        // 1. Roulette-Wheel selection of parent

        /*
         * // Calculating percentage
         * 
         * int totalFitnessOfPopulation = 0;
         * for (Individual i : population) {
         * totalFitnessOfPopulation += i.getFitness();
         * }
         * 
         * double[] cumulativePercentage = new double[population.length];
         * double cumulativePercentageOfPreviousSample = 0;
         * 
         * for (int i = 0; i < population.length; i++) {
         * 
         * double currentSamplePercentage = (double) population[i].getFitness() /
         * totalFitnessOfPopulation;
         * population[i].setPercentageShare(currentSamplePercentage);
         * 
         * cumulativePercentage[i] = cumulativePercentageOfPreviousSample +
         * currentSamplePercentage;
         * cumulativePercentageOfPreviousSample = cumulativePercentage[i];
         * }
         * 
         * for (int i = 0; i < noOfParents; i++) {
         * double num = Math.random();
         * 
         * if (num <= cumulativePercentage[i]) {
         * parents[i] = new Individual(population[i].getChromosome().clone(),
         * population[i].getFitness());
         * break;
         * }
         * }
         */

        // 2. Tournament selection

        int count = 0;
        int previousBestIndex = -1;

        while (count < noOfParents) {

            Set<Integer> randomIndices = new HashSet<>();
            int bestIndex = -1;
            int bestFitness = Integer.MIN_VALUE;

            while (randomIndices.size() < tournamentSize) {
                int randomIndex = random.nextInt(population.length);
                if (!randomIndices.add(randomIndex))
                    continue;
                int currentFitness = population[randomIndex].getFitness();
                if (currentFitness > bestFitness) {
                    bestIndex = randomIndex;
                    bestFitness = currentFitness;
                }
            }

            if (previousBestIndex == bestIndex)
                continue;

            Individual selectedIndividual = population[bestIndex];
            parents[count] = new Individual(selectedIndividual.getChromosome().clone(),
                    selectedIndividual.getFitness());

            previousBestIndex = bestIndex;
            count++;
        }
    }

    public static void singlePointCrossover(KnapsackProblem kp, Individual[] parents, Individual[] offsprings) {

        int[] weight = kp.getWeight();
        int[] profit = kp.getProfit();
        int noOfElements = kp.getNoOfElements();
        int capacity = kp.getCapacity();

        for (int i = 0; i < parents.length; i += 2) {
            Individual parentA = parents[i];
            Individual parentB = parents[i + 1];

            // cut = 2 means one part 0-2 and another part is 3-end
            // and end is noOfElements-1
            int cut = random.nextInt(noOfElements - 1);

            Boolean[] chromosomeA = parentA.getChromosome();
            Boolean[] chromosomeB = parentB.getChromosome();

            Boolean[] sampleChromosome1 = chromosomeA.clone();
            Boolean[] sampleChromosome2 = chromosomeB.clone();

            for (int j = cut + 1; j < noOfElements; j++) {
                sampleChromosome1[j] = chromosomeB[j];
                sampleChromosome2[j] = chromosomeA[j];
            }

            int totalWeightOfSample1 = 0;
            int totalWeightOfSample2 = 0;

            int fitnessOfSample1 = 0;
            int fitnessOfSample2 = 0;

            for (int j = 0; j < noOfElements; j++) {

                if (sampleChromosome1[j]) {
                    totalWeightOfSample1 += weight[j];
                    fitnessOfSample1 += profit[j];
                }

                if (sampleChromosome2[j]) {
                    totalWeightOfSample2 += weight[j];
                    fitnessOfSample2 += profit[j];
                }
            }

            if (totalWeightOfSample1 > capacity)
                fitnessOfSample1 = 0;

            if (totalWeightOfSample2 > capacity)
                fitnessOfSample2 = 0;

            offsprings[i] = new Individual(sampleChromosome1, fitnessOfSample1);
            offsprings[i + 1] = new Individual(sampleChromosome2, fitnessOfSample2);

            Individual bestSample = fitnessOfSample1 > fitnessOfSample2
                    ? offsprings[i]
                    : offsprings[i + 1];

            if (bestSample.getFitness() > kp.getMaxProfit()) {
                kp.setMaxProfit(bestSample.getFitness());
                kp.setMaxValueCombination(bestSample.getChromosome().clone());
            }
        }
    }

    public static void mutateOffsprings(KnapsackProblem kp, Individual[] offsprings) {
        int[] weight = kp.getWeight();
        int[] profit = kp.getProfit();
        int capacity = kp.getCapacity();

        // double mutationRate = 0.01 + random.nextDouble() * 0.09;
        double mutationRate = 0.25;

        for (int i = 0; i < offsprings.length; i++) {

            Boolean[] currentOffspringChromosome = offsprings[i].getChromosome();

            int totalWeightOfCurrentOffspring = 0;
            int currentOffspringFitness = 0;

            for (int j = 0; j < currentOffspringChromosome.length; j++) {

                double r = random.nextDouble();

                if (r < mutationRate)
                    currentOffspringChromosome[j] = !currentOffspringChromosome[j];

                if (currentOffspringChromosome[j]) {
                    totalWeightOfCurrentOffspring += weight[j];
                    currentOffspringFitness += profit[j];
                }
            }

            if (totalWeightOfCurrentOffspring > capacity)
                currentOffspringFitness = 0;

            offsprings[i].setFitness(currentOffspringFitness);

            if (currentOffspringFitness > kp.getMaxProfit()) {
                kp.setMaxProfit(currentOffspringFitness);
                kp.setMaxValueCombination(currentOffspringChromosome.clone());
            }
        }
    }
}