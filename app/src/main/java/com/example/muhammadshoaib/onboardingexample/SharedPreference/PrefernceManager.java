package com.example.muhammadshoaib.onboardingexample.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.muhammadshoaib.onboardingexample.R;

public class PrefernceManager {


    //declaring varriables
    private Context context;
    private SharedPreferences sharedPreferences;

    //constructor takes context as argument and initializing context object
    public PrefernceManager(Context context){

        this.context=context;

        //this method will give shared preference
        getSharedPreference();

    }

    private void getSharedPreference(){

        sharedPreferences=context.getSharedPreferences(context.getString(R.string.my_preference),Context.MODE_PRIVATE);
    }

    //this method will write shared preference
    public void writePreference(){

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_preference_key),"INIT_OK");
        editor.commit();
    }


    //this method will check preference and return a status varriable
    public boolean checkPreference(){

        boolean status = false;

        if (sharedPreferences.getString(context.getString(R.string.my_preference_key),"null").equals("null")){

            status = false;
        }

        else {

            status = true;
        }

        return status;
    }



}
