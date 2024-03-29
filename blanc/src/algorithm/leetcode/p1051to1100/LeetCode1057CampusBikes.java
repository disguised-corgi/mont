package algorithm.leetcode.p1051to1100;

import org.junit.Test;

import java.util.PriorityQueue;

import algorithm.util.BlobUtil;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 1057. Campus Bikes
 * Medium
 *
 * On a campus represented as a 2D grid, there are N workers and M bikes,
 * with N <= M. Each worker and bike is a 2D coordinate on this grid.
 *
 * Our goal is to assign a bike to each worker.
 * Among the available bikes and workers,
 * we choose the (worker, bike) pair with the shortest Manhattan distance between each other,
 * and assign the bike to that worker.
 * (If there are multiple (worker, bike) pairs with the same shortest Manhattan distance,
 * we choose the pair with the smallest worker index;
 * if there are multiple ways to do that,
 * we choose the pair with the smallest bike index).
 * We repeat this process until there are no available workers.
 *
 * The Manhattan distance between two points p1 and p2
 * is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
 *
 * Return a vector ans of length N,
 * where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.
 *
 *
 * Example 1:
 * Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
 * Output: [1,0]
 * Explanation:
 * Worker 1 grabs Bike 0 as they are closest (without ties),
 * and Worker 0 is assigned Bike 1. So the output is [1, 0].
 *
 * Example 2:
 * Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
 * Output: [0,2,1]
 * Explanation:
 * Worker 0 grabs Bike 0 at first. Worker 1 and Worker 2 share the same distance to Bike 2,
 * thus Worker 1 is assigned to Bike 2, and Worker 2 will take Bike 1. So the output is [0,2,1].
 *
 * Note:
 * 0 <= workers[i][j], bikes[i][j] < 1000
 * All worker and bike locations are distinct.
 * 1 <= workers.length <= bikes.length <= 1000
 */
public class LeetCode1057CampusBikes {

    private interface Method {
        int[] assignBikes(int[][] workers, int[][] bikes);
    }

    private static final class QueueImpl implements Method {

        private static final class Pair implements Comparable<Pair> {
            int distance;
            int worker;
            int bike;

            Pair(int distance, int worker, int bike) {
                this.distance = distance;
                this.worker = worker;
                this.bike = bike;
            }

            public int compareTo(Pair o) {
                if (this.distance != o.distance) {
                    return this.distance - o.distance;
                } else {
                    return this.worker != o.worker ? this.worker - o.worker : this.bike - o.bike;
                }
            }
        }

        public int[] assignBikes(int[][] workers, int[][] bikes) {
            int pairedWorkerCount = 0;
            int[] result = new int[workers.length];
            boolean[] pairedBikes = new boolean[bikes.length];
            boolean[] pairedWorkers = new boolean[workers.length];
            PriorityQueue<Pair> pairsQueue = new PriorityQueue<>();
            for (int workerIndex = 0; workerIndex < workers.length; workerIndex++) {
                int[] worker = workers[workerIndex];
                for (int BikeIndex = 0; BikeIndex < bikes.length; BikeIndex++) {
                    int[] bike = bikes[BikeIndex];
                    int distance = Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
                    pairsQueue.add(new Pair(distance, workerIndex, BikeIndex));
                }
            }

            while (pairedWorkerCount < workers.length) {
                Pair cursor = pairsQueue.poll();
                if (pairedBikes[cursor.bike] || pairedWorkers[cursor.worker]) {
                    continue;
                }
                pairedBikes[cursor.bike] = true;
                pairedWorkers[cursor.worker] = true;
                result[cursor.worker] = cursor.bike;
                pairedWorkerCount++;
            }

            return result;
        }
    }

    private static Method getMethod() {
        return new QueueImpl();
    }

    private void test(int[][] workers, int[][] bikes, int[] expected) {
        int[] actual = getMethod().assignBikes(workers, bikes);
        assertThat(actual, is(expected));
    }

    @Test
    public void testcase1() {
        int[][] workers = BlobUtil.parseArray2D("[[0,0],[2,1]]");
        int[][] bikes = BlobUtil.parseArray2D("[[1,2],[3,3]]");
        int[] expected = BlobUtil.parseArray1D("[1,0]");
        test(workers, bikes, expected);
    }
}
