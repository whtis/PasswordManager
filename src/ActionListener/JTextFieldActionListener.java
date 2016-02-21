package ActionListener;

import Frame.MainFrame;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by ht on 2016/2/20.
 */
public class JTextFieldActionListener implements ActionListener {

    private MainFrame mainFrame;

    public JTextFieldActionListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFrame.getJtfFileName()) {
            String filename = mainFrame.getJtfFileName().getText();
            if (filename != null) {
                mainFrame.getJtfName().requestFocus();
            }
        }
        if (e.getSource() == mainFrame.getJtfName()) {
            String name = mainFrame.getJtfName().getText();
            if (name != null) {
                mainFrame.getJtfKeyword().requestFocus();
            }
        }
    }
}

