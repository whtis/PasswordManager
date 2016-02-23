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
        int[] ways = wayOfWrite(mainFrame);

        if (e.getSource() == mainFrame.getJbtGenerate()) {
            generate(e, fileDirPath, ways);
            updateJList(fileDirPath);
        } else if (e.getSource() == mainFrame.getJbtCheck()) {
            check(e, fileDirPath);
        } else if (e.getSource() == mainFrame.getJbtUpdate()) {
            update(e, fileDirPath, ways);
        } else if (e.getSource() == mainFrame.getJbtDelete()) {
            delete(e, fileDirPath);
            updateJList(fileDirPath);
        } else if (e.getSource() == mainFrame.getJbtCopy()) {
            copy(e);
        } else if (e.getSource() == mainFrame.getJbtRefresh()) {
            refreshMainFrame();
        }
    }

    private void refreshMainFrame() {
        mainFrame.getJtfName().setText("");
        mainFrame.getJtfFileName().setText("");
        mainFrame.getJtfKeyword().setText("");
        mainFrame.getJtfPasswd().setText("");
        mainFrame.getJcbWord().setSelected(true);
        mainFrame.getJcbNumber().setSelected(true);
        mainFrame.getJcbSpecialChar().setSelected(false);
        mainFrame.getJcbFile().setSelected(false);
    }

    private void generate(ActionEvent e, String fileDirPath, int[] ways) {

        //获取将要生成文件的信息
        String filename = mainFrame.getJtfFileName().getText();
        String name = mainFrame.getJtfName().getText();
        String keyWord = mainFrame.getJtfKeyword().getText();

        boolean fileOfNew = false;
        if (ways[3] == PasswordIO.READ_BY_FILE) {
            File file = new File(fileDirPath + "/" + filename + ".wf");
            if (file.exists()) {
                fileOfNew = checkFile(name, keyWord, file, ways, "wf");
            } else {
                PasswordIO.writePasswd(filename, name, keyWord, ways, mainFrame);
            }
        } else {
            File binaryFile = new File(fileDirPath + "/" + filename + ".w");
            if (binaryFile.exists()) {
                fileOfNew = checkFile(name, keyWord, binaryFile, ways, "w");
            } else {
                PasswordIO.writePasswd(filename, name, keyWord, ways, mainFrame);
            }
        }
        if (fileOfNew) {
            int comfirmInt = JOptionPane.showConfirmDialog(null, "该文件已存在，是否进行更新操作？");
            if (comfirmInt == JOptionPane.YES_OPTION) {
                update(e, fileDirPath, ways);
            }
        }
    }

    private void check(ActionEvent e, String fileDirPath) {
        String filename = mainFrame.getJtfFileName().getText();
        if (filename.equals("")) {
            JOptionPane.showMessageDialog(null, "网站/网址为空，请确认");
        } else {
            File binaryFile = new File(fileDirPath + "/" + filename + ".w");
            File file = new File(fileDirPath + "/" + filename + ".wf");
            String[] result = new String[3];
            if (binaryFile.exists()) {
                result = PasswordIO.readBinaryPassed(binaryFile);
            } else if (file.exists()) {
                result = PasswordIO.readFilePasswd(file);
            } else {
                JOptionPane.showMessageDialog(null, "该帐号不存在，请检查");
            }
            if (result.length == 3) {
                PasswordIO.showContent(result, mainFrame);
            }
        }
    }

    private void update(ActionEvent e, String fileDirPath, int[] ways) {
        String filename = mainFrame.getJtfFileName().getText();
        String name = mainFrame.getJtfName().getText();
        String keyWord = mainFrame.getJtfKeyword().getText();

        if (checkTextFiled(filename, name, keyWord)) {
            File binaryFile = new File(fileDirPath + "/" + filename + ".w");
            File file = new File(fileDirPath + "/" + filename + ".wf");
            if (binaryFile.exists()) {
                if (!checkFile(filename, name, binaryFile, ways, "w")) {
                    PasswordIO.writePasswd(filename, name, keyWord, ways, mainFrame);
                    JOptionPane.showMessageDialog(null,"数据更新成功！");
                } else {
                    JOptionPane.showMessageDialog(null, "数据重复，无需修改");
                }
            } else if (file.exists()) {
                if (!checkFile(filename, name, file, ways, "wf")) {
                    PasswordIO.writePasswd(filename, name, keyWord, ways, mainFrame);
                    JOptionPane.showMessageDialog(null, "数据更新成功！");
                } else {
                    JOptionPane.showMessageDialog(null, "数据重复，无需修改");
                }
            } else {
                JOptionPane.showMessageDialog(null, "该帐号不存在，请检查");
            }
        }
    }

    private void delete(ActionEvent e, String fileDirPath) {
        String filename = mainFrame.getJtfFileName().getText();
        String name = mainFrame.getJtfName().getText();
        String keyWord = mainFrame.getJtfKeyword().getText();
        if (checkTextFiled(filename, name, keyWord)) {
            File file = new File(fileDirPath + "/" + filename + ".w");
            File binaryFile = new File(fileDirPath + "/" + filename + ".wf");
            if (file.exists() || binaryFile.exists()) {
                if (file.delete()) {
                    JOptionPane.showMessageDialog(null, "文件删除成功");

                } else if (binaryFile.delete()) {
                    JOptionPane.showMessageDialog(null, "文件删除成功");
                } else {
                    JOptionPane.showMessageDialog(null, "文件删除出错，请重试");
                }
            } else {
                JOptionPane.showMessageDialog(null, "该帐号不存在，请检查");
            }
            mainFrame.getJtfFileName().setText("");
            mainFrame.getJtfName().setText("");
            mainFrame.getJtfKeyword().setText("");
            mainFrame.getJtfPasswd().setText("");
            mainFrame.getJcbWord().setSelected(true);
            mainFrame.getJcbNumber().setSelected(true);
            mainFrame.getJcbSpecialChar().setSelected(false);
            mainFrame.getJcbFile().setSelected(false);

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

    private boolean checkFile(String filename, String name, File file, int[] ways, String s) {
        String[] results = new String[3];
        if (s.equals("w")) {
            results = PasswordIO.readBinaryPassed(file);
        } else if (s.equals("wf")) {
            results = PasswordIO.readFilePasswd(file);
        }
        String sWays = results[2].substring(results[2].length() - 4, results[2].length());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ways.length; i++) {
            sb.append(ways[i]);
        }
        if (sb.toString().equals(sWays) && filename.equals(results[0]) && name.equals(results[1])) {
            return false;
        } else {
            return true;
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

    private static int[] wayOfWrite(MainFrame mainFrame) {
        int[] cbStatus = new int[4];

        boolean cbWord = mainFrame.getJcbWord().isSelected();
        boolean cbNum = mainFrame.getJcbNumber().isSelected();
        boolean cbSpecialChar = mainFrame.getJcbSpecialChar().isSelected();
        boolean cbFile = mainFrame.getJcbFile().isSelected();

        cbStatus[0] = cbWord ? PasswordIO.CONTAIN_WORDS : 0;
        cbStatus[1] = cbNum ? PasswordIO.CONTAIN_NUMBERS : 0;
        cbStatus[2] = cbSpecialChar ? PasswordIO.CONTAIN_SPECIAL_CHARS : 0;
        cbStatus[3] = cbFile ? PasswordIO.READ_BY_FILE : 0;

        return cbStatus;
    }

    private void updateJList(String fileDirPath) {
        String[] files = new File(fileDirPath).list();
        FileModel fileModel = new FileModel(files);
        mainFrame.getJlFileName().setModel(fileModel);
        mainFrame.getJlFileName().updateUI();
    }
}
