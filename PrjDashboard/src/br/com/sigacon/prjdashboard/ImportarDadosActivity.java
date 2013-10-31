package br.com.sigacon.prjdashboard;

import java.util.ArrayList;

import br.com.thinkti.android.filechooser.FileChooser;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.support.v7.app.ActionBarActivity;
	
public class ImportarDadosActivity extends ActionBarActivity {
	private static final int FILE_CHOOSER =  10;
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
    public void chooseFile(View view)
    {
    	 Intent intent = new Intent(this, FileChooser.class);
    	 ArrayList<String> extensions = new ArrayList<String>();
    	 //extensions.add(".pdf");
    	 extensions.add(".xls");   
    	 extensions.add(".xlsx");
    	 intent.putStringArrayListExtra("filterFileExtension", extensions);
    	 startActivityForResult(intent, FILE_CHOOSER);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CHOOSER && data != null) {
            String fileSelected = data.getStringExtra("fileSelected");
            EditText fileName = (EditText)findViewById(R.id.fileName);
            fileName.setText(fileSelected);
        }                  
    }
       
}
