package incubator.visprotocol.vis.player.ui;

import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.vis2d.Transformator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 * @author Ondrej Milenovsky
 * */
public class PlayerControls extends JPanel implements Transformator, PlayerController {

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
    private boolean useYodaTime = false;

    // state
    private long timeOffset = 0;
    private long currentTime = 0;
    private long durationTime = 0;

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

        seeker.setMinimum(0);

        seeker.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                setCurretnTime(getSeekerTime());
            }
        });

    }

    public void setSeekerPrecision(int precision) {
        this.seekerPrecision = precision;
    }

    public int getSeekerPrecision() {
        return seekerPrecision;
    }

    @Override
    public void setCurretnTime(long time) {
        currentTime = time;
        int value = (int) (time / seekerPrecision);
        if (value > seeker.getMaximum()) {
            seeker.setMaximum(value);
        }
        seeker.setValue(value);
        repaintCurrTime();
    }

    @Override
    public void setDurationTime(long time) {
        durationTime = time;
        seeker.setMaximum((int) (time / seekerPrecision));
        repaintDurTime();
    }

    @Override
    public void setTimeDispOffset(long time) {
        timeOffset = time;
    }

    @Override
    public void setToVis(Vis2DOutput vis2d) {
        vis2d.addPanel(this, BorderLayout.SOUTH);

    }

    private long getSeekerTime() {
        return seeker.getValue() * seekerPrecision;
    }

    private void repaintCurrTime() {
        if (!useYodaTime) {
            lblTime.setText(printTime(currentTime + timeOffset));
        }
    }

    private void repaintDurTime() {
        if (!useYodaTime) {
            lblDuration.setText(printTime(durationTime));
        }
    }

    public static String printTime(long time) {
        return String.format("%d:%02d:%02d:%03d", time / 3600000, time % 3600000 / 60000,
                time % 60000 / 1000, time % 1000);
    }
}
