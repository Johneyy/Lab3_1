package johneyy.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import johneyy.lab3.adapters.CustomList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CustomizedView extends ActionBarActivity {
    Integer[] imageId = {
            R.drawable.microsoft,
            R.drawable.linux_logo,
            R.drawable.ios_icon1,
            R.drawable.android_icon,
            R.drawable.blackberry_logo_png,
    };
    public static String MY_FILE = "List1";
    ListView lv;
    List<Person> list= new ArrayList<Person>();
    List<String> list_array = new ArrayList<String>();
    CustomList MyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customized_view);

        lv = (ListView)findViewById(R.id.listView2);
        create();
        MyAdapter = new CustomList(this, list, imageId);
        lv.setAdapter(MyAdapter);
        MyAdapter.notifyDataSetChanged();

        lv.setOnItemClickListener(new ListView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                //String value = (String)adapter.getItemAtPosition(position);
                list.get(position).toString();
                Toast.makeText(getApplicationContext(), list.get(position).toString(), Toast.LENGTH_LONG).show();
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v, int position,
                                           long arg3) {
                //String value = (String)adapter.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), list.get(position).name + " has been deleted", Toast.LENGTH_LONG).show();
                list.remove(position);
                list_array.remove(position);
                MyAdapter.notifyDataSetChanged();
                try {
                    FileOutputStream fOut = openFileOutput(MY_FILE, MODE_WORLD_READABLE);
                    ObjectOutputStream out = new ObjectOutputStream(fOut);
                    out.writeObject(list);
                    out.close();
                    fOut.close();



                } catch (Exception e) {
                    Log.e("EnterData", "Internal directory not created");
                }

                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_customized_view, menu);
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

    public void create(){
        try {
            FileInputStream fin = openFileInput(MY_FILE);
            ObjectInputStream in = new ObjectInputStream(fin);
            list = (ArrayList) in.readObject();

            Iterator<Person> it = list.iterator();
            while(it.hasNext()){
                list_array.add(it.next().name);
            }

        }catch(Exception e){

        }
    }

    public void backToMenu(View view){
        final Intent backIntention= new Intent(this,MainActivity.class);
        startActivity(backIntention);
    }
}
