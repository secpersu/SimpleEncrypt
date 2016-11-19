package com.smartdone.simpleencrypt;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.scrat.app.selectorlibrary.ImageSelector;
import com.smartdone.simpleencrypt.adapter.FileListAdapter;
import com.smartdone.simpleencrypt.core.EncryptMessage;
import com.smartdone.simpleencrypt.core.EncryptTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private File root;
    private List<File> files;

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
//        ImageSelector.show(this, 1, 10);
        root = new File("/sdcard");
        files = new ArrayList<>();
        for (File file : root.listFiles()) {
            files.add(file);
        }
        ListView listView = (ListView) findViewById(R.id.lv_filelist);
        final FileListAdapter adapter = new FileListAdapter(this, files);
        listView.setAdapter(adapter);
        getSupportActionBar().setTitle(root.getAbsolutePath());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                File cfile = (File) adapter.getItem(i);
                if(cfile.isDirectory()) {
                    root = cfile;
                    files.clear();
                    for (File file : root.listFiles()) {
                        files.add(file);
                    }
                    getSupportActionBar().setTitle(root.getAbsolutePath());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            List<String> yourSelectImgPaths = ImageSelector.getImagePaths(data);
            Log.d("imgSelector", "paths: " + yourSelectImgPaths);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
