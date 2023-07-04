package com.example.mywork;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mywork.bean.Note;
import com.example.mywork.util.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
//事件添加
public class AddActivity extends AppCompatActivity {

    private EditText etTitle,etContent,etEditor;

    private com.example.mywork.NoteDbOpenHelper mNoteDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
//获取控件
        etTitle = findViewById(R.id.et_title);
        etEditor = findViewById(R.id.et_editor);
        etContent = findViewById(R.id.et_content);
        mNoteDbOpenHelper = new com.example.mywork.NoteDbOpenHelper(this);

    }
//标题为空判断
    public void add(View view) {
        String title = etTitle.getText().toString();
        String editor = etEditor.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.toastShort(this, "标题不能为空！");
            return;
        }

        if (TextUtils.isEmpty(editor)) {
            editor = "admin"; // 设置默认的作者名
        }
        Note note = new Note();

        note.setTitle(title);
        note.setEditor(editor);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        long row = mNoteDbOpenHelper.insertData(note);
        //指针符合添加成功
        if (row != -1) {
            ToastUtil.toastShort(this,"添加成功！");
            this.finish();
        }else {
            ToastUtil.toastShort(this,"添加失败！");
        }

    }
//事件获取及设置
    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}