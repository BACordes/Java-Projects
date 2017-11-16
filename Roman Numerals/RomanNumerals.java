import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class RomanNumerals extends JFrame {

    private final JLabel arabic;
    private final JTextField arabicText;
    private static String arabicS;
    private final JLabel roman;
    private final JTextField romanText;
    private static String romanS;
    private static String[] rLetters = new String[] {
        "I","IV","V","IX","X",
        "XL","L","XC","C","CD",
        "D","CM","M"
    };
    private static int[] Nums = new int[] {
        1, 4, 5, 9, 10, 40, 50, 90, 100, 400,
        500, 900, 1000
    };

    public RomanNumerals(String Title) {
        super(Title);

        GridLayout panel = new GridLayout(2, 2);
        setLayout(panel);

        //Creates everything for the gui
        arabic = new JLabel("Arabic Numural");
        roman = new JLabel("Roman Numural");
        arabicText = new JTextField();
        romanText = new JTextField();
        	//adds everything to gui
        add(arabic);
        add(arabicText);
        add(roman);
        add(romanText);
        
        //creates a listener for both text fields
        Handler handler = new Handler();
        arabicText.addKeyListener(handler);
        romanText.addKeyListener(handler);

    }

   //Handles all keys press in any text field
    private class Handler implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }
        @Override
        public void keyPressed(KeyEvent e) {

        }
        @Override
        public void keyReleased(KeyEvent e) {
            char keyChar = e.getKeyChar();

            if (e.getSource() == arabicText) {

                //Checks if the source is in the arabictext field and what key is pressed.

                if ((keyChar == '0') || (keyChar == '1') || (keyChar == '2') ||
                    (keyChar == '3') || (keyChar == '4') || (keyChar == '5') ||
                    (keyChar == '6') || (keyChar == '7') || (keyChar == '8') ||
                    (keyChar == '9')) {

                    if ((Integer.parseInt(arabicText.getText()) < 4000) && (Integer.parseInt(arabicText.getText()) > 0)) {
                        arabicS = arabicText.getText();
                        int aInt = Integer.parseInt(arabicS);
                        romanText.setText("");
                        romanS = intToRoman(aInt);
                        romanText.setText(romanS);
                    } else {
                        arabicText.setText(arabicText.getText().substring(0, arabicText.getText().length() - 1));
                    }

                }
              //if delete button was pressed it deletes it and does re-calculates the arabic text field.
                else if ((e.getKeyCode() == KeyEvent.VK_DELETE) ||
                    (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {

                    if (arabicText.getText().equals("")) {
                        romanText.setText("");
                    } else {
                        arabicS = arabicText.getText();
                        int aInt = Integer.parseInt(arabicS);

                        romanText.setText("");
                        romanS = intToRoman(aInt);
                        romanText.setText(romanS);
                    }
                }
                // If a invalid key is pressed it deletes it.
                else {
                    arabicS = arabicText.getText();
                    String KS = Character.toString(keyChar);
                    arabicS = arabicS.replace(KS, "");
                    arabicText.setText(arabicS);
                }
            }

            //Roman Text converter
            if (e.getSource() == romanText) {
                if  ((keyChar == 'V') || (keyChar == 'X') || (keyChar == 'I') ||
                		(keyChar == 'C') || (keyChar == 'D') || (keyChar == 'L') ||
                		(keyChar == 'M') || (keyChar == 'v') || (keyChar == 'i') ||
                    (keyChar == 'c') || (keyChar == 'd') || (keyChar == 'l') ||
                    (keyChar == 'm') || (keyChar == 'x')) {
                	
                    romanS = romanText.getText();
                    romanS.toUpperCase();
                    int aInt = R2A(romanS);
                    //when the aInt is above 3999 it'll delete the last input in that field
                    if (aInt < 4000) {
                        romanText.setText(romanS);
                        arabicS = Integer.toString(aInt);
                        arabicText.setText(arabicS);
                    } else {
                        romanText.setText(romanText.getText().substring(0, romanText.getText().length() - 1));
                    }
                }
                
                //if delete button was pressed it deletes it and does re-calculates the arabic text field.
                else if ((e.getKeyCode() == KeyEvent.VK_DELETE) ||
                    (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    if (romanText.getText().equals("")) {
                        arabicText.setText("");
                    } else {
                        romanS = romanText.getText();
                        romanS.toUpperCase();
                        romanText.setText(romanS);
                        int aInt = R2A(romanS);
                        arabicS = Integer.toString(aInt);
                        arabicText.setText(arabicS);
                    }
                }
                
                // If a invalid key is pressed it deletes it.
                else {
                    romanS = romanText.getText();
                    String KS = Character.toString(keyChar);
                    romanS = romanS.replace(KS, "");
                    romanText.setText(romanS);
                }
            }
        }
    }

     //turns an int to a roman Char.
    public static String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        int times = 0;
        for (int i = Nums.length - 1; i >= 0; i--) {
            times = num / Nums[i];
            num %= Nums[i];
            while (times > 0) {
                sb.append(rLetters[i]);
                times--;
            }
        }
        return sb.toString();
    }
    
    	//Converts Roman to Arabic
    //@param string
    public int R2A(String string) {
        int res = 0;

        for (int i = 0; i < string.length(); i++) {
            //see what char value is i in the string
            int val1 = VAL(string.charAt(i));

            if (i + 1 < string.length()) {
                int val2 = VAL(string.charAt(i + 1));

                //to see if val1 is larger than val2
                if (val1 >= val2) {
                    res += val1;
                } else //val1 is less than val2
                {
                    res = res + val2 - val1;
                    i++;
                }
            } else {
                res += val1;
                i++;
            }
        }

        return res;
    }

  //Returns Value of Each roman character
    public int VAL(char C) {
        int i = 1000;
        if (C == 'M')
            return i;
        else if (C == 'D')
            return i / 2;
        else if (C == 'C')
            return i / 10;
        else if (C == 'L')
            return i / 20;
        else if (C == 'X')
            return i / 100;
        else if (C == 'V')
            return i / 200;
        else
            return 1;
    }
}


