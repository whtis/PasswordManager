package utils;

import Frame.MainFrame;
import sun.misc.BASE64Decoder;

import javax.swing.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.regex.*;

/**
 * Created by ht on 2016/2/20.
 * 该类是个工具类，用于写入和读取密码数据，包含了多种读写数据的方法
 */
public class PasswordIO {

    public static final int CONTAIN_WORDS = 1; //65~122
    public static final int CONTAIN_NUMBERS = 2; //48~57
    public static final int CONTAIN_SPECIAL_CHARS = 3; //33~47、58~64、91~96、123~126
    public static final int READ_BY_FILE = 4;

    /*
    获取md5工具类
     */
    private static MessageDigest md5 = null;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 用于向指定路径写入密码文件，为了保留主界面中所有的信息，采用了如下方法：每个密码文件单独存为一个
     * 文件，文件名就是网站/网址名加相应后缀，后缀取决于密码的写入方式，如果是文本方式写入，后缀为“.wf”,如果
     * 是二进制方式，后缀为“.w”;密码生成之前有四个选项，这四个选项的内容作为常量写入生成的密码最后，但是在显示
     * 给用户时，会屏蔽掉。
     * @param filename 网站、网址名
     * @param name 用户名
     * @param keyWord 关键字
     * @param ways JCheckBox中的内容，密码的写入方式
     * @param mainFrame 主界面对象的一个实例
     */

    /**
     * 用二进制方法读取二进制密码文件
     *
     * @param file 需要读取的二进制密码文件
     * @return
     */
    public static String[] readBinaryPassed(File file) {
        String[] strings = new String[3];
        DataInputStream is = null;
        try {
            is = new DataInputStream(new FileInputStream(file));
            StringBuffer sb = new StringBuffer();
            while (is.available() != 0) {
                sb.append(is.readUTF());
            }
            String binaryStr = sb.toString();
//这里采用正则表达式来匹配8位长度的数据，然后一个个find()
            Matcher matcher = Pattern.compile("\\d{8}").matcher(binaryStr);

            StringBuilder result = new StringBuilder();
            while (matcher.find()) {
//在binaryStr中找到了8位长度的数据，依次往后面找
//matcher.group()中存储了找到匹配模式的数据，这里以2进制的形式转换为整数
                int intVal = Integer.valueOf(matcher.group(), 2);
//将整数转换为对应的字符，并添加到结果中
                result.append((char) intVal);
            }
            strings = result.toString().split("\n");
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

    /**
     * 用二进制方式向指定路径写入密码文件，该方式写入的密码文件后缀为“.w”
     *
     * @param filename
     * @param name
     * @param keyWord
     * @param ways
     * @param mainFrame
     */
    public static boolean writeUseBinaryWay(String filename, String name, String keyWord, int[] ways, MainFrame mainFrame) {
        if (filename.equals("")) {
            return false;
        }
        if (checkGB2312(name)) {
            JOptionPane.showMessageDialog(null, "非文本写入方式不支持用户名为汉字，请确认");
            mainFrame.getJtfName().setText("");
            return false;
        }
        if (checkGB2312(keyWord)) {
            JOptionPane.showMessageDialog(null, "非文本写入方式不支持关键字为汉字，请确认");
            mainFrame.getJtfKeyword().setText("");
            return false;
        }
        DataOutputStream os = null;
        try {
            String fileDirPath = mainFrame.getFileDirPath();
            File file = new File(fileDirPath + "/" + filename + ".w");

            String password = generatePassWord(keyWord, ways);
            String info = name + "\n" + keyWord + "\n" + password;

            os = new DataOutputStream(new FileOutputStream(file));
            for (char c : info.toCharArray()) {
//单个字符转换成的二进制字符串
                String binaryStr = Integer.toBinaryString(c);
                String format = String.format("%8s", binaryStr);
//因为上面转换成二进制后的位数不够8位所以不足的前面补空格，这里是考虑到能够从数据文件批量读取。
//高位空格替换成0，其实编码后的数据最大范围为2的6次方，首位一定是空格，不然就要用format.startWith(" ");来判断
                format = format.replace(" ", "0");
//输出
                os.writeUTF(format);
            }
            //生成密码的同时显示在密码框中，密码不显示表示JcheckBox状态的四位数字
            mainFrame.getJtfPasswd().setText(password.substring(0, password.length() - 4));
            mainFrame.getJtfMD5().setText(generateMD5(password.substring(0, password.length() - 4)));

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
                return false;
            }
        }
        return true;
    }

    /**
     * 用文本方式读取密码文件
     *
     * @param file
     * @return
     */
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

    /**
     * 用文本方式向指定路径写入密码文件，该方式写入的密码文件后缀为“.wf”，可以用普通的文本浏览器打开查看
     *
     * @param filename
     * @param name
     * @param keyWord
     * @param ways
     * @param mainFrame
     */
    public static boolean writeUseFileWay(String filename, String name, String keyWord, int[] ways, MainFrame mainFrame) {
        if (filename.equals("")) {
            return false;
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
            mainFrame.getJtfMD5().setText(generateMD5(password.substring(0, password.length() - 4)));

            JOptionPane.showMessageDialog(null, "数据写入成功！");
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "目标文件不存在，请检查");
            e1.printStackTrace();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "数据写入异常，请重试");
            e1.printStackTrace();
            return false;
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
        return true;
    }

    /*
    程序生成密码的一种方法，该方法根据传入条件的不同生成不同的有规律的密码
     */
    private static String generatePassWord(String keyWord, int[] ways) {
        StringBuilder key = new StringBuilder();
        if (ways[0] == CONTAIN_WORDS) {
            char[] chars = keyWord.toCharArray();
            for (char aChar : chars) {
                int a = aChar;
                if (a >= 65 && a <= 122) {
                    key.append(generateChar((char) (a + 100), 48, 57, 8, 3));
                }
                key.append(generateChar(aChar, 65, 122, 6, 15));
            }
        }
        if (ways[1] == CONTAIN_NUMBERS) {
            char[] chars = keyWord.toCharArray();
            for (char aChar : chars) {
                int a = aChar;
                if (a >= 48 && a <= 57) {
                    key.append(generateChar((char) (a - 8), 48, 57, 8, 3));
                }
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

    /*
    生成密码的辅助方法，生成单个有规律的字符
     */
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

    /*
    生成的密码，如果想查看其MD5的值，调用该方法即可实现
     */
    private static String generateMD5(String password) {
        byte[] bs = md5.digest(password.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

    /**
     * 该方法从密码文件中读取数据后，在主界面上显示出来
     *
     * @param result    密码文件中返回的数据
     * @param mainFrame
     */
    public static void showContent(String[] result, MainFrame mainFrame) {
        mainFrame.getJtfName().setText(result[0]);
        mainFrame.getJtfKeyword().setText(result[1]);
        String allKey = result[2];
        String key = allKey.substring(0, allKey.length() - 4);
        mainFrame.getJtfPasswd().setText(key);
        mainFrame.getJtfMD5().setText(generateMD5(key));

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

    private static boolean checkGB2312(String str) {

        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }
}