package ro.pub.cs.systems.eim.practicaltest02;

import java.sql.Date;
import java.util.Calendar;

public class CurrencyConversion {
    private long updated;
    private double value;

    public CurrencyConversion(double value, long updated) {
        this.value = value;
        this.updated = updated;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isExpired() {
        long c = Calendar.getInstance().getTimeInMillis();
        c = c - Constants.MINUTE;

        return (updated - c) < 0;
    }

    @Override
    public String toString() {
        return "CurrencyConversion{" +
                "updated='" + updated + '\'' +
                ", value=" + value +
                '}';
    }
}
