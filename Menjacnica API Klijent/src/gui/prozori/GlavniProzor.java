package gui.prozori;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import domenske_klase.Drzava;
import gui.kontroler.GUIKontroler;

import javax.swing.border.LineBorder;

import java.util.ArrayList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GlavniProzor extends JFrame {

	private JPanel contentPane;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JComboBox comboBoxFrom;
	private JComboBox comboBoxTo;
	private JLabel lblIznoFrom;
	private JLabel lblIznosTo;
	private JTextField textFieldFrom;
	private JTextField textFieldTo;
	private JButton btnKonvertuj;
	private ArrayList<Drzava> drzave;

	/**
	 * Create the frame.
	 */
	public GlavniProzor() {

		drzave = GUIKontroler.menjacnica.vratiDrzave();
		setResizable(false);
		setTitle("Menjacnica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblFrom());
		contentPane.add(getLblTo());
		contentPane.add(getComboBoxFrom());
		contentPane.add(getComboBoxTo());
		contentPane.add(getLblIznoFrom());
		contentPane.add(getLblIznosTo());
		contentPane.add(getTextFieldFrom());
		contentPane.add(getTextFieldTo());
		contentPane.add(getBtnKonvertuj());

	}

	private JLabel getLblFrom() {
		if (lblFrom == null) {
			lblFrom = new JLabel("Iz valute zemlje:");
			lblFrom.setBounds(32, 68, 134, 15);
		}
		return lblFrom;
	}

	private JLabel getLblTo() {
		if (lblTo == null) {
			lblTo = new JLabel("U valute zemlje:");
			lblTo.setBounds(251, 68, 134, 15);
		}
		return lblTo;
	}

	private JComboBox getComboBoxFrom() {

		if (comboBoxFrom == null) {

			comboBoxFrom = new JComboBox(drzave.toArray());

			comboBoxFrom.setBounds(32, 95, 172, 24);
		}
		return comboBoxFrom;
	}

	private JComboBox getComboBoxTo() {
		if (comboBoxTo == null) {
			comboBoxTo = new JComboBox(drzave.toArray());

			comboBoxTo.setBounds(251, 95, 172, 24);
		}
		return comboBoxTo;
	}

	private JLabel getLblIznoFrom() {
		if (lblIznoFrom == null) {
			lblIznoFrom = new JLabel("Iznos:");
			lblIznoFrom.setBounds(57, 155, 114, 15);
		}
		return lblIznoFrom;
	}

	private JLabel getLblIznosTo() {
		if (lblIznosTo == null) {
			lblIznosTo = new JLabel("Iznos:");
			lblIznosTo.setBounds(269, 155, 110, 15);
		}
		return lblIznosTo;
	}

	private JTextField getTextFieldFrom() {
		if (textFieldFrom == null) {
			textFieldFrom = new JTextField();
			textFieldFrom.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {

				}

				public void removeUpdate(DocumentEvent e) {
					if (textFieldFrom.getText().isEmpty())
						btnKonvertuj.setEnabled(false);
				}

				public void insertUpdate(DocumentEvent e) {
					btnKonvertuj.setEnabled(true);
				}
			});
			textFieldFrom.setBounds(57, 183, 114, 19);
			textFieldFrom.setColumns(10);
		}
		return textFieldFrom;
	}

	private JTextField getTextFieldTo() {
		if (textFieldTo == null) {
			textFieldTo = new JTextField();
			textFieldTo.setBounds(265, 183, 114, 19);
			textFieldTo.setColumns(10);
		}
		return textFieldTo;
	}

	private JButton getBtnKonvertuj() {
		if (btnKonvertuj == null) {
			btnKonvertuj = new JButton("Konvertuj");

			btnKonvertuj.setEnabled(false);
			btnKonvertuj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int from = comboBoxFrom.getSelectedIndex();
					int to = comboBoxTo.getSelectedIndex();
					String valFrom = drzave.get(from).getCurrencyId();
					String valTo = drzave.get(to).getCurrencyId();
					Double iznosFrom = Double.parseDouble(textFieldFrom.getText());
					Double kurs = 0.0;
					try {
						kurs = GUIKontroler.menjacnica.vratiKurs(valFrom, valTo);
						Double iznosTo = iznosFrom * kurs;
						textFieldTo.setText("" + iznosTo);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Ne postoje podaci o konverziji izmedju datih valuta.",
								"Greska", JOptionPane.ERROR_MESSAGE);
					} finally {
						GUIKontroler.menjacnica.sacuvajLog(valFrom, valTo, kurs);
					}

				}
			});

			btnKonvertuj.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
			btnKonvertuj.setBackground(Color.WHITE);
			btnKonvertuj.setBounds(157, 224, 117, 25);
		}
		return btnKonvertuj;
	}
	
}
