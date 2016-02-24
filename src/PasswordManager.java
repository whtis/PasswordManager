import javax.swing.*;
import java.awt.*;
import java.io.File;

import Frame.*;
import utils.*;

/**
 * Created by ht on 2016/2/20.
 */
public class PasswordManager {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        MainFrame frame = new MainFrame();
        frame.pack();
        frame.setTitle("简易密码管理器by@whtis");
        frame.setResizable(false);
        frame.setLocation(screenWidth / 2 - frame.getWidth() / 2, screenHeight / 2 - frame.getHeight() / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        InitFrame iframe = new InitFrame(frame);
        iframe.pack();
        iframe.setTitle("byr@wht");
        iframe.setResizable(false);
        iframe.setLocation(screenWidth / 2 - iframe.getWidth() / 2, screenHeight / 2 - iframe.getHeight() / 2);
        iframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //File可以写成配置文件，更加方便
        File file = new File(".pmconfig.w");
        if (file.exists()) {
            FileChooser fileChooserFrame = new FileChooser();
            fileChooserFrame.readFilePath(file);
            frame.setFileDirPath(fileChooserFrame.getFileDirPath());
            FileModel fileModel = new FileModel(new File(fileChooserFrame.getFileDirPath()).list());
            frame.getJlFileName().setModel(fileModel);
            frame.setVisible(true);
        } else {
            iframe.setVisible(true);
        }

    }

}
