package br.com.sigacon.prjdashboard;

import android.os.Bundle;
import android.view.Menu;
import android.support.v7.app.ActionBarActivity;
	
public class ColetarDadosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                
        setContentView(R.layout.dashboard);    
    }   
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coletar_dados, menu);
        return true;
    }
       
}
