package com.example.grihini.grihini.Shopping;

/**
 * Created by ag on 5/1/2015.
 */

public class Shopping {
    private int mId;
    private String mContent;

    public Shopping(int id, String content) {
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
