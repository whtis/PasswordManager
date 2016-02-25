import sun.misc.*;

import java.io.*;
import java.util.regex.*;

/**
 * Created by ht on 2016/2/24.
 */
public class test {
    public static void main(String[] args) throws IOException {
        TransformBS transformBS = new TransformBS();
        transformBS.testB2S();
        transformBS.testS2B();
    }
}

/**
 * 今天在网上看到一个程序游戏，只要目的是为了能够将文件中的01二进制数据读取到程序然后通过base64解码，再转换成程序
 * 所以，自己写了个程序：（1）将一段字符串进行base64处理，然后转换成二进制输出。（2）将一段二进制数据转换成字符串，然后base64解码到对应的字符串
 */
class TransformBS {
    /**
     * @ see 字符串进行base64编码后转换为二进制形式,如：（h(原字符)->a(编码后)->01100001010000010011110100111101(二进制形式)）
     */
    public void testS2B() throws IOException {
        File file = new File("test.w");
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));

        BASE64Encoder e = new BASE64Encoder();
        String name = "whtis";
        String keyword = "hehe";
        String password = e.encode(keyword.getBytes());

        String info = name + "\n" + keyword + "\n" + password;
        for (char c : info.toCharArray()) {
//单个字符转换成的二进制字符串
            String binaryStr = Integer.toBinaryString(c);
            String format = String.format("%8s", binaryStr);
//因为上面转换成二进制后的位数不够8位所以不足的前面补空格，这里是考虑到能够从数据文件批量读取。
//高位空格替换成0，其实编码后的数据最大范围为2的6次方，首位一定是空格，不然就要用format.startWith(" ");来判断
            format = format.replace(" ", "0");
//输出
            dos.writeUTF(format);
        }
        dos.flush();
        dos.close();
        System.out.println("\n=========字符串到二进制结束！=============");
    }

    /**
     * @ see 二进制形式转换为字符串后进行base64解码的字符串如：(01100001010000010011110100111101->a->h)
     */
    public void testB2S() throws IOException {
        StringBuilder sb = new StringBuilder();
        StringBuilder results = new StringBuilder();
//保存尚未解码的数据结果
        DataInputStream dis = new DataInputStream(new FileInputStream("test.w"));
        while (dis.available() != 0) {
            sb.append(dis.readUTF());
        }
        String binaryStr = sb.toString();
//二进制数据，这里是取用上面程序的最后结果
        System.out.println("二进制数据：" + binaryStr);
//这里采用正则表达式来匹配8位长度的数据，然后一个个find()
        Matcher matcher = Pattern.compile("\\d{8}").matcher(binaryStr);
//定义匹配模式并，获取模式
        BASE64Decoder d = new BASE64Decoder();
//解码器
        while (matcher.find()) {
//在binaryStr中找到了8位长度的数据，依次往后面找
//matcher.group()中存储了找到匹配模式的数据，这里以2进制的形式转换为整数
            int intVal = Integer.valueOf(matcher.group(), 2);
//将整数转换为对应的字符，并添加到结果中
            results.append((char) intVal);
        }
        System.out.println("尚未解码的数据：" + results);
//输出尚未解码的数据
        String[] strings = results.toString().split("\n");
        String s = new String(d.decodeBuffer(strings[2].toString()));
//得到解码后的数据
        System.out.println("解码后的数据：" + s);
//输出解码后的数据
        System.out.println("=========二进制到字符串结束！=============");
    }
}