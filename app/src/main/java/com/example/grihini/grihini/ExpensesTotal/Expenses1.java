package com.example.grihini.grihini.ExpensesTotal;

/**
 * Created by ag on 5/1/2015.
 */
public class Expenses1 {
    private int mId;
    private String mDate;
    private String mCategory;
    private String mAmount;
    private String mDesc;

    public Expenses1(int id, String date, String category, String amount, String desc) {
        mId = id;
        mDate = date;
        mCategory = category;
        mAmount = amount;
        mDesc = desc;

    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String mAmount) {
        this.mAmount = mAmount;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String mDesc) {
        this.mDesc = mDesc;
    }
}

