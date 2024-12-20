package com.example.crudapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText id,name = this.<EditText>findViewById(R.id.name),contact,dob;
    Button insert,update,delete,view;
    DBHelper dbHelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        id = findViewById(R.id.id);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);
        insert = findViewById(R.id.btn_insert);
        update = findViewById(R.id.btn_update);
        delete = findViewById(R.id.btn_delete);
        view = findViewById(R.id.btn_view);
        dbHelper = new DBHelper(MainActivity.this);
        view_all();

        insert.setOnClickListener(v -> {
            boolean b = dbHelper.insert_data(name.getText().toString(), contact.getText().toString(), dob.getText().toString());
            if (b) {
                Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(v -> {
            boolean b = dbHelper.update_data(id.getText().toString(), name.getText().toString(), contact.getText().toString(), dob.getText().toString());
            if (b) {
                Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(v -> {
            Integer i = dbHelper.delete_data(id.getText().toString());
            if (i > 0){
                Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void view_all() {
        view.setOnClickListener(v -> {
            Cursor cursor = dbHelper.view_data();
            if (cursor.getCount() == 0){
                show_Message("Error", "No data found");
                return;
            }
            StringBuilder buffer = new StringBuilder();
            while (cursor.moveToNext()){
                buffer.append("Id : ").append(cursor.getString(0)).append("\n");
                buffer.append("Name : ").append(cursor.getString(1)).append("\n");
                buffer.append("Contact : ").append(cursor.getString(2)).append("\n");
                buffer.append("Dob : ").append(cursor.getString(3)).append("\n").append("\n");
            }
            show_Message( buffer.toString(),"Data");
        });
    }

    public void show_Message(String Message,String Title){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }
}