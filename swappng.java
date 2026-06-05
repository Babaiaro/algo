public class swappng {
    public static void main(String[] args){
        new swappng().sw(0);
    }

    public void sw(int num){
        int left = 5;
        int right = 8;
        swap(num, left, right);


    }
    private void swap(int num, int left, int right){
        int temp = left;
        left = right;
        right = temp;
        System.out.println("this is left: " + left + " this is right: " + right+ " and this is temp: " + temp);

    }

}
