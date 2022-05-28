package com.example.myapplication;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;
public class MainActivity extends AppCompatActivity {
    DatabaseHelper mDb;
    EditText id, RoNum, RoType, Floor, days, availability, Amount;
    Button b1, b2, b3, b4, b5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDb = new DatabaseHelper(this);
        //get the IDs of editext
        id = (EditText) findViewById(R.id.TXTID);
        RoNum = (EditText) findViewById(R.id.TXTRoomNumber);
        RoType = (EditText) findViewById(R.id.TXTRoomType);
        Floor = (EditText) findViewById(R.id.TXTFloorNumber);
        days = (EditText) findViewById(R.id.TXTDays);
        availability = (EditText) findViewById(R.id.TXTAvailability);
        Amount = (EditText) findViewById(R.id.TXTAmount);
        //get the ids of button
        b1 = (Button) findViewById(R.id.AddButton);
        b2 = (Button) findViewById(R.id.SubmitButton);
        b3 = (Button) findViewById(R.id.deleteButton);
        b4 = (Button) findViewById(R.id.ViewAllButton);
        b5 = (Button) findViewById(R.id.ClearButton);
//define user defined methods
        addData();
        updateData();
        deleteData();
        viewData();
        clearData();
    }
public void MainActivity(){
    String RoonNum = RoNum.getText().toString().trim();
    String types = RoType.getText().toString().trim();
    String floors = Floor.getText().toString().trim();
    String day = days.getText().toString().trim();
    if (RoonNum.isEmpty()) {
        RoNum.setError("Room Number is required!");
        RoNum.requestFocus();
        return;
    }
    if (types.isEmpty()) {
        RoType.setError("Room Types is required!");
        RoType.requestFocus();
        return;
    }
    if (floors.isEmpty()) {
        Floor.setError("Floor Number is required!");
        Floor.requestFocus();
        return;
    }
    if (day.isEmpty()) {
        days.setError("Number of Days is required!");
        days.requestFocus();
        return;
    }
}
public void addData() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean
                        insert = mDb.insertData(RoNum.getText().toString(), RoType.getText().toString(), Floor.getText().toString(), days.getText().toString(), availability.getText().toString());

                if (insert == true)
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
 }
        });
    }
    public void updateData() {
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ava, type;
                int day , amount , res;
                ava = availability.getText().toString();
                type = RoType.getText().toString();
                day =Integer.parseInt(days.getText().toString());
                boolean
                        update = mDb.updateData(id.getText().toString(), RoNum.getText().toString(), RoType.getText().toString(), Floor.getText().toString(), days.getText().toString(), "reserved");
                if (ava.equals("reserved")) {
                    Toast.makeText(MainActivity.this, "Sorry, This Room Was reserved!", Toast.LENGTH_LONG).show();
                }

              else  if (update == true){
                    Toast.makeText(MainActivity.this, "Room Reserved Successfully...", Toast.LENGTH_LONG).show();
                    availability.setText("reserved");
                    if (type.equals("Single"))
                    {
                        res = (25) * day;
                        Amount.setText(Integer.toString(res));
                    }
                    if (type.equals("Double"))
                    {
                        res = (30) * day;
                        Amount.setText(Integer.toString(res));
                    }
                    if (type.equals("Triple"))
                    {
                        res = (35) * day;
                        Amount.setText(Integer.toString(res));
                    }
                    if (type.equals("Quad"))
                    {
                        res = (50) * day;
                        Amount.setText(Integer.toString(res));
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Invalid Data of room type!!", Toast.LENGTH_LONG).show();

                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
        });
    } // delete data
    public void deleteData()
    {
        b3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v)
    {
        Integer del=mDb.deleteData(id.getText().toString());
        if(del>0)
            Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
        else
        Toast.makeText(MainActivity.this,"Dat not deleted",Toast.LENGTH_LONG).show();
    } });
    }// view all data
    public void viewData() {
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor r = mDb.getAllData();
                if (r.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer b = new StringBuffer();
                while (r.moveToNext()) {
                    b.append("ID:" + r.getString(0) + "\n");
                    b.append("RoomNumber:" + r.getString(1) + "\n");
                    b.append("RoomType:" + r.getString(2) + "\n");
                    b.append("FloorNumber:" + r.getString(3) + "\n");
                    b.append("NumberOfDays:" + r.getString(4) + "\n");
                    b.append("Availability of Room:" + r.getString(5) + "\n");
                }
                showMessage("Room Details", b.toString());
            }
        });
    } //clear the screen
    public void clearData() {
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText("");
                RoNum.setText("");
                RoType.setText("");
                Floor.setText("");
                days.setText("");
                availability.setText("");
            }
        });
    }
    //status bar
    public void showMessage(String title, String mes) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setCancelable(true);
        ad.setTitle(title);
        ad.setMessage(mes);
        ad.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layoutmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}

