package com.example.weightpet0604;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText wgtEditText;
    private Button btnAdd;
    private RecyclerView recyclerView;
    private WeightAdapter adapter;
    private ArrayList<Weight> weightList;
    private DBHelper dbHelper;

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wgtEditText = findViewById(R.id.wgtEditText);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.rv);
        lineChart = findViewById(R.id.lineChart);

        weightList = new ArrayList<>();
        dbHelper = new DBHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeightAdapter(weightList);
        recyclerView.setAdapter(adapter);

        // Load initial data from the database
        weightList.addAll(dbHelper.getAllWeights());
        adapter.notifyDataSetChanged();

        // Update chart with initial data
        updateChart();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightText = wgtEditText.getText().toString();
                if (!weightText.isEmpty()) {
                    double weight = Double.parseDouble(weightText);
                    String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    addWeightToDatabase(weight, dateTime);
                    weightList.add(0, new Weight(weight, dateTime));
                    adapter.notifyDataSetChanged();
                    wgtEditText.setText("");
                    // RecyclerView를 최신 데이터로 스크롤
                    recyclerView.scrollToPosition(0);
                    // 차트를 업데이트
                    updateChart();
                } else {
                    // Handle empty input case
                }
            }
        });
    }

    private void addWeightToDatabase(double weight, String dateTime) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_WEIGHT, weight);
        values.put(DBHelper.COLUMN_DATETIME, dateTime);
        db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
    }

    private void updateChart() {
        List<Weight> weights = dbHelper.getLast10Weights(); // 최근 10개의 기록만 가져옴
        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < weights.size(); i++) {
            Weight weight = weights.get(weights.size() - 1 - i);
            entries.add(new Entry(i, (float) weight.getWeight()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "몸무게");
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
    }

}