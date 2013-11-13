package br.com.sigacon.dashboard;

import android.os.Bundle;
import br.com.sigacon.prjdashboard.R;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.app.SherlockActivity;
	
public class ColetarDadosActivity extends SherlockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                
        setContentView(R.layout.dashboard);    
    }   


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.coletar_dados, menu);
        return true;
    }
       
}
