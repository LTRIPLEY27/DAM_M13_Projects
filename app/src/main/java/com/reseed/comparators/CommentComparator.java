package com.reseed.comparators;

import com.reseed.objects.TaskComment;

import java.util.Comparator;

// creates the comparator for comparing stock value
public class CommentComparator implements Comparator<TaskComment> {

	// override the compare() method

	@Override
	public int compare(TaskComment o1, TaskComment o2) {

		return Integer.compare(o1.getId(), o2.getId());
	}
}