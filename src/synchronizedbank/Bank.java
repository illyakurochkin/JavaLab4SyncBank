package synchronizedbank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private final int[] accounts;
    ReentrantLock locker;
    Condition condition;
    public Bank (int numbOfAccounts, int initialBalance, ReentrantLock lock) {
        accounts = new int[numbOfAccounts];
        for (int i=0;i<accounts.length;i++) {
            accounts[i]=initialBalance;
        }
        locker = lock;
        condition = locker.newCondition();
    }

    @Override
    public String toString() {
        String s = "";
        for (int v:accounts) s+=(v+" ");
        return s;
    }

    public int getTotalBalance() {
        int s=0;
        for (int v:accounts) s+=v;
        return s;
    }

    public int getMin() {
        if (accounts.length==1) return accounts[0];
        else {
            int mm=accounts[0];
            for (int v:accounts) {
                if (v<mm) mm=v;
            }
            return mm;
        }
    }

    public int size() {
        return accounts.length;
    }

    public int getSpecificAccount (int i) {
        return accounts[i];
    }

    public void transfer (int from, int to, int amount, int delay) {
        locker.lock();
        try {
            while (accounts[from] <= amount) {
                condition.await();
            }

            accounts[from] -= amount;
            Thread.sleep(delay);
            accounts[to] += amount;

            condition.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }



}

