package com.smartdone.simpleencrypt.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
    private static Bitmap app;
    private static Bitmap zip;
    private static Bitmap unknown;

    public FileListAdapter(Context context, List<File> files) {
        this.context = context;
        this.files = files;
        folder = zoomBitmap(R.drawable.le_folder);
        document = zoomBitmap(R.drawable.le_file);
        app = zoomBitmap(R.drawable.le_apps);
        zip = zoomBitmap(R.drawable.le_zip);
        unknown = zoomBitmap(R.drawable.le_unknown);
    }

    private Bitmap zoomBitmap(int id){
        Bitmap ret;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        int width = bitmap.getWidth();
        float scale = 72f/width;
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        ret = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return ret;
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
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.cb_selected);
        textView.setText(files.get(i).getName());
        if(files.get(i).isDirectory()) {
            imageView.setImageBitmap(folder);
            textView.setTextColor(Color.BLACK);
            checkBox.setVisibility(View.INVISIBLE);
        }else{
            if(files.get(i).getName().contains(".")) {
                String prefix = files.get(i).getName().substring(files.get(i).getName().indexOf("."));
                if (prefix.equals(".apk")) {
                    imageView.setImageBitmap(app);
                } else if (prefix.equals(".txt")) {
                    imageView.setImageBitmap(document);
                } else if (prefix.equals(".zip")) {
                    imageView.setImageBitmap(zip);
                } else {
                    imageView.setImageBitmap(unknown);
                }
            }else {
                imageView.setImageBitmap(unknown);
            }

            textView.setTextColor(Color.BLACK);
            checkBox.setVisibility(View.VISIBLE);
        }

        return v;
    }
}
