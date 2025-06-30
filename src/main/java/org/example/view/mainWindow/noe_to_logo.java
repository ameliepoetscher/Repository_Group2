package org.example.view.mainWindow;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author amaim
 */
public class noe_to_logo extends JFrame {
    public noe_to_logo() {
        initComponents();
    }

    // BUTTON-KLICK: Login anzeigen!
    private void loginbutton2(ActionEvent e) {
        // Neues Login-Panel anzeigen:
        this.setContentPane(new login());  // <<--- zeigt Login an!
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
        button1 = new JButton();
        label1 = new JLabel();
        separator1 = new JPopupMenu.Separator();

        //======== this ========
        var contentPane = getContentPane();

        //---- button1 ----
        button1.setText("Log In");
        button1.setBackground(new Color(0x3399ff));
        button1.setForeground(Color.white);
        button1.setFont(button1.getFont().deriveFont(button1.getFont().getStyle() | Font.BOLD));
        button1.addActionListener(e -> loginbutton2(e));

        //---- label1 ----
        label1.setIcon(new ImageIcon(getClass().getResource("/noe_to_logo.jpg")));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(145, 145, 145)
                            .addComponent(button1, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(77, 77, 77)
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(87, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addContainerGap(48, Short.MAX_VALUE)
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                            .addGap(42, 42, 42))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(81, 81, 81)
                            .addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)))
                    .addComponent(button1)
                    .addGap(85, 85, 85))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
    private JButton button1;
    private JLabel label1;
    private JPopupMenu.Separator separator1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
