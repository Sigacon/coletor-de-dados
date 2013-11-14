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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.sigacon.dashboard.MainActivity;
import br.com.sigacon.prjdashboard.R;

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
public class FragmentEstratos extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(com.actionbarsherlock.R.style.Theme_Sherlock_Light); //Used for theme switching in samples
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.fragment_layout_estratos);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   switch (item.getItemId()) {
	      case android.R.id.home:
	         NavUtils.navigateUpTo(this,
	               new Intent(this, MainActivity.class));
	         return true;
	   }
	   return super.onOptionsItemSelected(item);
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getSupportMenuInflater().inflate(R.menu.coletar_dados, menu);
        return true;
    }
    public static class ParcelasActivity extends SherlockFragmentActivity {

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
                ParcelasFragment parcelas = new ParcelasFragment();
                parcelas.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, null).commit();
            }
        }
    }


    /**
     * This is the "top-level" fragment, showing a list of items that the
     * user can pick.  Upon picking an item, it takes care of displaying the
     * data to the user as appropriate based on the currrent UI layout.
     */

    public static class EstratosFragment extends SherlockListFragment {
        boolean mDualPane;
        int mCurCheckPosition = 0;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Populate list with our static array of titles.
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.list_item_estrato_checkable,
                    android.R.id.text1, ListasFragment.ESTRATO));

            View estratosFrame = getActivity().findViewById(R.id.estratos);
            estratosFrame.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_arrowlist_activate));
            
            // Check to see if we have a frame in which to embed the details
            // fragment directly in the containing UI.
            View parcelasFrame = getActivity().findViewById(R.id.parcelas);
            parcelasFrame.setBackgroundColor(getResources().getColor(R.color.white));
            
            mDualPane = parcelasFrame != null && parcelasFrame.getVisibility() == View.VISIBLE;

            if (savedInstanceState != null) {
                // Restore last state for checked position.
                mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
            }

            if (mDualPane) {
                // In dual-pane mode, the list view highlights the selected item.
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                // Make sure our UI is in the correct state.
                showParcelas(mCurCheckPosition);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice", mCurCheckPosition);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            showParcelas(position);
        }

        /**
         * Helper function to show the details of a selected item, either by
         * displaying a fragment in-place in the current UI, or starting a
         * whole new activity in which it is displayed.
         */
        void showParcelas(int index) {
            mCurCheckPosition = index;

            if (mDualPane) {
                // We can display everything in-place with fragments, so update
                // the list to highlight the selected item and show the data.
                getListView().setItemChecked(index, true);
                
                // Check what fragment is currently shown, replace if needed.
                ParcelasFragment parcelas = (ParcelasFragment) getFragmentManager().findFragmentById(R.id.parcelas);
                
                if (parcelas != null) {
                	parcelas.setListAdapter(new ArrayAdapter<String>(getActivity(),
                            R.layout.list_item_parcela_checkable,
                            android.R.id.text1, ListasFragment.PARCELAS[index]));
                }
            }
        }
    }

    public static class ParcelasFragment extends SherlockListFragment {
       // boolean mDualPane;
        int mCurCheckPosition = 0;

		@Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Populate list with our static array of titles.
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.list_item_parcela_checkable,
                    android.R.id.text1, ListasFragment.PARCELAS[mCurCheckPosition]));
        }
		
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
        	Toast.makeText(getSherlockActivity(), "Parcela "+ position, Toast.LENGTH_SHORT).show();
        }
    }
}
