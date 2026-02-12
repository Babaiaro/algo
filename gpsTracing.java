    package java_feb;

    public class gpsTracing {
        public int[] locateSnake( int n, int position){
        

            int row = position / n ;
            int col = position % n;

            return new int[]{row,col};


        }
          public static void main(String args[]){
            gpsTracing tracker = new gpsTracing();

            int n = 4;
            int position = 12;
             
            int[] result = tracker.locateSnake(n, position);

            System.out.println("For position " + position + " in a " + n + "x" + n + " grid:");
            System.out.println("Row: " + result[0]);
            System.out.println("Col: " + result[1]);

            System.out.println("Coordinates: " + Arrays.toString(result));


        }
        
    }
