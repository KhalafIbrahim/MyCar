package com.example.mycar;

import android.content.ContentValues;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;



public class DatabaseAccess {




    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static  DatabaseAccess instance;

    private DatabaseAccess  (Context context) {
        this.openHelper= new Mydatabase(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null){

            instance = new DatabaseAccess(context);

        }
        return instance;
    }
    public void Open(){
        this.database=this.openHelper.getWritableDatabase();
    }

    public void Close(){
        if (this.database != null)
        {
            this.database.close();
        }

    }








    public boolean  insertCar(Car car){
        ContentValues values=new ContentValues();

        values.put(Mydatabase.CAR_CLN_MODEL,car.getModel());
        values.put(Mydatabase.CAR_CLN_COLOR,car.getColor());
        values.put(Mydatabase.CAR_CLN_DPL,car.getDpl());
        values.put(Mydatabase.CAR_CLN_IMAGE,car.getImage());
        values.put(Mydatabase.CAR_CLN_DESC,car.getDescription());

        long result =database.insert(Mydatabase.CAR_TB_NAME,null,values);
        return result!=-1;
    }


    public boolean  updateCar(Car car){



        ContentValues values=new ContentValues();


        values.put(Mydatabase.CAR_CLN_MODEL,car.getModel());
        values.put(Mydatabase.CAR_CLN_COLOR,car.getColor());
        values.put(Mydatabase.CAR_CLN_DPL,car.getDpl());
        values.put(Mydatabase.CAR_CLN_IMAGE,car.getImage());
        values.put(Mydatabase.CAR_CLN_DESC,car.getDescription());


        String args[]=new String[] {String.valueOf(car.getId())};

        long result=  database.update(Mydatabase.CAR_TB_NAME,values,"id=?",args);
        return result > 0;


    }

    public long getCarsCount(){

        return DatabaseUtils.queryNumEntries(database, Mydatabase.CAR_TB_NAME);

    }

    public boolean  deleteCar(Car car){
         String args[]=new String[] {String.valueOf(car.getId())};

        int result=  database.delete(Mydatabase.CAR_TB_NAME,"id=?",args);
        return result > 0;


    }

    public ArrayList<Car> getAllCars(){

        ArrayList<Car> cars=new ArrayList<>();

         Cursor cursor=database.rawQuery(" SELECT * FROM " + Mydatabase.CAR_TB_NAME,null);

        if (cursor != null && cursor.moveToFirst()){

            do {

                int id = cursor.getInt(cursor.getColumnIndex(Mydatabase.CAR_CLN_ID));
                String model = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_MODEL));
                String color = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_COLOR));
                String image = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_IMAGE));
                String description = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_DESC));

                double dpl = cursor.getDouble(cursor.getColumnIndex(Mydatabase.CAR_CLN_DPL));

                Car c=new Car(id,model,color,dpl,image,description);
                cars.add(c);

            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return  cars;



    }



    public ArrayList<Car> getCars(String modelSearch){

        ArrayList<Car> cars=new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM "+Mydatabase.CAR_TB_NAME +" WHERE " +Mydatabase.CAR_CLN_MODEL +" LIKE ?",
                        new String[]{modelSearch+"%"});

        if (cursor != null && cursor.moveToFirst()){

            do {
                int id = cursor.getInt(cursor.getColumnIndex(Mydatabase.CAR_CLN_ID));
                String model = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_MODEL));
                String color = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_COLOR));
                String image = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_IMAGE));
                String description = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_DESC));
                double dpl = cursor.getDouble(cursor.getColumnIndex(Mydatabase.CAR_CLN_DPL));

                Car c=new Car(id,model,color,dpl,image,description);
                cars.add(c);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return  cars;



    }



    public Car getCar(int carId){
        Cursor cursor=
                database.rawQuery("SELECT * FROM " + Mydatabase.
                        CAR_TB_NAME +" WHERE "+Mydatabase.CAR_CLN_ID+"=?",new String[]{String.valueOf(carId)});
        if (cursor != null && cursor.moveToFirst()){

                int id = cursor.getInt(cursor.getColumnIndex(Mydatabase.CAR_CLN_ID));
                String model = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_MODEL));
                String color = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_COLOR));
                 String image = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_IMAGE));
                String description = cursor.getString(cursor.getColumnIndex(Mydatabase.CAR_CLN_DESC));
                double dpl = cursor.getDouble(cursor.getColumnIndex(Mydatabase.CAR_CLN_DPL));

                Car c=new Car(id,model,color,dpl,image,description);


             cursor.close();
             return c;
        }
        return   null;



    }




}
