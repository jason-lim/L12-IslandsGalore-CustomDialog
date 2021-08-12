package com.myapplicationdev.android.islandsgalore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    Button btn5Stars, btnInsert;
	ListView lv;
    ArrayList<Item> itemList;
    CustomAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(this);
        itemList.clear();
        itemList.addAll(dbh.getAllItems());
        adapter.notifyDataSetChanged();

    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_second));

        lv = findViewById(R.id.lv);
        btn5Stars = findViewById(R.id.btnShow5Stars);
        btnInsert = findViewById(R.id.btnInsert);

        DBHelper dbh = new DBHelper(this);

        itemList = dbh.getAllItems();
        adapter = new CustomAdapter(this, R.layout.row, itemList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("song", itemList.get(position));
                startActivity(i);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn5Stars.getText().equals(getString(R.string.btn5Stars))){
                    DBHelper dbh = new DBHelper(SecondActivity.this);
                    itemList.clear();
                    itemList.addAll(dbh.getAllItemsByStars(5));
                    adapter.notifyDataSetChanged();
                    btn5Stars.setText("SHOW ALL ISLANDS");
                } else {
                    DBHelper dbh = new DBHelper(SecondActivity.this);
                    itemList.clear();
                    itemList.addAll(dbh.getAllItems());
                    adapter.notifyDataSetChanged();
                    btn5Stars.setText(R.string.btn5Stars);
                }
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate the input.xml layout file
                LayoutInflater inflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.activity_main, null);

                final EditText etTitle, etSingers, etYear;
                final RatingBar ratingBar;

                // Obtain the UI component in the input.xml layout
                // It needs to be defined as "final", so that it can used in the onClick() method later
                etTitle =  viewDialog.findViewById(R.id.etName);
                etSingers = viewDialog.findViewById(R.id.etDescription);
                etYear =  viewDialog.findViewById(R.id.etSquareKm);
                ratingBar = viewDialog.findViewById(R.id.ratingBarStars);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);
                myBuilder.setView(viewDialog);  // Set the view of the dialog
                myBuilder.setTitle("Insert New Island");
                myBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Extract the data entered by the user
                        String title = etTitle.getText().toString().trim();
                        String singers = etSingers.getText().toString().trim();
                        if (title.length() == 0 || singers.length() == 0){
                            Toast.makeText(SecondActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String year_str = etYear.getText().toString().trim();
                        int year = 0;
                        try {
                            year = Integer.valueOf(year_str);
                        } catch (Exception e){
                            Toast.makeText(SecondActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DBHelper dbh = new DBHelper(SecondActivity.this);

                        int rating = (int) ratingBar.getRating();
                        dbh.insertItem(title, singers, year, rating);
                        dbh.close();
                        Toast.makeText(SecondActivity.this, "Inserted", Toast.LENGTH_LONG).show();

                        itemList.clear();
                        itemList.addAll(dbh.getAllItems());
                        adapter.notifyDataSetChanged();
                    }
                });
                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }
}
