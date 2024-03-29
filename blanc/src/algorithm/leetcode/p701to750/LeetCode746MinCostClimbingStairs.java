package algorithm.leetcode.p701to750;

public class LeetCode746MinCostClimbingStairs {

    private interface Method {
        int minCostClimbingStairs(int[] cost);
    }

    private static final class DP implements Method {

        public int minCostClimbingStairs(int[] cost) {
            if (cost == null || cost.length == 0) {
                return 0;
            }
            int[] dp = new int[cost.length + 1];
            for (int i = 2; i < dp.length; i++) {
                dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
            }
            return dp[cost.length];
        }
    }
}
