package br.com.sigacon.prjdashboard;

import android.os.Bundle;
import android.view.Menu;
import android.support.v7.app.ActionBarActivity;
	
public class ImportarDadosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                
        setContentView(R.layout.importar_dados);    
    }   
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.importar, menu);
        return true;
    }
       
}
