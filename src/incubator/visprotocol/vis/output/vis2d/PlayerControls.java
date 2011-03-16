package incubator.visprotocol.vis.output.vis2d;

import incubator.visprotocol.vis.output.Vis2DOutput;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 * @author Ondrej Milenovsky
 * */
public class PlayerControls extends JPanel implements Transformator {

    private static final long serialVersionUID = 1993157784144029481L;

    // components
    private final JScrollBar seeker = new JScrollBar(JScrollBar.HORIZONTAL);
    private final JButton btnPlay = new JButton("Play");
    private final JButton btnBack = new JButton("Back");;
    private final JButton btnPause = new JButton("Pause");;
    private final JScrollBar speedBar = new JScrollBar(JScrollBar.HORIZONTAL);
    private final JLabel lblSpeed = new JLabel("Speed: 1x");
    private final JLabel lblTime = new JLabel("0:00:00:000");
    private final JLabel lblDuration = new JLabel("0:00:00:000");
    

    // properties
    private int seekerPrecision = 1000;

    public PlayerControls() {
        initComponents();
    }
    
    private void initComponents() {
        setPreferredSize(new Dimension(100, 30));
        setLayout(new GridLayout(2, 1));
        
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 10));
        panel1.add(btnPause);
        panel1.add(btnPlay);
        panel1.add(btnBack);
        panel1.add(new JPanel());
        panel1.add(speedBar);
        panel1.add(lblSpeed);
        panel1.add(new JPanel());
        panel1.add(lblTime);
        panel1.add(lblDuration);
        
        add(panel1);
        add(seeker);
    }
    
    public void setSeekerPrecision(int precision) {
        this.seekerPrecision = precision;
    }
    
    public int getSeekerPrecision() {
        return seekerPrecision;
    }

    @Override
    public void setToVis(Vis2DOutput vis2d) {
        vis2d.addPanel(this, BorderLayout.SOUTH);

    }
}
