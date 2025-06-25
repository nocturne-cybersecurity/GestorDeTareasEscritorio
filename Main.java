package DiaX;

import javax.swing.*;
public class Main {
public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorTareas gestor = new GestorTareas();
            gestor.setVisible(true);
        });
    }
}


