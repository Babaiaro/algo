    package java_feb;

    public class gpsTracing {
        public int[] locateSnake( int n, int position){
        

            int row = position / n ;
            int col = position % n;

            return new int[]{row,col};


        }
        
    }
