package com.example.grihini.grihini;

/**
 * Created by ag on 5/1/2015.
 */
public class Expenses {
  
  private int mId;
    private String mContent;

    public Expenses(int id, String content) {
        mId = id;
        mContent = content;
    }
    public int getId() {
        return mId;
    }
    public void setId(int id) {
        mId = id;
    }
    public String getContent() {
        return mContent;
    }
    public void setContent(String content) {
        mContent = content;
    }
}
