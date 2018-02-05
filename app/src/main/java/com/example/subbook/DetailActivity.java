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

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity for showing the full subscription details
 */
public class DetailActivity extends AppCompatActivity {

    private TextView name;
    private TextView date;
    private TextView charge;
    private TextView comment;
    private int position;
    private Subscription sub;

    /**
     * Populates and fills the fields
     *
     * @param savedInstanceState this instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.nameText2);
        date = findViewById(R.id.dateText2);
        charge = findViewById(R.id.chargeText2);
        comment = findViewById(R.id.commentText2);

        //Get the individual subscription and position
        sub = (Subscription) getIntent().getSerializableExtra("sub");
        position = (Integer) getIntent().getSerializableExtra("position");

        name.setText(sub.getName());
        date.setText(sub.getDate());
        charge.setText(String.format("%.2f", sub.getCharge()));     //Convert float to string
        comment.setText(sub.getComment());
    }
}
