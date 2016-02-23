package Frame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import ActionListener.*;

/**
 * Created by ht on 2016/2/21.
 * 用户初次使用时，会出现该初始界面，用于确定密码文件的存储路径
 */
public class InitFrame extends JFrame {

    private JLabel jlInitMessage;
    private JTextField jtfInitTextField;
    private JButton jbtInitButton;

    private MainFrame mainFrame;

    private JInitButtonActionListener jInitButtonActionListener = new JInitButtonActionListener(this);

    public InitFrame(MainFrame mainFrame) throws HeadlessException {

        this.mainFrame = mainFrame;

        jlInitMessage = new JLabel("请选择密码文件存放位置：");
        jtfInitTextField = new JTextField(40);
        jbtInitButton = new JButton("...");

        jbtInitButton.addActionListener(jInitButtonActionListener);

        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBorder(new TitledBorder("文件存放路径"));
        p1.add(jtfInitTextField, BorderLayout.CENTER);
        JPanel p2 = new JPanel(new BorderLayout());
        p2.setBorder(new TitledBorder("选择路径"));
        p2.add(jbtInitButton, BorderLayout.EAST);

        JPanel sPanel = new JPanel(new BorderLayout());
        sPanel.add(p1, BorderLayout.CENTER);
        sPanel.add(p2, BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        this.getContentPane().add(jlInitMessage, BorderLayout.NORTH);
        this.getContentPane().add(sPanel, BorderLayout.SOUTH);
    }

    public JTextField getJtfInitTextField() {
        return jtfInitTextField;
    }

    public void setJtfInitTextField(JTextField jtfInitTextField) {
        this.jtfInitTextField = jtfInitTextField;
    }

    public JButton getJbtInitButton() {
        return jbtInitButton;
    }

    public void setJbtInitButton(JButton jbtInitButton) {
        this.jbtInitButton = jbtInitButton;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
