import java.util.*;

public class ATM {
    private Scanner scanner = new Scanner(System.in);
    private String acno;
    private String pin;
    private double balance;
    private ArrayList<String> arr1 = new ArrayList<String>();

    public ATM(String acno, String pin) {
        this.acno = acno;
        this.pin = pin;
        this.balance = 1000;
        arr1.add("Your Initial Balance: Rs.1000");
    }

    public boolean authenticate(String enteredUserId, String enteredPin) {
        return acno.equals(enteredUserId) && pin.equals(enteredPin);
    }

    public void displayMenu() {
        System.out.println("\n1. Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
    }

    public void performTransaction(int choice) {
        switch(choice) {
            case 1:
                displayTransactionHistory();
                break;
            case 2:
                withdraw();
                break;
            case 3:
                deposit();
                break;
            case 4:
                transfer();
                break;
            case 5:
                System.out.println("\nExiting ATM. Thank you!");
                break;
            default:
                System.out.println("\nInvalid choice. Please try again.\n");
        }
    }

    private void updatehistory(String action) {
        arr1.add(action);
    }

    private void displayTransactionHistory() {
        System.out.println("\nTransaction History:\n");
        for(int i=0;i<arr1.size();i++){
            System.out.println((i+1)+"."+arr1.get(i));
        }
        System.out.println();
    }

    private void withdraw() {
        System.out.print("\nEnter withdrawal amount: ");
        double amount = scanner.nextDouble();
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            updatehistory("Withdrawal successful. Remaining balance: " + balance);
            System.out.println("\nWithdrawal successful. Remaining balance: " + balance+"\n");
        } 
        else {
            System.out.println("\nInvalid Amount or Insufficient Funds.");
        }
    }

    private void deposit() {
        System.out.print("\nEnter deposit amount: ");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            balance += amount;
            updatehistory("Deposit successful. New balance: " + balance);
            System.out.println("\nDeposit successful. New balance: " + balance+"\n");
        } else {
            System.out.println("\nInvalid deposit amount.\n");
        }
    }

    private void transfer() {
        System.out.print("\nEnter Recipient's 11-digit A/C Number: ");
        String recipientaccno = scanner.next();
        while(!(recipientaccno.length()==11 && isNumeric(recipientaccno) && !recipientaccno.equals(acno))){
            if(recipientaccno.equals(acno)){
                System.out.println("\nRecipient's A/C Number can not be same as Sender's A/C Number");
                System.out.print("\nEnter a valid A/C Number: ");
                recipientaccno = scanner.next();
                continue;
            }
            if(!isNumeric(recipientaccno)){
                System.out.print("\nEnter a valid A/C Number: ");
                recipientaccno = scanner.next();
                continue;
            }
            System.out.print("\nA/C number entered is not 11-digits long\nRe-enter Recipient's A/C Number: ");
            recipientaccno = scanner.next();
        }
        System.out.print("\nEnter transfer amount: ");
        double amount = scanner.nextDouble();
        if(amount<=balance){
            balance-=amount;
            updatehistory("Rs. "+amount+" Sent to A/C No.: "+recipientaccno+" New Balance: "+balance);
            System.out.println("\nTransaction Successful!");
            System.out.println("Your New Balance: "+balance+"\n");
        }
        else{
            System.out.println("\nInvalid amount or Insufficient Funds.\n");
        }
    }

    public static boolean isNumeric(String x){
        for(int i=0;i<x.length();i++){
            if(!(x.charAt(i)>=48 && x.charAt(i)<=57)){
                return false;
            }
        }
        return true;
        // scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM!");

        System.out.print("Enter Your 11-digit A/C Number: ");
        String acno = scanner.next();

        while(acno.length()!=11 || !isNumeric(acno)){
            if(!isNumeric(acno)){
                System.out.print("\nEnter a valid A/C Number: ");
                acno = scanner.next();
                continue;
            }
            System.out.print("\nA/C number entered is not 11-digits long\nRe-enter your A/C Number: ");
            acno = scanner.next();
        }

        System.out.print("Enter Your 4-digit PIN: ");
        String pin = scanner.next();
        while(pin.length()!=4 || !isNumeric(pin)){
            if(!isNumeric(pin)){
                System.out.print("\nEnter a valid PIN: ");
                pin = scanner.next();
                continue;
            }
            System.out.print("\nPIN entered is not 4-digits long\nRe-enter your PIN: ");
            pin = scanner.next();
        }

        ATM atm = new ATM(acno, pin);

        if(atm.authenticate(acno, pin)) {
            System.out.println("\nAuthentication successful. Welcome, " + acno + "!");
            int choice;
            do {
                atm.displayMenu();
                System.out.println("\nEnter your choice (1-5):\n");
                choice = scanner.nextInt();
                atm.performTransaction(choice);
                System.out.print("Press Any Alphanumeric key and Enter to continue: ");
                scanner.next();
            } while (choice != 5);
        }
        else {
            System.out.println("\nAuthentication failed. Exiting ATM.\n");
        }
        atm.scanner.close();
        scanner.close();
    }
}
