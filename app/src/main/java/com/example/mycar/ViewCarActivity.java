package com.example.mycar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ViewCarActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQ_CODE = 1;
    public static final int ADD_CAR_RESULT_CODE = 2;
    public static final int EDIT_CAR_RESULT_CODE = 3;



    Toolbar toolbar;
    TextInputEditText et_model, et_color, et_dpl, et_description;
    ImageView imageView;
DatabaseAccess dp;

    private int carID = -1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);

        toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.car_iv);
        et_model = findViewById(R.id.et_details_model);
        et_color = findViewById(R.id.et_details_color);
        et_dpl = findViewById(R.id.et_details_dpl);
        et_description = findViewById(R.id.et_details_description);

        dp=DatabaseAccess.getInstance(this);


        Intent intent = getIntent();
        carID = intent.getIntExtra(MainActivity.CAR_KEY, -1);

        if (carID == -1) {
//add
            enabledFields();
            clearFields();


        } else {
//display
disabledFields();

dp.Open();
Car c=dp.getCar(carID);
dp.Close();
if (c !=null){
fillToFields(c);

} }



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in,PICK_IMAGE_REQ_CODE);
            }
        });

    }

    private void fillToFields(Car car){
        if (car.getImage() !=null && !car.getImage().equals(""));


        imageView.setImageURI(Uri.parse(car.getImage()));
        et_model.setText(car.getModel());
        et_color.setText(car.getColor());
        et_description.setText(car.getDescription());
        et_dpl.setText(car.getDpl()+"");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem save = menu.findItem(R.id.ic_save);
        MenuItem edit = menu.findItem(R.id.ic_edit);
        MenuItem delete = menu.findItem(R.id.ic_delete);


        if (carID == -1) {
//add
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);


        } else {
//display

            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return true;
    }

private  void disabledFields(){
        imageView.setEnabled(false);
        et_model.setEnabled(false);
    et_color.setEnabled(false);
    et_description.setEnabled(false);
    et_dpl.setEnabled(false);
}

    private  void enabledFields(){
        imageView.setEnabled(true);
        et_model.setEnabled(true);
        et_color.setEnabled(true);
        et_description.setEnabled(true);
        et_dpl.setEnabled(true);
    }

    private  void clearFields(){
        imageView.setImageURI(null);
        et_model.setText("");
        et_color.setText("");
        et_description.setText("");
        et_dpl.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
String model, color , desc,image;
double dpl;

        switch (item.getItemId()) {

            case R.id.ic_save:
                model= et_model.getText().toString();
                color= et_color.getText().toString();
                desc= et_description.getText().toString();
                dpl=Double.parseDouble(et_dpl.getText().toString());
image=imageUri.toString();
Car car=new Car(model,color,dpl,image,desc);
dp.Open();
   boolean results=dp.insertCar(car);
dp.Close();
if (results){

    Toast.makeText(this, "Car Added Successfully ", Toast.LENGTH_SHORT).show();

    finish();
}


                return true;
            case R.id.ic_edit:
                return true;

            case R.id.ic_delete:
                return true;

        }
        return false;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE_REQ_CODE && resultCode ==RESULT_OK){

            if (data !=null ){
                imageUri=data.getData();

                imageView.setImageURI(imageUri);

            }


        }
    }
}
