package component;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class ButtonMenu extends JButton {

    private Animator animator;
    private int targetSize;
    private float animaSize;
    private Point pressPoint;
    private float alpha;
    private Color effectColor = new Color(173, 173, 173);

    
    private boolean isSubMenu = false;
    private boolean showingSubMenu = false;
    private List<ButtonMenu> subMenus = new ArrayList<>();

    public ButtonMenu() {
        setContentAreaFilled(false);
        setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 20));
        setBorder(new EmptyBorder(15, 10, 15, 10));
        setHorizontalAlignment(JButton.LEFT);
        setBackground(new Color(43, 44, 75));
        setForeground(new Color(250, 250, 250));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
        setFocusable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                targetSize = Math.max(getWidth(), getHeight() * 2);
                animaSize = 0;
                pressPoint = e.getPoint();
                alpha = 0.5f;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }
        });

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animaSize = fraction * targetSize;
                repaint();
            }
        };
        animator = new Animator(400, target);
        animator.setResolution(0);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        if (pressPoint != null) {
            Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, 10, 10));
            g2.setColor(effectColor);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            area.intersect(new Area(new Ellipse2D.Double(
                (pressPoint.x - animaSize / 2),
                (pressPoint.y - animaSize / 2),
                animaSize, animaSize)));
            g2.fill(area);
        }
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(grphcs);
    }

    @Override
    public void paint(Graphics grphcs) {
        if (isSelected()) {
            int width = getWidth();
            int height = getHeight();
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(60, 60, 60));
            g2.fillRoundRect(0, 0, width - 1, height - 1, 10, 10);
        }
        super.paint(grphcs);
    }

    
    public void setAsSubMenu(boolean isSubMenu) {
        this.isSubMenu = isSubMenu;
        if (isSubMenu) {
          setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 10));
        }
    }

    public void addSubMenu(ButtonMenu sub) {
        sub.setAsSubMenu(true);
        subMenus.add(sub);
    }

    public boolean isShowingSubMenu() {
        return showingSubMenu;
    }

    public void setShowingSubMenu(boolean show) {
        this.showingSubMenu = show;
    }

    public List<ButtonMenu> getSubMenus() {
        return subMenus;
    }
}
