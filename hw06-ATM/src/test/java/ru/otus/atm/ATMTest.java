package ru.otus.atm;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ATMTest {
    @Test
    public void newATMTest() throws IllegalDenominationException {
        ATM atm = new ATM();
        Integer[] denominations = {5000, 1000, 500, 100, 50, 10};
        Assert.assertTrue(Arrays.equals(denominations, atm.getDenominations().toArray()));
    }

    @Test
    public void ATMWithOtherDenomination() throws IllegalDenominationException {
        ATM atm = new ATM(50, 100, 1000);
        Assert.assertTrue(Arrays.equals(new Integer[]{1000, 100, 50}, atm.getDenominations().toArray()));
    }

    @Test(expected =  IllegalDenominationException.class)
    public void ATMWithWrongDenomination() throws IllegalDenominationException {
        ATM atm = new ATM(50, -100, 1000);
    }

    @Test
    public void putMoneyTest() throws IllegalDenominationException, IllegalCountException {
        ATM atm = new ATM();
        atm.putMoney(500, 5);
        atm.putMoney(10, 2);
        atm.putMoney(5000, 3);
        atm.putMoney(5000, 2);
        Assert.assertEquals(27520, atm.getBalance());
    }

    @Test(expected =  IllegalDenominationException.class)
    public void putWrongDenominationMoneyTest() throws IllegalDenominationException, IllegalCountException {
        ATM atm = new ATM();
        atm.putMoney(200, 5);
        atm.putMoney(10, 2);
    }

    @Test(expected =  IllegalCountException.class)
    public void putWrongCountMoneyTest1() throws IllegalDenominationException, IllegalCountException {
        ATM atm = new ATM();
        atm.putMoney(500, -1);
    }

    @Test(expected =  IllegalCountException.class)
    public void putWrongCountMoneyTest2() throws IllegalDenominationException, IllegalCountException {
        ATM atm = new ATM();
        atm.putMoney(500, 0);
    }

    @Test
    public void getMoneyTest() throws IllegalDenominationException, IllegalAmountException, ImpossibleAmountException, IllegalCountException {
        ATM atm = new ATM();
        atm.putMoney(500, 5);
        atm.putMoney(10, 12);
        atm.putMoney(1000, 3);
        int balanceBefore = atm.getBalance();

        List<Bundle> myMoney = atm.getMoney(1610);
        List<Bundle> money = new ArrayList<Bundle>(Arrays.asList(new Bundle(1000, 1), new Bundle(500, 1), new Bundle(10, 11)));
        Assert.assertEquals(money.toArray(), myMoney.toArray());
        Assert.assertEquals(1610, balanceBefore - atm.getBalance());
    }

    @Test (expected =  ImpossibleAmountException.class)
    public void getWrongMoneyTest() throws IllegalDenominationException, IllegalAmountException, ImpossibleAmountException, IllegalCountException {
        ATM atm = new ATM();
        atm.putMoney(500, 5);
        atm.putMoney(10, 12);
        atm.putMoney(1000, 3);
        int balanceBefore = atm.getBalance();

        List<Bundle> myMoney = atm.getMoney(630);
        Assert.assertEquals(balanceBefore, atm.getBalance());
    }

    @Test
    public void getMoneyTest2() throws IllegalDenominationException, IllegalAmountException, ImpossibleAmountException, IllegalCountException {
        ATM atm = new ATM();
        atm.putMoney(500, 5);
        atm.putMoney(10, 12);
        atm.putMoney(1000, 3);
        int balanceBefore = atm.getBalance();
        try {
            List<Bundle> myMoney = atm.getMoney(630);
        }catch (ImpossibleAmountException e){ }

        Assert.assertEquals(balanceBefore, atm.getBalance());
    }
}
