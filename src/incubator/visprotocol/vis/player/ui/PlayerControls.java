package incubator.visprotocol.vis.player.ui;

import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.vis.player.FrameListener;
import incubator.visprotocol.vis.player.PlayerInterface;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 * @author Ondrej Milenovsky
 * */
public class PlayerControls extends JPanel implements FrameListener {

    private static final long serialVersionUID = 1993157784144029481L;

    private final PlayerInterface player;

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
    private int seekerPrecision = 100;
    private boolean useYodaTime = false;

    // state
    private long startTime = 0;
    private long durationTime = 0;
    private double speed = 1;
    private boolean seekerLock = true;

    public PlayerControls(PlayerInterface player) {
        this.player = player;
        player.addFrameListener(this);
        initComponents();
        seekerLock = false;
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
        seeker.setMaximum(0);

        speedBar.setMinimum(1);
        speedBar.setMaximum(10000);

        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.play();
            }
        });
        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.pause();
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.playBackwards();
            }
        });

        seeker.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (!seekerLock) {
                    seekerLock = true;
                    player.setPosition(getPosition());
                    seekerLock = false;
                }
            }
        });
        speedBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                computeSpeed();
                repaintSpeed();
                player.setSpeed(speed);
            }
        });

    }

    public void setSeekerPrecision(int precision) {
        this.seekerPrecision = precision;
    }

    public int getSeekerPrecision() {
        return seekerPrecision;
    }

    public void setPosition(long time) {
        int value = (int) (time / seekerPrecision);
        if (value > seeker.getMaximum()) {
            seeker.setMaximum(value);
        }
        seekerLock = true;
        seeker.setValue(value);
        seekerLock = false;
        repaintCurrTime();
    }

    public void setDurationTime(long time) {
        durationTime = time;
        seekerLock = true;
        seeker.setMaximum((int) (time / seekerPrecision));
        seekerLock = false;
        repaintDurTime();
    }

    public void setStartTime(long time) {
        startTime = time;
    }

    @Override
    public void drawFrame(Structure frame) {
        setStartTime(player.getStartTime());
        setDurationTime(player.getDuration());
        setPosition(frame.getTimeStamp());
    }

    private long getPosition() {
        return startTime + seeker.getValue() * seekerPrecision;
    }

    private void computeSpeed() {
        speed = speedBar.getValue() / 8000.0;
    }

    private void repaintCurrTime() {
        if (!useYodaTime) {
            lblTime.setText(printTime(getPosition()));
        }
    }

    private void repaintDurTime() {
        if (!useYodaTime) {
            lblDuration.setText(printTime(durationTime));
        }
    }

    private void repaintSpeed() {
        lblSpeed.setText(String.format("%.3fx", speed));
    }

    public static String printTime(long time) {
        return String.format("%d:%02d:%02d:%03d", time / 3600000, time % 3600000 / 60000,
                time % 60000 / 1000, time % 1000);
    }
}
