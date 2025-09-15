public class KnapsackBruteForce {
    private static int iterations = 0;
    private static boolean[] bestSelection;
    
    static class Item {
        int weight;
        int value;
        
        Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    public static int knapsackBruteForce(Item[] items, int capacity, int n, boolean[] selected) {
        iterations++;
        
        // Base case
        if (n == 0 || capacity == 0) {
            return 0;
        }
        
        // If weight of item is more than capacity, skip it
        if (items[n-1].weight > capacity) {
            boolean[] newSelected = selected.clone();
            return knapsackBruteForce(items, capacity, n-1, newSelected);
        }
        
        boolean[] includedItems = selected.clone();
        includedItems[n-1] = true;
        
        boolean[] excludedItems = selected.clone();
        excludedItems[n-1] = false;
        
        int includeItem = items[n-1].value + 
                         knapsackBruteForce(items, capacity - items[n-1].weight, n-1, includedItems);
        int excludeItem = knapsackBruteForce(items, capacity, n-1, excludedItems);
        
        if (includeItem > excludeItem) {
            bestSelection = includedItems;
            return includeItem;
        } else {
            bestSelection = excludedItems;
            return excludeItem;
        }
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
        bestSelection = new boolean[items.length];
        
        int maxValue = knapsackBruteForce(items, capacity, items.length, new boolean[items.length]);
        
        System.out.println("Knapsack Solution (Brute Force):");
        System.out.println("--------------------------------");
        System.out.println("Capacity: " + capacity);
        System.out.println("Maximum value: " + maxValue);
        System.out.println("Number of iterations: " + iterations);
        System.out.println("\nSelected items:");
        
        int totalWeight = 0;
        for (int i = 0; i < items.length; i++) {
            if (bestSelection[i]) {
                System.out.printf("Item %d: (weight=%d, value=%d)\n", 
                                i+1, items[i].weight, items[i].value);
                totalWeight += items[i].weight;
            }
        }
        System.out.println("\nTotal weight: " + totalWeight);
    }
}
