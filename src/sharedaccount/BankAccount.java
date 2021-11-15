package sharedaccount;

import java.util.concurrent.locks.*;

/**
 * A bank account has  balance that can be changed by
 * deposits and withdrawals.
 */
public class BankAccount  {
    // no AtomicDouble Class in java.util.concurrent
    private double balance;
    // use an external lock for the BankAccount
   // Lock balanceLock = new ReentrantLock();
    // use a Condition to make sure the balance does not
    // become negative
   // Condition enoughMoney = balanceLock.newCondition();

    /**
     * Constructs a bank account with a zero balance.
     */
    public BankAccount() {
        balance = 0;
    }

    /**
     * Deposits money into the bank account.
     *
     * @param amount the amount to deposit
     */
    public synchronized void deposit(double amount) {
       // balanceLock.lock();
        // intrinsic lock applied
        try {
            System.out.print("Depositing " + amount);
            double newBalance = balance + amount;
            System.out.println(", new balance is " + newBalance);
            balance = newBalance;
       //     enoughMoney.signalAll();
            this.notifyAll();
        } finally {
       //     balanceLock.unlock();
        }

    }

    /**
     * Withdraws money from the bank account.
     *
     * @param amount the amount to withdraw
     */
    public synchronized void withdraw(double amount) {
       // balanceLock.lock();
        // intrinsic lock occurs automatically
        try {
            while (balance < amount) {
                // put the withdraw thread in a BLOCKED state
       //         enoughMoney.await();
                  this.wait();
            }
            System.out.print("Withdrawing " + amount);
            double newBalance = balance - amount;
            System.out.println(", new balance is " + newBalance);
            balance = newBalance;
        }catch (InterruptedException e){
            System.out.println("Withdrawal Thread Interrupted");
        } finally {
     //      balanceLock.unlock();
        }
        // instrinsic unlock occurs
    }

    /**
     * Gets the current balance of the bank account.
     *
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }
}

