package utils;

import Frame.MainFrame;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;

/**
 * Created by ht on 2016/2/20.
 */
public class PasswordIO {
    public static final int CONTAIN_WORDS = 1; //65~122
    public static final int CONTAIN_NUMBERS = 2; //48~57
    public static final int CONTAIN_SPECIAL_CHARS = 3; //33~47、58~64、91~96、123~126
    public static final int READ_BY_FILE = 4;

    public static void writePasswd(String filename, String name, String keyWord, int[] ways, MainFrame mainFrame) {


        if (ways[3] == READ_BY_FILE) {
            writeUseFileWay(filename, name, keyWord, ways, mainFrame);
        } else {
            writeUseBinaryWay(filename, name, keyWord, ways, mainFrame);
        }
    }

    private static void writeUseBinaryWay(String filename, String name, String keyWord, int[] ways, MainFrame mainFrame) {
        if (filename.equals("")) {
            return;
        }
        DataOutputStream os = null;
        try {
            String fileDirPath = mainFrame.getFileDirPath();
            File file = new File(fileDirPath + "/" + filename + ".w");

            String password = generatePassWord(keyWord, ways);
            String info = name + "\n" + keyWord + "\n" + password;

            os = new DataOutputStream(new FileOutputStream(file));
            os.writeChars(info);
            //生成密码的同时显示在密码框中，密码不显示表示JcheckBox状态的四位数字
            mainFrame.getJtfPasswd().setText(password.substring(0, password.length() - 4));

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

    public static String[] readBinaryPassed(File file) {
        String[] strings = new String[3];
        DataInputStream is = null;
        try {
            is = new DataInputStream(new FileInputStream(file));
            StringBuffer sb = new StringBuffer();
            while (is.available() != 0) {
                char c = is.readChar();
                sb.append(c);
            }
            strings = sb.toString().split("\n");
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

    private static void writeUseFileWay(String filename, String name, String keyWord, int[] ways, MainFrame mainFrame) {
        if (filename.equals("")) {
            return;
        }
        DataOutputStream os = null;
        try {
            String fileDirPath = mainFrame.getFileDirPath();
            File file = new File(fileDirPath + "/" + filename + ".wf");

            os = new DataOutputStream(new FileOutputStream(file));
            os.writeUTF(name);
            os.writeUTF(keyWord);
            String password = generatePassWord(keyWord, ways);
            os.writeUTF(password);

            //生成密码的同时显示在密码框中，密码不显示表示JcheckBox状态的四位数字
            mainFrame.getJtfPasswd().setText(password.substring(0, password.length() - 4));

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

    public static String[] readFilePasswd(File file) {
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


    private static String generatePassWord(String keyWord, int[] ways) {
        StringBuilder key = new StringBuilder();
        if (ways[0] == CONTAIN_WORDS) {
            char[] chars = keyWord.toCharArray();
            for (char aChar : chars) {
                key.append(generateChar(aChar, 65, 122, 6, 30));
            }
        }
        if (ways[1] == CONTAIN_NUMBERS) {
            char[] chars = keyWord.toCharArray();
            for (char aChar : chars) {
                key.append(generateChar(aChar, 48, 57, 8, 3));
            }
        }
        if (ways[2] == CONTAIN_SPECIAL_CHARS) {
            //33 ~47、58 ~64、91 ~96、123 ~126
            /*由于使用原始关键字生成特殊字符会出现递归次数过多从而出现java.lang.StackOverflowError
            因此采用生成后的字母和数字生成特殊字符*/
            char[] chars = key.toString().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (i % 3 == 0) {
                    key.append(generateChar(chars[i], 58, 64, 2, 2));
                    continue;
                }
                if (i % 5 == 0) {
                    key.append(generateChar(chars[i], 91, 96, 2, 2));
                    continue;
                }
                if (i % 7 == 0) {
                    key.append(generateChar(chars[i], 123, 126, 2, 2));
                    continue;
                }
                key.append(generateChar(chars[i], 33, 47, 2, 2));
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ways.length; i++) {
            stringBuilder.append(ways[i]);
        }
        key.append(stringBuilder.toString());
        return key.toString();
    }

    //为了防止特殊字符的递归太深，使用三个变量进行控制
    //cont是首次改变，lowIncrease是递增量，highDecrease是递减量

    private static char generateChar(char aChar, int low, int high, int lowIncrease, int highDecrease) {

        char c = 'A';
        int a = aChar;
        if (a >= low && a <= high) {
            c = (char) a;
            return c;
        } else if (a < low) {
            return generateChar((char) (a + lowIncrease), low, high, lowIncrease, highDecrease);
        } else if (a > high) {
            return generateChar((char) (a - highDecrease), low, high, lowIncrease, highDecrease);
        }
        return c;
    }


    public static void showContent(String[] result, MainFrame mainFrame) {
        mainFrame.getJtfName().setText(result[0]);
        mainFrame.getJtfKeyword().setText(result[1]);
        String allKey = result[2];
        String key = allKey.substring(0, allKey.length() - 4);
        mainFrame.getJtfPasswd().setText(key);

        String s = allKey.substring(allKey.length() - 4, allKey.length());
        char[] chars = s.toCharArray();
        if (chars[0] == '1') {
            mainFrame.getJcbWord().setSelected(true);
        } else {
            mainFrame.getJcbWord().setSelected(false);
        }
        if (chars[1] == '2') {
            mainFrame.getJcbNumber().setSelected(true);
        } else {
            mainFrame.getJcbNumber().setSelected(false);
        }
        if (chars[2] == '3') {
            mainFrame.getJcbSpecialChar().setSelected(true);
        } else {
            mainFrame.getJcbSpecialChar().setSelected(false);
        }
        if (chars[3] == '4') {
            mainFrame.getJcbFile().setSelected(true);
        } else {
            mainFrame.getJcbFile().setSelected(false);
        }
    }
}