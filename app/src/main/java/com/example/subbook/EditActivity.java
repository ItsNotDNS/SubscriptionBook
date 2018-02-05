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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

/**
 * Activity for editing and creating new subscriptions.
 */
public class EditActivity extends AppCompatActivity {

    private EditText name;
    private EditText date;
    private EditText charge;
    private EditText comment;
    private int position;
    private Button saveSubButton;
    private Subscription sub;

    /**
     * Create and fill fields on creation of activity
     *
     * @param savedInstanceState This instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        name = findViewById(R.id.nameText3);
        date = findViewById(R.id.dateText3);
        charge = findViewById(R.id.chargeText3);
        comment = findViewById(R.id.commentText3);
        saveSubButton = findViewById(R.id.saveSubButton);

        //Get the subscription clicked on
        sub = (Subscription) getIntent().getSerializableExtra("sub");
        position = (int) getIntent().getSerializableExtra("position");
        name.setText(sub.getName());
        date.setText(sub.getDate());
        charge.setText(String.format("%.2f", sub.getCharge()));     //Convert float to string
        comment.setText(sub.getComment());

        listenSaveButton();
    }

    /**
     * Listener for save button.  Returns request of 1 to MainActivity
     */
    private void listenSaveButton() {
        saveSubButton = findViewById(R.id.saveSubButton);
        saveSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = findViewById(R.id.nameText3);
                date = findViewById(R.id.dateText3);
                charge = findViewById(R.id.chargeText3);
                comment = findViewById(R.id.commentText3);

                Subscription updatedSub = new Subscription(name.getText().toString(), date.getText().toString(), Float.parseFloat(charge.getText().toString()), comment.getText().toString());
                Intent newIntent = new Intent();
                newIntent.putExtra("sub", updatedSub);
                newIntent.putExtra("position", position);
                setResult(Activity.RESULT_OK, newIntent);
                finish();
            }
        });
    }
}
