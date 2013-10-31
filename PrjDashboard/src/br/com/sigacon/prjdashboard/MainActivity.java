package br.com.sigacon.prjdashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;
	
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                
        setContentView(R.layout.dashboard);    
    }          
    
    public void importarDados(View view){
    	Intent intent = new Intent(this, ImportarDadosActivity.class);
    	startActivity(intent);
    }
    
    public void exportarDados(View view){
    	Intent intent = new Intent(this, ExportarDadosActivity.class);
    	startActivity(intent);    	
    }
    
    public void coletarDados(View view){
        Toast.makeText(getApplicationContext(), "Coletar Dados", Toast.LENGTH_LONG).show();
    }

    public void configuracoes(View view){
    	Toast.makeText(getApplicationContext(), "Configurações", Toast.LENGTH_SHORT).show();
    }
}
