package com.reseed.util.adapter;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.reseed.R;
import com.reseed.AppActivity;
import com.reseed.objects.TaskObj;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

	private final ArrayList<TaskObj> localDataSet;

	public void setClickListener(AppActivity appActivity) {
	}

	/**
	 * Provide a reference to the type of views that you are using
	 * (custom ViewHolder)
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final TextView titleText, descriptionText, dateTaskText;
		private CardView cardViewTask;
		private ImageView imageViewTask;


		public ViewHolder(View view) {
			super(view);
			// Define click listener for the ViewHolder's View

			titleText = view.findViewById(R.id.titleTaskText);
			descriptionText = view.findViewById(R.id.descriptionTaskText);
			dateTaskText = view.findViewById(R.id.dateTaskText);
			cardViewTask = view.findViewById(R.id.card_task);
			imageViewTask = view.findViewById(R.id.imageTaskType);

		}

		public TextView getTitleText() {
			return titleText;
		}

		public TextView getDescriptionText() {
			return descriptionText;
		}

		public TextView getDateTaskText() {
			return dateTaskText;
		}

		public CardView getCardViewTask() {
			return cardViewTask;
		}

		public ImageView getImageTask() {
			return imageViewTask;
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
		viewHolder.getTitleText().setText(localDataSet.get(position).getTarea());

		//TODO Make the date formater for date and time.

		viewHolder.getDescriptionText().setText(localDataSet.get(position).getName());
		viewHolder.getDateTaskText().setText(localDataSet.get(position).getFecha_culminacion());
		if (localDataSet.get(position).getTarea().contentEquals("ANALISIS")) {
			viewHolder.getCardViewTask().setBackgroundColor(Color.parseColor("#acceff"));
		}
		if (localDataSet.get(position).getTarea().contentEquals("LIMPIEZA")) {
			viewHolder.getCardViewTask().setBackgroundColor(Color.parseColor("#fff4ac"));
		}

	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		if (localDataSet.isEmpty()) {
			return 0;
		} else {
			return localDataSet.size();
		}

	}
}

