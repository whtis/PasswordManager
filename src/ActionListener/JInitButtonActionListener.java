package ActionListener;

import Frame.*;
import utils.FileChooser;

import java.awt.event.*;
import java.io.File;

/**
 * Created by ht on 2016/2/21.
 * 这是监听初始化界面按钮的类
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

    /*
    在程序第一次运行时，该方法确定文件的存储路径
     */
    private void chooseFIleDir() {
        File file = new File(".pmconfig.w");
        FileChooser fcf = new FileChooser();
        fcf.writeFilePath(file);
        this.initFrame.setVisible(false);
        //初始化设置
        this.initFrame.getMainFrame().setFileDirPath(fcf.getFileDirPath());
        this.initFrame.getMainFrame().setVisible(true);
    }
}
