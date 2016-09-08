package converter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import converter.SpriteConverter;

public class UI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UI frame;
	private JFileChooser imageFC = new JFileChooser();
	private File file = null;

	public UI() throws HeadlessException {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createGUI();
			}
		});
	}

	private void createGUI() {
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Bilder (*.jpg, *.jpeg, *.gif, *.png, *.bmp)",
				"gif", "png", "jpg", "jpeg", "bmp");
		imageFC.setFileFilter(imageFilter);

		JPanel input = new JPanel(new GridLayout(2, 1));
		JPanel asset = new JPanel(new BorderLayout());
		JTextField assetSize = new JTextField();
		assetSize.setColumns(5);
		;
		JLabel sizeLabel = new JLabel("Asset Größe (px):");
		sizeLabel.setLabelFor(assetSize);
		asset.add(sizeLabel, BorderLayout.WEST);
		asset.add(assetSize, BorderLayout.EAST);
		JPanel sprite = new JPanel(new BorderLayout());
		JLabel spriteLabel = new JLabel("Sprite:");
		JButton open = new JButton("Öffnen");
		open.setName("open");
		open.addActionListener(this);
		spriteLabel.setLabelFor(open);
		sprite.add(spriteLabel, BorderLayout.WEST);
		sprite.add(open, BorderLayout.EAST);
		input.add(asset);
		input.add(sprite);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton convert = new JButton("Konvertieren");
		convert.addActionListener(this);
		convert.setName("convert");
		JButton close = new JButton("Schließen");
		close.addActionListener(this);
		close.setName("close");
		buttons.add(convert);
		buttons.add(close);

		JPanel main = new JPanel();
		main.setBorder(new EmptyBorder(10, 10, 10, 10));
		main.setLayout(new BorderLayout());
		main.add(input, BorderLayout.CENTER);
		main.add(buttons, BorderLayout.SOUTH);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setTitle("Sprites konvertieren");
		frame.setSize(400, 150);
		frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.add(main);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		frame = new UI();
	} // end of main method

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = ((Component) e.getSource()).getName();
		switch (name) {
		case "open":
			int retVal = imageFC.showOpenDialog(this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				file = imageFC.getSelectedFile();
			}
			break;

		case "convert":
			if (file != null) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    int returnVal = chooser.showSaveDialog(this);
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	convertSprite(file, chooser.getSelectedFile());
				}				
			} else {
				JOptionPane.showMessageDialog(this, "Keine Sprite ausgewählt.");
			}
			break;

		case "close":
			setVisible(false);
			dispose();
			break;
		}
	}
	
	public void convertSprite(File in, File dest){
		List failed = new List();
		try {
			SpriteConverter sc = new SpriteConverter(ImageIO.read(in));
			failed = sc.convert(dest);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Fehler beim Lesen der Sprite.");
			e.printStackTrace();
		};
		if (failed.getItemCount() == 0) {
			JOptionPane.showMessageDialog(this, "Sprite konvertiert.");
		} else {
			String out = "Fehler beim Schreiben der folgenden Dateien: \n";
			for (int i = 0; i < failed.getItemCount(); i++) {
				out += failed.getItem(i);
			}
			JOptionPane.showMessageDialog(this, out);
		}
	}

}
