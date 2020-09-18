package com.project.rnbgopartner;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.ipaulpro.afilechooser.utils.FileUtils;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoomRegisterActivity extends AppCompatActivity {


    private static final int CROP_PIC =2 ;


    private Double latitude;
    private Double longitute;
    Location gps_loc=null,network_loc=null,final_loc=null;
    private EditText et_room_onwer_name;
    private EditText et_room_onwer_contact_number;

    //Edittex for room address?

    private EditText et_room_address;

    //checkboxes for what is available in room?

    private CheckBox cb_room_facility_bed;
    private CheckBox cb_room_facility_fan;
    private CheckBox cb_room_facility_refrigerator;
    private CheckBox cb_room_facility_ac;
    private CheckBox cb_room_facility_table;
    private CheckBox cb_room_facility_tv;
    private CheckBox cb_room_facility_washing_machine;
    private CheckBox cb_room_facility_chair;

    //checkbox for how many people can adjust?

    private CheckBox cb_person_count_one;
    private CheckBox cb_person_count_two;
    private CheckBox cb_person_count_three;
    private CheckBox cb_person_count_four;
    private CheckBox cb_person_count_more;

    //checkbox for Is room/flat condition?

    private Spinner spinner_room_condition;


    //checkbox for Who can Stay?

    private CheckBox gender_type_boys;
    private CheckBox gender_type_girls;
    private CheckBox gender_type_family;

    //Checkbox for How many rooms?

    private CheckBox room_type_one_room;
    private CheckBox room_type_two_room;
    private CheckBox room_type_three_room;
    private CheckBox room_type_one_bhk;
    private CheckBox room_type_two_bhk;
    private CheckBox room_type_three_bhk;


    //Edit text for Room Rent.
    private EditText et_room_rent;


    //Switch for eletric bill

    private Switch sw_electric_bill;

    //Switch for water bill
    private Switch sw_water_bill;

    //Switch for ready to checkin

    private Switch sw_ready_checkin;

    //Edittext for Extra charge
    private EditText et_extra_charge;

    //Edittext for description
    private EditText et_description;


    private Button btn_room_register;


    private String room_owner_name,room_owner_contact_number,room_address,room_coordinates,room_person_count,room_facility,room_condition,gender_type,room_type,room_rent,extra_charge,electric_bill,water_bill,ready_checkin,description;
    private String partner_uid;
    private String address,city,state,country,postal_code,area;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_register);




        et_room_onwer_name=(EditText)findViewById(R.id.room_owner_name);
        et_room_onwer_contact_number=(EditText)findViewById(R.id.room_owner_contact_number);
        et_room_address=(EditText)findViewById(R.id.room_address);



        cb_room_facility_bed=(CheckBox)findViewById(R.id.cb_facility_bed);
        cb_room_facility_fan=(CheckBox)findViewById(R.id.cb_facility_fan);
        cb_room_facility_refrigerator=(CheckBox)findViewById(R.id.cb_facility_refrigerator);
        cb_room_facility_ac=(CheckBox)findViewById(R.id.cb_facility_ac);
        cb_room_facility_table=(CheckBox)findViewById(R.id.cb_facility_table);
        cb_room_facility_tv=(CheckBox)findViewById(R.id.cb_facility_tv);
        cb_room_facility_chair=(CheckBox)findViewById(R.id.cb_facility_chair);
        cb_room_facility_washing_machine=(CheckBox)findViewById(R.id.cb_facility_washing_maching);

        cb_person_count_one=(CheckBox)findViewById(R.id.cbone_people);
        cb_person_count_two=(CheckBox)findViewById(R.id.cbtwo_people);
        cb_person_count_three=(CheckBox)findViewById(R.id.cbthree_people);
        cb_person_count_four=(CheckBox)findViewById(R.id.cbfour_people);
        cb_person_count_more=(CheckBox)findViewById(R.id.cbmore_people);

        //spinner for room condition drop down

        spinner_room_condition = findViewById(R.id.spinner_room_condition);

        gender_type_boys=(CheckBox)findViewById(R.id.cbboys);
        gender_type_girls=(CheckBox)findViewById(R.id.cbgirls);
        gender_type_family=(CheckBox)findViewById(R.id.cbfamily);

        room_type_one_room=(CheckBox)findViewById(R.id.one_room);
        room_type_two_room=(CheckBox)findViewById(R.id.two_room);
        room_type_three_room=(CheckBox)findViewById(R.id.three_room);
        room_type_one_bhk=(CheckBox)findViewById(R.id.one_bhk);
        room_type_two_bhk=(CheckBox)findViewById(R.id.two_bhk);
        room_type_three_bhk=(CheckBox)findViewById(R.id.three_bhk);

        et_room_rent=(EditText)findViewById(R.id.et_room_rent);
        et_extra_charge=(EditText)findViewById(R.id.et_extra_charge);
        sw_electric_bill=(Switch)findViewById(R.id.sw_electric_bill);
        sw_water_bill=(Switch)findViewById(R.id.sw_water_bill);
        sw_ready_checkin=(Switch)findViewById(R.id.room_checkin);










        et_description=(EditText)findViewById(R.id.et_description);
        btn_room_register=(Button)findViewById(R.id.btn_register_room);





        btn_room_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_room();

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        String[] items = new String[]{"Well Furnished", "Furnished(Atleast BED and Fan)", "Not Furnished"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        spinner_room_condition.setAdapter(adapter);


        LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED&&
                        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE)!=PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, " Permissions Not Granted", Toast.LENGTH_SHORT).show();
        }
        else {

        }


        try{
            gps_loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(gps_loc!=null)
        {
            final_loc=gps_loc;
            latitude=final_loc.getLatitude();
            longitute=final_loc.getLongitude();
        }
        else if(network_loc !=null)
        {
            final_loc=network_loc;
            latitude=final_loc.getLatitude();
            longitute=final_loc.getLongitude();
        }
        else
        {
            latitude=0.0;
            longitute=0.0;
        }

        try{
            Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addressList=geocoder.getFromLocation(latitude,longitute,1);
            if(addressList!=null && addressList.size()>0)
            {
                address=addressList.get(0).getAddressLine(0);
                city=addressList.get(0).getLocality();
                state=addressList.get(0).getAdminArea();
                country=addressList.get(0).getCountryName();
                postal_code=addressList.get(0).getPostalCode();
                area=addressList.get(0).getSubLocality();

                room_coordinates=latitude+","+longitute;
                String known_name=addressList.get(0).getFeatureName();


                et_room_address.setText(address);

            }
            else
            {
                et_room_address.setText("Permission denied!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    private void register_room() {




        room_owner_name=et_room_onwer_name.getText().toString();
        room_owner_contact_number=et_room_onwer_contact_number.getText().toString();
        room_address=et_room_address.getText().toString();
        room_facility=get_string_room_facility();
        room_person_count=get_person_count_string();
        room_condition=spinner_room_condition.getSelectedItem().toString();
        gender_type=get_gender_type_string();
        room_type=get_room_type_string();
        room_rent=et_room_rent.getText().toString();
        extra_charge=et_extra_charge.getText().toString();
        electric_bill=get_electric_bill_string();
        water_bill=get_water_bill_string();
        ready_checkin=get_checkin_status_string();
        description=et_description.getText().toString();
        partner_uid= FirebaseAuth.getInstance().getCurrentUser().getUid();




        //upload_room_images();











        Call<ResponseBody> call=RetrofitClient.getInstance().getApi().register_room(room_owner_name,room_owner_contact_number,room_address,area,room_coordinates,room_facility,room_person_count,room_condition,gender_type,room_type,room_rent,extra_charge,electric_bill,water_bill,ready_checkin,description,partner_uid);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {



                try {
                    Log.i("register_room",response.body().string());
                    startActivity(new Intent(RoomRegisterActivity.this,RegisterSuccessActivity.class));
                    finish();

                }
                catch (IOException e)
                {
                    Toast.makeText(RoomRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }




            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(RoomRegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }






    private String get_checkin_status_string() {
        String checkin_status;
        if(sw_ready_checkin.isChecked())
        {
            checkin_status="Yes";
        }
        else
        {
            checkin_status="No";
        }
        return checkin_status;
    }

    private String get_person_count_string()
    {
        String person_count="";
        if(cb_person_count_one.isChecked())
        {
            person_count="1";
        }
        else if(cb_person_count_two.isChecked())
        {
            person_count="2";
        }
        else if(cb_person_count_three.isChecked())
        {
            person_count="3";
        }
        else if(cb_person_count_four.isChecked())
        {
            person_count="4";
        }
        else if(cb_person_count_more.isChecked())
        {
            person_count="more";
        }

        return person_count;
    }
    private String get_electric_bill_string()
    {
        String melectric_bill;
        if(sw_electric_bill.isChecked())
        {
            melectric_bill="Yes";
        }
        else
        {
            melectric_bill="No";
        }

        return melectric_bill;
    }

    private String get_water_bill_string()
    {
        String mWater_bill;
        if(sw_water_bill.isChecked())
        {
            mWater_bill="Yes";
        }
        else
        {
            mWater_bill="No";
        }
        return mWater_bill;

    }
    private String get_room_type_string()
    {
        String mroom_type="";
        if(room_type_one_room.isChecked())
        {
            mroom_type="1 Room";
        }
        else if(room_type_two_room.isChecked())
        {
            mroom_type="2 Room";
        }
        else if(room_type_three_room.isChecked())
        {
            mroom_type="3 Room";
        }
        else if(room_type_one_bhk.isChecked())
        {
            mroom_type="1 BHK";
        }
        else if(room_type_two_bhk.isChecked())
        {
            mroom_type="2 BHK";
        }
        else if(room_type_three_bhk.isChecked())
        {
            mroom_type="3 BHK";
        }

        return mroom_type;

    }
    private String get_gender_type_string()
    {
        String string_gender_type;
        String boys="",girls="",family="";
        if(gender_type_boys.isChecked())
        {
            boys="boys,";
        }
        if(gender_type_girls.isChecked())
        {
            girls="girls";
        }
        if(gender_type_family.isChecked())
        {
            family="family;";
        }
        string_gender_type=boys+girls+family;

        return string_gender_type;
    }


    private String get_string_room_facility() {

        String string_room_facility="";
        String bed="",fan="",refrigerator="",ac="",table="",tv="",washing_machine="",chair="";

        if(cb_room_facility_bed.isChecked())
        {



            bed="Bed,";

        }
        if(cb_room_facility_fan.isChecked())
        {

            fan="Fan,";

        }
        if(cb_room_facility_refrigerator.isChecked())
        {

            refrigerator="Refrigerator,";

        }
        if(cb_room_facility_ac.isChecked())
        {

            ac="A.C.,";
        }
        if(cb_room_facility_table.isChecked())
        {

            table="Table,";
        }
        if(cb_room_facility_tv.isChecked())
        {

            tv="Tv,";

        }
        if(cb_room_facility_washing_machine.isChecked())
        {

            washing_machine="Washing Machine,";
        }
        if(cb_room_facility_chair.isChecked())
        {

            chair="Chair,";
        }

        string_room_facility=bed+fan+refrigerator+ac+table+tv+washing_machine+chair;
        return string_room_facility;

    }

}
