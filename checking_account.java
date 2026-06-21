package jun.error;

public class checking_account {
    private double balance;
    private int number;

    public class InsufficientFundsException extends Exception {
    private double amount;
   
    public InsufficientFundsException(double amount) {
      this.amount = amount;
    }
   
    public double getAmou   nt() {
      return amount;
    }
}

    public checking_account(int number){
      this.number = number;
    }

    public void deposit(double amount){
        balance += amount;
    }
    public void withdraw(double amount) throws InsufficientFundsException{
        if(amount <= balance){
            balance -= amount; 
        }else{
            double needs = amount - balance;
            throw new InsufficientFundsException(needs);
        }

    }
    public double getBalance() {
        return balance;
    }
    public int getNumber(){
        return number;
    }
    public class bankDemo{
        public static void main(String[] args) {
            checking_account c = new checking_account(101);
            System.out.println("Depositing 500 ...");
            c.deposit(500);

            try{
                System.out.println("\n Withdrawing $100 ...");
                c.withdraw(100);
                 System.out.println("\n Withdrawing $600 ...");
                c.withdraw(600);
            }catch(InsufficientFundsException e){
                System.out.println("Sorry but you are short $ " + e.getAmount());
                e.printStackTrace();
            }
        }
    }
    
}

