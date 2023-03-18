package com.example.reseed.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reseed.R;
import com.example.reseed.AppActivity;
import com.example.reseed.objects.TaskObj;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final ArrayList <TaskObj> localDataSet;

    public void setClickListener(AppActivity appActivity) {
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText,typeText, descriptionText, dateTaskText;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleText = (TextView) view.findViewById(R.id.titleTaskText);
            typeText = (TextView) view.findViewById(R.id.typeTaskText);
            descriptionText = (TextView) view.findViewById(R.id.descriptionTaskText);
            dateTaskText = (TextView) view.findViewById(R.id.dateTaskText);
        }

        public TextView getTitleText() {
            return titleText;
        }
        public TextView getTypeText() {
            return typeText;
        }
        public TextView getDescriptionText() {
            return descriptionText;
        }
        public TextView getDateTaskText() {
            return dateTaskText;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView
     */
    public TaskAdapter(ArrayList<TaskObj> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.taskitem, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        //set the title text.
        viewHolder.getTitleText().setText(localDataSet.get(position).getName());

        //TODO Make the date formater for date and time.

        viewHolder.getTypeText().setText("Hola");
        viewHolder.getDescriptionText().setText(localDataSet.get(position).getDescription());
        viewHolder.getDateTaskText().setText(Long.toString(localDataSet.get(position).getDateFinal()));



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

