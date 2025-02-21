import java.util.ArrayList;
import java.util.List;

class Solution {
    static final int INF = 0x3f3f3f3f;

    public int minimumWhiteTiles(String floor, int numCarpets, int carpetLen) {
        int n = floor.length();
        int[][] d = new int[n + 1][numCarpets + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= numCarpets; j++) {
                d[i][j] = INF;
            }
        }
        for (int j = 0; j <= numCarpets; j++) {
            d[0][j] = 0;
        }
        for (int i = 1; i <= n; i++) {
            d[i][0] = d[i - 1][0] + (floor.charAt(i - 1) == '1' ? 1 : 0);
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= numCarpets; j++) {
                d[i][j] = d[i - 1][j] + (floor.charAt(i - 1) == '1' ? 1 : 0);
                d[i][j] = Math.min(d[i][j], d[Math.max(0, i - carpetLen)][j - 1]);
            }
        }
        return d[n][numCarpets];
    }
}