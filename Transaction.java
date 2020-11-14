public class Transaction {
        private String debitAccount;
        private String creditAccount;
        private int balance;

        public Transaction(String debitAccount, String creditAccount, int balance) {
            this.debitAccount = debitAccount;
            this.creditAccount = creditAccount;
            this.balance = balance;
        }

        @Override
        public String toString() {
            return debitAccount + " " + creditAccount + " " + balance;
        }

        public String getDebitAccount() {
            return debitAccount;
        }

        public void setDebitAccount(String debitAccount) {
            this.debitAccount = debitAccount;
        }

        public String getCreditAccount() {
            return creditAccount;
        }

        public void setCreditAccount(String creditAccount) {
            this.creditAccount = creditAccount;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

}
