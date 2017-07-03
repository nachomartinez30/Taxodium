import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.StringTokenizer;
import java.lang.Math;

public class PrincipalCalculoVolumen {

//	public static void main(String[] args) {
//
//		PrincipalCalculoVolumen principal = new PrincipalCalculoVolumen();
//		principal.recoleccionDatos();
//
//	}

	public void recoleccionDatos() {
		{
			String rutaArchivo = "EcuacionasignadaDNyalturaTotalCORREGIDO.txt";
			String cgl_sit_arb, ecuacion, nivel;
			double diametroNormal, alturaTotal, resultado = 0;

			// String numeros = "1,2,3,4,5,6";sustituir por tabop
			try {
				// Abrimos el archivo
				FileReader leerarchivo = new FileReader(rutaArchivo);
				BufferedReader buffer = new BufferedReader(leerarchivo);
				String linea;

				// archivo de escritura
				File f;
				f = new File(System.getProperty("user.home") + "\\Desktop\\ResultadoVolumenes.txt");
				FileWriter w = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(w);
				PrintWriter wr = new PrintWriter(bw);
				wr.write("ID,DIAMETRO,ALTURA,ECUACION=,RESULTADO,NIVEL\n");// escribimos
																			// en
																			// el
																			// archivo

				// Leer el archivo linea por linea

				while ((linea = buffer.readLine()) != null) {

					StringTokenizer tokensaux = new StringTokenizer(linea, ",");

					while (tokensaux.hasMoreTokens()) {

						cgl_sit_arb = tokensaux.nextToken();
						diametroNormal = Double.parseDouble(tokensaux.nextToken());
						alturaTotal = Double.parseDouble(tokensaux.nextToken());
						ecuacion = tokensaux.nextToken();
						nivel = tokensaux.nextToken();
						// System.out.println(cgl_sit_arb+"|"+diametroNormal+"|"+alturaTotal+"|"+ecuacion+"|"+nivel);

						switch (ecuacion) {

						case "TPino_Zac":
							resultado = 0.0036 - 0.49 * Math.pow(diametroNormal / 100, 2) + 0.481 * alturaTotal * Math.pow(diametroNormal / 100, 2);
							break;

						default:
							resultado = 0.0000000000000000000000000000000000000000000001;
							break;

						}/* final switch */
						System.out.println(cgl_sit_arb + "\t" + resultado);

						wr.append(cgl_sit_arb + "," + "\n"); // concatenamos en
																// el archivo
																// sin borrar lo
																// existente

						// ahora cerramos los flujos de canales de datos, al
						// cerrarlos el archivo quedará guardado con información
						// escrita

						// de no hacerlo no se escribirá nada en el archivo

					}
					// Cerramos el archivo

				}
				wr.close();
				bw.close();
				buffer.close();

			} catch (Exception e) { // Catch de excepciones
				System.err.println("Ocurrio un error: " + e.getMessage());
			}

		}
	}

}// Final Clase
