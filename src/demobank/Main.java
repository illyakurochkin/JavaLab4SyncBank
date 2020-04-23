package demobank;

import synchronizedbank.Bank;
import synchronizedbank.BankThread;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int ACCOUNTS = 100;
        final int INITIAL_BALANCE = 1000;
        final int MAXTRANSFER = 200;
        final int DELAY=5;

        ReentrantLock locker = new ReentrantLock();
        Bank bank = new Bank(ACCOUNTS, INITIAL_BALANCE, locker);

        for (int t=0; t<ACCOUNTS; t++) {
            BankThread bt = new BankThread(bank,t,MAXTRANSFER,DELAY);
            Thread tr = new Thread(bt);
            tr.start();
        }

        while (true) {
            System.out.println("Total balance is "+bank.getTotalBalance());
            System.out.println("Minimal account is "+bank.getMin());

            System.out.println("**********************************");
            Thread.sleep(1000);

        }

    }
}
