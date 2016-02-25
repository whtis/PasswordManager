package utils;

import javax.swing.*;
import java.io.*;

/**
 * Created by ht on 2016/2/21.
 * 这是一个工具类，用于程序初始化和每次启动时。程序初始化会在本地机器上写入一个配置文件，每次启动时会检查配置文件是否
 * 存在，如果存在，则不会再出现初始化界面。
 */
public class FileChooser {

    private JFileChooser jFileChooser;

    private String fileDirPath;

    public FileChooser() {

    }

    /**
     * 该方法用于向用户指定路径写入配置文件
     * @param file
     * file是将要写入的配置文件
     */
    public void writeFilePath(File file) {
        jFileChooser = new JFileChooser(".");
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int i = jFileChooser.showOpenDialog(null);
        if (i == jFileChooser.APPROVE_OPTION) {
            fileDirPath = jFileChooser.getSelectedFile().getAbsolutePath();
        }
        //判断用户选取的路径文件夹中是否为空
        if (new File(fileDirPath).list().length != 0) {
            int comfirmInt = JOptionPane.showConfirmDialog(null, "所选文件夹不为空，是否继续");
            if (comfirmInt != JOptionPane.YES_OPTION) {
                System.exit(-1);
            }
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

    /**
     * 该方法在每次程序启动时读取配置文件
     * @param file
     * file是配置文件
     */
    public void readFilePath(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
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

    /**
     * 该方法用于从配置文件中读取密码存取路径，为了跨平台使用，对window平台上的路径做了处理
     * @return
     */
    public String getFileDirPath() {
        fileDirPath = fileDirPath.replaceAll("\\\\", "/");
        return fileDirPath;
    }
}


