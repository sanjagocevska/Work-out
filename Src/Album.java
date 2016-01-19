import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Album extends Applet implements ActionListener{
    
Timer casovnik;

TextField txtInterval = new TextField("3");
int speed = 3000;

Button
    btnPrethodna = new Button("Prethodna"),
    btnPrva = new Button("Prva"),
    btnPosledna = new Button("Posledna"),
    btnSledna = new Button("Sleadn");

Canvas
    canvas = new Canvas();

int vkupno = 0;	// vkupen broj na sliki
int i = 0;      // tekovna slika

Image Sliki[];

	 
public void init() {

    casovnik = new Timer(speed, this);
    casovnik.setInitialDelay(1900);
    casovnik.start(); 

    iscrtajGui();

    // get all the images 
    try {
        while(true)
        {
            URL url = new URL(getCodeBase(), "Photos/Image"+vkupno+".JPG");
            Image img = ImageIO.read(url);
            vkupno++;
        }
    } catch (Exception e) {
        // Nema veke sliki! Prodolzi so izvrsuvanjeto
    }
    
    Sliki = new Image[vkupno];	
    
    for (int p=0; p < vkupno; p++) {
            Sliki[p] = getImage(getCodeBase(), "Photos/Image"+p+".JPG");
            prepareImage(Sliki[p], this);
    }

} // init
	
private void displayImage(int n) {
        // Prikazi ja n-tata slika na kanvasot
        Graphics g = canvas.getGraphics();
        g.clearRect(10, 10, 700, 700);
        g.drawImage(Sliki[n], 30, 10, this);
        g.setColor(Color.BLUE);
        g.drawString("Slika: "+(n+1)+"/"+ vkupno, 20, 15);
} // displayImage

public void drawFirst(){

        displayImage(0);

} // drawFirst

public void drawLast(){
        displayImage(vkupno-1);
} // drawLast

public void drawPrevious(){
        i = i - 1;
        if (i <= -1){
                i = vkupno-1;
        }
        displayImage(i);
} // drawPrevious

public void drawNext(){

        i = i + 1;
        if (i == vkupno){
                i = 0;
        }

        displayImage(i);
} // drawNext

private void messageBox(String message) 
 { 
    javax.swing.JOptionPane pane = new 
    javax.swing.JOptionPane(message);	        	    	     
    javax.swing.JDialog dialog = pane.createDialog(new 
    javax.swing.JFrame(), "Greska"); 
    pane.showMessageDialog(dialog, message, "Greska", pane.OK_OPTION);	
} 

public void actionPerformed(ActionEvent e){
        if (e.getSource() == casovnik)
          drawNext();
        else if (e.getSource() == txtInterval)
        {
            String text = txtInterval.getText();
            int newspeed = speed;
            try {
                newspeed = Integer.parseInt(text);
            } 
            catch (Exception ex) {
                messageBox("Nevaliden broj!");
            }
            speed = newspeed;
            casovnik = new Timer(speed * 1000, this);
            casovnik.setInitialDelay(1900);
            casovnik.start(); 
            
        }
        else if (e.getSource() == btnPrethodna)
                drawPrevious();
        else if (e.getSource() == btnPrva)
                drawFirst();
        else if (e.getSource() == btnPosledna)
                drawLast();
        else if (e.getSource() == btnSledna)
                drawNext();
} // actionPerformed

public void paint(Graphics g) {
        drawFirst();
        i = 0;
}

private void iscrtajGui() {
        setBackground(Color.WHITE);
        setForeground(Color.BLUE);
        setLayout(new BorderLayout());

        Panel p1 = new Panel();

        p1.add(new Label("Interval"));
        p1.add(txtInterval);
        p1.add(new Label("sekundi"));
        p1.add(btnPrethodna);
        p1.add(btnPrva);

        p1.add(btnSledna);
        p1.add(btnPosledna);

        add(BorderLayout.NORTH, p1);
        add(BorderLayout.CENTER, canvas);

        btnPrethodna.addActionListener(this);
        btnPrva.addActionListener(this);
        btnPosledna.addActionListener(this);
        btnSledna.addActionListener(this);
        
        txtInterval.addActionListener(this);

} // iscrtajGui
} 