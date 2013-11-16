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

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;
import br.com.sigacon.dashboard.MainActivity;
import br.com.sigacon.sgcativosflorestais.R;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI
 * that switches between tabs and also allows the user to perform horizontal
 * flicks to move between the tabs.
 */
public class FragmentParcelasTabsPager extends SherlockFragmentActivity {
    TabHost mTabHost;
    ViewPager  mViewPager;
    TabsAdapter mTabsAdapter;
    private boolean mapDefault = false;
    private GoogleMap map = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tabs_pager);
        setTitle("Dados parcela");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mViewPager = (ViewPager)findViewById(R.id.pager);

        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

        mTabsAdapter.addTab(mTabHost.newTabSpec("maps").setIndicator("Mapa"),
        		FragmentParcelasMap.CountingFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("form").setIndicator("Formulario parcela"),
                FragmentParcelasForm.CountingFragment.class, null);

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        
    	new Handler().postDelayed(new Runnable() {
    		
    		@Override
    		public void run() {
    			String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);   
        		//Se vier null ou length == 0   é por que o GPS esta desabilitado. 
             
               Log.e("sigaLog", "provider "+String.valueOf(provider == null) + " ou " + ((provider.contains("gps"))?provider:"not Gps") );
               if( provider == null || !provider.contains("gps"))
               {
        	        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FragmentParcelasTabsPager.this);
        	        // Seta o Titulo do Dialog
        	        alertDialogBuilder.setTitle("Atenção");
        	        Log.e("sigaLog", "alertDialogBuilder.setTitle"); 
        	        // seta a mensagem
        	        alertDialogBuilder.setMessage("O GPS está desativado deseja ativa-lo agora?");
        	        alertDialogBuilder.setCancelable(false).setPositiveButton(R.string.sim,
        	            new DialogInterface.OnClickListener() {
        		            public void onClick(DialogInterface dialog, int id) {
        		            	  
                   			//Para abrir a tela do menu pode fazer assim:   
                   			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);   
                   			startActivityForResult(intent, 1);  
        	                }
        	            }).setNegativeButton(R.string.nao,
        	            new DialogInterface.OnClickListener() {
        	                public void onClick(DialogInterface dialog, int id) {
        	                    dialog.cancel();
        	                }
        	            });
        	     
        	        // Cria o alertDialog com o conteudo do alertDialogBuilder
        	        AlertDialog alertDialog = alertDialogBuilder.create();
        	        Log.e("sigaLog", "alertDialog.create");
        	        // Exibe o Dialog
        	        alertDialog.show();
        	        Log.e("sigaLog", "alertDialog.show");
               }     			
    		}
    	}, 1000);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapParcelas)).getMap();
 		switch (item.getItemId()) {
	      	case android.R.id.home:
	      		NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
	      		return true;
	      	case R.id.form_parcela_save:
	      		Intent intent = new Intent(getApplicationContext(), FragmentArvores.class);
	        	startActivity(intent);
	      		return true;
	      	case R.id.mapType:
	      		if(map != null){
		      		if(mapDefault)
			      		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		      		else
			      		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		      		
		      		mapDefault = !mapDefault;
	      		}else{
	      			Toast.makeText(getApplicationContext(), "Opção desabilitada por falta de comunicação com GPS", Toast.LENGTH_SHORT).show();
	      		}
	      		return true;
	      	case R.id.myLocation:
	      		if(map != null){
		      		if(map.getMyLocation() != null){
		      			Toast.makeText(getApplicationContext(), "Longitude: "+map.getMyLocation().getLongitude()+" | Latitude: "+map.getMyLocation().getLatitude() , Toast.LENGTH_SHORT).show();
		      		}else{
		      			Toast.makeText(getApplicationContext(), "Sua localização ainda não foi encontrada tente novamente em alguns segundos", Toast.LENGTH_SHORT).show();
		      		}
		 		}else{
		  			Toast.makeText(getApplicationContext(), "Opção desabilitada por falta de comunicação com GPS", Toast.LENGTH_SHORT).show();
		  		}
	      		return true;
	   }
	   return super.onOptionsItemSelected(item);
	}
	  
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }

	/**
     * This is a helper class that implements the management of tabs and all
     * details of connecting a ViewPager with associated TabHost.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between pages.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct paged in the ViewPager whenever the selected
     * tab changes.
     */
    public static class TabsAdapter extends FragmentPagerAdapter implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }       
        
        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }
        
        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onTabChanged(String tabId) {
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // Unfortunately when TabHost changes the current tab, it kindly
            // also takes care of putting focus on it when not in touch mode.
            // The jerk.
            // This hack tries to prevent this from pulling focus out of our
            // ViewPager.
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
            widget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
