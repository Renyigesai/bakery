package com.renyigesai.bakery.inventory;

public class SimpleIContainerData implements IContainerData {
    private final int[] ints;
    private final double[] doubles;
    private final String[] strings;

    public SimpleIContainerData(int ints, int doubles,int strings) {
        this.ints = new int[ints];
        this.doubles = new double[doubles];
        this.strings = new String[strings];
    }

    @Override
    public double getDouble(int pIndex) {
        return this.doubles[pIndex];
    }

    @Override
    public int getInt(int pIndex) {
        return this.ints[pIndex];
    }

    @Override
    public String getString(int pIndex) {
        return this.strings[pIndex];
    }

    @Override
    public void setDouble(int pIndex, double pValue) {
       this.doubles[pIndex] = pValue;
    }

    @Override
    public void setInt(int pIndex, int pValue) {
      this.ints[pIndex] = pValue;
    }

    @Override
    public void setString(int pIndex, String pValue) {
       this.strings[pIndex] = pValue;
    }

    public int getCount() {
        return this.ints.length;
    }
}
