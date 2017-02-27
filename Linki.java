import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Linki {
    static GUI view;
    static JFrame frame;
    static int size;
    static ClickLists selectInList = new ClickLists();
    static java.util.List<String> curr = new ArrayList<String>();
    static JList listHistory = new JList();
    static int current = 0;
    static Connection c;
    static Statement stmt;
    static JScrollPane scrollHist;
    static String sql;
    static JTable table;
    static Object[][] dataTable;
    static String[] columnNames = {"Http", "Date"};
    static int nrRows = 0;

    public Linki(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                c = null;
                stmt = null;
                try {
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:test.db");
                    System.out.println("Opened database successfully");
                    stmt = c.createStatement();
                    sql = "CREATE TABLE IF NOT EXISTS HISTORY" +
                            "(TMP TEXT      NOT NULL," +
                            " HTTP           TEXT     NOT NULL)";
                    stmt.execute(sql);
                    stmt.close();
                    c.close();

                    frame = new JFrame("LINKI");
                    view = new GUI(frame);
                    view.button1.addActionListener(e -> showBack());
                    view.button3.addActionListener(e -> showUrl());
                    view.button4.addActionListener(e -> showHistory());
                    view.area.addActionListener(e -> showUrl());
                    view.list.addMouseListener(selectInList);
                } catch ( Exception e ) {
                    JOptionPane.showMessageDialog(frame, "Problem with open database!",  "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        Linki aaa = new Linki();
    }

    static class ClickLists implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() >= 1) {
                String selectedItem = (String) view.list.getSelectedValue();
                curr.add(selectedItem);
                current++;
                view.area.setText(selectedItem);
                int test = 0;
                if(selectedItem == null)
                    test = 1;
                connect y = new connect(selectedItem, view, curr, current, test);
                if(test==1)
                    return;
                view.listmodel.clear();
                view.text1.setText(String.valueOf(y.counter));
                view.text2.setText(String.valueOf(y.size));
                DefaultListModel model = new DefaultListModel();
                if(y.https == null)
                    return;
                for(String xx : y.https){
                    model.addElement(xx);
                }
                view.list.setModel(model);

                try {
                    Class.forName("org.sqlite.JDBC");
                    c = DriverManager.getConnection("jdbc:sqlite:test.db");
                    c.setAutoCommit(false);
                    stmt = c.createStatement();
                    sql = "INSERT INTO HISTORY (TMP, HTTP) " +
                            "VALUES (\"" + selectedItem + "\", datetime('now'));";
                    stmt.executeUpdate(sql);
                    c.commit();
                    stmt.close();
                    c.close();
                } catch (Exception f) {
                    if(selectedItem == null || selectedItem.equals(""))
                       return;
                    JOptionPane.showMessageDialog(frame, "Problem with database!",  "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
        @Override
        public void mousePressed(MouseEvent e) {
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
    }

    void showHistory(){
        try {
            DefaultListModel modelTest = new DefaultListModel();
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM HISTORY;");
            int count = 0;
            java.util.List<String> column1 = new ArrayList<String>();
            java.util.List<String> column2 = new ArrayList<String>();

            while (resultSet.next())
            {
                String a = resultSet.getString("tmp");
                String b = resultSet.getString("http");
                column1.add(a);
                column2.add(b);
            }
            nrRows = column1.size();
            dataTable = new Object[nrRows][2];
            while(count < nrRows) {
                dataTable[count][0] = column1.get(count);
                dataTable[count][1] = column2.get(count);
                count = count + 1;
            }
            table = new JTable(dataTable, columnNames);
            resultSet.close();
            stmt.close();
            c.close();
        } catch (Exception g){
            JOptionPane.showMessageDialog(frame, "Incorrect URL! Try again",  "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        final JDialog dialog = new JDialog(frame, "HISTORY", true);
        JPanel Hist = new JPanel();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(false);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 1) {
                    String SelectValue = null;
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();
                    SelectValue = (String) table.getValueAt(row, 0);
                    curr.add(SelectValue);
                    current++;
                    view.area.setText(SelectValue);
                    connect y = new connect(SelectValue, view, curr, current, 0);

                    view.listmodel.clear();
                    view.text1.setText(String.valueOf(y.counter));
                    view.text2.setText(String.valueOf(y.size));
                    DefaultListModel model = new DefaultListModel();
                    if(y.https == null)
                        return;
                    for(String xx : y.https){
                        model.addElement(xx);
                    }
                    view.list.setModel(model);

                    try {
                        Class.forName("org.sqlite.JDBC");
                        c = DriverManager.getConnection("jdbc:sqlite:test.db");
                        c.setAutoCommit(false);
                        stmt = c.createStatement();
                        sql = "INSERT INTO HISTORY (TMP, HTTP) " +
                                "VALUES (\"" + SelectValue + "\", datetime('now'));";
                        stmt.executeUpdate(sql);
                        c.commit();
                        stmt.close();
                        c.close();
                    } catch (Exception f) {
                        JOptionPane.showMessageDialog(frame, "Problem with database",  "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    dialog.dispose();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
        scrollHist = new JScrollPane();
        scrollHist.setViewportView(table);
        Hist.add(scrollHist);
        dialog.add(Hist);
        dialog.setMinimumSize(new Dimension(500, 500));
        dialog.setVisible(true);
    }

    void showBack(){
        if(current>1){
            String temp = null;
            curr.remove(current-1);
            current--;
            temp = curr.get(current-1);
            view.area.setText(temp);
            connect y = new connect(temp, view, curr, current, 0);

            view.listmodel.clear();
            view.text1.setText(String.valueOf(y.counter));
            view.text2.setText(String.valueOf(y.size));
            DefaultListModel model = new DefaultListModel();
            if(y.https == null)
                return;
            for(String xx : y.https){
                model.addElement(xx);
            }
            view.list.setModel(model);

            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:test.db");
                c.setAutoCommit(false);
                stmt = c.createStatement();
                sql = "INSERT INTO HISTORY (TMP, HTTP) " +
                        "VALUES (\"" + temp + "\", datetime('now'));";
                stmt.executeUpdate(sql);
                c.commit();
                stmt.close();
                c.close();
            } catch (Exception f) {
                JOptionPane.showMessageDialog(frame, "Problem with database!",  "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void showUrl(){
        String x = null;
        x = view.area.getText();
        curr.add(x);
        current++;
        connect y = new connect(x, view, curr, current, 0);

        view.listmodel.clear();
        view.text1.setText(String.valueOf(y.counter));
        view.text2.setText(String.valueOf(y.size));
        DefaultListModel model = new DefaultListModel();
        if(y.https == null)
            return;
        for(String xx : y.https){
            model.addElement(xx);
        }
        view.list.setModel(model);

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            sql = "INSERT INTO HISTORY (TMP, HTTP) " +
                    "VALUES (\"" + x + "\", datetime('now'));";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception f) {
            JOptionPane.showMessageDialog(frame, "Problem with database!",  "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}