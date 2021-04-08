package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nameEt;
    EditText ageEt;
     Switch check;
     Button  viewAll, add;
     ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEt = findViewById(R.id.et_name);
        ageEt = findViewById(R.id.et_age);
        check = findViewById(R.id.switch_id);
        add =  findViewById(R.id.btn_add);
         viewAll = findViewById(R.id.btn_viewAll);
         listView =  findViewById(R.id.list_view);


         listOfData();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Customer customer;
                try {
                    customer = new Customer(-1,nameEt.getText().toString(),Integer.parseInt(ageEt.getText().toString()),check.isChecked());
                    Toast.makeText(MainActivity.this,customer.toString(),Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();
                    customer =  new Customer(-1,"Error",0,false);
                }

                DataBaseOpenHelper helper =  new DataBaseOpenHelper(MainActivity.this);
                boolean success = helper.addOne(customer);
                if(success){
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                }

                ageEt.setText("");
                nameEt.setText("");
                listOfData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer  customer = (Customer) parent.getItemAtPosition(position);
                DataBaseOpenHelper db  =  new DataBaseOpenHelper(MainActivity.this);
                db.deleteEntry(customer);
                listOfData();
                Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
            }
        });


        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             listOfData();

            }
        });



    }



    private void listOfData() {
        DataBaseOpenHelper db =  new DataBaseOpenHelper(MainActivity.this);
        List<Customer> customers =  db.getAll();

        ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, customers);

        listView.setAdapter(adapter);
    }
}
