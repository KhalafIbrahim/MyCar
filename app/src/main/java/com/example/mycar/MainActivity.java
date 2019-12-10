package com.example.mycar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_CAR_REQ_CODE =1 ;
    private static final int EDIT_CAR_REQ_CODE =1 ;
    public static final String CAR_KEY ="car key";

    private FloatingActionButton fab;
    private Toolbar toolbar;
    RecyclerView rv;

    RecyclerViewAdapter adapter;
    DatabaseAccess dp;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






         funRecycler();


        fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), ViewCarActivity.class);
                startActivityForResult(intent,ADD_CAR_REQ_CODE);
            }
        });



    }

    private void funRecycler() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        rv = findViewById(R.id.main_recycler);
        dp = DatabaseAccess.getInstance(this);

        dp.Open();
        ArrayList<Car> cars = dp.getAllCars();
        dp.Close();


        adapter= new RecyclerViewAdapter(cars, new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int carId) {
                Intent i=new Intent(getBaseContext(),ViewCarActivity.class);
                i.putExtra(CAR_KEY,carId);
                startActivityForResult(i, EDIT_CAR_REQ_CODE);

            }
        });
        rv.setAdapter(adapter);
        layoutManager  = new GridLayoutManager(this, 2);

        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(MainActivity.this, "Submit clicked ", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, "Text Changed", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(MainActivity.this, "Search finished", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== ADD_CAR_REQ_CODE && resultCode==ViewCarActivity.ADD_CAR_RESULT_CODE){

            dp.Open();
            ArrayList<Car> cars = dp.getAllCars();

            dp.Close();

adapter.setCarlist(cars);
adapter.notifyDataSetChanged();
        }
    }
}
