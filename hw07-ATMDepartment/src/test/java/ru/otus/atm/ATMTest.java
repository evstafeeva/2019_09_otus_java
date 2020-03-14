package ru.otus.atm;


import org.junit.Assert;
import org.junit.Test;
import ru.otus.atm.cell.Denominations;
import ru.otus.atm.exceptions.IllegalAmountException;
import ru.otus.atm.exceptions.ImpossibleAmountException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ATMTest {

    @Test
    public void putMoneyTest() throws Exception {
        ATM atm = new ATMImpl();

        atm.putMoney(Arrays.asList(
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.FIVE_THOUSAND_RUBLES,
                Denominations.FIVE_THOUSAND_RUBLES,
                Denominations.FIVE_THOUSAND_RUBLES));

        atm.putMoney(Arrays.asList(
                Denominations.FIVE_THOUSAND_RUBLES,
                Denominations.FIVE_THOUSAND_RUBLES));

        Assert.assertEquals(27520, atm.getBalance());
    }

    @Test
    public  void getMoneyTest() throws Exception {
        ATM atm = new ATMImpl();
        atm.putMoney(Arrays.asList(
                Denominations.THOUSAND_RUBLES,
                Denominations.THOUSAND_RUBLES,
                Denominations.THOUSAND_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES));
        int balanceBefore = atm.getBalance();
        List<Denominations> myMoney = atm.getMoney(1520);
        List<Denominations> money = new ArrayList<>(Arrays.asList(
                Denominations.THOUSAND_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES));
        Assert.assertEquals(money.toArray(), myMoney.toArray());
        Assert.assertEquals(1520, balanceBefore - atm.getBalance());
    }

    @Test (expected =  ImpossibleAmountException.class)
    public void getWrongMoneyTest() throws Exception {
        ATM atm = new ATMImpl();
        atm.putMoney(Arrays.asList(
                Denominations.THOUSAND_RUBLES,
                Denominations.THOUSAND_RUBLES,
                Denominations.THOUSAND_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES));
        int balanceBefore = atm.getBalance();
        Assert.assertEquals(balanceBefore, 5620);
        List<Denominations> myMoney = atm.getMoney(1630);
        Assert.assertEquals(balanceBefore, atm.getBalance());
    }

    @Test (expected =  IllegalAmountException.class)
    public void getWrongMoneyTest2() throws Exception {
        ATM atm = new ATMImpl();
        atm.getMoney(-100);
    }

    @Test
    public void undo() throws Exception {
        ATM atm = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));
        atm.getMoney(120);
        atm.putMoney(Arrays.asList(Denominations.TEN_RUBLES));
        assert atm.getBalance()==310;
        atm.restoreATM();
        assert atm.getBalance()==300;
        atm.restoreATM();
        assert atm.getBalance()==420;
        assert atm.restoreATM()==false;
    }

}
