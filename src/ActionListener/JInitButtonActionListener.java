package ActionListener;

import Frame.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by ht on 2016/2/21.
 */
public class JInitButtonActionListener implements ActionListener {

    private InitFrame initFrame;

    public JInitButtonActionListener(InitFrame initFrame) {
        this.initFrame = initFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == initFrame.getJbtInitButton()) {
            chooseFIleDir();
        }
    }

    private void chooseFIleDir() {
        File file = new File("Config.w");
        FileChooserFrame fcf = new FileChooserFrame();
        fcf.writeFilePath(file);
        this.initFrame.setVisible(false);
        //初始化设置
        this.initFrame.getMainFrame().setFileDirPath(fcf.getFileDirPath());
        this.initFrame.getMainFrame().setVisible(true);
    }
}
