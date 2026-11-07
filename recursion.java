class Solution{
	static int n;
	// ans = 
	static void print(int [] ar){
		System.out.print("[");
		for (int i = 0; i < n; i++){
			System.out.print([i]);
			if (i + 1 < n) {
				System.out.print(" , ");
			}
		}
		System.out.println(" ] ");
	}
	
	static void fun (int[] nums, int[] arr, int ind, List<List<Integer>>ans) {
		if (ind == n) {
			List<Integer> copy = new ArrayList<>();
			for (int i = 0; i < n; i++){
				copy.add(arr[i]);
			}
			ans.add(copy);
			return;
		}
		for (int i = 0; i < n; i++(
			boolean has = false;
			for (int j = 0; j < ind; j++){
				if (nums[i] == arr[j]) {
					has = true;
					break;
				}	
			}

			if (!has){
				arr[ind] = nums[i];
				fun(nums, arr, ind + 1, ans);
				arr[ind] = 0;
			 }
		}
		return;
	}
		
	public List<List<Integer>> permute(int[] nums) {
		n = nums.length;
		List<List<Integer>> ans = new ArrayList<>();
		int[] arr = new int [n];

		fun(nums, arr, 0, ans);
		return ans;


