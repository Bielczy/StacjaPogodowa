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
import java.util.List;

public class RetriveActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
   // EditText dataSetRetriveLabel;
    Button retriveFromFirebase;
    TextView retriveLabel;
    LineChart lineChartRetrive;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive);



        retriveLabel = findViewById(R.id.tvRetriveLabel);
        retriveFromFirebase = findViewById(R.id.btnRetriveFromFirebase);
        // EditText dataSetRetriveLabel = findViewById(R.id.etDataSetRetriveLabel);
        lineChartRetrive = findViewById(R.id.lineChartRetrive);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ChartValues");

        retriveFromFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Entry> lineDataVals = new ArrayList<Entry>();
                       // List<String> keys = new ArrayList<>();
                        if (dataSnapshot.hasChildren()){
                            for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                               // keys.add(myDataSnapshot.getKey());

                                DataFetch dataFetch = myDataSnapshot.getValue(DataFetch.class);

                                lineDataVals.add(new Entry(dataFetch.getAxisX(), dataFetch.getAxisY()));
                            }
                            showLineChart(lineDataVals);
                        }
                        else {
                            lineChartRetrive.clear();
                            lineChartRetrive.invalidate();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.RED);
    }

    private void showLineChart(ArrayList<Entry> lineDataVals) {
        lineDataSet.setValues(lineDataVals);
       // lineDataSet.setLabel(dataSetRetriveLabel.getText().toString());
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChartRetrive.clear();
        lineChartRetrive.setData(lineData);
        lineChartRetrive.invalidate();

        Description description = lineChartRetrive.getDescription();
        //description.setText(dataSetLabel.getText().toString());
        //description.setTextSize(12f);
        description.setEnabled(false);

        Legend legend = lineChartRetrive.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(12f);
        legend.setTextColor(Color.RED);
    }
}
