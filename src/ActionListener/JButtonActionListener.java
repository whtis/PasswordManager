package ActionListener;

import Frame.*;
import com.sun.org.apache.xpath.internal.compiler.Keywords;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

/**
 * Created by ht on 2016/2/20.
 *这是监听各个按钮的类
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
            if (mainFrame.getJcbAutoFresh().isSelected()) {
                refreshMainFrame();
            }
        } else if (e.getSource() == mainFrame.getJbtCheck()) {
            check(e, fileDirPath,ways);
        } else if (e.getSource() == mainFrame.getJbtUpdate()) {
            update(e, fileDirPath, ways);
        } else if (e.getSource() == mainFrame.getJbtDelete()) {
            delete(e, fileDirPath);
            updateJList(fileDirPath);
        } else if (e.getSource() == mainFrame.getJbtCopy()) {
            copy(e,mainFrame.getJtfPasswd().getText());
        } else if (e.getSource() == mainFrame.getJbtRefresh()) {
            refreshMainFrame();
        } else if (e.getSource() == mainFrame.getJbtMD5()) {
            copy(e,mainFrame.getJtfMD5().getText());
        } else if (e.getSource() == mainFrame.getJtfKeyword()) {
            generate(e, fileDirPath, ways);
            updateJList(fileDirPath);
        }
    }

    /*刷新主界面的方法*/
    private void refreshMainFrame() {
        mainFrame.getJtfName().setText("");
        mainFrame.getJtfFileName().setText("");
        mainFrame.getJtfKeyword().setText("");
        mainFrame.getJtfPasswd().setText("");
        mainFrame.getJtfMD5().setText("");
        mainFrame.getJcbWord().setSelected(true);
        mainFrame.getJcbNumber().setSelected(true);
        mainFrame.getJcbSpecialChar().setSelected(false);
        mainFrame.getJcbFile().setSelected(false);
        updateJList(mainFrame.getFileDirPath());
    }

    /*
    该方法生成密码并显示在主界面上
     */
    private void generate(ActionEvent e, String fileDirPath, int[] ways) {

        //获取将要生成文件的信息
        String filename = mainFrame.getJtfFileName().getText();
        String name = mainFrame.getJtfName().getText();
        String keyWord = mainFrame.getJtfKeyword().getText();

        if (!checkTextFiled(filename, name, keyWord)) {
            return;
        }
        boolean fileOfNew = false;
        if (ways[3] == PasswordIO.READ_BY_FILE) {
            File file = new File(fileDirPath + "/" + filename + ".wf");
            if (file.exists()) {
                fileOfNew = checkFile(name, keyWord, file, ways, "wf");
            } else {
                PasswordIO.writeUseFileWay(filename, name, keyWord, ways, mainFrame);
            }
        } else {
            File binaryFile = new File(fileDirPath + "/" + filename + ".w");
            if (binaryFile.exists()) {
                fileOfNew = checkFile(name, keyWord, binaryFile, ways, "w");
            } else {
                PasswordIO.writeUseBinaryWay(filename, name, keyWord, ways, mainFrame);
            }
        }
        if (fileOfNew) {
            int comfirmInt = JOptionPane.showConfirmDialog(null, "该文件已存在，是否进行更新操作？");
            if (comfirmInt == JOptionPane.YES_OPTION) {
                update(e, fileDirPath, ways);
            }
        }
        //生成密码后刷新页面，方便继续写下一个
        if (mainFrame.getJcbAutoFresh().isSelected()) {
            refreshMainFrame();
        }

    }

    /*
    该方法用于查看密码文件
     */
    private void check(ActionEvent e, String fileDirPath, int[] ways) {
        String filename = mainFrame.getJtfFileName().getText();
        if (filename.equals("")) {
            JOptionPane.showMessageDialog(null, "网站/网址为空，请确认");
        } else {
            File binaryFile = new File(fileDirPath + "/" + filename + ".w");
            File file = new File(fileDirPath + "/" + filename + ".wf");
            String[] result = new String[3];
            if (binaryFile.exists() && !file.exists()) {
                result = PasswordIO.readBinaryPassed(binaryFile);
            } else if (file.exists() && !binaryFile.exists()) {
                result = PasswordIO.readFilePasswd(file);
            } else if (file.exists() && binaryFile.exists()) {
                if (ways[3] == PasswordIO.READ_BY_FILE) {
                    result = PasswordIO.readFilePasswd(file);
                } else {
                    result = PasswordIO.readBinaryPassed(binaryFile);
                }
            } else {
                JOptionPane.showMessageDialog(null, "该帐号不存在，请检查");
            }
            if (result.length == 3) {
                PasswordIO.showContent(result, mainFrame);
            }
        }
    }

    /*
    该方法用于更新密码文件
     */
    private void update(ActionEvent e, String fileDirPath, int[] ways) {
        String filename = mainFrame.getJtfFileName().getText();
        String name = mainFrame.getJtfName().getText();
        String keyWord = mainFrame.getJtfKeyword().getText();

        if (checkTextFiled(filename, name, keyWord)) {
            File binaryFile = new File(fileDirPath + "/" + filename + ".w");
            File file = new File(fileDirPath + "/" + filename + ".wf");
            if (binaryFile.exists() && !file.exists()) {
                if (checkFile(name, keyWord, binaryFile, ways, "w")) {
                    boolean b = PasswordIO.writeUseBinaryWay(filename, name, keyWord, ways, mainFrame);
                    if (b) {
                        JOptionPane.showMessageDialog(null, "数据更新成功！");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "数据重复，无需修改");
                }
            } else if (file.exists() && !binaryFile.exists()) {
                if (checkFile(name, keyWord, file, ways, "wf")) {
                    boolean b = PasswordIO.writeUseFileWay(filename, name, keyWord, ways, mainFrame);
                    if (b) {
                        JOptionPane.showMessageDialog(null, "数据更新成功！");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "数据重复，无需修改");
                }
            } else if (file.exists() && binaryFile.exists()) {
                if (ways[3] == PasswordIO.READ_BY_FILE) {
                    if (checkFile(name, keyWord, file, ways, "wf")) {
                        boolean b = PasswordIO.writeUseFileWay(filename, name, keyWord, ways, mainFrame);
                        if (b) {
                            JOptionPane.showMessageDialog(null, "数据更新成功！");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "数据重复，无需修改");
                    }
                } else {
                    if (checkFile(name, keyWord, binaryFile, ways, "w")) {
                        boolean b = PasswordIO.writeUseBinaryWay(filename, name, keyWord, ways, mainFrame);
                        if (b) {
                            JOptionPane.showMessageDialog(null, "数据更新成功！");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "数据重复，无需修改");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "该帐号不存在，请检查");
            }
        }
    }

    /*
    该方法用于删除密码文件
     */
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
            mainFrame.getJtfMD5().setText("");
            mainFrame.getJcbWord().setSelected(true);
            mainFrame.getJcbNumber().setSelected(true);
            mainFrame.getJcbSpecialChar().setSelected(false);
            mainFrame.getJcbFile().setSelected(false);

        }
    }

    /*
    该方法将指定的字符串复制到系统剪切板上
     */
    private void copy(ActionEvent e, String password) {
        if (password != null) {
            //获得系统剪切板
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            //复制到剪切板上
            StringSelection ss = new StringSelection(password);
            clipboard.setContents(ss, null);
//            JOptionPane.showMessageDialog(null,"复制到剪切板操作成功");
        }
    }

    /*
    该方法JButtonActionListener的私有辅助方法，用来比对主界面上的内容是否和密码文件一样，以决定是否需要进行
    更新操作
     */
    private boolean checkFile(String name, String keyword, File file, int[] ways, String s) {
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
        if (sb.toString().equals(sWays) && name.equals(results[0]) && keyword.equals(results[1])) {
            return false;
        } else {
            return true;
        }
    }

    /*
        该方法是JButtonActionListener的私有辅助方法，用于检查网站名、用户名和关键字是否存在
     */
    //检查JTextField中的内容，返回true则表示检查通过
    private boolean checkTextFiled(String filename, String name, String keyWord) {
        if (filename.equals("")) {
            JOptionPane.showMessageDialog(null, "网站/网址为空，请确认");
            mainFrame.getJlFileName().requestFocus();
            return false;
        } else if (name.equals("")) {
            JOptionPane.showMessageDialog(null, "用户名为空，请确认");
            mainFrame.getJtfName().requestFocus();
            return false;
        } else if (keyWord.equals("")) {
            JOptionPane.showMessageDialog(null, "关键字为空，请确认");
            mainFrame.getJtfKeyword().requestFocus();
            return false;
        } else return true;
    }

    /*
        该方法是JButtonActionListener的私有辅助方法，用于获取主界面上JCheckBox的内容
     */
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

    /*
    该方法是JButtonActionListener的私有辅助方法，用于检测JList区域的变化，如果JList中文件有改动，则
    执行该方法
     */
    private void updateJList(String fileDirPath) {
        String[] files = new File(fileDirPath).list();
        StringBuilder sb = new StringBuilder();
        //排除其他文件
        for (int i = 0; i < files.length; i++) {
            if (files[i].endsWith(".w") || files[i].endsWith("wf")) {
                sb.append(files[i] + "\n");
            }
        }
        String[] pmFiles = sb.toString().split("\n");
        FileModel fileModel = new FileModel(pmFiles);
        mainFrame.getJlFileName().setModel(fileModel);
        mainFrame.getJlFileName().updateUI();
    }
}
