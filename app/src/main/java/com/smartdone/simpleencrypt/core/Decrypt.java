package com.smartdone.simpleencrypt.core;

import android.os.Handler;

/**
 * Created by Smartdone on 2016/11/19.
 */
public class Decrypt extends Thread{
    private Handler handler;
    private String srcpath;
    private String destpath;
    private String password;

    public Decrypt(Handler handler, String srcpath, String destpath, String password){
        this.handler = handler;
        this.srcpath = srcpath;
        this.destpath = destpath;
        this.password = password;
        this.start();
    }

    @Override
    public void run() {
        EncryptTools.decrypt(this.handler, this.srcpath, this.destpath, this.password);
    }
}
