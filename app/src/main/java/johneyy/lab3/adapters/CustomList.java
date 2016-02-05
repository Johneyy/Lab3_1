package johneyy.lab3.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import johneyy.lab3.Person;
import johneyy.lab3.R;

import java.util.List;

public class CustomList extends BaseAdapter {
    private final Activity context;
    private List<Person> personList;
    private final Integer[] imageId;
    private final String[] web = {
            "Windows",
            "Linux",
            "iOS",
            "Android",
            "Blackberry"
    } ;
    public CustomList(Activity context,
                      List<Person>personList, Integer[] imageId) {
        this.context = context;
        this.personList = personList;
        this.imageId = imageId;
    }
    public int getCount() {
        return personList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.customized_list_row, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        txtTitle.setText(personList.get(position).name);
        imageView.setImageResource(check(personList.get(position).system));

        TextView txt1 = (TextView) rowView.findViewById(R.id.txt1);
        txt1.setText("System: "+personList.get(position).system);

        TextView txt2 = (TextView) rowView.findViewById(R.id.txt2);
        txt2.setText("Age: "+personList.get(position).age);

        TextView txt3 = (TextView) rowView.findViewById(R.id.txt3);
        txt3.setText("Rating: "+personList.get(position).stars);
        return rowView;
    }
    public int check(String system){
        for(int i=0;i<web.length;i++){
            if(system.equals(web[i]))
                return imageId[i];
        }
        return 0;
    }
}