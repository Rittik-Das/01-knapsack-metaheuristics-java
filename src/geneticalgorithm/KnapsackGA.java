package geneticalgorithm;

import java.util.Arrays;

public class KnapsackGA {

    public static void main(String[] args) {

        try {
            int[] weight = { 31, 10, 20, 19, 4, 3, 6 }; // weight.length >= 2
            int[] profit = { 70, 20, 39, 37, 7, 5, 10 };
            int capacity = 50;

            KnapsackProblem kp = new KnapsackProblem(weight, profit, capacity);

            Operations.setPopulationSize(kp);
            Individual[] population = new Individual[Operations.getPopulationSize()];

            Operations.setTournamentSize(3); // tournamentSize <= populationSize

            // Initialize Population
            Operations.initializePopulation(kp, population);

            int bestFitnessInitialization = 0;
            Individual ansInitialization = null;

            for (Individual individual : population) {
                if (individual.getFitness() > bestFitnessInitialization) {
                    bestFitnessInitialization = individual.getFitness();
                    ansInitialization = individual;
                }
            }

            System.out.println("\nBest fitness of initialized population : " + bestFitnessInitialization);
            System.out.print("Best Chromosome of initialized population : ");
            CommonUtils.decode(kp, ansInitialization);
            System.out.println();

            int generations = 100;

            Individual[] parents = new Individual[Operations.getNoOfParents()];
            Individual[] offsprings = new Individual[Operations.getNoOfOffsprings()];

            System.out.println("----- Generation wise fitness -----\n");

            for (int i = 1; i <= generations; i++) {

                // Selection
                Operations.selectParent(population, parents);

                // Crossover
                Operations.singlePointCrossover(kp, parents, offsprings);

                // Mutation
                Operations.mutateOffsprings(kp, offsprings);

                // Required population size for next generation
                int requiredSize = parents.length + offsprings.length;

                if (population.length < requiredSize) {
                    population = Arrays.copyOf(population, requiredSize);
                }

                int index = 0;

                for (Individual parent : parents)
                    population[index++] = parent;

                for (Individual offspring : offsprings)
                    population[index++] = offspring;

                int bestFitness = 0;
                Individual ans = null;

                for (Individual individual : population) {
                    if (individual.getFitness() > bestFitness) {
                        bestFitness = individual.getFitness();
                        ans = individual;
                    }
                }

                System.out.println("Best fitness of generation " + i + " : " + bestFitness);
                System.out.print("Best Chromosome of generation " + i + " : ");
                CommonUtils.decode(kp, ans);
                System.out.println();
            }

            System.out.println("Maximum profit : " + kp.getMaxProfit());
            System.out.print("Best Chromosome found : ");
            CommonUtils.decode(kp, kp.getMaxValueCombination());
            System.out.println();

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input : " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Invalid operation : " + e.getMessage());
        }
    }
}