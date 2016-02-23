package ActionListener;

import Frame.MainFrame;

import java.awt.event.*;

/**
 * Created by ht on 2016/2/20.
 * 这是监听各个复选框（JCheckBox）的类
 */
public class JCheckBoxActionListener implements ActionListener {

    private MainFrame mainFrame;

    public JCheckBoxActionListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
