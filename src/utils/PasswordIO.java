package utils;

import Frame.MainFrame;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Created by ht on 2016/2/20.
 */
public class PasswordIO {

    private static final int CONTAIN_WORDS = 1;
    private static final int CONTAIN_NUMBERS = 2;
    private static final int CONTAIN_SPECIAL_CHARS = 3;
    private static final int READ_BY_FILE = 4;

    public static void writePasswd(String filename, String name, String keyWord, MainFrame mainFrame) {

        int[] ways = wayOfWrite(mainFrame);

        if (ways[3] == READ_BY_FILE) {
            writeUseFileWay(filename, name, keyWord, ways, mainFrame);
        } else {
            writeUseBinaryWay(filename, name, keyWord, ways,mainFrame);
        }
    }

    private static void writeUseBinaryWay(String filename, String name, String keyWord, int[] ways, MainFrame mainFrame) {
        DataOutputStream os = null;
        try {
            String fileDirPath = mainFrame.getFileDirPath();
            File file = new File(fileDirPath + "/" + filename + ".w");

            os = new DataOutputStream(new FileOutputStream(file));
            os.writeChars(name);
            os.writeChar(' ');
            os.writeChars(keyWord);
            os.writeChar(' ');
            os.writeChars(generatePassWord(keyWord, ways));
            JOptionPane.showMessageDialog(null, "数据写入成功！");
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "目标文件不存在，请检查");
            e1.printStackTrace();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "数据写入异常，请重试");
            e1.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void writeUseFileWay(String filename, String name, String keyWord, int[] ways, MainFrame mainFrame) {
        DataOutputStream os = null;
        try {
            String fileDirPath = mainFrame.getFileDirPath();
            File file = new File(fileDirPath + "/" + filename + ".wf");

            os = new DataOutputStream(new FileOutputStream(file));
            os.writeUTF(name);
            os.writeUTF(keyWord);
            os.writeUTF(generatePassWord(keyWord, ways));
            JOptionPane.showMessageDialog(null, "数据写入成功！");
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "目标文件不存在，请检查");
            e1.printStackTrace();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "数据写入异常，请重试");
            e1.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static String[] readWFPasswd(File file) {
        String[] strings = new String[3];
        DataInputStream is = null;
        try {
            is = new DataInputStream(new FileInputStream(file));
            strings[0] = is.readUTF();
            strings[1] = is.readUTF();
            strings[2] = is.readUTF();
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "密码文件不存在");
            e1.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "密码数据读取出错");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strings;
    }

    public static String[] readWPasswd(File file) {
        String[] strings = new String[3];
        DataInputStream is = null;
        try {
            is = new DataInputStream(new FileInputStream(file));
            List<Character> lists = new ArrayList<>();
            while (is.readChar() != -1) {
                char c = is.readChar();
                lists.add(c);
            }
            String allString = lists.toString();
            strings = allString.split("\n");
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "密码文件不存在");
            e1.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "密码数据读取出错");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strings;
    }

    private static int[] wayOfWrite(MainFrame mainFrame) {
        int[] cbStatus = new int[4];

        boolean cbWord = mainFrame.getJcbWord().isSelected();
        boolean cbNum = mainFrame.getJcbNumber().isSelected();
        boolean cbSpecialChar = mainFrame.getJcbSpecialChar().isSelected();
        boolean cbFile = mainFrame.getJcbFile().isSelected();

        cbStatus[0] = cbWord ? CONTAIN_WORDS : 0;
        cbStatus[1] = cbNum ? CONTAIN_NUMBERS : 0;
        cbStatus[2] = cbSpecialChar ? CONTAIN_SPECIAL_CHARS : 0;
        cbStatus[3] = cbFile ? READ_BY_FILE : 0;

        return cbStatus;
    }

    private static String generatePassWord(String keyWord, int[] mainFrame) {

        return keyWord;
    }

}
