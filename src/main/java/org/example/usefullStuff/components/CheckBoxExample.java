package org.example.usefullStuff.components;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CheckBoxExample extends JFrame {



    public CheckBoxExample() {
        setTitle("CheckBox Example");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

        JCheckBox cb_petFriendly = new JCheckBox("Pet Friendly");
        JCheckBox cb_adultOnly = new JCheckBox("Adult Only");
        JCheckBox cb_hasPool = new JCheckBox("Pool");
        checkBoxes.add(cb_petFriendly);
        checkBoxes.add(cb_adultOnly);
        checkBoxes.add(cb_hasPool);

        JButton btRead = new JButton("Read");
        JTextField textField = new JTextField(20);

        cb_petFriendly.setActionCommand("1");
        cb_adultOnly.setActionCommand("2");
        cb_hasPool.setActionCommand("3");

       // checkBox.setSelected(true); // Set the checkbox to be selected by default

        JPanel panel = new JPanel();
        panel.add(cb_petFriendly);
        panel.add(cb_adultOnly);
        panel.add(cb_hasPool);
        panel.add(btRead);
        panel.add(textField);

        add(panel);


        btRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              ArrayList<Integer> selectedCheckBoxes = new ArrayList<>();

                for(JCheckBox box : checkBoxes){
                    if(box.isSelected()){
                       selectedCheckBoxes.add(Integer.valueOf(box.getActionCommand()));
                    }
                }

                selectedCheckBoxes.forEach(System.out::println);
            }

        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CheckBoxExample example = new CheckBoxExample();
            example.setVisible(true);
        });
    }
}
