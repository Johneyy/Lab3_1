package johneyy.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class EnterData extends ActionBarActivity {

    public static String MY_FILE= "List1";
    private TextView tv;
    private EditText ed;
    private SeekBar sb;
    private RatingBar rb;
    private String name;
    private int age;
    private float stars;
    private String system;
    Person person;
    String temp;
    List<Person>list=new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
        tv = (TextView)findViewById(R.id.textView6);
        ed = (EditText)findViewById(R.id.editText2);
        rb = (RatingBar)findViewById(R.id.ratingBar);
        sb = (SeekBar)findViewById(R.id.seekBar2);
        sb.setOnSeekBarChangeListener(seekBarChangeListener);
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.systems, R.layout.my_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                // An item was selected. You can retrieve the selected item using
                temp = parent.getItemAtPosition(pos).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_data, menu);
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
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener
            = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
            age = sb.getProgress();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };


    public void add(View view) {
        final Intent addIntention = new Intent(this, EnterData.class);
        name=ed.getText().toString();
        stars= rb.getRating();
        system = temp;
        person = new Person(name,age,system,stars);

        try {
            FileInputStream fin = openFileInput(MY_FILE);
            ObjectInputStream in = new ObjectInputStream(fin);
            list = (ArrayList)in.readObject();
            list.add(person);
            in.close();
            fin.close();

            FileOutputStream fOut = openFileOutput(MY_FILE, MODE_WORLD_READABLE);
            ObjectOutputStream out = new ObjectOutputStream(fOut);
            out.writeObject(list);
            out.close();
            fOut.close();

        }
        catch(Exception e){
            try {
                FileOutputStream fOut = openFileOutput(MY_FILE, MODE_WORLD_READABLE);
                ObjectOutputStream out = new ObjectOutputStream(fOut);
                list = new ArrayList<Person>();
                out.writeObject(list);
                out.close();
                fOut.close();
            }catch(Exception ex) {
                Log.e("EnterData", "Internal directory not created");
            }
        }


        //write();

        startActivity(addIntention);
    }
    public void backToMenu(View view){
        final Intent backIntention= new Intent(this,MainActivity.class);
        startActivity(backIntention);
    }
}
