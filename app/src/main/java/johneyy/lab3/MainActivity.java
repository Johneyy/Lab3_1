package johneyy.lab3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends ActionBarActivity {
    private TextView tv;
    private TextView itv;
    public static String MY_PREFS_NAME;
    public static String MY_FILE = "Properties";
    String text;
    int colour;
    UtilityProperties up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume(){
        tv= (TextView)findViewById(R.id.linkedText);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        text = prefs.getString("greetings", "Default");//"No name defined" is the default value.
        colour = prefs.getInt("colour", 0); //0 is the default value.
        tv.setText(text);
        tv.setTextColor(colour);

        itv = (TextView)findViewById(R.id.internalText);

        try{
            FileInputStream fin = openFileInput(MY_FILE);
            ObjectInputStream in = new ObjectInputStream(fin);
            up = (UtilityProperties) in.readObject();
        }catch(Exception e){
            try {
                FileOutputStream fOut = openFileOutput(MY_FILE, MODE_WORLD_READABLE);
                ObjectOutputStream out = new ObjectOutputStream(fOut);
                up=new UtilityProperties("Not choosen yet", Color.BLACK);
                out.writeObject(up);
                out.close();
                fOut.close();
            }catch(Exception ex) {
                Log.e("Main", "Internal Memory problem");
            }
        }

        itv.setText(up.text);
        itv.setTextColor(up.colour);

        super.onResume();
    }

    public void launchProperties(View view){
        final Intent propertiesIntention= new Intent(this,Properties.class);
        startActivity(propertiesIntention);

    }
    public void launchEnterData(View view){
        final Intent enterDataIntention= new Intent(this,EnterData.class);
        startActivity(enterDataIntention);
    }
    public void launchShowData(View view){
        final Intent showDataIntention= new Intent(this,ShowData.class);
        startActivity(showDataIntention);
    }
    public void launchCustomizedView(View view){
        final Intent customizedViewIntention= new Intent(this,CustomizedView.class);
        startActivity(customizedViewIntention);
    }
    public void exitApplication(View view){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
