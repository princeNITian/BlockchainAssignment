import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

public class App {

    private static ArrayList<Transaction> randomTransaction;
    public static void main(String[] args){
        ArrayList<Block> blockchain = new ArrayList<>();

        // Register nodes with their Initial deposit 
        Person p1 = new Person("Rajesh", 100);
        Person p2 = new Person("Rohan", 200);
        Person p3 = new Person("Gauri", 300);
        Person p4 = new Person("Tez", 500);
        Person p5 = new Person("Mohit", 600);
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(p1);
        persons.add(p2);
        persons.add(p3);
        persons.add(p4);
        persons.add(p5);

        // Maintain Ledger for initial transaction
        String Reason = "Initial Deposit";
        ArrayList<Transaction> ledger = new ArrayList<>();
        System.out.println("Ledger Initialized:");
        for (int i = 0; i < 5; i++) {
            ledger.add(new Transaction(Reason, persons.get(i).getName(),
                    persons.get(i).getBalance()));
            System.out.println(ledger.get(i));
        }
        System.out.println("\n===========================\n");

        // generate random transactions
        System.out.println("Now we generated some random transactions which is yet to be performed..");
        App.generateRandomTransaction();

        System.out.println("\n===========================\n");
        System.out.println("\nPerforming each transaction....");
        // for each block there will be 10 transaction
        int count = 0;
        int chain = 0;
        for(Transaction t: App.randomTransaction){
            int senderIndex = -1;
            int receiverIndex = -1;
            for(int k=0;k<persons.size();k++){
                if(persons.get(k).getName().equals(t.getDebitAccount())){
                    senderIndex = k;
                }
                if(persons.get(k).getName().equals(t.getCreditAccount())){
                    receiverIndex = k;
                }
            }
            
            // check for valid transaction
            if (persons.get(senderIndex).getBalance() < t.getBalance()) {
                System.out.println("Not a valid transaction!");
                continue;
            }else{
                System.out.println(t);
            }

            if(count == 0){
                // it's the first block i.e. genesis
                Timestamp instant = Timestamp.from(Instant.now());
                Long timestamp = instant.getTime();
                Block b = new Block("0",timestamp);
                blockchain.add(b);
                blockchain.get(0).getTransactions().add(t);
                count++;
                ledger.add(new Transaction(t.getDebitAccount(),t.getCreditAccount(),persons.get(senderIndex).getBalance()-t.getBalance()));
                ledger.add(new Transaction(t.getDebitAccount(), t.getCreditAccount(),
                        persons.get(receiverIndex).getBalance() + t.getBalance()));
            }else{
                if(count%10==0){
                    // new Block will be added
                    Timestamp instant = Timestamp.from(Instant.now());
                    Long timestamp = instant.getTime();
                    Block b = new Block(blockchain.get(chain).getHash(),timestamp);
                    blockchain.add(b);
                    chain++;
                    count++;
                    blockchain.get(chain).getTransactions().add(t);
                    ledger.add(new Transaction(t.getDebitAccount(), t.getCreditAccount(),
                            persons.get(senderIndex).getBalance() - t.getBalance()));
                    ledger.add(new Transaction(t.getDebitAccount(), t.getCreditAccount(),
                            persons.get(receiverIndex).getBalance() + t.getBalance()));
                }else{
                    // this transaction will be added to the same block
                    count++;
                    blockchain.get(chain).getTransactions().add(t);
                    ledger.add(new Transaction(t.getDebitAccount(), t.getCreditAccount(),
                            persons.get(senderIndex).getBalance() - t.getBalance()));
                    ledger.add(new Transaction(t.getDebitAccount(), t.getCreditAccount(),
                            persons.get(receiverIndex).getBalance() + t.getBalance()));
                }
            }
            // update sender balance
            persons.get(senderIndex).setBalance(persons.get(senderIndex).getBalance()-t.getBalance());
            // update receiver balance
            persons.get(receiverIndex).setBalance(persons.get(receiverIndex).getBalance()+t.getBalance());
            
        }

        // Printing Blocks
        System.out.println("\n=============Blocks Involved in Chain=============\n");
        for(int i = 0;i<blockchain.size();i++){
            System.out.println("Block "+(i+1)+": "+blockchain.get(i));
        }

        // Checkin validity of blockchian
        boolean isValid = false;
        for(int i=0;i<blockchain.size()-1;i++){
            if(blockchain.get(i).getHash().equals(blockchain.get(i+1).getPrevHash())){
                isValid = true;
            }else{
                isValid = false;
            }
        }

        System.out.println("\n*** Blockchian is Valid Staus: "+isValid);

        for(Transaction t: blockchain.get(0).getTransactions()){
            System.out.println(t);
        }

        // Printing the final ledger
        System.out.println("\n\n=======Here's Our Ledger=========\n\n");
        for(int i = 0;i<ledger.size();i++){
            System.out.println(ledger.get(i));
        }

    }

    public static void generateRandomTransaction(){
        Random rand = new Random();
        // min 20 transacitons
        int num = rand.nextInt(100)+20;
        randomTransaction = new ArrayList<>();
        String names[] = {"Rajesh","Rohan","Gauri","Tez","Mohit"};
        int j =0;
        for(int i=0;i<num;i++){
            int bal = rand.nextInt(100)*5 + 100;
            int deb = rand.nextInt(5);
            int cred = rand.nextInt(5);
            if(deb != cred){
                randomTransaction.add(new Transaction(names[deb], names[cred], bal));
                System.out.println(randomTransaction.get(j));
                j++;
            }           
            
        }

    }
}