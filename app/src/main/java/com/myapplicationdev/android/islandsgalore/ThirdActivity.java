package com.myapplicationdev.android.islandsgalore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    EditText etID, etTitle, etSingers, etYear;
    Button btnCancel, btnUpdate, btnDelete;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_third));

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etID = (EditText) findViewById(R.id.etID);
        etTitle = (EditText) findViewById(R.id.etName);
        etSingers = (EditText) findViewById(R.id.etDescription);
        etYear = (EditText) findViewById(R.id.etSquareKm);
        ratingBar = findViewById(R.id.ratingbarStars);

        Intent i = getIntent();
        final Item currentItem = (Item) i.getSerializableExtra("song");

        etID.setText(currentItem.getId()+"");
        etTitle.setText(currentItem.getName());
        etSingers.setText(currentItem.getDescription());
        etYear.setText(currentItem.getSquarekm()+"");

        ratingBar.setRating(currentItem.getStars());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentItem.setName(etTitle.getText().toString().trim());
                currentItem.setDescription(etSingers.getText().toString().trim());
                int year = 0;
                try {
                    year = Integer.valueOf(etYear.getText().toString().trim());
                } catch (Exception e){
                    Toast.makeText(ThirdActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentItem.setSquarekm(year);

                //int selectedRB = rg.getCheckedRadioButtonId();
                //RadioButton rb = (RadioButton) findViewById(selectedRB);
                //currentSong.setStars(Integer.parseInt(rb.getText().toString()));
                currentItem.setStars((int) ratingBar.getRating());
                int result = dbh.updateItem(currentItem);
                if (result>0){
                    Toast.makeText(ThirdActivity.this, "Song updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ThirdActivity.this);
                alertBuilder.setTitle("Danger");
                alertBuilder.setMessage("Are you sure you want to delete the island\n" + currentItem.getName());
                alertBuilder.setCancelable(true);
                alertBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper dbh = new DBHelper(ThirdActivity.this);
                        int result = dbh.deleteItem(currentItem.getId());
                        if (result>0){
                            Toast.makeText(ThirdActivity.this, "Song deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertBuilder.setPositiveButton("Cancel", null);
                AlertDialog alertDialog = alertBuilder.create();
                alertBuilder.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ThirdActivity.this);
                alertBuilder.setTitle("Danger");
                alertBuilder.setMessage("Are you sure you want discard the changes\n");
                alertBuilder.setCancelable(true);
                alertBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertBuilder.setPositiveButton("Do NOT Discard", null);
                AlertDialog alertDialog = alertBuilder.create();
                alertBuilder.show();

            }
        });

    }


}