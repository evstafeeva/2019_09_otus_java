package ru.otus.atm;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment {
    private List<ATM> atms = new ArrayList<>();

   public void addATM(ATM atm){
       atms.add(atm);
   }

   public boolean removeATM (ATM atm){
       if(atms.contains(atm)){
           return atms.remove(atm);
       }
       return false;
   }

   public int getBalance(){
       int balance = 0;
       for(ATM atm:atms){
           balance += atm.getBalance();
       }
       return balance;
   }
}
