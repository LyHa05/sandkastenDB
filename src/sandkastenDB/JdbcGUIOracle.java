package sandkastenDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

// Original Source: Jean-René Thies
// Veränderungen: Thomas Thiel-Clemen

//Import der benötigten Packages
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JTable;

public class JdbcGUIOracle extends javax.swing.JFrame {

	// Variablendeklaration
	private javax.swing.JScrollPane scrollTabelle;

	public JdbcGUIOracle() {
		initComponents();
		aktualisiereTabelle();
	} // JdbcGUI()

	private void initComponents() {
		scrollTabelle = new javax.swing.JScrollPane();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		getContentPane().add(scrollTabelle, java.awt.BorderLayout.CENTER);

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 800) / 2, (screenSize.height - 200) / 2, 800, 200);
	} // initComponents()

	private void aktualisiereTabelle() {
		Vector<String> columnNames = new Vector<String>();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		try {
			// Abfrage definieren
			String query = "SELECT * FROM Professoren";
			// Aufruf des Treibers
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Datenbankverbindung herstellen
			Connection con = DriverManager.getConnection(
					"jdbc:oracle:thin:@oracle.informatik.haw-hamburg.de:1521:inf09", "YOUR_ACCOUNT", "YOUR_PW"); // Kennung
																													// eintragen
			System.out.println("Connection Successful ");
			Statement stmt = con.createStatement();
			ResultSet rst = stmt.executeQuery(query);
			ResultSetMetaData md = rst.getMetaData();
			// Anzahl der Spalten ermitteln
			int columns = md.getColumnCount();
			// Spaltennamen ermitteln und in columnNames speichern
			for (int i = 1; i <= columns; i++) {
				String colname = md.getColumnName(i);
				columnNames.addElement(colname);
			}
			// Zeileninhalt ermitteln
			while (rst.next()) {
				Vector<Object> row = new Vector<Object>(columns);
				for (int i = 1; i <= columns; i++) {
					row.addElement(rst.getObject(i));
				}
				data.addElement(row);
			}
			rst.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Tabelle erzeugen
		JTable table = new JTable(data, columnNames);
		scrollTabelle.setViewportView(table);
	} // aktualisiereTabelle()

	public static void main(String args[]) {
		new JdbcGUIOracle().setVisible(true);
	} // main()

} // Class JdbcGUI
