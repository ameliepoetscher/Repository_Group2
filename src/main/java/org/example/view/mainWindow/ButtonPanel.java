package org.example.view.mainWindow;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    public JButton btnAddMasterData;
    public JButton btnAddTransactionalData;

    public ButtonPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        btnAddMasterData = new JButton("Add Hotel");
        btnAddTransactionalData = new JButton("Add Transaction");

        add(btnAddMasterData);
        add(btnAddTransactionalData);
    }
}
