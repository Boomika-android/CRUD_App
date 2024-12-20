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
    EditText id,name,contact,dob;
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
        name = (EditText) findViewById(R.id.name);
        id = (EditText) findViewById(R.id.id);
        contact = (EditText) findViewById(R.id.contact);
        dob = (EditText) findViewById(R.id.dob);
        insert = (Button) findViewById(R.id.btn_insert);
        update = (Button) findViewById(R.id.btn_update);
        delete = (Button) findViewById(R.id.btn_delete);
        view = (Button) findViewById(R.id.btn_view);
        dbHelper = new DBHelper(MainActivity.this);
        view_all();

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = dbHelper.insert_data(name.getText().toString(), contact.getText().toString(), dob.getText().toString());
                if (b == true) {
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                }
            }

            });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = dbHelper.update_data(id.getText().toString(), name.getText().toString(), contact.getText().toString(), dob.getText().toString());
                if (b == true) {
                    Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = dbHelper.delete_data(id.getText().toString());
                if (i > 0){
                    Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void view_all() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.view_data();
                if (cursor.getCount() == 0){
                    show_Message("Error", "No data found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()){
                    buffer.append("Id : "+ cursor.getString(0)+ "\n" );
                    buffer.append("Name : "+ cursor.getString(1)+ "\n" );
                    buffer.append("Contact : "+ cursor.getString(2)+ "\n" );
                    buffer.append("Dob : "+ cursor.getString(3)+ "\n" +"\n" );
                }
                show_Message( buffer.toString(),"Data");

            }
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