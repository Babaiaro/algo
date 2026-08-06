class Solution {
    public List<List<Integer>> generate(int numRows) {
       int[][] tri = new int[numRows][];
       for (int r = 0; r < numRows; r++){
        tri[r] = new int[r+1];
        tri[r][0] = 1;
        tri[r][r] = 1;
        for(int c = 1; c < r; c++){
            tri[r][c] = tri[r-1][c-1] + tri[r-1][c];
        }
       }
       List<List<Integer>> nestedList = new ArrayList<>();
       for (int[] num : tri){
            List<Integer> inner = new ArrayList<>();
             for (int san : num){
                 inner.add(san);
            }
        nestedList.add(inner);
       }
       return nestedList; 
    }
}
