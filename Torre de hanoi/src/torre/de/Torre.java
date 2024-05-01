
package torre.de;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Torre extends JPanel{
    
     public Torre() {
        this.setLayout(null);
    }

 
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        this.setBackground(Color.WHITE);

        g.setColor(Color.BLACK);

        g.fillRect(10, 270, 200,5);
  
        g.fillRect(110, 30, 5, 240);
        
    }
}
