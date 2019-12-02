package ru.otus.atm;

public class Bundle {
    private Integer denomination;
    private Integer count;

    public Bundle(Integer denomination, Integer count) {
        this.count = count;
        this.denomination = denomination;
    }

    public Integer getDenomination() {
        return denomination;
    }

    public void setDenomination(Integer denomination) {
        this.denomination = denomination;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Denomination: " + denomination + ", Count: " + count;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null)
            return false;

        if (!(getClass() == obj.getClass()))
            return false;

        else {
            Bundle bundle = (Bundle) obj;
            if (((int)bundle.getCount() == (int)this.getCount()) && ((int)bundle.getDenomination() == (int)this.getDenomination()))
                return true;
            else
                return false;
        }
    }
}
