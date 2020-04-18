package ru.otus.atm;

import org.junit.Test;
import ru.otus.atm.cell.Cell;
import ru.otus.atm.cell.Denominations;

import java.util.Arrays;
import java.util.Map;

public class ATMGroupTest {
    @Test
    public void balanceTest(){
        //420
        ATM atm1 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));
        //410
        ATM atm2 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));
        //310
        ATM atm3 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));

        ATM atmGroup = new ATMGroup(Arrays.asList(atm1, atm2, atm3));
        assert atmGroup.getBalance() == 1140;

        ((ATMGroup) atmGroup).removeAtm(atm1);
        assert atmGroup.getBalance() == 720;

        ATM atm4 = new ATMImpl(Arrays.asList(
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));
        ((ATMGroup) atmGroup).addATM(atm4);
        assert atmGroup.getBalance() == 920;
    }

    @Test
    public void groupInGroupTest(){
        //420
        ATM atm1 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));
        //410
        ATM atm2 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));

        ATM atmSubGroup = new ATMGroup(Arrays.asList(atm1, atm2));
        //310
        ATM atm3 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));

        ATM atmSuperGroup = new ATMGroup(Arrays.asList(atmSubGroup, atm3));
        assert atmSuperGroup.getBalance() == 1140;

        ((ATMGroup) atmSuperGroup).removeAtm(atmSubGroup);
        assert atmSuperGroup.getBalance() == 310;
    }

    @Test
    public void getCellsTest() {
        //150
        ATM atm1 = new ATMImpl(Arrays.asList(
                Denominations.FIFTY_RUBLES,
                Denominations.HUNDRED_RUBLES));
        //60
        ATM atm2 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES));

        ATM atmSubGroup = new ATMGroup(Arrays.asList(atm1, atm2));
        //10
        ATM atm3 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIVE_THOUSAND_RUBLES));

        ATM atmSuperGroup = new ATMGroup(Arrays.asList(atmSubGroup, atm3));

        Map<Denominations, Cell> allMoney = atmSuperGroup.getCells();
        assert allMoney.get(Denominations.TEN_RUBLES).getCount() == 2;
        assert allMoney.get(Denominations.FIFTY_RUBLES).getCount() == 2;
        assert allMoney.get(Denominations.HUNDRED_RUBLES).getCount() == 1;
        assert allMoney.get(Denominations.FIVE_HUNDRED_RUBLES).getCount() == 0;
        assert allMoney.get(Denominations.THOUSAND_RUBLES).getCount() == 0;
        assert allMoney.get(Denominations.FIVE_THOUSAND_RUBLES).getCount() == 1;
    }

    @Test
    public void groupUndo() throws Exception {
        //150
        ATM atm1 = new ATMImpl(Arrays.asList(
                Denominations.FIFTY_RUBLES,
                Denominations.HUNDRED_RUBLES));
        //5160
        atm1.putMoney(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIVE_THOUSAND_RUBLES));
        assert atm1.getBalance() == 5160;

        //60
        ATM atm2 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES));
        //160
        atm2.putMoney(Arrays.asList(
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES));
        //360
        atm2.putMoney(Arrays.asList(
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));
        assert atm2.getBalance() == 360;

        ATM atmSubGroup = new ATMGroup(Arrays.asList(atm1, atm2));
        assert atmSubGroup.getBalance()==5520;
        //5010
        ATM atm3 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIVE_THOUSAND_RUBLES));
        //5210
        atm3.putMoney(Arrays.asList(
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));
        //10220
        atm3.putMoney(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIVE_THOUSAND_RUBLES));

        ATM atmSuperGroup = new ATMGroup(Arrays.asList(atmSubGroup, atm3));
        assert atmSuperGroup.getBalance() == 15740;
        assert atmSuperGroup.restoreATM();
        assert atmSuperGroup.getBalance() == 5520;
        assert atmSuperGroup.restoreATM();
        assert atmSuperGroup.getBalance() == 5220;
        assert !atmSuperGroup.restoreATM();
    }

}
