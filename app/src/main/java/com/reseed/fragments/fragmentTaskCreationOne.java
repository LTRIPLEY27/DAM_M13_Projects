package com.reseed.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reseed.R;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentTaskCreationOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentTaskCreationOne extends Fragment {

	JSONObject jsonTask;

	public fragmentTaskCreationOne() {
		// Required empty public constructor
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			//mParam1 = getArguments().getString(ARG_PARAM1);
			//mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_task_creation_one, container, false);
	}
}