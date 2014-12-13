package ptec.ihm.grapheur.ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by jean on 11/11/14.
 */
public class JListFunc extends JPanel {
    private Vector<String> vs;
    private JList listbox;
    private Grapher grapher;
    private JPanel flow;

    public JListFunc(Grapher g) {
        setLayout(new BorderLayout());
        grapher = g;
        draw_toolbar();
        draw_func_list();
        add(listbox, BorderLayout.CENTER);
        add(flow, BorderLayout.SOUTH);
    }

    public void add_func() {
        String input = JOptionPane.showInputDialog("Nouvelle Expression :");
        grapher.add(input);
        repaint();
    }

    public void remove_func() {
        int index = grapher.get_index();
        if (index >= 0) {
            grapher.remove_func(index);
            grapher.set_index(-1);
            repaint();
        }
    }

    public void draw_toolbar() {
        flow = new JPanel(new FlowLayout());
        JButton plus = new JButton("+");
        JButton minus = new JButton("-");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_func();
            }
        });

        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove_func();
            }
        });

        flow.add(plus);
        flow.add(minus);
    }

    public void draw_func_list() {
        vs = grapher.getFunc();
        listbox = new JList(vs);
        listbox.getSelectionModel().addListSelectionListener(new SharedListSelectionHandler());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        vs = grapher.getFunc();
        listbox.setListData(vs);
    }

    private class SharedListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            for (int i = 0; i < listbox.getModel().getSize(); ++i) {
                if (listbox.isSelectedIndex(i)) {
                    grapher.set_index(i);
                }
            }
        }
    }
}
