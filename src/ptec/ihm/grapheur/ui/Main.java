package ptec.ihm.grapheur.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Main extends JFrame {
    private JList listbox;
    private Grapher grapher;
    private JListFunc list_func;

    Main(String title, String[] expressions) {
        super(title);
        grapher = new Grapher();
        for (String expression : expressions) {
            grapher.add(expression);
        }
        list_func = new JListFunc(grapher);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list_func, grapher);
        add(splitPane);

        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("Expression");
        file.setMnemonic(KeyEvent.VK_E);

        JMenuItem iadd = new JMenuItem("Add..");
        iadd.setMnemonic(KeyEvent.VK_N);
        iadd.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.META_MASK));
        iadd.setToolTipText("Add function");
        iadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                list_func.add_func();
            }
        });

        JMenuItem iremove = new JMenuItem("Remove");
        iremove.setMnemonic(KeyEvent.VK_DELETE);
        iremove.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_DELETE, ActionEvent.META_MASK));
        iremove.setToolTipText("Remove function");
        iremove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                list_func.remove_func();
            }
        });

        file.add(iadd);
        file.add(iremove);
        menubar.add(file);
        setJMenuBar(menubar);
    }

    public static void main(String[] argv) {
        final String[] expressions = argv;
        Main frame = new Main("grapher", expressions);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // frame.pack();
        frame.setVisible(true);
    }


}
