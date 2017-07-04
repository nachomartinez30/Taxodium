import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class Index extends JFrame {

	private JPanel contentPane;
	public JProgressBar progressBar;
	private JTextField textField;
	public String ruta = "";
	public JButton btnCalcular;
	public JButton btnCargar;
	private JLabel lblNumeroDeRegistros;
	private JLabel lblCalculoDeVolumen;
	private JFormattedTextField txtDentroRango;
	private JFormattedTextField txtFueraRango;
	private JFormattedTextField txtSinRango;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Index frame = new Index();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Index() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Index.class.getResource("/image/image_index.png")));
		setResizable(false);
		setTitle("Calculo volumen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		textField = new JTextField();
		textField.setBounds(113, 115, 341, 20);
		textField.setColumns(10);

		btnCargar = new JButton("Cargar");
		btnCargar.setBounds(21, 113, 80, 20);
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarArchivoEntrada();
			}
		});

		btnCalcular = new JButton("Calcular");
		btnCalcular.setBounds(278, 176, 148, 44);
		btnCalcular.setEnabled(false);
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					CalcularVolumen volumen = new CalcularVolumen(progressBar, ruta, btnCalcular, btnCargar,lblNumeroDeRegistros,txtDentroRango,txtFueraRango,txtSinRango);
					btnCargar.setEnabled(false);
					btnCalcular.setEnabled(false);
					volumen.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		progressBar = new JProgressBar();
		progressBar.setBounds(12, 409, 442, 14);
		
		lblNumeroDeRegistros = new JLabel("Numero de registros calculados:");
		lblNumeroDeRegistros.setBounds(12, 387, 442, 16);
		lblNumeroDeRegistros.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		lblCalculoDeVolumen = new JLabel("Calculo de Volumen VRTA");
		lblCalculoDeVolumen.setBounds(0, 12, 466, 51);
		lblCalculoDeVolumen.setFont(new Font("Dialog", Font.BOLD, 27));
		lblCalculoDeVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Index.class.getResource("/image/image_index.png")));
		lblNewLabel.setBounds(new Rectangle(54, 176, 137, 137));
		panel.add(lblNewLabel);
		panel.add(lblNumeroDeRegistros);
		panel.add(btnCalcular);
		panel.add(progressBar);
		panel.add(btnCargar);
		panel.add(textField);
		panel.add(lblCalculoDeVolumen);
		
		txtDentroRango = new JFormattedTextField();
		txtDentroRango.setBounds(354, 246, 80, 20);
		panel.add(txtDentroRango);
		txtDentroRango.setColumns(10);
		
		txtFueraRango = new JFormattedTextField();
		txtFueraRango.setColumns(10);
		txtFueraRango.setBounds(354, 278, 80, 20);
		panel.add(txtFueraRango);
		
		txtSinRango = new JFormattedTextField();
		txtSinRango.setColumns(10);
		txtSinRango.setBounds(354, 310, 80, 20);
		panel.add(txtSinRango);
		
		JLabel lblFormulaConRangos = new JLabel("Registros dentro de rango");
		lblFormulaConRangos.setBounds(203, 248, 148, 16);
		panel.add(lblFormulaConRangos);
		
		JLabel lblRegistrosFueraDe = new JLabel("Registros fuera de rango");
		lblRegistrosFueraDe.setBounds(203, 280, 148, 16);
		panel.add(lblRegistrosFueraDe);
		
		JLabel lblRegistrosSinRango = new JLabel("Registros sin rango");
		lblRegistrosSinRango.setBounds(203, 314, 148, 16);
		panel.add(lblRegistrosSinRango);
	}

	public void cargarArchivoEntrada() {
		JFileChooser fcBaseDatos = new JFileChooser();
		fcBaseDatos.setDialogTitle("Insumo de entrada");
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.csv", "csv");
		fcBaseDatos.setAcceptAllFileFilterUsed(false);
		fcBaseDatos.setFileFilter(filtro);
		int returnVal = fcBaseDatos.showOpenDialog(this);

		try {
			File baseDatos = fcBaseDatos.getSelectedFile();
			ruta = baseDatos.getAbsolutePath();
			int tamanio = ruta.length();
			int cadena = tamanio - 3;
			String extension = ruta.substring(cadena, tamanio);
			//System.out.println(extension);
			if (!extension.equals("csv")) {
				// System.out.println(extension);
				JOptionPane.showMessageDialog(null, "Debe seleccionar un archivo valido a calcular" + "", "Importación",JOptionPane.INFORMATION_MESSAGE);

			} else {

				if (returnVal == JFileChooser.APPROVE_OPTION) { /* OBJETOS */
					textField.setText(ruta);
					btnCalcular.setEnabled(true);
				}
			}
		} catch (Exception e) {
		}
	}
}
