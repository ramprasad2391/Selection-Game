//Group9A_HW01
//Aaron Maisto
//Ram Prasad Narayanaswamy

package com.example.selectiongame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

public class ImageAdapter implements ListAdapter {

	private Context mContext;

	public ImageAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	private Integer[] mThumbIds = { R.drawable.apple, R.drawable.lemon,
			R.drawable.mango, R.drawable.peach, R.drawable.strawberry,
			R.drawable.tomato };

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 25;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;

	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		Random random = new Random();
		int random_fruit_no = ((Integer) parent.getTag(R.id.focus_fruit_no)).intValue();
		int pos[] = new int[25];
		HashSet<Integer> focus_pos = (HashSet<Integer>) parent
				.getTag(R.id.random_positions);

		ArrayList<Integer> tr = new ArrayList<Integer>();
		tr.add(0);
		tr.add(1);
		tr.add(2);
		tr.add(3);
		tr.add(4);
		tr.add(5);
		tr.remove(random_fruit_no);

		if (focus_pos.contains(position)) {
			pos[position] = random_fruit_no;
		} else {
			pos[position] = (int) tr.get(random.nextInt(5));
		}

		Integer i = pos[position];
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
			imageView.setTag(R.id.fruit_no, i);

		} else {
			imageView = (ImageView) convertView;
		}
		// Log.d("Demo",
		// mContext.getResources().getResourceName(R.id.gridView1)+"");
		imageView.setImageResource(mThumbIds[pos[position]]);
		return imageView;

	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return true;
	}

}
