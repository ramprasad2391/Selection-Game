//Group9A_HW01
//Aaron Maisto
//Ram Prasad Narayanaswamy

package com.example.selectiongame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	final Integer[] fruit_ids = { R.string.apple, R.string.lemon,
			R.string.mango, R.string.peach, R.string.strawberry,
			R.string.tomato };
	final Integer[] mThumbIds = { R.drawable.apple, R.drawable.lemon,
			R.drawable.mango, R.drawable.peach, R.drawable.strawberry,
			R.drawable.tomato };

	final public void shuffling(AdapterView<?> parent, HashSet<Integer> hs,
			int position) {
		ArrayList<Integer> fruit_arr1 = new ArrayList<Integer>();

		for (int i = 0; i < 25; i++) {
			if (!hs.contains(i)) {
				fruit_arr1.add((Integer) ((ImageView) parent.getChildAt(i))
						.getTag(R.id.fruit_no));
			}
		}
		Collections.shuffle(fruit_arr1);
		int c = 0;
		for (int i = 0; i < 25; i++) {
			if (!hs.contains(i)) {
				int new_fruit_no = fruit_arr1.get(c);
				c++;
				((ImageView) parent.getChildAt(i))
						.setImageResource(mThumbIds[new_fruit_no]);
				((ImageView) parent.getChildAt(i)).setTag(R.id.fruit_no,
						new_fruit_no);
			}
		}
	}

	public void game_over() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Game Over")
				.setMessage("The current game has ended.")
				.setCancelable(false)
				.setPositiveButton("Reset",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								onCreate(null);
							}
						})
				.setNegativeButton("Exit",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								android.os.Process
										.killProcess(android.os.Process.myPid());
							}
						}).show();
	}

	public GridView init_game(Context mcontext) {
		Random rn = new Random();
		final int random_fruit_no = rn.nextInt(6);
		final int random_fruit_id = fruit_ids[random_fruit_no];
		final int random_count = rn.nextInt(8) + 1;
		HashSet<Integer> foc_pos = new HashSet<Integer>();
		for (int i = 0; i < random_count;) {
			if (foc_pos.add(rn.nextInt(25))) {
				i++;
			}
		}
		TextView fruits = (TextView) findViewById(R.id.textView_Fruit);
		fruits.setText(random_fruit_id);
		fruits.append("    (" + random_count + ")");

		GridView gridview = (GridView) findViewById(R.id.gridView1);
		if (gridview.getChildCount() != 0) {
			gridview.removeViews(0, 25);
		}
		gridview.setTag(R.id.focus_fruit_no, random_fruit_no);
		gridview.setTag(R.id.focus_fruit_count, random_count);
		gridview.setTag(R.id.cnt, random_count);
		gridview.setTag(R.id.random_positions, foc_pos);
		gridview.setAdapter(new ImageAdapter(mcontext));
		return gridview;
	}

	public void targetClicked(AdapterView<?> parent, int targetCount,
			int random_fruit_id, int random_fruit_no, Context mcontext) {
		final GridView grid = (GridView) parent;
		TextView fruits = (TextView) findViewById(R.id.textView_Fruit);
		fruits.setText(random_fruit_id);
		fruits.append("    (" + targetCount + ")");
		if (targetCount == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Found all shapes")
					.setMessage(
							"Congratulations!!  You have found all the "+ mcontext.getResources().getString(random_fruit_id))
					.setCancelable(false)
					.setPositiveButton("New Game",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// reset
									onCreate(null);
								}
							})
					.setNegativeButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									grid.setClickable(false);
								}
							}).show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Context mcontext = this;
		final GridView gridview = init_game(mcontext);
		final int random_fruit_no = ((Integer) gridview.getTag(R.id.focus_fruit_no)).intValue();
		final int random_fruit_id = fruit_ids[random_fruit_no];

		gridview.setOnItemClickListener(new OnItemClickListener() {
			HashSet<Integer> hs = new HashSet<Integer>();
			int random_count = ((Integer) gridview.getTag(R.id.focus_fruit_count)).intValue();

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (parent.getChildAt(position).getTag(R.id.fruit_no) != (Integer) random_fruit_no) {
					game_over();
				} else if (hs.add(position)) {
					random_count = random_count - 1;
					parent.getChildAt(position).setAlpha((float) 0.3);
					parent.getChildAt(position).setPressed(true);
					targetClicked(parent, random_count, random_fruit_id,random_fruit_no,
							mcontext);
					shuffling(parent, hs, position);
				}
			}
		});

		Button btn = (Button) findViewById(R.id.button_exit);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});

		btn = (Button) findViewById(R.id.button_rst);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onCreate(null);
			}
		});
	}
}
