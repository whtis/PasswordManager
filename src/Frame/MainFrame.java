package Frame;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import ActionListener.*;

/**
 * Created by ht on 2016/2/20.
 * 该类是主界面，在用户选取了密码存取路径之后，启动该界面。在该界面上，可以进行密码的生成、查看、更新和删除等功能。
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
    private JButton jbtRefresh;
    private JCheckBox jcbAutoFresh;

    private JList jlFileName;

    private JTextField jtfPasswd;
    private JButton jbtCopy;

    private JTextField jtfMD5;
    private JButton jbtMD5;

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
        jbtRefresh = new JButton("刷新");
        jcbAutoFresh = new JCheckBox("生成后刷新");
        JPanel jpPasswdFun = new JPanel(new FlowLayout());
        jpPasswdFun.setBorder(new TitledBorder("密码操作区"));
        jpPasswdFun.add(jbtGenerate);
        jpPasswdFun.add(jbtCheck);
        jpPasswdFun.add(jbtUpdate);
        jpPasswdFun.add(jbtDelete);
        jpPasswdFun.add(jbtRefresh);
        jpPasswdFun.add(jcbAutoFresh);

        jlFileName = new JList();
        jlFileName.setBackground(Color.CYAN);
        jlFileName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(jlFileName);
        JPanel jpPasswdList = new JPanel(new BorderLayout());
        jpPasswdList.setBorder(new TitledBorder("密码列表区"));
        jpPasswdList.add(scrollPane, BorderLayout.NORTH);

        jtfPasswd = new JTextField(40);
        jtfPasswd.setEditable(false);
        jbtCopy = new JButton("复制");
        jtfMD5 = new JTextField(38);
        jtfMD5.setEditable(false);
        jbtMD5 = new JButton("复制MD5");
        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.add(jtfPasswd);
        panel1.add(jbtCopy);
        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.add(jtfMD5);
        panel2.add(jbtMD5);

        JPanel jpShowPasswd = new JPanel(new BorderLayout());
        jpShowPasswd.setBorder(new TitledBorder("密码显示区"));
        jpShowPasswd.add(panel1, BorderLayout.NORTH);
        jpShowPasswd.add(panel2,BorderLayout.SOUTH);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(jpPasswdFun, BorderLayout.NORTH);
        southPanel.add(jpShowPasswd, BorderLayout.CENTER);
        southPanel.add(jpPasswdList, BorderLayout.SOUTH);

        //给JTextField对象添加监听器
        jtfFileName.addActionListener(jTextFieldActionListener);
        jtfName.addActionListener(jTextFieldActionListener);
        //给关键字文本添加按钮监听器，这样做是方便实现和生成按钮相同的功能
        jtfKeyword.addActionListener(jButtonActionListener);

        jtfPasswd.addActionListener(jTextFieldActionListener);
        jtfMD5.addActionListener(jTextFieldActionListener);

        //给JCheckBox对象添加监听器
        jcbWord.addActionListener(jCheckBoxActionListener);
        jcbNumber.addActionListener(jCheckBoxActionListener);
        jcbSpecialChar.addActionListener(jCheckBoxActionListener);
        jcbFile.addActionListener(jCheckBoxActionListener);
        jcbAutoFresh.addActionListener(jCheckBoxActionListener);

        //给JButton对象添加监听器
        jbtGenerate.addActionListener(jButtonActionListener);
        jbtCheck.addActionListener(jButtonActionListener);
        jbtUpdate.addActionListener(jButtonActionListener);
        jbtDelete.addActionListener(jButtonActionListener);
        jbtCopy.addActionListener(jButtonActionListener);
        jbtRefresh.addActionListener(jButtonActionListener);
        jbtMD5.addActionListener(jButtonActionListener);

        //给JList对象添加监听器
        jlFileName.addListSelectionListener(jListActionListener);

        this.setLayout(new BorderLayout());
        this.getContentPane().add(northPanel, BorderLayout.NORTH);
        this.getContentPane().add(new JPanel());
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

    public JButton getJbtRefresh() {
        return jbtRefresh;
    }

    public void setJbtRefresh(JButton jbtRefresh) {
        this.jbtRefresh = jbtRefresh;
    }

    public JCheckBox getJcbAutoFresh() {
        return jcbAutoFresh;
    }

    public void setJcbAutoFresh(JCheckBox jcbAutoFresh) {
        this.jcbAutoFresh = jcbAutoFresh;
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

    public JButton getJbtMD5() {
        return jbtMD5;
    }

    public void setJbtMD5(JButton jbtMD5) {
        this.jbtMD5 = jbtMD5;
    }

    public JTextField getJtfMD5() {
        return jtfMD5;
    }

    public void setJtfMD5(JTextField jtfMD5) {
        this.jtfMD5 = jtfMD5;
    }

    public String getFileDirPath() {
        return fileDirPath;
    }

    public void setFileDirPath(String fileDirPath) {
        this.fileDirPath = fileDirPath;
    }

}
