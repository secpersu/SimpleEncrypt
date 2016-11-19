package com.smartdone.simpleencrypt;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.smartdone.simpleencrypt.core.Decrypt;
import com.smartdone.simpleencrypt.core.Encrypt;
import com.smartdone.simpleencrypt.core.EncryptMessage;
import com.smartdone.simpleencrypt.core.EncryptTools;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == EncryptTools.SUCCESS) {
                if (msg.obj instanceof EncryptMessage) {
                    EncryptMessage emsg = (EncryptMessage) msg.obj;
                    textView.setText(emsg.getOffset() + "/" + emsg.getTotalLength());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
//        new Encrypt(handler, "/sdcard/X510t.zip", "/sdcard/new.zip", "1234567890123456");
        new Decrypt(handler, "/sdcard/new.zip", "/sdcard/decrypted.zip", "1234567890123456");
    }
}
