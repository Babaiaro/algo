package java_feb;

public class batteryDrain {
    public int finalBattery(String[] apps){
        int batteryLevel = 100;
        int text = 2;

        for (String app : apps){
            if (apps.equals("TEXT")){
                batteryLevel -= 2;
            } 
            else if (apps.equals("VIDEO")){
                batteryLevel -= 10;
            }
            else if (apps.equals("GAME")){
                batteryLevel -= 20;
            }
            else if (apps.equals("GAME")){
                batteryLevel -= 20;
            }
            else if (apps.equals("GAME")){
                batteryLevel -= 20;
            }
            else if (apps.equals("GAME")){
                batteryLevel = batteryLevel - 20;
            }
           
           batteryLevel = Math.max(0, batteryLevel);
        }
        return batteryLevel;

    }
    
}
