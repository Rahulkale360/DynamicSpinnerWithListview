package com.example.rahul.dynamiclistview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ListView lview;
    Spinner sp;
    String path = "/storage/emulated/0/";
    File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = findViewById(R.id.sp);
        lview=findViewById(R.id.lview);
        int status = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (status == PackageManager.PERMISSION_GRANTED)
        {
            readFiles();
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},11);

        }



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            readFiles();
        }
        else
        {
            Toast.makeText(this, "If you Dont allow then cant read Internal storage", Toast.LENGTH_SHORT).show();
        }
    }


    public void readFiles()
    {
        f=new File(path);
        if(!f.exists())
        {
            path="/storage/sdcard0/";
            f=new File(path);
        }
        String [] folders=f.list();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,folders);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_item=sp.getSelectedItem().toString();
                String path1=path+selected_item;
                f=new File(path1);
                if(f.isDirectory())
                {
                    String files[]=f.list();
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.select_dialog_singlechoice,files);
                    lview.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



}
