package com.tr.p0341_simplesqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{

	final static String SQL = "SQL_LOG : "; 
	
	EditText editEmail;
	EditText editName;
	EditText editId;
	
	Button btnAdd;
	Button btnRead;
	Button btnClear;
	
	Button btnUpdate;
	Button btnDelete;
	
	DBHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    btnAdd = (Button) findViewById(R.id.btnAdd);
	    btnAdd.setOnClickListener(this);

	    btnRead = (Button) findViewById(R.id.btnRead);
	    btnRead.setOnClickListener(this);

	    btnClear = (Button) findViewById(R.id.btnClear);
	    btnClear.setOnClickListener(this);

	    editName = (EditText) findViewById(R.id.editName);
	    editEmail = (EditText) findViewById(R.id.editEmail);
	    editId = (EditText) findViewById(R.id.editId);
	    
	    btnUpdate = (Button) findViewById(R.id.btnUpdate);
	    btnUpdate.setOnClickListener(this);

	    btnDelete = (Button) findViewById(R.id.btnDelete);
	    btnDelete.setOnClickListener(this);
	    
	    // создаем объект для создания и управления версиями БД
	    dbHelper = new DBHelper(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, "db1", null, 1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			Log.d(SQL, "--- onCreate database ---");
		      // создаем таблицу с полями
		    db.execSQL("create table mytable ("
		    	+ "id integer primary key autoincrement," 
		        + "name text,"
		        + "email text" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// создаем объект для данных
	    ContentValues cv = new ContentValues();
	    
	    // получаем данные из полей ввода
	    String name = editName.getText().toString();
	    String email = editEmail.getText().toString();
	    String id = editId.getText().toString();

	    // подключаемся к БД
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    

	    switch (v.getId()) {
	    case R.id.btnAdd:
	      Log.d(SQL, "--- Insert in mytable: ---");
	      // подготовим данные для вставки в виде пар: наименование столбца - значение
	      
	      cv.put("name", name);
	      cv.put("email", email);
	      // вставляем запись и получаем ее ID
	      long rowID = db.insert("mytable", null, cv);
	      Log.d(SQL, "row inserted, ID = " + rowID);
	      break;
	      
	    case R.id.btnRead:
	      Log.d(SQL, "--- Rows in mytable: ---");
	      // делаем запрос всех данных из таблицы mytable, получаем Cursor 
	      Cursor c = db.query("mytable", null, null, null, null, null, null);

	      // ставим позицию курсора на первую строку выборки
	      // если в выборке нет строк, вернется false
	      if (c.moveToFirst()) {

	        // определяем номера столбцов по имени в выборке
	        int idColIndex = c.getColumnIndex("id");
	        int nameColIndex = c.getColumnIndex("name");
	        int emailColIndex = c.getColumnIndex("email");

	        do {
	          // получаем значения по номерам столбцов и пишем все в лог
	          Log.d(SQL,
	              "ID = " + c.getInt(idColIndex) + 
	              ", name = " + c.getString(nameColIndex) + 
	              ", email = " + c.getString(emailColIndex));
	          // переход на следующую строку 
	          // а если следующей нет (текущая - последняя), то false - выходим из цикла
	        } while (c.moveToNext());
	      } else
	        Log.d(SQL, "0 rows");
	      c.close();
	      break;
	      
	    case R.id.btnClear:
	      Log.d(SQL, "--- Clear mytable: ---");
	      // удаляем все записи
	      int clearCount = db.delete("mytable", null, null);
	      Log.d(SQL, "deleted rows count = " + clearCount);
	      break;
	      
	    case R.id.btnUpdate:
	        if (id.equalsIgnoreCase("")) {
	          break;
	        }
	        Log.d(SQL, "--- Update mytabe: ---");
	        // подготовим значения для обновления
	        cv.put("name", name);
	        cv.put("email", email);
	        // обновляем по id
	        int updCount = db.update("mytable", cv, "id = ?",
	            new String[] { id });
	        Log.d(SQL, "updated rows count = " + updCount);
	        break;
	      case R.id.btnDelete:
	        if (id.equalsIgnoreCase("")) {
	          break;
	        }
	        Log.d(SQL, "--- Delete from mytabe: ---");
	        // удаляем по id
	        int delCount = db.delete("mytable", "id = " + id, null);
	        Log.d(SQL, "deleted rows count = " + delCount);
	        break;
	    }
	    // закрываем подключение к БД
	    dbHelper.close();
	}
}
