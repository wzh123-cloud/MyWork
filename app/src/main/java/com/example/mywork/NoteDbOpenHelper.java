package com.example.mywork;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.mywork.bean.Note;

import java.util.ArrayList;
import java.util.List;
//数据库链接实现类
public class NoteDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dailySQLite.db";
    private static final String TABLE_NAME_NOTE = "note";
//创建note表
    private static final String CREATE_TABLE_SQL =
            "create table " + TABLE_NAME_NOTE
                    + " (id integer primary key autoincrement, title text, editor text, content text, create_time text)";


    public NoteDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }



    @Override
    //数据库创建
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//添加数据
    public long insertData(Note note) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("editor", note.getEditor());
        values.put("content", note.getContent());
        values.put("create_time", note.getCreatedTime());

        return db.insert(TABLE_NAME_NOTE, null, values);
    }
//删除数据
    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
//        return db.delete(TABLE_NAME_NOTE, "id = ?", new String[]{id});
//        return db.delete(TABLE_NAME_NOTE, "id is ?", new String[]{id});
        return db.delete(TABLE_NAME_NOTE, "id like ?", new String[]{id});
    }
//修改数据
    public int updateData(Note note) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("editor", note.getEditor());
        values.put("content", note.getContent());
        values.put("create_time", note.getCreatedTime());

        return db.update(TABLE_NAME_NOTE, values, "id like ?", new String[]{note.getId()});
    }

    public List<Note> queryAllFromDb() {

        SQLiteDatabase db = getWritableDatabase();
        List<Note> noteList = new ArrayList<>();
//查询数据
        Cursor cursor = db.query(TABLE_NAME_NOTE, null, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range")String editor = cursor.getString(cursor.getColumnIndex("editor"));
                @SuppressLint("Range")String content = cursor.getString(cursor.getColumnIndex("content"));
                @SuppressLint("Range")String createTime = cursor.getString(cursor.getColumnIndex("create_time"));

                Note note = new Note();
                note.setId(id);
                note.setTitle(title);
                note.setEditor(editor);
                note.setContent(content);
                note.setCreatedTime(createTime);

                noteList.add(note);
            }
            //关闭
            cursor.close();
        }

        return noteList;

    }

    public List<Note> queryFromDbByTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return queryAllFromDb();
        }

        SQLiteDatabase db = getWritableDatabase();
        List<Note> noteList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_NOTE, null, "title like ?", new String[]{"%"+title+"%"}, null, null, null,null);

        if (cursor != null) {

            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range")String title2 = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String editor = cursor.getString(cursor.getColumnIndex("editor"));
                @SuppressLint("Range")String content = cursor.getString(cursor.getColumnIndex("content"));
                @SuppressLint("Range") String createTime = cursor.getString(cursor.getColumnIndex("create_time"));

                Note note = new Note();
                note.setId(id);
                note.setTitle(title2);
                note.setEditor(editor);
                note.setContent(content);
                note.setCreatedTime(createTime);

                noteList.add(note);
            }
            cursor.close();
        }
        return noteList;
    }


}
