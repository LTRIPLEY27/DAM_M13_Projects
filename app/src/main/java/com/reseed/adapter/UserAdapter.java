package com.reseed.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.reseed.R;
import com.reseed.interfaces.RecyclerViewInterface;
import com.reseed.objects.TaskObj;
import com.reseed.objects.UserObj;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

	private final RecyclerViewInterface recyclerViewInterface;
	private final ArrayList<UserObj> localDataSet;

	/**
	 * Provide a reference to the type of views that you are using
	 * (custom ViewHolder)
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder{
		private final TextView nameText, emailText;
		private final CardView cardViewUser;


		public ViewHolder(@NonNull View view, RecyclerViewInterface recyclerViewInterface) {
			super(view);

			nameText = view.findViewById(R.id.user_name_text);
			emailText = view.findViewById(R.id.user_email_text);
			cardViewUser = view.findViewById(R.id.card_user);

			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if(recyclerViewInterface != null){

						int pos = getAbsoluteAdapterPosition();

						if(pos != RecyclerView.NO_POSITION){
							recyclerViewInterface.onItemClicked(pos);
						}
					}
				}
			});
		}

		public TextView getNameText() {
			return nameText;
		}

		public TextView getEmailText() {
			return emailText;
		}
		public CardView getCardViewTask() {
			return cardViewUser;
		}

	}

	/**
	 * Initialize the dataset of the Adapter
	 *
	 * @param dataSet String[] containing the data to populate views to be used
	 *                by RecyclerView
	 */
	public UserAdapter(ArrayList<UserObj> dataSet, RecyclerViewInterface recyclerViewInterface) {
		localDataSet = dataSet;
		this.recyclerViewInterface = recyclerViewInterface;

	}

	// Create new views (invoked by the layout manager)
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		// Create a new view, which defines the UI of the list item
		View view = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.user_item, viewGroup, false);

		return new ViewHolder(view, recyclerViewInterface);
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, final int position) {

		// Get element from your dataset at this position and replace the
		// contents of the view with that element

		//set the name text.
		viewHolder.getNameText().setText(localDataSet.get(position).getNombre()
				.concat(" ")
				.concat(localDataSet.get(position).getNombre()));

		viewHolder.getEmailText().setText(localDataSet.get(position).getEmail());

		// Cambio el color de la targeta segun si es usuario o admin.

		if(localDataSet.get(position).getTipoUsuario().contentEquals("ADMIN")){
			viewHolder.getCardViewTask().setCardBackgroundColor((Color.parseColor("#ffcccc")));
		}else{
			viewHolder.getCardViewTask().setCardBackgroundColor((Color.parseColor("#99ccff")));
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

