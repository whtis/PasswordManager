package Frame;

import javax.swing.*;
import java.io.*;

/**
 * Created by ht on 2016/2/21.
 */
public class FileChooserFrame {

    private JFileChooser jFileChooser;

    private String fileDirPath;

    public FileChooserFrame() {

    }

    public void writeFilePath(File file) {
        jFileChooser = new JFileChooser(".");
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int i = jFileChooser.showOpenDialog(null);
        if (i == jFileChooser.APPROVE_OPTION) {
            fileDirPath = jFileChooser.getSelectedFile().getAbsolutePath();
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(fileDirPath);
            bw.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "写入配置文件出错，请重试");
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void readFilePath(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("Config.w"));
            fileDirPath = br.readLine();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "配置文件不存在，请检查");
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "配置文件读取出错，请检查");
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getFileDirPath() {
        fileDirPath = fileDirPath.replaceAll("\\\\", "/");
        return fileDirPath;
    }
}


