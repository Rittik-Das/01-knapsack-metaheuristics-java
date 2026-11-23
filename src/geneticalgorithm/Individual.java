package geneticalgorithm;

public class Individual {

    private Boolean[] chromosome;
    private int fitness;

    private double percentageShare;

    public Individual(Boolean[] chromosome, int fitness) {
        this.chromosome = chromosome;
        this.fitness = fitness;
    }

    public Boolean[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(Boolean[] chromosome) {
        this.chromosome = chromosome;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public double getPercentageShare() {
        return percentageShare;
    }

    public void setPercentageShare(double percentageShare) {
        this.percentageShare = percentageShare;
    }
}