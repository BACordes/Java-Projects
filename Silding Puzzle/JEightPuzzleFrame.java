import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing. * ;

public class JEightPuzzleFrame extends JFrame implements ActionListener {
	//title and source files
	private String Source;
	private String Title;
	private static int ShuffleCount = 0;

	// creates Panel
	private JPanel centerPanel = new JPanel();

	//intialize buttons
	private JButton[] PuzzleButtonA = new JButton[9];
	List Collections = new List(9);

	//Creates a Container for panel
	private Container mainPanel = this.getContentPane();
	private int[][] pos;
	private Image source,
	image;

	int width,
	height;

	//Shuffles the array around and moves the buttons when time to shuffle
	public static < T > void shuffle(T[] array) {
		Random random = new Random();

		for (int i = array.length - 1; i >= 1; --i) {
			int j = random.nextInt(i);
			T temp = array[i];
			array[i] = array[j];
			array[j] = temp;

		}
	}
	//Puzzle object
	public JEightPuzzleFrame(String Title, String Source) {
		this.Source = Source;
		this.Title = Title;
		setSize(width, height);
		setTitle(Title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		set();
	}

	public void set() {
		BufferedImage image1 = null;
		try {
			image1 = ImageIO.read(new File(Source));
		} catch(IOException e) {
			System.err.println("Image not found");
			System.exit(1);
		}
		//setting the positions to be able to move them
		pos = new int[][] {
			{
				0,
				1,
				2
			},
			{
				3,
				4,
				5
			},
			{
				6,
				7,
				8
			}
		};
		//Extracts the icons and sets height and width
		ImageIcon Puzimg = new ImageIcon(Source);
		source = Puzimg.getImage();

		width = Puzimg.getIconWidth();
		height = Puzimg.getIconHeight();
		setSize(width, height);

		/** Creates button and sets icon */
		for (int i = 0; i < PuzzleButtonA.length - 1; i++) {
			image = createImage(new FilteredImageSource(source.getSource(), new CropImageFilter((i % 3) * width / 3, (i / 3) * height / 3, width / 3, height / 3)));

			PuzzleButtonA[i + 1] = new JButton();
			PuzzleButtonA[i + 1].setIcon(new ImageIcon(image));
		}
		PuzzleButtonA[0] = new JButton();
		//adds Panel
		centerPanel.setLayout(new GridLayout(3, 3, 0, 0));

		/** Add actionListeners to buttons */
		for (int i = 1; i < 9; i++) {
			PuzzleButtonA[i].addActionListener(this);
		}

		addButtons();
		mainPanel.revalidate();
		mainPanel.add(centerPanel);
	}

	public void addButtons() {

		//Addes the buttons the first time a certain way for easy solve
		centerPanel.removeAll();
		if (ShuffleCount == 0) {
			centerPanel.add(PuzzleButtonA[0]);
			centerPanel.add(PuzzleButtonA[1]);
			centerPanel.add(PuzzleButtonA[2]);
			centerPanel.add(PuzzleButtonA[5]);
			centerPanel.add(PuzzleButtonA[6]);
			centerPanel.add(PuzzleButtonA[3]);
			centerPanel.add(PuzzleButtonA[4]);
			centerPanel.add(PuzzleButtonA[7]);
			centerPanel.add(PuzzleButtonA[8]);
		}
		//After the first solve it randoms the buttons for the shuffle after the win
		if (ShuffleCount == 1) {
			shuffle(PuzzleButtonA);
			for (int j = 8; j >= 0; j--) {
				centerPanel.add(PuzzleButtonA[j]);
			}
		}
		centerPanel.revalidate();

	}

	public boolean PuzzleDone() {
		{ //Checks to see if the puzzle is finish by the cords
			if ((PuzzleButtonA[1].getY() == PuzzleButtonA[2].getY() && PuzzleButtonA[2].getY() == PuzzleButtonA[3].getY()) && (PuzzleButtonA[4].getY() == PuzzleButtonA[5].getY() && PuzzleButtonA[5].getY() == PuzzleButtonA[6].getY()) && (PuzzleButtonA[7].getY() == PuzzleButtonA[8].getY())) {
				if ((PuzzleButtonA[1].getX() == PuzzleButtonA[4].getX() && PuzzleButtonA[4].getX() == PuzzleButtonA[7].getX()) && (PuzzleButtonA[2].getX() == PuzzleButtonA[5].getX() && PuzzleButtonA[5].getX() == PuzzleButtonA[8].getX()))

				{
					return true;
				}
			}

			return false;
		}
	}

	public static void main(String[] args) {
		JEightPuzzleFrame puzzle = new JEightPuzzleFrame("JEightPuzzleFrame", "FGCU_logo.png");
		puzzle.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {

		//Moves the butotns and then removes and adds the 0 then adds the next on by cords.
		JButton button = (JButton) ae.getSource();
		Dimension size = button.getSize();
		int emptyX = PuzzleButtonA[0].getX();
		int emptyY = PuzzleButtonA[0].getY();
		int buttonX = button.getX();
		int buttonY = button.getY();
		int buttonPosX = buttonX / size.width;
		int buttonPosY = buttonY / size.height;
		int buttonIndex = pos[buttonPosY][buttonPosX];
		if (emptyX == buttonX && (emptyY - buttonY) == size.height) {
			int labelIndex = buttonIndex + 3;
			centerPanel.remove(buttonIndex);
			centerPanel.add(PuzzleButtonA[0], buttonIndex);
			centerPanel.add(button, labelIndex);
			centerPanel.validate();

		}
		if (emptyX == buttonX && (emptyY - buttonY) == -size.height) {
			int labelIndex = buttonIndex - 3;
			centerPanel.remove(labelIndex);
			centerPanel.add(button, labelIndex);
			centerPanel.add(PuzzleButtonA[0], buttonIndex);
			centerPanel.validate();

		}
		if (emptyY == buttonY && (emptyX - buttonX) == size.width) {
			int labelIndex = buttonIndex + 1;
			centerPanel.remove(buttonIndex);
			centerPanel.add(PuzzleButtonA[0], buttonIndex);
			centerPanel.add(button, labelIndex);
			centerPanel.validate();

		}
		if (emptyY == buttonY && (emptyX - buttonX) == -size.width) {
			int labelIndex = buttonIndex - 1;
			centerPanel.remove(buttonIndex);
			centerPanel.add(PuzzleButtonA[0], labelIndex);
			centerPanel.add(button, labelIndex);
			centerPanel.validate();
		}
		// If the puzzle is done it changes shuffle count to 1 and shuffles everytime after the first time.
		if (PuzzleDone()) {
			JOptionPane.showMessageDialog(null, "You Solved the puzzle, Click ok to Shuffle!");
			ShuffleCount = 1;
			set();
		}

	}}