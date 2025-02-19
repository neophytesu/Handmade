class Solution {
    public int maxProfit(int[] prices) {
        int ans = 0;
        int cur = prices[0];
        for (int price : prices) {
            if (price < cur) {
                cur = price;
            } else {
                ans = Math.max(ans, price - cur);
            }
        }
        return ans;
    }
}