import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.plaf.SliderUI;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

public class CalcularVolumen extends SwingWorker<Integer, String> {
	public JProgressBar barraProgreso;
	public JButton btnCalcular, btnCargar;
	public JTextField txtDentroRango;
	public JTextField txtFueraRango;
	public JTextField txtSinRango;
	public JLabel numeroRegistros;
	public String ruta, ecuacion = null;
	public double Rango_AT_Est_CMAX, Rango_AT_Est_CMIN, Rango_DN_Est_CMAX, Rango_DN_Est_CMIN;
	// public String Rango_AT_Est_CMAX_String, Rango_AT_Est_CMIN_String,
	// Rango_DN_Est_CMAX_String,Rango_DN_Est_CMIN_String;
	public double RESULTADO = 0;
	public int iteradorFueraRangos = 0, iteradorDentroRangos = 0, iteradorNoFormulaRango = 0;

	boolean resultado = false;

	public CalcularVolumen(JProgressBar barraProgreso, String ruta, JButton btnCalcular, JButton btnCargar,
			JLabel numeroRegistros, JTextField txtDentroRango, JTextField txtFueraRango, JTextField txtSinRango) {
		super();
		this.btnCalcular = btnCalcular;
		this.btnCargar = btnCargar;
		this.barraProgreso = barraProgreso;
		this.numeroRegistros = numeroRegistros;
		this.txtDentroRango = txtDentroRango;
		this.txtFueraRango = txtFueraRango;
		this.txtSinRango = txtSinRango;
		this.ruta = ruta;
	}

	@Override
	public Integer doInBackground() throws Exception {
		barraProgreso.setIndeterminate(true);
		recoleccionDatos(ruta);
		barraProgreso.setIndeterminate(false);
		btnCalcular.setEnabled(false);
		btnCargar.setEnabled(true);
		return 0;
	}

