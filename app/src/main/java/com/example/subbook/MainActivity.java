/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *     David Laycock - 2018
 */

package com.example.subbook;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Main Activity for the Subscription List
 */
public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView subListView;
    private ArrayList<Subscription> subArrayList  = new ArrayList<>();
    private CustomAdapter adapter;
    private TextView totalCharge;

    /**
     * Called when activity is first created
     *
     * @param savedInstanceState The state of the program
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromFile();

        subListView = (ListView) findViewById(R.id.oldSubList);
        Button addButton = findViewById(R.id.addButton);
        totalCharge = findViewById(R.id.totalCharge);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubscription();
            }
        });
        getTotal();
    }

    /**
     * Adds a new subscription to the list
     */
    private void addSubscription() {
        Intent newIntent = new Intent(this, EditActivity.class);
        Subscription sub = new Subscription("", "", 0, "");
        newIntent.putExtra("sub", sub);
        newIntent.putExtra("position", 0);
        startActivityForResult(newIntent, 2);
    }

    /**
     * Called at the start of the main activity
     */
    @Override
    protected void onStart() {
        super.onStart();

        adapter = new CustomAdapter(this, R.layout.list_item, subArrayList);
        subListView.setAdapter(adapter);

        //https://www.youtube.com/watch?v=ZEEYYvVwJGY
        //Detail Activity
        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent newIntent = new Intent(MainActivity.this, DetailActivity.class);
                Subscription thisSub = subArrayList.get(i);
                newIntent.putExtra("sub", thisSub);
                newIntent.putExtra("position", i);
                startActivity(newIntent);
                getTotal();
            }
        });
    }

    /**
     * Invokes behaviour after interactive methods are called.
     *
     * @param requestCode  1 for edits, 2 for additions
     * @param resultCode status of method return
     * @param data data passed back to this activity namely subscription and position
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
            Subscription sub = (Subscription)data.getSerializableExtra("sub");
            int position = data.getIntExtra("position", 0);
            subArrayList.set(position, sub);
            adapter.notifyDataSetChanged();
        }
        if (requestCode == 2) {
            Subscription sub = (Subscription)data.getSerializableExtra("sub");
            subArrayList.add(sub);
            adapter.notifyDataSetChanged();
        }
        saveInFile();
        getTotal();
    }

    /**
     * Updates the total charges field
     */
    public void getTotal() {
        float total = 0f;
        for (int i = 0; i < subArrayList.size(); i++) {
            total = total + subArrayList.get(i).getCharge();
        }
        totalCharge.setText("Total: " + String.format("%.2f", total));
    }

    /**
     * Loads the saved file
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subArrayList = gson.fromJson(in, new TypeToken<ArrayList<Subscription>>(){}.getType());

        } catch (FileNotFoundException e) {
            subArrayList = new ArrayList<Subscription>();
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Stores the current arraylist into a file
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(subArrayList, out);
            out.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void onStop() {
        saveInFile();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
