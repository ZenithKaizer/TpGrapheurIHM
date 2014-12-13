package ptec.ihm.grapheur.ui;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;

public class MListener extends MouseInputAdapter implements MouseWheelListener {
    enum State {
        IDLE, PRESSED, DRAGGED
    }

    private Grapher graph;
    private Rectangle2D rect;
    Point oldpoint, newpoint;
    State state = State.IDLE;

    public MListener(Grapher g) {
        this.graph = g;
        rect = null;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        state = State.PRESSED;
        oldpoint = new Point(event.getX(), event.getY());
        graph.setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        Point center = new Point(x, y);
        if (event.getButton() == MouseEvent.BUTTON1)
            graph.zoom(center, 5);
        else if (event.getButton() == MouseEvent.BUTTON3)
            graph.zoom(center, -5);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        graph.setCursor(Cursor.getDefaultCursor());
        int notches = e.getWheelRotation();

        if (notches > 0)
            graph.zoom(new Point(e.getX(), e.getY()), 5);
        else
            graph.zoom(new Point(e.getX(), e.getY()), -5);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point newpoint = new Point(e.getX(), e.getY());
        if (e.getButton() == MouseEvent.BUTTON1) {
            Cursor cr = new Cursor(Cursor.HAND_CURSOR);
            graph.setCursor(cr);

            graph.translate(newpoint.x - oldpoint.x, newpoint.y - oldpoint.y);
            oldpoint = newpoint;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            if (newpoint.x > oldpoint.x && newpoint.y > oldpoint.y) {
                rect = new Rectangle2D.Double(oldpoint.x, oldpoint.y,
                        Math.abs(newpoint.x - oldpoint.x), Math.abs(newpoint.y - oldpoint.y));
            } else if (newpoint.x > oldpoint.x && newpoint.y < oldpoint.y) {
                rect = new Rectangle2D.Double(oldpoint.x, newpoint.y,
                        Math.abs(newpoint.x - oldpoint.x), Math.abs(newpoint.y - oldpoint.y));
            } else if (newpoint.x < oldpoint.x && newpoint.y > oldpoint.y) {
                rect = new Rectangle2D.Double(newpoint.x, oldpoint.y,
                        Math.abs(newpoint.x - oldpoint.x), Math.abs(newpoint.y - oldpoint.y));
            } else {
                rect = new Rectangle2D.Double(newpoint.x, newpoint.y,
                        Math.abs(newpoint.x - oldpoint.x), Math.abs(newpoint.y - oldpoint.y));
            }
            graph.repaint();
        }
        state = State.DRAGGED;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //graph.setCursor(Cursor.getDefaultCursor());
        newpoint = new Point(e.getX(), e.getY());
        if (state == State.DRAGGED && e.getButton() == MouseEvent.BUTTON3) {
            graph.zoom(oldpoint, newpoint);
        }
        rect = null;

        state = State.IDLE;
        oldpoint = newpoint;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void draw_rec(Graphics2D g) {
        if(rect != null)
            g.draw(rect);
    }

}