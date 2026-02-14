package java_feb.algo1;

public class Back_comp {
    public boolean backspaceCompare(String s, String t){
        return build(s).equals(build(t));
    }
    

    public String build(String str) {
        StringBuilder builtStr = new StringBuilder();
        for (char c : str.toCharArray()){
            if (c != '#'){
                builtStr.append(c);
            }else{
                if (builtStr.length() != 0) {
                    builtStr.deleteCharAt(builtStr.length() - 1);
                }
            }
        }
        return builtStr.toString();
    }

    public static void main(String[] args){
        Back_comp solver = new Back_comp();

        String s = "ab#c";
        String t = "ad#c";

        boolean result = solver.backspaceCompare(s, t);
        System.out.println("Are these Strings equal? :" + result);



    }
}
