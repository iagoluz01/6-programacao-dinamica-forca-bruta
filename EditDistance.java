public class EditDistance {
    private static int bruteForceIterations = 0;
    private static int dynamicIterations = 0;

    // Brute Force implementation
    public static int editDistanceBruteForce(String str1, String str2, int m, int n) {
        bruteForceIterations++;

        // Base cases
        if (m == 0) return n;
        if (n == 0) return m;

        // If last characters match
        if (str1.charAt(m - 1) == str2.charAt(n - 1))
            return editDistanceBruteForce(str1, str2, m - 1, n - 1);

        // If last characters don't match, try all operations
        return 1 + Math.min(
            editDistanceBruteForce(str1, str2, m, n - 1),      // Insert
            Math.min(
                editDistanceBruteForce(str1, str2, m - 1, n),  // Remove
                editDistanceBruteForce(str1, str2, m - 1, n - 1) // Replace
            )
        );
    }

    // Updated Dynamic Programming implementation to match pseudocode
    public static int editDistanceDP(String A, String B) {
        int m = A.length();
        int n = B.length();
        int[][] matriz = new int[m + 1][n + 1];
        dynamicIterations = 0;

        // Initialize matriz[0][0]
        matriz[0][0] = 0;

        // Initialize first column (Insertion cost)
        for (int i = 1; i <= m; i++) {
            matriz[i][0] = matriz[i-1][0] + 1;
            dynamicIterations++;
        }

        // Initialize first row (Removal cost)
        for (int j = 1; j <= n; j++) {
            matriz[0][j] = matriz[0][j-1] + 1;
            dynamicIterations++;
        }

        // Fill the rest of the matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dynamicIterations++;
                int custoExtra = (A.charAt(i-1) == B.charAt(j-1)) ? 0 : 1;
                matriz[i][j] = Math.min(
                    matriz[i-1][j] + 1,  // Removal
                    Math.min(
                        matriz[i][j-1] + 1,  // Insertion
                        matriz[i-1][j-1] + custoExtra  // Match/Substitution
                    )
                );
            }
        }
        return matriz[m][n];
    }

    public static void main(String[] args) {
        String[][] testCases = {
            {"Casablanca", "Portentoso"},
            {
                "Maven, a Yiddish word meaning accumulator of knowledge, began as an attempt to " +
                "simplify the build processes in the Jakarta Turbine project. There were several" +
                " projects, each with their own Ant build files, that were all slightly different." +
                "JARs were checked into CVS. We wanted a standard way to build the projects, a clear " +
                "definition of what the project consisted of, an easy way to publish project information" +
                "and a way to share JARs across several projects. The result is a tool that can now be" +
                "used for building and managing any Java-based project. We hope that we have created " +
                "something that will make the day-to-day work of Java developers easier and generally help " +
                "with the comprehension of any Java-based project.",

                "This post is not about deep learning. But it could be might as well. This is the power of " +
                "kernels. They are universally applicable in any machine learning algorithm. Why you might" +
                "ask? I am going to try to answer this question in this article." +
                "Go to the profile of Marin Vlastelica Pogančić" +
                "Marin Vlastelica Pogančić Jun"
            }
        };

        System.out.println("\nEdit Distance Analysis");
        System.out.println("====================");
        
        for (int i = 0; i < testCases.length; i++) {
            String s1 = testCases[i][0];
            String s2 = testCases[i][1];
            
            bruteForceIterations = 0;
            dynamicIterations = 0;

            System.out.printf("\nTest Case %d:\n", i + 1);
            System.out.println("Input Details:");
            System.out.printf("- String 1 (length=%d): %s\n", s1.length(), 
                            s1.length() > 50 ? s1.substring(0, 50) + "..." : s1);
            System.out.printf("- String 2 (length=%d): %s\n", s2.length(), 
                            s2.length() > 50 ? s2.substring(0, 50) + "..." : s2);
            
            System.out.println("\nResults:");
            int dpResult = editDistanceDP(s1, s2);
            System.out.printf("- Dynamic Programming:\n");
            System.out.printf("  * Edit Distance: %d\n", dpResult);
            System.out.printf("  * Iterations: %d\n", dynamicIterations);
            
            if (i == 0) { // Only run brute force for small strings
                int bfResult = editDistanceBruteForce(s1, s2, s1.length(), s2.length());
                System.out.printf("- Brute Force:\n");
                System.out.printf("  * Edit Distance: %d\n", bfResult);
                System.out.printf("  * Iterations: %d\n", bruteForceIterations);
                System.out.printf("- Performance Comparison:\n");
                System.out.printf("  * DP/BF Iteration Ratio: 1:%.2f\n", 
                                (double)bruteForceIterations/dynamicIterations);
            }
            
            System.out.println("----------------------------------------");
        }
    }
}
