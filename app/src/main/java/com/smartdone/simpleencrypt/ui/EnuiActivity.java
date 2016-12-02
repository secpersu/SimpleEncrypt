package com.smartdone.simpleencrypt.ui;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.smartdone.simpleencrypt.R;
import com.smartdone.simpleencrypt.adapter.FileListAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class EnuiActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private List<View> views;
    private ListView left;
    private File root;
    private List<File> files;
    private TextView left_text;
    private TextView right_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enui);
        init_widget();

    }

    private void resetColor(){
        left_text.setTextColor(Color.rgb(199,203,214));
        right_text.setTextColor(Color.rgb(199,203,214));
    }

    private void init_widget() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        toolbar.setTitle(R.string.filemanager);
        drawerLayout.addDrawerListener(drawerToggle);
        viewPager = (ViewPager) findViewById(R.id.id_pageAdapter);
        left_text = (TextView) findViewById(R.id.text_page_left);
        right_text = (TextView) findViewById(R.id.text_page_right);
        left_text.setOnClickListener(this);
        right_text.setOnClickListener(this);
        views = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        View v1 = inflater.inflate(R.layout.list, null);
        View v2 = inflater.inflate(R.layout.gird, null);
        init_left_view(v1);
        views.add(v1);
        views.add(v2);
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View v = views.get(position);
                container.addView(v);
                return v;
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetColor();
                if(position == 0) {
                    left_text.setTextColor(Color.WHITE);
                }else {
                    right_text.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void init_left_view(View view) {
        left = (ListView) view.findViewById(R.id.list_filelist);
        root = new File("/sdcard");
        files = new ArrayList<>();
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                files.add(file);
            }
        }
        for (File file : root.listFiles()) {
            if (!file.isDirectory()) {
                files.add(file);
            }
        }
        final FileListAdapter adapter = new FileListAdapter(this, files);
        left.setAdapter(adapter);
        left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                File cfile = (File) adapter.getItem(i);
                if (cfile.isDirectory()) {
                    root = cfile;
                    files.clear();
                    for (File file : root.listFiles()) {
                        if (file.isDirectory()) {
                            files.add(file);
                        }
                    }
                    for (File file : root.listFiles()) {
                        if (!file.isDirectory()) {
                            files.add(file);
                        }
                    }
                    if (root.getParentFile() != null) {
                        if (!root.getParent().equals("/")) {
                            Log.w("TAG xxx", root.getParent());
                            files.add(0, root.getParentFile());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text_page_left:
                resetColor();
                left_text.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(0);
                break;
            case R.id.text_page_right:
                resetColor();
                right_text.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }
}
