package johneyy.lab3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class Properties extends Activity implements AdapterView.OnItemSelectedListener{
    private int seekR, seekG, seekB;
    private SeekBar redSeekBar, greenSeekBar, blueSeekBar;
    private EditText tv;
    private int colour=0;
    String text;
    EditText ed;
    TextView greetings;
    String temp;
    String temp1;
    public static String MY_PREFS_NAME;
    public static String MY_FILE= "Properties";
    UtilityProperties up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        text = prefs.getString("greetings", "No toast defined");//"No name defined" is the default value.
        colour = prefs.getInt("colour", 0); //0 is the default value.

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.toasts, R.layout.my_spinner);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        redSeekBar = (SeekBar) findViewById(R.id.colourBarR);
        greenSeekBar = (SeekBar) findViewById(R.id.colourBarG);
        blueSeekBar = (SeekBar) findViewById(R.id.colourBarB);

        redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        tv= (EditText)findViewById(R.id.editText);
        tv.setText(text);
        tv.setTextColor(colour);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.destination, R.layout.my_spinner);


        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
            int pos, long id) {

                // An item was selected. You can retrieve the selected item using
                temp1 = parent.getItemAtPosition(pos).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_properties, menu);
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

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        // An item was selected. You can retrieve the selected item using
        temp = parent.getItemAtPosition(pos).toString();


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener
            = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
            seekR = redSeekBar.getProgress();
            seekG = greenSeekBar.getProgress();
            seekB = blueSeekBar.getProgress();
            tv.setTextColor(Color.rgb(seekR, seekG, seekB));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    protected void onStop(){
        super.onStop();

    }
    public void backToMenu(View view){
        final Intent backIntention= new Intent(this,MainActivity.class);
        startActivity(backIntention);
    }
    public void save(View view){
        final Intent saveIntention= new Intent(this,Properties.class);

        colour=tv.getCurrentTextColor();
        text=tv.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("greetings", tv.getText().toString());
        editor.putInt("colour", colour);
        editor.commit();
    if(temp1.equals("InternalStorage"))
        try {
            up = new UtilityProperties(text,colour);
            FileOutputStream fOut = openFileOutput(MY_FILE, MODE_WORLD_READABLE);
            ObjectOutputStream out = new ObjectOutputStream(fOut);
            out.writeObject(up);
            out.close();
            fOut.close();
        }catch(Exception e){
            Log.e("Properties", "Internal directory not created");
        }
        else if(temp1.equals("ExternalStorage")){
        if(isExternalStorageWritable()){
            File file;
            FileOutputStream outputStream;
            try {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MyCache");

                outputStream = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(outputStream);
                out.writeObject(up);
                        out.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            };
        }
        else{
            Toast.makeText(getApplicationContext(), "External card is ureachable", Toast.LENGTH_SHORT).show();
        }
    }
        startActivity(saveIntention);
    }
    public void set(View view) {
        tv.setText(temp);
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), albumName);
        if (!file.mkdirs()) {
            Log.e("Properties", "Directory not created");
            Toast.makeText(getApplicationContext(), "Error during creating an file", Toast.LENGTH_SHORT).show();
        }
        return file;
    }
}
