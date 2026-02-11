package DiaX;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GestorTareas extends JFrame {
    private final ArrayList<String> tareas = new ArrayList<>();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> tareasList = new JList<>(listModel);
    private final JTextField tareaTextField = new JTextField(20);
    private final JButton agregarBtn = crearBotonEstilizado("➕ Agregar", new Color(46, 204, 113));
    private final JButton actualizarBtn = crearBotonEstilizado("✏️ Actualizar", new Color(52, 152, 219));
    private final JButton eliminarBtn = crearBotonEstilizado("🗑️ Eliminar", new Color(231, 76, 60));
    private final JButton buscarBtn = crearBotonEstilizado("🔍 Buscar", new Color(155, 89, 182));
    private Timer animacionTimer;
    private float alpha = 0f;

    public GestorTareas() {
        configurarVentana();
        aplicarEstiloModerno();
        inicializarComponentes();
        configurarAnimacion();
        cargarTareasIniciales();
    }

    private void configurarVentana() {
        setTitle("✨ Gestor de Tareas Moderno");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));
    }

    private void aplicarEstiloModerno() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    private static JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.brighter());
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color.darker(), 1),
                        BorderFactory.createEmptyBorder(9, 19, 9, 19)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
                boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            }
        });

        return boton;
    }

    private void inicializarComponentes() {
        JPanel headerPanel = crearHeaderPanel();
        JPanel centerPanel = crearCenterPanel();
        JPanel footerPanel = crearFooterPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        configurarAcciones();
    }

    private JPanel crearHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(52, 73, 94));

        JLabel titulo = new JLabel("📝 Nueva Tarea");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);

        tareaTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tareaTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        panel.add(titulo);
        panel.add(tareaTextField);
        panel.add(agregarBtn);

        return panel;
    }

    private JPanel crearCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panel.setBackground(Color.WHITE);

        JLabel listaTitulo = new JLabel("📋 Lista de Tareas");
        listaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listaTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        tareasList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tareasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tareasList.setBackground(new Color(250, 250, 250));
        tareasList.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        tareasList.setCellRenderer(new CeldaTareaRenderer());

        JScrollPane scrollPane = new JScrollPane(tareasList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        panel.add(listaTitulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        panel.add(actualizarBtn);
        panel.add(eliminarBtn);
        panel.add(buscarBtn);

        return panel;
    }

    private void configurarAcciones() {
        agregarBtn.addActionListener(e -> animarAccion(this::agregarTarea));
        actualizarBtn.addActionListener(e -> animarAccion(this::actualizarTarea));
        eliminarBtn.addActionListener(e -> animarAccion(this::eliminarTarea));
        buscarBtn.addActionListener(e -> animarAccion(this::buscarTarea));
    }

    private void configurarAnimacion() {
        animacionTimer = new Timer(16, e -> {
            alpha = Math.min(1f, alpha + 0.05f);
            float scale = 0.95f + (0.05f * alpha);

            if (alpha >= 1f) {
                animacionTimer.stop();
            }

            repaint();
        });
    }

    private void animarAccion(Runnable accion) {
        alpha = 0f;
        accion.run();
        animacionTimer.start();
    }

    private void cargarTareasIniciales() {
        String[] tareasIniciales = {"Completar documentación", "Revisar código", "Preparar presentación", "Responder correos", "Planificar semana"};
        for (String tarea : tareasIniciales) {
            tareas.add(tarea);
            listModel.addElement(tarea);
        }
    }

    private void agregarTarea() {
        String nuevaTarea = tareaTextField.getText().trim();
        if (!nuevaTarea.isEmpty()) {
            tareas.add(nuevaTarea);
            listModel.addElement("✨ " + nuevaTarea);
            tareaTextField.setText("");
            mostrarNotificacion("✅ Tarea agregada exitosamente");
        } else {
            mostrarNotificacion("❌ Ingrese una tarea válida", true);
        }
    }

    private void actualizarTarea() {
        int index = tareasList.getSelectedIndex();
        String nuevaTarea = tareaTextField.getText().trim();
        if (index != -1 && !nuevaTarea.isEmpty()) {
            tareas.set(index, nuevaTarea);
            listModel.set(index, "✏️ " + nuevaTarea);
            tareaTextField.setText("");
            mostrarNotificacion("📝 Tarea actualizada");
        } else {
            mostrarNotificacion("⚠️ Seleccione una tarea y escriba un nuevo nombre", true);
        }
    }

    private void eliminarTarea() {
        int index = tareasList.getSelectedIndex();
        if (index != -1) {
            String tareaEliminada = tareas.remove(index);
            listModel.remove(index);
            mostrarNotificacion("🗑️ Eliminada: " + tareaEliminada);
        } else {
            mostrarNotificacion("⚠️ Seleccione una tarea para eliminar", true);
        }
    }

    private void buscarTarea() {
        String buscar = JOptionPane.showInputDialog(this, "🔍 Buscar tarea:", "Búsqueda", JOptionPane.QUESTION_MESSAGE);
        if (buscar != null && !buscar.trim().isEmpty()) {
            boolean encontrada = tareas.stream().anyMatch(t -> t.toLowerCase().contains(buscar.toLowerCase()));

            if (encontrada) {
                mostrarNotificacion("✔️ Tarea encontrada en la lista");
                tareasList.setSelectedIndex(tareas.indexOf(buscar));
                tareasList.ensureIndexIsVisible(tareasList.getSelectedIndex());
            } else {
                mostrarNotificacion("❌ Tarea no encontrada", true);
            }
        }
    }

    private void mostrarNotificacion(String mensaje) {
        mostrarNotificacion(mensaje, false);
    }

    private void mostrarNotificacion(String mensaje, boolean esError) {
        Color color = esError ? new Color(231, 76, 60) : new Color(46, 204, 113);
        JOptionPane pane = new JOptionPane(mensaje, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(this, esError ? "Advertencia" : "Éxito");
        dialog.getContentPane().setBackground(color);
        dialog.setVisible(true);
    }

    private static class CeldaTareaRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            label.setIcon(new Icon() {
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    g.setColor(new Color(52, 152, 219));
                    g.fillOval(x, y + 4, 8, 8);
                }
                public int getIconWidth() { return 12; }
                public int getIconHeight() { return 16; }
            });

            if (isSelected) {
                label.setBackground(new Color(52, 152, 219, 50));
                label.setForeground(new Color(52, 152, 219));
            }

            return label;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (alpha > 0) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha * 0.3f));
            g2d.setColor(new Color(52, 152, 219));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorTareas gestor = new GestorTareas();
            gestor.setVisible(true);
        });
    }
}
