package johneyy.lab3;
import java.io.Serializable;


public class UtilityProperties implements Serializable {
    String text;
    int colour;
    UtilityProperties(String text,int colour){
        this.text=text;
        this.colour=colour;
    }
}
