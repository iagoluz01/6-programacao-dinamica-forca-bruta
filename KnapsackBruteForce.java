public class KnapsackBruteForce {
    private static int iterations = 0;
    
    static class Item {
        int weight;
        int value;
        
        Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    public static int knapsackBruteForce(Item[] items, int capacity, int n) {
        iterations++;
        
        // Base case
        if (n == 0 || capacity == 0)
            return 0;
            
        // If weight of item is more than capacity, skip it
        if (items[n-1].weight > capacity)
            return knapsackBruteForce(items, capacity, n-1);
            
        // Return maximum of:
        // 1. Item n-1 included
        // 2. Item n-1 not included
        return Math.max(
            items[n-1].value + knapsackBruteForce(items, capacity - items[n-1].weight, n-1),
            knapsackBruteForce(items, capacity, n-1)
        );
    }

    public static void main(String[] args) {
        // Example from class
        Item[] items = new Item[] {
            new Item(2, 3),  // weight = 2, value = 3
            new Item(3, 4),  // weight = 3, value = 4
            new Item(4, 5),  // weight = 4, value = 5
            new Item(5, 6)   // weight = 5, value = 6
        };
        
        int capacity = 10;
        iterations = 0;
        
        int maxValue = knapsackBruteForce(items, capacity, items.length);
        System.out.println("Maximum value: " + maxValue);
        System.out.println("Number of iterations: " + iterations);
    }
}
