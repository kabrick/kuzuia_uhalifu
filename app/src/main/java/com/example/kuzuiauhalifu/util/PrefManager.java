package com.example.kuzuiauhalifu.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String ID = "user_id";
    private static final String PREF_NAME = "preferences";
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    public PrefManager(Context context) {
        int PRIVATE_MODE = 0;
        Context mContext = context;
        this.pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public String getUserId(){
        return this.pref.getString(ID, "");
    }

    public void setUserId(String user_id){
        this.editor.putString(ID, user_id);
        this.editor.commit();
    }

    public void deleteUserId(){
        this.editor.putString(ID, "0");
        this.editor.commit();
    }
}
