import javax.swing.*;
import java.awt.*;
import java.io.File;

import Frame.*;

/**
 * Created by ht on 2016/2/20.
 */
public class testMainPanel {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        MainFrame frame = new MainFrame();
        frame.pack();
        frame.setResizable(false);
        frame.setLocation(screenWidth / 2 - frame.getWidth() / 2, screenHeight / 2 - frame.getHeight() / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        InitFrame iframe = new InitFrame(frame);
        iframe.pack();
        iframe.setResizable(false);
        iframe.setLocation(screenWidth / 2 - iframe.getWidth() / 2, screenHeight / 2 - iframe.getHeight() / 2);
        iframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //File可以写成配置文件，更加方便
        File file = new File("Config.w");
        if (file.exists()) {
            FileChooserFrame fileChooserFrame = new FileChooserFrame();
            fileChooserFrame.readFilePath(file);
            frame.setFileDirPath(fileChooserFrame.getFileDirPath());
            frame.setFiles(new File(fileChooserFrame.getFileDirPath()).list());
            frame.setVisible(true);
        } else {
            iframe.setVisible(true);
        }

    }

}
