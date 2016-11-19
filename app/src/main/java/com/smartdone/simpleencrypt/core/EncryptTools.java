package com.smartdone.simpleencrypt.core;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Smartdone on 2016/11/19.
 */
public class EncryptTools {

    public static final int SUCCESS = 1;
    public static final int FAILER = 2;

    /**
     * 加密方法
     *
     * @param handler  用来发送加密进度
     * @param srcpath  加密原始文件
     * @param destPath 加密之后输出文件位置
     * @param password 密码
     */
    public static void encrypt(Handler handler, String srcpath, String destPath, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(password.getBytes(), "AES");
            IvParameterSpec iv = new IvParameterSpec("SMARTDONEFLYFLYF".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, spec, iv);
            File file = new File(srcpath);
            long filelen = file.length();
            long offset = 0;
            CipherInputStream cipherInputStream = new CipherInputStream(new FileInputStream(file), cipher);
            FileOutputStream out = new FileOutputStream(destPath);
            byte[] buffer = new byte[2048];
            int len = cipherInputStream.read(buffer);

            while (len > 0) {
                out.write(buffer, 0, len);
                out.flush();
                offset += len;
                Message message = new Message();
                EncryptMessage encryptmessage = new EncryptMessage();
                message.what = SUCCESS;
                encryptmessage.setOffset(offset);
                encryptmessage.setTotalLength(filelen);
                message.obj = encryptmessage;
                handler.sendMessage(message);
                len = cipherInputStream.read(buffer);
            }
            Message message = new Message();
            EncryptMessage encryptmessage = new EncryptMessage();
            message.what = SUCCESS;
            encryptmessage.setOffset(filelen);
            encryptmessage.setTotalLength(filelen);
            message.obj = encryptmessage;
            handler.sendMessage(message);
            out.close();
            cipherInputStream.close();
        } catch (Exception e) {
            Log.e(EncryptTools.class.getName(), e.getMessage());
            Message message = new Message();
            message.what = FAILER;
            handler.sendMessage(message);
        }
    }

    /**
     * 解密方法
     *
     * @param handler  用来传递解密进度
     * @param srcpath  加密文件路径
     * @param destPath 解密后文件存放路径
     * @param password 密码
     */
    public static void decrypt(Handler handler, String srcpath, String destPath, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(password.getBytes(), "AES");
            IvParameterSpec iv = new IvParameterSpec("SMARTDONEFLYFLYF".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, spec, iv);
            File file = new File(srcpath);
            long filelen = file.length();
            long offset = 0;
            FileInputStream fileInputStream = new FileInputStream(file);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(new FileOutputStream(destPath), cipher);
            byte[] buffer = new byte[2048];
            int len = fileInputStream.read(buffer);
            while (len > 0) {
                cipherOutputStream.write(buffer, 0, len);
                cipherOutputStream.flush();
                offset += len;
                Message message = new Message();
                EncryptMessage encryptmessage = new EncryptMessage();
                message.what = SUCCESS;
                encryptmessage.setOffset(offset);
                encryptmessage.setTotalLength(filelen);
                message.obj = encryptmessage;
                handler.sendMessage(message);
                len = fileInputStream.read(buffer);
            }
            Message message = new Message();
            EncryptMessage encryptmessage = new EncryptMessage();
            message.what = SUCCESS;
            encryptmessage.setOffset(filelen);
            encryptmessage.setTotalLength(filelen);
            message.obj = encryptmessage;
            handler.sendMessage(message);
            cipherOutputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            Log.e(EncryptTools.class.getName(), e.getMessage());
            Message message = new Message();
            message.what = FAILER;
            handler.sendMessage(message);
        }
    }

}
