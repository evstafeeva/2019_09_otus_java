package ru.otus.atm;

import org.junit.Test;
import ru.otus.atm.cell.Cell;
import ru.otus.atm.cell.Denominations;

import java.util.Arrays;
import java.util.Map;

public class ATMDepartamentTest {

    @Test
    public void groupsTest(){
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

        //210
        ATM atm4 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));

        ATMDepartment department = new ATMDepartment();
        department.addATM(Arrays.asList(atmSuperGroup, atm4));
        assert department.getBalance()==1350;

        department.removeATM(atm4);
        assert department.getBalance()==1140;
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
        //5010
        ATM atm3 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIVE_THOUSAND_RUBLES));

        ATM atmSuperGroup = new ATMGroup(Arrays.asList(atmSubGroup, atm3));

        //5010
        ATM atm4 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIVE_THOUSAND_RUBLES));

        ATMDepartment department = new ATMDepartment(Arrays.asList(atmSuperGroup, atm4));


        Map<Denominations, Cell> allMoney = department.getCells();
        assert allMoney.get(Denominations.TEN_RUBLES).getCount() == 3;
        assert allMoney.get(Denominations.FIFTY_RUBLES).getCount() == 2;
        assert allMoney.get(Denominations.HUNDRED_RUBLES).getCount() == 1;
        assert allMoney.get(Denominations.FIVE_HUNDRED_RUBLES).getCount() == 0;
        assert allMoney.get(Denominations.THOUSAND_RUBLES).getCount() == 0;
        assert allMoney.get(Denominations.FIVE_THOUSAND_RUBLES).getCount() == 2;
    }

    @Test
    public void restoreATMOneStepTest1() throws Exception {
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

        //60
        ATM atm4 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES));
        //160
        atm4.putMoney(Arrays.asList(
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES));
        //360
        atm4.putMoney(Arrays.asList(
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));
        assert atm4.getBalance() == 360;

        ATMDepartment department = new ATMDepartment(Arrays.asList(atmSuperGroup, atm4));

        assert department.getBalance() == 16_100;
        assert department.restoreATMOneStep(atm4);
        assert department.getBalance() == 15_900;
    }

    @Test
    public void restoreATMOneStep2Test() throws Exception {
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

        //60
        ATM atm4 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES));
        //160
        atm4.putMoney(Arrays.asList(
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES));
        //360
        atm4.putMoney(Arrays.asList(
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));
        assert atm4.getBalance() == 360;

        ATMDepartment department = new ATMDepartment(Arrays.asList(atmSuperGroup, atm4));

        assert department.getBalance() == 16_100;
        assert department.restoreATMOneStep();
        assert department.getBalance() == 5680;
    }

    @Test
    public void restoreATMTest() throws Exception {
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

        //60
        ATM atm4 = new ATMImpl(Arrays.asList(
                Denominations.TEN_RUBLES,
                Denominations.FIFTY_RUBLES));
        //160
        atm4.putMoney(Arrays.asList(
                Denominations.FIFTY_RUBLES,
                Denominations.FIFTY_RUBLES));
        //360
        atm4.putMoney(Arrays.asList(
                Denominations.HUNDRED_RUBLES,
                Denominations.HUNDRED_RUBLES));
        assert atm4.getBalance() == 360;

        ATMDepartment department = new ATMDepartment(Arrays.asList(atmSuperGroup, atm4));

        assert department.getBalance() == 16_100;
        assert department.restoreATM();
        assert department.getBalance() == 5280;
    }

}
