package synchronizedbank;

import java.util.Random;

public class BankThread implements Runnable {
    Random rg = new Random();
    private Bank bank;
    private int from;
    private int maxTransfer;
    private int delay;

    public BankThread(Bank bank, int from, int maxTransfer, int delay) {
        this.bank = bank;
        this.from = from;
        this.maxTransfer = maxTransfer;
        this.delay = delay;
    }


    @Override
    public void run() {

        while (true) {
            int to = rg.nextInt(bank.size());
            int amount = rg.nextInt(maxTransfer);

            bank.transfer(from, to, amount, delay);
        }

    }

}
