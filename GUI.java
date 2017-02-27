import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class GUI {
    JButton button1, button3, button4;
    JFrame frame;
    JList list;
    JScrollPane listScroller;
    JLabel text1, text2;
    JTextField area;
    DefaultListModel listmodel;

    public GUI(JFrame frame) {
        try {
            this.frame = frame;
            this.frame.setResizable(true);
            frame.getContentPane().setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(500, 500));

            JPanel chooseOption = new JPanel(new BorderLayout());
            Image img0 = ImageIO.read(getClass().getResource("/back.png"));
            Image img1 = ImageIO.read(getClass().getResource("/start.png"));
            Image img2 = ImageIO.read(getClass().getResource("/history-clock-button.png"));
            button1 = new JButton();
            button1.setPreferredSize(new Dimension(64, 40));
            button1.setIcon(new ImageIcon(img0));
            button3 = new JButton();
            button3.setPreferredSize(new Dimension(64, 40));
            button3.setIcon(new ImageIcon(img1));
            button4 = new JButton();
            button4.setPreferredSize(new Dimension(64, 40));
            button4.setIcon(new ImageIcon(img2));
            JPanel leftPanel = new JPanel(new GridLayout(1, 2));
            area = new JTextField();

            leftPanel.add(button3);
            leftPanel.add(button4);
            chooseOption.add(button1, BorderLayout.WEST);
            chooseOption.add(area, BorderLayout.CENTER);
            chooseOption.add(leftPanel, BorderLayout.EAST);
            listmodel = new DefaultListModel();
            list = new JList(listmodel);
            list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            listScroller = new JScrollPane();
            listScroller.setViewportView(list);


            JPanel clear = new JPanel();

            clear.add (new JLabel ("Counter Img:"));
            text1 = new JLabel(" ");
            clear.add(text1);
            clear.add (new JLabel ("Sum sizes:"));
            text2 = new JLabel(" ");
            clear.add(text2);

            frame.getContentPane().add(chooseOption, BorderLayout.NORTH);
            frame.getContentPane().add(listScroller, BorderLayout.CENTER);
            frame.getContentPane().add(clear, BorderLayout.SOUTH);

            frame.pack();
            frame.setVisible(true);
        } catch (Exception f) {
            JOptionPane.showMessageDialog(frame, "Problem with create GUI!",  "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}