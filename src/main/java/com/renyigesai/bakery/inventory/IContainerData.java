package com.renyigesai.bakery.inventory;

public interface IContainerData {
    default int getInt(int pIndex) {
        return 0;
    }

    default void setInt(int pIndex, int pValue) {

    }

    default double getDouble(int pIndex) {
        return 0;
    }

    default void setDouble(int pIndex, double pValue) {

    }

    default String getString(int pIndex) {
        return null;
    }

    default void setString(int pIndex, String pValue) {

    }

    int getCount();
}
