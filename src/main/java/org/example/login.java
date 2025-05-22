package org.example;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/*
 * Created by JFormDesigner on Fri May 16 19:03:35 CEST 2025
 */
/**
 * @author ami
 */


public class login extends JPanel {
    public login() {
        initComponents();
        setupLogin();
    }

    private void setupLogin() {
        button1.addActionListener(e -> {
            String user = textField1.getText().trim();
            String pass = new String(passwordField1.getPassword()).trim();

            // ein einziges Mal das umgebende JFrame holen
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

            if ("senioruser".equals(user) && "senioruser".equals(pass)) {
                topFrame.setContentPane(new startseite());
            }
            else if ("headofnt".equals(user) && "headofnt".equals(pass)) {
                topFrame.setContentPane(new startseite()); // ggf. anderes Panel für Head of NT
            }
            else if ("hotelrep".equals(user) && "hotelrep".equals(pass)) {
                topFrame.setContentPane(new hotel_rep());
            }
            else {
                JOptionPane.showMessageDialog(
                        this,
                        "Username or password incorrect!",
                        "Login failed",
                        JOptionPane.ERROR_MESSAGE
                );
                return;  // abbrechen, kein Wechsel des Panels
            }

            // Nach dem Setzen des neuen Content-Panes einmal packen und zentrieren:
            topFrame.pack();
            topFrame.setLocationRelativeTo(null);
        });
    }







    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
        panel1 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        label3 = new JLabel();
        passwordField1 = new JPasswordField();
        button1 = new JButton();

        //======== this ========
        setAutoscrolls(true);
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax .
        swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e" , javax. swing .border
        . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dialo\u0067"
        , java .awt . Font. BOLD ,12 ) ,java . awt. Color .red ) , getBorder
        () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java
        . beans. PropertyChangeEvent e) { if( "borde\u0072" .equals ( e. getPropertyName () ) )throw new RuntimeException
        ( ) ;} } );

        //======== panel1 ========
        {

            //---- label1 ----
            label1.setText("text");
            label1.setIcon(new ImageIcon(getClass().getResource("/logo.jpeg")));

            //---- label2 ----
            label2.setText("Username:");

            //---- label3 ----
            label3.setText("Password:");

            //---- button1 ----
            button1.setText("Log In");
            button1.setBackground(new Color(0x3399ff));
            button1.setForeground(Color.white);
            button1.setFont(button1.getFont().deriveFont(button1.getFont().getStyle() | Font.BOLD));

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(145, 145, 145)
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(label3)
                                    .addComponent(label2))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(222, 222, 222)
                                .addComponent(button1)))
                        .addContainerGap(163, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2))
                        .addGap(18, 18, 18)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label3)
                            .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addComponent(button1)
                        .addContainerGap(74, Short.MAX_VALUE))
            );
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JLabel label3;
    private JPasswordField passwordField1;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
