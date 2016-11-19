package com.smartdone.simpleencrypt.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartdone.simpleencrypt.R;

import java.io.File;
import java.util.List;

/**
 * Created by Smartdone on 2016/11/19.
 */
public class FileListAdapter extends BaseAdapter {

    private Context context;
    private List<File> files;
    private static Bitmap folder;
    private static Bitmap document;

    public FileListAdapter(Context context, List<File> files) {
        this.context = context;
        this.files = files;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.document);
        int width = bitmap.getWidth();
        float scale = 72f/width;
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        document = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.folder);
        width = bitmap.getWidth();
        scale = 72f/width;
        Matrix matrix1 = new Matrix();
        matrix1.postScale(scale,scale);
        folder = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix1, true);
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int i) {
        return files.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_icon);
        TextView textView = (TextView) v.findViewById(R.id.tv_filename);
        textView.setText(files.get(i).getName());
        if(files.get(i).isDirectory()) {

            imageView.setImageBitmap(folder);
            textView.setTextColor(Color.BLUE);
        }else{
            imageView.setImageBitmap(document);
            textView.setTextColor(Color.BLACK);
        }
        return v;
    }
}
