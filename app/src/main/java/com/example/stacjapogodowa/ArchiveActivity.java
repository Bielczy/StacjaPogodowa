package com.example.stacjapogodowa;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class ArchiveActivity extends AppCompatActivity {

    Button btnInsert, btnClear;
    LineChart lineChartArchive;
    EditText xValue, yValue, dataSetLabel;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    long maxid = 0;
   // String userID;
  Realm mRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        mRealm = Realm.getDefaultInstance();

        lineChartArchive = findViewById(R.id.lineChartArchive);
        dataSetLabel = findViewById(R.id.etDataSetLabel);
        btnClear = findViewById(R.id.btnClear);
        btnInsert = findViewById(R.id.btnInsert);
        xValue = findViewById(R.id.etAxisX);
        yValue = findViewById(R.id.etAxisY);

        String chartLabel = dataSetLabel.getText().toString();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(chartLabel);
       // FirebaseUser user = firebaseAuth.getCurrentUser();
       // userID = user.getUid();


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.setValue(null);
                btnClear.setEnabled(false);
            }
        });
//

        insertData();
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.RED);
    }

    private void insertData() {

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxid++;
              // String id = databaseReference.push().getKey();
                String chartLabel = dataSetLabel.getText().toString();
                float x = Float.parseFloat(xValue.getText().toString());
                float y = Float.parseFloat(yValue.getText().toString());

                DataPoint dataPoint = new DataPoint(x, y);
                databaseReference.child(chartLabel + maxid).setValue(dataPoint);
                settingLabel();
                retriveData();

            }
        });
    }



    private void settingLabel() {
        if(dataSetLabel.getText().toString().isEmpty()){
            dataSetLabel.setEnabled(true);
        }else {
            dataSetLabel.setEnabled(false);
        }
    }

    private void retriveData() {
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> lineDataVals = new ArrayList<Entry>();

                if (dataSnapshot.hasChildren()){
                    for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                       DataPoint  dataPoint = myDataSnapshot.getValue(DataPoint.class);

                          lineDataVals.add(new Entry(dataPoint.getxValue(), dataPoint.getyValue()));
                    }
                    showLineChart(lineDataVals);
                }
                else {
                    lineChartArchive.clear();
                    lineChartArchive.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showLineChart(ArrayList<Entry> lineDataVals) {

        lineDataSet.setValues(lineDataVals);
        lineDataSet.setLabel(dataSetLabel.getText().toString());
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChartArchive.clear();
        lineChartArchive.setData(lineData);
        lineChartArchive.invalidate();

        Description description = lineChartArchive.getDescription();
        description.setEnabled(false);

        Legend legend = lineChartArchive.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(12f);
        legend.setTextColor(Color.RED);

    }
}
