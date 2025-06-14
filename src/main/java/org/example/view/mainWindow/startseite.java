package org.example.view.mainWindow;
import org.example.entity.Hotel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.*;
import org.example.data.txt.HotelFileReader;
import java.util.List;
import javax.swing.table.DefaultTableModel;



/*
 * Created by JFormDesigner on Fri May 16 15:12:45 CEST 2025
 */

/**
 * @author ami
 */


public class startseite extends JPanel {
    public startseite() {
        initComponents();
        ladeHotelsInTabelle();


        // ======= HIER kommt Dein eigener Code =======
        button25.addActionListener(new save());
        button15.addActionListener(new save());
        button18.addActionListener(new save());
        button21.addActionListener(new save());
        button24.addActionListener(new save());



        deleteButton.addActionListener(new DeleteHotelAction());

        ActionListener logout = e -> {
            JFrame top = (JFrame) SwingUtilities.getWindowAncestor(this);
            top.setContentPane(new login());
            top.pack();
            top.setLocationRelativeTo(null);
        };

        button1.addActionListener(logout);
        button16.addActionListener(logout);
        button19.addActionListener(logout);
        button22.addActionListener(logout);

        ActionListener help = e -> {
            JOptionPane.showMessageDialog(
                    startseite.this,
                    "Welcome to the Lower Austria Tourist Portal!\n\n" +
                            "Here’s how to use this application:\n" +
                            "1. In the Hotels tab, view and edit master data for all hotels.\n" +
                            "2. In Hotels Summary, see aggregate statistics per hotel category.\n" +
                            "3. In Occupancy, select a year, month, and category to view occupancy trends.\n" +
                            "4. In Occupancy Summary, choose date ranges or hotel to see summarized occupancy data.\n\n" +
                            "Use the +, save and logout buttons as needed. If you need further assistance,\n" +
                            "please consult the user guide or contact support@example.com.",
                    "Help",
                    JOptionPane.INFORMATION_MESSAGE
            );
        };

        button2 .addActionListener(help);
        button17.addActionListener(help);
        button20.addActionListener(help);
        button23.addActionListener(help);

        


    }

    private void ladeHotelsInTabelle() {
        String filePath = "src/main/java/org/example/data/txt/hotels.txt";
        List<Hotel> hotels = HotelFileReader.readHotelsFromFile(filePath);

        DefaultTableModel model = new DefaultTableModel(
                new String[] {"ID", "Category", "Name", "Adresse", "City", "PLZ", "Rooms", "Beds"}, 0
        );


        for (Hotel hotel : hotels) {
            Object[] rowData = {
                    hotel.getId(),
                    hotel.getCategory(),
                    hotel.getName(),
                    hotel.getAddress(),
                    hotel.getCity(),
                    hotel.getCityCode(),
                    hotel.getNoRooms(),
                    hotel.getNoBeds()
            };
            model.addRow(rowData);
        }

        table1.setModel(model);
    }

    private void Add(ActionEvent e) {
        // TODO add your code here
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
        this2 = new JPanel();
        label21 = new JLabel();
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        panel7 = new JPanel();
        panel8 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        button6 = new JButton();
        button25 = new JButton();
        deleteButton = new JButton();
        panel3 = new JPanel();
        panel17 = new JPanel();
        button15 = new JButton();
        scrollPane5 = new JScrollPane();
        table5 = new JTable();
        panel25 = new JPanel();
        button26 = new JButton();
        button27 = new JButton();
        panel4 = new JPanel();
        panel19 = new JPanel();
        panel20 = new JPanel();
        button16 = new JButton();
        button17 = new JButton();
        button18 = new JButton();
        comboBox3 = new JComboBox<>();
        label6 = new JLabel();
        comboBox4 = new JComboBox<>();
        label10 = new JLabel();
        label11 = new JLabel();
        comboBox5 = new JComboBox<>();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        comboBox15 = new JComboBox<>();
        label22 = new JLabel();
        panel5 = new JPanel();
        panel21 = new JPanel();
        panel22 = new JPanel();
        button19 = new JButton();
        button20 = new JButton();
        button21 = new JButton();
        comboBox7 = new JComboBox<>();
        label12 = new JLabel();
        label13 = new JLabel();
        comboBox8 = new JComboBox<>();
        scrollPane3 = new JScrollPane();
        table3 = new JTable();
        label14 = new JLabel();
        comboBox9 = new JComboBox<>();
        label15 = new JLabel();
        comboBox10 = new JComboBox<>();
        comboBox11 = new JComboBox<>();
        panel6 = new JPanel();
        panel23 = new JPanel();
        panel24 = new JPanel();
        button22 = new JButton();
        button23 = new JButton();
        button24 = new JButton();
        comboBox12 = new JComboBox<>();
        label16 = new JLabel();
        label17 = new JLabel();
        comboBox13 = new JComboBox<>();
        comboBox16 = new JComboBox<>();
        comboBox6 = new JComboBox<>();
        label7 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        label18 = new JLabel();
        label19 = new JLabel();
        textField4 = new JTextField();
        textField5 = new JTextField();
        textField6 = new JTextField();
        textField7 = new JTextField();
        scrollPane4 = new JScrollPane();
        table4 = new JTable();
        label20 = new JLabel();
        comboBox14 = new JComboBox<>();

        //======== this ========
        setPreferredSize(new Dimension(900, 600));
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing.
        border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e" , javax. swing .border . TitledBorder. CENTER
        ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dialo\u0067", java .awt . Font
        . BOLD ,12 ) ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener(
        new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "borde\u0072"
        .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );

