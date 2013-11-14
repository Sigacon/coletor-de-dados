package br.com.sigacon.dashboard;

import java.util.ArrayList;

import br.com.sigacon.prjdashboard.R;
import br.com.thinkti.android.filechooser.FileChooser;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.EditText;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
	
public class ImportarDadosActivity extends SherlockActivity {
	private static final int FILE_CHOOSER =  10;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
             
        setContentView(R.layout.importar_dados);    
    }   
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   switch (item.getItemId()) {
	      case android.R.id.home:
	         NavUtils.navigateUpTo(this,
	               new Intent(this, MainActivity.class));
	         return true;
	   }
	   return super.onOptionsItemSelected(item);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getSupportMenuInflater().inflate(R.menu.importar, menu);
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
