package utils;

import javax.swing.*;
import java.io.*;

/**
 * Created by ht on 2016/2/20.
 */
public class PasswordIO {
    public static void writePasswd(File file,String name,String keyWord) {
        DataOutputStream os = null;
        try {
            os = new DataOutputStream(new FileOutputStream(file));
            os.writeUTF(name);
            os.writeUTF(keyWord);
            os.writeUTF(generatePassWord(keyWord));
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

    public static String[] readPasswd(File file) {
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
            JOptionPane.showMessageDialog(null,"密码数据读取出错");
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

    private static String generatePassWord(String keyWord) {
        return keyWord;
    }

}
