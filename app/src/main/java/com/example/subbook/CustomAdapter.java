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
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by david on 03/02/18.
 * https://www.youtube.com/watch?v=nOdSARCVYic&t=1s
 *
 * This adapter allows for interaction between the arraylist of subscriptions and the listview
 * in the main activity
 */
class CustomAdapter extends ArrayAdapter<Subscription> {
    ArrayList<Subscription> subList = new ArrayList<>();
    private Context mcon;

    /**
     * Constructor
     *
     * @param context Context of the listview item
     * @param ID position of the listview item
     * @param subs subscription of the listview item
     */
    public CustomAdapter(Context context, int ID, ArrayList<Subscription> subs) {
        super(context, ID, subs);
        subList = subs;
        mcon = context;
    }

    /**
     * Sets up the view of the list item.  Fields are linked and filled
     *
     * @param position position in listview
     * @param convertView
     * @param parent the parent viewgroup
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.list_item, parent, false);

        TextView name = customView.findViewById(R.id.nameText);
        TextView date = customView.findViewById(R.id.dateText);
        TextView charge = customView.findViewById(R.id.chargeText);
        Button editSubButton = customView.findViewById(R.id.editSubButton);
        Button deleteSubButton = customView.findViewById(R.id.deleteSubButton);
        //TextView comment = customView.findViewById(R.id.commentText);

        name.setText(subList.get(position).getName());
        date.setText(subList.get(position).getDate());
        charge.setText(String.format("%.2f", subList.get(position).getCharge()));
        //comment.setText(subList.get(position).getComment());

        /**
         * Action for the delete button, removes item and updates list
         */
        deleteSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete object
                subList.remove(position);
                notifyDataSetChanged();
            }
        });

        /**
         * Action for the edit button, calls the EditActivity, sends for a result, result returns
         * to the mainactivity where it updates the list item there
         */
        editSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Edit activity
                Intent newIntent = new Intent(mcon, EditActivity.class);
                newIntent.putExtra("sub", subList.get(position));
                newIntent.putExtra("position", position);
                ((Activity) view.getContext()).startActivityForResult(newIntent, 1);
            }
        });

        return customView;
    }
}
