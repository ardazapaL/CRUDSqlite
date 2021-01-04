package com.iratsel.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editItem, editPrice, editTotal, editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        /* Initialize variable */
        editItem = findViewById(R.id.editText_item);
        editPrice = findViewById(R.id.editText_price);
        editTotal = findViewById(R.id.editText_total);
        editTextId = findViewById(R.id.editTextId);
        btnAddData = findViewById(R.id.button_add);
        btnViewAll = findViewById(R.id.button_view);
        btnUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);

        AddData();
        viewAll();
        UpdateData();
        deleteData();
    }

    /* DELETE DATA */
    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows =
                                myDb.deleteData(editTextId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data Failed to Deleted!",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /* UPDATE DATA */
    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate =
                                myDb.updateData(editTextId.getText().toString(),
                                        editItem.getText().toString(), editPrice.getText().toString(),
                                        editTotal.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data Failed to Update",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /* ADD FUNCTION */
    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted =
                                myDb.insertData(editItem.getText().toString(),
                                        editPrice.getText().toString(),
                                        editTotal.getText().toString() );
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /* VIEW ALL FUNCTION */
    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Noting Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer(); while (res.moveToNext() ) {
                            buffer.append("Id :"+ res.getString(0)+"\n");  buffer.append("Item :"+ res.getString(1)+"\n");  buffer.append("Price :"+ res.getString(2)+"\n");  buffer.append("Total :"+ res.getString(3)+"\n\n");  }
                        // show all data
                        showMessage("Data",buffer.toString());  }
                }
        );
    }

    /* ALERT FUNCTION */
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}