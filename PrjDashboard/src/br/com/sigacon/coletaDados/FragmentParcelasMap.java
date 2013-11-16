/*
 * Copyright (C) 2011 The Android Open Source Project
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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.com.sigacon.sgcativosflorestais.R;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentParcelasMap extends SherlockFragmentActivity {
    int mStackLevel = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map_parcelas);
       
        if (savedInstanceState == null) {
            // Do first time initialization -- add initial fragment.
            Fragment newFragment = CountingFragment.newInstance(mStackLevel);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.mapParcelas, newFragment).commit();
            
        } else {
            mStackLevel = savedInstanceState.getInt("level");
        }
    }    

	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", mStackLevel);
    }

    public static class CountingFragment extends SherlockFragment {
        int mNum;

        private LatLng frameworkSystemLocation = new LatLng(-19.92550, -43.64058);
        private GoogleMap map;
        
        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static CountingFragment newInstance(int num) {
            CountingFragment f = new CountingFragment();
            
            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        @Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			// TODO Auto-generated method stub
        	//menu.clear();
        	Log.i("sigaLog", "R.menu.map");
        	inflater.inflate(R.menu.map, menu);
			super.onCreateOptionsMenu(menu,inflater);
		}

		/**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        	
            View v = inflater.inflate(R.layout.fragment_map_parcelas, container, false);
            
            map = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapParcelas)).getMap();
            
            if(map!=null)
            {
	            frameworkSystemLocation = new LatLng(-24.112151,-49.31387);
	            map.setMyLocationEnabled(true);
	            map.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation, 18));
	            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	            map.addMarker(new MarkerOptions()
	                    .title("Parcela")
	                    .snippet("Possição: -24.112151,-49.31387")
	                    .position(frameworkSystemLocation));
            }
            return v;
        }
    }
}
