package br.com.sigacon.prjdashboard;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
	
public class ExportarDadosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   
        Toast.makeText(getApplicationContext(), "Dados exportados com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }          
}