	public void recoleccionDatos(String ruta) {
		int i = 0,registros=0;
		double AT_Est_C = 0, DN_Est_C = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Calendar cal = Calendar.getInstance();
		// System.out.println(dateFormat.format(cal)); //2016/11/16 12:08:43

		String rutaArchivo = ruta;
		String Conglomerado, cgl_sit_arb, Anio, IdEstado, Estado, Formato, Sitio, Registro, Arbol, Familia_APG,
				Genero_APG, Especie_APG, Categoria_Infra_APG, Infra_APG, Condicion, Forma_Biologica_1,
				NombreCientifico_APG, CVE_ECUACION, Prioridad_Arbol, Nivel = null, fuente, Rango_Alt, Rango_Diam;

		// String numeros = "1,2,3,4,5,6";sustituir por tabop
		try {
			// Abrimos el archivo
			FileReader leerarchivo = new FileReader(rutaArchivo);
			BufferedReader buffer = new BufferedReader(leerarchivo);
			String linea = null;

			// archivo de escritura
			File f;
			f = new File(System.getProperty("user.home") + "\\Desktop\\ResultadoVolumenes_" + "2017" + ".csv");
			FileWriter w = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);
			wr.write(
					"Conglomerado,cgl_sit_arb,Anio,IdEstado,Estado,Formato,Sitio,Registro,Arbol,Familia_APG,Genero_APG,Especie_APG,Categoria_Infra_APG,Infra_APG,Condicion,Forma_Biologica_1,NombreCientifico_APG,AT_Est_C,DN_Est_C,CVE_ECUACION,Ecuacion,Fuente,Rango_Alt,Rango_Diam,Prioridad_Arbol,Nivel,VolumenVRTA:m3\n");// escribimos

			while ((linea = buffer.readLine()) != null) {
				i++;
				if (i == 1) {
					System.out.println(linea);
				} else {
					registros++;
					StringTokenizer tokensaux = new StringTokenizer(linea, ",");
					while (tokensaux.hasMoreTokens()) {

						Conglomerado = tokensaux.nextToken();
						cgl_sit_arb = tokensaux.nextToken();
						Anio = tokensaux.nextToken();
						IdEstado = tokensaux.nextToken();
						Estado = tokensaux.nextToken();
						Formato = tokensaux.nextToken();
						Sitio = tokensaux.nextToken();
						Registro = tokensaux.nextToken();
						Arbol = tokensaux.nextToken();
						Familia_APG = tokensaux.nextToken();
						Genero_APG = tokensaux.nextToken();
						Especie_APG = tokensaux.nextToken();
						Categoria_Infra_APG = tokensaux.nextToken();
						Infra_APG = tokensaux.nextToken();
						Condicion = tokensaux.nextToken();
						Forma_Biologica_1 = tokensaux.nextToken();
						NombreCientifico_APG = tokensaux.nextToken();
						AT_Est_C = Double.parseDouble(tokensaux.nextToken());
						DN_Est_C = Double.parseDouble(tokensaux.nextToken());
						CVE_ECUACION = tokensaux.nextToken();
						fuente = tokensaux.nextToken();
						Rango_Alt = tokensaux.nextToken();
						Rango_Diam = tokensaux.nextToken();
						Prioridad_Arbol = tokensaux.nextToken();
						/* REVISAR SI ES UNA ECUACION CON RANGOS */

						switch (CVE_ECUACION) {
						case "Oyamelz2y3_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 97.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "Pinoz2y3_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 97.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "T0z1_Pue":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T0z1_SLP":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T0z3_Hgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T1_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T6z4_Gro":/* Especial */
							resultado = true;
							Rango_DN_Est_CMIN = 12.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_AT_Est_CMAX = 47.5;
							break;
						case "T9z5_Gro":/* Especial */
							resultado = true;
							Rango_DN_Est_CMIN = 12.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_AT_Est_CMAX = 47.5;
							break;
						case "T12_Gro":/* Especial */
							resultado = true;
							Rango_DN_Est_CMIN = 12.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_AT_Est_CMAX = 47.5;
							break;
						case "T10z5_Gro":/* Especial */
							resultado = true;
							Rango_DN_Est_CMIN = 12.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_AT_Est_CMAX = 47.5;
							break;
						case "T3z2_Gro":/* Especial */
							resultado = true;
							Rango_DN_Est_CMIN = 12.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_AT_Est_CMAX = 47.5;
							break;
						case "T2z2_Gro":/* Especial */
							resultado = true;
							Rango_DN_Est_CMIN = 12.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_AT_Est_CMAX = 47.5;
							break;
						case "T4z3_Gro":/* Especial */
							resultado = true;
							Rango_DN_Est_CMIN = 12.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_AT_Est_CMAX = 47.5;
							break;
						case "T5z3_Gro":/* Especial */
							resultado = true;
							Rango_DN_Est_CMIN = 12.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_AT_Est_CMAX = 47.5;
							break;
						case "T1z1_Gro":/* Especial */
							resultado = true;
							Rango_DN_Est_CMIN = 12.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_AT_Est_CMAX = 47.5;
							break;

						case "T10_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T10z4_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T11_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T11_Gro":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "T11_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T12_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T12_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T12_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T13_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T13_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T14_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T14_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T15_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T15_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T16_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T16_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T1z1_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T1z1_Tlax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T2_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T2_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T2z1_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T2z2_Tlax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T3_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T3_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T3z2_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T3z3_Tlax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T4_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T4_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T4z2_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T4z3_Tlax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T5_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T5_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T5z2_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T6_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T6_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T6z3_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T7_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T7_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T7z3_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T8_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T8_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T8z4_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T9_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T9_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "T9z4_Mex":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TA_Hgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TA_NL":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TA_Qro":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TA_Tam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TA1_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TA1z3_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TA2_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TA2z2_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TAz2_SLP":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TAz2y3_Pue":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB_Coah":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB_Hgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB_NL":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB_Qro":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB_Tam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB1_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB1z2y3_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB2_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB2z2y3_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB3_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB3z2_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB4_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB5_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB6_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TB7_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TBz2_SLP":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TBz2y3_Pue":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TC_Coah":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TC_Gto":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TC_Hgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TC_NL":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TC_Qro":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TC_Tam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TCz2_SLP":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TD_Coah":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TD_Gto":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TD_NL":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TD_Qro":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TDz2_SLP":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TE_Hgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TE_NL":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TE_Qro":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TG_Gto":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TG_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TG_Qro":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TG_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TGz1_Pue":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TGz1y2_Hgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TGz2_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TGz2_SLP":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TGz2y3_Pue":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TGz3_Hgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TH_Qro":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "THz2y3_Pue":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TI_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TI_Hgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TI_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TI_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TII_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "Tii_Hgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TII_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TII_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIII_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIII_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIIz1_Pue":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIV_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIV_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;

						case "TIV_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIV_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIX_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIX_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIX-S_Pue":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIz2_Chis":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TIz2_SLP":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TPino_BC":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "TPino_Chih":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "TPino_Dgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 42.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_DN_Est_CMAX = 112.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "TPino_Jal":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 42.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "TPino_Sin":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 42.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "TPino_Zac":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 42.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "TPinoNay":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 42.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "TQue_Jal":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 22.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "TQue_Nay":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 22.5;
							Rango_AT_Est_CMIN = 7.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 12.5;
							break;
						case "TV_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TV_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TV_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TVI_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TVI_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TVI_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TVI_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TVII_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TVII_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TVII_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TVIII_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TVIII_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TVIII_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "Tvol1_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "Tvol10_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "Tvol11_QR":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TX_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TX_Hgo":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TX_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXI_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXI_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXII_cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXII_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXII_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXIII_Cam":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXIII_Tab":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXIV_Oax":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXV_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXVI_Ver":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;
						case "TXVIz1_Pue":/* Especial */
							resultado = true;
							Rango_AT_Est_CMAX = 47.5;
							Rango_AT_Est_CMIN = 2.5;
							Rango_DN_Est_CMAX = 132.5;
							Rango_DN_Est_CMIN = 7.5;
							break;

						default:
							resultado = false;
							iteradorNoFormulaRango++;
							break;
						}

						if (resultado == true) {// si tiene rango
							if (AT_Est_C >= Rango_AT_Est_CMIN && AT_Est_C <= Rango_AT_Est_CMAX
									&& DN_Est_C <= Rango_DN_Est_CMAX && DN_Est_C >= Rango_DN_Est_CMIN) {
								RESULTADO = calculoVolumen(CVE_ECUACION, DN_Est_C, AT_Est_C);
								iteradorDentroRangos++;
							} else {
								RESULTADO = 0.7854 * Math.pow(DN_Est_C / 100, 2) * AT_Est_C * 0.3;
								CVE_ECUACION = "Coef_Morf_03";
								Prioridad_Arbol = "60";
								iteradorFueraRangos++;
							}
						}
						if (resultado == false) {
							RESULTADO = calculoVolumen(CVE_ECUACION, DN_Est_C, AT_Est_C);
						}

						if (Integer.parseInt(Prioridad_Arbol) >= 1 && Integer.parseInt(Prioridad_Arbol) <= 4) {
							Nivel = "1";
						}

						if (Integer.parseInt(Prioridad_Arbol) >= 5 && Integer.parseInt(Prioridad_Arbol) <= 12) {
							Nivel = "2";
						}

						if (Integer.parseInt(Prioridad_Arbol) >= 13 && Integer.parseInt(Prioridad_Arbol) <= 16) {
							Nivel = "3";
						}

						if (Integer.parseInt(Prioridad_Arbol) >= 17 && Integer.parseInt(Prioridad_Arbol) <= 24) {
							Nivel = "4";
						}

						if (Integer.parseInt(Prioridad_Arbol) >= 25 && Integer.parseInt(Prioridad_Arbol) <= 28) {
							Nivel = "5";
						}

						if (Integer.parseInt(Prioridad_Arbol) >= 29 && Integer.parseInt(Prioridad_Arbol) <= 36) {
							Nivel = "6";
						}

						if (Integer.parseInt(Prioridad_Arbol) >= 37 && Integer.parseInt(Prioridad_Arbol) <= 39) {
							Nivel = "7";
						}

						if (Integer.parseInt(Prioridad_Arbol) >= 40 && Integer.parseInt(Prioridad_Arbol) <= 47) {
							Nivel = "8";
						}

						if (Integer.parseInt(Prioridad_Arbol) >= 48 && Integer.parseInt(Prioridad_Arbol) <= 50) {
							Nivel = "9";
						}

						if (Integer.parseInt(Prioridad_Arbol) >= 51 && Integer.parseInt(Prioridad_Arbol) <= 58) {
							Nivel = "10";
						}

						if (Integer.parseInt(Prioridad_Arbol) == 59) {
							Nivel = "11";
						}

						if (Integer.parseInt(Prioridad_Arbol) == 60) {
							Nivel = "12";
						}

						if (Rango_AT_Est_CMAX == 0) {
							Rango_Alt = "NULL";
						}

						if (Rango_DN_Est_CMAX == 0) {
							Rango_Diam = "NULL";
						}

						ecuacion = getEcuacion(CVE_ECUACION);

						wr.append(Conglomerado + "," + cgl_sit_arb + "," + Anio + "," + IdEstado + "," + Estado + ","
								+ Formato + "," + Sitio + "," + Registro + "," + Arbol + "," + Familia_APG + ","
								+ Genero_APG + "," + Especie_APG + "," + Categoria_Infra_APG + "," + Infra_APG + ","
								+ Condicion + "," + Forma_Biologica_1 + "," + NombreCientifico_APG + "," + AT_Est_C
								+ "," + DN_Est_C + "," + CVE_ECUACION + "," + ecuacion + "," + fuente + "," + Rango_Alt
								+ "," + Rango_Diam + "," + Prioridad_Arbol + "," + Nivel + "," + RESULTADO + ","
								+ "\n");

						Rango_AT_Est_CMAX = 0;
						Rango_AT_Est_CMIN = 0;
						Rango_DN_Est_CMAX = 0;
						Rango_DN_Est_CMIN = 0;
						resultado = false;
						CVE_ECUACION = "";
						
						numeroRegistros.setText("Numero de registros calculados: " + registros);
					}
					// Cerramos el archivo
				}

			}
			txtDentroRango.setText(Integer.toString(iteradorDentroRangos));
			txtFueraRango.setText(Integer.toString(iteradorFueraRangos));
			txtSinRango.setText(Integer.toString(iteradorNoFormulaRango));
			wr.close();
			bw.close();
			buffer.close();
			JOptionPane.showMessageDialog(null, "Terminó satisfactoriamente");

		} catch (Exception e) { // Catch de excepciones
			System.err.println("Ocurrio un error: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Hubo un error " + e.getMessage());
			e.printStackTrace();
		}

	}

	public double calculoVolumen(String CVE_ECUACION, double DN_EC, double AT_EC) {
		double res = 0;
		switch (CVE_ECUACION) {
		case "Coef_Morf_03":
			res = 0.7854 * Math.pow(DN_EC / 100, 2) * AT_EC * 0.3;
			break;
		case "Oyamelz2y3_Ver":
			res = Math.exp(-9.64447683 + 1.90134225 * Math.log(DN_EC) + 1.03162716 * Math.log(AT_EC));
			break;
		case "Pinoz2y3_Ver":
			res = Math.exp(-9.29401031 + 2.07031121 * Math.log(DN_EC) + 0.68219841 * Math.log(AT_EC));
			break;
		case "T_Chih_1":
			res = 0.00007 * Math.pow(DN_EC, 1.86212) * Math.pow(AT_EC, 0.99213);
			break;
		case "T_Chih_10":
			res = 0.00014 * Math.pow(DN_EC, 1.981) * Math.pow(AT_EC, 0.50526);
			break;
		case "T_Chih_11":
			res = 0.00005 * Math.pow(DN_EC, 1.8743) * Math.pow(AT_EC, 1.01781);
			break;
		case "T_Chih_2":
			res = 0.00004 * Math.pow(DN_EC, 1.95737) * Math.pow(AT_EC, 1.01871);
			break;
		case "T_Chih_3":
			res = 0.00006 * Math.pow(DN_EC, 1.67728) * Math.pow(AT_EC, 1.24808);
			break;
		case "T_Chih_4":
			res = 0.00004 * Math.pow(DN_EC, 1.95933) * Math.pow(AT_EC, 1.00994);
			break;
		case "T_Chih_5":
			res = 0.0001 * Math.pow(DN_EC, 1.65927) * Math.pow(AT_EC, 1.10781);
			break;
		case "T_Chih_6":
			res = 0.00008 * Math.pow(DN_EC, 2.11183) * Math.pow(AT_EC, 0.58883);
			break;
		case "T_Chih_7":
			res = 0.00004 * Math.pow(DN_EC, 1.98476) * Math.pow(AT_EC, 0.98928);
			break;
		case "T_Chih_8":
			res = 0.00006 * Math.pow(DN_EC, 2.25209) * Math.pow(AT_EC, 0.4497);
			break;
		case "T_Chih_9":
			res = 0.00063 * Math.pow(DN_EC, 1.32235) * Math.pow(AT_EC, 0.7039);
			break;
		case "T_Dgo_1":
			res = 0.00006 * Math.pow(DN_EC, 1.93438) * Math.pow(AT_EC, 0.98688);
			break;
		case "T_Dgo_10":
			res = 0.00006 * Math.pow(DN_EC, 1.8562) * Math.pow(AT_EC, 1.06672);
			break;
		case "T_Dgo_11":
			res = 0.00003 * Math.pow(DN_EC, 2.1406) * Math.pow(AT_EC, 0.98974);
			break;
		case "T_Dgo_12":
			res = 0.00005 * Math.pow(DN_EC, 1.98268) * Math.pow(AT_EC, 0.97952);
			break;
		case "T_Dgo_13":
			res = 0.00014 * Math.pow(DN_EC, 1.57309) * Math.pow(AT_EC, 1.11458);
			break;
		case "T_Dgo_14":
			res = 0.00006 * Math.pow(DN_EC, 1.90009) * Math.pow(AT_EC, 0.98458);
			break;
		case "T_Dgo_15":
			res = 0.00005 * Math.pow(DN_EC, 2.02305) * Math.pow(AT_EC, 0.88588);
			break;
		case "T_Dgo_16":
			res = 0.00004 * Math.pow(DN_EC, 2.03023) * Math.pow(AT_EC, 0.98222);
			break;
		case "T_Dgo_17":
			res = 0.00006 * Math.pow(DN_EC, 1.86961) * Math.pow(AT_EC, 0.97764);
			break;
		case "T_Dgo_18":
			res = 0.00006 * Math.pow(DN_EC, 1.7906) * Math.pow(AT_EC, 1.11575);
			break;
		case "T_Dgo_19":
			res = 0.00004 * Math.pow(DN_EC, 1.73417) * Math.pow(AT_EC, 1.27799);
			break;
		case "T_Dgo_2":
			res = 0.00006 * Math.pow(DN_EC, 1.97044) * Math.pow(AT_EC, 0.91647);
			break;
		case "T_Dgo_20":
			res = 0.00005 * Math.pow(DN_EC, 1.97485) * Math.pow(AT_EC, 0.92877);
			break;
		case "T_Dgo_21":
			res = 0.00003 * Math.pow(DN_EC, 1.96472) * Math.pow(AT_EC, 1.10172);
			break;
		case "T_Dgo_22":
			res = 0.00005 * Math.pow(DN_EC, 1.98158) * Math.pow(AT_EC, 0.88795);
			break;
		case "T_Dgo_23":
			res = 0.00006 * Math.pow(DN_EC, 1.97289) * Math.pow(AT_EC, 0.83957);
			break;
		case "T_Dgo_24":
			res = 0.0001 * Math.pow(DN_EC, 1.818) * Math.pow(AT_EC, 0.82401);
			break;
		case "T_Dgo_25":
			res = 0.00024 * Math.pow(DN_EC, 1.58917) * Math.pow(AT_EC, 0.77994);
			break;
		case "T_Dgo_27":
			res = 0.00008 * Math.pow(DN_EC, 1.87607) * Math.pow(AT_EC, 0.95211);
			break;
		case "T_Dgo_28":
			res = 0.00006 * Math.pow(DN_EC, 1.98226) * Math.pow(AT_EC, 0.84224);
			break;
		case "T_Dgo_3":
			res = 0.00006 * Math.pow(DN_EC, 1.98909) * Math.pow(AT_EC, 0.90204);
			break;
		case "T_Dgo_4":
			res = 0.00006 * Math.pow(DN_EC, 1.96882) * Math.pow(AT_EC, 0.8985);
			break;
		case "T_Dgo_5":
			res = 0.00007 * Math.pow(DN_EC, 1.91236) * Math.pow(AT_EC, 0.93298);
			break;
		case "T_Dgo_6":
			res = 0.00006 * Math.pow(DN_EC, 1.93753) * Math.pow(AT_EC, 0.95704);
			break;
		case "T_Dgo_7":
			res = 0.00009 * Math.pow(DN_EC, 1.89329) * Math.pow(AT_EC, 0.86226);
			break;
		case "T_Dgo_8":
			res = 0.00007 * Math.pow(DN_EC, 1.91367) * Math.pow(AT_EC, 0.89469);
			break;
		case "T_Dgo_9":
			res = 0.00006 * Math.pow(DN_EC, 1.89932) * Math.pow(AT_EC, 1.00996);
			break;
		case "T_Jal_1":
			res = 0.00005 * Math.pow(DN_EC, 2.02355) * Math.pow(AT_EC, 0.89358);
			break;
		case "T_Jal_10":
			res = 0.00005 * Math.pow(DN_EC, 2.068) * Math.pow(AT_EC, 0.83963);
			break;
		case "T_Jal_11":
			res = 0.00008 * Math.pow(DN_EC, 1.83084) * Math.pow(AT_EC, 0.94669);
			break;
		case "T_Jal_12":
			res = 0.00006 * Math.pow(DN_EC, 2.02788) * Math.pow(AT_EC, 0.78274);
			break;
		case "T_Jal_13":
			res = 0.00005 * Math.pow(DN_EC, 2.10799) * Math.pow(AT_EC, 0.70889);
			break;
		case "T_Jal_14":
			res = 0.00005 * Math.pow(DN_EC, 2.08151) * Math.pow(AT_EC, 0.78693);
			break;
		case "T_Jal_15":
			res = 0.00004 * Math.pow(DN_EC, 1.9704) * Math.pow(AT_EC, 0.97487);
			break;
		case "T_Jal_16":
			res = 0.00004 * Math.pow(DN_EC, 2.1692) * Math.pow(AT_EC, 0.78086);
			break;
		case "T_Jal_17":
			res = 0.00005 * Math.pow(DN_EC, 1.99451) * Math.pow(AT_EC, 0.8596);
			break;
		case "T_Jal_18":
			res = 0.00004 * Math.pow(DN_EC, 2.04581) * Math.pow(AT_EC, 0.84152);
			break;
		case "T_Jal_19":
			res = 0.00006 * Math.pow(DN_EC, 2.05695) * Math.pow(AT_EC, 0.67161);
			break;
		case "T_Jal_2":
			res = 0.00004 * Math.pow(DN_EC, 2.07732) * Math.pow(AT_EC, 0.87754);
			break;
		case "T_Jal_3":
			res = 0.00004 * Math.pow(DN_EC, 1.95382) * Math.pow(AT_EC, 1.01814);
			break;
		case "T_Jal_4":
			res = 0.00003 * Math.pow(DN_EC, 2.09697) * Math.pow(AT_EC, 0.91094);
			break;
		case "T_Jal_5":
			res = 0.00007 * Math.pow(DN_EC, 2.13047) * Math.pow(AT_EC, 0.64784);
			break;
		case "T_Jal_6":
			res = 0.00004 * Math.pow(DN_EC, 2.04734) * Math.pow(AT_EC, 0.94933);
			break;
		case "T_Jal_7":
			res = 0.00004 * Math.pow(DN_EC, 2.01679) * Math.pow(AT_EC, 1.00059);
			break;
		case "T_Jal_8":
			res = 0.00005 * Math.pow(DN_EC, 2.03386) * Math.pow(AT_EC, 0.83557);
			break;
		case "T_Jal_9":
			res = 0.00004 * Math.pow(DN_EC, 2.05692) * Math.pow(AT_EC, 0.92422);
			break;
		case "T_Oax_1":
			res = 0.00006 * Math.pow(DN_EC, 1.99373) * Math.pow(AT_EC, 0.84739);
			break;
		case "T_Oax_10":
			res = 0.00006 * Math.pow(DN_EC, 1.88959) * Math.pow(AT_EC, 0.96425);
			break;
		case "T_Oax_11":
			res = 0.00005 * Math.pow(DN_EC, 1.9735) * Math.pow(AT_EC, 0.92129);
			break;
		case "T_Oax_12":
			res = 0.00005 * Math.pow(DN_EC, 1.93129) * Math.pow(AT_EC, 1.01585);
			break;
		case "T_Oax_13":
			res = 0.00006 * Math.pow(DN_EC, 1.91862) * Math.pow(AT_EC, 0.95512);
			break;
		case "T_Oax_14":
			res = 0.00005 * Math.pow(DN_EC, 1.89422) * Math.pow(AT_EC, 0.98998);
			break;
		case "T_Oax_15":
			res = 0.00004 * Math.pow(DN_EC, 1.89668) * Math.pow(AT_EC, 1.0223);
			break;
		case "T_Oax_18":
			res = 0.00005 * Math.pow(DN_EC, 1.82449) * Math.pow(AT_EC, 1.06723);
			break;
		case "T_Oax_19":
			res = 0.00006 * Math.pow(DN_EC, 1.82403) * Math.pow(AT_EC, 0.962);
			break;
		case "T_Oax_2":
			res = 0.00005 * Math.pow(DN_EC, 1.92726) * Math.pow(AT_EC, 0.94878);
			break;
		case "T_Oax_20":
			res = 0.00007 * Math.pow(DN_EC, 1.77028) * Math.pow(AT_EC, 0.98424);
			break;
		case "T_Oax_3":
			res = 0.00005 * Math.pow(DN_EC, 1.89571) * Math.pow(AT_EC, 0.98306);
			break;
		case "T_Oax_4":
			res = 0.00005 * Math.pow(DN_EC, 1.90989) * Math.pow(AT_EC, 0.99571);
			break;
		case "T_Oax_5":
			res = 0.00005 * Math.pow(DN_EC, 1.91295) * Math.pow(AT_EC, 0.97195);
			break;
		case "T_Oax_6":
			res = 0.00005 * Math.pow(DN_EC, 1.92894) * Math.pow(AT_EC, 0.96078);
			break;
		case "T_Oax_7":
			res = 0.00006 * Math.pow(DN_EC, 1.87224) * Math.pow(AT_EC, 0.99003);
			break;
		case "T_Oax_8":
			res = 0.00007 * Math.pow(DN_EC, 1.90765) * Math.pow(AT_EC, 0.89216);
			break;
		case "T_Oax_9":
			res = 0.00006 * Math.pow(DN_EC, 1.98654) * Math.pow(AT_EC, 0.89281);
			break;
		case "T0z1_Pue":
			res = Math.exp(-9.80397476 + 1.91686765 * Math.log(DN_EC) + 1.02474479 * Math.log(AT_EC));
			break;
		case "T0z1_SLP":
			res = Math.exp(-9.66777918 + 1.8605487 * Math.log(DN_EC) + 1.02318950 * Math.log(AT_EC));
			break;
		case "T0z3_Hgo":
			res = Math.exp(-9.61229531 + 1.86381485 * Math.log(DN_EC) + 0.99550933 * Math.log(AT_EC));
			break;
		case "T1_Chis":
			res = Math.exp(-9.88038392 + 1.97088088 * Math.log(DN_EC) + 1.00098368 * Math.log(AT_EC));
			break;
		case "T1_U1001_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.82398) * Math.pow(AT_EC, 1.03686);
			break;
		case "T1_U1002_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 1.92896) * Math.pow(AT_EC, 1.04616);
			break;
		case "T1_U1003_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.97681) * Math.pow(AT_EC, 0.9112);
			break;
		case "T1_U1004_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.9281) * Math.pow(AT_EC, 0.90225);
			break;
		case "T1_U1005_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 1.96185) * Math.pow(AT_EC, 0.97014);
			break;
		case "T1_U1006_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.86811) * Math.pow(AT_EC, 1.06214);
			break;
		case "T1_U1007_Dgo":
			res = 0.00013 * Math.pow(DN_EC, 1.75073) * Math.pow(AT_EC, 0.92895);
			break;
		case "T1_U1008_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 2.07454) * Math.pow(AT_EC, 0.89233);
			break;
		case "T1_U1009_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 1.92947) * Math.pow(AT_EC, 1.02125);
			break;
		case "T1_U1010_Dgo":
			res = 0.00003 * Math.pow(DN_EC, 2.05061) * Math.pow(AT_EC, 0.99619);
			break;
		case "T1_U1011_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 1.99897) * Math.pow(AT_EC, 1.05569);
			break;
		case "T1_U1201_Gro":
			res = 0.00009 * Math.pow(DN_EC, 2.00426) * Math.pow(AT_EC, 0.66249);
			break;
		case "T1_U1203_Gro":
			res = 0.00004 * Math.pow(DN_EC, 2.03025) * Math.pow(AT_EC, 0.94754);
			break;
		case "T1_U1204_Gro":
			res = 0.00009 * Math.pow(DN_EC, 1.65091) * Math.pow(AT_EC, 1.10287);
			break;
		case "T1_U1205_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.98993) * Math.pow(AT_EC, 0.86172);
			break;
		case "T1_U1302_Hgo":
			res = 0.00003 * Math.pow(DN_EC, 1.69394) * Math.pow(AT_EC, 1.41751);
			break;
		case "T1_U1303_Hgo":
			res = 0.00012 * Math.pow(DN_EC, 1.98134) * Math.pow(AT_EC, 0.6307);
			break;
		case "T1_U1305_Hgo":
			res = 0.00004 * Math.pow(DN_EC, 1.7432) * Math.pow(AT_EC, 1.2633);
			break;
		case "T1_U1401_Jal":
			res = 0.00007 * Math.pow(DN_EC, 1.98723) * Math.pow(AT_EC, 0.84818);
			break;
		case "T1_U1404_Jal":
			res = 0.00005 * Math.pow(DN_EC, 2.10644) * Math.pow(AT_EC, 0.80051);
			break;
		case "T1_U1405_Jal":
			res = 0.00003 * Math.pow(DN_EC, 2.02565) * Math.pow(AT_EC, 1.00268);
			break;
		case "T1_U1406_Jal":
			res = 0.00005 * Math.pow(DN_EC, 2.09078) * Math.pow(AT_EC, 0.85543);
			break;
		case "T1_U1407_Jal":
			res = 0.00004 * Math.pow(DN_EC, 1.99977) * Math.pow(AT_EC, 0.97769);
			break;
		case "T1_U1408_Jal":
			res = 0.00004 * Math.pow(DN_EC, 2.05524) * Math.pow(AT_EC, 0.90206);
			break;
		case "T1_U1410_Jal":
			res = 0.00006 * Math.pow(DN_EC, 1.79089) * Math.pow(AT_EC, 1.14268);
			break;
		case "T1_U1505_Mex":
			res = 0.00003 * Math.pow(DN_EC, 2.05097) * Math.pow(AT_EC, 0.99643);
			break;
		case "T1_U1507_Mex":
			res = 0.00004 * Math.pow(DN_EC, 1.87703) * Math.pow(AT_EC, 1.0933);
			break;
		case "T1_U1508_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.82108) * Math.pow(AT_EC, 1.05985);
			break;
		case "T1_U1509_Mex":
			res = 0.00006 * Math.pow(DN_EC, 2.11578) * Math.pow(AT_EC, 0.69756);
			break;
		case "T1_U1510_Mex":
			res = 0.00008 * Math.pow(DN_EC, 1.82171) * Math.pow(AT_EC, 0.93929);
			break;
		case "T1_U1601_Mich":
			res = 0.00009 * Math.pow(DN_EC, 1.88655) * Math.pow(AT_EC, 0.80173);
			break;
		case "T1_U1603_Mich":
			res = 0.00007 * Math.pow(DN_EC, 1.91555) * Math.pow(AT_EC, 0.88843);
			break;
		case "T1_U1604_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.59223) * Math.pow(AT_EC, 1.32999);
			break;
		case "T1_U1605_Mich":
			res = 0.00009 * Math.pow(DN_EC, 1.82578) * Math.pow(AT_EC, 0.92278);
			break;
		case "T1_U1607_Mich":
			res = 0.00008 * Math.pow(DN_EC, 2.06498) * Math.pow(AT_EC, 0.6612);
			break;
		case "T1_U1608_Mich":
			res = 0.00007 * Math.pow(DN_EC, 1.77662) * Math.pow(AT_EC, 1.02641);
			break;
		case "T1_U1610_Mich":
			res = 0.00007 * Math.pow(DN_EC, 1.7253) * Math.pow(AT_EC, 1.12568);
			break;
		case "T1_U2001_Oax":
			res = 0.00004 * Math.pow(DN_EC, 2.01746) * Math.pow(AT_EC, 0.91953);
			break;
		case "T1_U2003_Oax":
			res = 0.00005 * Math.pow(DN_EC, 2.14361) * Math.pow(AT_EC, 0.73119);
			break;
		case "T1_U2007_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.99813) * Math.pow(AT_EC, 0.84388);
			break;
		case "T1_U2008_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.99699) * Math.pow(AT_EC, 0.76895);
			break;
		case "T1_U2009_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.94358) * Math.pow(AT_EC, 0.88488);
			break;
		case "T1_U2010_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.95669) * Math.pow(AT_EC, 0.84675);
			break;
		case "T1_U2012_Oax":
			res = 0.00006 * Math.pow(DN_EC, 2.0388) * Math.pow(AT_EC, 0.78043);
			break;
		case "T1_U2013_Oax":
			res = 0.00003 * Math.pow(DN_EC, 2.05309) * Math.pow(AT_EC, 0.95747);
			break;
		case "T1_U2014_Oax":
			res = 0.00004 * Math.pow(DN_EC, 2.10964) * Math.pow(AT_EC, 0.88324);
			break;
		case "T1_U2101_Pue":
			res = 0.00007 * Math.pow(DN_EC, 1.7611) * Math.pow(AT_EC, 1.02855);
			break;
		case "T1_U2103_Pue":
			res = 0.0001 * Math.pow(DN_EC, 1.7097) * Math.pow(AT_EC, 0.96196);
			break;
		case "T1_U2105_Pue":
			res = 0.00004 * Math.pow(DN_EC, 1.89329) * Math.pow(AT_EC, 1.04186);
			break;
		case "T1_U2107_Pue":
			res = 0.00005 * Math.pow(DN_EC, 1.78432) * Math.pow(AT_EC, 1.0895);
			break;
		case "T1_U2108_Pue":
			res = 0.00005 * Math.pow(DN_EC, 1.4906) * Math.pow(AT_EC, 1.50494);
			break;
		case "T1_U2301_QRoo":
			res = 0.00031 * Math.pow(DN_EC, 1.57708) * Math.pow(AT_EC, 0.75499);
			break;
		case "T1_U2302_QRoo":
			res = 0.00008 * Math.pow(DN_EC, 1.72129) * Math.pow(AT_EC, 1.08118);
			break;
		case "T1_U2303_QRoo":
			res = 0.00004 * Math.pow(DN_EC, 2.34325) * Math.pow(AT_EC, 0.51945);
			break;
		case "T1_U2304_QRoo":
			res = 0.00006 * Math.pow(DN_EC, 2.02451) * Math.pow(AT_EC, 0.76808);
			break;
		case "T1_U2901_Tlax":
			res = 0.00006 * Math.pow(DN_EC, 2.01859) * Math.pow(AT_EC, 0.85547);
			break;
		case "T1_U2902_Tlax":
			res = 0.00004 * Math.pow(DN_EC, 2.04267) * Math.pow(AT_EC, 0.97354);
			break;
		case "T1_U3004_Ver":
			res = 0.00006 * Math.pow(DN_EC, 1.81795) * Math.pow(AT_EC, 1.03504);
			break;
		case "T1_U3012_Ver":
			res = 0.00007 * Math.pow(DN_EC, 1.86035) * Math.pow(AT_EC, 0.97824);
			break;
		case "T1_U3013_Ver":
			res = 0.00008 * Math.pow(DN_EC, 1.82144) * Math.pow(AT_EC, 0.89308);
			break;
		case "T1_U801_Chih":
			res = 0.00004 * Math.pow(DN_EC, 2.05275) * Math.pow(AT_EC, 0.89703);
			break;
		case "T1_U802_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.902) * Math.pow(AT_EC, 0.91709);
			break;
		case "T1_U804_Chih":
			res = 0.00008 * Math.pow(DN_EC, 2.01648) * Math.pow(AT_EC, 0.75786);
			break;
		case "T1_U805_Chih":
			res = 0.00002 * Math.pow(DN_EC, 2.15442) * Math.pow(AT_EC, 1.02415);
			break;
		case "T1_U806_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.95677) * Math.pow(AT_EC, 0.93114);
			break;
		case "T1_U807_Chih":
			res = 0.00005 * Math.pow(DN_EC, 1.94246) * Math.pow(AT_EC, 1.00059);
			break;
		case "T1_U808_Chih":
			res = 0.00008 * Math.pow(DN_EC, 1.9951) * Math.pow(AT_EC, 0.76449);
			break;
		case "T1_U809_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.84807) * Math.pow(AT_EC, 1.06881);
			break;
		case "T1_U810_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.05719) * Math.pow(AT_EC, 0.85407);
			break;
		case "T1_U811_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.00965) * Math.pow(AT_EC, 0.89719);
			break;
		case "T10_Chis":
			res = Math.exp(-9.05936092 + 1.68198628 * Math.log(DN_EC) + 1.0337615 * Math.log(AT_EC));
			break;
		case "T10_U1001_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.8731) * Math.pow(AT_EC, 0.96865);
			break;
		case "T10_U1002_Dgo":
			res = 0.00012 * Math.pow(DN_EC, 1.78213) * Math.pow(AT_EC, 0.85848);
			break;
		case "T10_U1003_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 2.12406) * Math.pow(AT_EC, 0.78704);
			break;
		case "T10_U1006_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.89307) * Math.pow(AT_EC, 0.96307);
			break;
		case "T10_U1008_Dgo":
			res = 0.00009 * Math.pow(DN_EC, 1.76817) * Math.pow(AT_EC, 1.01466);
			break;
		case "T10_U1009_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 1.95918) * Math.pow(AT_EC, 1.00516);
			break;
		case "T10_U1010_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 2.15357) * Math.pow(AT_EC, 0.84972);
			break;
		case "T10_U1011_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.98226) * Math.pow(AT_EC, 0.84224);
			break;
		case "T10_U1201y2_Gro":
			res = 0.00009 * Math.pow(DN_EC, 1.73474) * Math.pow(AT_EC, 0.99207);
			break;
		case "T10_U1203_Gro":
			res = 0.00004 * Math.pow(DN_EC, 1.7452) * Math.pow(AT_EC, 1.22851);
			break;
		case "T10_U1204_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.8584) * Math.pow(AT_EC, 0.93);
			break;
		case "T10_U1205_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.64843) * Math.pow(AT_EC, 1.21042);
			break;
		case "T10_U1302_Hgo":
			res = 0.00008 * Math.pow(DN_EC, 1.88035) * Math.pow(AT_EC, 0.80907);
			break;
		case "T10_U1303_Hgo":
			res = 0.00006 * Math.pow(DN_EC, 1.82931) * Math.pow(AT_EC, 0.98593);
			break;
		case "T10_U1404_Jal":
			res = 0.00002 * Math.pow(DN_EC, 2.06929) * Math.pow(AT_EC, 1.02961);
			break;
		case "T10_U1405_Jal":
			res = 0.00005 * Math.pow(DN_EC, 1.71823) * Math.pow(AT_EC, 1.20129);
			break;
		case "T10_U1406_Jal":
			res = 0.00006 * Math.pow(DN_EC, 2.09696) * Math.pow(AT_EC, 0.64491);
			break;
		case "T10_U1407_Jal":
			res = 0.00004 * Math.pow(DN_EC, 1.90131) * Math.pow(AT_EC, 1.03487);
			break;
		case "T10_U1410_Jal":
			res = 0.00005 * Math.pow(DN_EC, 1.71081) * Math.pow(AT_EC, 1.21788);
			break;
		case "T10_U1505_Mex":
			res = 0.00009 * Math.pow(DN_EC, 2.03275) * Math.pow(AT_EC, 0.70552);
			break;
		case "T10_U1507_Mex":
			res = 0.00003 * Math.pow(DN_EC, 2.39918) * Math.pow(AT_EC, 0.48419);
			break;
		case "T10_U1508_Mex":
			res = 0.00004 * Math.pow(DN_EC, 1.95131) * Math.pow(AT_EC, 0.99831);
			break;
		case "T10_U1509_Mex":
			res = 0.00003 * Math.pow(DN_EC, 1.89268) * Math.pow(AT_EC, 1.16081);
			break;
		case "T10_U1510_Mex":
			res = 0.00009 * Math.pow(DN_EC, 1.78519) * Math.pow(AT_EC, 0.92364);
			break;
		case "T10_U1603_Mich":
			res = 0.00008 * Math.pow(DN_EC, 1.80922) * Math.pow(AT_EC, 0.96199);
			break;
		case "T10_U1607_Mich":
			res = 0.00009 * Math.pow(DN_EC, 2.02762) * Math.pow(AT_EC, 0.60394);
			break;
		case "T10_U2001_Oax":
			res = 0.00006 * Math.pow(DN_EC, 2.02255) * Math.pow(AT_EC, 0.83354);
			break;
		case "T10_U2003_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.88856) * Math.pow(AT_EC, 1.06298);
			break;
		case "T10_U2008_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.83002) * Math.pow(AT_EC, 1.04349);
			break;
		case "T10_U2009_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.79088) * Math.pow(AT_EC, 1.17172);
			break;
		case "T10_U2010_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.94157) * Math.pow(AT_EC, 1.005);
			break;
		case "T10_U2012_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.99265) * Math.pow(AT_EC, 1.00428);
			break;
		case "T10_U2013_Oax":
			res = 0.00002 * Math.pow(DN_EC, 2.06095) * Math.pow(AT_EC, 1.04435);
			break;
		case "T10_U2101_Pue":
			res = 0.00004 * Math.pow(DN_EC, 1.96558) * Math.pow(AT_EC, 0.98027);
			break;
		case "T10_U2108_Pue":
			res = 0.00008 * Math.pow(DN_EC, 1.1668) * Math.pow(AT_EC, 1.73911);
			break;
		case "T10_U2301_QRoo":
			res = 0.00015 * Math.pow(DN_EC, 1.72921) * Math.pow(AT_EC, 0.84001);
			break;
		case "T10_U2302_QRoo":
			res = 0.00017 * Math.pow(DN_EC, 1.75669) * Math.pow(AT_EC, 0.7768);
			break;
		case "T10_U2303_QRoo":
			res = 0.00027 * Math.pow(DN_EC, 1.81392) * Math.pow(AT_EC, 0.39339);
			break;
		case "T10_U2304_QRoo":
			res = 0.00011 * Math.pow(DN_EC, 1.84942) * Math.pow(AT_EC, 0.6965);
			break;
		case "T10_U2901_Tlax":
			res = 0.00009 * Math.pow(DN_EC, 1.74161) * Math.pow(AT_EC, 1.03886);
			break;
		case "T10_U2902_Tlax":
			res = 0.00007 * Math.pow(DN_EC, 1.77764) * Math.pow(AT_EC, 1.04287);
			break;
		case "T10_U3012_Ver":
			res = 0.00008 * Math.pow(DN_EC, 1.83173) * Math.pow(AT_EC, 0.92786);
			break;
		case "T10_U3013_Ver":
			res = 0.00005 * Math.pow(DN_EC, 1.88185) * Math.pow(AT_EC, 0.95411);
			break;
		case "T10_U801_Chih":
			res = 0.00006 * Math.pow(DN_EC, 2.05268) * Math.pow(AT_EC, 0.7319);
			break;
		case "T10_U802_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.9842) * Math.pow(AT_EC, 0.74711);
			break;
		case "T10_U804_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.98983) * Math.pow(AT_EC, 0.87849);
			break;
		case "T10_U806_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.02955) * Math.pow(AT_EC, 0.93238);
			break;
		case "T10_U808_Chih":
			res = 0.00018 * Math.pow(DN_EC, 1.91424) * Math.pow(AT_EC, 0.51072);
			break;
		case "T10_U810_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.86262) * Math.pow(AT_EC, 0.93531);
			break;
		case "T10_U811_Chih":
			res = 0.00008 * Math.pow(DN_EC, 1.92643) * Math.pow(AT_EC, 0.76389);
			break;
		case "T10z4_Mex":
			res = Math.exp(-1.27349 + 1.791348 * Math.log(DN_EC / 100) + 1.019809 * Math.log(AT_EC));
			break;
		case "T10z5_Gro":
			res = -0.0798 + 0.63398 * DN_EC / 100 + 0.00413 * AT_EC + 0.23888 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "T11_Chis":
			res = Math.exp(-10.09141965 + 1.90481967 * Math.log(DN_EC) + 1.09303973 * Math.log(AT_EC));
			break;
		case "T11_Gro":
			res = 0.23104 - 2.32118 * DN_EC / 100 - 0.02378 * AT_EC + 7.475 * Math.pow(DN_EC / 100, 2)
					+ 0.18215 * AT_EC * DN_EC / 100;
			break;
		case "T11_Mex":
			res = Math.exp(-1.586189 + 1.708726 * Math.log(DN_EC / 100) + 1.094269 * Math.log(AT_EC));
			break;
		case "T11_U1001_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.87268) * Math.pow(AT_EC, 1.00995);
			break;
		case "T11_U1002_Dgo":
			res = 0.00002 * Math.pow(DN_EC, 1.84212) * Math.pow(AT_EC, 1.30357);
			break;
		case "T11_U1003_Dgo":
			res = 0.00024 * Math.pow(DN_EC, 1.61852) * Math.pow(AT_EC, 0.74262);
			break;
		case "T11_U1006_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 2.02067) * Math.pow(AT_EC, 0.99615);
			break;
		case "T11_U1008_Dgo":
			res = 0.0002 * Math.pow(DN_EC, 1.69519) * Math.pow(AT_EC, 0.84876);
			break;
		case "T11_U1010_Dgo":
			res = 0.00013 * Math.pow(DN_EC, 1.84925) * Math.pow(AT_EC, 0.69308);
			break;
		case "T11_U1201y2_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.79891) * Math.pow(AT_EC, 1.08229);
			break;
		case "T11_U1203_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.86461) * Math.pow(AT_EC, 0.92862);
			break;
		case "T11_U1204_Gro":
			res = 0.00009 * Math.pow(DN_EC, 1.87134) * Math.pow(AT_EC, 0.77585);
			break;
		case "T11_U1205_Gro":
			res = 0.00003 * Math.pow(DN_EC, 1.97164) * Math.pow(AT_EC, 1.03669);
			break;
		case "T11_U1302_Hgo":
			res = 0.00002 * Math.pow(DN_EC, 2.34709) * Math.pow(AT_EC, 0.70399);
			break;
		case "T11_U1303_Hgo":
			res = 0.00005 * Math.pow(DN_EC, 1.91537) * Math.pow(AT_EC, 0.93148);
			break;
		case "T11_U1404_Jal":
			res = 0.00008 * Math.pow(DN_EC, 1.81817) * Math.pow(AT_EC, 0.93346);
			break;
		case "T11_U1405_Jal":
			res = 0.00008 * Math.pow(DN_EC, 1.77603) * Math.pow(AT_EC, 1.00051);
			break;
		case "T11_U1406_Jal":
			res = 0.00006 * Math.pow(DN_EC, 2.08628) * Math.pow(AT_EC, 0.72886);
			break;
		case "T11_U1407_Jal":
			res = 0.00003 * Math.pow(DN_EC, 1.99106) * Math.pow(AT_EC, 1.08692);
			break;
		case "T11_U1410_Jal":
			res = 0.00001 * Math.pow(DN_EC, 2.09434) * Math.pow(AT_EC, 1.16445);
			break;
		case "T11_U1505_Mex":
			res = 0.00014 * Math.pow(DN_EC, 1.73676) * Math.pow(AT_EC, 0.77552);
			break;
		case "T11_U1507_Mex":
			res = 0.00004 * Math.pow(DN_EC, 1.93144) * Math.pow(AT_EC, 1.08733);
			break;
		case "T11_U1508_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.78917) * Math.pow(AT_EC, 1.04436);
			break;
		case "T11_U1509_Mex":
			res = 0.00003 * Math.pow(DN_EC, 1.78245) * Math.pow(AT_EC, 1.30442);
			break;
		case "T11_U1510_Mex":
			res = 0.0001 * Math.pow(DN_EC, 2.00468) * Math.pow(AT_EC, 0.56892);
			break;
		case "T11_U2001_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.92132) * Math.pow(AT_EC, 1.0083);
			break;
		case "T11_U2003_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.72731) * Math.pow(AT_EC, 1.13779);
			break;
		case "T11_U2008_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.87754) * Math.pow(AT_EC, 0.87749);
			break;
		case "T11_U2009_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.7947) * Math.pow(AT_EC, 1.11387);
			break;
		case "T11_U2010_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.84771) * Math.pow(AT_EC, 1.04512);
			break;
		case "T11_U2012_Oax":
			res = 0.00008 * Math.pow(DN_EC, 1.78075) * Math.pow(AT_EC, 0.99887);
			break;
		case "T11_U2013_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.76527) * Math.pow(AT_EC, 1.11918);
			break;
		case "T11_U2101_Pue":
			res = 0.00007 * Math.pow(DN_EC, 1.74873) * Math.pow(AT_EC, 1.10365);
			break;
		case "T11_U2108_Pue":
			res = 0.00008 * Math.pow(DN_EC, 1.6026) * Math.pow(AT_EC, 1.19117);
			break;
		case "T11_U2901_Tlax":
			res = 0.00009 * Math.pow(DN_EC, 1.8462) * Math.pow(AT_EC, 0.88922);
			break;
		case "T11_U2902_Tlax":
			res = 0.00009 * Math.pow(DN_EC, 1.82594) * Math.pow(AT_EC, 0.87733);
			break;
		case "T11_U3012_Ver":
			res = 0.00008 * Math.pow(DN_EC, 1.98735) * Math.pow(AT_EC, 0.77516);
			break;
		case "T11_U3013_Ver":
			res = 0.0001 * Math.pow(DN_EC, 1.67436) * Math.pow(AT_EC, 1.04303);
			break;
		case "T11_U801_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.95884) * Math.pow(AT_EC, 0.83487);
			break;
		case "T11_U804_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.00358) * Math.pow(AT_EC, 0.91881);
			break;
		case "T11_U806_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.88688) * Math.pow(AT_EC, 0.96713);
			break;
		case "T11_U810_Chih":
			res = 0.00021 * Math.pow(DN_EC, 1.73875) * Math.pow(AT_EC, 0.57042);
			break;
		case "T11_U811_Chih":
			res = 0.00011 * Math.pow(DN_EC, 1.84965) * Math.pow(AT_EC, 0.70945);
			break;
		case "T12_Chis":
			res = Math.exp(-9.84669352 + 1.93536382 * Math.log(DN_EC) + 1.01473381 * Math.log(AT_EC));
			break;
		case "T12_Gro":
			res = 0.06629 + 0.34683 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "T12_Mex":
			res = Math.exp(-0.77785 + 1.872175 * Math.log(DN_EC / 100) + 0.815238 * Math.log(AT_EC));
			break;
		case "T12_QR":
			res = Math.exp(-9.63573531 + 1.90246451 * Math.log(DN_EC) + 0.97875991 * Math.log(AT_EC));
			break;
		case "T12_U1001_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.9367) * Math.pow(AT_EC, 0.83897);
			break;
		case "T12_U1002_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.89803) * Math.pow(AT_EC, 0.96438);
			break;
		case "T12_U1003_Dgo":
			res = 0.00008 * Math.pow(DN_EC, 1.91234) * Math.pow(AT_EC, 0.89825);
			break;
		case "T12_U1006_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 2.07582) * Math.pow(AT_EC, 0.90926);
			break;
		case "T12_U1008_Dgo":
			res = 0.00003 * Math.pow(DN_EC, 2.11797) * Math.pow(AT_EC, 0.93901);
			break;
		case "T12_U1010_Dgo":
			res = 0.00011 * Math.pow(DN_EC, 1.86859) * Math.pow(AT_EC, 0.76618);
			break;
		case "T12_U1201y2_Gro":
			res = 0.00008 * Math.pow(DN_EC, 1.85355) * Math.pow(AT_EC, 0.90562);
			break;
		case "T12_U1203_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.93129) * Math.pow(AT_EC, 0.96447);
			break;
		case "T12_U1204_Gro":
			res = 0.0001 * Math.pow(DN_EC, 1.87652) * Math.pow(AT_EC, 0.73884);
			break;
		case "T12_U1205_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.9694) * Math.pow(AT_EC, 0.79289);
			break;
		case "T12_U1302_Hgo":
			res = 0.00005 * Math.pow(DN_EC, 1.57065) * Math.pow(AT_EC, 1.3666);
			break;
		case "T12_U1303_Hgo":
			res = 0.00012 * Math.pow(DN_EC, 1.40124) * Math.pow(AT_EC, 1.24464);
			break;
		case "T12_U1404_Jal":
			res = 0.00003 * Math.pow(DN_EC, 1.97118) * Math.pow(AT_EC, 1.02248);
			break;
		case "T12_U1406_Jal":
			res = 0.00006 * Math.pow(DN_EC, 1.94334) * Math.pow(AT_EC, 0.91533);
			break;
		case "T12_U1410_Jal":
			res = 0.00003 * Math.pow(DN_EC, 2.09965) * Math.pow(AT_EC, 0.94988);
			break;
		case "T12_U1505_Mex":
			res = 0.0001 * Math.pow(DN_EC, 1.8022) * Math.pow(AT_EC, 0.90355);
			break;
		case "T12_U1507_Mex":
			res = 0.00003 * Math.pow(DN_EC, 1.7487) * Math.pow(AT_EC, 1.31458);
			break;
		case "T12_U1509_Mex":
			res = 0.00007 * Math.pow(DN_EC, 1.79799) * Math.pow(AT_EC, 0.96039);
			break;
		case "T12_U1510_Mex":
			res = 0.00003 * Math.pow(DN_EC, 1.77772) * Math.pow(AT_EC, 1.34495);
			break;
		case "T12_U2001_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.83769) * Math.pow(AT_EC, 0.98303);
			break;
		case "T12_U2003_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.91401) * Math.pow(AT_EC, 0.89035);
			break;
		case "T12_U2008_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.52983) * Math.pow(AT_EC, 1.41074);
			break;
		case "T12_U2009_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.77172) * Math.pow(AT_EC, 1.04104);
			break;
		case "T12_U2010_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.88817) * Math.pow(AT_EC, 0.91436);
			break;
		case "T12_U2012_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.85893) * Math.pow(AT_EC, 1.15049);
			break;
		case "T12_U2013_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.86566) * Math.pow(AT_EC, 0.88945);
			break;
		case "T12_U2014_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.85974) * Math.pow(AT_EC, 1.05727);
			break;
		case "T12_U2108_Pue":
			res = 0.0001 * Math.pow(DN_EC, 1.73265) * Math.pow(AT_EC, 0.95724);
			break;
		case "T12_U2901_Tlax":
			res = 0.00015 * Math.pow(DN_EC, 1.8332) * Math.pow(AT_EC, 0.68578);
			break;
		case "T12_U2902_Tlax":
			res = 0.0001 * Math.pow(DN_EC, 1.68118) * Math.pow(AT_EC, 1.03035);
			break;
		case "T12_U3012_Ver":
			res = 0.00005 * Math.pow(DN_EC, 2.11265) * Math.pow(AT_EC, 0.76038);
			break;
		case "T12_U3013_Ver":
			res = 0.00007 * Math.pow(DN_EC, 1.59046) * Math.pow(AT_EC, 1.29633);
			break;
		case "T12_U801_Chih":
			res = 0.00014 * Math.pow(DN_EC, 1.74965) * Math.pow(AT_EC, 0.769);
			break;
		case "T12_U806_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.96232) * Math.pow(AT_EC, 0.80451);
			break;
		case "T12_U810_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.82279) * Math.pow(AT_EC, 1.06227);
			break;
		case "T12_U811_Chih":
			res = 0.0001 * Math.pow(DN_EC, 1.84682) * Math.pow(AT_EC, 0.76733);
			break;
		case "T13_Chis":
			res = Math.exp(-9.87689 + 1.9349903 * Math.log(DN_EC) + 1.03862975 * Math.log(AT_EC));
			break;
		case "T13_QR":
			res = Math.exp(-9.84052491 + 1.92716537 * Math.log(DN_EC) + 1.00282618 * Math.log(AT_EC));
			break;
		case "T13_U1002_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 2.0108) * Math.pow(AT_EC, 0.89173);
			break;
		case "T13_U1003_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 2.0634) * Math.pow(AT_EC, 0.7825);
			break;
		case "T13_U1006_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.96305) * Math.pow(AT_EC, 0.8026);
			break;
		case "T13_U1008_Dgo":
			res = 0.00011 * Math.pow(DN_EC, 1.84425) * Math.pow(AT_EC, 0.76222);
			break;
		case "T13_U1010_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 2.03965) * Math.pow(AT_EC, 0.92099);
			break;
		case "T13_U1201y2_Gro":
			res = 0.00008 * Math.pow(DN_EC, 1.73593) * Math.pow(AT_EC, 0.98418);
			break;
		case "T13_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.93135) * Math.pow(AT_EC, 0.92651);
			break;
		case "T13_U1205_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.82814) * Math.pow(AT_EC, 0.97979);
			break;
		case "T13_U1303_Hgo":
			res = 0.00003 * Math.pow(DN_EC, 1.79496) * Math.pow(AT_EC, 1.25512);
			break;
		case "T13_U1404_Jal":
			res = 0.00002 * Math.pow(DN_EC, 1.93249) * Math.pow(AT_EC, 1.23334);
			break;
		case "T13_U1410_Jal":
			res = 0.00003 * Math.pow(DN_EC, 1.97205) * Math.pow(AT_EC, 1.03928);
			break;
		case "T13_U1507_Mex":
			res = 0.00005 * Math.pow(DN_EC, 1.95754) * Math.pow(AT_EC, 0.94176);
			break;
		case "T13_U2001_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.87516) * Math.pow(AT_EC, 0.97445);
			break;
		case "T13_U2003_Oax":
			res = 0.00005 * Math.pow(DN_EC, 2.00365) * Math.pow(AT_EC, 0.81287);
			break;
		case "T13_U2008_Oax":
			res = 0.00015 * Math.pow(DN_EC, 1.68185) * Math.pow(AT_EC, 0.80271);
			break;
		case "T13_U2010_Oax":
			res = 0.00003 * Math.pow(DN_EC, 2.07561) * Math.pow(AT_EC, 0.90062);
			break;
		case "T13_U2012_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.91711) * Math.pow(AT_EC, 0.99324);
			break;
		case "T13_U2108_Pue":
			res = 0.00007 * Math.pow(DN_EC, 1.96954) * Math.pow(AT_EC, 0.87052);
			break;
		case "T13_U2901_Tlax":
			res = 0.00006 * Math.pow(DN_EC, 1.85434) * Math.pow(AT_EC, 1.01199);
			break;
		case "T13_U2902_Tlax":
			res = 0.00012 * Math.pow(DN_EC, 1.92002) * Math.pow(AT_EC, 0.65657);
			break;
		case "T13_U3012_Ver":
			res = 0.00002 * Math.pow(DN_EC, 2.1642) * Math.pow(AT_EC, 1.04988);
			break;
		case "T13_U3013_Ver":
			res = 0.00007 * Math.pow(DN_EC, 1.69633) * Math.pow(AT_EC, 1.20836);
			break;
		case "T13_U804_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.95563) * Math.pow(AT_EC, 0.83333);
			break;
		case "T13_U806_Chih":
			res = 0.00003 * Math.pow(DN_EC, 1.99154) * Math.pow(AT_EC, 1.03238);
			break;
		case "T13_U810_Chih":
			res = 0.00009 * Math.pow(DN_EC, 1.82515) * Math.pow(AT_EC, 0.91812);
			break;
		case "T13_U811_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.86162) * Math.pow(AT_EC, 0.94108);
			break;
		case "T14_Chis":
			res = Math.exp(-9.82944377 + 1.9060093 * Math.log(DN_EC) + 1.04047533 * Math.log(AT_EC));
			break;
		case "T14_QR":
			res = Math.exp(-9.52375084 + 1.81551953 * Math.log(DN_EC) + 1.03039019 * Math.log(AT_EC));
			break;
		case "T14_U1006_Dgo":
			res = 0.00016 * Math.pow(DN_EC, 1.68594) * Math.pow(AT_EC, 0.79879);
			break;
		case "T14_U1008_Dgo":
			res = 0.00008 * Math.pow(DN_EC, 1.84636) * Math.pow(AT_EC, 0.90146);
			break;
		case "T14_U1201_Gro":
			res = 0.00009 * Math.pow(DN_EC, 1.98843) * Math.pow(AT_EC, 0.67767);
			break;
		case "T14_U1203_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.71575) * Math.pow(AT_EC, 1.20382);
			break;
		case "T14_U1205_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.88299) * Math.pow(AT_EC, 0.96023);
			break;
		case "T14_U1303_Hgo":
			res = 0.00006 * Math.pow(DN_EC, 1.8925) * Math.pow(AT_EC, 0.94134);
			break;
		case "T14_U1404_Jal":
			res = 0.00004 * Math.pow(DN_EC, 1.94151) * Math.pow(AT_EC, 1.01896);
			break;
		case "T14_U1410_Jal":
			res = 0.00002 * Math.pow(DN_EC, 2.20796) * Math.pow(AT_EC, 0.9231);
			break;
		case "T14_U2001_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.93975) * Math.pow(AT_EC, 0.97875);
			break;
		case "T14_U2010_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.84825) * Math.pow(AT_EC, 1.13777);
			break;
		case "T14_U2012_Oax":
			res = 0.00003 * Math.pow(DN_EC, 1.83624) * Math.pow(AT_EC, 1.21178);
			break;
		case "T14_U2901_Tlax":
			res = 0.00008 * Math.pow(DN_EC, 1.81743) * Math.pow(AT_EC, 0.93896);
			break;
		case "T14_U2902_Tlax":
			res = 0.00006 * Math.pow(DN_EC, 1.83021) * Math.pow(AT_EC, 1.07396);
			break;
		case "T14_U3012_Ver":
			res = 0.00003 * Math.pow(DN_EC, 2.20121) * Math.pow(AT_EC, 0.87633);
			break;
		case "T14_U3013_Ver":
			res = 0.00011 * Math.pow(DN_EC, 1.61584) * Math.pow(AT_EC, 1.07489);
			break;
		case "T14_U804_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.97893) * Math.pow(AT_EC, 0.87775);
			break;
		case "T14_U806_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.07127) * Math.pow(AT_EC, 0.75716);
			break;
		case "T14_U810_Chih":
			res = 0.00009 * Math.pow(DN_EC, 1.71498) * Math.pow(AT_EC, 1.11909);
			break;
		case "T14_U811_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.04563) * Math.pow(AT_EC, 0.78953);
			break;
		case "T15_Chis":
			res = Math.exp(-9.80434696 + 1.91033696 * Math.log(DN_EC) + 1.03262007 * Math.log(AT_EC));
			break;
		case "T15_QR":
			res = Math.exp(-9.45441486 + 1.868413 * Math.log(DN_EC) + 0.9316352 * Math.log(AT_EC));
			break;
		case "T15_U1006_Dgo":
			res = 0.00009 * Math.pow(DN_EC, 1.82002) * Math.pow(AT_EC, 0.9438);
			break;
		case "T15_U1008_Dgo":
			res = 0.00009 * Math.pow(DN_EC, 1.75675) * Math.pow(AT_EC, 0.98256);
			break;
		case "T15_U1201y2_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.83318) * Math.pow(AT_EC, 1.02182);
			break;
		case "T15_U1203_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.91091) * Math.pow(AT_EC, 0.95788);
			break;
		case "T15_U1205_Gro":
			res = 0.00007 * Math.pow(DN_EC, 2.05405) * Math.pow(AT_EC, 0.66476);
			break;
		case "T15_U1303_Hgo":
			res = 0.00006 * Math.pow(DN_EC, 1.95017) * Math.pow(AT_EC, 0.85372);
			break;
		case "T15_U2001_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.88297) * Math.pow(AT_EC, 0.93261);
			break;
		case "T15_U2010_Oax":
			res = 0.0001 * Math.pow(DN_EC, 1.67636) * Math.pow(AT_EC, 0.96056);
			break;
		case "T15_U2012_Oax":
			res = 0.00003 * Math.pow(DN_EC, 1.80711) * Math.pow(AT_EC, 1.21149);
			break;
		case "T15_U2901_Tlax":
			res = 0.00019 * Math.pow(DN_EC, 1.63752) * Math.pow(AT_EC, 0.86217);
			break;
		case "T15_U2902_Tlax":
			res = 0.00009 * Math.pow(DN_EC, 1.93142) * Math.pow(AT_EC, 0.84994);
			break;
		case "T15_U804_Chih":
			res = 0.00006 * Math.pow(DN_EC, 2.01975) * Math.pow(AT_EC, 0.78619);
			break;
		case "T15_U806_Chih":
			res = 0.00006 * Math.pow(DN_EC, 2.01191) * Math.pow(AT_EC, 0.80774);
			break;
		case "T15_U811_Chih":
			res = 0.00012 * Math.pow(DN_EC, 1.80879) * Math.pow(AT_EC, 0.7094);
			break;
		case "T16_Chis":
			res = Math.exp(-10.12597512 + 2.04755627 * Math.log(DN_EC) + 0.96453516 * Math.log(AT_EC));
			break;
		case "T16_QR":
			res = Math.exp(-9.63519924 + 1.83658572 * Math.log(DN_EC) + 1.02444663 * Math.log(AT_EC));
			break;
		case "T16_U1006_Dgo":
			res = 0.00003 * Math.pow(DN_EC, 2.03332) * Math.pow(AT_EC, 1.01105);
			break;
		case "T16_U1008_Dgo":
			res = 0.0001 * Math.pow(DN_EC, 1.75125) * Math.pow(AT_EC, 1.02691);
			break;
		case "T16_U1201_Gro":
			res = 0.00011 * Math.pow(DN_EC, 1.88392) * Math.pow(AT_EC, 0.72281);
			break;
		case "T16_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.80374) * Math.pow(AT_EC, 1.03167);
			break;
		case "T16_U1205_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.66062) * Math.pow(AT_EC, 1.16511);
			break;
		case "T16_U1303_Hgo":
			res = 0.00005 * Math.pow(DN_EC, 1.90765) * Math.pow(AT_EC, 0.98787);
			break;
		case "T16_U2001_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.83477) * Math.pow(AT_EC, 0.99152);
			break;
		case "T16_U2012_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.84015) * Math.pow(AT_EC, 0.91458);
			break;
		case "T16_U2902_Tlax":
			res = 0.00008 * Math.pow(DN_EC, 1.72088) * Math.pow(AT_EC, 1.03414);
			break;
		case "T16_U804_Chih":
			res = 0.00003 * Math.pow(DN_EC, 2.05885) * Math.pow(AT_EC, 0.96617);
			break;
		case "T16_U806_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.0334) * Math.pow(AT_EC, 0.84059);
			break;
		case "T17_U1008_Dgo":
			res = 0.00008 * Math.pow(DN_EC, 1.82596) * Math.pow(AT_EC, 0.91915);
			break;
		case "T17_U1201_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.89103) * Math.pow(AT_EC, 0.90469);
			break;
		case "T17_U1203_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.8555) * Math.pow(AT_EC, 0.94653);
			break;
		case "T17_U1205_Gro":
			res = 0.00004 * Math.pow(DN_EC, 1.98034) * Math.pow(AT_EC, 0.99271);
			break;
		case "T17_U1303_Hgo":
			res = 0.00006 * Math.pow(DN_EC, 1.82154) * Math.pow(AT_EC, 0.96124);
			break;
		case "T17_U2012_Oax":
			res = 0.00008 * Math.pow(DN_EC, 1.66019) * Math.pow(AT_EC, 1.02933);
			break;
		case "T17_U2902_Tlax":
			res = 0.00021 * Math.pow(DN_EC, 1.29859) * Math.pow(AT_EC, 1.23463);
			break;
		case "T17_U804_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.85832) * Math.pow(AT_EC, 0.99531);
			break;
		case "T17_U806_Chih":
			res = 0.00008 * Math.pow(DN_EC, 1.76881) * Math.pow(AT_EC, 0.98972);
			break;
		case "T18_U1201y2_Gro":
			res = 0.00004 * Math.pow(DN_EC, 2.09874) * Math.pow(AT_EC, 0.84217);
			break;
		case "T18_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.88952) * Math.pow(AT_EC, 0.9247);
			break;
		case "T18_U1303_Hgo":
			res = 0.00003 * Math.pow(DN_EC, 1.64033) * Math.pow(AT_EC, 1.41443);
			break;
		case "T18_U804_Chih":
			res = 0.00013 * Math.pow(DN_EC, 1.87996) * Math.pow(AT_EC, 0.58307);
			break;
		case "T18_U806_Chih":
			res = 0.00009 * Math.pow(DN_EC, 1.95421) * Math.pow(AT_EC, 0.65826);
			break;
		case "T19_U1201_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.9076) * Math.pow(AT_EC, 0.86915);
			break;
		case "T19_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.96691) * Math.pow(AT_EC, 0.82907);
			break;
		case "T19_U1303_Hgo":
			res = 0.00005 * Math.pow(DN_EC, 1.73605) * Math.pow(AT_EC, 1.16221);
			break;
		case "T1z1_Gro":
			res = 0.17784 + 0.34232 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "T1z1_Mex":
			res = Math.exp(-0.909190 + 1.876563 * Math.log(DN_EC / 100) + 0.947742 * Math.log(AT_EC));
			break;
		case "T1z1_Tlax":
			res = Math.exp(-0.82535 + 1.899918 * Math.log(DN_EC / 100) + 0.945586 * Math.log(AT_EC));
			break;
		case "T2_Chis":
			res = Math.exp(-9.92570337 + 1.96275753 * Math.log(DN_EC) + 1.00116088 * Math.log(AT_EC));
			break;
		case "T2_QR":
			res = Math.exp(-9.6873264 + 1.87777278 * Math.log(DN_EC) + 1.01495306 * Math.log(AT_EC));
			break;
		case "T2_U1001_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.86117) * Math.pow(AT_EC, 1.05993);
			break;
		case "T2_U1002_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.95633) * Math.pow(AT_EC, 0.93913);
			break;
		case "T2_U1003_Dgo":
			res = 0.00009 * Math.pow(DN_EC, 1.98828) * Math.pow(AT_EC, 0.75962);
			break;
		case "T2_U1004_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.97576) * Math.pow(AT_EC, 0.86312);
			break;
		case "T2_U1005_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 2.03226) * Math.pow(AT_EC, 0.87136);
			break;
		case "T2_U1006_Dgo":
			res = 0.0001 * Math.pow(DN_EC, 1.88188) * Math.pow(AT_EC, 0.87044);
			break;
		case "T2_U1007_Dgo":
			res = 0.00011 * Math.pow(DN_EC, 1.93565) * Math.pow(AT_EC, 0.73791);
			break;
		case "T2_U1008_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 1.94821) * Math.pow(AT_EC, 0.97026);
			break;
		case "T2_U1009_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 2.00275) * Math.pow(AT_EC, 0.8598);
			break;
		case "T2_U1010_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.92673) * Math.pow(AT_EC, 0.98615);
			break;
		case "T2_U1011_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 2.00005) * Math.pow(AT_EC, 0.85033);
			break;
		case "T2_U1201y2_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.8127) * Math.pow(AT_EC, 1.05354);
			break;
		case "T2_U1203_Gro":
			res = 0.00004 * Math.pow(DN_EC, 1.86721) * Math.pow(AT_EC, 1.12828);
			break;
		case "T2_U1204_Gro":
			res = 0.00008 * Math.pow(DN_EC, 1.80194) * Math.pow(AT_EC, 0.92263);
			break;
		case "T2_U1205_Gro":
			res = 0.00004 * Math.pow(DN_EC, 1.95113) * Math.pow(AT_EC, 1.00285);
			break;
		case "T2_U1302_Hgo":
			res = 0.00007 * Math.pow(DN_EC, 1.97977) * Math.pow(AT_EC, 0.81845);
			break;
		case "T2_U1303_Hgo":
			res = 0.00005 * Math.pow(DN_EC, 1.55341) * Math.pow(AT_EC, 1.39447);
			break;
		case "T2_U1305_Hgo":
			res = 0.00004 * Math.pow(DN_EC, 1.93694) * Math.pow(AT_EC, 1.03169);
			break;
		case "T2_U1401_Jal":
			res = 0.0001 * Math.pow(DN_EC, 1.9822) * Math.pow(AT_EC, 0.70974);
			break;
		case "T2_U1404_Jal":
			res = 0.00004 * Math.pow(DN_EC, 1.91718) * Math.pow(AT_EC, 1.08265);
			break;
		case "T2_U1405_Jal":
			res = 0.00004 * Math.pow(DN_EC, 1.97454) * Math.pow(AT_EC, 1.01106);
			break;
		case "T2_U1406_Jal":
			res = 0.00003 * Math.pow(DN_EC, 2.06504) * Math.pow(AT_EC, 0.97807);
			break;
		case "T2_U1407_Jal":
			res = 0.00003 * Math.pow(DN_EC, 2.20898) * Math.pow(AT_EC, 0.8316);
			break;
		case "T2_U1408_Jal":
			res = 0.00005 * Math.pow(DN_EC, 2.07869) * Math.pow(AT_EC, 0.85276);
			break;
		case "T2_U1410_Jal":
			res = 0.00004 * Math.pow(DN_EC, 2.06714) * Math.pow(AT_EC, 0.93064);
			break;
		case "T2_U1505_Mex":
			res = 0.00005 * Math.pow(DN_EC, 1.82435) * Math.pow(AT_EC, 1.14342);
			break;
		case "T2_U1507_Mex":
			res = 0.00004 * Math.pow(DN_EC, 1.96323) * Math.pow(AT_EC, 0.97782);
			break;
		case "T2_U1508_Mex":
			res = 0.00007 * Math.pow(DN_EC, 2.0441) * Math.pow(AT_EC, 0.80253);
			break;
		case "T2_U1509_Mex":
			res = 0.00005 * Math.pow(DN_EC, 1.89374) * Math.pow(AT_EC, 1.04389);
			break;
		case "T2_U1510_Mex":
			res = 0.00011 * Math.pow(DN_EC, 1.73764) * Math.pow(AT_EC, 0.93166);
			break;
		case "T2_U1601_Mich":
			res = 0.00008 * Math.pow(DN_EC, 1.84034) * Math.pow(AT_EC, 0.87928);
			break;
		case "T2_U1603_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.92319) * Math.pow(AT_EC, 0.90548);
			break;
		case "T2_U1604_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.82561) * Math.pow(AT_EC, 1.08247);
			break;
		case "T2_U1605_Mich":
			res = 0.00009 * Math.pow(DN_EC, 1.83282) * Math.pow(AT_EC, 0.90437);
			break;
		case "T2_U1607_Mich":
			res = 0.00003 * Math.pow(DN_EC, 1.97523) * Math.pow(AT_EC, 1.11777);
			break;
		case "T2_U1608_Mich":
			res = 0.00005 * Math.pow(DN_EC, 1.84295) * Math.pow(AT_EC, 1.07958);
			break;
		case "T2_U1610_Mich":
			res = 0.00005 * Math.pow(DN_EC, 1.95469) * Math.pow(AT_EC, 0.98265);
			break;
		case "T2_U2001_Oax":
			res = 0.00008 * Math.pow(DN_EC, 1.93213) * Math.pow(AT_EC, 0.82212);
			break;
		case "T2_U2003_Oax":
			res = 0.00005 * Math.pow(DN_EC, 2.03713) * Math.pow(AT_EC, 0.81892);
			break;
		case "T2_U2007_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.88364) * Math.pow(AT_EC, 0.87928);
			break;
		case "T2_U2008_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.99388) * Math.pow(AT_EC, 0.92306);
			break;
		case "T2_U2009_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.9378) * Math.pow(AT_EC, 0.87829);
			break;
		case "T2_U2010_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.91641) * Math.pow(AT_EC, 0.89172);
			break;
		case "T2_U2012_Oax":
			res = 0.00005 * Math.pow(DN_EC, 2.08092) * Math.pow(AT_EC, 0.85026);
			break;
		case "T2_U2013_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.92158) * Math.pow(AT_EC, 1.0768);
			break;
		case "T2_U2101_Pue":
			res = 0.00014 * Math.pow(DN_EC, 1.35427) * Math.pow(AT_EC, 1.27362);
			break;
		case "T2_U2103_Pue":
			res = 0.00005 * Math.pow(DN_EC, 1.99006) * Math.pow(AT_EC, 0.92655);
			break;
		case "T2_U2105_Pue":
			res = 0.00006 * Math.pow(DN_EC, 1.70785) * Math.pow(AT_EC, 1.15901);
			break;
		case "T2_U2107_Pue":
			res = 0.00013 * Math.pow(DN_EC, 1.77287) * Math.pow(AT_EC, 0.67909);
			break;
		case "T2_U2108_Pue":
			res = 0.00008 * Math.pow(DN_EC, 1.4768) * Math.pow(AT_EC, 1.32147);
			break;
		case "T2_U2301_QRoo":
			res = 0.00011 * Math.pow(DN_EC, 1.57877) * Math.pow(AT_EC, 1.08291);
			break;
		case "T2_U2302_QRoo":
			res = 0.00009 * Math.pow(DN_EC, 1.73806) * Math.pow(AT_EC, 0.95672);
			break;
		case "T2_U2303_QRoo":
			res = 0.00005 * Math.pow(DN_EC, 2.08565) * Math.pow(AT_EC, 0.8069);
			break;
		case "T2_U2304_QRoo":
			res = 0.00004 * Math.pow(DN_EC, 2.10523) * Math.pow(AT_EC, 0.84016);
			break;
		case "T2_U2901_Tlax":
			res = 0.00007 * Math.pow(DN_EC, 1.85095) * Math.pow(AT_EC, 0.98151);
			break;
		case "T2_U2902_Tlax":
			res = 0.00004 * Math.pow(DN_EC, 1.94909) * Math.pow(AT_EC, 1.04078);
			break;
		case "T2_U3004_Ver":
			res = 0.00003 * Math.pow(DN_EC, 1.89909) * Math.pow(AT_EC, 1.14576);
			break;
		case "T2_U3012_Ver":
			res = 0.00006 * Math.pow(DN_EC, 2.01037) * Math.pow(AT_EC, 0.86184);
			break;
		case "T2_U3013_Ver":
			res = 0.00006 * Math.pow(DN_EC, 1.97608) * Math.pow(AT_EC, 0.9002);
			break;
		case "T2_U801_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.95675) * Math.pow(AT_EC, 0.94514);
			break;
		case "T2_U802_Chih":
			res = 0.00023 * Math.pow(DN_EC, 1.42715) * Math.pow(AT_EC, 1.11661);
			break;
		case "T2_U804_Chih":
			res = 0.00009 * Math.pow(DN_EC, 1.92394) * Math.pow(AT_EC, 0.82353);
			break;
		case "T2_U805_Chih":
			res = 0.00003 * Math.pow(DN_EC, 2.12015) * Math.pow(AT_EC, 0.96935);
			break;
		case "T2_U806_Chih":
			res = 0.00005 * Math.pow(DN_EC, 1.91386) * Math.pow(AT_EC, 1.06539);
			break;
		case "T2_U807_Chih":
			res = 0.00003 * Math.pow(DN_EC, 1.95717) * Math.pow(AT_EC, 1.10348);
			break;
		case "T2_U808_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.78357) * Math.pow(AT_EC, 1.09827);
			break;
		case "T2_U809_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.95135) * Math.pow(AT_EC, 0.90316);
			break;
		case "T2_U810_Chih":
			res = 0.00004 * Math.pow(DN_EC, 2.02204) * Math.pow(AT_EC, 0.96801);
			break;
		case "T2_U811_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.84606) * Math.pow(AT_EC, 0.97668);
			break;
		case "T20_U1201_Gro":
			res = 0.00008 * Math.pow(DN_EC, 1.99129) * Math.pow(AT_EC, 0.71462);
			break;
		case "T20_U1203_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.87424) * Math.pow(AT_EC, 0.87397);
			break;
		case "T20_U1303_Hgo":
			res = 0.00006 * Math.pow(DN_EC, 1.85604) * Math.pow(AT_EC, 0.97898);
			break;
		case "T21_U1201y2_Gro":
			res = 0.00008 * Math.pow(DN_EC, 1.9661) * Math.pow(AT_EC, 0.73174);
			break;
		case "T21_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.87357) * Math.pow(AT_EC, 0.93714);
			break;
		case "T22_U1201y2_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.91897) * Math.pow(AT_EC, 0.984);
			break;
		case "T22_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.98985) * Math.pow(AT_EC, 0.81221);
			break;
		case "T23_U1201_Gro":
			res = 0.00007 * Math.pow(DN_EC, 2.02075) * Math.pow(AT_EC, 0.72025);
			break;
		case "T23_U1203_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.84483) * Math.pow(AT_EC, 1.01948);
			break;
		case "T24_U1201y2_Gro":
			res = 0.00005 * Math.pow(DN_EC, 2.02194) * Math.pow(AT_EC, 0.83266);
			break;
		case "T24_U1203_Gro":
			res = 0.00004 * Math.pow(DN_EC, 2.0771) * Math.pow(AT_EC, 0.80396);
			break;
		case "T24_U804_Chih":
			res = 0.00008 * Math.pow(DN_EC, 1.89076) * Math.pow(AT_EC, 0.83904);
			break;
		case "T25_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.74316) * Math.pow(AT_EC, 1.13058);
			break;
		case "T26_U1203_Gro":
			res = 0.00004 * Math.pow(DN_EC, 1.98818) * Math.pow(AT_EC, 0.9522);
			break;
		case "T27_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.86597) * Math.pow(AT_EC, 0.95817);
			break;
		case "T2z1_Mex":
			res = Math.exp(-1.128349 + 1.782485 * Math.log(DN_EC / 100) + 0.924868 * Math.log(AT_EC));
			break;
		case "T2z2_Gro":
			res = -0.08526 + 4.61495 * Math.pow(DN_EC / 100, 2) + 0.22781 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "T2z2_Tlax":
			res = Math.exp(-1.037999 + 1.814934 * Math.log(DN_EC / 100) + 0.950521 * Math.log(AT_EC));
			break;
		case "T3_Chis":
			res = Math.exp(-10.22400164 + 1.93392327 * Math.log(DN_EC) + 1.12044335 * Math.log(AT_EC));
			break;
		case "T3_QR":
			res = Math.exp(-10.078102 + 1.92989964 * Math.log(DN_EC) + 1.0770193 * Math.log(AT_EC));
			break;
		case "T3_U1001_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.83699) * Math.pow(AT_EC, 1.01607);
			break;
		case "T3_U1002_Dgo":
			res = 0.00008 * Math.pow(DN_EC, 1.91626) * Math.pow(AT_EC, 0.86554);
			break;
		case "T3_U1003_Dgo":
			res = 0.00008 * Math.pow(DN_EC, 2.03749) * Math.pow(AT_EC, 0.78853);
			break;
		case "T3_U1004_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.89458) * Math.pow(AT_EC, 0.93872);
			break;
		case "T3_U1005_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 2.00232) * Math.pow(AT_EC, 0.79361);
			break;
		case "T3_U1006_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.94435) * Math.pow(AT_EC, 0.94293);
			break;
		case "T3_U1007_Dgo":
			res = 0.00011 * Math.pow(DN_EC, 1.87742) * Math.pow(AT_EC, 0.80985);
			break;
		case "T3_U1008_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.82353) * Math.pow(AT_EC, 1.05086);
			break;
		case "T3_U1009_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 1.869) * Math.pow(AT_EC, 1.09056);
			break;
		case "T3_U1010_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 2.10833) * Math.pow(AT_EC, 0.79107);
			break;
		case "T3_U1011_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.99784) * Math.pow(AT_EC, 0.90528);
			break;
		case "T3_U1201y2_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.79902) * Math.pow(AT_EC, 1.0437);
			break;
		case "T3_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.88481) * Math.pow(AT_EC, 0.98452);
			break;
		case "T3_U1204_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.83114) * Math.pow(AT_EC, 1.05311);
			break;
		case "T3_U1205_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.93557) * Math.pow(AT_EC, 0.87194);
			break;
		case "T3_U1302_Hgo":
			res = 0.00007 * Math.pow(DN_EC, 1.76194) * Math.pow(AT_EC, 0.99479);
			break;
		case "T3_U1303_Hgo":
			res = 0.00004 * Math.pow(DN_EC, 1.98903) * Math.pow(AT_EC, 0.99997);
			break;
		case "T3_U1305_Hgo":
			res = 0.00006 * Math.pow(DN_EC, 2.12208) * Math.pow(AT_EC, 0.69047);
			break;
		case "T3_U1401_Jal":
			res = 0.00008 * Math.pow(DN_EC, 1.8619) * Math.pow(AT_EC, 0.94937);
			break;
		case "T3_U1404_Jal":
			res = 0.00007 * Math.pow(DN_EC, 1.99208) * Math.pow(AT_EC, 0.84139);
			break;
		case "T3_U1405_Jal":
			res = 0.00004 * Math.pow(DN_EC, 2.04553) * Math.pow(AT_EC, 0.92615);
			break;
		case "T3_U1406_Jal":
			res = 0.00002 * Math.pow(DN_EC, 2.10768) * Math.pow(AT_EC, 1.0438);
			break;
		case "T3_U1407_Jal":
			res = 0.00005 * Math.pow(DN_EC, 2.15031) * Math.pow(AT_EC, 0.69787);
			break;
		case "T3_U1408_Jal":
			res = 0.00006 * Math.pow(DN_EC, 1.95265) * Math.pow(AT_EC, 0.91457);
			break;
		case "T3_U1410_Jal":
			res = 0.00004 * Math.pow(DN_EC, 1.86523) * Math.pow(AT_EC, 1.16582);
			break;
		case "T3_U1505_Mex":
			res = 0.00006 * Math.pow(DN_EC, 2.10068) * Math.pow(AT_EC, 0.74372);
			break;
		case "T3_U1507_Mex":
			res = 0.00005 * Math.pow(DN_EC, 1.99166) * Math.pow(AT_EC, 0.95628);
			break;
		case "T3_U1508_Mex":
			res = 0.00005 * Math.pow(DN_EC, 1.97258) * Math.pow(AT_EC, 0.92797);
			break;
		case "T3_U1509_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.73205) * Math.pow(AT_EC, 1.14501);
			break;
		case "T3_U1510_Mex":
			res = 0.00003 * Math.pow(DN_EC, 1.99742) * Math.pow(AT_EC, 1.0143);
			break;
		case "T3_U1601_Mich":
			res = 0.00007 * Math.pow(DN_EC, 1.89055) * Math.pow(AT_EC, 0.91218);
			break;
		case "T3_U1603_Mich":
			res = 0.00017 * Math.pow(DN_EC, 1.9634) * Math.pow(AT_EC, 0.52724);
			break;
		case "T3_U1604_Mich":
			res = 0.00012 * Math.pow(DN_EC, 1.76169) * Math.pow(AT_EC, 0.87707);
			break;
		case "T3_U1605_Mich":
			res = 0.00007 * Math.pow(DN_EC, 1.89969) * Math.pow(AT_EC, 0.9162);
			break;
		case "T3_U1607_Mich":
			res = 0.0001 * Math.pow(DN_EC, 1.83255) * Math.pow(AT_EC, 0.84444);
			break;
		case "T3_U1608_Mich":
			res = 0.00009 * Math.pow(DN_EC, 1.82941) * Math.pow(AT_EC, 0.88438);
			break;
		case "T3_U1610_Mich":
			res = 0.00009 * Math.pow(DN_EC, 1.65844) * Math.pow(AT_EC, 1.08049);
			break;
		case "T3_U2001_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.87534) * Math.pow(AT_EC, 0.96396);
			break;
		case "T3_U2003_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.93058) * Math.pow(AT_EC, 0.91274);
			break;
		case "T3_U2007_Oax":
			res = 0.00005 * Math.pow(DN_EC, 2.03262) * Math.pow(AT_EC, 0.89212);
			break;
		case "T3_U2008_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.88161) * Math.pow(AT_EC, 1.05241);
			break;
		case "T3_U2009_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.94028) * Math.pow(AT_EC, 0.86351);
			break;
		case "T3_U2010_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.7681) * Math.pow(AT_EC, 1.13505);
			break;
		case "T3_U2012_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.90528) * Math.pow(AT_EC, 1.08307);
			break;
		case "T3_U2013_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.89801) * Math.pow(AT_EC, 0.94841);
			break;
		case "T3_U2101_Pue":
			res = 0.0001 * Math.pow(DN_EC, 1.7342) * Math.pow(AT_EC, 0.87992);
			break;
		case "T3_U2103_Pue":
			res = 0.00012 * Math.pow(DN_EC, 1.60676) * Math.pow(AT_EC, 1.05386);
			break;
		case "T3_U2105_Pue":
			res = 0.00009 * Math.pow(DN_EC, 1.60091) * Math.pow(AT_EC, 1.12463);
			break;
		case "T3_U2107_Pue":
			res = 0.00006 * Math.pow(DN_EC, 2.15782) * Math.pow(AT_EC, 0.59696);
			break;
		case "T3_U2108_Pue":
			res = 0.00007 * Math.pow(DN_EC, 1.73333) * Math.pow(AT_EC, 1.0186);
			break;
		case "T3_U2301_QRoo":
			res = 0.00032 * Math.pow(DN_EC, 1.56551) * Math.pow(AT_EC, 0.75743);
			break;
		case "T3_U2302_QRoo":
			res = 0.00008 * Math.pow(DN_EC, 1.50911) * Math.pow(AT_EC, 1.30006);
			break;
		case "T3_U2303_QRoo":
			res = 0.00009 * Math.pow(DN_EC, 2.33248) * Math.pow(AT_EC, 0.2543);
			break;
		case "T3_U2304_QRoo":
			res = 0.00008 * Math.pow(DN_EC, 1.94313) * Math.pow(AT_EC, 0.71364);
			break;
		case "T3_U2901_Tlax":
			res = 0.00008 * Math.pow(DN_EC, 1.84318) * Math.pow(AT_EC, 0.99726);
			break;
		case "T3_U2902_Tlax":
			res = 0.00004 * Math.pow(DN_EC, 1.97131) * Math.pow(AT_EC, 1.02665);
			break;
		case "T3_U3004_Ver":
			res = 0.00005 * Math.pow(DN_EC, 1.9058) * Math.pow(AT_EC, 1.00125);
			break;
		case "T3_U3012_Ver":
			res = 0.00005 * Math.pow(DN_EC, 1.90571) * Math.pow(AT_EC, 1.07229);
			break;
		case "T3_U3013_Ver":
			res = 0.00006 * Math.pow(DN_EC, 1.86749) * Math.pow(AT_EC, 1.0438);
			break;
		case "T3_U801_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.03962) * Math.pow(AT_EC, 0.90856);
			break;
		case "T3_U802_Chih":
			res = 0.0001 * Math.pow(DN_EC, 1.73194) * Math.pow(AT_EC, 1.00662);
			break;
		case "T3_U804_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.85344) * Math.pow(AT_EC, 1.04846);
			break;
		case "T3_U805_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.8472) * Math.pow(AT_EC, 1.03422);
			break;
		case "T3_U806_Chih":
			res = 0.00005 * Math.pow(DN_EC, 1.95437) * Math.pow(AT_EC, 1.01606);
			break;
		case "T3_U807_Chih":
			res = 0.00004 * Math.pow(DN_EC, 1.71722) * Math.pow(AT_EC, 1.29709);
			break;
		case "T3_U808_Chih":
			res = 0.00004 * Math.pow(DN_EC, 1.64195) * Math.pow(AT_EC, 1.39789);
			break;
		case "T3_U809_Chih":
			res = 0.00012 * Math.pow(DN_EC, 1.5659) * Math.pow(AT_EC, 1.18256);
			break;
		case "T3_U810_Chih":
			res = 0.00016 * Math.pow(DN_EC, 1.69491) * Math.pow(AT_EC, 0.82788);
			break;
		case "T3_U811_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.95077) * Math.pow(AT_EC, 0.90439);
			break;
		case "T3z2_Gro":
			res = 0.08225 + 0.35644 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "T3z2_Mex":
			res = Math.exp(-1.08569 + 1.916254 * Math.log(DN_EC / 100) + 1.006618 * Math.log(AT_EC));
			break;
		case "T3z3_Tlax":
			res = Math.exp(-1.455580 + 1.699912 * Math.log(DN_EC / 100) + 1.095361 * Math.log(AT_EC));
			break;
		case "T4_Chis":
			res = Math.exp(-10.01137401 + 1.97688779 * Math.log(DN_EC) + 1.02860759 * Math.log(AT_EC));
			break;
		case "T4_QR":
			res = Math.exp(-9.67348922 + 1.86887607 * Math.log(DN_EC) + 1.01858126 * Math.log(AT_EC));
			break;
		case "T4_U1001_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.86782) * Math.pow(AT_EC, 1.05922);
			break;
		case "T4_U1002_Dgo":
			res = 0.00008 * Math.pow(DN_EC, 1.77083) * Math.pow(AT_EC, 1.09408);
			break;
		case "T4_U1003_Dgo":
			res = 0.00015 * Math.pow(DN_EC, 1.96228) * Math.pow(AT_EC, 0.63426);
			break;
		case "T4_U1004_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.953) * Math.pow(AT_EC, 0.86033);
			break;
		case "T4_U1005_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.78423) * Math.pow(AT_EC, 1.12274);
			break;
		case "T4_U1006_Dgo":
			res = 0.00015 * Math.pow(DN_EC, 1.81218) * Math.pow(AT_EC, 0.79456);
			break;
		case "T4_U1007_Dgo":
			res = 0.00014 * Math.pow(DN_EC, 1.75228) * Math.pow(AT_EC, 0.87889);
			break;
		case "T4_U1008_Dgo":
			res = 0.00014 * Math.pow(DN_EC, 1.60507) * Math.pow(AT_EC, 1.07264);
			break;
		case "T4_U1009_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.98357) * Math.pow(AT_EC, 0.86114);
			break;
		case "T4_U1010_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 2.06636) * Math.pow(AT_EC, 0.92022);
			break;
		case "T4_U1011_Dgo":
			res = 0.00024 * Math.pow(DN_EC, 1.80735) * Math.pow(AT_EC, 0.64792);
			break;
		case "T4_U1201_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.92401) * Math.pow(AT_EC, 0.84751);
			break;
		case "T4_U1203_Gro":
			res = 0.00003 * Math.pow(DN_EC, 1.84788) * Math.pow(AT_EC, 1.19236);
			break;
		case "T4_U1204_Gro":
			res = 0.00009 * Math.pow(DN_EC, 1.67362) * Math.pow(AT_EC, 1.04466);
			break;
		case "T4_U1205_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.75262) * Math.pow(AT_EC, 1.15074);
			break;
		case "T4_U1302_Hgo":
			res = 0.00007 * Math.pow(DN_EC, 1.76382) * Math.pow(AT_EC, 1.01135);
			break;
		case "T4_U1303_Hgo":
			res = 0.00008 * Math.pow(DN_EC, 1.63706) * Math.pow(AT_EC, 1.13283);
			break;
		case "T4_U1305_Hgo":
			res = 0.00013 * Math.pow(DN_EC, 1.77192) * Math.pow(AT_EC, 0.73487);
			break;
		case "T4_U1401_Jal":
			res = 0.00007 * Math.pow(DN_EC, 1.91826) * Math.pow(AT_EC, 0.9309);
			break;
		case "T4_U1404_Jal":
			res = 0.00004 * Math.pow(DN_EC, 1.98004) * Math.pow(AT_EC, 0.97395);
			break;
		case "T4_U1405_Jal":
			res = 0.00004 * Math.pow(DN_EC, 1.98391) * Math.pow(AT_EC, 1.02984);
			break;
		case "T4_U1406_Jal":
			res = 0.00005 * Math.pow(DN_EC, 2.15177) * Math.pow(AT_EC, 0.73424);
			break;
		case "T4_U1407_Jal":
			res = 0.00003 * Math.pow(DN_EC, 1.9927) * Math.pow(AT_EC, 1.04053);
			break;
		case "T4_U1408_Jal":
			res = 0.00005 * Math.pow(DN_EC, 2.04036) * Math.pow(AT_EC, 0.89958);
			break;
		case "T4_U1410_Jal":
			res = 0.00004 * Math.pow(DN_EC, 2.18294) * Math.pow(AT_EC, 0.75844);
			break;
		case "T4_U1505_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.73987) * Math.pow(AT_EC, 1.13272);
			break;
		case "T4_U1507_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.78612) * Math.pow(AT_EC, 1.08403);
			break;
		case "T4_U1508_Mex":
			res = 0.00004 * Math.pow(DN_EC, 1.7334) * Math.pow(AT_EC, 1.25297);
			break;
		case "T4_U1509_Mex":
			res = 0.00007 * Math.pow(DN_EC, 1.92093) * Math.pow(AT_EC, 0.81524);
			break;
		case "T4_U1510_Mex":
			res = 0.00004 * Math.pow(DN_EC, 2.15224) * Math.pow(AT_EC, 0.78917);
			break;
		case "T4_U1601_Mich":
			res = 0.00009 * Math.pow(DN_EC, 1.97983) * Math.pow(AT_EC, 0.7097);
			break;
		case "T4_U1603_Mich":
			res = 0.0001 * Math.pow(DN_EC, 1.75905) * Math.pow(AT_EC, 0.95892);
			break;
		case "T4_U1604_Mich":
			res = 0.00005 * Math.pow(DN_EC, 1.93266) * Math.pow(AT_EC, 0.98664);
			break;
		case "T4_U1605_Mich":
			res = 0.0001 * Math.pow(DN_EC, 1.84363) * Math.pow(AT_EC, 0.84749);
			break;
		case "T4_U1607_Mich":
			res = 0.00011 * Math.pow(DN_EC, 1.85724) * Math.pow(AT_EC, 0.80951);
			break;
		case "T4_U1608_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.79113) * Math.pow(AT_EC, 1.0996);
			break;
		case "T4_U1610_Mich":
			res = 0.00011 * Math.pow(DN_EC, 2.00705) * Math.pow(AT_EC, 0.65663);
			break;
		case "T4_U2001_Oax":
			res = 0.00005 * Math.pow(DN_EC, 2.05828) * Math.pow(AT_EC, 0.82453);
			break;
		case "T4_U2003_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.94743) * Math.pow(AT_EC, 0.94935);
			break;
		case "T4_U2007_Oax":
			res = 0.00005 * Math.pow(DN_EC, 2.0443) * Math.pow(AT_EC, 0.8419);
			break;
		case "T4_U2008_Oax":
			res = 0.00003 * Math.pow(DN_EC, 1.93165) * Math.pow(AT_EC, 1.09039);
			break;
		case "T4_U2009_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.92053) * Math.pow(AT_EC, 1.06499);
			break;
		case "T4_U2010_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.92049) * Math.pow(AT_EC, 0.92088);
			break;
		case "T4_U2012_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.99676) * Math.pow(AT_EC, 0.90793);
			break;
		case "T4_U2013_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.72578) * Math.pow(AT_EC, 1.12789);
			break;
		case "T4_U2101_Pue":
			res = 0.00002 * Math.pow(DN_EC, 1.86335) * Math.pow(AT_EC, 1.30261);
			break;
		case "T4_U2103_Pue":
			res = 0.00009 * Math.pow(DN_EC, 1.72747) * Math.pow(AT_EC, 1.07998);
			break;
		case "T4_U2105_Pue":
			res = 0.00004 * Math.pow(DN_EC, 2.20234) * Math.pow(AT_EC, 0.66536);
			break;
		case "T4_U2107_Pue":
			res = 0.00005 * Math.pow(DN_EC, 2.00099) * Math.pow(AT_EC, 0.9233);
			break;
		case "T4_U2108_Pue":
			res = 0.00005 * Math.pow(DN_EC, 1.62539) * Math.pow(AT_EC, 1.31179);
			break;
		case "T4_U2301_QRoo":
			res = 0.00015 * Math.pow(DN_EC, 1.83611) * Math.pow(AT_EC, 0.71389);
			break;
		case "T4_U2302_QRoo":
			res = 0.00012 * Math.pow(DN_EC, 1.65669) * Math.pow(AT_EC, 0.99668);
			break;
		case "T4_U2303_QRoo":
			res = 0.00006 * Math.pow(DN_EC, 2.03452) * Math.pow(AT_EC, 0.70758);
			break;
		case "T4_U2304_QRoo":
			res = 0.00009 * Math.pow(DN_EC, 1.84408) * Math.pow(AT_EC, 0.79741);
			break;
		case "T4_U2901_Tlax":
			res = 0.00005 * Math.pow(DN_EC, 1.94953) * Math.pow(AT_EC, 0.98298);
			break;
		case "T4_U2902_Tlax":
			res = 0.00024 * Math.pow(DN_EC, 1.67285) * Math.pow(AT_EC, 0.75535);
			break;
		case "T4_U3004_Ver":
			res = 0.00005 * Math.pow(DN_EC, 1.86318) * Math.pow(AT_EC, 1.02497);
			break;
		case "T4_U3012_Ver":
			res = 0.00006 * Math.pow(DN_EC, 1.74421) * Math.pow(AT_EC, 1.17697);
			break;
		case "T4_U3013_Ver":
			res = 0.00009 * Math.pow(DN_EC, 2.04618) * Math.pow(AT_EC, 0.63953);
			break;
		case "T4_U801_Chih":
			res = 0.00004 * Math.pow(DN_EC, 2.05674) * Math.pow(AT_EC, 0.92703);
			break;
		case "T4_U802_Chih":
			res = 0.0001 * Math.pow(DN_EC, 1.84431) * Math.pow(AT_EC, 0.88524);
			break;
		case "T4_U804_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.94367) * Math.pow(AT_EC, 0.88911);
			break;
		case "T4_U805_Chih":
			res = 0.00001 * Math.pow(DN_EC, 2.24481) * Math.pow(AT_EC, 1.11621);
			break;
		case "T4_U806_Chih":
			res = 0.00006 * Math.pow(DN_EC, 2.00968) * Math.pow(AT_EC, 0.87833);
			break;
		case "T4_U807_Chih":
			res = 0.00003 * Math.pow(DN_EC, 1.91193) * Math.pow(AT_EC, 1.13653);
			break;
		case "T4_U808_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.21213) * Math.pow(AT_EC, 0.61522);
			break;
		case "T4_U809_Chih":
			res = 0.00003 * Math.pow(DN_EC, 2.16669) * Math.pow(AT_EC, 0.94476);
			break;
		case "T4_U810_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.86155) * Math.pow(AT_EC, 1.04073);
			break;
		case "T4_U811_Chih":
			res = 0.00005 * Math.pow(DN_EC, 1.89429) * Math.pow(AT_EC, 1.09183);
			break;
		case "T4z2_Mex":
			res = Math.exp(-0.69244 + 1.918286 * Math.log(DN_EC / 100) + 0.817385 * Math.log(AT_EC));
			break;
		case "T4z3_Gro":
			res = -0.1034 + 0.74185 * DN_EC / 100 + 0.64447 * Math.pow(DN_EC / 100, 2)
					+ 0.33173 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "T4z3_Tlax":
			res = Math.exp(-1.503329 + 1.717585 * Math.log(DN_EC / 100) + 1.084423 * Math.log(AT_EC));
			break;
		case "T5_Chis":
			res = Math.exp(-9.843341 + 1.92700277 * Math.log(DN_EC) + 1.00612327 * Math.log(AT_EC));
			break;
		case "T5_QR":
			res = Math.exp(-9.5208709 + 1.85580925 * Math.log(DN_EC) + 0.96207943 * Math.log(AT_EC));
			break;
		case "T5_U1001_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.79279) * Math.pow(AT_EC, 1.09127);
			break;
		case "T5_U1002_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.9345) * Math.pow(AT_EC, 0.999);
			break;
		case "T5_U1003_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 2.28511) * Math.pow(AT_EC, 0.70113);
			break;
		case "T5_U1004_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.91787) * Math.pow(AT_EC, 0.98318);
			break;
		case "T5_U1005_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 2.12146) * Math.pow(AT_EC, 0.85116);
			break;
		case "T5_U1006_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 2.12756) * Math.pow(AT_EC, 0.74636);
			break;
		case "T5_U1007_Dgo":
			res = 0.0001 * Math.pow(DN_EC, 1.69469) * Math.pow(AT_EC, 1.0923);
			break;
		case "T5_U1008_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 1.98731) * Math.pow(AT_EC, 1.02409);
			break;
		case "T5_U1009_Dgo":
			res = 0.00003 * Math.pow(DN_EC, 2.10177) * Math.pow(AT_EC, 1.03353);
			break;
		case "T5_U1010_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 2.06636) * Math.pow(AT_EC, 0.92022);
			break;
		case "T5_U1011_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.8987) * Math.pow(AT_EC, 0.9726);
			break;
		case "T5_U1201y2_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.8982) * Math.pow(AT_EC, 0.97267);
			break;
		case "T5_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.87377) * Math.pow(AT_EC, 0.96465);
			break;
		case "T5_U1204_Gro":
			res = 0.00013 * Math.pow(DN_EC, 1.82119) * Math.pow(AT_EC, 0.72735);
			break;
		case "T5_U1205_Gro":
			res = 0.00003 * Math.pow(DN_EC, 1.80945) * Math.pow(AT_EC, 1.22993);
			break;
		case "T5_U1302_Hgo":
			res = 0.00004 * Math.pow(DN_EC, 1.83083) * Math.pow(AT_EC, 1.16068);
			break;
		case "T5_U1303_Hgo":
			res = 0.00008 * Math.pow(DN_EC, 1.7991) * Math.pow(AT_EC, 1.03742);
			break;
		case "T5_U1305_Hgo":
			res = 0.00005 * Math.pow(DN_EC, 1.80836) * Math.pow(AT_EC, 1.0436);
			break;
		case "T5_U1401_Jal":
			res = 0.00008 * Math.pow(DN_EC, 1.94172) * Math.pow(AT_EC, 0.85746);
			break;
		case "T5_U1404_Jal":
			res = 0.00003 * Math.pow(DN_EC, 2.03387) * Math.pow(AT_EC, 1.03049);
			break;
		case "T5_U1405_Jal":
			res = 0.00005 * Math.pow(DN_EC, 2.03105) * Math.pow(AT_EC, 0.87804);
			break;
		case "T5_U1406_Jal":
			res = 0.00004 * Math.pow(DN_EC, 2.11924) * Math.pow(AT_EC, 0.87698);
			break;
		case "T5_U1407_Jal":
			res = 0.00004 * Math.pow(DN_EC, 2.05296) * Math.pow(AT_EC, 0.92951);
			break;
		case "T5_U1408_Jal":
			res = 0.00002 * Math.pow(DN_EC, 2.20463) * Math.pow(AT_EC, 0.94039);
			break;
		case "T5_U1410_Jal":
			res = 0.00006 * Math.pow(DN_EC, 2.00007) * Math.pow(AT_EC, 0.85983);
			break;
		case "T5_U1505_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.85702) * Math.pow(AT_EC, 0.99713);
			break;
		case "T5_U1507_Mex":
			res = 0.00005 * Math.pow(DN_EC, 1.93742) * Math.pow(AT_EC, 0.98587);
			break;
		case "T5_U1508_Mex":
			res = 0.00004 * Math.pow(DN_EC, 1.73648) * Math.pow(AT_EC, 1.31797);
			break;
		case "T5_U1509_Mex":
			res = 0.00007 * Math.pow(DN_EC, 1.67308) * Math.pow(AT_EC, 1.07884);
			break;
		case "T5_U1510_Mex":
			res = 0.00012 * Math.pow(DN_EC, 1.94146) * Math.pow(AT_EC, 0.67712);
			break;
		case "T5_U1601_Mich":
			res = 0.00014 * Math.pow(DN_EC, 1.90867) * Math.pow(AT_EC, 0.66513);
			break;
		case "T5_U1603_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.85249) * Math.pow(AT_EC, 0.99732);
			break;
		case "T5_U1604_Mich":
			res = 0.00008 * Math.pow(DN_EC, 1.81161) * Math.pow(AT_EC, 0.97288);
			break;
		case "T5_U1605_Mich":
			res = 0.00005 * Math.pow(DN_EC, 2.0059) * Math.pow(AT_EC, 0.93032);
			break;
		case "T5_U1607_Mich":
			res = 0.00009 * Math.pow(DN_EC, 1.68501) * Math.pow(AT_EC, 1.08455);
			break;
		case "T5_U1608_Mich":
			res = 0.00005 * Math.pow(DN_EC, 1.88171) * Math.pow(AT_EC, 1.01454);
			break;
		case "T5_U1610_Mich":
			res = 0.00007 * Math.pow(DN_EC, 1.62629) * Math.pow(AT_EC, 1.26518);
			break;
		case "T5_U2001_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.92441) * Math.pow(AT_EC, 0.99975);
			break;
		case "T5_U2003_Oax":
			res = 0.00009 * Math.pow(DN_EC, 1.83206) * Math.pow(AT_EC, 0.9082);
			break;
		case "T5_U2007_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.85794) * Math.pow(AT_EC, 0.94142);
			break;
		case "T5_U2008_Oax":
			res = 0.00002 * Math.pow(DN_EC, 1.80587) * Math.pow(AT_EC, 1.36202);
			break;
		case "T5_U2009_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.85461) * Math.pow(AT_EC, 1.01316);
			break;
		case "T5_U2010_Oax":
			res = 0.00012 * Math.pow(DN_EC, 1.79318) * Math.pow(AT_EC, 0.86415);
			break;
		case "T5_U2012_Oax":
			res = 0.00004 * Math.pow(DN_EC, 2.00896) * Math.pow(AT_EC, 0.95837);
			break;
		case "T5_U2013_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.96253) * Math.pow(AT_EC, 0.81775);
			break;
		case "T5_U2101_Pue":
			res = 0.00006 * Math.pow(DN_EC, 1.99376) * Math.pow(AT_EC, 0.90054);
			break;
		case "T5_U2103_Pue":
			res = 0.00002 * Math.pow(DN_EC, 1.91536) * Math.pow(AT_EC, 1.30782);
			break;
		case "T5_U2105_Pue":
			res = 0.00008 * Math.pow(DN_EC, 1.9926) * Math.pow(AT_EC, 0.71239);
			break;
		case "T5_U2107_Pue":
			res = 0.00003 * Math.pow(DN_EC, 1.99763) * Math.pow(AT_EC, 0.97406);
			break;
		case "T5_U2108_Pue":
			res = 0.00006 * Math.pow(DN_EC, 1.83581) * Math.pow(AT_EC, 1.07277);
			break;
		case "T5_U2301_QRoo":
			res = 0.00007 * Math.pow(DN_EC, 2.39475) * Math.pow(AT_EC, 0.31482);
			break;
		case "T5_U2302_QRoo":
			res = 0.0002 * Math.pow(DN_EC, 1.74723) * Math.pow(AT_EC, 0.70306);
			break;
		case "T5_U2303_QRoo":
			res = 0.00007 * Math.pow(DN_EC, 2.10053) * Math.pow(AT_EC, 0.60424);
			break;
		case "T5_U2304_QRoo":
			res = 0.0001 * Math.pow(DN_EC, 1.99384) * Math.pow(AT_EC, 0.57302);
			break;
		case "T5_U2901_Tlax":
			res = 0.00012 * Math.pow(DN_EC, 1.87375) * Math.pow(AT_EC, 0.77347);
			break;
		case "T5_U2902_Tlax":
			res = 0.00009 * Math.pow(DN_EC, 1.93599) * Math.pow(AT_EC, 0.79557);
			break;
		case "T5_U3004_Ver":
			res = 0.00005 * Math.pow(DN_EC, 1.86913) * Math.pow(AT_EC, 1.03914);
			break;
		case "T5_U3012_Ver":
			res = 0.00003 * Math.pow(DN_EC, 2.0141) * Math.pow(AT_EC, 1.13703);
			break;
		case "T5_U3013_Ver":
			res = 0.00004 * Math.pow(DN_EC, 2.00155) * Math.pow(AT_EC, 1.06694);
			break;
		case "T5_U801_Chih":
			res = 0.00006 * Math.pow(DN_EC, 2.01262) * Math.pow(AT_EC, 0.80547);
			break;
		case "T5_U802_Chih":
			res = 0.00015 * Math.pow(DN_EC, 1.66695) * Math.pow(AT_EC, 0.90882);
			break;
		case "T5_U804_Chih":
			res = 0.00008 * Math.pow(DN_EC, 1.97318) * Math.pow(AT_EC, 0.80803);
			break;
		case "T5_U805_Chih":
			res = 0.00005 * Math.pow(DN_EC, 1.87802) * Math.pow(AT_EC, 0.99663);
			break;
		case "T5_U806_Chih":
			res = 0.00004 * Math.pow(DN_EC, 2.11528) * Math.pow(AT_EC, 0.8922);
			break;
		case "T5_U807_Chih":
			res = 0.00014 * Math.pow(DN_EC, 1.58693) * Math.pow(AT_EC, 0.98344);
			break;
		case "T5_U808_Chih":
			res = 0.00009 * Math.pow(DN_EC, 1.91124) * Math.pow(AT_EC, 0.82715);
			break;
		case "T5_U809_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.99394) * Math.pow(AT_EC, 0.85832);
			break;
		case "T5_U810_Chih":
			res = 0.00004 * Math.pow(DN_EC, 2.01185) * Math.pow(AT_EC, 0.99089);
			break;
		case "T5_U811_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.97809) * Math.pow(AT_EC, 0.81327);
			break;
		case "T5z2_Mex":
			res = Math.exp(-1.51205 + 1.691204 * Math.log(DN_EC / 100) + 1.057673 * Math.log(AT_EC));
			break;
		case "T5z3_Gro":
			res = 0.17106 + 0.33731 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "T6_Chis":
			res = Math.exp(-9.8528306 + 1.93994057 * Math.log(DN_EC) + 1.0307694 * Math.log(AT_EC));
			break;
		case "T6_QR":
			res = Math.exp(-9.75894522 + 1.90722681 * Math.log(DN_EC) + 1.01257027 * Math.log(AT_EC));
			break;
		case "T6_U1001_Dgo":
			res = 0.00009 * Math.pow(DN_EC, 1.83358) * Math.pow(AT_EC, 0.95271);
			break;
		case "T6_U1002_Dgo":
			res = 0.00012 * Math.pow(DN_EC, 2.05071) * Math.pow(AT_EC, 0.52863);
			break;
		case "T6_U1004_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 1.90387) * Math.pow(AT_EC, 1.03643);
			break;
		case "T6_U1005_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.99304) * Math.pow(AT_EC, 0.91377);
			break;
		case "T6_U1006_Dgo":
			res = 0.00021 * Math.pow(DN_EC, 1.7538) * Math.pow(AT_EC, 0.71871);
			break;
		case "T6_U1007_Dgo":
			res = 0.00012 * Math.pow(DN_EC, 1.75424) * Math.pow(AT_EC, 0.84555);
			break;
		case "T6_U1008_Dgo":
			res = 0.00009 * Math.pow(DN_EC, 1.92699) * Math.pow(AT_EC, 0.82181);
			break;
		case "T6_U1009_Dgo":
			res = 0.00002 * Math.pow(DN_EC, 2.09571) * Math.pow(AT_EC, 1.08267);
			break;
		case "T6_U1010_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.92908) * Math.pow(AT_EC, 0.95192);
			break;
		case "T6_U1011_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 1.98777) * Math.pow(AT_EC, 0.94393);
			break;
		case "T6_U1201_Gro":
			res = 0.00009 * Math.pow(DN_EC, 1.85279) * Math.pow(AT_EC, 0.86927);
			break;
		case "T6_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.77047) * Math.pow(AT_EC, 1.08496);
			break;
		case "T6_U1204_Gro":
			res = 0.00009 * Math.pow(DN_EC, 1.69058) * Math.pow(AT_EC, 0.98779);
			break;
		case "T6_U1205_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.70121) * Math.pow(AT_EC, 1.07272);
			break;
		case "T6_U1302_Hgo":
			res = 0.00008 * Math.pow(DN_EC, 1.79472) * Math.pow(AT_EC, 0.96587);
			break;
		case "T6_U1303_Hgo":
			res = 0.00003 * Math.pow(DN_EC, 2.00166) * Math.pow(AT_EC, 1.04188);
			break;
		case "T6_U1305_Hgo":
			res = 0.00007 * Math.pow(DN_EC, 1.95166) * Math.pow(AT_EC, 0.80933);
			break;
		case "T6_U1401_Jal":
			res = 0.00006 * Math.pow(DN_EC, 2.13895) * Math.pow(AT_EC, 0.63737);
			break;
		case "T6_U1404_Jal":
			res = 0.00005 * Math.pow(DN_EC, 2.07339) * Math.pow(AT_EC, 0.78986);
			break;
		case "T6_U1405_Jal":
			res = 0.00007 * Math.pow(DN_EC, 1.72532) * Math.pow(AT_EC, 1.08818);
			break;
		case "T6_U1406_Jal":
			res = 0.00019 * Math.pow(DN_EC, 1.82013) * Math.pow(AT_EC, 0.65369);
			break;
		case "T6_U1407_Jal":
			res = 0.00004 * Math.pow(DN_EC, 1.83445) * Math.pow(AT_EC, 1.15501);
			break;
		case "T6_U1408_Jal":
			res = 0.00003 * Math.pow(DN_EC, 2.05024) * Math.pow(AT_EC, 1.01535);
			break;
		case "T6_U1410_Jal":
			res = 0.00004 * Math.pow(DN_EC, 2.00572) * Math.pow(AT_EC, 0.98905);
			break;
		case "T6_U1505_Mex":
			res = 0.00003 * Math.pow(DN_EC, 2.19211) * Math.pow(AT_EC, 0.82495);
			break;
		case "T6_U1507_Mex":
			res = 0.00004 * Math.pow(DN_EC, 1.88146) * Math.pow(AT_EC, 1.15028);
			break;
		case "T6_U1508_Mex":
			res = 0.00008 * Math.pow(DN_EC, 1.72245) * Math.pow(AT_EC, 1.06962);
			break;
		case "T6_U1509_Mex":
			res = 0.00005 * Math.pow(DN_EC, 2.03601) * Math.pow(AT_EC, 0.83725);
			break;
		case "T6_U1510_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.74105) * Math.pow(AT_EC, 1.09461);
			break;
		case "T6_U1601_Mich":
			res = 0.00004 * Math.pow(DN_EC, 1.75214) * Math.pow(AT_EC, 1.21127);
			break;
		case "T6_U1603_Mich":
			res = 0.00011 * Math.pow(DN_EC, 1.7689) * Math.pow(AT_EC, 0.87272);
			break;
		case "T6_U1604_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.88213) * Math.pow(AT_EC, 0.99285);
			break;
		case "T6_U1605_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.84688) * Math.pow(AT_EC, 1.06104);
			break;
		case "T6_U1607_Mich":
			res = 0.00005 * Math.pow(DN_EC, 1.75119) * Math.pow(AT_EC, 1.22266);
			break;
		case "T6_U1608_Mich":
			res = 0.00009 * Math.pow(DN_EC, 1.80901) * Math.pow(AT_EC, 0.94962);
			break;
		case "T6_U1610_Mich":
			res = 0.00022 * Math.pow(DN_EC, 1.71406) * Math.pow(AT_EC, 0.75067);
			break;
		case "T6_U2001_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.83655) * Math.pow(AT_EC, 1.07612);
			break;
		case "T6_U2003_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.93951) * Math.pow(AT_EC, 1.00491);
			break;
		case "T6_U2007_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.75797) * Math.pow(AT_EC, 1.09047);
			break;
		case "T6_U2008_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.8555) * Math.pow(AT_EC, 1.14414);
			break;
		case "T6_U2009_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.7676) * Math.pow(AT_EC, 1.10555);
			break;
		case "T6_U2010_Oax":
			res = 0.00011 * Math.pow(DN_EC, 1.90911) * Math.pow(AT_EC, 0.74508);
			break;
		case "T6_U2012_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.87353) * Math.pow(AT_EC, 1.10471);
			break;
		case "T6_U2013_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.95294) * Math.pow(AT_EC, 0.85352);
			break;
		case "T6_U2101_Pue":
			res = 0.00005 * Math.pow(DN_EC, 1.86727) * Math.pow(AT_EC, 1.08343);
			break;
		case "T6_U2103_Pue":
			res = 0.00006 * Math.pow(DN_EC, 1.79927) * Math.pow(AT_EC, 1.01495);
			break;
		case "T6_U2105_Pue":
			res = 0.00015 * Math.pow(DN_EC, 1.60556) * Math.pow(AT_EC, 1.016);
			break;
		case "T6_U2107_Pue":
			res = 0.00007 * Math.pow(DN_EC, 1.8641) * Math.pow(AT_EC, 0.9722);
			break;
		case "T6_U2108_Pue":
			res = 0.00008 * Math.pow(DN_EC, 1.69) * Math.pow(AT_EC, 1.11055);
			break;
		case "T6_U2301_QRoo":
			res = 0.00005 * Math.pow(DN_EC, 1.18838) * Math.pow(AT_EC, 1.84537);
			break;
		case "T6_U2302_QRoo":
			res = 0.00037 * Math.pow(DN_EC, 1.55894) * Math.pow(AT_EC, 0.71828);
			break;
		case "T6_U2303_QRoo":
			res = 0.00004 * Math.pow(DN_EC, 2.2082) * Math.pow(AT_EC, 0.66695);
			break;
		case "T6_U2304_QRoo":
			res = 0.00009 * Math.pow(DN_EC, 1.94955) * Math.pow(AT_EC, 0.69216);
			break;
		case "T6_U2901_Tlax":
			res = 0.00012 * Math.pow(DN_EC, 1.70944) * Math.pow(AT_EC, 0.97364);
			break;
		case "T6_U2902_Tlax":
			res = 0.00009 * Math.pow(DN_EC, 1.76419) * Math.pow(AT_EC, 1.00432);
			break;
		case "T6_U3004_Ver":
			res = 0.00012 * Math.pow(DN_EC, 1.62795) * Math.pow(AT_EC, 1.03726);
			break;
		case "T6_U3012_Ver":
			res = 0.00006 * Math.pow(DN_EC, 1.7876) * Math.pow(AT_EC, 1.08113);
			break;
		case "T6_U3013_Ver":
			res = 0.00008 * Math.pow(DN_EC, 1.99615) * Math.pow(AT_EC, 0.70608);
			break;
		case "T6_U801_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.05073) * Math.pow(AT_EC, 0.88292);
			break;
		case "T6_U802_Chih":
			res = 0.00008 * Math.pow(DN_EC, 1.82537) * Math.pow(AT_EC, 0.90578);
			break;
		case "T6_U804_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.98621) * Math.pow(AT_EC, 0.91734);
			break;
		case "T6_U805_Chih":
			res = 0.00004 * Math.pow(DN_EC, 1.81878) * Math.pow(AT_EC, 1.15847);
			break;
		case "T6_U806_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.8815) * Math.pow(AT_EC, 0.95474);
			break;
		case "T6_U807_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.92408) * Math.pow(AT_EC, 0.81686);
			break;
		case "T6_U808_Chih":
			res = 0.00008 * Math.pow(DN_EC, 2.10277) * Math.pow(AT_EC, 0.60646);
			break;
		case "T6_U809_Chih":
			res = 0.00009 * Math.pow(DN_EC, 1.85285) * Math.pow(AT_EC, 0.87639);
			break;
		case "T6_U810_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.95098) * Math.pow(AT_EC, 0.87901);
			break;
		case "T6_U811_Chih":
			res = 0.00008 * Math.pow(DN_EC, 1.76923) * Math.pow(AT_EC, 1.02294);
			break;
		case "T6z3_Mex":
			res = Math.exp(-0.973370 + 1.836140 * Math.log(DN_EC / 100) + 0.96391 * Math.log(AT_EC));
			break;
		case "T6z4_Gro":
			res = 0.00645 + 0.00249 * AT_EC + 0.64169 * Math.pow(DN_EC / 100, 2)
					+ 0.34731 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "T7_Chis":
			res = Math.exp(-9.86139158 + 1.93994057 * Math.log(DN_EC) + 1.04126898 * Math.log(AT_EC));
			break;
		case "T7_QR":
			res = Math.exp(-9.89061571 + 1.924447818 * Math.log(DN_EC) + 1.04545276 * Math.log(AT_EC));
			break;
		case "T7_U1001_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 2.14275) * Math.pow(AT_EC, 0.67224);
			break;
		case "T7_U1002_Dgo":
			res = 0.00008 * Math.pow(DN_EC, 1.67614) * Math.pow(AT_EC, 1.16197);
			break;
		case "T7_U1003_Dgo":
			res = 0.0001 * Math.pow(DN_EC, 2.07845) * Math.pow(AT_EC, 0.58545);
			break;
		case "T7_U1004_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 2.07854) * Math.pow(AT_EC, 0.82172);
			break;
		case "T7_U1006_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.8332) * Math.pow(AT_EC, 1.0539);
			break;
		case "T7_U1007_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.9062) * Math.pow(AT_EC, 0.91576);
			break;
		case "T7_U1008_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.86667) * Math.pow(AT_EC, 0.94131);
			break;
		case "T7_U1009_Dgo":
			res = 0.00003 * Math.pow(DN_EC, 1.86245) * Math.pow(AT_EC, 1.24064);
			break;
		case "T7_U1010_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.87061) * Math.pow(AT_EC, 1.02768);
			break;
		case "T7_U1011_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 2.03672) * Math.pow(AT_EC, 0.7885);
			break;
		case "T7_U1201_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.8853) * Math.pow(AT_EC, 0.94692);
			break;
		case "T7_U1203_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.87297) * Math.pow(AT_EC, 1.05348);
			break;
		case "T7_U1204_Gro":
			res = 0.0001 * Math.pow(DN_EC, 1.79784) * Math.pow(AT_EC, 0.84901);
			break;
		case "T7_U1205_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.93281) * Math.pow(AT_EC, 0.81579);
			break;
		case "T7_U1302_Hgo":
			res = 0.00004 * Math.pow(DN_EC, 1.83348) * Math.pow(AT_EC, 1.16153);
			break;
		case "T7_U1303_Hgo":
			res = 0.00004 * Math.pow(DN_EC, 1.97967) * Math.pow(AT_EC, 0.97866);
			break;
		case "T7_U1305_Hgo":
			res = 0.00006 * Math.pow(DN_EC, 1.96351) * Math.pow(AT_EC, 0.86282);
			break;
		case "T7_U1401_Jal":
			res = 0.00007 * Math.pow(DN_EC, 1.94421) * Math.pow(AT_EC, 0.84086);
			break;
		case "T7_U1404_Jal":
			res = 0.00004 * Math.pow(DN_EC, 2.02326) * Math.pow(AT_EC, 0.91734);
			break;
		case "T7_U1405_Jal":
			res = 0.0001 * Math.pow(DN_EC, 1.78585) * Math.pow(AT_EC, 0.9044);
			break;
		case "T7_U1406_Jal":
			res = 0.00006 * Math.pow(DN_EC, 2.10516) * Math.pow(AT_EC, 0.63717);
			break;
		case "T7_U1407_Jal":
			res = 0.00008 * Math.pow(DN_EC, 1.82152) * Math.pow(AT_EC, 0.88533);
			break;
		case "T7_U1408_Jal":
			res = 0.00004 * Math.pow(DN_EC, 1.89847) * Math.pow(AT_EC, 1.06621);
			break;
		case "T7_U1410_Jal":
			res = 0.00006 * Math.pow(DN_EC, 1.86077) * Math.pow(AT_EC, 1.00268);
			break;
		case "T7_U1505_Mex":
			res = 0.00007 * Math.pow(DN_EC, 1.87429) * Math.pow(AT_EC, 0.96011);
			break;
		case "T7_U1507_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.70925) * Math.pow(AT_EC, 1.13826);
			break;
		case "T7_U1508_Mex":
			res = 0.00005 * Math.pow(DN_EC, 1.93483) * Math.pow(AT_EC, 0.92207);
			break;
		case "T7_U1509_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.72953) * Math.pow(AT_EC, 1.13649);
			break;
		case "T7_U1510_Mex":
			res = 0.00009 * Math.pow(DN_EC, 1.71075) * Math.pow(AT_EC, 0.98634);
			break;
		case "T7_U1601_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.75454) * Math.pow(AT_EC, 1.11334);
			break;
		case "T7_U1603_Mich":
			res = 0.00004 * Math.pow(DN_EC, 1.55198) * Math.pow(AT_EC, 1.42953);
			break;
		case "T7_U1604_Mich":
			res = 0.00004 * Math.pow(DN_EC, 1.89152) * Math.pow(AT_EC, 1.07349);
			break;
		case "T7_U1605_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.85816) * Math.pow(AT_EC, 1.03342);
			break;
		case "T7_U1607_Mich":
			res = 0.00015 * Math.pow(DN_EC, 1.89879) * Math.pow(AT_EC, 0.63228);
			break;
		case "T7_U1608_Mich":
			res = 0.00022 * Math.pow(DN_EC, 1.70208) * Math.pow(AT_EC, 0.69509);
			break;
		case "T7_U1610_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.96053) * Math.pow(AT_EC, 0.82385);
			break;
		case "T7_U2001_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.85811) * Math.pow(AT_EC, 0.95672);
			break;
		case "T7_U2003_Oax":
			res = 0.00007 * Math.pow(DN_EC, 1.58934) * Math.pow(AT_EC, 1.26877);
			break;
		case "T7_U2007_Oax":
			res = 0.0001 * Math.pow(DN_EC, 1.68734) * Math.pow(AT_EC, 0.95855);
			break;
		case "T7_U2008_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.80967) * Math.pow(AT_EC, 1.14314);
			break;
		case "T7_U2009_Oax":
			res = 0.00009 * Math.pow(DN_EC, 1.73596) * Math.pow(AT_EC, 0.99685);
			break;
		case "T7_U2010_Oax":
			res = 0.00008 * Math.pow(DN_EC, 1.82541) * Math.pow(AT_EC, 0.98782);
			break;
		case "T7_U2012_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.93095) * Math.pow(AT_EC, 0.90416);
			break;
		case "T7_U2013_Oax":
			res = 0.00008 * Math.pow(DN_EC, 1.96541) * Math.pow(AT_EC, 0.74537);
			break;
		case "T7_U2101_Pue":
			res = 0.00003 * Math.pow(DN_EC, 2.07684) * Math.pow(AT_EC, 0.94066);
			break;
		case "T7_U2103_Pue":
			res = 0.00008 * Math.pow(DN_EC, 1.66246) * Math.pow(AT_EC, 1.14258);
			break;
		case "T7_U2105_Pue":
			res = 0.0001 * Math.pow(DN_EC, 1.79224) * Math.pow(AT_EC, 0.9019);
			break;
		case "T7_U2107_Pue":
			res = 0.00009 * Math.pow(DN_EC, 1.74619) * Math.pow(AT_EC, 1.01447);
			break;
		case "T7_U2108_Pue":
			res = 0.00006 * Math.pow(DN_EC, 1.79793) * Math.pow(AT_EC, 1.10758);
			break;
		case "T7_U2301_QRoo":
			res = 0.00405 * Math.pow(DN_EC, 1.23099) * Math.pow(AT_EC, 0.4354);
			break;
		case "T7_U2302_QRoo":
			res = 0.00263 * Math.pow(DN_EC, 1.69337) * Math.pow(AT_EC, -0.09625);
			break;
		case "T7_U2303_QRoo":
			res = 0.00004 * Math.pow(DN_EC, 2.12007) * Math.pow(AT_EC, 0.76244);
			break;
		case "T7_U2304_QRoo":
			res = 0.00009 * Math.pow(DN_EC, 1.86659) * Math.pow(AT_EC, 0.78013);
			break;
		case "T7_U2901_Tlax":
			res = 0.00006 * Math.pow(DN_EC, 1.69351) * Math.pow(AT_EC, 1.22077);
			break;
		case "T7_U2902_Tlax":
			res = 0.00004 * Math.pow(DN_EC, 1.96467) * Math.pow(AT_EC, 1.00411);
			break;
		case "T7_U3004_Ver":
			res = 0.00004 * Math.pow(DN_EC, 1.89487) * Math.pow(AT_EC, 1.14547);
			break;
		case "T7_U3012_Ver":
			res = 0.00009 * Math.pow(DN_EC, 1.81846) * Math.pow(AT_EC, 0.89476);
			break;
		case "T7_U3013_Ver":
			res = 0.00006 * Math.pow(DN_EC, 1.94544) * Math.pow(AT_EC, 0.90587);
			break;
		case "T7_U801_Chih":
			res = 0.00007 * Math.pow(DN_EC, 2.00347) * Math.pow(AT_EC, 0.73035);
			break;
		case "T7_U802_Chih":
			res = 0.00006 * Math.pow(DN_EC, 2.02776) * Math.pow(AT_EC, 0.73638);
			break;
		case "T7_U804_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.94191) * Math.pow(AT_EC, 0.93453);
			break;
		case "T7_U805_Chih":
			res = 0.00002 * Math.pow(DN_EC, 2.08009) * Math.pow(AT_EC, 1.05183);
			break;
		case "T7_U806_Chih":
			res = 0.00005 * Math.pow(DN_EC, 1.95647) * Math.pow(AT_EC, 0.9909);
			break;
		case "T7_U807_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.86394) * Math.pow(AT_EC, 0.86163);
			break;
		case "T7_U808_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.93664) * Math.pow(AT_EC, 0.96329);
			break;
		case "T7_U809_Chih":
			res = 0.00003 * Math.pow(DN_EC, 2.31772) * Math.pow(AT_EC, 0.73232);
			break;
		case "T7_U810_Chih":
			res = 0.00004 * Math.pow(DN_EC, 2.07746) * Math.pow(AT_EC, 0.92124);
			break;
		case "T7_U811_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.82139) * Math.pow(AT_EC, 1.06265);
			break;
		case "T7z3_Mex":
			res = Math.exp(0.142440 + 2.178814 * Math.log(DN_EC / 100) + 0.668873 * Math.log(AT_EC));
			break;
		case "T7z4_CF_Gro":
			res = 0.42577 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "T8_Chis":
			res = Math.exp(-10.1261214 + 1.97537735 * Math.log(DN_EC) + 1.05085957 * Math.log(AT_EC));
			break;
		case "T8_QR":
			res = Math.exp(-9.68220947 + 1.89488929 * Math.log(DN_EC) + 1.01453225 * Math.log(AT_EC));
			break;
		case "T8_U1001_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 2.08427) * Math.pow(AT_EC, 0.77182);
			break;
		case "T8_U1002_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.8233) * Math.pow(AT_EC, 1.0226);
			break;
		case "T8_U1003_Dgo":
			res = 0.00011 * Math.pow(DN_EC, 1.82414) * Math.pow(AT_EC, 0.90378);
			break;
		case "T8_U1004_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.93976) * Math.pow(AT_EC, 0.93785);
			break;
		case "T8_U1006_Dgo":
			res = 0.00009 * Math.pow(DN_EC, 1.67915) * Math.pow(AT_EC, 1.09573);
			break;
		case "T8_U1007_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.85076) * Math.pow(AT_EC, 0.91171);
			break;
		case "T8_U1008_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.95316) * Math.pow(AT_EC, 0.92688);
			break;
		case "T8_U1009_Dgo":
			res = 0.00008 * Math.pow(DN_EC, 1.763) * Math.pow(AT_EC, 1.01337);
			break;
		case "T8_U1010_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 1.95287) * Math.pow(AT_EC, 0.99991);
			break;
		case "T8_U1011_Dgo":
			res = 0.00017 * Math.pow(DN_EC, 1.68446) * Math.pow(AT_EC, 0.85398);
			break;
		case "T8_U1201y2_Gro":
			res = 0.00008 * Math.pow(DN_EC, 1.9271) * Math.pow(AT_EC, 0.78496);
			break;
		case "T8_U1203_Gro":
			res = 0.00005 * Math.pow(DN_EC, 1.79849) * Math.pow(AT_EC, 1.14481);
			break;
		case "T8_U1204_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.72729) * Math.pow(AT_EC, 1.05279);
			break;
		case "T8_U1205_Gro":
			res = 0.00008 * Math.pow(DN_EC, 1.83241) * Math.pow(AT_EC, 0.93793);
			break;
		case "T8_U1302_Hgo":
			res = 0.00006 * Math.pow(DN_EC, 1.77154) * Math.pow(AT_EC, 1.03384);
			break;
		case "T8_U1303_Hgo":
			res = 0.00003 * Math.pow(DN_EC, 1.568) * Math.pow(AT_EC, 1.47853);
			break;
		case "T8_U1305_Hgo":
			res = 0.00004 * Math.pow(DN_EC, 2.04243) * Math.pow(AT_EC, 0.8387);
			break;
		case "T8_U1401_Jal":
			res = 0.00013 * Math.pow(DN_EC, 1.92204) * Math.pow(AT_EC, 0.60389);
			break;
		case "T8_U1404_Jal":
			res = 0.0001 * Math.pow(DN_EC, 1.80515) * Math.pow(AT_EC, 0.92785);
			break;
		case "T8_U1405_Jal":
			res = 0.00006 * Math.pow(DN_EC, 1.78647) * Math.pow(AT_EC, 1.05085);
			break;
		case "T8_U1406_Jal":
			res = 0.00006 * Math.pow(DN_EC, 2.05454) * Math.pow(AT_EC, 0.75375);
			break;
		case "T8_U1407_Jal":
			res = 0.00002 * Math.pow(DN_EC, 1.88391) * Math.pow(AT_EC, 1.26622);
			break;
		case "T8_U1408_Jal":
			res = 0.00002 * Math.pow(DN_EC, 2.10035) * Math.pow(AT_EC, 0.98971);
			break;
		case "T8_U1410_Jal":
			res = 0.00003 * Math.pow(DN_EC, 2.02775) * Math.pow(AT_EC, 1.04012);
			break;
		case "T8_U1505_Mex":
			res = 0.00004 * Math.pow(DN_EC, 2.09133) * Math.pow(AT_EC, 0.85085);
			break;
		case "T8_U1507_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.96552) * Math.pow(AT_EC, 0.87073);
			break;
		case "T8_U1508_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.94283) * Math.pow(AT_EC, 0.87185);
			break;
		case "T8_U1509_Mex":
			res = 0.00011 * Math.pow(DN_EC, 1.57206) * Math.pow(AT_EC, 1.07944);
			break;
		case "T8_U1510_Mex":
			res = 0.00005 * Math.pow(DN_EC, 1.69791) * Math.pow(AT_EC, 1.2772);
			break;
		case "T8_U1601_Mich":
			res = 0.00007 * Math.pow(DN_EC, 1.89245) * Math.pow(AT_EC, 0.87841);
			break;
		case "T8_U1603_Mich":
			res = 0.00006 * Math.pow(DN_EC, 1.69419) * Math.pow(AT_EC, 1.14055);
			break;
		case "T8_U1604_Mich":
			res = 0.00003 * Math.pow(DN_EC, 2.01624) * Math.pow(AT_EC, 0.98493);
			break;
		case "T8_U1605_Mich":
			res = 0.00007 * Math.pow(DN_EC, 1.86601) * Math.pow(AT_EC, 0.94676);
			break;
		case "T8_U1607_Mich":
			res = 0.00005 * Math.pow(DN_EC, 1.93562) * Math.pow(AT_EC, 0.96082);
			break;
		case "T8_U1608_Mich":
			res = 0.0001 * Math.pow(DN_EC, 1.61568) * Math.pow(AT_EC, 1.11656);
			break;
		case "T8_U1610_Mich":
			res = 0.00005 * Math.pow(DN_EC, 1.88384) * Math.pow(AT_EC, 0.9914);
			break;
		case "T8_U2001_Oax":
			res = 0.00008 * Math.pow(DN_EC, 1.90189) * Math.pow(AT_EC, 0.90572);
			break;
		case "T8_U2003_Oax":
			res = 0.00006 * Math.pow(DN_EC, 2.06768) * Math.pow(AT_EC, 0.7882);
			break;
		case "T8_U2008_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.96333) * Math.pow(AT_EC, 0.9002);
			break;
		case "T8_U2009_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.95642) * Math.pow(AT_EC, 0.88418);
			break;
		case "T8_U2010_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.85217) * Math.pow(AT_EC, 1.0584);
			break;
		case "T8_U2012_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.9033) * Math.pow(AT_EC, 1.09949);
			break;
		case "T8_U2013_Oax":
			res = 0.00003 * Math.pow(DN_EC, 1.96175) * Math.pow(AT_EC, 1.08365);
			break;
		case "T8_U2101_Pue":
			res = 0.00008 * Math.pow(DN_EC, 1.84989) * Math.pow(AT_EC, 0.91831);
			break;
		case "T8_U2103_Pue":
			res = 0.00008 * Math.pow(DN_EC, 1.71457) * Math.pow(AT_EC, 1.10603);
			break;
		case "T8_U2105_Pue":
			res = 0.00001 * Math.pow(DN_EC, 1.94902) * Math.pow(AT_EC, 1.46857);
			break;
		case "T8_U2107_Pue":
			res = 0.00007 * Math.pow(DN_EC, 1.63676) * Math.pow(AT_EC, 1.19026);
			break;
		case "T8_U2108_Pue":
			res = 0.00011 * Math.pow(DN_EC, 1.75875) * Math.pow(AT_EC, 0.95559);
			break;
		case "T8_U2301_QRoo":
			res = 0.00015 * Math.pow(DN_EC, 1.32903) * Math.pow(AT_EC, 1.22874);
			break;
		case "T8_U2302_QRoo":
			res = 0.00019 * Math.pow(DN_EC, 1.60581) * Math.pow(AT_EC, 0.82702);
			break;
		case "T8_U2303_QRoo":
			res = 0.00012 * Math.pow(DN_EC, 2.08405) * Math.pow(AT_EC, 0.42681);
			break;
		case "T8_U2304_QRoo":
			res = 0.00012 * Math.pow(DN_EC, 1.77286) * Math.pow(AT_EC, 0.79622);
			break;
		case "T8_U2901_Tlax":
			res = 0.00008 * Math.pow(DN_EC, 2.06469) * Math.pow(AT_EC, 0.6856);
			break;
		case "T8_U2902_Tlax":
			res = 0.00007 * Math.pow(DN_EC, 1.76847) * Math.pow(AT_EC, 1.06584);
			break;
		case "T8_U3004_Ver":
			res = 0.00008 * Math.pow(DN_EC, 1.8751) * Math.pow(AT_EC, 0.82694);
			break;
		case "T8_U3012_Ver":
			res = 0.00007 * Math.pow(DN_EC, 1.67846) * Math.pow(AT_EC, 1.20297);
			break;
		case "T8_U3013_Ver":
			res = 0.00013 * Math.pow(DN_EC, 1.87829) * Math.pow(AT_EC, 0.67938);
			break;
		case "T8_U801_Chih":
			res = 0.00009 * Math.pow(DN_EC, 2.03824) * Math.pow(AT_EC, 0.49904);
			break;
		case "T8_U802_Chih":
			res = 0.00008 * Math.pow(DN_EC, 1.96171) * Math.pow(AT_EC, 0.72439);
			break;
		case "T8_U804_Chih":
			res = 0.00007 * Math.pow(DN_EC, 2.03941) * Math.pow(AT_EC, 0.79305);
			break;
		case "T8_U805_Chih":
			res = 0.00004 * Math.pow(DN_EC, 1.85388) * Math.pow(AT_EC, 1.08173);
			break;
		case "T8_U806_Chih":
			res = 0.00005 * Math.pow(DN_EC, 2.05768) * Math.pow(AT_EC, 0.87192);
			break;
		case "T8_U807_Chih":
			res = 0.00003 * Math.pow(DN_EC, 1.907) * Math.pow(AT_EC, 1.14228);
			break;
		case "T8_U808_Chih":
			res = 0.00006 * Math.pow(DN_EC, 2.25209) * Math.pow(AT_EC, 0.4497);
			break;
		case "T8_U809_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.91096) * Math.pow(AT_EC, 0.91004);
			break;
		case "T8_U810_Chih":
			res = 0.00003 * Math.pow(DN_EC, 2.13667) * Math.pow(AT_EC, 0.84587);
			break;
		case "T8_U811_Chih":
			res = 0.00005 * Math.pow(DN_EC, 1.9274) * Math.pow(AT_EC, 0.94972);
			break;
		case "T8z4_Mex":
			res = Math.exp(-1.205429 + 1.795256 * Math.log(DN_EC / 100) + 1.027285 * Math.log(AT_EC));
			break;
		case "T8z5_CF_Gro":
			res = 0.45503 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "T9_Chis":
			res = Math.exp(-9.73746695 + 1.85643537 * Math.log(DN_EC) + 1.07354086 * Math.log(AT_EC));
			break;
		case "T9_QR":
			res = Math.exp(-9.45811109 + 1.82568462 * Math.log(DN_EC) + 1.00281859 * Math.log(AT_EC));
			break;
		case "T9_U1001_Dgo":
			res = 0.00008 * Math.pow(DN_EC, 1.87931) * Math.pow(AT_EC, 0.89222);
			break;
		case "T9_U1002_Dgo":
			res = 0.00004 * Math.pow(DN_EC, 2.1152) * Math.pow(AT_EC, 0.83967);
			break;
		case "T9_U1003_Dgo":
			res = 0.00007 * Math.pow(DN_EC, 1.89463) * Math.pow(AT_EC, 0.93274);
			break;
		case "T9_U1004_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 2.05677) * Math.pow(AT_EC, 0.82855);
			break;
		case "T9_U1006_Dgo":
			res = 0.00005 * Math.pow(DN_EC, 2.16341) * Math.pow(AT_EC, 0.7593);
			break;
		case "T9_U1008_Dgo":
			res = 0.0001 * Math.pow(DN_EC, 1.65948) * Math.pow(AT_EC, 1.11021);
			break;
		case "T9_U1009_Dgo":
			res = 0.00006 * Math.pow(DN_EC, 1.96532) * Math.pow(AT_EC, 0.91818);
			break;
		case "T9_U1010_Dgo":
			res = 0.00014 * Math.pow(DN_EC, 1.57408) * Math.pow(AT_EC, 1.11268);
			break;
		case "T9_U1011_Dgo":
			res = 0.00008 * Math.pow(DN_EC, 1.87607) * Math.pow(AT_EC, 0.95211);
			break;
		case "T9_U1201_Gro":
			res = 0.00009 * Math.pow(DN_EC, 1.92431) * Math.pow(AT_EC, 0.78212);
			break;
		case "T9_U1203_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.70308) * Math.pow(AT_EC, 1.14845);
			break;
		case "T9_U1204_Gro":
			res = 0.00007 * Math.pow(DN_EC, 1.66889) * Math.pow(AT_EC, 1.14331);
			break;
		case "T9_U1205_Gro":
			res = 0.00006 * Math.pow(DN_EC, 1.77253) * Math.pow(AT_EC, 1.07939);
			break;
		case "T9_U1302_Hgo":
			res = 0.00009 * Math.pow(DN_EC, 1.83765) * Math.pow(AT_EC, 0.77801);
			break;
		case "T9_U1303_Hgo":
			res = 0.00014 * Math.pow(DN_EC, 1.61561) * Math.pow(AT_EC, 0.93755);
			break;
		case "T9_U1401_Jal":
			res = 0.00008 * Math.pow(DN_EC, 1.97638) * Math.pow(AT_EC, 0.76149);
			break;
		case "T9_U1404_Jal":
			res = 0.00007 * Math.pow(DN_EC, 1.86893) * Math.pow(AT_EC, 0.91262);
			break;
		case "T9_U1405_Jal":
			res = 0.00006 * Math.pow(DN_EC, 1.83039) * Math.pow(AT_EC, 1.01842);
			break;
		case "T9_U1406_Jal":
			res = 0.00005 * Math.pow(DN_EC, 1.98017) * Math.pow(AT_EC, 0.92907);
			break;
		case "T9_U1407_Jal":
			res = 0.00002 * Math.pow(DN_EC, 2.12593) * Math.pow(AT_EC, 1.05987);
			break;
		case "T9_U1408_Jal":
			res = 0.00002 * Math.pow(DN_EC, 1.96243) * Math.pow(AT_EC, 1.24572);
			break;
		case "T9_U1410_Jal":
			res = 0.00005 * Math.pow(DN_EC, 2.01887) * Math.pow(AT_EC, 0.91994);
			break;
		case "T9_U1505_Mex":
			res = 0.00007 * Math.pow(DN_EC, 1.86648) * Math.pow(AT_EC, 0.91409);
			break;
		case "T9_U1507_Mex":
			res = 0.00005 * Math.pow(DN_EC, 2.10051) * Math.pow(AT_EC, 0.80641);
			break;
		case "T9_U1508_Mex":
			res = 0.00005 * Math.pow(DN_EC, 2.09745) * Math.pow(AT_EC, 0.71315);
			break;
		case "T9_U1509_Mex":
			res = 0.00014 * Math.pow(DN_EC, 1.81149) * Math.pow(AT_EC, 0.68374);
			break;
		case "T9_U1510_Mex":
			res = 0.00006 * Math.pow(DN_EC, 1.86169) * Math.pow(AT_EC, 0.92411);
			break;
		case "T9_U1603_Mich":
			res = 0.00007 * Math.pow(DN_EC, 1.42549) * Math.pow(AT_EC, 1.44157);
			break;
		case "T9_U1604_Mich":
			res = 0.00002 * Math.pow(DN_EC, 2.00811) * Math.pow(AT_EC, 1.14932);
			break;
		case "T9_U1605_Mich":
			res = 0.00003 * Math.pow(DN_EC, 1.99993) * Math.pow(AT_EC, 1.03771);
			break;
		case "T9_U1607_Mich":
			res = 0.0001 * Math.pow(DN_EC, 1.79239) * Math.pow(AT_EC, 0.87635);
			break;
		case "T9_U1610_Mich":
			res = 0.00004 * Math.pow(DN_EC, 1.81443) * Math.pow(AT_EC, 1.19608);
			break;
		case "T9_U2001_Oax":
			res = 0.00005 * Math.pow(DN_EC, 1.7569) * Math.pow(AT_EC, 1.16074);
			break;
		case "T9_U2003_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.80658) * Math.pow(AT_EC, 1.0885);
			break;
		case "T9_U2008_Oax":
			res = 0.00007 * Math.pow(DN_EC, 2.13865) * Math.pow(AT_EC, 0.633);
			break;
		case "T9_U2009_Oax":
			res = 0.00006 * Math.pow(DN_EC, 1.96511) * Math.pow(AT_EC, 0.86099);
			break;
		case "T9_U2010_Oax":
			res = 0.00003 * Math.pow(DN_EC, 1.8398) * Math.pow(AT_EC, 1.25374);
			break;
		case "T9_U2012_Oax":
			res = 0.00004 * Math.pow(DN_EC, 2.15717) * Math.pow(AT_EC, 0.80596);
			break;
		case "T9_U2013_Oax":
			res = 0.00004 * Math.pow(DN_EC, 1.99493) * Math.pow(AT_EC, 0.9913);
			break;
		case "T9_U2101_Pue":
			res = 0.00009 * Math.pow(DN_EC, 1.46801) * Math.pow(AT_EC, 1.27569);
			break;
		case "T9_U2105_Pue":
			res = 0.00006 * Math.pow(DN_EC, 1.69493) * Math.pow(AT_EC, 1.13305);
			break;
		case "T9_U2107_Pue":
			res = 0.00009 * Math.pow(DN_EC, 1.84484) * Math.pow(AT_EC, 0.84503);
			break;
		case "T9_U2108_Pue":
			res = 0.00008 * Math.pow(DN_EC, 1.84824) * Math.pow(AT_EC, 0.95479);
			break;
		case "T9_U2301_QRoo":
			res = 0.0001 * Math.pow(DN_EC, 1.51244) * Math.pow(AT_EC, 1.21242);
			break;
		case "T9_U2302_QRoo":
			res = 0.00006 * Math.pow(DN_EC, 1.81766) * Math.pow(AT_EC, 1.02093);
			break;
		case "T9_U2303_QRoo":
			res = 0.00005 * Math.pow(DN_EC, 2.12584) * Math.pow(AT_EC, 0.65387);
			break;
		case "T9_U2304_QRoo":
			res = 0.00006 * Math.pow(DN_EC, 1.99569) * Math.pow(AT_EC, 0.68664);
			break;
		case "T9_U2901_Tlax":
			res = 0.00011 * Math.pow(DN_EC, 1.74687) * Math.pow(AT_EC, 0.9233);
			break;
		case "T9_U2902_Tlax":
			res = 0.00006 * Math.pow(DN_EC, 1.89549) * Math.pow(AT_EC, 0.96322);
			break;
		case "T9_U3012_Ver":
			res = 0.00008 * Math.pow(DN_EC, 1.96545) * Math.pow(AT_EC, 0.76485);
			break;
		case "T9_U3013_Ver":
			res = 0.00004 * Math.pow(DN_EC, 1.94121) * Math.pow(AT_EC, 0.94878);
			break;
		case "T9_U801_Chih":
			res = 0.00007 * Math.pow(DN_EC, 1.8919) * Math.pow(AT_EC, 0.88634);
			break;
		case "T9_U802_Chih":
			res = 0.00008 * Math.pow(DN_EC, 1.81179) * Math.pow(AT_EC, 0.96734);
			break;
		case "T9_U804_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.97959) * Math.pow(AT_EC, 0.91082);
			break;
		case "T9_U806_Chih":
			res = 0.00005 * Math.pow(DN_EC, 1.91697) * Math.pow(AT_EC, 1.02231);
			break;
		case "T9_U807_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.88895) * Math.pow(AT_EC, 0.89891);
			break;
		case "T9_U808_Chih":
			res = 0.00059 * Math.pow(DN_EC, 1.33888) * Math.pow(AT_EC, 0.70707);
			break;
		case "T9_U810_Chih":
			res = 0.0001 * Math.pow(DN_EC, 1.89271) * Math.pow(AT_EC, 0.69004);
			break;
		case "T9_U811_Chih":
			res = 0.00006 * Math.pow(DN_EC, 1.91145) * Math.pow(AT_EC, 0.90831);
			break;
		case "T9z4_Mex":
			res = Math.exp(-0.179960 + 1.962243 * Math.log(DN_EC / 100) + 0.626109 * Math.log(AT_EC));
			break;
		case "T9z5_Gro":
			res = 0.00791 + 0.06429 * AT_EC * DN_EC / 100 + 0.26527 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "TA_Hgo":
			res = Math.exp(-9.6214586 + 1.86021863 * Math.log(DN_EC) + 0.9605345 * Math.log(AT_EC));
			break;
		case "TA_NL":
			res = Math.exp(-9.4786656 + 1.77975727 * Math.log(DN_EC) + 1.07182675 * Math.log(AT_EC));
			break;
		case "TA_Qro":
			res = Math.exp(-9.61737291 + 1.84298897 * Math.log(DN_EC) + 1.0217753 * Math.log(AT_EC));
			break;
		case "TA_Tam":
			res = Math.exp(-9.7096827 + 1.89157585 * Math.log(DN_EC) + 1.04481742 * Math.log(AT_EC));
			break;
		case "TA1_Oax":
			res = Math.exp(-9.99696596 + 1.91126433 * Math.log(DN_EC) + 1.05268932 * Math.log(AT_EC));
			break;
		case "TA1z3_Chis":
			res = Math.exp(-9.71600772 + 1.85902896 * Math.log(DN_EC) + 0.97320372 * Math.log(AT_EC));
			break;
		case "TA2_Oax":
			res = Math.exp(-9.68821955 + 1.81948603 * Math.log(DN_EC) + 1.05886031 * Math.log(AT_EC));
			break;
		case "TA2z2_Chis":
			res = Math.exp(-9.78252427 + 1.86824763 * Math.log(DN_EC) + 1.03889813 * Math.log(AT_EC));
			break;
		case "TAz2_SLP":
			res = Math.exp(-9.65632612 + 1.79105716 * Math.log(DN_EC) + 1.10660162 * Math.log(AT_EC));
			break;
		case "TAz2y3_Pue":
			res = Math.exp(-9.73084158 + 1.86001307 * Math.log(DN_EC) + 0.98860113 * Math.log(AT_EC));
			break;
		case "TB_Coah":
			res = Math.exp(-9.8732337 + 1.85512732 * Math.log(DN_EC) + 1.11426549 * Math.log(AT_EC));
			break;
		case "TB_Hgo":
			res = Math.exp(-9.55313303 + 1.86224116 * Math.log(DN_EC) + 0.96618469 * Math.log(AT_EC));
			break;
		case "TB_NL":
			res = Math.exp(-8.72641434 + 1.43032994 * Math.log(DN_EC) + 1.19541675 * Math.log(AT_EC));
			break;
		case "TB_Oax":
			res = Math.exp(-7.14699440 + 1.12704568 * Math.log(DN_EC) + 1.07202239 * Math.log(AT_EC));
			break;
		case "TB_Qro":
			res = Math.exp(-9.75972413 + 1.87784315 * Math.log(DN_EC) + 1.04315416 * Math.log(AT_EC));
			break;
		case "TB_Tam":
			res = Math.exp(-9.81803031 + 1.96214343 * Math.log(DN_EC) + 0.97915381 * Math.log(AT_EC));
			break;
		case "TB1_Oax":
			res = Math.exp(-9.75644044 + 1.82088904 * Math.log(DN_EC) + 1.03604126 * Math.log(AT_EC));
			break;
		case "TB1z2y3_Chis":
			res = Math.exp(-9.25574404 + 1.68436063 * Math.log(DN_EC) + 1.06130723 * Math.log(AT_EC));
			break;
		case "TB2_Oax":
			res = Math.exp(-10.34881812 + 2.02143823 * Math.log(DN_EC) + 1.03958842 * Math.log(AT_EC));
			break;
		case "TB2z2y3_Chis":
			res = Math.exp(-9.58801453 + 1.75592913 * Math.log(DN_EC) + 1.07004135 * Math.log(AT_EC));
			break;
		case "TB3_Oax":
			res = Math.exp(-9.77006304 + 1.87882617 * Math.log(DN_EC) + 1.02464593 * Math.log(AT_EC));
			break;
		case "TB3z2_Chis":
			res = Math.exp(-9.79893158 + 1.87545256 * Math.log(DN_EC) + 1.00591418 * Math.log(AT_EC));
			break;
		case "TB4_Oax":
			res = Math.exp(-9.79021441 + 1.85732519 * Math.log(DN_EC) + 1.07570774 * Math.log(AT_EC));
			break;
		case "TB5_Oax":
			res = Math.exp(-9.09950564 + 1.57672586 * Math.log(DN_EC) + 1.17621222 * Math.log(AT_EC));
			break;
		case "TB6_Oax":
			res = Math.exp(-7.86843737 + 1.14373248 * Math.log(DN_EC) + 1.26021484 * Math.log(AT_EC));
			break;
		case "TB7_Oax":
			res = Math.exp(-9.89882648 + 1.9245184 * Math.log(DN_EC) + 1.00776177 * Math.log(AT_EC));
			break;
		case "TBz2_SLP":
			res = Math.exp(-9.59030142 + 1.81853306 * Math.log(DN_EC) + 1.04785678 * Math.log(AT_EC));
			break;
		case "TBz2y3_Pue":
			res = Math.exp(-9.63495649 + 1.86670523 * Math.log(DN_EC) + 0.99551381 * Math.log(AT_EC));
			break;
		case "TC_Coah":
			res = Math.exp(-9.98378974 + 1.88083718 * Math.log(DN_EC) + 1.12572772 * Math.log(AT_EC));
			break;
		case "TC_Gto":
			res = Math.exp(-9.56168726 + 1.83727218 * Math.log(DN_EC) + 1.03577031 * Math.log(AT_EC));
			break;
		case "TC_Hgo":
			res = Math.exp(-9.91251786 + 1.92329567 * Math.log(DN_EC) + 1.08023764 * Math.log(AT_EC));
			break;
		case "TC_NL":
			res = Math.exp(-9.55262477 + 1.79669337 * Math.log(DN_EC) + 1.12955055 * Math.log(AT_EC));
			break;
		case "TC_Qro":
			res = Math.exp(-9.40012627 + 1.78375769 * Math.log(DN_EC) + 1.04316093 * Math.log(AT_EC));
			break;
		case "TC_Tam":
			res = Math.exp(-9.43439983 + 1.80881432 * Math.log(DN_EC) + 1.05080385 * Math.log(AT_EC));
			break;
		case "TCz2_SLP":
			res = Math.exp(-9.4484666 + 1.77072475 * Math.log(DN_EC) + 1.03638608 * Math.log(AT_EC));
			break;
		case "TD_Coah":
			res = Math.exp(-9.87472826 + 1.87766305 * Math.log(DN_EC) + 1.00933229 * Math.log(AT_EC));
			break;
		case "TD_Gto":
			res = Math.exp(-9.70894854 + 1.90070355 * Math.log(DN_EC) + 0.96499386 * Math.log(AT_EC));
			break;
		case "TD_NL":
			res = Math.exp(-9.8207876 + 1.89180185 * Math.log(DN_EC) + 1.08048365 * Math.log(AT_EC));
			break;
		case "TD_Qro":
			res = Math.exp(-9.13281811 + 1.58527271 * Math.log(DN_EC) + 1.08374249 * Math.log(AT_EC));
			break;
		case "TDz2_SLP":
			res = Math.exp(-8.96131139 + 1.7549128 * Math.log(DN_EC) + 0.82744403 * Math.log(AT_EC));
			break;
		case "TE_Hgo":
			res = Math.exp(-9.54274357 + 1.81010631 * Math.log(DN_EC) + 1.05764337 * Math.log(AT_EC));
			break;
		case "TE_NL":
			res = Math.exp(-9.48686252 + 1.82408096 * Math.log(DN_EC) + 0.96892639 * Math.log(AT_EC));
			break;
		case "TE_Qro":
			res = Math.exp(-9.58311796 + 1.86739198 * Math.log(DN_EC) + 0.98501687 * Math.log(AT_EC));
			break;
		case "TG_Gto":
			res = Math.exp(-9.45552671 + 1.83036294 * Math.log(DN_EC) + 0.97662425 * Math.log(AT_EC));
			break;
		case "TG_Oax":
			res = Math.exp(-9.41218007 + 1.70376160 * Math.log(DN_EC) + 1.09456111 * Math.log(AT_EC));
			break;
		case "TG_Qro":
			res = Math.exp(-9.57166007 + 1.91664005 * Math.log(DN_EC) + 0.95219556 * Math.log(AT_EC));
			break;
		case "TG_Tab":
			res = Math.exp(-9.33289255 + 1.6966435 * Math.log(DN_EC) + 1.0938266 * Math.log(AT_EC));
			break;
		case "TGz1_Pue":
			res = Math.exp(-10.13480453 + 1.96960849 * Math.log(DN_EC) + 1.01643160 * Math.log(AT_EC));
			break;
		case "TGz1y2_Hgo":
			res = Math.exp(-9.65237643 + 1.86211603 * Math.log(DN_EC) + 0.99010357 * Math.log(AT_EC));
			break;
		case "TGz2_Chis":
			res = Math.exp(-9.75761673 + 1.91856550 * Math.log(DN_EC) + 0.94385612 * Math.log(AT_EC));
			break;
		case "TGz2_SLP":
			res = Math.exp(-9.77269977 + 1.9000311 * Math.log(DN_EC) + 1.00274161 * Math.log(AT_EC));
			break;
		case "TGz2y3_Pue":
			res = Math.exp(-9.69246238 + 1.92883177 * Math.log(DN_EC) + 0.90538711 * Math.log(AT_EC));
			break;
		case "TGz3_Hgo":
			res = Math.exp(-9.64826886 + 1.88146054 * Math.log(DN_EC) + 0.95398007 * Math.log(AT_EC));
			break;
		case "TH_Qro":
			res = Math.exp(-9.86428069 + 1.76684334 * Math.log(DN_EC) + 1.30487505 * Math.log(AT_EC));
			break;
		case "THz2y3_Pue":
			res = Math.exp(-9.5017845 + 1.82547723 * Math.log(DN_EC) + 0.98416091 * Math.log(AT_EC));
			break;
		case "TI_Cam":
			res = Math.exp(-9.64583328 + 1.79389367 * Math.log(DN_EC) + 1.03915044 * Math.log(AT_EC));
			break;
		case "TI_Hgo":
			res = Math.exp(-9.8437685 + 1.93425311 * Math.log(DN_EC) + 0.96703607 * Math.log(AT_EC));
			break;
		case "TI_Tab":
			res = Math.exp(-9.19540026 + 1.64562367 * Math.log(DN_EC) + 1.07521209 * Math.log(AT_EC));
			break;
		case "TI_Ver":
			res = Math.exp(-10.05426786 + 2.02274846 * Math.log(DN_EC) + 1.0027556 * Math.log(AT_EC));
			break;
		case "TII_Cam":
			res = Math.exp(-10.06001321 + 1.98160359 * Math.log(DN_EC) + 1.03695598 * Math.log(AT_EC));
			break;
		case "Tii_Hgo":
			res = Math.exp(-9.39858604 + 1.79050244 * Math.log(DN_EC) + 0.94980089 * Math.log(AT_EC));
			break;
		case "TII_Tab":
			res = Math.exp(-9.91798848 + 1.95127527 * Math.log(DN_EC) + 1.03165275 * Math.log(AT_EC));
			break;
		case "TII_Ver":
			res = Math.exp(-10.02137029 + 1.9539179 * Math.log(DN_EC) + 1.04262341 * Math.log(AT_EC));
			break;
		case "TIII_Cam":
			res = Math.exp(-9.53415154 + 1.85980581 * Math.log(DN_EC) + 0.96989346 * Math.log(AT_EC));
			break;
		case "TIII_Tab":
			res = Math.exp(-10.22563374 + 1.92362277 * Math.log(DN_EC) + 1.14061993 * Math.log(AT_EC));
			break;
		case "TIIz1_Pue":
			res = Math.exp(-8.59435094 + 1.58378134 * Math.log(DN_EC) + 0.984262 * Math.log(AT_EC));
			break;
		case "TIV_Cam":
			res = Math.exp(-9.84923104 + 1.91175328 * Math.log(DN_EC) + 1.04555238 * Math.log(AT_EC));
			break;
		case "TIV_Oax":
			res = Math.exp(-10.71439546 + 1.97139127 * Math.log(DN_EC) + 1.06409203 * Math.log(AT_EC));
			break;
		case "TIV_Tab":
			res = Math.exp(-9.82447804 + 1.93162616 * Math.log(DN_EC) + 1.01919725 * Math.log(AT_EC));
			break;
		case "TIV_Ver":
			res = Math.exp(-10.25777326 + 1.98588189 * Math.log(DN_EC) + 1.1159178 * Math.log(AT_EC));
			break;
		case "TIX_Cam":
			res = Math.exp(-9.60981068 + 1.8285472 * Math.log(DN_EC) + 1.01082458 * Math.log(AT_EC));
			break;
		case "TIX_Tab":
			res = Math.exp(-9.80750322 + 1.87831474 * Math.log(DN_EC) + 1.07425292 * Math.log(AT_EC));
			break;
		case "TIX-S_Pue":
			res = Math.exp(-9.57488735 + 1.80188465 * Math.log(DN_EC) + 1.01577894 * Math.log(AT_EC));
			break;
		case "TIz2_Chis":
			res = Math.exp(-9.63738842 + 1.85037732 * Math.log(DN_EC) + 0.98847468 * Math.log(AT_EC));
			break;
		case "TIz2_SLP":
			res = Math.exp(-9.93459231 + 1.788694411 * Math.log(DN_EC) + 1.18166191 * Math.log(AT_EC));
			break;
		case "TPino_BC":
			res = 0.01 + 0.388 * Math.pow(DN_EC / 100, 2) * AT_EC - 1.369 * Math.pow(DN_EC / 100, 3);
			break;
		case "TPino_Chih":
			res = -0.00018 - 3.39 * Math.pow(DN_EC / 100, 2) + 4.48 * Math.pow(DN_EC / 100, 3)
					+ 0.09 * AT_EC * DN_EC / 100 + 0.33 * Math.pow(DN_EC / 100, 2) * AT_EC;
			break;
		case "TPino_Dgo":
			res = 0.38 + 5.8 * Math.pow(DN_EC / 100, 2) + 0.018 * AT_EC + 0.21 * Math.pow(DN_EC / 100, 2) * AT_EC;
			break;
		case "TPino_Jal":
			res = 0.02289 + 0.39172 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "TPino_Sin":
			res = 0.0187 + 0.699 * Math.pow(DN_EC / 100, 2) + 0.385 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "TPino_Zac":
			res = 0.0036 - 0.49 * Math.pow(DN_EC / 100, 2) + 0.481 * AT_EC * Math.pow(DN_EC / 100, 2);
			break;
		case "TPinoNay":
			res = 0.054 + 0.5 * Math.pow(DN_EC / 100, 2) * AT_EC - 1.35 * Math.pow(DN_EC / 100, 3)
					- 0.136 * Math.pow(DN_EC / 100, 2);
			break;
		case "TQue_Jal":
			res = -0.06016 + 0.60757 * DN_EC / 100 + 0.00031 * AT_EC + 0.33129 * AT_EC * Math.pow(DN_EC / 100, 2)
					- 0.03087 * AT_EC * Math.pow(DN_EC / 100, 3);
			break;
		case "TQue_Nay":
			res = 0.008 + 0.326 * Math.pow(DN_EC / 100, 2) * AT_EC + 1.439 * Math.pow(DN_EC / 100, 2);
			break;
		case "TV_Cam":
			res = Math.exp(-9.88284891 + 1.92178549 * Math.log(DN_EC) + 1.04714889 * Math.log(AT_EC));
			break;
		case "TV_Oax":
			res = Math.exp(-10.06787497 + 2.0005528 * Math.log(DN_EC) + 0.99031834 * Math.log(AT_EC));
			break;
		case "TV_Tab":
			res = Math.exp(-10.02137029 + 1.95391791 * Math.log(DN_EC) + 1.04262341 * Math.log(AT_EC));
			break;
		case "TVI_Cam":
			res = Math.exp(-10.09141259 + 1.93246219 * Math.log(DN_EC) + 1.06194865 * Math.log(AT_EC));
			break;
		case "TVI_Oax":
			res = Math.exp(-10.07005937 + 2.00090768 * Math.log(DN_EC) + 0.98373378 * Math.log(AT_EC));
			break;
		case "TVI_Tab":
			res = Math.exp(-9.98279857 + 1.97250941 * Math.log(DN_EC) + 1.03206162 * Math.log(AT_EC));
			break;
		case "TVI_Ver":
			res = Math.exp(-10.04355155 + 1.98934236 * Math.log(DN_EC) + 1.01544747 * Math.log(AT_EC));
			break;
		case "TVII_Cam":
			res = Math.exp(-9.98357915 + 1.95005045 * Math.log(DN_EC) + 1.05153755 * Math.log(AT_EC));
			break;
		case "TVII_Tab":
			res = Math.exp(-9.60249233 + 1.83229453 * Math.log(DN_EC) + 1.07410993 * Math.log(AT_EC));
			break;
		case "TVII_Ver":
			res = Math.exp(-9.72017711 + 1.9079834 * Math.log(DN_EC) + 0.98125131 * Math.log(AT_EC));
			break;
		case "TVIII_Cam":
			res = Math.exp(-8.81312542 + 1.56449274 * Math.log(DN_EC) + 1.08361129 * Math.log(AT_EC));
			break;
		case "TVIII_Tab":
			res = Math.exp(-9.75226675 + 1.94677823 * Math.log(DN_EC) + 1.00656774 * Math.log(AT_EC));
			break;
		case "TVIII_Ver":
			res = Math.exp(-10.07263436 + 1.96992797 * Math.log(DN_EC) + 1.01423627 * Math.log(AT_EC));
			break;
		case "Tvol1_QR":
			res = Math.exp(-9.74143096 + 1.95019123 * Math.log(DN_EC) + 0.95525758 * Math.log(AT_EC));
			break;
		case "Tvol10_QR":
			res = Math.exp(-9.71122198 + 1.8428147 * Math.log(DN_EC) + 1.05795417 * Math.log(AT_EC));
			break;
		case "Tvol11_QR":
			res = Math.exp(-9.88332368 + 1.9131735 * Math.log(DN_EC) + 1.07391062 * Math.log(AT_EC));
			break;
		case "TX_Cam":
			res = Math.exp(-9.5643815 + 1.82330416 * Math.log(DN_EC) + 1.01741981 * Math.log(AT_EC));
			break;
		case "TX_Hgo":
			res = Math.exp(-9.78340833 + 1.83372702 * Math.log(DN_EC) + 1.07704058 * Math.log(AT_EC));
			break;
		case "TX_Tab":
			res = Math.exp(-9.83318819 + 1.91517713 * Math.log(DN_EC) + 1.03835777 * Math.log(AT_EC));
			break;
		case "TXI_Cam":
			res = Math.exp(-9.52774573 + 1.76329569 * Math.log(DN_EC) + 1.08168791 * Math.log(AT_EC));
			break;
		case "TXI_Tab":
			res = Math.exp(-9.96832495 + 1.89779841 * Math.log(DN_EC) + 1.08188905 * Math.log(AT_EC));
			break;
		case "TXII_cam":
			res = Math.exp(-9.83322527 + 1.92412457 * Math.log(DN_EC) + 1.00970142 * Math.log(AT_EC));
			break;
		case "TXII_Tab":
			res = Math.exp(-9.20446857 + 1.70136976 * Math.log(DN_EC) + 1.07521396 * Math.log(AT_EC));
			break;
		case "TXII_Ver":
			res = Math.exp(-9.83318819 + 1.91517715 * Math.log(DN_EC) + 1.0383577 * Math.log(AT_EC));
			break;
		case "TXIII_Cam":
			res = Math.exp(-9.41737421 + 1.76385327 * Math.log(DN_EC) + 1.04067089 * Math.log(AT_EC));
			break;
		case "TXIII_Tab":
			res = Math.exp(-9.76784179 + 1.87539164 * Math.log(DN_EC) + 1.05141081 * Math.log(AT_EC));
			break;
		case "TXIV_Oax":
			res = Math.exp(-9.98262558 + 1.94239763 * Math.log(DN_EC) + 1.02228707 * Math.log(AT_EC));
			break;
		case "TXV_Ver":
			res = Math.exp(-9.85922486 + 1.93825951 * Math.log(DN_EC) + 1.01557735 * Math.log(AT_EC));
			break;
		case "TXVI_Ver":
			res = Math.exp(-9.7891527 + 1.88887745 * Math.log(DN_EC) + 1.04457398 * Math.log(AT_EC));
			break;
		case "TXVIz1_Pue":
			res = Math.exp(-9.40152632 + 1.71919595 * Math.log(DN_EC) + 1.07447205 * Math.log(AT_EC));
			break;
		default:
			res = 0.00000000000000000000000000000000000000000000000000000000000000000000000000000000009;
			break;
		}
		return res;
	}

	public String getEcuacion(String CVE_ECUACION) {
		ecuacion = "0";
		switch (CVE_ECUACION) {
		case "Coef_Morf_03":
			ecuacion = " 0.7854 * POTENCIA(DN_EC / 100; 2) * AT_EC * 0.3";
			break;
		case "Oyamelz2y3_Ver":
			ecuacion = " EXP(-9.64447683 + 1.90134225 * LN(DN_EC) + 1.03162716 * LN(AT_EC))";
			break;
		case "Pinoz2y3_Ver":
			ecuacion = " EXP(-9.29401031 + 2.07031121 * LN(DN_EC) + 0.68219841 * LN(AT_EC))";
			break;
		case "T_Chih_1":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.86212) * POTENCIA(AT_EC; 0.99213)";
			break;
		case "T_Chih_10":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.981) * POTENCIA(AT_EC; 0.50526)";
			break;
		case "T_Chih_11":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.8743) * POTENCIA(AT_EC; 1.01781)";
			break;
		case "T_Chih_2":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.95737) * POTENCIA(AT_EC; 1.01871)";
			break;
		case "T_Chih_3":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.67728) * POTENCIA(AT_EC; 1.24808)";
			break;
		case "T_Chih_4":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.95933) * POTENCIA(AT_EC; 1.00994)";
			break;
		case "T_Chih_5":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.65927) * POTENCIA(AT_EC; 1.10781)";
			break;
		case "T_Chih_6":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 2.11183) * POTENCIA(AT_EC; 0.58883)";
			break;
		case "T_Chih_7":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.98476) * POTENCIA(AT_EC; 0.98928)";
			break;
		case "T_Chih_8":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.25209) * POTENCIA(AT_EC; 0.4497)";
			break;
		case "T_Chih_9":
			ecuacion = " 0.00063 * POTENCIA(DN_EC; 1.32235) * POTENCIA(AT_EC; 0.7039)";
			break;
		case "T_Dgo_1":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.93438) * POTENCIA(AT_EC; 0.98688)";
			break;
		case "T_Dgo_10":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.8562) * POTENCIA(AT_EC; 1.06672)";
			break;
		case "T_Dgo_11":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.1406) * POTENCIA(AT_EC; 0.98974)";
			break;
		case "T_Dgo_12":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.98268) * POTENCIA(AT_EC; 0.97952)";
			break;
		case "T_Dgo_13":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.57309) * POTENCIA(AT_EC; 1.11458)";
			break;
		case "T_Dgo_14":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.90009) * POTENCIA(AT_EC; 0.98458)";
			break;
		case "T_Dgo_15":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.02305) * POTENCIA(AT_EC; 0.88588)";
			break;
		case "T_Dgo_16":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.03023) * POTENCIA(AT_EC; 0.98222)";
			break;
		case "T_Dgo_17":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.86961) * POTENCIA(AT_EC; 0.97764)";
			break;
		case "T_Dgo_18":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.7906) * POTENCIA(AT_EC; 1.11575)";
			break;
		case "T_Dgo_19":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.73417) * POTENCIA(AT_EC; 1.27799)";
			break;
		case "T_Dgo_2":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.97044) * POTENCIA(AT_EC; 0.91647)";
			break;
		case "T_Dgo_20":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.97485) * POTENCIA(AT_EC; 0.92877)";
			break;
		case "T_Dgo_21":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.96472) * POTENCIA(AT_EC; 1.10172)";
			break;
		case "T_Dgo_22":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.98158) * POTENCIA(AT_EC; 0.88795)";
			break;
		case "T_Dgo_23":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.97289) * POTENCIA(AT_EC; 0.83957)";
			break;
		case "T_Dgo_24":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.818) * POTENCIA(AT_EC; 0.82401)";
			break;
		case "T_Dgo_25":
			ecuacion = " 0.00024 * POTENCIA(DN_EC; 1.58917) * POTENCIA(AT_EC; 0.77994)";
			break;
		case "T_Dgo_27":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.87607) * POTENCIA(AT_EC; 0.95211)";
			break;
		case "T_Dgo_28":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.98226) * POTENCIA(AT_EC; 0.84224)";
			break;
		case "T_Dgo_3":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.98909) * POTENCIA(AT_EC; 0.90204)";
			break;
		case "T_Dgo_4":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.96882) * POTENCIA(AT_EC; 0.8985)";
			break;
		case "T_Dgo_5":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.91236) * POTENCIA(AT_EC; 0.93298)";
			break;
		case "T_Dgo_6":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.93753) * POTENCIA(AT_EC; 0.95704)";
			break;
		case "T_Dgo_7":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.89329) * POTENCIA(AT_EC; 0.86226)";
			break;
		case "T_Dgo_8":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.91367) * POTENCIA(AT_EC; 0.89469)";
			break;
		case "T_Dgo_9":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.89932) * POTENCIA(AT_EC; 1.00996)";
			break;
		case "T_Jal_1":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.02355) * POTENCIA(AT_EC; 0.89358)";
			break;
		case "T_Jal_10":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.068) * POTENCIA(AT_EC; 0.83963)";
			break;
		case "T_Jal_11":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.83084) * POTENCIA(AT_EC; 0.94669)";
			break;
		case "T_Jal_12":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.02788) * POTENCIA(AT_EC; 0.78274)";
			break;
		case "T_Jal_13":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.10799) * POTENCIA(AT_EC; 0.70889)";
			break;
		case "T_Jal_14":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.08151) * POTENCIA(AT_EC; 0.78693)";
			break;
		case "T_Jal_15":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.9704) * POTENCIA(AT_EC; 0.97487)";
			break;
		case "T_Jal_16":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.1692) * POTENCIA(AT_EC; 0.78086)";
			break;
		case "T_Jal_17":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.99451) * POTENCIA(AT_EC; 0.8596)";
			break;
		case "T_Jal_18":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.04581) * POTENCIA(AT_EC; 0.84152)";
			break;
		case "T_Jal_19":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.05695) * POTENCIA(AT_EC; 0.67161)";
			break;
		case "T_Jal_2":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.07732) * POTENCIA(AT_EC; 0.87754)";
			break;
		case "T_Jal_3":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.95382) * POTENCIA(AT_EC; 1.01814)";
			break;
		case "T_Jal_4":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.09697) * POTENCIA(AT_EC; 0.91094)";
			break;
		case "T_Jal_5":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.13047) * POTENCIA(AT_EC; 0.64784)";
			break;
		case "T_Jal_6":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.04734) * POTENCIA(AT_EC; 0.94933)";
			break;
		case "T_Jal_7":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.01679) * POTENCIA(AT_EC; 1.00059)";
			break;
		case "T_Jal_8":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.03386) * POTENCIA(AT_EC; 0.83557)";
			break;
		case "T_Jal_9":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.05692) * POTENCIA(AT_EC; 0.92422)";
			break;
		case "T_Oax_1":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.99373) * POTENCIA(AT_EC; 0.84739)";
			break;
		case "T_Oax_10":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.88959) * POTENCIA(AT_EC; 0.96425)";
			break;
		case "T_Oax_11":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.9735) * POTENCIA(AT_EC; 0.92129)";
			break;
		case "T_Oax_12":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.93129) * POTENCIA(AT_EC; 1.01585)";
			break;
		case "T_Oax_13":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.91862) * POTENCIA(AT_EC; 0.95512)";
			break;
		case "T_Oax_14":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.89422) * POTENCIA(AT_EC; 0.98998)";
			break;
		case "T_Oax_15":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.89668) * POTENCIA(AT_EC; 1.0223)";
			break;
		case "T_Oax_18":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.82449) * POTENCIA(AT_EC; 1.06723)";
			break;
		case "T_Oax_19":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.82403) * POTENCIA(AT_EC; 0.962)";
			break;
		case "T_Oax_2":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.92726) * POTENCIA(AT_EC; 0.94878)";
			break;
		case "T_Oax_20":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.77028) * POTENCIA(AT_EC; 0.98424)";
			break;
		case "T_Oax_3":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.89571) * POTENCIA(AT_EC; 0.98306)";
			break;
		case "T_Oax_4":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.90989) * POTENCIA(AT_EC; 0.99571)";
			break;
		case "T_Oax_5":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.91295) * POTENCIA(AT_EC; 0.97195)";
			break;
		case "T_Oax_6":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.92894) * POTENCIA(AT_EC; 0.96078)";
			break;
		case "T_Oax_7":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.87224) * POTENCIA(AT_EC; 0.99003)";
			break;
		case "T_Oax_8":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.90765) * POTENCIA(AT_EC; 0.89216)";
			break;
		case "T_Oax_9":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.98654) * POTENCIA(AT_EC; 0.89281)";
			break;
		case "T0z1_Pue":
			ecuacion = " EXP(-9.80397476 + 1.91686765 * LN(DN_EC) + 1.02474479 * LN(AT_EC))";
			break;
		case "T0z1_SLP":
			ecuacion = " EXP(-9.66777918 + 1.8605487 * LN(DN_EC) + 1.02318950 * LN(AT_EC))";
			break;
		case "T0z3_Hgo":
			ecuacion = " EXP(-9.61229531 + 1.86381485 * LN(DN_EC) + 0.99550933 * LN(AT_EC))";
			break;
		case "T1_Chis":
			ecuacion = " EXP(-9.88038392 + 1.97088088 * LN(DN_EC) + 1.00098368 * LN(AT_EC))";
			break;
		case "T1_U1001_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.82398) * POTENCIA(AT_EC; 1.03686)";
			break;
		case "T1_U1002_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.92896) * POTENCIA(AT_EC; 1.04616)";
			break;
		case "T1_U1003_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.97681) * POTENCIA(AT_EC; 0.9112)";
			break;
		case "T1_U1004_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.9281) * POTENCIA(AT_EC; 0.90225)";
			break;
		case "T1_U1005_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.96185) * POTENCIA(AT_EC; 0.97014)";
			break;
		case "T1_U1006_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.86811) * POTENCIA(AT_EC; 1.06214)";
			break;
		case "T1_U1007_Dgo":
			ecuacion = " 0.00013 * POTENCIA(DN_EC; 1.75073) * POTENCIA(AT_EC; 0.92895)";
			break;
		case "T1_U1008_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.07454) * POTENCIA(AT_EC; 0.89233)";
			break;
		case "T1_U1009_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.92947) * POTENCIA(AT_EC; 1.02125)";
			break;
		case "T1_U1010_Dgo":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.05061) * POTENCIA(AT_EC; 0.99619)";
			break;
		case "T1_U1011_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.99897) * POTENCIA(AT_EC; 1.05569)";
			break;
		case "T1_U1201_Gro":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 2.00426) * POTENCIA(AT_EC; 0.66249)";
			break;
		case "T1_U1203_Gro":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.03025) * POTENCIA(AT_EC; 0.94754)";
			break;
		case "T1_U1204_Gro":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.65091) * POTENCIA(AT_EC; 1.10287)";
			break;
		case "T1_U1205_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.98993) * POTENCIA(AT_EC; 0.86172)";
			break;
		case "T1_U1302_Hgo":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.69394) * POTENCIA(AT_EC; 1.41751)";
			break;
		case "T1_U1303_Hgo":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.98134) * POTENCIA(AT_EC; 0.6307)";
			break;
		case "T1_U1305_Hgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.7432) * POTENCIA(AT_EC; 1.2633)";
			break;
		case "T1_U1401_Jal":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.98723) * POTENCIA(AT_EC; 0.84818)";
			break;
		case "T1_U1404_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.10644) * POTENCIA(AT_EC; 0.80051)";
			break;
		case "T1_U1405_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.02565) * POTENCIA(AT_EC; 1.00268)";
			break;
		case "T1_U1406_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.09078) * POTENCIA(AT_EC; 0.85543)";
			break;
		case "T1_U1407_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.99977) * POTENCIA(AT_EC; 0.97769)";
			break;
		case "T1_U1408_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.05524) * POTENCIA(AT_EC; 0.90206)";
			break;
		case "T1_U1410_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.79089) * POTENCIA(AT_EC; 1.14268)";
			break;
		case "T1_U1505_Mex":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.05097) * POTENCIA(AT_EC; 0.99643)";
			break;
		case "T1_U1507_Mex":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.87703) * POTENCIA(AT_EC; 1.0933)";
			break;
		case "T1_U1508_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.82108) * POTENCIA(AT_EC; 1.05985)";
			break;
		case "T1_U1509_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.11578) * POTENCIA(AT_EC; 0.69756)";
			break;
		case "T1_U1510_Mex":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.82171) * POTENCIA(AT_EC; 0.93929)";
			break;
		case "T1_U1601_Mich":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.88655) * POTENCIA(AT_EC; 0.80173)";
			break;
		case "T1_U1603_Mich":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.91555) * POTENCIA(AT_EC; 0.88843)";
			break;
		case "T1_U1604_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.59223) * POTENCIA(AT_EC; 1.32999)";
			break;
		case "T1_U1605_Mich":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.82578) * POTENCIA(AT_EC; 0.92278)";
			break;
		case "T1_U1607_Mich":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 2.06498) * POTENCIA(AT_EC; 0.6612)";
			break;
		case "T1_U1608_Mich":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.77662) * POTENCIA(AT_EC; 1.02641)";
			break;
		case "T1_U1610_Mich":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.7253) * POTENCIA(AT_EC; 1.12568)";
			break;
		case "T1_U2001_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.01746) * POTENCIA(AT_EC; 0.91953)";
			break;
		case "T1_U2003_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.14361) * POTENCIA(AT_EC; 0.73119)";
			break;
		case "T1_U2007_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.99813) * POTENCIA(AT_EC; 0.84388)";
			break;
		case "T1_U2008_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.99699) * POTENCIA(AT_EC; 0.76895)";
			break;
		case "T1_U2009_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.94358) * POTENCIA(AT_EC; 0.88488)";
			break;
		case "T1_U2010_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.95669) * POTENCIA(AT_EC; 0.84675)";
			break;
		case "T1_U2012_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.0388) * POTENCIA(AT_EC; 0.78043)";
			break;
		case "T1_U2013_Oax":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.05309) * POTENCIA(AT_EC; 0.95747)";
			break;
		case "T1_U2014_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.10964) * POTENCIA(AT_EC; 0.88324)";
			break;
		case "T1_U2101_Pue":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.7611) * POTENCIA(AT_EC; 1.02855)";
			break;
		case "T1_U2103_Pue":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.7097) * POTENCIA(AT_EC; 0.96196)";
			break;
		case "T1_U2105_Pue":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.89329) * POTENCIA(AT_EC; 1.04186)";
			break;
		case "T1_U2107_Pue":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.78432) * POTENCIA(AT_EC; 1.0895)";
			break;
		case "T1_U2108_Pue":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.4906) * POTENCIA(AT_EC; 1.50494)";
			break;
		case "T1_U2301_QRoo":
			ecuacion = " 0.00031 * POTENCIA(DN_EC; 1.57708) * POTENCIA(AT_EC; 0.75499)";
			break;
		case "T1_U2302_QRoo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.72129) * POTENCIA(AT_EC; 1.08118)";
			break;
		case "T1_U2303_QRoo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.34325) * POTENCIA(AT_EC; 0.51945)";
			break;
		case "T1_U2304_QRoo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.02451) * POTENCIA(AT_EC; 0.76808)";
			break;
		case "T1_U2901_Tlax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.01859) * POTENCIA(AT_EC; 0.85547)";
			break;
		case "T1_U2902_Tlax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.04267) * POTENCIA(AT_EC; 0.97354)";
			break;
		case "T1_U3004_Ver":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.81795) * POTENCIA(AT_EC; 1.03504)";
			break;
		case "T1_U3012_Ver":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.86035) * POTENCIA(AT_EC; 0.97824)";
			break;
		case "T1_U3013_Ver":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.82144) * POTENCIA(AT_EC; 0.89308)";
			break;
		case "T1_U801_Chih":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.05275) * POTENCIA(AT_EC; 0.89703)";
			break;
		case "T1_U802_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.902) * POTENCIA(AT_EC; 0.91709)";
			break;
		case "T1_U804_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 2.01648) * POTENCIA(AT_EC; 0.75786)";
			break;
		case "T1_U805_Chih":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.15442) * POTENCIA(AT_EC; 1.02415)";
			break;
		case "T1_U806_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95677) * POTENCIA(AT_EC; 0.93114)";
			break;
		case "T1_U807_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.94246) * POTENCIA(AT_EC; 1.00059)";
			break;
		case "T1_U808_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.9951) * POTENCIA(AT_EC; 0.76449)";
			break;
		case "T1_U809_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.84807) * POTENCIA(AT_EC; 1.06881)";
			break;
		case "T1_U810_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.05719) * POTENCIA(AT_EC; 0.85407)";
			break;
		case "T1_U811_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.00965) * POTENCIA(AT_EC; 0.89719)";
			break;
		case "T10_Chis":
			ecuacion = " EXP(-9.05936092 + 1.68198628 * LN(DN_EC) + 1.0337615 * LN(AT_EC))";
			break;
		case "T10_U1001_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.8731) * POTENCIA(AT_EC; 0.96865)";
			break;
		case "T10_U1002_Dgo":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.78213) * POTENCIA(AT_EC; 0.85848)";
			break;
		case "T10_U1003_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.12406) * POTENCIA(AT_EC; 0.78704)";
			break;
		case "T10_U1006_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.89307) * POTENCIA(AT_EC; 0.96307)";
			break;
		case "T10_U1008_Dgo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.76817) * POTENCIA(AT_EC; 1.01466)";
			break;
		case "T10_U1009_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.95918) * POTENCIA(AT_EC; 1.00516)";
			break;
		case "T10_U1010_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.15357) * POTENCIA(AT_EC; 0.84972)";
			break;
		case "T10_U1011_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.98226) * POTENCIA(AT_EC; 0.84224)";
			break;
		case "T10_U1201y2_Gro":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.73474) * POTENCIA(AT_EC; 0.99207)";
			break;
		case "T10_U1203_Gro":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.7452) * POTENCIA(AT_EC; 1.22851)";
			break;
		case "T10_U1204_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.8584) * POTENCIA(AT_EC; 0.93)";
			break;
		case "T10_U1205_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.64843) * POTENCIA(AT_EC; 1.21042)";
			break;
		case "T10_U1302_Hgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.88035) * POTENCIA(AT_EC; 0.80907)";
			break;
		case "T10_U1303_Hgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.82931) * POTENCIA(AT_EC; 0.98593)";
			break;
		case "T10_U1404_Jal":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.06929) * POTENCIA(AT_EC; 1.02961)";
			break;
		case "T10_U1405_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.71823) * POTENCIA(AT_EC; 1.20129)";
			break;
		case "T10_U1406_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.09696) * POTENCIA(AT_EC; 0.64491)";
			break;
		case "T10_U1407_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.90131) * POTENCIA(AT_EC; 1.03487)";
			break;
		case "T10_U1410_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.71081) * POTENCIA(AT_EC; 1.21788)";
			break;
		case "T10_U1505_Mex":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 2.03275) * POTENCIA(AT_EC; 0.70552)";
			break;
		case "T10_U1507_Mex":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.39918) * POTENCIA(AT_EC; 0.48419)";
			break;
		case "T10_U1508_Mex":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.95131) * POTENCIA(AT_EC; 0.99831)";
			break;
		case "T10_U1509_Mex":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.89268) * POTENCIA(AT_EC; 1.16081)";
			break;
		case "T10_U1510_Mex":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.78519) * POTENCIA(AT_EC; 0.92364)";
			break;
		case "T10_U1603_Mich":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.80922) * POTENCIA(AT_EC; 0.96199)";
			break;
		case "T10_U1607_Mich":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 2.02762) * POTENCIA(AT_EC; 0.60394)";
			break;
		case "T10_U2001_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.02255) * POTENCIA(AT_EC; 0.83354)";
			break;
		case "T10_U2003_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.88856) * POTENCIA(AT_EC; 1.06298)";
			break;
		case "T10_U2008_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.83002) * POTENCIA(AT_EC; 1.04349)";
			break;
		case "T10_U2009_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.79088) * POTENCIA(AT_EC; 1.17172)";
			break;
		case "T10_U2010_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.94157) * POTENCIA(AT_EC; 1.005)";
			break;
		case "T10_U2012_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.99265) * POTENCIA(AT_EC; 1.00428)";
			break;
		case "T10_U2013_Oax":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.06095) * POTENCIA(AT_EC; 1.04435)";
			break;
		case "T10_U2101_Pue":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.96558) * POTENCIA(AT_EC; 0.98027)";
			break;
		case "T10_U2108_Pue":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.1668) * POTENCIA(AT_EC; 1.73911)";
			break;
		case "T10_U2301_QRoo":
			ecuacion = " 0.00015 * POTENCIA(DN_EC; 1.72921) * POTENCIA(AT_EC; 0.84001)";
			break;
		case "T10_U2302_QRoo":
			ecuacion = " 0.00017 * POTENCIA(DN_EC; 1.75669) * POTENCIA(AT_EC; 0.7768)";
			break;
		case "T10_U2303_QRoo":
			ecuacion = " 0.00027 * POTENCIA(DN_EC; 1.81392) * POTENCIA(AT_EC; 0.39339)";
			break;
		case "T10_U2304_QRoo":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.84942) * POTENCIA(AT_EC; 0.6965)";
			break;
		case "T10_U2901_Tlax":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.74161) * POTENCIA(AT_EC; 1.03886)";
			break;
		case "T10_U2902_Tlax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.77764) * POTENCIA(AT_EC; 1.04287)";
			break;
		case "T10_U3012_Ver":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.83173) * POTENCIA(AT_EC; 0.92786)";
			break;
		case "T10_U3013_Ver":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.88185) * POTENCIA(AT_EC; 0.95411)";
			break;
		case "T10_U801_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.05268) * POTENCIA(AT_EC; 0.7319)";
			break;
		case "T10_U802_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.9842) * POTENCIA(AT_EC; 0.74711)";
			break;
		case "T10_U804_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.98983) * POTENCIA(AT_EC; 0.87849)";
			break;
		case "T10_U806_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.02955) * POTENCIA(AT_EC; 0.93238)";
			break;
		case "T10_U808_Chih":
			ecuacion = " 0.00018 * POTENCIA(DN_EC; 1.91424) * POTENCIA(AT_EC; 0.51072)";
			break;
		case "T10_U810_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.86262) * POTENCIA(AT_EC; 0.93531)";
			break;
		case "T10_U811_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.92643) * POTENCIA(AT_EC; 0.76389)";
			break;
		case "T10z4_Mex":
			ecuacion = " EXP(-1.27349 + 1.791348 * LN(DN_EC / 100) + 1.019809 * LN(AT_EC))";
			break;
		case "T10z5_Gro":
			ecuacion = " -0.0798 + 0.63398 * DN_EC / 100 + 0.00413 * AT_EC + 0.23888 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "T11_Chis":
			ecuacion = " EXP(-10.09141965 + 1.90481967 * LN(DN_EC) + 1.09303973 * LN(AT_EC))";
			break;
		case "T11_Gro":
			ecuacion = " 0.23104 - 2.32118 * DN_EC / 100 - 0.02378 * AT_EC + 7.475 * POTENCIA(DN_EC / 100; 2)+ 0.18215 * AT_EC * DN_EC / 100";
			break;
		case "T11_Mex":
			ecuacion = " EXP(-1.586189 + 1.708726 * LN(DN_EC / 100) + 1.094269 * LN(AT_EC))";
			break;
		case "T11_U1001_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.87268) * POTENCIA(AT_EC; 1.00995)";
			break;
		case "T11_U1002_Dgo":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 1.84212) * POTENCIA(AT_EC; 1.30357)";
			break;
		case "T11_U1003_Dgo":
			ecuacion = " 0.00024 * POTENCIA(DN_EC; 1.61852) * POTENCIA(AT_EC; 0.74262)";
			break;
		case "T11_U1006_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.02067) * POTENCIA(AT_EC; 0.99615)";
			break;
		case "T11_U1008_Dgo":
			ecuacion = " 0.0002 * POTENCIA(DN_EC; 1.69519) * POTENCIA(AT_EC; 0.84876)";
			break;
		case "T11_U1010_Dgo":
			ecuacion = " 0.00013 * POTENCIA(DN_EC; 1.84925) * POTENCIA(AT_EC; 0.69308)";
			break;
		case "T11_U1201y2_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.79891) * POTENCIA(AT_EC; 1.08229)";
			break;
		case "T11_U1203_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.86461) * POTENCIA(AT_EC; 0.92862)";
			break;
		case "T11_U1204_Gro":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.87134) * POTENCIA(AT_EC; 0.77585)";
			break;
		case "T11_U1205_Gro":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.97164) * POTENCIA(AT_EC; 1.03669)";
			break;
		case "T11_U1302_Hgo":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.34709) * POTENCIA(AT_EC; 0.70399)";
			break;
		case "T11_U1303_Hgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.91537) * POTENCIA(AT_EC; 0.93148)";
			break;
		case "T11_U1404_Jal":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.81817) * POTENCIA(AT_EC; 0.93346)";
			break;
		case "T11_U1405_Jal":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.77603) * POTENCIA(AT_EC; 1.00051)";
			break;
		case "T11_U1406_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.08628) * POTENCIA(AT_EC; 0.72886)";
			break;
		case "T11_U1407_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.99106) * POTENCIA(AT_EC; 1.08692)";
			break;
		case "T11_U1410_Jal":
			ecuacion = " 0.00001 * POTENCIA(DN_EC; 2.09434) * POTENCIA(AT_EC; 1.16445)";
			break;
		case "T11_U1505_Mex":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.73676) * POTENCIA(AT_EC; 0.77552)";
			break;
		case "T11_U1507_Mex":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.93144) * POTENCIA(AT_EC; 1.08733)";
			break;
		case "T11_U1508_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.78917) * POTENCIA(AT_EC; 1.04436)";
			break;
		case "T11_U1509_Mex":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.78245) * POTENCIA(AT_EC; 1.30442)";
			break;
		case "T11_U1510_Mex":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 2.00468) * POTENCIA(AT_EC; 0.56892)";
			break;
		case "T11_U2001_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.92132) * POTENCIA(AT_EC; 1.0083)";
			break;
		case "T11_U2003_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.72731) * POTENCIA(AT_EC; 1.13779)";
			break;
		case "T11_U2008_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.87754) * POTENCIA(AT_EC; 0.87749)";
			break;
		case "T11_U2009_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.7947) * POTENCIA(AT_EC; 1.11387)";
			break;
		case "T11_U2010_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.84771) * POTENCIA(AT_EC; 1.04512)";
			break;
		case "T11_U2012_Oax":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.78075) * POTENCIA(AT_EC; 0.99887)";
			break;
		case "T11_U2013_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.76527) * POTENCIA(AT_EC; 1.11918)";
			break;
		case "T11_U2101_Pue":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.74873) * POTENCIA(AT_EC; 1.10365)";
			break;
		case "T11_U2108_Pue":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.6026) * POTENCIA(AT_EC; 1.19117)";
			break;
		case "T11_U2901_Tlax":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.8462) * POTENCIA(AT_EC; 0.88922)";
			break;
		case "T11_U2902_Tlax":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.82594) * POTENCIA(AT_EC; 0.87733)";
			break;
		case "T11_U3012_Ver":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.98735) * POTENCIA(AT_EC; 0.77516)";
			break;
		case "T11_U3013_Ver":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.67436) * POTENCIA(AT_EC; 1.04303)";
			break;
		case "T11_U801_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95884) * POTENCIA(AT_EC; 0.83487)";
			break;
		case "T11_U804_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.00358) * POTENCIA(AT_EC; 0.91881)";
			break;
		case "T11_U806_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.88688) * POTENCIA(AT_EC; 0.96713)";
			break;
		case "T11_U810_Chih":
			ecuacion = " 0.00021 * POTENCIA(DN_EC; 1.73875) * POTENCIA(AT_EC; 0.57042)";
			break;
		case "T11_U811_Chih":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.84965) * POTENCIA(AT_EC; 0.70945)";
			break;
		case "T12_Chis":
			ecuacion = " EXP(-9.84669352 + 1.93536382 * LN(DN_EC) + 1.01473381 * LN(AT_EC))";
			break;
		case "T12_Gro":
			ecuacion = " 0.06629 + 0.34683 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "T12_Mex":
			ecuacion = " EXP(-0.77785 + 1.872175 * LN(DN_EC / 100) + 0.815238 * LN(AT_EC))";
			break;
		case "T12_QR":
			ecuacion = " EXP(-9.63573531 + 1.90246451 * LN(DN_EC) + 0.97875991 * LN(AT_EC))";
			break;
		case "T12_U1001_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.9367) * POTENCIA(AT_EC; 0.83897)";
			break;
		case "T12_U1002_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.89803) * POTENCIA(AT_EC; 0.96438)";
			break;
		case "T12_U1003_Dgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.91234) * POTENCIA(AT_EC; 0.89825)";
			break;
		case "T12_U1006_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.07582) * POTENCIA(AT_EC; 0.90926)";
			break;
		case "T12_U1008_Dgo":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.11797) * POTENCIA(AT_EC; 0.93901)";
			break;
		case "T12_U1010_Dgo":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.86859) * POTENCIA(AT_EC; 0.76618)";
			break;
		case "T12_U1201y2_Gro":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.85355) * POTENCIA(AT_EC; 0.90562)";
			break;
		case "T12_U1203_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.93129) * POTENCIA(AT_EC; 0.96447)";
			break;
		case "T12_U1204_Gro":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.87652) * POTENCIA(AT_EC; 0.73884)";
			break;
		case "T12_U1205_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.9694) * POTENCIA(AT_EC; 0.79289)";
			break;
		case "T12_U1302_Hgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.57065) * POTENCIA(AT_EC; 1.3666)";
			break;
		case "T12_U1303_Hgo":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.40124) * POTENCIA(AT_EC; 1.24464)";
			break;
		case "T12_U1404_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.97118) * POTENCIA(AT_EC; 1.02248)";
			break;
		case "T12_U1406_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.94334) * POTENCIA(AT_EC; 0.91533)";
			break;
		case "T12_U1410_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.09965) * POTENCIA(AT_EC; 0.94988)";
			break;
		case "T12_U1505_Mex":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.8022) * POTENCIA(AT_EC; 0.90355)";
			break;
		case "T12_U1507_Mex":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.7487) * POTENCIA(AT_EC; 1.31458)";
			break;
		case "T12_U1509_Mex":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.79799) * POTENCIA(AT_EC; 0.96039)";
			break;
		case "T12_U1510_Mex":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.77772) * POTENCIA(AT_EC; 1.34495)";
			break;
		case "T12_U2001_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.83769) * POTENCIA(AT_EC; 0.98303)";
			break;
		case "T12_U2003_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.91401) * POTENCIA(AT_EC; 0.89035)";
			break;
		case "T12_U2008_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.52983) * POTENCIA(AT_EC; 1.41074)";
			break;
		case "T12_U2009_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.77172) * POTENCIA(AT_EC; 1.04104)";
			break;
		case "T12_U2010_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.88817) * POTENCIA(AT_EC; 0.91436)";
			break;
		case "T12_U2012_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.85893) * POTENCIA(AT_EC; 1.15049)";
			break;
		case "T12_U2013_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.86566) * POTENCIA(AT_EC; 0.88945)";
			break;
		case "T12_U2014_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.85974) * POTENCIA(AT_EC; 1.05727)";
			break;
		case "T12_U2108_Pue":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.73265) * POTENCIA(AT_EC; 0.95724)";
			break;
		case "T12_U2901_Tlax":
			ecuacion = " 0.00015 * POTENCIA(DN_EC; 1.8332) * POTENCIA(AT_EC; 0.68578)";
			break;
		case "T12_U2902_Tlax":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.68118) * POTENCIA(AT_EC; 1.03035)";
			break;
		case "T12_U3012_Ver":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.11265) * POTENCIA(AT_EC; 0.76038)";
			break;
		case "T12_U3013_Ver":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.59046) * POTENCIA(AT_EC; 1.29633)";
			break;
		case "T12_U801_Chih":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.74965) * POTENCIA(AT_EC; 0.769)";
			break;
		case "T12_U806_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.96232) * POTENCIA(AT_EC; 0.80451)";
			break;
		case "T12_U810_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.82279) * POTENCIA(AT_EC; 1.06227)";
			break;
		case "T12_U811_Chih":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.84682) * POTENCIA(AT_EC; 0.76733)";
			break;
		case "T13_Chis":
			ecuacion = " EXP(-9.87689 + 1.9349903 * LN(DN_EC) + 1.03862975 * LN(AT_EC))";
			break;
		case "T13_QR":
			ecuacion = " EXP(-9.84052491 + 1.92716537 * LN(DN_EC) + 1.00282618 * LN(AT_EC))";
			break;
		case "T13_U1002_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.0108) * POTENCIA(AT_EC; 0.89173)";
			break;
		case "T13_U1003_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.0634) * POTENCIA(AT_EC; 0.7825)";
			break;
		case "T13_U1006_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.96305) * POTENCIA(AT_EC; 0.8026)";
			break;
		case "T13_U1008_Dgo":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.84425) * POTENCIA(AT_EC; 0.76222)";
			break;
		case "T13_U1010_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.03965) * POTENCIA(AT_EC; 0.92099)";
			break;
		case "T13_U1201y2_Gro":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.73593) * POTENCIA(AT_EC; 0.98418)";
			break;
		case "T13_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.93135) * POTENCIA(AT_EC; 0.92651)";
			break;
		case "T13_U1205_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.82814) * POTENCIA(AT_EC; 0.97979)";
			break;
		case "T13_U1303_Hgo":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.79496) * POTENCIA(AT_EC; 1.25512)";
			break;
		case "T13_U1404_Jal":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 1.93249) * POTENCIA(AT_EC; 1.23334)";
			break;
		case "T13_U1410_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.97205) * POTENCIA(AT_EC; 1.03928)";
			break;
		case "T13_U1507_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.95754) * POTENCIA(AT_EC; 0.94176)";
			break;
		case "T13_U2001_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.87516) * POTENCIA(AT_EC; 0.97445)";
			break;
		case "T13_U2003_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.00365) * POTENCIA(AT_EC; 0.81287)";
			break;
		case "T13_U2008_Oax":
			ecuacion = " 0.00015 * POTENCIA(DN_EC; 1.68185) * POTENCIA(AT_EC; 0.80271)";
			break;
		case "T13_U2010_Oax":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.07561) * POTENCIA(AT_EC; 0.90062)";
			break;
		case "T13_U2012_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.91711) * POTENCIA(AT_EC; 0.99324)";
			break;
		case "T13_U2108_Pue":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.96954) * POTENCIA(AT_EC; 0.87052)";
			break;
		case "T13_U2901_Tlax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.85434) * POTENCIA(AT_EC; 1.01199)";
			break;
		case "T13_U2902_Tlax":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.92002) * POTENCIA(AT_EC; 0.65657)";
			break;
		case "T13_U3012_Ver":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.1642) * POTENCIA(AT_EC; 1.04988)";
			break;
		case "T13_U3013_Ver":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.69633) * POTENCIA(AT_EC; 1.20836)";
			break;
		case "T13_U804_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95563) * POTENCIA(AT_EC; 0.83333)";
			break;
		case "T13_U806_Chih":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.99154) * POTENCIA(AT_EC; 1.03238)";
			break;
		case "T13_U810_Chih":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.82515) * POTENCIA(AT_EC; 0.91812)";
			break;
		case "T13_U811_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.86162) * POTENCIA(AT_EC; 0.94108)";
			break;
		case "T14_Chis":
			ecuacion = " EXP(-9.82944377 + 1.9060093 * LN(DN_EC) + 1.04047533 * LN(AT_EC))";
			break;
		case "T14_QR":
			ecuacion = " EXP(-9.52375084 + 1.81551953 * LN(DN_EC) + 1.03039019 * LN(AT_EC))";
			break;
		case "T14_U1006_Dgo":
			ecuacion = " 0.00016 * POTENCIA(DN_EC; 1.68594) * POTENCIA(AT_EC; 0.79879)";
			break;
		case "T14_U1008_Dgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.84636) * POTENCIA(AT_EC; 0.90146)";
			break;
		case "T14_U1201_Gro":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.98843) * POTENCIA(AT_EC; 0.67767)";
			break;
		case "T14_U1203_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.71575) * POTENCIA(AT_EC; 1.20382)";
			break;
		case "T14_U1205_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.88299) * POTENCIA(AT_EC; 0.96023)";
			break;
		case "T14_U1303_Hgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.8925) * POTENCIA(AT_EC; 0.94134)";
			break;
		case "T14_U1404_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.94151) * POTENCIA(AT_EC; 1.01896)";
			break;
		case "T14_U1410_Jal":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.20796) * POTENCIA(AT_EC; 0.9231)";
			break;
		case "T14_U2001_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.93975) * POTENCIA(AT_EC; 0.97875)";
			break;
		case "T14_U2010_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.84825) * POTENCIA(AT_EC; 1.13777)";
			break;
		case "T14_U2012_Oax":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.83624) * POTENCIA(AT_EC; 1.21178)";
			break;
		case "T14_U2901_Tlax":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.81743) * POTENCIA(AT_EC; 0.93896)";
			break;
		case "T14_U2902_Tlax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.83021) * POTENCIA(AT_EC; 1.07396)";
			break;
		case "T14_U3012_Ver":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.20121) * POTENCIA(AT_EC; 0.87633)";
			break;
		case "T14_U3013_Ver":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.61584) * POTENCIA(AT_EC; 1.07489)";
			break;
		case "T14_U804_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.97893) * POTENCIA(AT_EC; 0.87775)";
			break;
		case "T14_U806_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.07127) * POTENCIA(AT_EC; 0.75716)";
			break;
		case "T14_U810_Chih":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.71498) * POTENCIA(AT_EC; 1.11909)";
			break;
		case "T14_U811_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.04563) * POTENCIA(AT_EC; 0.78953)";
			break;
		case "T15_Chis":
			ecuacion = " EXP(-9.80434696 + 1.91033696 * LN(DN_EC) + 1.03262007 * LN(AT_EC))";
			break;
		case "T15_QR":
			ecuacion = " EXP(-9.45441486 + 1.868413 * LN(DN_EC) + 0.9316352 * LN(AT_EC))";
			break;
		case "T15_U1006_Dgo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.82002) * POTENCIA(AT_EC; 0.9438)";
			break;
		case "T15_U1008_Dgo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.75675) * POTENCIA(AT_EC; 0.98256)";
			break;
		case "T15_U1201y2_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.83318) * POTENCIA(AT_EC; 1.02182)";
			break;
		case "T15_U1203_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.91091) * POTENCIA(AT_EC; 0.95788)";
			break;
		case "T15_U1205_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.05405) * POTENCIA(AT_EC; 0.66476)";
			break;
		case "T15_U1303_Hgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95017) * POTENCIA(AT_EC; 0.85372)";
			break;
		case "T15_U2001_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.88297) * POTENCIA(AT_EC; 0.93261)";
			break;
		case "T15_U2010_Oax":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.67636) * POTENCIA(AT_EC; 0.96056)";
			break;
		case "T15_U2012_Oax":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.80711) * POTENCIA(AT_EC; 1.21149)";
			break;
		case "T15_U2901_Tlax":
			ecuacion = " 0.00019 * POTENCIA(DN_EC; 1.63752) * POTENCIA(AT_EC; 0.86217)";
			break;
		case "T15_U2902_Tlax":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.93142) * POTENCIA(AT_EC; 0.84994)";
			break;
		case "T15_U804_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.01975) * POTENCIA(AT_EC; 0.78619)";
			break;
		case "T15_U806_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.01191) * POTENCIA(AT_EC; 0.80774)";
			break;
		case "T15_U811_Chih":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.80879) * POTENCIA(AT_EC; 0.7094)";
			break;
		case "T16_Chis":
			ecuacion = " EXP(-10.12597512 + 2.04755627 * LN(DN_EC) + 0.96453516 * LN(AT_EC))";
			break;
		case "T16_QR":
			ecuacion = " EXP(-9.63519924 + 1.83658572 * LN(DN_EC) + 1.02444663 * LN(AT_EC))";
			break;
		case "T16_U1006_Dgo":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.03332) * POTENCIA(AT_EC; 1.01105)";
			break;
		case "T16_U1008_Dgo":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.75125) * POTENCIA(AT_EC; 1.02691)";
			break;
		case "T16_U1201_Gro":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.88392) * POTENCIA(AT_EC; 0.72281)";
			break;
		case "T16_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.80374) * POTENCIA(AT_EC; 1.03167)";
			break;
		case "T16_U1205_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.66062) * POTENCIA(AT_EC; 1.16511)";
			break;
		case "T16_U1303_Hgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.90765) * POTENCIA(AT_EC; 0.98787)";
			break;
		case "T16_U2001_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.83477) * POTENCIA(AT_EC; 0.99152)";
			break;
		case "T16_U2012_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.84015) * POTENCIA(AT_EC; 0.91458)";
			break;
		case "T16_U2902_Tlax":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.72088) * POTENCIA(AT_EC; 1.03414)";
			break;
		case "T16_U804_Chih":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.05885) * POTENCIA(AT_EC; 0.96617)";
			break;
		case "T16_U806_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.0334) * POTENCIA(AT_EC; 0.84059)";
			break;
		case "T17_U1008_Dgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.82596) * POTENCIA(AT_EC; 0.91915)";
			break;
		case "T17_U1201_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.89103) * POTENCIA(AT_EC; 0.90469)";
			break;
		case "T17_U1203_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.8555) * POTENCIA(AT_EC; 0.94653)";
			break;
		case "T17_U1205_Gro":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.98034) * POTENCIA(AT_EC; 0.99271)";
			break;
		case "T17_U1303_Hgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.82154) * POTENCIA(AT_EC; 0.96124)";
			break;
		case "T17_U2012_Oax":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.66019) * POTENCIA(AT_EC; 1.02933)";
			break;
		case "T17_U2902_Tlax":
			ecuacion = " 0.00021 * POTENCIA(DN_EC; 1.29859) * POTENCIA(AT_EC; 1.23463)";
			break;
		case "T17_U804_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.85832) * POTENCIA(AT_EC; 0.99531)";
			break;
		case "T17_U806_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.76881) * POTENCIA(AT_EC; 0.98972)";
			break;
		case "T18_U1201y2_Gro":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.09874) * POTENCIA(AT_EC; 0.84217)";
			break;
		case "T18_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.88952) * POTENCIA(AT_EC; 0.9247)";
			break;
		case "T18_U1303_Hgo":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.64033) * POTENCIA(AT_EC; 1.41443)";
			break;
		case "T18_U804_Chih":
			ecuacion = " 0.00013 * POTENCIA(DN_EC; 1.87996) * POTENCIA(AT_EC; 0.58307)";
			break;
		case "T18_U806_Chih":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.95421) * POTENCIA(AT_EC; 0.65826)";
			break;
		case "T19_U1201_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.9076) * POTENCIA(AT_EC; 0.86915)";
			break;
		case "T19_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.96691) * POTENCIA(AT_EC; 0.82907)";
			break;
		case "T19_U1303_Hgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.73605) * POTENCIA(AT_EC; 1.16221)";
			break;
		case "T1z1_Gro":
			ecuacion = " 0.17784 + 0.34232 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "T1z1_Mex":
			ecuacion = " EXP(-0.909190 + 1.876563 * LN(DN_EC / 100) + 0.947742 * LN(AT_EC))";
			break;
		case "T1z1_Tlax":
			ecuacion = " EXP(-0.82535 + 1.899918 * LN(DN_EC / 100) + 0.945586 * LN(AT_EC))";
			break;
		case "T2_Chis":
			ecuacion = " EXP(-9.92570337 + 1.96275753 * LN(DN_EC) + 1.00116088 * LN(AT_EC))";
			break;
		case "T2_QR":
			ecuacion = " EXP(-9.6873264 + 1.87777278 * LN(DN_EC) + 1.01495306 * LN(AT_EC))";
			break;
		case "T2_U1001_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.86117) * POTENCIA(AT_EC; 1.05993)";
			break;
		case "T2_U1002_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95633) * POTENCIA(AT_EC; 0.93913)";
			break;
		case "T2_U1003_Dgo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.98828) * POTENCIA(AT_EC; 0.75962)";
			break;
		case "T2_U1004_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.97576) * POTENCIA(AT_EC; 0.86312)";
			break;
		case "T2_U1005_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.03226) * POTENCIA(AT_EC; 0.87136)";
			break;
		case "T2_U1006_Dgo":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.88188) * POTENCIA(AT_EC; 0.87044)";
			break;
		case "T2_U1007_Dgo":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.93565) * POTENCIA(AT_EC; 0.73791)";
			break;
		case "T2_U1008_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.94821) * POTENCIA(AT_EC; 0.97026)";
			break;
		case "T2_U1009_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.00275) * POTENCIA(AT_EC; 0.8598)";
			break;
		case "T2_U1010_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.92673) * POTENCIA(AT_EC; 0.98615)";
			break;
		case "T2_U1011_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.00005) * POTENCIA(AT_EC; 0.85033)";
			break;
		case "T2_U1201y2_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.8127) * POTENCIA(AT_EC; 1.05354)";
			break;
		case "T2_U1203_Gro":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.86721) * POTENCIA(AT_EC; 1.12828)";
			break;
		case "T2_U1204_Gro":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.80194) * POTENCIA(AT_EC; 0.92263)";
			break;
		case "T2_U1205_Gro":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.95113) * POTENCIA(AT_EC; 1.00285)";
			break;
		case "T2_U1302_Hgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.97977) * POTENCIA(AT_EC; 0.81845)";
			break;
		case "T2_U1303_Hgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.55341) * POTENCIA(AT_EC; 1.39447)";
			break;
		case "T2_U1305_Hgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.93694) * POTENCIA(AT_EC; 1.03169)";
			break;
		case "T2_U1401_Jal":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.9822) * POTENCIA(AT_EC; 0.70974)";
			break;
		case "T2_U1404_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.91718) * POTENCIA(AT_EC; 1.08265)";
			break;
		case "T2_U1405_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.97454) * POTENCIA(AT_EC; 1.01106)";
			break;
		case "T2_U1406_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.06504) * POTENCIA(AT_EC; 0.97807)";
			break;
		case "T2_U1407_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.20898) * POTENCIA(AT_EC; 0.8316)";
			break;
		case "T2_U1408_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.07869) * POTENCIA(AT_EC; 0.85276)";
			break;
		case "T2_U1410_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.06714) * POTENCIA(AT_EC; 0.93064)";
			break;
		case "T2_U1505_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.82435) * POTENCIA(AT_EC; 1.14342)";
			break;
		case "T2_U1507_Mex":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.96323) * POTENCIA(AT_EC; 0.97782)";
			break;
		case "T2_U1508_Mex":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.0441) * POTENCIA(AT_EC; 0.80253)";
			break;
		case "T2_U1509_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.89374) * POTENCIA(AT_EC; 1.04389)";
			break;
		case "T2_U1510_Mex":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.73764) * POTENCIA(AT_EC; 0.93166)";
			break;
		case "T2_U1601_Mich":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.84034) * POTENCIA(AT_EC; 0.87928)";
			break;
		case "T2_U1603_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.92319) * POTENCIA(AT_EC; 0.90548)";
			break;
		case "T2_U1604_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.82561) * POTENCIA(AT_EC; 1.08247)";
			break;
		case "T2_U1605_Mich":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.83282) * POTENCIA(AT_EC; 0.90437)";
			break;
		case "T2_U1607_Mich":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.97523) * POTENCIA(AT_EC; 1.11777)";
			break;
		case "T2_U1608_Mich":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.84295) * POTENCIA(AT_EC; 1.07958)";
			break;
		case "T2_U1610_Mich":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.95469) * POTENCIA(AT_EC; 0.98265)";
			break;
		case "T2_U2001_Oax":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.93213) * POTENCIA(AT_EC; 0.82212)";
			break;
		case "T2_U2003_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.03713) * POTENCIA(AT_EC; 0.81892)";
			break;
		case "T2_U2007_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.88364) * POTENCIA(AT_EC; 0.87928)";
			break;
		case "T2_U2008_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.99388) * POTENCIA(AT_EC; 0.92306)";
			break;
		case "T2_U2009_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.9378) * POTENCIA(AT_EC; 0.87829)";
			break;
		case "T2_U2010_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.91641) * POTENCIA(AT_EC; 0.89172)";
			break;
		case "T2_U2012_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.08092) * POTENCIA(AT_EC; 0.85026)";
			break;
		case "T2_U2013_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.92158) * POTENCIA(AT_EC; 1.0768)";
			break;
		case "T2_U2101_Pue":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.35427) * POTENCIA(AT_EC; 1.27362)";
			break;
		case "T2_U2103_Pue":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.99006) * POTENCIA(AT_EC; 0.92655)";
			break;
		case "T2_U2105_Pue":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.70785) * POTENCIA(AT_EC; 1.15901)";
			break;
		case "T2_U2107_Pue":
			ecuacion = " 0.00013 * POTENCIA(DN_EC; 1.77287) * POTENCIA(AT_EC; 0.67909)";
			break;
		case "T2_U2108_Pue":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.4768) * POTENCIA(AT_EC; 1.32147)";
			break;
		case "T2_U2301_QRoo":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.57877) * POTENCIA(AT_EC; 1.08291)";
			break;
		case "T2_U2302_QRoo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.73806) * POTENCIA(AT_EC; 0.95672)";
			break;
		case "T2_U2303_QRoo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.08565) * POTENCIA(AT_EC; 0.8069)";
			break;
		case "T2_U2304_QRoo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.10523) * POTENCIA(AT_EC; 0.84016)";
			break;
		case "T2_U2901_Tlax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.85095) * POTENCIA(AT_EC; 0.98151)";
			break;
		case "T2_U2902_Tlax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.94909) * POTENCIA(AT_EC; 1.04078)";
			break;
		case "T2_U3004_Ver":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.89909) * POTENCIA(AT_EC; 1.14576)";
			break;
		case "T2_U3012_Ver":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.01037) * POTENCIA(AT_EC; 0.86184)";
			break;
		case "T2_U3013_Ver":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.97608) * POTENCIA(AT_EC; 0.9002)";
			break;
		case "T2_U801_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95675) * POTENCIA(AT_EC; 0.94514)";
			break;
		case "T2_U802_Chih":
			ecuacion = " 0.00023 * POTENCIA(DN_EC; 1.42715) * POTENCIA(AT_EC; 1.11661)";
			break;
		case "T2_U804_Chih":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.92394) * POTENCIA(AT_EC; 0.82353)";
			break;
		case "T2_U805_Chih":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.12015) * POTENCIA(AT_EC; 0.96935)";
			break;
		case "T2_U806_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.91386) * POTENCIA(AT_EC; 1.06539)";
			break;
		case "T2_U807_Chih":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.95717) * POTENCIA(AT_EC; 1.10348)";
			break;
		case "T2_U808_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.78357) * POTENCIA(AT_EC; 1.09827)";
			break;
		case "T2_U809_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.95135) * POTENCIA(AT_EC; 0.90316)";
			break;
		case "T2_U810_Chih":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.02204) * POTENCIA(AT_EC; 0.96801)";
			break;
		case "T2_U811_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.84606) * POTENCIA(AT_EC; 0.97668)";
			break;
		case "T20_U1201_Gro":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.99129) * POTENCIA(AT_EC; 0.71462)";
			break;
		case "T20_U1203_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.87424) * POTENCIA(AT_EC; 0.87397)";
			break;
		case "T20_U1303_Hgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.85604) * POTENCIA(AT_EC; 0.97898)";
			break;
		case "T21_U1201y2_Gro":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.9661) * POTENCIA(AT_EC; 0.73174)";
			break;
		case "T21_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.87357) * POTENCIA(AT_EC; 0.93714)";
			break;
		case "T22_U1201y2_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.91897) * POTENCIA(AT_EC; 0.984)";
			break;
		case "T22_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.98985) * POTENCIA(AT_EC; 0.81221)";
			break;
		case "T23_U1201_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.02075) * POTENCIA(AT_EC; 0.72025)";
			break;
		case "T23_U1203_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.84483) * POTENCIA(AT_EC; 1.01948)";
			break;
		case "T24_U1201y2_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.02194) * POTENCIA(AT_EC; 0.83266)";
			break;
		case "T24_U1203_Gro":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.0771) * POTENCIA(AT_EC; 0.80396)";
			break;
		case "T24_U804_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.89076) * POTENCIA(AT_EC; 0.83904)";
			break;
		case "T25_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.74316) * POTENCIA(AT_EC; 1.13058)";
			break;
		case "T26_U1203_Gro":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.98818) * POTENCIA(AT_EC; 0.9522)";
			break;
		case "T27_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.86597) * POTENCIA(AT_EC; 0.95817)";
			break;
		case "T2z1_Mex":
			ecuacion = " EXP(-1.128349 + 1.782485 * LN(DN_EC / 100) + 0.924868 * LN(AT_EC))";
			break;
		case "T2z2_Gro":
			ecuacion = " -0.08526 + 4.61495 * POTENCIA(DN_EC / 100; 2) + 0.22781 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "T2z2_Tlax":
			ecuacion = " EXP(-1.037999 + 1.814934 * LN(DN_EC / 100) + 0.950521 * LN(AT_EC))";
			break;
		case "T3_Chis":
			ecuacion = " EXP(-10.22400164 + 1.93392327 * LN(DN_EC) + 1.12044335 * LN(AT_EC))";
			break;
		case "T3_QR":
			ecuacion = " EXP(-10.078102 + 1.92989964 * LN(DN_EC) + 1.0770193 * LN(AT_EC))";
			break;
		case "T3_U1001_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.83699) * POTENCIA(AT_EC; 1.01607)";
			break;
		case "T3_U1002_Dgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.91626) * POTENCIA(AT_EC; 0.86554)";
			break;
		case "T3_U1003_Dgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 2.03749) * POTENCIA(AT_EC; 0.78853)";
			break;
		case "T3_U1004_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.89458) * POTENCIA(AT_EC; 0.93872)";
			break;
		case "T3_U1005_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.00232) * POTENCIA(AT_EC; 0.79361)";
			break;
		case "T3_U1006_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.94435) * POTENCIA(AT_EC; 0.94293)";
			break;
		case "T3_U1007_Dgo":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.87742) * POTENCIA(AT_EC; 0.80985)";
			break;
		case "T3_U1008_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.82353) * POTENCIA(AT_EC; 1.05086)";
			break;
		case "T3_U1009_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.869) * POTENCIA(AT_EC; 1.09056)";
			break;
		case "T3_U1010_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.10833) * POTENCIA(AT_EC; 0.79107)";
			break;
		case "T3_U1011_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.99784) * POTENCIA(AT_EC; 0.90528)";
			break;
		case "T3_U1201y2_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.79902) * POTENCIA(AT_EC; 1.0437)";
			break;
		case "T3_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.88481) * POTENCIA(AT_EC; 0.98452)";
			break;
		case "T3_U1204_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.83114) * POTENCIA(AT_EC; 1.05311)";
			break;
		case "T3_U1205_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.93557) * POTENCIA(AT_EC; 0.87194)";
			break;
		case "T3_U1302_Hgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.76194) * POTENCIA(AT_EC; 0.99479)";
			break;
		case "T3_U1303_Hgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.98903) * POTENCIA(AT_EC; 0.99997)";
			break;
		case "T3_U1305_Hgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.12208) * POTENCIA(AT_EC; 0.69047)";
			break;
		case "T3_U1401_Jal":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.8619) * POTENCIA(AT_EC; 0.94937)";
			break;
		case "T3_U1404_Jal":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.99208) * POTENCIA(AT_EC; 0.84139)";
			break;
		case "T3_U1405_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.04553) * POTENCIA(AT_EC; 0.92615)";
			break;
		case "T3_U1406_Jal":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.10768) * POTENCIA(AT_EC; 1.0438)";
			break;
		case "T3_U1407_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.15031) * POTENCIA(AT_EC; 0.69787)";
			break;
		case "T3_U1408_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95265) * POTENCIA(AT_EC; 0.91457)";
			break;
		case "T3_U1410_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.86523) * POTENCIA(AT_EC; 1.16582)";
			break;
		case "T3_U1505_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.10068) * POTENCIA(AT_EC; 0.74372)";
			break;
		case "T3_U1507_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.99166) * POTENCIA(AT_EC; 0.95628)";
			break;
		case "T3_U1508_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.97258) * POTENCIA(AT_EC; 0.92797)";
			break;
		case "T3_U1509_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.73205) * POTENCIA(AT_EC; 1.14501)";
			break;
		case "T3_U1510_Mex":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.99742) * POTENCIA(AT_EC; 1.0143)";
			break;
		case "T3_U1601_Mich":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.89055) * POTENCIA(AT_EC; 0.91218)";
			break;
		case "T3_U1603_Mich":
			ecuacion = " 0.00017 * POTENCIA(DN_EC; 1.9634) * POTENCIA(AT_EC; 0.52724)";
			break;
		case "T3_U1604_Mich":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.76169) * POTENCIA(AT_EC; 0.87707)";
			break;
		case "T3_U1605_Mich":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.89969) * POTENCIA(AT_EC; 0.9162)";
			break;
		case "T3_U1607_Mich":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.83255) * POTENCIA(AT_EC; 0.84444)";
			break;
		case "T3_U1608_Mich":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.82941) * POTENCIA(AT_EC; 0.88438)";
			break;
		case "T3_U1610_Mich":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.65844) * POTENCIA(AT_EC; 1.08049)";
			break;
		case "T3_U2001_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.87534) * POTENCIA(AT_EC; 0.96396)";
			break;
		case "T3_U2003_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.93058) * POTENCIA(AT_EC; 0.91274)";
			break;
		case "T3_U2007_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.03262) * POTENCIA(AT_EC; 0.89212)";
			break;
		case "T3_U2008_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.88161) * POTENCIA(AT_EC; 1.05241)";
			break;
		case "T3_U2009_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.94028) * POTENCIA(AT_EC; 0.86351)";
			break;
		case "T3_U2010_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.7681) * POTENCIA(AT_EC; 1.13505)";
			break;
		case "T3_U2012_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.90528) * POTENCIA(AT_EC; 1.08307)";
			break;
		case "T3_U2013_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.89801) * POTENCIA(AT_EC; 0.94841)";
			break;
		case "T3_U2101_Pue":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.7342) * POTENCIA(AT_EC; 0.87992)";
			break;
		case "T3_U2103_Pue":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.60676) * POTENCIA(AT_EC; 1.05386)";
			break;
		case "T3_U2105_Pue":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.60091) * POTENCIA(AT_EC; 1.12463)";
			break;
		case "T3_U2107_Pue":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.15782) * POTENCIA(AT_EC; 0.59696)";
			break;
		case "T3_U2108_Pue":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.73333) * POTENCIA(AT_EC; 1.0186)";
			break;
		case "T3_U2301_QRoo":
			ecuacion = " 0.00032 * POTENCIA(DN_EC; 1.56551) * POTENCIA(AT_EC; 0.75743)";
			break;
		case "T3_U2302_QRoo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.50911) * POTENCIA(AT_EC; 1.30006)";
			break;
		case "T3_U2303_QRoo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 2.33248) * POTENCIA(AT_EC; 0.2543)";
			break;
		case "T3_U2304_QRoo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.94313) * POTENCIA(AT_EC; 0.71364)";
			break;
		case "T3_U2901_Tlax":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.84318) * POTENCIA(AT_EC; 0.99726)";
			break;
		case "T3_U2902_Tlax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.97131) * POTENCIA(AT_EC; 1.02665)";
			break;
		case "T3_U3004_Ver":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.9058) * POTENCIA(AT_EC; 1.00125)";
			break;
		case "T3_U3012_Ver":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.90571) * POTENCIA(AT_EC; 1.07229)";
			break;
		case "T3_U3013_Ver":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.86749) * POTENCIA(AT_EC; 1.0438)";
			break;
		case "T3_U801_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.03962) * POTENCIA(AT_EC; 0.90856)";
			break;
		case "T3_U802_Chih":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.73194) * POTENCIA(AT_EC; 1.00662)";
			break;
		case "T3_U804_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.85344) * POTENCIA(AT_EC; 1.04846)";
			break;
		case "T3_U805_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.8472) * POTENCIA(AT_EC; 1.03422)";
			break;
		case "T3_U806_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.95437) * POTENCIA(AT_EC; 1.01606)";
			break;
		case "T3_U807_Chih":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.71722) * POTENCIA(AT_EC; 1.29709)";
			break;
		case "T3_U808_Chih":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.64195) * POTENCIA(AT_EC; 1.39789)";
			break;
		case "T3_U809_Chih":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.5659) * POTENCIA(AT_EC; 1.18256)";
			break;
		case "T3_U810_Chih":
			ecuacion = " 0.00016 * POTENCIA(DN_EC; 1.69491) * POTENCIA(AT_EC; 0.82788)";
			break;
		case "T3_U811_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95077) * POTENCIA(AT_EC; 0.90439)";
			break;
		case "T3z2_Gro":
			ecuacion = " 0.08225 + 0.35644 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "T3z2_Mex":
			ecuacion = " EXP(-1.08569 + 1.916254 * LN(DN_EC / 100) + 1.006618 * LN(AT_EC))";
			break;
		case "T3z3_Tlax":
			ecuacion = " EXP(-1.455580 + 1.699912 * LN(DN_EC / 100) + 1.095361 * LN(AT_EC))";
			break;
		case "T4_Chis":
			ecuacion = " EXP(-10.01137401 + 1.97688779 * LN(DN_EC) + 1.02860759 * LN(AT_EC))";
			break;
		case "T4_QR":
			ecuacion = " EXP(-9.67348922 + 1.86887607 * LN(DN_EC) + 1.01858126 * LN(AT_EC))";
			break;
		case "T4_U1001_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.86782) * POTENCIA(AT_EC; 1.05922)";
			break;
		case "T4_U1002_Dgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.77083) * POTENCIA(AT_EC; 1.09408)";
			break;
		case "T4_U1003_Dgo":
			ecuacion = " 0.00015 * POTENCIA(DN_EC; 1.96228) * POTENCIA(AT_EC; 0.63426)";
			break;
		case "T4_U1004_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.953) * POTENCIA(AT_EC; 0.86033)";
			break;
		case "T4_U1005_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.78423) * POTENCIA(AT_EC; 1.12274)";
			break;
		case "T4_U1006_Dgo":
			ecuacion = " 0.00015 * POTENCIA(DN_EC; 1.81218) * POTENCIA(AT_EC; 0.79456)";
			break;
		case "T4_U1007_Dgo":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.75228) * POTENCIA(AT_EC; 0.87889)";
			break;
		case "T4_U1008_Dgo":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.60507) * POTENCIA(AT_EC; 1.07264)";
			break;
		case "T4_U1009_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.98357) * POTENCIA(AT_EC; 0.86114)";
			break;
		case "T4_U1010_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.06636) * POTENCIA(AT_EC; 0.92022)";
			break;
		case "T4_U1011_Dgo":
			ecuacion = " 0.00024 * POTENCIA(DN_EC; 1.80735) * POTENCIA(AT_EC; 0.64792)";
			break;
		case "T4_U1201_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.92401) * POTENCIA(AT_EC; 0.84751)";
			break;
		case "T4_U1203_Gro":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.84788) * POTENCIA(AT_EC; 1.19236)";
			break;
		case "T4_U1204_Gro":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.67362) * POTENCIA(AT_EC; 1.04466)";
			break;
		case "T4_U1205_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.75262) * POTENCIA(AT_EC; 1.15074)";
			break;
		case "T4_U1302_Hgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.76382) * POTENCIA(AT_EC; 1.01135)";
			break;
		case "T4_U1303_Hgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.63706) * POTENCIA(AT_EC; 1.13283)";
			break;
		case "T4_U1305_Hgo":
			ecuacion = " 0.00013 * POTENCIA(DN_EC; 1.77192) * POTENCIA(AT_EC; 0.73487)";
			break;
		case "T4_U1401_Jal":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.91826) * POTENCIA(AT_EC; 0.9309)";
			break;
		case "T4_U1404_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.98004) * POTENCIA(AT_EC; 0.97395)";
			break;
		case "T4_U1405_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.98391) * POTENCIA(AT_EC; 1.02984)";
			break;
		case "T4_U1406_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.15177) * POTENCIA(AT_EC; 0.73424)";
			break;
		case "T4_U1407_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.9927) * POTENCIA(AT_EC; 1.04053)";
			break;
		case "T4_U1408_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.04036) * POTENCIA(AT_EC; 0.89958)";
			break;
		case "T4_U1410_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.18294) * POTENCIA(AT_EC; 0.75844)";
			break;
		case "T4_U1505_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.73987) * POTENCIA(AT_EC; 1.13272)";
			break;
		case "T4_U1507_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.78612) * POTENCIA(AT_EC; 1.08403)";
			break;
		case "T4_U1508_Mex":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.7334) * POTENCIA(AT_EC; 1.25297)";
			break;
		case "T4_U1509_Mex":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.92093) * POTENCIA(AT_EC; 0.81524)";
			break;
		case "T4_U1510_Mex":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.15224) * POTENCIA(AT_EC; 0.78917)";
			break;
		case "T4_U1601_Mich":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.97983) * POTENCIA(AT_EC; 0.7097)";
			break;
		case "T4_U1603_Mich":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.75905) * POTENCIA(AT_EC; 0.95892)";
			break;
		case "T4_U1604_Mich":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.93266) * POTENCIA(AT_EC; 0.98664)";
			break;
		case "T4_U1605_Mich":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.84363) * POTENCIA(AT_EC; 0.84749)";
			break;
		case "T4_U1607_Mich":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.85724) * POTENCIA(AT_EC; 0.80951)";
			break;
		case "T4_U1608_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.79113) * POTENCIA(AT_EC; 1.0996)";
			break;
		case "T4_U1610_Mich":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 2.00705) * POTENCIA(AT_EC; 0.65663)";
			break;
		case "T4_U2001_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.05828) * POTENCIA(AT_EC; 0.82453)";
			break;
		case "T4_U2003_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.94743) * POTENCIA(AT_EC; 0.94935)";
			break;
		case "T4_U2007_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.0443) * POTENCIA(AT_EC; 0.8419)";
			break;
		case "T4_U2008_Oax":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.93165) * POTENCIA(AT_EC; 1.09039)";
			break;
		case "T4_U2009_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.92053) * POTENCIA(AT_EC; 1.06499)";
			break;
		case "T4_U2010_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.92049) * POTENCIA(AT_EC; 0.92088)";
			break;
		case "T4_U2012_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.99676) * POTENCIA(AT_EC; 0.90793)";
			break;
		case "T4_U2013_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.72578) * POTENCIA(AT_EC; 1.12789)";
			break;
		case "T4_U2101_Pue":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 1.86335) * POTENCIA(AT_EC; 1.30261)";
			break;
		case "T4_U2103_Pue":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.72747) * POTENCIA(AT_EC; 1.07998)";
			break;
		case "T4_U2105_Pue":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.20234) * POTENCIA(AT_EC; 0.66536)";
			break;
		case "T4_U2107_Pue":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.00099) * POTENCIA(AT_EC; 0.9233)";
			break;
		case "T4_U2108_Pue":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.62539) * POTENCIA(AT_EC; 1.31179)";
			break;
		case "T4_U2301_QRoo":
			ecuacion = " 0.00015 * POTENCIA(DN_EC; 1.83611) * POTENCIA(AT_EC; 0.71389)";
			break;
		case "T4_U2302_QRoo":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.65669) * POTENCIA(AT_EC; 0.99668)";
			break;
		case "T4_U2303_QRoo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.03452) * POTENCIA(AT_EC; 0.70758)";
			break;
		case "T4_U2304_QRoo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.84408) * POTENCIA(AT_EC; 0.79741)";
			break;
		case "T4_U2901_Tlax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.94953) * POTENCIA(AT_EC; 0.98298)";
			break;
		case "T4_U2902_Tlax":
			ecuacion = " 0.00024 * POTENCIA(DN_EC; 1.67285) * POTENCIA(AT_EC; 0.75535)";
			break;
		case "T4_U3004_Ver":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.86318) * POTENCIA(AT_EC; 1.02497)";
			break;
		case "T4_U3012_Ver":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.74421) * POTENCIA(AT_EC; 1.17697)";
			break;
		case "T4_U3013_Ver":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 2.04618) * POTENCIA(AT_EC; 0.63953)";
			break;
		case "T4_U801_Chih":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.05674) * POTENCIA(AT_EC; 0.92703)";
			break;
		case "T4_U802_Chih":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.84431) * POTENCIA(AT_EC; 0.88524)";
			break;
		case "T4_U804_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.94367) * POTENCIA(AT_EC; 0.88911)";
			break;
		case "T4_U805_Chih":
			ecuacion = " 0.00001 * POTENCIA(DN_EC; 2.24481) * POTENCIA(AT_EC; 1.11621)";
			break;
		case "T4_U806_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.00968) * POTENCIA(AT_EC; 0.87833)";
			break;
		case "T4_U807_Chih":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.91193) * POTENCIA(AT_EC; 1.13653)";
			break;
		case "T4_U808_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.21213) * POTENCIA(AT_EC; 0.61522)";
			break;
		case "T4_U809_Chih":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.16669) * POTENCIA(AT_EC; 0.94476)";
			break;
		case "T4_U810_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.86155) * POTENCIA(AT_EC; 1.04073)";
			break;
		case "T4_U811_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.89429) * POTENCIA(AT_EC; 1.09183)";
			break;
		case "T4z2_Mex":
			ecuacion = " EXP(-0.69244 + 1.918286 * LN(DN_EC / 100) + 0.817385 * LN(AT_EC))";
			break;
		case "T4z3_Gro":
			ecuacion = " -0.1034 + 0.74185 * DN_EC / 100 + 0.64447 * POTENCIA(DN_EC / 100; 2)+ 0.33173 * AT_EC * POTENCIA(DN_EC / 100; 2)";

			break;
		case "T4z3_Tlax":
			ecuacion = " EXP(-1.503329 + 1.717585 * LN(DN_EC / 100) + 1.084423 * LN(AT_EC))";
			break;
		case "T5_Chis":
			ecuacion = " EXP(-9.843341 + 1.92700277 * LN(DN_EC) + 1.00612327 * LN(AT_EC))";
			break;
		case "T5_QR":
			ecuacion = " EXP(-9.5208709 + 1.85580925 * LN(DN_EC) + 0.96207943 * LN(AT_EC))";
			break;
		case "T5_U1001_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.79279) * POTENCIA(AT_EC; 1.09127)";
			break;
		case "T5_U1002_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.9345) * POTENCIA(AT_EC; 0.999)";
			break;
		case "T5_U1003_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.28511) * POTENCIA(AT_EC; 0.70113)";
			break;
		case "T5_U1004_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.91787) * POTENCIA(AT_EC; 0.98318)";
			break;
		case "T5_U1005_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.12146) * POTENCIA(AT_EC; 0.85116)";
			break;
		case "T5_U1006_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.12756) * POTENCIA(AT_EC; 0.74636)";
			break;
		case "T5_U1007_Dgo":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.69469) * POTENCIA(AT_EC; 1.0923)";
			break;
		case "T5_U1008_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.98731) * POTENCIA(AT_EC; 1.02409)";
			break;
		case "T5_U1009_Dgo":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.10177) * POTENCIA(AT_EC; 1.03353)";
			break;
		case "T5_U1010_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.06636) * POTENCIA(AT_EC; 0.92022)";
			break;
		case "T5_U1011_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.8987) * POTENCIA(AT_EC; 0.9726)";
			break;
		case "T5_U1201y2_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.8982) * POTENCIA(AT_EC; 0.97267)";
			break;
		case "T5_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.87377) * POTENCIA(AT_EC; 0.96465)";
			break;
		case "T5_U1204_Gro":
			ecuacion = " 0.00013 * POTENCIA(DN_EC; 1.82119) * POTENCIA(AT_EC; 0.72735)";
			break;
		case "T5_U1205_Gro":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.80945) * POTENCIA(AT_EC; 1.22993)";
			break;
		case "T5_U1302_Hgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.83083) * POTENCIA(AT_EC; 1.16068)";
			break;
		case "T5_U1303_Hgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.7991) * POTENCIA(AT_EC; 1.03742)";
			break;
		case "T5_U1305_Hgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.80836) * POTENCIA(AT_EC; 1.0436)";
			break;
		case "T5_U1401_Jal":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.94172) * POTENCIA(AT_EC; 0.85746)";
			break;
		case "T5_U1404_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.03387) * POTENCIA(AT_EC; 1.03049)";
			break;
		case "T5_U1405_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.03105) * POTENCIA(AT_EC; 0.87804)";
			break;
		case "T5_U1406_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.11924) * POTENCIA(AT_EC; 0.87698)";
			break;
		case "T5_U1407_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.05296) * POTENCIA(AT_EC; 0.92951)";
			break;
		case "T5_U1408_Jal":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.20463) * POTENCIA(AT_EC; 0.94039)";
			break;
		case "T5_U1410_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.00007) * POTENCIA(AT_EC; 0.85983)";
			break;
		case "T5_U1505_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.85702) * POTENCIA(AT_EC; 0.99713)";
			break;
		case "T5_U1507_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.93742) * POTENCIA(AT_EC; 0.98587)";
			break;
		case "T5_U1508_Mex":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.73648) * POTENCIA(AT_EC; 1.31797)";
			break;
		case "T5_U1509_Mex":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.67308) * POTENCIA(AT_EC; 1.07884)";
			break;
		case "T5_U1510_Mex":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.94146) * POTENCIA(AT_EC; 0.67712)";
			break;
		case "T5_U1601_Mich":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.90867) * POTENCIA(AT_EC; 0.66513)";
			break;
		case "T5_U1603_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.85249) * POTENCIA(AT_EC; 0.99732)";
			break;
		case "T5_U1604_Mich":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.81161) * POTENCIA(AT_EC; 0.97288)";
			break;
		case "T5_U1605_Mich":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.0059) * POTENCIA(AT_EC; 0.93032)";
			break;
		case "T5_U1607_Mich":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.68501) * POTENCIA(AT_EC; 1.08455)";
			break;
		case "T5_U1608_Mich":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.88171) * POTENCIA(AT_EC; 1.01454)";
			break;
		case "T5_U1610_Mich":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.62629) * POTENCIA(AT_EC; 1.26518)";
			break;
		case "T5_U2001_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.92441) * POTENCIA(AT_EC; 0.99975)";
			break;
		case "T5_U2003_Oax":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.83206) * POTENCIA(AT_EC; 0.9082)";
			break;
		case "T5_U2007_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.85794) * POTENCIA(AT_EC; 0.94142)";
			break;
		case "T5_U2008_Oax":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 1.80587) * POTENCIA(AT_EC; 1.36202)";
			break;
		case "T5_U2009_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.85461) * POTENCIA(AT_EC; 1.01316)";
			break;
		case "T5_U2010_Oax":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.79318) * POTENCIA(AT_EC; 0.86415)";
			break;
		case "T5_U2012_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.00896) * POTENCIA(AT_EC; 0.95837)";
			break;
		case "T5_U2013_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.96253) * POTENCIA(AT_EC; 0.81775)";
			break;
		case "T5_U2101_Pue":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.99376) * POTENCIA(AT_EC; 0.90054)";
			break;
		case "T5_U2103_Pue":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 1.91536) * POTENCIA(AT_EC; 1.30782)";
			break;
		case "T5_U2105_Pue":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.9926) * POTENCIA(AT_EC; 0.71239)";
			break;
		case "T5_U2107_Pue":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.99763) * POTENCIA(AT_EC; 0.97406)";
			break;
		case "T5_U2108_Pue":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.83581) * POTENCIA(AT_EC; 1.07277)";
			break;
		case "T5_U2301_QRoo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.39475) * POTENCIA(AT_EC; 0.31482)";
			break;
		case "T5_U2302_QRoo":
			ecuacion = " 0.0002 * POTENCIA(DN_EC; 1.74723) * POTENCIA(AT_EC; 0.70306)";
			break;
		case "T5_U2303_QRoo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.10053) * POTENCIA(AT_EC; 0.60424)";
			break;
		case "T5_U2304_QRoo":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.99384) * POTENCIA(AT_EC; 0.57302)";
			break;
		case "T5_U2901_Tlax":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.87375) * POTENCIA(AT_EC; 0.77347)";
			break;
		case "T5_U2902_Tlax":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.93599) * POTENCIA(AT_EC; 0.79557)";
			break;
		case "T5_U3004_Ver":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.86913) * POTENCIA(AT_EC; 1.03914)";
			break;
		case "T5_U3012_Ver":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.0141) * POTENCIA(AT_EC; 1.13703)";
			break;
		case "T5_U3013_Ver":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.00155) * POTENCIA(AT_EC; 1.06694)";
			break;
		case "T5_U801_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.01262) * POTENCIA(AT_EC; 0.80547)";
			break;
		case "T5_U802_Chih":
			ecuacion = " 0.00015 * POTENCIA(DN_EC; 1.66695) * POTENCIA(AT_EC; 0.90882)";
			break;
		case "T5_U804_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.97318) * POTENCIA(AT_EC; 0.80803)";
			break;
		case "T5_U805_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.87802) * POTENCIA(AT_EC; 0.99663)";
			break;
		case "T5_U806_Chih":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.11528) * POTENCIA(AT_EC; 0.8922)";
			break;
		case "T5_U807_Chih":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.58693) * POTENCIA(AT_EC; 0.98344)";
			break;
		case "T5_U808_Chih":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.91124) * POTENCIA(AT_EC; 0.82715)";
			break;
		case "T5_U809_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.99394) * POTENCIA(AT_EC; 0.85832)";
			break;
		case "T5_U810_Chih":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.01185) * POTENCIA(AT_EC; 0.99089)";
			break;
		case "T5_U811_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.97809) * POTENCIA(AT_EC; 0.81327)";
			break;
		case "T5z2_Mex":
			ecuacion = " EXP(-1.51205 + 1.691204 * LN(DN_EC / 100) + 1.057673 * LN(AT_EC))";
			break;
		case "T5z3_Gro":
			ecuacion = " 0.17106 + 0.33731 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "T6_Chis":
			ecuacion = " EXP(-9.8528306 + 1.93994057 * LN(DN_EC) + 1.0307694 * LN(AT_EC))";
			break;
		case "T6_QR":
			ecuacion = " EXP(-9.75894522 + 1.90722681 * LN(DN_EC) + 1.01257027 * LN(AT_EC))";
			break;
		case "T6_U1001_Dgo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.83358) * POTENCIA(AT_EC; 0.95271)";
			break;
		case "T6_U1002_Dgo":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 2.05071) * POTENCIA(AT_EC; 0.52863)";
			break;
		case "T6_U1004_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.90387) * POTENCIA(AT_EC; 1.03643)";
			break;
		case "T6_U1005_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.99304) * POTENCIA(AT_EC; 0.91377)";
			break;
		case "T6_U1006_Dgo":
			ecuacion = " 0.00021 * POTENCIA(DN_EC; 1.7538) * POTENCIA(AT_EC; 0.71871)";
			break;
		case "T6_U1007_Dgo":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.75424) * POTENCIA(AT_EC; 0.84555)";
			break;
		case "T6_U1008_Dgo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.92699) * POTENCIA(AT_EC; 0.82181)";
			break;
		case "T6_U1009_Dgo":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.09571) * POTENCIA(AT_EC; 1.08267)";
			break;
		case "T6_U1010_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.92908) * POTENCIA(AT_EC; 0.95192)";
			break;
		case "T6_U1011_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.98777) * POTENCIA(AT_EC; 0.94393)";
			break;
		case "T6_U1201_Gro":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.85279) * POTENCIA(AT_EC; 0.86927)";
			break;
		case "T6_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.77047) * POTENCIA(AT_EC; 1.08496)";
			break;
		case "T6_U1204_Gro":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.69058) * POTENCIA(AT_EC; 0.98779)";
			break;
		case "T6_U1205_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.70121) * POTENCIA(AT_EC; 1.07272)";
			break;
		case "T6_U1302_Hgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.79472) * POTENCIA(AT_EC; 0.96587)";
			break;
		case "T6_U1303_Hgo":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.00166) * POTENCIA(AT_EC; 1.04188)";
			break;
		case "T6_U1305_Hgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.95166) * POTENCIA(AT_EC; 0.80933)";
			break;
		case "T6_U1401_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.13895) * POTENCIA(AT_EC; 0.63737)";
			break;
		case "T6_U1404_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.07339) * POTENCIA(AT_EC; 0.78986)";
			break;
		case "T6_U1405_Jal":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.72532) * POTENCIA(AT_EC; 1.08818)";
			break;
		case "T6_U1406_Jal":
			ecuacion = " 0.00019 * POTENCIA(DN_EC; 1.82013) * POTENCIA(AT_EC; 0.65369)";
			break;
		case "T6_U1407_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.83445) * POTENCIA(AT_EC; 1.15501)";
			break;
		case "T6_U1408_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.05024) * POTENCIA(AT_EC; 1.01535)";
			break;
		case "T6_U1410_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.00572) * POTENCIA(AT_EC; 0.98905)";
			break;
		case "T6_U1505_Mex":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.19211) * POTENCIA(AT_EC; 0.82495)";
			break;
		case "T6_U1507_Mex":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.88146) * POTENCIA(AT_EC; 1.15028)";
			break;
		case "T6_U1508_Mex":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.72245) * POTENCIA(AT_EC; 1.06962)";
			break;
		case "T6_U1509_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.03601) * POTENCIA(AT_EC; 0.83725)";
			break;
		case "T6_U1510_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.74105) * POTENCIA(AT_EC; 1.09461)";
			break;
		case "T6_U1601_Mich":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.75214) * POTENCIA(AT_EC; 1.21127)";
			break;
		case "T6_U1603_Mich":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.7689) * POTENCIA(AT_EC; 0.87272)";
			break;
		case "T6_U1604_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.88213) * POTENCIA(AT_EC; 0.99285)";
			break;
		case "T6_U1605_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.84688) * POTENCIA(AT_EC; 1.06104)";
			break;
		case "T6_U1607_Mich":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.75119) * POTENCIA(AT_EC; 1.22266)";
			break;
		case "T6_U1608_Mich":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.80901) * POTENCIA(AT_EC; 0.94962)";
			break;
		case "T6_U1610_Mich":
			ecuacion = " 0.00022 * POTENCIA(DN_EC; 1.71406) * POTENCIA(AT_EC; 0.75067)";
			break;
		case "T6_U2001_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.83655) * POTENCIA(AT_EC; 1.07612)";
			break;
		case "T6_U2003_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.93951) * POTENCIA(AT_EC; 1.00491)";
			break;
		case "T6_U2007_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.75797) * POTENCIA(AT_EC; 1.09047)";
			break;
		case "T6_U2008_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.8555) * POTENCIA(AT_EC; 1.14414)";
			break;
		case "T6_U2009_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.7676) * POTENCIA(AT_EC; 1.10555)";
			break;
		case "T6_U2010_Oax":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.90911) * POTENCIA(AT_EC; 0.74508)";
			break;
		case "T6_U2012_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.87353) * POTENCIA(AT_EC; 1.10471)";
			break;
		case "T6_U2013_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.95294) * POTENCIA(AT_EC; 0.85352)";
			break;
		case "T6_U2101_Pue":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.86727) * POTENCIA(AT_EC; 1.08343)";
			break;
		case "T6_U2103_Pue":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.79927) * POTENCIA(AT_EC; 1.01495)";
			break;
		case "T6_U2105_Pue":
			ecuacion = " 0.00015 * POTENCIA(DN_EC; 1.60556) * POTENCIA(AT_EC; 1.016)";
			break;
		case "T6_U2107_Pue":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.8641) * POTENCIA(AT_EC; 0.9722)";
			break;
		case "T6_U2108_Pue":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.69) * POTENCIA(AT_EC; 1.11055)";
			break;
		case "T6_U2301_QRoo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.18838) * POTENCIA(AT_EC; 1.84537)";
			break;
		case "T6_U2302_QRoo":
			ecuacion = " 0.00037 * POTENCIA(DN_EC; 1.55894) * POTENCIA(AT_EC; 0.71828)";
			break;
		case "T6_U2303_QRoo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.2082) * POTENCIA(AT_EC; 0.66695)";
			break;
		case "T6_U2304_QRoo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.94955) * POTENCIA(AT_EC; 0.69216)";
			break;
		case "T6_U2901_Tlax":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.70944) * POTENCIA(AT_EC; 0.97364)";
			break;
		case "T6_U2902_Tlax":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.76419) * POTENCIA(AT_EC; 1.00432)";
			break;
		case "T6_U3004_Ver":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.62795) * POTENCIA(AT_EC; 1.03726)";
			break;
		case "T6_U3012_Ver":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.7876) * POTENCIA(AT_EC; 1.08113)";
			break;
		case "T6_U3013_Ver":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.99615) * POTENCIA(AT_EC; 0.70608)";
			break;
		case "T6_U801_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.05073) * POTENCIA(AT_EC; 0.88292)";
			break;
		case "T6_U802_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.82537) * POTENCIA(AT_EC; 0.90578)";
			break;
		case "T6_U804_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.98621) * POTENCIA(AT_EC; 0.91734)";
			break;
		case "T6_U805_Chih":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.81878) * POTENCIA(AT_EC; 1.15847)";
			break;
		case "T6_U806_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.8815) * POTENCIA(AT_EC; 0.95474)";
			break;
		case "T6_U807_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.92408) * POTENCIA(AT_EC; 0.81686)";
			break;
		case "T6_U808_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 2.10277) * POTENCIA(AT_EC; 0.60646)";
			break;
		case "T6_U809_Chih":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.85285) * POTENCIA(AT_EC; 0.87639)";
			break;
		case "T6_U810_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95098) * POTENCIA(AT_EC; 0.87901)";
			break;
		case "T6_U811_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.76923) * POTENCIA(AT_EC; 1.02294)";
			break;
		case "T6z3_Mex":
			ecuacion = " EXP(-0.973370 + 1.836140 * LN(DN_EC / 100) + 0.96391 * LN(AT_EC))";
			break;
		case "T6z4_Gro":
			ecuacion = " 0.00645 + 0.00249 * AT_EC + 0.64169 * POTENCIA(DN_EC / 100; 2)+ 0.34731 * AT_EC * POTENCIA(DN_EC / 100; 2);";

			break;
		case "T7_Chis":
			ecuacion = " EXP(-9.86139158 + 1.93994057 * LN(DN_EC) + 1.04126898 * LN(AT_EC))";
			break;
		case "T7_QR":
			ecuacion = " EXP(-9.89061571 + 1.924447818 * LN(DN_EC) + 1.04545276 * LN(AT_EC))";
			break;
		case "T7_U1001_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.14275) * POTENCIA(AT_EC; 0.67224)";
			break;
		case "T7_U1002_Dgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.67614) * POTENCIA(AT_EC; 1.16197)";
			break;
		case "T7_U1003_Dgo":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 2.07845) * POTENCIA(AT_EC; 0.58545)";
			break;
		case "T7_U1004_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.07854) * POTENCIA(AT_EC; 0.82172)";
			break;
		case "T7_U1006_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.8332) * POTENCIA(AT_EC; 1.0539)";
			break;
		case "T7_U1007_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.9062) * POTENCIA(AT_EC; 0.91576)";
			break;
		case "T7_U1008_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.86667) * POTENCIA(AT_EC; 0.94131)";
			break;
		case "T7_U1009_Dgo":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.86245) * POTENCIA(AT_EC; 1.24064)";
			break;
		case "T7_U1010_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.87061) * POTENCIA(AT_EC; 1.02768)";
			break;
		case "T7_U1011_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.03672) * POTENCIA(AT_EC; 0.7885)";
			break;
		case "T7_U1201_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.8853) * POTENCIA(AT_EC; 0.94692)";
			break;
		case "T7_U1203_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.87297) * POTENCIA(AT_EC; 1.05348)";
			break;
		case "T7_U1204_Gro":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.79784) * POTENCIA(AT_EC; 0.84901)";
			break;
		case "T7_U1205_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.93281) * POTENCIA(AT_EC; 0.81579)";
			break;
		case "T7_U1302_Hgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.83348) * POTENCIA(AT_EC; 1.16153)";
			break;
		case "T7_U1303_Hgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.97967) * POTENCIA(AT_EC; 0.97866)";
			break;
		case "T7_U1305_Hgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.96351) * POTENCIA(AT_EC; 0.86282)";
			break;
		case "T7_U1401_Jal":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.94421) * POTENCIA(AT_EC; 0.84086)";
			break;
		case "T7_U1404_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.02326) * POTENCIA(AT_EC; 0.91734)";
			break;
		case "T7_U1405_Jal":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.78585) * POTENCIA(AT_EC; 0.9044)";
			break;
		case "T7_U1406_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.10516) * POTENCIA(AT_EC; 0.63717)";
			break;
		case "T7_U1407_Jal":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.82152) * POTENCIA(AT_EC; 0.88533)";
			break;
		case "T7_U1408_Jal":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.89847) * POTENCIA(AT_EC; 1.06621)";
			break;
		case "T7_U1410_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.86077) * POTENCIA(AT_EC; 1.00268)";
			break;
		case "T7_U1505_Mex":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.87429) * POTENCIA(AT_EC; 0.96011)";
			break;
		case "T7_U1507_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.70925) * POTENCIA(AT_EC; 1.13826)";
			break;
		case "T7_U1508_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.93483) * POTENCIA(AT_EC; 0.92207)";
			break;
		case "T7_U1509_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.72953) * POTENCIA(AT_EC; 1.13649)";
			break;
		case "T7_U1510_Mex":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.71075) * POTENCIA(AT_EC; 0.98634)";
			break;
		case "T7_U1601_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.75454) * POTENCIA(AT_EC; 1.11334)";
			break;
		case "T7_U1603_Mich":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.55198) * POTENCIA(AT_EC; 1.42953)";
			break;
		case "T7_U1604_Mich":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.89152) * POTENCIA(AT_EC; 1.07349)";
			break;
		case "T7_U1605_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.85816) * POTENCIA(AT_EC; 1.03342)";
			break;
		case "T7_U1607_Mich":
			ecuacion = " 0.00015 * POTENCIA(DN_EC; 1.89879) * POTENCIA(AT_EC; 0.63228)";
			break;
		case "T7_U1608_Mich":
			ecuacion = " 0.00022 * POTENCIA(DN_EC; 1.70208) * POTENCIA(AT_EC; 0.69509)";
			break;
		case "T7_U1610_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.96053) * POTENCIA(AT_EC; 0.82385)";
			break;
		case "T7_U2001_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.85811) * POTENCIA(AT_EC; 0.95672)";
			break;
		case "T7_U2003_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.58934) * POTENCIA(AT_EC; 1.26877)";
			break;
		case "T7_U2007_Oax":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.68734) * POTENCIA(AT_EC; 0.95855)";
			break;
		case "T7_U2008_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.80967) * POTENCIA(AT_EC; 1.14314)";
			break;
		case "T7_U2009_Oax":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.73596) * POTENCIA(AT_EC; 0.99685)";
			break;
		case "T7_U2010_Oax":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.82541) * POTENCIA(AT_EC; 0.98782)";
			break;
		case "T7_U2012_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.93095) * POTENCIA(AT_EC; 0.90416)";
			break;
		case "T7_U2013_Oax":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.96541) * POTENCIA(AT_EC; 0.74537)";
			break;
		case "T7_U2101_Pue":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.07684) * POTENCIA(AT_EC; 0.94066)";
			break;
		case "T7_U2103_Pue":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.66246) * POTENCIA(AT_EC; 1.14258)";
			break;
		case "T7_U2105_Pue":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.79224) * POTENCIA(AT_EC; 0.9019)";
			break;
		case "T7_U2107_Pue":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.74619) * POTENCIA(AT_EC; 1.01447)";
			break;
		case "T7_U2108_Pue":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.79793) * POTENCIA(AT_EC; 1.10758)";
			break;
		case "T7_U2301_QRoo":
			ecuacion = " 0.00405 * POTENCIA(DN_EC; 1.23099) * POTENCIA(AT_EC; 0.4354)";
			break;
		case "T7_U2302_QRoo":
			ecuacion = " 0.00263 * POTENCIA(DN_EC; 1.69337) * POTENCIA(AT_EC; -0.09625)";
			break;
		case "T7_U2303_QRoo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.12007) * POTENCIA(AT_EC; 0.76244)";
			break;
		case "T7_U2304_QRoo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.86659) * POTENCIA(AT_EC; 0.78013)";
			break;
		case "T7_U2901_Tlax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.69351) * POTENCIA(AT_EC; 1.22077)";
			break;
		case "T7_U2902_Tlax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.96467) * POTENCIA(AT_EC; 1.00411)";
			break;
		case "T7_U3004_Ver":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.89487) * POTENCIA(AT_EC; 1.14547)";
			break;
		case "T7_U3012_Ver":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.81846) * POTENCIA(AT_EC; 0.89476)";
			break;
		case "T7_U3013_Ver":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.94544) * POTENCIA(AT_EC; 0.90587)";
			break;
		case "T7_U801_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.00347) * POTENCIA(AT_EC; 0.73035)";
			break;
		case "T7_U802_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.02776) * POTENCIA(AT_EC; 0.73638)";
			break;
		case "T7_U804_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.94191) * POTENCIA(AT_EC; 0.93453)";
			break;
		case "T7_U805_Chih":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.08009) * POTENCIA(AT_EC; 1.05183)";
			break;
		case "T7_U806_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.95647) * POTENCIA(AT_EC; 0.9909)";
			break;
		case "T7_U807_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.86394) * POTENCIA(AT_EC; 0.86163)";
			break;
		case "T7_U808_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.93664) * POTENCIA(AT_EC; 0.96329)";
			break;
		case "T7_U809_Chih":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.31772) * POTENCIA(AT_EC; 0.73232)";
			break;
		case "T7_U810_Chih":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.07746) * POTENCIA(AT_EC; 0.92124)";
			break;
		case "T7_U811_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.82139) * POTENCIA(AT_EC; 1.06265)";
			break;
		case "T7z3_Mex":
			ecuacion = " EXP(0.142440 + 2.178814 * LN(DN_EC / 100) + 0.668873 * LN(AT_EC))";
			break;
		case "T7z4_CF_Gro":
			ecuacion = " 0.42577 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "T8_Chis":
			ecuacion = " EXP(-10.1261214 + 1.97537735 * LN(DN_EC) + 1.05085957 * LN(AT_EC))";
			break;
		case "T8_QR":
			ecuacion = " EXP(-9.68220947 + 1.89488929 * LN(DN_EC) + 1.01453225 * LN(AT_EC))";
			break;
		case "T8_U1001_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.08427) * POTENCIA(AT_EC; 0.77182)";
			break;
		case "T8_U1002_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.8233) * POTENCIA(AT_EC; 1.0226)";
			break;
		case "T8_U1003_Dgo":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.82414) * POTENCIA(AT_EC; 0.90378)";
			break;
		case "T8_U1004_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.93976) * POTENCIA(AT_EC; 0.93785)";
			break;
		case "T8_U1006_Dgo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.67915) * POTENCIA(AT_EC; 1.09573)";
			break;
		case "T8_U1007_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.85076) * POTENCIA(AT_EC; 0.91171)";
			break;
		case "T8_U1008_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95316) * POTENCIA(AT_EC; 0.92688)";
			break;
		case "T8_U1009_Dgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.763) * POTENCIA(AT_EC; 1.01337)";
			break;
		case "T8_U1010_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.95287) * POTENCIA(AT_EC; 0.99991)";
			break;
		case "T8_U1011_Dgo":
			ecuacion = " 0.00017 * POTENCIA(DN_EC; 1.68446) * POTENCIA(AT_EC; 0.85398)";
			break;
		case "T8_U1201y2_Gro":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.9271) * POTENCIA(AT_EC; 0.78496)";
			break;
		case "T8_U1203_Gro":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.79849) * POTENCIA(AT_EC; 1.14481)";
			break;
		case "T8_U1204_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.72729) * POTENCIA(AT_EC; 1.05279)";
			break;
		case "T8_U1205_Gro":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.83241) * POTENCIA(AT_EC; 0.93793)";
			break;
		case "T8_U1302_Hgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.77154) * POTENCIA(AT_EC; 1.03384)";
			break;
		case "T8_U1303_Hgo":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.568) * POTENCIA(AT_EC; 1.47853)";
			break;
		case "T8_U1305_Hgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.04243) * POTENCIA(AT_EC; 0.8387)";
			break;
		case "T8_U1401_Jal":
			ecuacion = " 0.00013 * POTENCIA(DN_EC; 1.92204) * POTENCIA(AT_EC; 0.60389)";
			break;
		case "T8_U1404_Jal":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.80515) * POTENCIA(AT_EC; 0.92785)";
			break;
		case "T8_U1405_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.78647) * POTENCIA(AT_EC; 1.05085)";
			break;
		case "T8_U1406_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.05454) * POTENCIA(AT_EC; 0.75375)";
			break;
		case "T8_U1407_Jal":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 1.88391) * POTENCIA(AT_EC; 1.26622)";
			break;
		case "T8_U1408_Jal":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.10035) * POTENCIA(AT_EC; 0.98971)";
			break;
		case "T8_U1410_Jal":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.02775) * POTENCIA(AT_EC; 1.04012)";
			break;
		case "T8_U1505_Mex":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.09133) * POTENCIA(AT_EC; 0.85085)";
			break;
		case "T8_U1507_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.96552) * POTENCIA(AT_EC; 0.87073)";
			break;
		case "T8_U1508_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.94283) * POTENCIA(AT_EC; 0.87185)";
			break;
		case "T8_U1509_Mex":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.57206) * POTENCIA(AT_EC; 1.07944)";
			break;
		case "T8_U1510_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.69791) * POTENCIA(AT_EC; 1.2772)";
			break;
		case "T8_U1601_Mich":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.89245) * POTENCIA(AT_EC; 0.87841)";
			break;
		case "T8_U1603_Mich":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.69419) * POTENCIA(AT_EC; 1.14055)";
			break;
		case "T8_U1604_Mich":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.01624) * POTENCIA(AT_EC; 0.98493)";
			break;
		case "T8_U1605_Mich":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.86601) * POTENCIA(AT_EC; 0.94676)";
			break;
		case "T8_U1607_Mich":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.93562) * POTENCIA(AT_EC; 0.96082)";
			break;
		case "T8_U1608_Mich":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.61568) * POTENCIA(AT_EC; 1.11656)";
			break;
		case "T8_U1610_Mich":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.88384) * POTENCIA(AT_EC; 0.9914)";
			break;
		case "T8_U2001_Oax":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.90189) * POTENCIA(AT_EC; 0.90572)";
			break;
		case "T8_U2003_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.06768) * POTENCIA(AT_EC; 0.7882)";
			break;
		case "T8_U2008_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.96333) * POTENCIA(AT_EC; 0.9002)";
			break;
		case "T8_U2009_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.95642) * POTENCIA(AT_EC; 0.88418)";
			break;
		case "T8_U2010_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.85217) * POTENCIA(AT_EC; 1.0584)";
			break;
		case "T8_U2012_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.9033) * POTENCIA(AT_EC; 1.09949)";
			break;
		case "T8_U2013_Oax":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.96175) * POTENCIA(AT_EC; 1.08365)";
			break;
		case "T8_U2101_Pue":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.84989) * POTENCIA(AT_EC; 0.91831)";
			break;
		case "T8_U2103_Pue":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.71457) * POTENCIA(AT_EC; 1.10603)";
			break;
		case "T8_U2105_Pue":
			ecuacion = " 0.00001 * POTENCIA(DN_EC; 1.94902) * POTENCIA(AT_EC; 1.46857)";
			break;
		case "T8_U2107_Pue":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.63676) * POTENCIA(AT_EC; 1.19026)";
			break;
		case "T8_U2108_Pue":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.75875) * POTENCIA(AT_EC; 0.95559)";
			break;
		case "T8_U2301_QRoo":
			ecuacion = " 0.00015 * POTENCIA(DN_EC; 1.32903) * POTENCIA(AT_EC; 1.22874)";
			break;
		case "T8_U2302_QRoo":
			ecuacion = " 0.00019 * POTENCIA(DN_EC; 1.60581) * POTENCIA(AT_EC; 0.82702)";
			break;
		case "T8_U2303_QRoo":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 2.08405) * POTENCIA(AT_EC; 0.42681)";
			break;
		case "T8_U2304_QRoo":
			ecuacion = " 0.00012 * POTENCIA(DN_EC; 1.77286) * POTENCIA(AT_EC; 0.79622)";
			break;
		case "T8_U2901_Tlax":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 2.06469) * POTENCIA(AT_EC; 0.6856)";
			break;
		case "T8_U2902_Tlax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.76847) * POTENCIA(AT_EC; 1.06584)";
			break;
		case "T8_U3004_Ver":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.8751) * POTENCIA(AT_EC; 0.82694)";
			break;
		case "T8_U3012_Ver":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.67846) * POTENCIA(AT_EC; 1.20297)";
			break;
		case "T8_U3013_Ver":
			ecuacion = " 0.00013 * POTENCIA(DN_EC; 1.87829) * POTENCIA(AT_EC; 0.67938)";
			break;
		case "T8_U801_Chih":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 2.03824) * POTENCIA(AT_EC; 0.49904)";
			break;
		case "T8_U802_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.96171) * POTENCIA(AT_EC; 0.72439)";
			break;
		case "T8_U804_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.03941) * POTENCIA(AT_EC; 0.79305)";
			break;
		case "T8_U805_Chih":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.85388) * POTENCIA(AT_EC; 1.08173)";
			break;
		case "T8_U806_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.05768) * POTENCIA(AT_EC; 0.87192)";
			break;
		case "T8_U807_Chih":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.907) * POTENCIA(AT_EC; 1.14228)";
			break;
		case "T8_U808_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 2.25209) * POTENCIA(AT_EC; 0.4497)";
			break;
		case "T8_U809_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.91096) * POTENCIA(AT_EC; 0.91004)";
			break;
		case "T8_U810_Chih":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 2.13667) * POTENCIA(AT_EC; 0.84587)";
			break;
		case "T8_U811_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.9274) * POTENCIA(AT_EC; 0.94972)";
			break;
		case "T8z4_Mex":
			ecuacion = " EXP(-1.205429 + 1.795256 * LN(DN_EC / 100) + 1.027285 * LN(AT_EC))";
			break;
		case "T8z5_CF_Gro":
			ecuacion = " 0.45503 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "T9_Chis":
			ecuacion = " EXP(-9.73746695 + 1.85643537 * LN(DN_EC) + 1.07354086 * LN(AT_EC))";
			break;
		case "T9_QR":
			ecuacion = " EXP(-9.45811109 + 1.82568462 * LN(DN_EC) + 1.00281859 * LN(AT_EC))";
			break;
		case "T9_U1001_Dgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.87931) * POTENCIA(AT_EC; 0.89222)";
			break;
		case "T9_U1002_Dgo":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.1152) * POTENCIA(AT_EC; 0.83967)";
			break;
		case "T9_U1003_Dgo":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.89463) * POTENCIA(AT_EC; 0.93274)";
			break;
		case "T9_U1004_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.05677) * POTENCIA(AT_EC; 0.82855)";
			break;
		case "T9_U1006_Dgo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.16341) * POTENCIA(AT_EC; 0.7593)";
			break;
		case "T9_U1008_Dgo":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.65948) * POTENCIA(AT_EC; 1.11021)";
			break;
		case "T9_U1009_Dgo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.96532) * POTENCIA(AT_EC; 0.91818)";
			break;
		case "T9_U1010_Dgo":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.57408) * POTENCIA(AT_EC; 1.11268)";
			break;
		case "T9_U1011_Dgo":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.87607) * POTENCIA(AT_EC; 0.95211)";
			break;
		case "T9_U1201_Gro":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.92431) * POTENCIA(AT_EC; 0.78212)";
			break;
		case "T9_U1203_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.70308) * POTENCIA(AT_EC; 1.14845)";
			break;
		case "T9_U1204_Gro":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.66889) * POTENCIA(AT_EC; 1.14331)";
			break;
		case "T9_U1205_Gro":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.77253) * POTENCIA(AT_EC; 1.07939)";
			break;
		case "T9_U1302_Hgo":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.83765) * POTENCIA(AT_EC; 0.77801)";
			break;
		case "T9_U1303_Hgo":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.61561) * POTENCIA(AT_EC; 0.93755)";
			break;
		case "T9_U1401_Jal":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.97638) * POTENCIA(AT_EC; 0.76149)";
			break;
		case "T9_U1404_Jal":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.86893) * POTENCIA(AT_EC; 0.91262)";
			break;
		case "T9_U1405_Jal":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.83039) * POTENCIA(AT_EC; 1.01842)";
			break;
		case "T9_U1406_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.98017) * POTENCIA(AT_EC; 0.92907)";
			break;
		case "T9_U1407_Jal":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.12593) * POTENCIA(AT_EC; 1.05987)";
			break;
		case "T9_U1408_Jal":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 1.96243) * POTENCIA(AT_EC; 1.24572)";
			break;
		case "T9_U1410_Jal":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.01887) * POTENCIA(AT_EC; 0.91994)";
			break;
		case "T9_U1505_Mex":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.86648) * POTENCIA(AT_EC; 0.91409)";
			break;
		case "T9_U1507_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.10051) * POTENCIA(AT_EC; 0.80641)";
			break;
		case "T9_U1508_Mex":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.09745) * POTENCIA(AT_EC; 0.71315)";
			break;
		case "T9_U1509_Mex":
			ecuacion = " 0.00014 * POTENCIA(DN_EC; 1.81149) * POTENCIA(AT_EC; 0.68374)";
			break;
		case "T9_U1510_Mex":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.86169) * POTENCIA(AT_EC; 0.92411)";
			break;
		case "T9_U1603_Mich":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.42549) * POTENCIA(AT_EC; 1.44157)";
			break;
		case "T9_U1604_Mich":
			ecuacion = " 0.00002 * POTENCIA(DN_EC; 2.00811) * POTENCIA(AT_EC; 1.14932)";
			break;
		case "T9_U1605_Mich":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.99993) * POTENCIA(AT_EC; 1.03771)";
			break;
		case "T9_U1607_Mich":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.79239) * POTENCIA(AT_EC; 0.87635)";
			break;
		case "T9_U1610_Mich":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.81443) * POTENCIA(AT_EC; 1.19608)";
			break;
		case "T9_U2001_Oax":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.7569) * POTENCIA(AT_EC; 1.16074)";
			break;
		case "T9_U2003_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.80658) * POTENCIA(AT_EC; 1.0885)";
			break;
		case "T9_U2008_Oax":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 2.13865) * POTENCIA(AT_EC; 0.633)";
			break;
		case "T9_U2009_Oax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.96511) * POTENCIA(AT_EC; 0.86099)";
			break;
		case "T9_U2010_Oax":
			ecuacion = " 0.00003 * POTENCIA(DN_EC; 1.8398) * POTENCIA(AT_EC; 1.25374)";
			break;
		case "T9_U2012_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 2.15717) * POTENCIA(AT_EC; 0.80596)";
			break;
		case "T9_U2013_Oax":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.99493) * POTENCIA(AT_EC; 0.9913)";
			break;
		case "T9_U2101_Pue":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.46801) * POTENCIA(AT_EC; 1.27569)";
			break;
		case "T9_U2105_Pue":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.69493) * POTENCIA(AT_EC; 1.13305)";
			break;
		case "T9_U2107_Pue":
			ecuacion = " 0.00009 * POTENCIA(DN_EC; 1.84484) * POTENCIA(AT_EC; 0.84503)";
			break;
		case "T9_U2108_Pue":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.84824) * POTENCIA(AT_EC; 0.95479)";
			break;
		case "T9_U2301_QRoo":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.51244) * POTENCIA(AT_EC; 1.21242)";
			break;
		case "T9_U2302_QRoo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.81766) * POTENCIA(AT_EC; 1.02093)";
			break;
		case "T9_U2303_QRoo":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 2.12584) * POTENCIA(AT_EC; 0.65387)";
			break;
		case "T9_U2304_QRoo":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.99569) * POTENCIA(AT_EC; 0.68664)";
			break;
		case "T9_U2901_Tlax":
			ecuacion = " 0.00011 * POTENCIA(DN_EC; 1.74687) * POTENCIA(AT_EC; 0.9233)";
			break;
		case "T9_U2902_Tlax":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.89549) * POTENCIA(AT_EC; 0.96322)";
			break;
		case "T9_U3012_Ver":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.96545) * POTENCIA(AT_EC; 0.76485)";
			break;
		case "T9_U3013_Ver":
			ecuacion = " 0.00004 * POTENCIA(DN_EC; 1.94121) * POTENCIA(AT_EC; 0.94878)";
			break;
		case "T9_U801_Chih":
			ecuacion = " 0.00007 * POTENCIA(DN_EC; 1.8919) * POTENCIA(AT_EC; 0.88634)";
			break;
		case "T9_U802_Chih":
			ecuacion = " 0.00008 * POTENCIA(DN_EC; 1.81179) * POTENCIA(AT_EC; 0.96734)";
			break;
		case "T9_U804_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.97959) * POTENCIA(AT_EC; 0.91082)";
			break;
		case "T9_U806_Chih":
			ecuacion = " 0.00005 * POTENCIA(DN_EC; 1.91697) * POTENCIA(AT_EC; 1.02231)";
			break;
		case "T9_U807_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.88895) * POTENCIA(AT_EC; 0.89891)";
			break;
		case "T9_U808_Chih":
			ecuacion = " 0.00059 * POTENCIA(DN_EC; 1.33888) * POTENCIA(AT_EC; 0.70707)";
			break;
		case "T9_U810_Chih":
			ecuacion = " 0.0001 * POTENCIA(DN_EC; 1.89271) * POTENCIA(AT_EC; 0.69004)";
			break;
		case "T9_U811_Chih":
			ecuacion = " 0.00006 * POTENCIA(DN_EC; 1.91145) * POTENCIA(AT_EC; 0.90831)";
			break;
		case "T9z4_Mex":
			ecuacion = " EXP(-0.179960 + 1.962243 * LN(DN_EC / 100) + 0.626109 * LN(AT_EC))";
			break;
		case "T9z5_Gro":
			ecuacion = " 0.00791 + 0.06429 * AT_EC * DN_EC / 100 + 0.26527 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "TA_Hgo":
			ecuacion = " EXP(-9.6214586 + 1.86021863 * LN(DN_EC) + 0.9605345 * LN(AT_EC))";
			break;
		case "TA_NL":
			ecuacion = " EXP(-9.4786656 + 1.77975727 * LN(DN_EC) + 1.07182675 * LN(AT_EC))";
			break;
		case "TA_Qro":
			ecuacion = " EXP(-9.61737291 + 1.84298897 * LN(DN_EC) + 1.0217753 * LN(AT_EC))";
			break;
		case "TA_Tam":
			ecuacion = " EXP(-9.7096827 + 1.89157585 * LN(DN_EC) + 1.04481742 * LN(AT_EC))";
			break;
		case "TA1_Oax":
			ecuacion = " EXP(-9.99696596 + 1.91126433 * LN(DN_EC) + 1.05268932 * LN(AT_EC))";
			break;
		case "TA1z3_Chis":
			ecuacion = " EXP(-9.71600772 + 1.85902896 * LN(DN_EC) + 0.97320372 * LN(AT_EC))";
			break;
		case "TA2_Oax":
			ecuacion = " EXP(-9.68821955 + 1.81948603 * LN(DN_EC) + 1.05886031 * LN(AT_EC))";
			break;
		case "TA2z2_Chis":
			ecuacion = " EXP(-9.78252427 + 1.86824763 * LN(DN_EC) + 1.03889813 * LN(AT_EC))";
			break;
		case "TAz2_SLP":
			ecuacion = " EXP(-9.65632612 + 1.79105716 * LN(DN_EC) + 1.10660162 * LN(AT_EC))";
			break;
		case "TAz2y3_Pue":
			ecuacion = " EXP(-9.73084158 + 1.86001307 * LN(DN_EC) + 0.98860113 * LN(AT_EC))";
			break;
		case "TB_Coah":
			ecuacion = " EXP(-9.8732337 + 1.85512732 * LN(DN_EC) + 1.11426549 * LN(AT_EC))";
			break;
		case "TB_Hgo":
			ecuacion = " EXP(-9.55313303 + 1.86224116 * LN(DN_EC) + 0.96618469 * LN(AT_EC))";
			break;
		case "TB_NL":
			ecuacion = " EXP(-8.72641434 + 1.43032994 * LN(DN_EC) + 1.19541675 * LN(AT_EC))";
			break;
		case "TB_Oax":
			ecuacion = " EXP(-7.14699440 + 1.12704568 * LN(DN_EC) + 1.07202239 * LN(AT_EC))";
			break;
		case "TB_Qro":
			ecuacion = " EXP(-9.75972413 + 1.87784315 * LN(DN_EC) + 1.04315416 * LN(AT_EC))";
			break;
		case "TB_Tam":
			ecuacion = " EXP(-9.81803031 + 1.96214343 * LN(DN_EC) + 0.97915381 * LN(AT_EC))";
			break;
		case "TB1_Oax":
			ecuacion = " EXP(-9.75644044 + 1.82088904 * LN(DN_EC) + 1.03604126 * LN(AT_EC))";
			break;
		case "TB1z2y3_Chis":
			ecuacion = " EXP(-9.25574404 + 1.68436063 * LN(DN_EC) + 1.06130723 * LN(AT_EC))";
			break;
		case "TB2_Oax":
			ecuacion = " EXP(-10.34881812 + 2.02143823 * LN(DN_EC) + 1.03958842 * LN(AT_EC))";
			break;
		case "TB2z2y3_Chis":
			ecuacion = " EXP(-9.58801453 + 1.75592913 * LN(DN_EC) + 1.07004135 * LN(AT_EC))";
			break;
		case "TB3_Oax":
			ecuacion = " EXP(-9.77006304 + 1.87882617 * LN(DN_EC) + 1.02464593 * LN(AT_EC))";
			break;
		case "TB3z2_Chis":
			ecuacion = " EXP(-9.79893158 + 1.87545256 * LN(DN_EC) + 1.00591418 * LN(AT_EC))";
			break;
		case "TB4_Oax":
			ecuacion = " EXP(-9.79021441 + 1.85732519 * LN(DN_EC) + 1.07570774 * LN(AT_EC))";
			break;
		case "TB5_Oax":
			ecuacion = " EXP(-9.09950564 + 1.57672586 * LN(DN_EC) + 1.17621222 * LN(AT_EC))";
			break;
		case "TB6_Oax":
			ecuacion = " EXP(-7.86843737 + 1.14373248 * LN(DN_EC) + 1.26021484 * LN(AT_EC))";
			break;
		case "TB7_Oax":
			ecuacion = " EXP(-9.89882648 + 1.9245184 * LN(DN_EC) + 1.00776177 * LN(AT_EC))";
			break;
		case "TBz2_SLP":
			ecuacion = " EXP(-9.59030142 + 1.81853306 * LN(DN_EC) + 1.04785678 * LN(AT_EC))";
			break;
		case "TBz2y3_Pue":
			ecuacion = " EXP(-9.63495649 + 1.86670523 * LN(DN_EC) + 0.99551381 * LN(AT_EC))";
			break;
		case "TC_Coah":
			ecuacion = " EXP(-9.98378974 + 1.88083718 * LN(DN_EC) + 1.12572772 * LN(AT_EC))";
			break;
		case "TC_Gto":
			ecuacion = " EXP(-9.56168726 + 1.83727218 * LN(DN_EC) + 1.03577031 * LN(AT_EC))";
			break;
		case "TC_Hgo":
			ecuacion = " EXP(-9.91251786 + 1.92329567 * LN(DN_EC) + 1.08023764 * LN(AT_EC))";
			break;
		case "TC_NL":
			ecuacion = " EXP(-9.55262477 + 1.79669337 * LN(DN_EC) + 1.12955055 * LN(AT_EC))";
			break;
		case "TC_Qro":
			ecuacion = " EXP(-9.40012627 + 1.78375769 * LN(DN_EC) + 1.04316093 * LN(AT_EC))";
			break;
		case "TC_Tam":
			ecuacion = " EXP(-9.43439983 + 1.80881432 * LN(DN_EC) + 1.05080385 * LN(AT_EC))";
			break;
		case "TCz2_SLP":
			ecuacion = " EXP(-9.4484666 + 1.77072475 * LN(DN_EC) + 1.03638608 * LN(AT_EC))";
			break;
		case "TD_Coah":
			ecuacion = " EXP(-9.87472826 + 1.87766305 * LN(DN_EC) + 1.00933229 * LN(AT_EC))";
			break;
		case "TD_Gto":
			ecuacion = " EXP(-9.70894854 + 1.90070355 * LN(DN_EC) + 0.96499386 * LN(AT_EC))";
			break;
		case "TD_NL":
			ecuacion = " EXP(-9.8207876 + 1.89180185 * LN(DN_EC) + 1.08048365 * LN(AT_EC))";
			break;
		case "TD_Qro":
			ecuacion = " EXP(-9.13281811 + 1.58527271 * LN(DN_EC) + 1.08374249 * LN(AT_EC))";
			break;
		case "TDz2_SLP":
			ecuacion = " EXP(-8.96131139 + 1.7549128 * LN(DN_EC) + 0.82744403 * LN(AT_EC))";
			break;
		case "TE_Hgo":
			ecuacion = " EXP(-9.54274357 + 1.81010631 * LN(DN_EC) + 1.05764337 * LN(AT_EC))";
			break;
		case "TE_NL":
			ecuacion = " EXP(-9.48686252 + 1.82408096 * LN(DN_EC) + 0.96892639 * LN(AT_EC))";
			break;
		case "TE_Qro":
			ecuacion = " EXP(-9.58311796 + 1.86739198 * LN(DN_EC) + 0.98501687 * LN(AT_EC))";
			break;
		case "TG_Gto":
			ecuacion = " EXP(-9.45552671 + 1.83036294 * LN(DN_EC) + 0.97662425 * LN(AT_EC))";
			break;
		case "TG_Oax":
			ecuacion = " EXP(-9.41218007 + 1.70376160 * LN(DN_EC) + 1.09456111 * LN(AT_EC))";
			break;
		case "TG_Qro":
			ecuacion = " EXP(-9.57166007 + 1.91664005 * LN(DN_EC) + 0.95219556 * LN(AT_EC))";
			break;
		case "TG_Tab":
			ecuacion = " EXP(-9.33289255 + 1.6966435 * LN(DN_EC) + 1.0938266 * LN(AT_EC))";
			break;
		case "TGz1_Pue":
			ecuacion = " EXP(-10.13480453 + 1.96960849 * LN(DN_EC) + 1.01643160 * LN(AT_EC))";
			break;
		case "TGz1y2_Hgo":
			ecuacion = " EXP(-9.65237643 + 1.86211603 * LN(DN_EC) + 0.99010357 * LN(AT_EC))";
			break;
		case "TGz2_Chis":
			ecuacion = " EXP(-9.75761673 + 1.91856550 * LN(DN_EC) + 0.94385612 * LN(AT_EC))";
			break;
		case "TGz2_SLP":
			ecuacion = " EXP(-9.77269977 + 1.9000311 * LN(DN_EC) + 1.00274161 * LN(AT_EC))";
			break;
		case "TGz2y3_Pue":
			ecuacion = " EXP(-9.69246238 + 1.92883177 * LN(DN_EC) + 0.90538711 * LN(AT_EC))";
			break;
		case "TGz3_Hgo":
			ecuacion = " EXP(-9.64826886 + 1.88146054 * LN(DN_EC) + 0.95398007 * LN(AT_EC))";
			break;
		case "TH_Qro":
			ecuacion = " EXP(-9.86428069 + 1.76684334 * LN(DN_EC) + 1.30487505 * LN(AT_EC))";
			break;
		case "THz2y3_Pue":
			ecuacion = " EXP(-9.5017845 + 1.82547723 * LN(DN_EC) + 0.98416091 * LN(AT_EC))";
			break;
		case "TI_Cam":
			ecuacion = " EXP(-9.64583328 + 1.79389367 * LN(DN_EC) + 1.03915044 * LN(AT_EC))";
			break;
		case "TI_Hgo":
			ecuacion = " EXP(-9.8437685 + 1.93425311 * LN(DN_EC) + 0.96703607 * LN(AT_EC))";
			break;
		case "TI_Tab":
			ecuacion = " EXP(-9.19540026 + 1.64562367 * LN(DN_EC) + 1.07521209 * LN(AT_EC))";
			break;
		case "TI_Ver":
			ecuacion = " EXP(-10.05426786 + 2.02274846 * LN(DN_EC) + 1.0027556 * LN(AT_EC))";
			break;
		case "TII_Cam":
			ecuacion = " EXP(-10.06001321 + 1.98160359 * LN(DN_EC) + 1.03695598 * LN(AT_EC))";
			break;
		case "Tii_Hgo":
			ecuacion = " EXP(-9.39858604 + 1.79050244 * LN(DN_EC) + 0.94980089 * LN(AT_EC))";
			break;
		case "TII_Tab":
			ecuacion = " EXP(-9.91798848 + 1.95127527 * LN(DN_EC) + 1.03165275 * LN(AT_EC))";
			break;
		case "TII_Ver":
			ecuacion = " EXP(-10.02137029 + 1.9539179 * LN(DN_EC) + 1.04262341 * LN(AT_EC))";
			break;
		case "TIII_Cam":
			ecuacion = " EXP(-9.53415154 + 1.85980581 * LN(DN_EC) + 0.96989346 * LN(AT_EC))";
			break;
		case "TIII_Tab":
			ecuacion = " EXP(-10.22563374 + 1.92362277 * LN(DN_EC) + 1.14061993 * LN(AT_EC))";
			break;
		case "TIIz1_Pue":
			ecuacion = " EXP(-8.59435094 + 1.58378134 * LN(DN_EC) + 0.984262 * LN(AT_EC))";
			break;
		case "TIV_Cam":
			ecuacion = " EXP(-9.84923104 + 1.91175328 * LN(DN_EC) + 1.04555238 * LN(AT_EC))";
			break;
		case "TIV_Oax":
			ecuacion = " EXP(-10.71439546 + 1.97139127 * LN(DN_EC) + 1.06409203 * LN(AT_EC))";
			break;
		case "TIV_Tab":
			ecuacion = " EXP(-9.82447804 + 1.93162616 * LN(DN_EC) + 1.01919725 * LN(AT_EC))";
			break;
		case "TIV_Ver":
			ecuacion = " EXP(-10.25777326 + 1.98588189 * LN(DN_EC) + 1.1159178 * LN(AT_EC))";
			break;
		case "TIX_Cam":
			ecuacion = " EXP(-9.60981068 + 1.8285472 * LN(DN_EC) + 1.01082458 * LN(AT_EC))";
			break;
		case "TIX_Tab":
			ecuacion = " EXP(-9.80750322 + 1.87831474 * LN(DN_EC) + 1.07425292 * LN(AT_EC))";
			break;
		case "TIX-S_Pue":
			ecuacion = " EXP(-9.57488735 + 1.80188465 * LN(DN_EC) + 1.01577894 * LN(AT_EC))";
			break;
		case "TIz2_Chis":
			ecuacion = " EXP(-9.63738842 + 1.85037732 * LN(DN_EC) + 0.98847468 * LN(AT_EC))";
			break;
		case "TIz2_SLP":
			ecuacion = " EXP(-9.93459231 + 1.788694411 * LN(DN_EC) + 1.18166191 * LN(AT_EC))";
			break;
		case "TPino_BC":
			ecuacion = " 0.01 + 0.388 * POTENCIA(DN_EC / 100; 2) * AT_EC - 1.369 * POTENCIA(DN_EC / 100; 3)";
			break;
		case "TPino_Chih":
			ecuacion = " -0.00018 - 3.39 * POTENCIA(DN_EC / 100; 2) + 4.48 * POTENCIA(DN_EC / 100; 3)+ 0.09 * AT_EC * DN_EC / 100 + 0.33 * POTENCIA(DN_EC / 100; 2) * AT_EC";

			break;
		case "TPino_Dgo":
			ecuacion = " 0.38 + 5.8 * POTENCIA(DN_EC / 100; 2) + 0.018 * AT_EC + 0.21 * POTENCIA(DN_EC / 100; 2) * AT_EC";
			break;
		case "TPino_Jal":
			ecuacion = " 0.02289 + 0.39172 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "TPino_Sin":
			ecuacion = " 0.0187 + 0.699 * POTENCIA(DN_EC / 100; 2) + 0.385 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "TPino_Zac":
			ecuacion = " 0.0036 - 0.49 * POTENCIA(DN_EC / 100; 2) + 0.481 * AT_EC * POTENCIA(DN_EC / 100; 2)";
			break;
		case "TPinoNay":
			ecuacion = " 0.054 + 0.5 * POTENCIA(DN_EC / 100; 2) * AT_EC - 1.35 * POTENCIA(DN_EC / 100; 3)- 0.136 * POTENCIA(DN_EC / 100; 2)";

			break;
		case "TQue_Jal":
			ecuacion = " -0.06016 + 0.60757 * DN_EC / 100 + 0.00031 * AT_EC + 0.33129 * AT_EC * POTENCIA(DN_EC / 100; 2)- 0.03087 * AT_EC * POTENCIA(DN_EC / 100; 3)";

			break;
		case "TQue_Nay":
			ecuacion = " 0.008 + 0.326 * POTENCIA(DN_EC / 100; 2) * AT_EC + 1.439 * POTENCIA(DN_EC / 100; 2)";
			break;
		case "TV_Cam":
			ecuacion = " EXP(-9.88284891 + 1.92178549 * LN(DN_EC) + 1.04714889 * LN(AT_EC))";
			break;
		case "TV_Oax":
			ecuacion = " EXP(-10.06787497 + 2.0005528 * LN(DN_EC) + 0.99031834 * LN(AT_EC))";
			break;
		case "TV_Tab":
			ecuacion = " EXP(-10.02137029 + 1.95391791 * LN(DN_EC) + 1.04262341 * LN(AT_EC))";
			break;
		case "TVI_Cam":
			ecuacion = " EXP(-10.09141259 + 1.93246219 * LN(DN_EC) + 1.06194865 * LN(AT_EC))";
			break;
		case "TVI_Oax":
			ecuacion = " EXP(-10.07005937 + 2.00090768 * LN(DN_EC) + 0.98373378 * LN(AT_EC))";
			break;
		case "TVI_Tab":
			ecuacion = " EXP(-9.98279857 + 1.97250941 * LN(DN_EC) + 1.03206162 * LN(AT_EC))";
			break;
		case "TVI_Ver":
			ecuacion = " EXP(-10.04355155 + 1.98934236 * LN(DN_EC) + 1.01544747 * LN(AT_EC))";
			break;
		case "TVII_Cam":
			ecuacion = " EXP(-9.98357915 + 1.95005045 * LN(DN_EC) + 1.05153755 * LN(AT_EC))";
			break;
		case "TVII_Tab":
			ecuacion = " EXP(-9.60249233 + 1.83229453 * LN(DN_EC) + 1.07410993 * LN(AT_EC))";
			break;
		case "TVII_Ver":
			ecuacion = " EXP(-9.72017711 + 1.9079834 * LN(DN_EC) + 0.98125131 * LN(AT_EC))";
			break;
		case "TVIII_Cam":
			ecuacion = " EXP(-8.81312542 + 1.56449274 * LN(DN_EC) + 1.08361129 * LN(AT_EC))";
			break;
		case "TVIII_Tab":
			ecuacion = " EXP(-9.75226675 + 1.94677823 * LN(DN_EC) + 1.00656774 * LN(AT_EC))";
			break;
		case "TVIII_Ver":
			ecuacion = " EXP(-10.07263436 + 1.96992797 * LN(DN_EC) + 1.01423627 * LN(AT_EC))";
			break;
		case "Tvol1_QR":
			ecuacion = " EXP(-9.74143096 + 1.95019123 * LN(DN_EC) + 0.95525758 * LN(AT_EC))";
			break;
		case "Tvol10_QR":
			ecuacion = " EXP(-9.71122198 + 1.8428147 * LN(DN_EC) + 1.05795417 * LN(AT_EC))";
			break;
		case "Tvol11_QR":
			ecuacion = " EXP(-9.88332368 + 1.9131735 * LN(DN_EC) + 1.07391062 * LN(AT_EC))";
			break;
		case "TX_Cam":
			ecuacion = " EXP(-9.5643815 + 1.82330416 * LN(DN_EC) + 1.01741981 * LN(AT_EC))";
			break;
		case "TX_Hgo":
			ecuacion = " EXP(-9.78340833 + 1.83372702 * LN(DN_EC) + 1.07704058 * LN(AT_EC))";
			break;
		case "TX_Tab":
			ecuacion = " EXP(-9.83318819 + 1.91517713 * LN(DN_EC) + 1.03835777 * LN(AT_EC))";
			break;
		case "TXI_Cam":
			ecuacion = " EXP(-9.52774573 + 1.76329569 * LN(DN_EC) + 1.08168791 * LN(AT_EC))";
			break;
		case "TXI_Tab":
			ecuacion = " EXP(-9.96832495 + 1.89779841 * LN(DN_EC) + 1.08188905 * LN(AT_EC))";
			break;
		case "TXII_cam":
			ecuacion = " EXP(-9.83322527 + 1.92412457 * LN(DN_EC) + 1.00970142 * LN(AT_EC))";
			break;
		case "TXII_Tab":
			ecuacion = " EXP(-9.20446857 + 1.70136976 * LN(DN_EC) + 1.07521396 * LN(AT_EC))";
			break;
		case "TXII_Ver":
			ecuacion = " EXP(-9.83318819 + 1.91517715 * LN(DN_EC) + 1.0383577 * LN(AT_EC))";
			break;
		case "TXIII_Cam":
			ecuacion = " EXP(-9.41737421 + 1.76385327 * LN(DN_EC) + 1.04067089 * LN(AT_EC))";
			break;
		case "TXIII_Tab":
			ecuacion = " EXP(-9.76784179 + 1.87539164 * LN(DN_EC) + 1.05141081 * LN(AT_EC))";
			break;
		case "TXIV_Oax":
			ecuacion = " EXP(-9.98262558 + 1.94239763 * LN(DN_EC) + 1.02228707 * LN(AT_EC))";
			break;
		case "TXV_Ver":
			ecuacion = " EXP(-9.85922486 + 1.93825951 * LN(DN_EC) + 1.01557735 * LN(AT_EC))";
			break;
		case "TXVI_Ver":
			ecuacion = " EXP(-9.7891527 + 1.88887745 * LN(DN_EC) + 1.04457398 * LN(AT_EC))";
			break;
		case "TXVIz1_Pue":
			ecuacion = " EXP(-9.40152632 + 1.71919595 * LN(DN_EC) + 1.07447205 * LN(AT_EC))";
			break;
		default:
			ecuacion = " 0.00000000000000000000000000000000000000000000000000000000000000000000000000000000009";
			break;
		}
		return ecuacion;
	}

	public int getIteradorFueraRangos() {
		return iteradorFueraRangos;
	}

	public void setIteradorFueraRangos(int iteradorFueraRangos) {
		this.iteradorFueraRangos = iteradorFueraRangos;
	}

	public int getIteradorDentroRangos() {
		return iteradorDentroRangos;
	}

	public void setIteradorDentroRangos(int iteradorDentroRangos) {
		this.iteradorDentroRangos = iteradorDentroRangos;
	}

	public int getIteradorNoFormulaRango() {
		return iteradorNoFormulaRango;
	}

	public void setIteradorNoFormulaRango(int iteradorNoFormulaRango) {
		this.iteradorNoFormulaRango = iteradorNoFormulaRango;
	}

}