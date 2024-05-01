
package torre.de;

import java.awt.Color;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Anillos extends JPanel {
     public Anillos() {
        Random rand = new Random();

        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        
        Color colorAnillo = new Color(r, g, b);

        Border bordejpanel = new TitledBorder(new BevelBorder(2));

        this.setBorder(bordejpanel); 
        this.setBackground(colorAnillo);
    }
}
    