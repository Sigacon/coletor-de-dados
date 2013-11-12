package br.com.sigacon.prjdashboard;

import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockActivity;
import android.view.View;
import android.widget.Toast;
import br.com.sigacon.sherlockFragments.FragmentLayoutSupport;
	
public class MainActivity extends SherlockActivity {

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
    	Intent intent = new Intent(this, FragmentLayoutSupport.class);
    	startActivity(intent);
        //Toast.makeText(getApplicationContext(), "Coletar Dados", Toast.LENGTH_LONG).show();
    }

    public void configuracoes(View view){
    	Toast.makeText(getApplicationContext(), "Configuracoes", Toast.LENGTH_SHORT).show();
    }
}
