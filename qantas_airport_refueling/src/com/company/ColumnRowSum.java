package com.company;

import java.util.List;

public class ColumnRowSum {
    private List<Integer> xSum;
    private List<Integer> ySum;

    public List<Integer> getxSum() {
        return xSum;
    }

    public void setxSum(List<Integer> xSum) {
        this.xSum = xSum;
    }

    public List<Integer> getySum() {
        return ySum;
    }

    public void setySum(List<Integer> ySum) {
        this.ySum = ySum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        ColumnRowSum columnRowSum = (ColumnRowSum) obj;
        if (this.xSum.isEmpty() || this.ySum.isEmpty()
                || columnRowSum.xSum.isEmpty() || columnRowSum.ySum.isEmpty()) {
            return false;
        }
        if (this.xSum.size() != columnRowSum.xSum.size() && this.ySum.size() != columnRowSum.ySum.size()) {
            return false;
        }
        for (int i = 0; i < this.xSum.size(); i++) {
            if (this.xSum.get(i).equals(columnRowSum.xSum.get(i))) {
                continue;
            } else {
                return false;
            }
        }

        for (int i = 0; i < this.ySum.size(); i++) {
            if (this.ySum.get(i).equals(columnRowSum.ySum.get(i))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if (xSum == null || xSum.isEmpty() || ySum == null || ySum.isEmpty() || xSum.size() != ySum.size()) {
            return hashCode;
        }
        for (int i = 0; i < xSum.size(); i++) {
            hashCode = (int) (hashCode + ((xSum.get(i) + 1 / ySum.get(i) + 1) * Math.pow(10, i)));
        }
        return hashCode;
    }
}
