package ActionListener;

import Frame.MainFrame;
import utils.PasswordIO;

import javax.swing.*;
import javax.swing.event.*;
import java.io.File;

/**
 * Created by ht on 2016/2/20.
 * 这是监听密码列表（JList）的类
 */
public class JListSelectionListener implements ListSelectionListener {

    private MainFrame mainFrame;

    public JListSelectionListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList jList = mainFrame.getJlFileName();
        String fileDirPath = mainFrame.getFileDirPath();
        String filename = (String) jList.getSelectedValue();
        //处理文件名中多余的部分
        int index = jList.getSelectedIndex();
        //根据index的位数决定filename字串是截取最前面几个字符
        if (index >= 0) {
            int x = String.valueOf(index + 1).toCharArray().length;
            filename = filename.substring(x + 1);
        }
        File file = new File(fileDirPath + "/" + filename);
        if (file.exists() && filename.endsWith("w")) {
            String[] result = PasswordIO.readBinaryPassed(file);
            if (result.length == 3) {
                PasswordIO.showContent(result, mainFrame);
                mainFrame.getJtfFileName().setText(filename.replace(".w", ""));
            }
        } else if (file.exists() && filename.endsWith("wf")) {
            String[] result = PasswordIO.readFilePasswd(file);
            if (result.length == 3) {
                PasswordIO.showContent(result,mainFrame);
                mainFrame.getJtfFileName().setText(filename.replace(".wf", ""));
            }
        }
    }
}
