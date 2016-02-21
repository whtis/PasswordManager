package ActionListener;

import Frame.*;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

/**
 * Created by ht on 2016/2/20.
 */
public class JButtonActionListener implements ActionListener {

    private MainFrame mainFrame;

    public JButtonActionListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String fileDirPath = mainFrame.getFileDirPath();
        if (e.getSource() == mainFrame.getJbtGenerate()) {
            generate(e,fileDirPath);
            updateJList(fileDirPath);
        } else if (e.getSource() == mainFrame.getJbtCheck()) {
            check(e,fileDirPath);
        } else if (e.getSource() == mainFrame.getJbtUpdate()) {
//            update(e,fileDirPath);
        } else if (e.getSource() == mainFrame.getJbtDelete()) {
            delete(e,fileDirPath);
            updateJList(fileDirPath);
        } else if (e.getSource() == mainFrame.getJbtCopy()) {
            copy(e);
        }
    }

    private void updateJList(String fileDirPath) {
        String[] files = new File(fileDirPath).list();
        FileModel fileModel = new FileModel(files);
        mainFrame.getJlFileName().setModel(fileModel);
        mainFrame.getJlFileName().updateUI();
    }

    private void generate(ActionEvent e,String fileDirPath) {
        String filename = mainFrame.getJtfFileName().getText();
        String name = mainFrame.getJtfName().getText();
        String keyWord = mainFrame.getJtfKeyword().getText();
        File file = new File(fileDirPath + "/" + filename + ".w");
        if (checkTextFiled(filename, name, keyWord)) {
            if (file.exists()) {
                int comfirmInt = JOptionPane.showConfirmDialog(null, "该文件已存在，是否进行更新操作？");
                if (comfirmInt == JOptionPane.YES_OPTION) {
//                    update(e,fileDirPath);
                }
            } else {
                PasswordIO.writePasswd(filename, name, keyWord,mainFrame);
            }
        }
    }

    private void check(ActionEvent e,String fileDirPath) {
        String filename = mainFrame.getJtfFileName().getText();
        if (filename.equals("")) {
            JOptionPane.showMessageDialog(null, "网站/网址为空，请确认");
        } else {
            File testWFile = new File(fileDirPath + "/" + filename + ".w");
            File testWFFile = new File(fileDirPath + "/" + filename + ".wf");
            if (testWFile.exists()) {
                String[] result = PasswordIO.readWPasswd(testWFile);
                if (result.length == 3) {
                    mainFrame.getJtfName().setText(result[0]);
                    mainFrame.getJtfKeyword().setText(result[1]);
                    mainFrame.getJtfPasswd().setText(result[2]);
                }
            } else if (testWFFile.exists()) {
                String[] result = PasswordIO.readWFPasswd(testWFFile);
                if (result.length == 3) {
                    mainFrame.getJtfName().setText(result[0]);
                    mainFrame.getJtfKeyword().setText(result[1]);
                    mainFrame.getJtfPasswd().setText(result[2]);
                }

            } else {
                JOptionPane.showMessageDialog(null, "该帐号不存在，请检查");
            }
        }
    }
//todo update方法涉及到checkbox的状态，可以考虑重新生成，比对原始数据
//    private void update(ActionEvent e,String fileDirPath) {
//        String filename = mainFrame.getJtfFileName().getText();
//        String name = mainFrame.getJtfName().getText();
//        String keyWord = mainFrame.getJtfKeyword().getText();
//        if (checkTextFiled(filename, name, keyWord)) {
//            File file = new File(fileDirPath + "/" + filename + ".w");
//            if (file.exists()) {
//                String[] result = PasswordIO.readPasswd(file);
//                if (name.equals(result[0]) && keyWord.equals(result[1])) {
//                    JOptionPane.showMessageDialog(null, "数据重复，无需修改");
//                } else {
//                    PasswordIO.writePasswd(filename, name, keyWord, mainFrame);
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "该帐号不存在，请检查");
//            }
//        }
//    }

    private void delete(ActionEvent e,String fileDirPath) {
        String filename = mainFrame.getJtfFileName().getText();
        String name = mainFrame.getJtfName().getText();
        String keyWord = mainFrame.getJtfKeyword().getText();
        if (checkTextFiled(filename, name, keyWord)) {
            File file = new File(fileDirPath + "/" + filename + ".w");
            if (file.exists()) {
                if (file.delete()) {
                    JOptionPane.showMessageDialog(null, "文件删除成功");
                    mainFrame.getJtfFileName().setText("");
                    mainFrame.getJtfName().setText("");
                    mainFrame.getJtfKeyword().setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "文件删除出错，请重试");
                }
            } else {
                JOptionPane.showMessageDialog(null, "该帐号不存在，请检查");
            }
        }
    }

    private void copy(ActionEvent e) {
        String password = mainFrame.getJtfPasswd().getText();
        if (password != null) {
            //获得系统剪切板
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            //复制到剪切板上
            StringSelection ss = new StringSelection(password);
            clipboard.setContents(ss, null);
//            JOptionPane.showMessageDialog(null,"复制到剪切板操作成功");
        }
    }


    //检查JTextField中的内容，返回true则表示检查通过
    private boolean checkTextFiled(String filename, String name, String keyWord) {
        if (filename.equals("")) {
            JOptionPane.showMessageDialog(null, "网站/网址为空，请确认");
            return false;
        } else if (name.equals("")) {
            JOptionPane.showMessageDialog(null, "用户名为空，请确认");
            return false;
        } else if (keyWord.equals("")) {
            JOptionPane.showMessageDialog(null, "关键字为空，请确认");
            return false;
        } else return true;
    }

}
