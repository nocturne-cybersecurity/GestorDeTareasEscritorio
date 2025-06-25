package DiaX;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GestorTareas extends JFrame {
    private ArrayList<String> tareas;
    private DefaultListModel<String> listModel;
    private JList<String> tareasList;
    private JTextField tareaTextField;
    private JButton agregarBtn, actualizarBtn, eliminarBtn, buscarBtn;

    public GestorTareas() {
        // Configuración básica del JFrame
        setTitle("Gestor de Tareas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar componentes
        tareas = new ArrayList<>();
        listModel = new DefaultListModel<>();
        tareasList = new JList<>(listModel);
        tareaTextField = new JTextField(20);

        // Botones
        agregarBtn = new JButton("Agregar");
        actualizarBtn = new JButton("Actualizar");
        eliminarBtn = new JButton("Eliminar");
        buscarBtn = new JButton("Buscar");

        // Panel superior para entrada de datos
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Tarea:"));
        inputPanel.add(tareaTextField);
        inputPanel.add(agregarBtn);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(actualizarBtn);
        buttonPanel.add(eliminarBtn);
        buttonPanel.add(buscarBtn);

        // Configurar el layout
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(tareasList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Agregar listeners
        agregarBtn.addActionListener(e -> agregarTarea());
        actualizarBtn.addActionListener(e -> actualizarTarea());
        eliminarBtn.addActionListener(e -> eliminarTarea());
        buscarBtn.addActionListener(e -> buscarTarea());

        // Cargar 5 tareas iniciales
        cargarTareasIniciales();
    }

    private void cargarTareasIniciales() {
        String[] tareasIniciales = {"Tarea 1", "Tarea 2", "Tarea 3", "Tarea 4", "Tarea 5"};
        for (String tarea : tareasIniciales) {
            tareas.add(tarea);
            listModel.addElement(tarea);
        }
    }

    private void agregarTarea() {
        String nuevaTarea = tareaTextField.getText().trim();
        if (!nuevaTarea.isEmpty()) {
            tareas.add(nuevaTarea);
            listModel.addElement(nuevaTarea);
            tareaTextField.setText("");
            JOptionPane.showMessageDialog(this, "Tarea agregada con éxito!");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una tarea válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTarea() {
        int selectedIndex = tareasList.getSelectedIndex();
        if (selectedIndex != -1) {
            String nuevaTarea = tareaTextField.getText().trim();
            if (!nuevaTarea.isEmpty()) {
                tareas.set(selectedIndex, nuevaTarea);
                listModel.set(selectedIndex, nuevaTarea);
                tareaTextField.setText("");
                JOptionPane.showMessageDialog(this, "Tarea actualizada con éxito!");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor ingrese una tarea válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una tarea para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarTarea() {
        int selectedIndex = tareasList.getSelectedIndex();
        if (selectedIndex != -1) {
            tareas.remove(selectedIndex);
            listModel.remove(selectedIndex);
            JOptionPane.showMessageDialog(this, "Tarea eliminada con éxito!");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una tarea para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarTarea() {
        String buscar = JOptionPane.showInputDialog(this, "Ingrese la tarea a buscar:");
        if (buscar != null && !buscar.trim().isEmpty()) {
            if (tareas.contains(buscar)) {
                JOptionPane.showMessageDialog(this, "¡La tarea \"" + buscar + "\" está en la lista!");
            } else {
                JOptionPane.showMessageDialog(this, "La tarea \"" + buscar + "\" no está en la lista.");
            }
        }
    }
}