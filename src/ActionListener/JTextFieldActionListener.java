package ActionListener;

import Frame.MainFrame;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by ht on 2016/2/20.
 * 这是监听各个文本（JTextField）的类
 */
public class JTextFieldActionListener implements ActionListener {

    private MainFrame mainFrame;

    public JTextFieldActionListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String filename = mainFrame.getJtfFileName().getText();
        String name = mainFrame.getJtfName().getText();

        if (e.getSource() == mainFrame.getJtfFileName()) {
            if (filename != null) {
                mainFrame.getJtfName().requestFocus();
            }
        }
        if (e.getSource() == mainFrame.getJtfName()) {
            if (filename != null && name != null) {
                mainFrame.getJtfKeyword().requestFocus();
            }
        }
        //第三个文本框的响应放在按钮响应类中，这样做是方便实现和生成按钮相同的功能
    }
}

