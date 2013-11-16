/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.sigacon.coletaDados;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.sigacon.dashboard.MainActivity;
import br.com.sigacon.sgcativosflorestais.R;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * Demonstration of using fragments to implement different activity layouts.
 * This sample provides a different layout (and activity flow) when run in
 * landscape.
 */
public class FragmentArvores extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(com.actionbarsherlock.R.style.Theme_Sherlock_Light); //Used for theme switching in samples
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("Arvores");
        setContentView(R.layout.fragment_layout_arvores);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   switch (item.getItemId()) {
	      case android.R.id.home:
	         NavUtils.navigateUpTo(this,
	               new Intent(this, MainActivity.class));
	         return true;
	      case R.id.action_finish:
	    	  Intent intent = new Intent(this, FragmentArvoresRateadas.class);
	      	  startActivity(intent);
		      return true;
	      case R.id.action_add:
	    	  Toast.makeText(getApplicationContext(), "Adicionar arvore", Toast.LENGTH_LONG).show();
	      case R.id.action_save:
	    	  Toast.makeText(getApplicationContext(), "Salvar dados", Toast.LENGTH_LONG).show();
	   }
	   return super.onOptionsItemSelected(item);
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getSupportMenuInflater().inflate(R.menu.arvores, menu);
        return true;
    }
    public static class ArvoresActivity extends SherlockFragmentActivity {

		@Override
        protected void onCreate(Bundle savedInstanceState) {
            //setTheme(com.actionbarsherlock.R.style.Theme_Sherlock_Light); //Used for theme switching in samples
            super.onCreate(savedInstanceState);

            if (getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE) {
                // If the screen is now in landscape mode, we can show the
                // dialog in-line with the list so we don't need this activity.
                finish();
                return;
            }

            if (savedInstanceState == null) {
                // During initial setup, plug in the details fragment.
                //FormParcelasFragment parcelas = new FormParcelasFragment();
                //parcelas.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, null).commit();
            }
        }		
    }


    /**
     * This is the "top-level" fragment, showing a list of items that the
     * user can pick.  Upon picking an item, it takes care of displaying the
     * data to the user as appropriate based on the currrent UI layout.
     */

    public static class ArvoresFragment extends SherlockListFragment {
        boolean mDualPane;
        int mCurCheckPosition = 0;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Populate list with our static array of titles.
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.list_item_estrato_checkable,
                    android.R.id.text1, ListasFragment.ARVORES));
            View estratosFrame = getActivity().findViewById(R.id.listArvores);
            estratosFrame.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_arrowlist_activate));
            
            // Check to see if we have a frame in which to embed the details
            // fragment directly in the containing UI.
            View parcelasFrame = getActivity().findViewById(R.id.formArvores);
            //parcelasFrame.setBackgroundColor(getResources().getColor(R.color.white));
            
            mDualPane = parcelasFrame != null && parcelasFrame.getVisibility() == View.VISIBLE;

            if (savedInstanceState != null) {
                // Restore last state for checked position.
                mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
            }

            if (mDualPane) {
                // In dual-pane mode, the list view highlights the selected item.
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                // Make sure our UI is in the correct state.
                showArvores(mCurCheckPosition);
            }
            getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                        int arg2, long arg3) {
                    Toast.makeText(getActivity(), "On long click listener", Toast.LENGTH_LONG).show();
                    return true;
                }
            });
            
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", mCurCheckPosition);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            showArvores(position);
        }
        

        /**
         * Helper function to show the details of a selected item, either by
         * displaying a fragment in-place in the current UI, or starting a
         * whole new activity in which it is displayed.
         */
        void showArvores(int index) {
        	mCurCheckPosition = index;

            if (mDualPane) {
                // We can display everything in-place with fragments, so update
                // the list to highlight the selected item and show the data.
                getListView().setItemChecked(index, true);

                // Check what fragment is currently shown, replace if needed.
                /*
                FormParcelasFragment details = (FormParcelasFragment)
                        getFragmentManager().findFragmentById(R.id.formParcelas);
                if (details == null || details.getShownIndex() != index) {
                    // Make new fragment to show this selection.
                    details = FormParcelasFragment.newInstance(index);

                    // Execute a transaction, replacing any existing fragment
                    // with this one inside the frame.
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.formParcelas, details);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }*/

            } /*else {
                // Otherwise we need to launch a new activity to display
                // the dialog fragment with selected text.
                Intent intent = new Intent();
                intent.setClass(getActivity(), DetailsActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }*/
        }
    }
    /*
    public static class FormParcelasFragment extends SherlockFragment {
      
        public static FormParcelasFragment newInstance(int index) {
        	FormParcelasFragment f = new FormParcelasFragment();

            // Supply index input as an argument.
            Bundle args = new Bundle();
            args.putInt("index", index);
            f.setArguments(args);

            return f;
        }

        public int getShownIndex() {
            return getArguments().getInt("index", 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            if (container == null) {
                // We have different layouts, and in one of them this
                // fragment's containing frame doesn't exist.  The fragment
                // may still be created from its saved state, but there is
                // no reason to try to create its view hierarchy because it
                // won't be displayed.  Note this is not needed -- we could
                // just run the code below, where we would create and return
                // the view hierarchy; it would just never be used.
                return null;
            }

            ScrollView scroller = new ScrollView(getActivity());
            TextView text = new TextView(getActivity());
            int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    4, getActivity().getResources().getDisplayMetrics());
            text.setPadding(padding, padding, padding, padding);
            scroller.addView(text);
            text.setText("So shaken as we are, so wan with care," +
                    "Find we a time for frighted peace to pant," +
                    "And breathe short-winded accents of new broils" +
                    "To be commenced in strands afar remote." +
                    "No more the thirsty entrance of this soil" +
                    "Shall daub her lips with her own children's blood;" +
                    "Nor more shall trenching war channel her fields," +
                    "Nor bruise her flowerets with the armed hoofs" +
                    "Of hostile paces: those opposed eyes," +
                    "Which, like the meteors of a troubled heaven," +
                    "All of one nature, of one substance bred," +
                    "Did lately meet in the intestine shock" +
                    "And furious close of civil butchery" +
                    "Shall now, in mutual well-beseeming ranks," +
                    "March all one way and be no more opposed" +
                    "Against acquaintance, kindred and allies:" +
                    "The edge of war, like an ill-sheathed knife," +
                    "No more shall cut his master. Therefore, friends," +
                    "As far as to the sepulchre of Christ," +
                    "Whose soldier now, under whose blessed cross" +
                    "We are impressed and engaged to fight," +
                    "Forthwith a power of English shall we levy;" +
                    "Whose arms were moulded in their mothers' womb" +
                    "To chase these pagans in those holy fields" +
                    "Over whose acres walk'd those blessed feet" +
                    "Which fourteen hundred years ago were nail'd" +
                    "For our advantage on the bitter cross." +
                    "But this our purpose now is twelve month old," +
                    "And bootless 'tis to tell you we will go:" +
                    "Therefore we meet not now. Then let me hear" +
                    "Of you, my gentle cousin Westmoreland," +
                    "What yesternight our council did decree" +
                    "In forwarding this dear expedience.");
            return scroller;
        }
    }*/
}
