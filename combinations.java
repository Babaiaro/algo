import java.util.*;

class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        backtrack(1, n, k, new ArrayList<>(), ans);
        return ans;
    }

    private void backtrack(int start, int n, int k, List<Integer> current, List<List<Integer>> ans) {
        if (current.size() == k) {
            ans.add(new ArrayList<>(current));  // copy, since 'current' keeps changing
            return;
        }

        for (int i = start; i <= n; i++) {
            current.add(i);
            backtrack(i + 1, n, k, current, ans);  // move start forward, no repeats
            current.remove(current.size() - 1);    // undo the choice (backtrack)
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.combine(4, 2));
    }
}
