package ru.otus.atm;


import java.util.*;

public class ATM {

    private Map<Integer, Cell> cells = new HashMap<Integer, Cell>();
    private List<Integer> denominations;    //возможные номиналы купюр

    //стандартный набор купюр
    public ATM() throws IllegalDenominationException {
        this(10, 50, 100, 500, 1000, 5000);
    }

    //пользовательсикй набор купюр
    public ATM(Integer ... denominations) throws IllegalDenominationException {
        for(Integer denomination:denominations){
            if (denomination <= 0)
                throw new IllegalDenominationException();
            cells.put(denomination, new Cell());
        }
        this.denominations = new ArrayList<Integer>(Arrays.asList(denominations));
        Collections.sort(this.denominations, Collections.reverseOrder());
    }

    //вносим купюры в АТМ
    public void putMoney(Integer denomination, Integer count) throws IllegalDenominationException, IllegalCountException {
        if(count<=0)
            throw new IllegalCountException();
        if(denominations.contains(denomination)) {
            cells.get(denomination).addBanknote(count);
        }else throw new IllegalDenominationException();
    }

    //снимаем деньги
    public List<Bundle> getMoney(int amount) throws IllegalAmountException, ImpossibleAmountException {
        if(amount<=0)
            throw new IllegalAmountException();

        List<Bundle> combination = new ArrayList<Bundle>();

        //сначала проверяем на возможность снять такую сумму в принципе
        if(!checkOpportunity(amount, combination))
            throw new ImpossibleAmountException();
        //если возможность есть, забираем купюры из ячеек
        getMoneyFromCells(combination);
        return combination;
    }


    private void getMoneyFromCells(List<Bundle> combination) {
        for(Bundle bundle:combination){
            cells.get(bundle.getDenomination()).getBanknote(bundle.getCount());
        }
    }

    private boolean checkOpportunity(int amount, List<Bundle> combination){
        for(Integer denomination:denominations){
            if(cells.get(denomination).getCount() == 0 || amount/denomination <= 0)
                continue;
            if(amount / denomination >= cells.get(denomination).getCount()){
                combination.add(new Bundle(denomination, cells.get(denomination).getCount()));
                amount -= denomination * (cells.get(denomination).getCount());
            }else{
                combination.add(new Bundle(denomination, amount / denomination));
                amount -= denomination * (amount / denomination);
            }
        }
        if(amount != 0)
            return false;
        return true;
    }

    public int getBalance(){
        int sum = 0;
        for(Integer denomination:denominations){
            sum+=cells.get(denomination).getCount()*denomination;
        }
        return sum;
    }

    public Map<Integer, Cell> getCells() {
        return cells;
    }

    public List<Integer> getDenominations() {
        return denominations;
    }
}
