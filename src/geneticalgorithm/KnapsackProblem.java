package geneticalgorithm;

public class KnapsackProblem {

    private int[] weight;
    private int[] profit;
    private int capacity;
    private int noOfElements;

    private Boolean[] maxValueCombination;
    private int maxProfit;

    public KnapsackProblem(int[] weight, int[] profit, int capacity) {

        if (weight.length < 2)
            throw new IllegalArgumentException("At least two items are required.");
        
        this.weight = weight.clone();
        this.profit = profit.clone();
        this.capacity = capacity;
        this.noOfElements = weight.length;

        this.maxProfit = 0;
    }

    public int[] getWeight() {
        return weight;
    }

    public void setWeight(int[] weight) {
        this.weight = weight.clone();
    }

    public int[] getProfit() {
        return profit;
    }

    public void setProfit(int[] profit) {
        this.profit = profit.clone();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNoOfElements() {
        return noOfElements;
    }

    public void setNoOfElements(int noOfElements) {
        this.noOfElements = noOfElements;
    }

    public Boolean[] getMaxValueCombination() {
        return maxValueCombination;
    }

    public void setMaxValueCombination(Boolean[] maxValueCombination) {
        this.maxValueCombination = maxValueCombination;
    }

    public int getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(int maxProfit) {
        this.maxProfit = maxProfit;
    }
}