package br.com.sigacon.dashboard;

import android.os.Bundle;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
	
public class ExportarDadosActivity extends SherlockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   
        Toast.makeText(getApplicationContext(), "Dados exportados com sucesso", Toast.LENGTH_LONG).show();
        finish();
    }          
}
