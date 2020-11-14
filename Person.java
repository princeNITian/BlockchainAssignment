public class Person {
    private String name;
    private int balance;

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setName(String name) {
		this.name = name;
    }
    
    @Override
    public String toString() {
       return this.getName()+" "+this.getBalance();
    }

    public Person(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }
    
}