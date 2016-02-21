package ActionListener;

import Frame.MainFrame;

import javax.swing.event.*;
import java.awt.event.*;

/**
 * Created by ht on 2016/2/20.
 */
public class JListSelectionListener implements ListSelectionListener {

    private MainFrame mainFrame;

    public JListSelectionListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
