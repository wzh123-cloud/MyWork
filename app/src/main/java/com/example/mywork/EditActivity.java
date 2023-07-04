package com.example.mywork;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mywork.bean.Note;
import com.example.mywork.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
//事件修改
public class EditActivity extends AppCompatActivity {

    private Note note;
    private EditText etTitle,etEditor,etContent;

    private NoteDbOpenHelper mNoteDbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
//控件获取
        etTitle = findViewById(R.id.et_title);
        etEditor = findViewById(R.id.et_editor);
        etContent = findViewById(R.id.et_content);

        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        if (note != null) {
            //标题、内容设置
            etTitle.setText(note.getTitle());
            etEditor.setText(note.getEditor());
            etContent.setText(note.getContent());
        }
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);
    }
//判断标题
    public void save(View view) {
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

        note.setTitle(title);
        note.setEditor(editor);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        long rowId = mNoteDbOpenHelper.updateData(note);
        //修改设置
        if (rowId != -1) {
            ToastUtil.toastShort(this, "修改成功！");
            this.finish();
        }else{
            ToastUtil.toastShort(this, "修改失败！");
        }

    }
//时间设置
    private String getCurrentTimeFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}