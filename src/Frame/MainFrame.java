package Frame;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import ActionListener.*;

/**
 * Created by ht on 2016/2/20.
 */
public class MainFrame extends JFrame {

    private String fileDirPath;

    private JLabel jFileName = new JLabel("网站/网址");
    private JLabel jName = new JLabel("用户名");
    private JLabel jKeyword = new JLabel("关键字");

    private JTextField jtfFileName;
    private JTextField jtfName;
    private JTextField jtfKeyword;

    private JCheckBox jcbWord;
    private JCheckBox jcbNumber;
    private JCheckBox jcbSpecialChar;
    private JCheckBox jcbFile;

    private JButton jbtGenerate;
    private JButton jbtCheck;
    private JButton jbtUpdate;
    private JButton jbtDelete;

    private JList jlFileName;

    private JTextField jtfPasswd;
    private JButton jbtCopy;

    private JTextFieldActionListener jTextFieldActionListener = new JTextFieldActionListener(this);
    private JCheckBoxActionListener jCheckBoxActionListener = new JCheckBoxActionListener(this);
    private JButtonActionListener jButtonActionListener = new JButtonActionListener(this);
    private JListSelectionListener jListActionListener = new JListSelectionListener(this);

    public MainFrame() throws HeadlessException {

        jtfFileName = new JTextField(30);
        jtfName = new JTextField(30);
        jtfKeyword = new JTextField(30);

        JPanel jpMessageGetPanel = new JPanel(new BorderLayout());
        jpMessageGetPanel.setBorder(new TitledBorder("信息采集"));
        JPanel p1 = new JPanel(new GridLayout(3, 1));
        p1.add(jFileName);
        p1.add(jName);
        p1.add(jKeyword);
        JPanel p2 = new JPanel(new GridLayout(3, 1));
        p2.setBorder(new BevelBorder(BevelBorder.RAISED));
        p2.add(jtfFileName);
        p2.add(jtfName);
        p2.add(jtfKeyword);
        jpMessageGetPanel.add(p1, BorderLayout.WEST);
        jpMessageGetPanel.add(p2, BorderLayout.CENTER);

        jcbWord = new JCheckBox("含大小写");
        jcbWord.setSelected(true);
        jcbNumber = new JCheckBox("含数字");
        jcbNumber.setSelected(true);
        jcbSpecialChar = new JCheckBox("含特殊字符");
        jcbFile = new JCheckBox("文本方式可读");
        JPanel jpCondition = new JPanel(new GridLayout(4, 1));
        jpCondition.setBorder(new TitledBorder("密码条件"));
        jpCondition.add(jcbWord);
        jpCondition.add(jcbNumber);
        jpCondition.add(jcbSpecialChar);
        jpCondition.add(jcbFile);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(jpMessageGetPanel, BorderLayout.CENTER);
        northPanel.add(jpCondition, BorderLayout.EAST);

        jbtGenerate = new JButton("生成");
        jbtCheck = new JButton("查看");
        jbtUpdate = new JButton("更新");
        jbtDelete = new JButton("删除");
        JPanel jpPasswdFun = new JPanel(new FlowLayout());
        jpPasswdFun.setBorder(new TitledBorder("密码操作区"));
        jpPasswdFun.add(jbtGenerate);
        jpPasswdFun.add(jbtCheck);
        jpPasswdFun.add(jbtUpdate);
        jpPasswdFun.add(jbtDelete);

        jlFileName = new JList();
        jlFileName.setBackground(Color.CYAN);
        jlFileName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(jlFileName);
        JPanel jpPasswdList = new JPanel(new BorderLayout());
        jpPasswdList.add(scrollPane, BorderLayout.CENTER);

        jtfPasswd = new JTextField(40);
        jbtCopy = new JButton("复制");
        JPanel jpShowPasswd = new JPanel(new FlowLayout());
        jpShowPasswd.add(jtfPasswd);
        jpShowPasswd.add(jbtCopy);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(jpPasswdFun, BorderLayout.NORTH);
        southPanel.add(jpPasswdList, BorderLayout.CENTER);
        southPanel.add(jpShowPasswd, BorderLayout.SOUTH);

        //给JTextField对象添加监听器
        jtfFileName.addActionListener(jTextFieldActionListener);
        jtfName.addActionListener(jTextFieldActionListener);
        jtfKeyword.addActionListener(jTextFieldActionListener);
        jtfPasswd.addActionListener(jTextFieldActionListener);

        //给JCheckBox对象添加监听器
        jcbWord.addActionListener(jCheckBoxActionListener);
        jcbNumber.addActionListener(jCheckBoxActionListener);
        jcbSpecialChar.addActionListener(jCheckBoxActionListener);
        jcbFile.addActionListener(jCheckBoxActionListener);

        //给JButton对象添加监听器
        jbtGenerate.addActionListener(jButtonActionListener);
        jbtCheck.addActionListener(jButtonActionListener);
        jbtUpdate.addActionListener(jButtonActionListener);
        jbtDelete.addActionListener(jButtonActionListener);
        jbtCopy.addActionListener(jButtonActionListener);

        //给JList对象添加监听器
        jlFileName.addListSelectionListener(jListActionListener);

        this.setLayout(new BorderLayout());
        this.getContentPane().add(northPanel, BorderLayout.NORTH);
        this.getContentPane().add(southPanel, BorderLayout.SOUTH);
    }

