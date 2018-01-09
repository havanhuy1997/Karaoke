package com.example.huyha.models.localData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.huyha.models.Path;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by huyva on 1/7/2018.
 */

public class Database {
    private static SQLiteDatabase instance = null;
    private static Context mContext;
    private static final String TAG = "Database";
    private static final String pathDir = Path.databaseDir;
    private static final String databaseName = Path.databaseName;


    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public SQLiteDatabase getInstance(){
        if (instance == null || !instance.isOpen() ){
            instance = connectDatase();
            return instance;
        }
        return instance;
    }
    private boolean checkExistDatabase(){
        if (mContext != null) {
            File databaseFile = mContext.getDatabasePath( databaseName);
            if (databaseFile.exists()) {
                return true;
            } else return false;
        }
        return false;
    }

    public void createDatabaseDirectory(){
        File dirPathFile = new File(mContext.getApplicationInfo().dataDir + '/' + pathDir);
        if (!dirPathFile.exists()){
            dirPathFile.mkdir();
        }
    }

    public void copyDatabaseToDirectory(){
        InputStream mInput;
        try {
            mInput = mContext.getAssets().open(databaseName);

            if (!checkExistDatabase()) {
                OutputStream databaseFile = new FileOutputStream(mContext.getApplicationInfo().dataDir + '/' + pathDir + '/' + databaseName);
                byte[] buffer = new byte[1024];
                int len = mInput.read(buffer);
                while (len  > 0 ){
                    databaseFile.write(buffer, 0 , len);
                    len = mInput.read(buffer);
                }
                databaseFile.close();
            }
            mInput.close();
        }
        catch (Exception ex){
            Log.d(TAG,ex.toString()+"Error copy database");
        }
    }

    private SQLiteDatabase connectDatase(){
        SQLiteDatabase db = mContext.openOrCreateDatabase(databaseName,mContext.MODE_PRIVATE,null);
        return db;
    }

    public void disconnect(){
        if (instance.isOpen()){
            instance.close();
        }
        instance = null;
    }

}
