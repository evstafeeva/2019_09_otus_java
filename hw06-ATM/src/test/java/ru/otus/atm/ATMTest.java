package ru.otus.atm;


import org.junit.Assert;
import org.junit.Test;
import ru.otus.atm.exceptions.IllegalAmountException;
import ru.otus.atm.exceptions.ImpossibleAmountException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ATMTest {

    @Test
    public void putMoneyTest(){
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
    public  void getMoneyTest() throws IllegalAmountException, ImpossibleAmountException {
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
    public void getWrongMoneyTest() throws IllegalAmountException, ImpossibleAmountException {
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


}