        //======== this2 ========
        {

            GroupLayout this2Layout = new GroupLayout(this2);
            this2.setLayout(this2Layout);
            this2Layout.setHorizontalGroup(
                this2Layout.createParallelGroup()
                    .addGap(0, 29, Short.MAX_VALUE)
            );
            this2Layout.setVerticalGroup(
                this2Layout.createParallelGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
            );
        }

        //---- label21 ----
        label21.setIcon(new ImageIcon(getClass().getResource("/logo2.png")));

        //======== tabbedPane1 ========
        {
            tabbedPane1.setPreferredSize(new Dimension(700, 400));

            //======== panel1 ========
            {
                panel1.setBackground(Color.white);

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
                                .addGroup(panel8Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel8Layout.createParallelGroup()
                                        .addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel8Layout.createSequentialGroup()
                                            .addComponent(button2, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)))
                                    .addContainerGap())
                        );
                        panel8Layout.setVerticalGroup(
                            panel8Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button1)
                                    .addGap(23, 23, 23))
                        );
                    }

                    //======== scrollPane1 ========
                    {

                        //---- table1 ----
                        table1.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"1", "Hotel Alpha", "Vienna", "20", "35"},
                                {"2", "Hotel Beta", "Graz", "30", "45"},
                                {"3", "Hotel Gamma", "Linz", "40", "55"},
                                {"4", "Hotel Delta", "Salzburg ", "50", "65"},
                                {"5", "Hotel Epsilon", "Klagenfurt", "60", "75"},
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
                            cm.getColumn(0).setPreferredWidth(5);
                        }
                        scrollPane1.setViewportView(table1);
                    }

                    //---- button6 ----
                    button6.setText("+");
                    button6.addActionListener(e -> Add(e));

                    //---- button25 ----
                    button25.setText("Save");

                    //---- deleteButton ----
                    deleteButton.setText("Delete");

                    GroupLayout panel7Layout = new GroupLayout(panel7);
                    panel7.setLayout(panel7Layout);
                    panel7Layout.setHorizontalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addComponent(panel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel7Layout.createParallelGroup()
                                    .addGroup(panel7Layout.createSequentialGroup()
                                        .addComponent(button6)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 387, Short.MAX_VALUE)
                                        .addComponent(deleteButton)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(button25)
                                        .addGap(94, 94, 94))
                                    .addGroup(panel7Layout.createSequentialGroup()
                                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                        .addContainerGap())))
                    );
                    panel7Layout.setVerticalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(button6)
                                    .addComponent(deleteButton)
                                    .addComponent(button25))
                                .addGap(28, 28, 28))
                            .addComponent(panel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            tabbedPane1.addTab("Hotels", panel1);

            //======== panel3 ========
            {
                panel3.setBackground(Color.white);

                //======== panel17 ========
                {

                    //---- button15 ----
                    button15.setText("save");

                    //======== scrollPane5 ========
                    {

                        //---- table5 ----
                        table5.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"\u2605", "20", "40", "70"},
                                {"\u2605\u2605", "25", "50", "75"},
                                {"\u2605\u2605\u2605", "30", "35", "50"},
                                {"\u2605\u2605\u2605\u2605", "20", "30", "40"},
                                {"\u2605\u2605\u2605\u2605\u2605", "15", "25", "30"},
                            },
                            new String[] {
                                "category", "hotels per category", "\u00d8 rooms", "\u00d8 beds"
                            }
                        ));
                        scrollPane5.setViewportView(table5);
                    }

                    //======== panel25 ========
                    {
                        panel25.setBackground(new Color(0x3366ff));

                        //---- button26 ----
                        button26.setText("Log Out");
                        button26.setBackground(Color.lightGray);

                        //---- button27 ----
                        button27.setText("Help");
                        button27.setBackground(Color.lightGray);

                        GroupLayout panel25Layout = new GroupLayout(panel25);
                        panel25.setLayout(panel25Layout);
                        panel25Layout.setHorizontalGroup(
                            panel25Layout.createParallelGroup()
                                .addGroup(panel25Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel25Layout.createParallelGroup()
                                        .addComponent(button26, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel25Layout.createSequentialGroup()
                                            .addComponent(button27, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)))
                                    .addContainerGap())
                        );
                        panel25Layout.setVerticalGroup(
                            panel25Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel25Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button27)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 277, Short.MAX_VALUE)
                                    .addComponent(button26)
                                    .addGap(23, 23, 23))
                        );
                    }

                    GroupLayout panel17Layout = new GroupLayout(panel17);
                    panel17.setLayout(panel17Layout);
                    panel17Layout.setHorizontalGroup(
                        panel17Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel17Layout.createSequentialGroup()
                                .addComponent(panel25, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel17Layout.createParallelGroup()
                                    .addGroup(panel17Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 521, Short.MAX_VALUE)
                                        .addComponent(button15)
                                        .addGap(72, 72, 72))
                                    .addGroup(panel17Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 562, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(78, Short.MAX_VALUE))))
                    );
                    panel17Layout.setVerticalGroup(
                        panel17Layout.createParallelGroup()
                            .addGroup(panel17Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button15)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel17Layout.createSequentialGroup()
                                .addComponent(panel25, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                    );
                }

                GroupLayout panel3Layout = new GroupLayout(panel3);
                panel3.setLayout(panel3Layout);
                panel3Layout.setHorizontalGroup(
                    panel3Layout.createParallelGroup()
                        .addComponent(panel17, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panel3Layout.setVerticalGroup(
                    panel3Layout.createParallelGroup()
                        .addComponent(panel17, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("Hotels Summary", panel3);

            //======== panel4 ========
            {
                panel4.setBackground(Color.white);

                //======== panel19 ========
                {

                    //======== panel20 ========
                    {
                        panel20.setBackground(new Color(0x3366ff));

                        //---- button16 ----
                        button16.setText("Log Out");
                        button16.setBackground(Color.lightGray);

                        //---- button17 ----
                        button17.setText("Help");
                        button17.setBackground(Color.lightGray);

                        GroupLayout panel20Layout = new GroupLayout(panel20);
                        panel20.setLayout(panel20Layout);
                        panel20Layout.setHorizontalGroup(
                            panel20Layout.createParallelGroup()
                                .addGroup(panel20Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel20Layout.createParallelGroup()
                                        .addComponent(button16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel20Layout.createSequentialGroup()
                                            .addComponent(button17, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)))
                                    .addContainerGap())
                        );
                        panel20Layout.setVerticalGroup(
                            panel20Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel20Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button17)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button16)
                                    .addGap(23, 23, 23))
                        );
                    }

                    //---- button18 ----
                    button18.setText("save");

                    //---- comboBox3 ----
                    comboBox3.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u2605",
                        "\u2605\u2605",
                        "\u2605\u2605\u2605",
                        "\u2605\u2605\u2605\u2605",
                        "\u2605\u2605\u2605\u2605\u2605"
                    }));

                    //---- label6 ----
                    label6.setText("category:");

                    //---- comboBox4 ----
                    comboBox4.setModel(new DefaultComboBoxModel<>(new String[] {
                        "2025",
                        "2024",
                        "2023",
                        "2022",
                        "2021",
                        "2020",
                        "2019",
                        "2018",
                        "2017",
                        "2016",
                        "2015",
                        "2014",
                        "2013",
                        "2012",
                        "2011",
                        "2010",
                        "2009",
                        "2008",
                        "2007",
                        "2006",
                        "2005",
                        "2004",
                        "2003",
                        "2002",
                        "2001",
                        "2000",
                        "1999",
                        "1998",
                        "1997",
                        "1996",
                        "1995",
                        "1994",
                        "1993",
                        "1992",
                        "1991",
                        "1990",
                        "1989",
                        "1988",
                        "1987",
                        "1986",
                        "1985",
                        "1984",
                        "1983",
                        "1982",
                        "1981",
                        "1980",
                        "1979",
                        "1978",
                        "1977",
                        "1976",
                        "1975",
                        "1974",
                        "1973",
                        "1972",
                        "1971",
                        "1970",
                        "1969",
                        "1968",
                        "1967",
                        "1966",
                        "1965",
                        "1964",
                        "1963",
                        "1962",
                        "1961",
                        "1960",
                        "1959",
                        "1958",
                        "1957",
                        "1956",
                        "1955",
                        "1954",
                        "1953",
                        "1952",
                        "1951",
                        "1950",
                        "1949",
                        "1948",
                        "1947",
                        "1946",
                        "1945",
                        "1944",
                        "1943",
                        "1942",
                        "1941",
                        "1940",
                        "1939",
                        "1938",
                        "1937",
                        "1936",
                        "1935",
                        "1934",
                        "1933",
                        "1932",
                        "1931",
                        "1930",
                        "1929",
                        "1928",
                        "1927",
                        "1926",
                        "1925",
                        "1924",
                        "1923",
                        "1922",
                        "1921",
                        "1920",
                        "1919",
                        "1918",
                        "1917",
                        "1916",
                        "1915",
                        "1914",
                        "1913",
                        "1912",
                        "1911",
                        "1910",
                        "1909",
                        "1908",
                        "1907",
                        "1906",
                        "1905",
                        "1904",
                        "1903",
                        "1902",
                        "1901",
                        "1900"
                    }));

                    //---- label10 ----
                    label10.setText("year:");

                    //---- label11 ----
                    label11.setText("month:");

                    //---- comboBox5 ----
                    comboBox5.setModel(new DefaultComboBoxModel<>(new String[] {
                        "January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "August",
                        "September",
                        "October",
                        "November",
                        "December"
                    }));

                    //======== scrollPane2 ========
                    {

                        //---- table2 ----
                        table2.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"1", "Hotel Alpha", "10", "15"},
                                {"2", "Hotel Beta", "20", "25"},
                                {"3", "Hotel Gamma", "30", "35"},
                                {"4", "Hotel Delta", "40", "45"},
                                {"5", "Hotel Gamma", "50", "55"},
                            },
                            new String[] {
                                "ID", "Name", "Occup. rooms", "Occup. beds"
                            }
                        ));
                        {
                            TableColumnModel cm = table2.getColumnModel();
                            cm.getColumn(0).setPreferredWidth(15);
                        }
                        scrollPane2.setViewportView(table2);
                    }

                    //---- comboBox15 ----
                    comboBox15.setModel(new DefaultComboBoxModel<>(new String[] {
                        "---select---",
                        "Hotel Alpha",
                        "Hotel Beta",
                        "Hotel Gamma ",
                        "Hotel Delta",
                        "Hotel Epsilon"
                    }));

                    //---- label22 ----
                    label22.setText("hotel:");

                    GroupLayout panel19Layout = new GroupLayout(panel19);
                    panel19.setLayout(panel19Layout);
                    panel19Layout.setHorizontalGroup(
                        panel19Layout.createParallelGroup()
                            .addGroup(panel19Layout.createSequentialGroup()
                                .addGroup(panel19Layout.createParallelGroup()
                                    .addComponent(panel20, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(button18))
                                .addGroup(panel19Layout.createParallelGroup()
                                    .addGroup(panel19Layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addGroup(panel19Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(label10)
                                            .addComponent(label11))
                                        .addGap(18, 18, 18)
                                        .addGroup(panel19Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addGroup(panel19Layout.createSequentialGroup()
                                                .addComponent(comboBox5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(label22))
                                            .addGroup(panel19Layout.createSequentialGroup()
                                                .addComponent(comboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(92, 92, 92)
                                                .addComponent(label6)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panel19Layout.createParallelGroup()
                                            .addComponent(comboBox15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comboBox3, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panel19Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 569, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(71, Short.MAX_VALUE))
                    );
                    panel19Layout.setVerticalGroup(
                        panel19Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel19Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(panel19Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label10)
                                    .addComponent(comboBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label6)
                                    .addComponent(comboBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel19Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label11)
                                    .addComponent(comboBox5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label22)
                                    .addComponent(comboBox15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel19Layout.createParallelGroup()
                                    .addGroup(panel19Layout.createSequentialGroup()
                                        .addGap(215, 215, 215)
                                        .addComponent(button18)
                                        .addContainerGap(42, Short.MAX_VALUE))
                                    .addGroup(panel19Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                        .addContainerGap())))
                            .addComponent(panel20, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                }

                GroupLayout panel4Layout = new GroupLayout(panel4);
                panel4.setLayout(panel4Layout);
                panel4Layout.setHorizontalGroup(
                    panel4Layout.createParallelGroup()
                        .addComponent(panel19, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panel4Layout.setVerticalGroup(
                    panel4Layout.createParallelGroup()
                        .addComponent(panel19, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("Occupancy Summary", panel4);

            //======== panel5 ========
            {
                panel5.setBackground(Color.white);

                //======== panel21 ========
                {

                    //======== panel22 ========
                    {
                        panel22.setBackground(new Color(0x3366ff));

                        //---- button19 ----
                        button19.setText("Log Out");
                        button19.setBackground(Color.lightGray);

                        //---- button20 ----
                        button20.setText("Help");
                        button20.setBackground(Color.lightGray);

                        GroupLayout panel22Layout = new GroupLayout(panel22);
                        panel22.setLayout(panel22Layout);
                        panel22Layout.setHorizontalGroup(
                            panel22Layout.createParallelGroup()
                                .addGroup(panel22Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel22Layout.createParallelGroup()
                                        .addComponent(button19, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel22Layout.createSequentialGroup()
                                            .addComponent(button20, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)))
                                    .addContainerGap())
                        );
                        panel22Layout.setVerticalGroup(
                            panel22Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel22Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button20)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button19)
                                    .addGap(23, 23, 23))
                        );
                    }

                    //---- button21 ----
                    button21.setText("save");

                    //---- comboBox7 ----
                    comboBox7.setModel(new DefaultComboBoxModel<>(new String[] {
                        "2025",
                        "2024",
                        "2023",
                        "2022",
                        "2021",
                        "2020",
                        "2019",
                        "2018",
                        "2017",
                        "2016",
                        "2015",
                        "2014",
                        "2013",
                        "2012",
                        "2011",
                        "2010",
                        "2009",
                        "2008",
                        "2007",
                        "2006",
                        "2005",
                        "2004",
                        "2003",
                        "2002",
                        "2001",
                        "2000",
                        "1999",
                        "1998",
                        "1997",
                        "1996",
                        "1995",
                        "1994",
                        "1993",
                        "1992",
                        "1991",
                        "1990",
                        "1989",
                        "1988",
                        "1987",
                        "1986",
                        "1985",
                        "1984",
                        "1983",
                        "1982",
                        "1981",
                        "1980",
                        "1979",
                        "1978",
                        "1977",
                        "1976",
                        "1975",
                        "1974",
                        "1973",
                        "1972",
                        "1971",
                        "1970",
                        "1969",
                        "1968",
                        "1967",
                        "1966",
                        "1965",
                        "1964",
                        "1963",
                        "1962",
                        "1961",
                        "1960",
                        "1959",
                        "1958",
                        "1957",
                        "1956",
                        "1955",
                        "1954",
                        "1953",
                        "1952",
                        "1951",
                        "1950",
                        "1949",
                        "1948",
                        "1947",
                        "1946",
                        "1945",
                        "1944",
                        "1943",
                        "1942",
                        "1941",
                        "1940",
                        "1939",
                        "1938",
                        "1937",
                        "1936",
                        "1935",
                        "1934",
                        "1933",
                        "1932",
                        "1931",
                        "1930",
                        "1929",
                        "1928",
                        "1927",
                        "1926",
                        "1925",
                        "1924",
                        "1923",
                        "1922",
                        "1921",
                        "1920",
                        "1919",
                        "1918",
                        "1917",
                        "1916",
                        "1915",
                        "1914",
                        "1913",
                        "1912",
                        "1911",
                        "1910",
                        "1909",
                        "1908",
                        "1907",
                        "1906",
                        "1905",
                        "1904",
                        "1903",
                        "1902",
                        "1901",
                        "1900"
                    }));

                    //---- label12 ----
                    label12.setText("from:");

                    //---- label13 ----
                    label13.setText("from:");

                    //---- comboBox8 ----
                    comboBox8.setModel(new DefaultComboBoxModel<>(new String[] {
                        "January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "August",
                        "September",
                        "October",
                        "November",
                        "December"
                    }));

                    //======== scrollPane3 ========
                    {

                        //---- table3 ----
                        table3.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"January", "2023", "10", "15"},
                                {"February", "2023", "20", "25"},
                                {"March", "2023", "30", "35"},
                                {"April", "2023", "40", "45"},
                                {"May", "2023", "50", "55"},
                                {"June", "2023", "20", "30"},
                                {"July", "2023", "35", "44"},
                                {"August", "2023", "55", "78"},
                                {"September", "2023", "62", "88"},
                                {"October", "2023", "44", "65"},
                                {"November", "2023", "58", "72"},
                                {"December", "2023", "60", "70"},
                            },
                            new String[] {
                                "Month", "Year", "Occup. rooms", "Occup. beds"
                            }
                        ));
                        scrollPane3.setViewportView(table3);
                    }

                    //---- label14 ----
                    label14.setText("to:");

                    //---- comboBox9 ----
                    comboBox9.setModel(new DefaultComboBoxModel<>(new String[] {
                        "2025",
                        "2024",
                        "2023",
                        "2022",
                        "2021",
                        "2020",
                        "2019",
                        "2018",
                        "2017",
                        "2016",
                        "2015",
                        "2014",
                        "2013",
                        "2012",
                        "2011",
                        "2010",
                        "2009",
                        "2008",
                        "2007",
                        "2006",
                        "2005",
                        "2004",
                        "2003",
                        "2002",
                        "2001",
                        "2000",
                        "1999",
                        "1998",
                        "1997",
                        "1996",
                        "1995",
                        "1994",
                        "1993",
                        "1992",
                        "1991",
                        "1990",
                        "1989",
                        "1988",
                        "1987",
                        "1986",
                        "1985",
                        "1984",
                        "1983",
                        "1982",
                        "1981",
                        "1980",
                        "1979",
                        "1978",
                        "1977",
                        "1976",
                        "1975",
                        "1974",
                        "1973",
                        "1972",
                        "1971",
                        "1970",
                        "1969",
                        "1968",
                        "1967",
                        "1966",
                        "1965",
                        "1964",
                        "1963",
                        "1962",
                        "1961",
                        "1960",
                        "1959",
                        "1958",
                        "1957",
                        "1956",
                        "1955",
                        "1954",
                        "1953",
                        "1952",
                        "1951",
                        "1950",
                        "1949",
                        "1948",
                        "1947",
                        "1946",
                        "1945",
                        "1944",
                        "1943",
                        "1942",
                        "1941",
                        "1940",
                        "1939",
                        "1938",
                        "1937",
                        "1936",
                        "1935",
                        "1934",
                        "1933",
                        "1932",
                        "1931",
                        "1930",
                        "1929",
                        "1928",
                        "1927",
                        "1926",
                        "1925",
                        "1924",
                        "1923",
                        "1922",
                        "1921",
                        "1920",
                        "1919",
                        "1918",
                        "1917",
                        "1916",
                        "1915",
                        "1914",
                        "1913",
                        "1912",
                        "1911",
                        "1910",
                        "1909",
                        "1908",
                        "1907",
                        "1906",
                        "1905",
                        "1904",
                        "1903",
                        "1902",
                        "1901",
                        "1900"
                    }));

                    //---- label15 ----
                    label15.setText("to:");

                    //---- comboBox10 ----
                    comboBox10.setModel(new DefaultComboBoxModel<>(new String[] {
                        "January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "August",
                        "September",
                        "October",
                        "November",
                        "December"
                    }));

                    //---- comboBox11 ----
                    comboBox11.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Hotel Alpha",
                        "Hotel Beta",
                        "Hotel Gamma ",
                        "Hotel Delta",
                        "Hotel Epsilon"
                    }));
                    comboBox11.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 15));

                    GroupLayout panel21Layout = new GroupLayout(panel21);
                    panel21.setLayout(panel21Layout);
                    panel21Layout.setHorizontalGroup(
                        panel21Layout.createParallelGroup()
                            .addGroup(panel21Layout.createSequentialGroup()
                                .addComponent(panel22, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel21Layout.createParallelGroup()
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel21Layout.createSequentialGroup()
                                        .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(panel21Layout.createSequentialGroup()
                                                .addComponent(comboBox11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(label14))
                                            .addGroup(panel21Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(label12)))
                                        .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(panel21Layout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(comboBox9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(label15))
                                            .addGroup(panel21Layout.createSequentialGroup()
                                                .addGap(5, 5, 5)
                                                .addComponent(comboBox7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(label13)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel21Layout.createParallelGroup()
                                            .addComponent(comboBox8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(comboBox10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(45, 45, 45))
                                    .addGroup(panel21Layout.createSequentialGroup()
                                        .addGap(399, 399, 399)
                                        .addComponent(button21)
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panel21Layout.createSequentialGroup()
                                        .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 103, Short.MAX_VALUE))))
                    );
                    panel21Layout.setVerticalGroup(
                        panel21Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel21Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label12)
                                    .addComponent(comboBox7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label13)
                                    .addComponent(comboBox8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel21Layout.createParallelGroup()
                                    .addComponent(comboBox11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label14)
                                        .addComponent(comboBox9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label15)
                                        .addComponent(comboBox10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button21)
                                .addGap(26, 26, 26))
                            .addComponent(panel22, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                }

                GroupLayout panel5Layout = new GroupLayout(panel5);
                panel5.setLayout(panel5Layout);
                panel5Layout.setHorizontalGroup(
                    panel5Layout.createParallelGroup()
                        .addComponent(panel21, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panel5Layout.setVerticalGroup(
                    panel5Layout.createParallelGroup()
                        .addComponent(panel21, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("Occupancy List", panel5);

            //======== panel6 ========
            {
                panel6.setBackground(Color.white);

                //======== panel23 ========
                {

                    //======== panel24 ========
                    {
                        panel24.setBackground(new Color(0x3366ff));

                        //---- button22 ----
                        button22.setText("Log Out");
                        button22.setBackground(Color.lightGray);

                        //---- button23 ----
                        button23.setText("Help");
                        button23.setBackground(Color.lightGray);

                        GroupLayout panel24Layout = new GroupLayout(panel24);
                        panel24.setLayout(panel24Layout);
                        panel24Layout.setHorizontalGroup(
                            panel24Layout.createParallelGroup()
                                .addGroup(panel24Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel24Layout.createParallelGroup()
                                        .addGroup(panel24Layout.createSequentialGroup()
                                            .addComponent(button23, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(button22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addContainerGap())
                        );
                        panel24Layout.setVerticalGroup(
                            panel24Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel24Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(button23)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(button22)
                                    .addGap(25, 25, 25))
                        );
                    }

                    //---- button24 ----
                    button24.setText("save");

                    //---- comboBox12 ----
                    comboBox12.setModel(new DefaultComboBoxModel<>(new String[] {
                        "2025",
                        "2024",
                        "2023",
                        "2022",
                        "2021",
                        "2020",
                        "2019",
                        "2018",
                        "2017",
                        "2016",
                        "2015",
                        "2014",
                        "2013",
                        "2012",
                        "2011",
                        "2010",
                        "2009",
                        "2008",
                        "2007",
                        "2006",
                        "2005",
                        "2004",
                        "2003",
                        "2002",
                        "2001",
                        "2000",
                        "1999",
                        "1998",
                        "1997",
                        "1996",
                        "1995",
                        "1994",
                        "1993",
                        "1992",
                        "1991",
                        "1990",
                        "1989",
                        "1988",
                        "1987",
                        "1986",
                        "1985",
                        "1984",
                        "1983",
                        "1982",
                        "1981",
                        "1980",
                        "1979",
                        "1978",
                        "1977",
                        "1976",
                        "1975",
                        "1974",
                        "1973",
                        "1972",
                        "1971",
                        "1970",
                        "1969",
                        "1968",
                        "1967",
                        "1966",
                        "1965",
                        "1964",
                        "1963",
                        "1962",
                        "1961",
                        "1960",
                        "1959",
                        "1958",
                        "1957",
                        "1956",
                        "1955",
                        "1954",
                        "1953",
                        "1952",
                        "1951",
                        "1950",
                        "1949",
                        "1948",
                        "1947",
                        "1946",
                        "1945",
                        "1944",
                        "1943",
                        "1942",
                        "1941",
                        "1940",
                        "1939",
                        "1938",
                        "1937",
                        "1936",
                        "1935",
                        "1934",
                        "1933",
                        "1932",
                        "1931",
                        "1930",
                        "1929",
                        "1928",
                        "1927",
                        "1926",
                        "1925",
                        "1924",
                        "1923",
                        "1922",
                        "1921",
                        "1920",
                        "1919",
                        "1918",
                        "1917",
                        "1916",
                        "1915",
                        "1914",
                        "1913",
                        "1912",
                        "1911",
                        "1910",
                        "1909",
                        "1908",
                        "1907",
                        "1906",
                        "1905",
                        "1904",
                        "1903",
                        "1902",
                        "1901",
                        "1900"
                    }));

                    //---- label16 ----
                    label16.setText("year:");

                    //---- label17 ----
                    label17.setText("month:");

                    //---- comboBox13 ----
                    comboBox13.setModel(new DefaultComboBoxModel<>(new String[] {
                        "January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "August",
                        "September",
                        "October",
                        "November",
                        "December"
                    }));

                    //---- comboBox16 ----
                    comboBox16.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Hotel Alpha",
                        "Hotel Beta",
                        "Hotel Gamma ",
                        "Hotel Delta",
                        "Hotel Epsilon"
                    }));
                    comboBox16.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 15));

                    //---- comboBox6 ----
                    comboBox6.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u2605",
                        "\u2605\u2605",
                        "\u2605\u2605\u2605",
                        "\u2605\u2605\u2605\u2605",
                        "\u2605\u2605\u2605\u2605\u2605"
                    }));

                    //---- label7 ----
                    label7.setText("category:");

                    //---- label8 ----
                    label8.setText("rooms:");

                    //---- label9 ----
                    label9.setText("occupied rooms:");

                    //---- label18 ----
                    label18.setText("beds:");

                    //---- label19 ----
                    label19.setText("ouccupied beds:");

                    //---- textField4 ----
                    textField4.setText("65");

                    //---- textField5 ----
                    textField5.setText("40");

                    //---- textField6 ----
                    textField6.setText("90");

                    //---- textField7 ----
                    textField7.setText("70");

                    //======== scrollPane4 ========
                    {

                        //---- table4 ----
                        table4.setModel(new DefaultTableModel(
                            new Object[][] {
                                {"adresse:", "Vienna"},
                            },
                            new String[] {
                                "ID:", "1"
                            }
                        ));
                        scrollPane4.setViewportView(table4);
                    }

                    //---- label20 ----
                    label20.setText("attributes:");

                    //---- comboBox14 ----
                    comboBox14.setModel(new DefaultComboBoxModel<>(new String[] {
                        "family friendly",
                        "dog friendly",
                        "spa ",
                        "fitness"
                    }));

                    GroupLayout panel23Layout = new GroupLayout(panel23);
                    panel23.setLayout(panel23Layout);
                    panel23Layout.setHorizontalGroup(
                        panel23Layout.createParallelGroup()
                            .addGroup(panel23Layout.createSequentialGroup()
                                .addComponent(panel24, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel23Layout.createParallelGroup()
                                    .addGroup(panel23Layout.createSequentialGroup()
                                        .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(scrollPane4, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(comboBox16, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel23Layout.createParallelGroup()
                                            .addGroup(panel23Layout.createSequentialGroup()
                                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                    .addComponent(label16)
                                                    .addComponent(label7))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel23Layout.createParallelGroup()
                                                    .addComponent(comboBox6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(comboBox12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(panel23Layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addComponent(label17)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(comboBox13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addGap(50, 50, 50))
                                    .addGroup(panel23Layout.createSequentialGroup()
                                        .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(panel23Layout.createSequentialGroup()
                                                .addComponent(label18)
                                                .addGap(18, 18, 18)
                                                .addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel23Layout.createSequentialGroup()
                                                .addComponent(label8)
                                                .addGap(18, 18, 18)
                                                .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel23Layout.createSequentialGroup()
                                                .addComponent(label9)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label20)
                                        .addGap(16, 16, 16)
                                        .addComponent(comboBox14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31))
                                    .addGroup(panel23Layout.createSequentialGroup()
                                        .addComponent(label19)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(button24)
                                        .addGap(79, 79, 79))))
                    );
                    panel23Layout.setVerticalGroup(
                        panel23Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel23Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel23Layout.createSequentialGroup()
                                        .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(panel23Layout.createSequentialGroup()
                                                .addComponent(comboBox16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel23Layout.createSequentialGroup()
                                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(label16)
                                                    .addComponent(comboBox12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(label17)
                                                    .addComponent(comboBox13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(12, 12, 12)
                                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(label7)
                                                    .addComponent(comboBox6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(3, 3, 3)))
                                        .addGap(48, 48, 48)
                                        .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label8)))
                                    .addGroup(panel23Layout.createParallelGroup()
                                        .addGroup(panel23Layout.createSequentialGroup()
                                            .addGap(3, 3, 3)
                                            .addComponent(label20))
                                        .addComponent(comboBox14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label9)
                                    .addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label18))
                                .addGroup(panel23Layout.createParallelGroup()
                                    .addGroup(panel23Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label19)
                                            .addComponent(textField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel23Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(button24)
                                        .addGap(36, 36, 36))))
                            .addComponent(panel24, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                }

                GroupLayout panel6Layout = new GroupLayout(panel6);
                panel6.setLayout(panel6Layout);
                panel6Layout.setHorizontalGroup(
                    panel6Layout.createParallelGroup()
                        .addComponent(panel23, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panel6Layout.setVerticalGroup(
                    panel6Layout.createParallelGroup()
                        .addComponent(panel23, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }
            tabbedPane1.addTab("combined Overview ", panel6);
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(label21, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup()
                                .addComponent(this2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 741, GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 22, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label21, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(this2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(23, 23, 23))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
    private JPanel this2;
    private JLabel label21;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel7;
    private JPanel panel8;
    private JButton button1;
    private JButton button2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton button6;
    private JButton button25;
    private JButton deleteButton;
    private JPanel panel3;
    private JPanel panel17;
    private JButton button15;
    private JScrollPane scrollPane5;
    private JTable table5;
    private JPanel panel25;
    private JButton button26;
    private JButton button27;
    private JPanel panel4;
    private JPanel panel19;
    private JPanel panel20;
    private JButton button16;
    private JButton button17;
    private JButton button18;
    private JComboBox<String> comboBox3;
    private JLabel label6;
    private JComboBox<String> comboBox4;
    private JLabel label10;
    private JLabel label11;
    private JComboBox<String> comboBox5;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JComboBox<String> comboBox15;
    private JLabel label22;
    private JPanel panel5;
    private JPanel panel21;
    private JPanel panel22;
    private JButton button19;
    private JButton button20;
    private JButton button21;
    private JComboBox<String> comboBox7;
    private JLabel label12;
    private JLabel label13;
    private JComboBox<String> comboBox8;
    private JScrollPane scrollPane3;
    private JTable table3;
    private JLabel label14;
    private JComboBox<String> comboBox9;
    private JLabel label15;
    private JComboBox<String> comboBox10;
    private JComboBox<String> comboBox11;
    private JPanel panel6;
    private JPanel panel23;
    private JPanel panel24;
    private JButton button22;
    private JButton button23;
    private JButton button24;
    private JComboBox<String> comboBox12;
    private JLabel label16;
    private JLabel label17;
    private JComboBox<String> comboBox13;
    private JComboBox<String> comboBox16;
    private JComboBox<String> comboBox6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JLabel label18;
    private JLabel label19;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JScrollPane scrollPane4;
    private JTable table4;
    private JLabel label20;
    private JComboBox<String> comboBox14;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private class save extends AbstractAction {
        private save() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
            // Generated using JFormDesigner Evaluation license - Amaim Mumtaz Rathor
            // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
        }

        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(
                    startseite.this,                  // als Parent-Component am besten das Panel selbst
                    "Changes successfully saved!",    // Deine Meldung
                    "Save",                           // Fenstertitel
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    private class DeleteHotelAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        startseite.this,
                        "Please select a hotel in the table first!",
                        "No selection",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String hotelId = table1.getValueAt(selectedRow, 0).toString();
            Object[] options = {"Delete", "Stop"};
            int choice = JOptionPane.showOptionDialog(
                    startseite.this,
                    "Transactional data of this Hotel will also be deleted!",
                    "Are you sure?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1]  // Default-Fokus auf "Stop"
            );

            if (choice == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(
                        startseite.this,
                        "Hotel with ID " + hotelId + " has been deleted.",
                        "Deleted",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
            // Bei Stop (NO_OPTION) passiert nichts
        }
    }

}

