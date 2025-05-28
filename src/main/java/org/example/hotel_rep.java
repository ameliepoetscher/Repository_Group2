/*
 * Created by JFormDesigner on Sun May 18 18:16:41 CEST 2025
 */

package org.example;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.*;

/**
 * @author ami
 */
public class hotel_rep extends JPanel {
    public hotel_rep() {
        initComponents();

        ActionListener logout = e -> {
            JFrame top = (JFrame) SwingUtilities.getWindowAncestor(this);
            top.setContentPane(new login());
            top.pack();
            top.setLocationRelativeTo(null);
        };
        button1.addActionListener(logout);

    }









    //ab da nichts verändern ?!
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        label1 = new JLabel();
        panel2 = new JPanel();

        //======== this ========
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.
        swing.border.EmptyBorder(0,0,0,0), "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e",javax.swing.border
        .TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("Dialo\u0067"
        ,java.awt.Font.BOLD,12),java.awt.Color.red), getBorder
        ())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java
        .beans.PropertyChangeEvent e){if("borde\u0072".equals(e.getPropertyName()))throw new RuntimeException
        ();}});

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {

                //======== panel7 ========
                {

                    //======== panel8 ========
                    {
                        panel8.setBackground(new Color(0x3366ff));

                        //---- button1 ----
                        button1.setText("Log Out");
                        button1.setBackground(Color.lightGray);

                        //---- button2 ----
                        button2.setText("Help");
                        button2.setBackground(Color.lightGray);

                        GroupLayout panel8Layout = new GroupLayout(panel8);
                        panel8.setLayout(panel8Layout);
                        panel8Layout.setHorizontalGroup(
                            panel8Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button2)
                                    .addGap(17, 17, 17))
                                .addGroup(panel8Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(button1)
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                        panel8Layout.setVerticalGroup(
                            panel8Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
                                    .addComponent(button1)
                                    .addGap(23, 23, 23))
                        );
                    }

                    //======== scrollPane1 ========
                    {

                        //---- table1 ----
                        table1.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"1", "   Hotel Alpha", "    Vienna", "  20", "    35"},
                            },
                            new String[] {
                                "ID", "name", "adresse", "rooms", "beds"
                            }
                        ) {
                            boolean[] columnEditable = new boolean[] {
                                false, true, true, true, true
                            };
                            @Override
                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return columnEditable[columnIndex];
                            }
                        });
                        {
                            TableColumnModel cm = table1.getColumnModel();
                            cm.getColumn(0).setPreferredWidth(15);
                        }
                        scrollPane1.setViewportView(table1);
                    }

                    //---- label1 ----
                    label1.setText("last report: 12.05.2025");
                    label1.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 6));
                    label1.setBackground(new Color(0x330033));

                    GroupLayout panel7Layout = new GroupLayout(panel7);
                    panel7.setLayout(panel7Layout);
                    panel7Layout.setHorizontalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addComponent(panel8, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label1)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                    panel7Layout.setVerticalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel7Layout.createParallelGroup()
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label1))
                                .addContainerGap(254, Short.MAX_VALUE))
                            .addComponent(panel8, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                }

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addComponent(panel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addComponent(panel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("master data", panel1);

            //======== panel2 ========
            {

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGap(0, 613, Short.MAX_VALUE)
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGap(0, 310, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("transactional", panel2);
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 613, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 122, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 117, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel7;
    private JPanel panel8;
    private JButton button1;
    private JButton button2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JLabel label1;
    private JPanel panel2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