    public JTextField getJtfFileName() {
        return jtfFileName;
    }

    public void setJtfFileName(JTextField jtfFileName) {
        this.jtfFileName = jtfFileName;
    }

    public JTextField getJtfName() {
        return jtfName;
    }

    public void setJtfName(JTextField jtfName) {
        this.jtfName = jtfName;
    }

    public JTextField getJtfKeyword() {
        return jtfKeyword;
    }

    public void setJtfKeyword(JTextField jtfKeyword) {
        this.jtfKeyword = jtfKeyword;
    }

    public JCheckBox getJcbWord() {
        return jcbWord;
    }

    public void setJcbWord(JCheckBox jcbWord) {
        this.jcbWord = jcbWord;
    }

    public JCheckBox getJcbNumber() {
        return jcbNumber;
    }

    public void setJcbNumber(JCheckBox jcbNumber) {
        this.jcbNumber = jcbNumber;
    }

    public JCheckBox getJcbSpecialChar() {
        return jcbSpecialChar;
    }

    public void setJcbSpecialChar(JCheckBox jcbSpecialChar) {
        this.jcbSpecialChar = jcbSpecialChar;
    }

    public JCheckBox getJcbFile() {
        return jcbFile;
    }

    public void setJcbFile(JCheckBox jcbFile) {
        this.jcbFile = jcbFile;
    }

    public JButton getJbtGenerate() {
        return jbtGenerate;
    }

    public void setJbtGenerate(JButton jbtGenerate) {
        this.jbtGenerate = jbtGenerate;
    }

    public JButton getJbtCheck() {
        return jbtCheck;
    }

    public void setJbtCheck(JButton jbtCheck) {
        this.jbtCheck = jbtCheck;
    }

    public JButton getJbtUpdate() {
        return jbtUpdate;
    }

    public void setJbtUpdate(JButton jbtUpdate) {
        this.jbtUpdate = jbtUpdate;
    }

    public JButton getJbtDelete() {
        return jbtDelete;
    }

    public void setJbtDelete(JButton jbtDelete) {
        this.jbtDelete = jbtDelete;
    }

    public JList getJlFileName() {
        return jlFileName;
    }

    public void setJlFileName(JList jlFileName) {
        this.jlFileName = jlFileName;
    }

    public JTextField getJtfPasswd() {
        return jtfPasswd;
    }

    public void setJtfPasswd(JTextField jtfPasswd) {
        this.jtfPasswd = jtfPasswd;
    }

    public JButton getJbtCopy() {
        return jbtCopy;
    }

    public void setJbtCopy(JButton jbtCopy) {
        this.jbtCopy = jbtCopy;
    }

    public String getFileDirPath() {
        return fileDirPath;
    }

    public void setFileDirPath(String fileDirPath) {
        this.fileDirPath = fileDirPath;
    }

}
