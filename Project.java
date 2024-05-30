import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import javax.swing.*;
class Encoder{
    private boolean encode,decode;
    Encoder(){
        encode=false;
        decode=false;
    }
    void enableEncode(){
        encode=true;
        decode=false;
    };
    void enableDecode(){
        decode=true;
        encode=false;
    }
    void disableEncode(){
        enableEncode();
    }
    void disableDecode(){
        enableEncode();
    }
    boolean getEncode(){
        return encode;
    }
    boolean getDecoder(){
        return decode;
    }
}

class EncoderOptions{
    private boolean morse_code,ascii,url;
    EncoderOptions(){
        morse_code=false;
        ascii=false;
        url=false;
    }
    void setMorseCode(){
        morse_code=true;
        ascii=false;
        url=false;
    }
    void setAscii(){
        ascii=true;
        url=false;
        morse_code=false;
    }
    void setUrl(){
        url=true;
        ascii=false;
        morse_code=false;
    }
    boolean getMorseCode(){
        return morse_code;
    }
    boolean getAscii(){
        return ascii;
    }
    boolean getUrl(){
        return url;
    }
    
    private static HashMap<Character, String> createMorseCodeMap1() {
        HashMap<Character, String> morseCodeMap = new HashMap<>();
        morseCodeMap.put('A', ".-");
        morseCodeMap.put('B', "-...");
        morseCodeMap.put('C', "-.-.");
        morseCodeMap.put('D', "-..");
        morseCodeMap.put('E', ".");
        morseCodeMap.put('F', "..-.");
        morseCodeMap.put('G', "--.");
        morseCodeMap.put('H', "....");
        morseCodeMap.put('I', "..");
        morseCodeMap.put('J', ".---");
        morseCodeMap.put('K', "-.-");
        morseCodeMap.put('L', ".-..");
        morseCodeMap.put('M', "--");
        morseCodeMap.put('N', "-.");
        morseCodeMap.put('O', "---");
        morseCodeMap.put('P', ".--.");
        morseCodeMap.put('Q', "--.-");
        morseCodeMap.put('R', ".-.");
        morseCodeMap.put('S', "...");
        morseCodeMap.put('T', "-");
        morseCodeMap.put('U', "..-");
        morseCodeMap.put('V', "...-");
        morseCodeMap.put('W', ".--");
        morseCodeMap.put('X', "-..-");
        morseCodeMap.put('Y', "-.--");
        morseCodeMap.put('Z', "--..");
        morseCodeMap.put('0', "-----");
        morseCodeMap.put('1', ".----");
        morseCodeMap.put('2', "..---");
        morseCodeMap.put('3', "...--");
        morseCodeMap.put('4', "....-");
        morseCodeMap.put('5', ".....");
        morseCodeMap.put('6', "-....");
        morseCodeMap.put('7', "--...");
        morseCodeMap.put('8', "---..");
        morseCodeMap.put('9', "----.");
        return morseCodeMap;
    }

    private static HashMap<String, Character> createMorseCodeMap2() {
        HashMap<String, Character> morseCodeMap = new HashMap<>();
        morseCodeMap.put(".-", 'A');
        morseCodeMap.put("-...", 'B');
        morseCodeMap.put("-.-.", 'C');
        morseCodeMap.put("-..", 'D');
        morseCodeMap.put(".", 'E');
        morseCodeMap.put("..-.", 'F');
        morseCodeMap.put("--.", 'G');
        morseCodeMap.put("....", 'H');
        morseCodeMap.put("..", 'I');
        morseCodeMap.put(".---", 'J');
        morseCodeMap.put("-.-", 'K');
        morseCodeMap.put(".-..", 'L');
        morseCodeMap.put("--", 'M');
        morseCodeMap.put("-.", 'N');
        morseCodeMap.put("---", 'O');
        morseCodeMap.put(".--.", 'P');
        morseCodeMap.put("--.-", 'Q');
        morseCodeMap.put(".-.", 'R');
        morseCodeMap.put("...", 'S');
        morseCodeMap.put("-", 'T');
        morseCodeMap.put("..-", 'U');
        morseCodeMap.put("...-", 'V');
        morseCodeMap.put(".--", 'W');
        morseCodeMap.put("-..-", 'X');
        morseCodeMap.put("-.--", 'Y');
        morseCodeMap.put("--..", 'Z');
        morseCodeMap.put("-----", '0');
        morseCodeMap.put(".----", '1');
        morseCodeMap.put("..---", '2');
        morseCodeMap.put("...--", '3');
        morseCodeMap.put("....-", '4');
        morseCodeMap.put(".....", '5');
        morseCodeMap.put("-....", '6');
        morseCodeMap.put("--...", '7');
        morseCodeMap.put("---..", '8');
        morseCodeMap.put("----.", '9');
        return morseCodeMap;
    }

    private static String encodeToMorseCode(String inputString, HashMap<Character, String> morseCodeMap) {
        StringBuilder morseCode = new StringBuilder();
        for (char ch : inputString.toCharArray()) {
            if (ch == ' ') {
                morseCode.append(" ");
            } else if (morseCodeMap.containsKey(ch)) {
                morseCode.append(morseCodeMap.get(ch)).append(" ");
            }
        }
        return morseCode.toString();
    }

    private static String decodeMorseCode(String[] morseCodeWords, HashMap<String, Character> morseCodeMap) {
        StringBuilder decodedString = new StringBuilder();
        for (String word : morseCodeWords) {
            if (word.equals("")) {
                decodedString.append(" ");
            } else if (morseCodeMap.containsKey(word)) {
                decodedString.append(morseCodeMap.get(word));
            }
        }
        return decodedString.toString();
    }

    private static String stringToAsciiBinaryString(String input) {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = input.getBytes();
        for (byte b : bytes) {
            for (int i = 7; i >= 0; i--) {
                sb.append((b >> i) & 1);
            }
        }
        return sb.toString();
    }
    
    private static String asciiBinaryStringToString(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i += 8) {
            String byteStr = input.substring(i, i + 8);
            int value = Integer.parseInt(byteStr, 2);
            sb.append((char) value);
        }
        return sb.toString();
    }

    private static String encodeAsUrl(String text) {
        String encoded = "";
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            if (Character.isLetterOrDigit((char) b) || b == '-' || b == '_' || b == '.' || b == '~') {
                encoded += (char) b;
            } else {
                encoded += "%" + String.format("%X", b);
            }
        }
        return encoded;
    }

    private static String decodeFromUrl(String encodedText) {
        StringBuilder decoded = new StringBuilder();
        int i = 0;
        while (i < encodedText.length()) {
            char currentChar = encodedText.charAt(i);
            if (currentChar == '%') {
                String hex = encodedText.substring(i + 1, i + 3);
                decoded.append((char) Integer.parseInt(hex, 16));
                i += 3;
            } else {
                decoded.append(currentChar);
                i++;
            }
        }
        return decoded.toString();
    }
    

    String getCorrespondingValue(String s,Encoder e){
        if(morse_code){
            if(e.getEncode()){  
                HashMap<Character, String> morseCodeMap1 = createMorseCodeMap1();
                return encodeToMorseCode(s.toUpperCase(), morseCodeMap1);
            }
            else{
                HashMap<String,Character> morseCodeMap2 = createMorseCodeMap2();
                    return decodeMorseCode(s.split(" "), morseCodeMap2);
                }
        }
        else if(ascii){
            if(e.getEncode()){
                return stringToAsciiBinaryString(s);
            }
            else{
                return asciiBinaryStringToString(s);
            }

        }
        else{
            if(e.getEncode()){
                return encodeAsUrl(s);
            }
            else{
                return decodeFromUrl(s);
            }
        }
    }
}

class Base{
private String from,to;
Base(){
    from="Dec";
    to="Bin";
}
void setFromValue(String a){
    from=a;
}
void setToValue(String a){
    to=a;
}


String getFromValue(){
    return from;
}
String getToValue(){
    return to;
}

private String decimalToHexadecimal(String d) {
    int decimal=Integer.parseInt(d);
    String hexadecimal = "";
    while (decimal > 0) {
        int remainder = decimal % 16;
        if (remainder < 10) {
            hexadecimal = remainder + hexadecimal;
        } else {
            char hexChar = (char) ('A' + remainder - 10);
            hexadecimal = hexChar + hexadecimal;
        }
        decimal = decimal / 16;
    }
    if (hexadecimal.equals("")) {
        hexadecimal = "0";
    }
    return hexadecimal;
}

private String decimalToOctal(String d) {
    int decimal=Integer.parseInt(d);
    String octal = "";
    while (decimal > 0) {
        int remainder = decimal % 8;
        octal = remainder + octal;
        decimal = decimal / 8;
    }
    if (octal.equals("")) {
        octal = "0";
    }
    return octal;
}

private String decimalToBinary(String d) {
    int decimal=Integer.parseInt(d);
    String binary = "";
    while (decimal > 0) {
        int remainder = decimal % 2;
        binary = remainder + binary;
        decimal = decimal / 2;
    }
    return binary;
}

private String binaryToDecimal(String binary) {
    int decimal = 0;
    int power = 0;
    for (int i = binary.length() - 1; i >= 0; i--) {
        int digit = binary.charAt(i) - '0';
        decimal += digit * Math.pow(2, power);
        power++;
    }
    return String.valueOf(decimal);
}

private String octalToDecimal(String octal) {
    int decimal = 0;
    int power = 0;
    for (int i = octal.length() - 1; i >= 0; i--) {
        int digit = octal.charAt(i) - '0';
        decimal += digit * Math.pow(8, power);
        power++;
    }
    return String.valueOf(decimal);
}

private String hexadecimalToDecimal(String hexadecimal) {
    int decimal = 0;
    int power = 0;
    for (int i = hexadecimal.length() - 1; i >= 0; i--) {
        char hexChar = hexadecimal.charAt(i);
        int digit;
        if (hexChar >= '0' && hexChar <= '9') {
            digit = hexChar - '0';
        } else {
            digit = hexChar - 'A' + 10;
        }
        decimal += digit * Math.pow(16, power);
        power++;
    }
    return String.valueOf(decimal);
}

String getCorrespondingValue(String s){
if(from.equals("Bin")){
    if(to.equals("Bin")){
        return s;
    }
    else if(to.equals("Oct")){
        return decimalToOctal(binaryToDecimal(s));
    }
    else if(to.equals("Dec")){
        return binaryToDecimal(s);
    }
    else{
        return decimalToHexadecimal(binaryToDecimal(s));
    }
}
else if(from.equals("Oct")){
    if(to.equals("Bin")){
        return decimalToBinary(octalToDecimal(s));
    }
    else if(to.equals("Oct")){
        return s;
    }
    else if(to.equals("Dec")){
        return octalToDecimal(s);
    }
    else{
        return decimalToHexadecimal(octalToDecimal(s));
    }
}
else if(from.equals("Dec")){
    if(to.equals("Bin")){
        return decimalToBinary(s);
    }
    else if(to.equals("Oct")){
        return decimalToOctal(s);
    }
    else if(to.equals("Dec")){
        return s;
    }
    else{
        return decimalToHexadecimal(s);
    }
}
else{
    if(to.equals("Bin")){
        return decimalToBinary(hexadecimalToDecimal(s));
    }
    else if(to.equals("Oct")){
        return decimalToOctal(hexadecimalToDecimal(s));
    }
    else if(to.equals("Hex")){
        return hexadecimalToDecimal(s);
    }
    else{
        return s;
    }
}
}

}

class Encrypt{
    private boolean enc,dec;
    String val;
    Encrypt(){
        enc=false;
        dec=false;
    }
    void setEncrypt(){
        enc=true;
        dec=false;
    }
    void setDecrypt(){
        dec=true;
        enc=false;
    }
    void disableEncrypt(){
        enc=false;
    }
    void disableDecrypt(){
        dec=false;
    }
    public void setValue(String s) {
        this.val=s;
    }
    private String vigenereCipher(String inputString, String key, boolean encrypt) {
        String result = "";
        inputString = inputString.toUpperCase();
        key = key.toUpperCase();
        int inputLength = inputString.length();
        int keyLength = key.length();
        int[] keyValues = new int[keyLength];
        for (int i = 0; i < keyLength; i++) {
            keyValues[i] = (int) key.charAt(i) - 65;
        }
        int keyIndex = 0;
        for (int i = 0; i < inputLength; i++) {
            char c = inputString.charAt(i);
            if (c < 65 || c > 90) {
                result += c;
            } else {
                int value = ((int) c - 65 + (encrypt ? keyValues[keyIndex] : -keyValues[keyIndex]) + 26) % 26;
                result += (char) (value + 65);
                keyIndex = (keyIndex + 1) % keyLength;
            }
        }
        return result;
    }

        
private String vernamCipher(String message, String key) {
    StringBuilder cipherText = new StringBuilder();
    for (int i = 0; i < message.length(); i++) {
        char ch = message.charAt(i);
        int chValue = (int) ch;
        int keyValue = (int) key.charAt(i % key.length());
        int encryptedValue = (chValue + keyValue) % 128;
        char encryptedChar = (char) encryptedValue;
        cipherText.append(encryptedChar);
    }
    return cipherText.toString();
}

private String vernamDecipher(String cipherText, String key) {
    StringBuilder message = new StringBuilder();
    for (int i = 0; i < cipherText.length(); i++) {
        char ch = cipherText.charAt(i);
        int chValue = (int) ch;
        int keyValue = (int) key.charAt(i % key.length());
        int decryptedValue = (chValue - keyValue + 128) % 128;
        char decryptedChar = (char) decryptedValue;
        message.append(decryptedChar);
    }
    return message.toString();
}

private String encryptCaesar(String plainText, String key) {
    int shift = key.length();
    StringBuilder cipherText = new StringBuilder();
    for (int i = 0; i < plainText.length(); i++) {
        char c = plainText.charAt(i);
        if (Character.isLetter(c)) {
            int asciiCode = (int) c;
            int shiftedAsciiCode = asciiCode + shift;
            if (Character.isUpperCase(c)) {
                shiftedAsciiCode = (shiftedAsciiCode - 65) % 26 + 65;
            } else {
                shiftedAsciiCode = (shiftedAsciiCode - 97) % 26 + 97;
            }
            cipherText.append((char) shiftedAsciiCode);
        } else {
            cipherText.append(c);
        }
    }
    return cipherText.toString();
}

private String decryptCaesar(String cipherText, String key) {
    int shift = key.length();
    StringBuilder plainText = new StringBuilder();
    for (int i = 0; i < cipherText.length(); i++) {
        char c = cipherText.charAt(i);
        if (Character.isLetter(c)) {
            int asciiCode = (int) c;
            int shiftedAsciiCode = asciiCode - shift;
            if (Character.isUpperCase(c)) {
                shiftedAsciiCode = (shiftedAsciiCode - 65 + 26) % 26 + 65;
            } else {
                shiftedAsciiCode = (shiftedAsciiCode - 97 + 26) % 26 + 97;
            }
            plainText.append((char) shiftedAsciiCode);
        } else {
            plainText.append(c);
        }
    }
    return plainText.toString();
}

    public String getCorrespondingValue(String s,String k) {
        if(val.equals("Vigenere")){
            return vigenereCipher(s, k, enc);
        }
        else if(val.equals("Vernam")){
            if(enc) return vernamCipher(s, k);
            return vernamDecipher(s, k);
        }
        else{
            if(enc) return encryptCaesar(s, k);
            return decryptCaesar(s, k);

        }
    }

}

public class Project {

    public static void main(String[] args) {
        JButton b1, b2, b3;
        b1 = new JButton();
        b1.setBackground(new Color(237, 43, 42));
        b1.setBounds(0, 50, 100, 50);
        b1.setText("Encoder");
        b1.setFocusable(false);

        b2 = new JButton();
        b2.setBounds(0, 150, 100, 50);
        b2.setBackground(new Color(237, 43, 42));
        b2.setText("Base Convertor");
        b2.setFocusable(false);

        b3 = new JButton();
        b3.setText("Encryptor");
        b3.setBounds(0, 250, 100, 50);
        b3.setBackground(new Color(237, 43, 42));
        b3.setFocusable(false);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(14, 24, 95));
        leftPanel.setBounds(0, 0, 100, 500);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(100, 0, 650, 500);
        rightPanel.setBackground(new Color(47, 164, 255));

        JFrame frame = new JFrame("Encoder and decoder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(750, 500);
        frame.setVisible(true);
        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.add(leftPanel);
        frame.add(rightPanel);

        
        
        String[] encodingOptions = { "Morse code", "Url", "ASCII" };
        JComboBox<String> encoderDropDown = new JComboBox<>(encodingOptions); 
        JLabel encoderInputLabel = new JLabel("Input:");
        JTextField encoderInputField = new JTextField(10);
        JLabel encoderOutputLabel = new JLabel("Output:");
        JTextField encoderOutputField = new JTextField(10);
        JButton encoderButton = new JButton("Result");
        JRadioButton en = new JRadioButton("Encode", null, false);
        JRadioButton de = new JRadioButton("Decode", null, false);
        ButtonGroup encoderGroup = new ButtonGroup();
        encoderGroup.add(en);
        encoderGroup.add(de);
        Encoder e= new Encoder();
        EncoderOptions eo = new EncoderOptions();
        eo.setMorseCode();
        

        String[] baseOptions = { "Bin", "Oct", "Dec","Hex" };
        JComboBox<String> baseDropDown1 = new JComboBox<>(baseOptions); 
        JComboBox<String> baseDropDown2 = new JComboBox<>(baseOptions); // create a dropdown with the options
        baseDropDown1.setSelectedIndex(2);
        baseDropDown2.setSelectedIndex(0); // set the default selected option
        JLabel baseInputLabel = new JLabel("Input:");
        JTextField baseInputField = new JTextField(10);
        JLabel baseOutputLabel = new JLabel("Output:");
        JTextField baseOutputField = new JTextField(10);
        JButton baseButton = new JButton("Result");
        Base B =new Base();

        String[] encryptorOptions = { "Vigenere", "Vernam", "Ceaser" };
        JComboBox<String> encryptorDropDown = new JComboBox<>(encryptorOptions);
        encryptorDropDown.setSelectedIndex(0);
        JLabel encryptorInputLabel = new JLabel("Input:");
        JTextField encryptorInputField = new JTextField(10);
        JLabel encryptorKeyLabel = new JLabel("Key:");
        JTextField encryptorKeyField = new JTextField(10);
        JLabel encryptorOutputLabel = new JLabel("Output:");
        JTextField encryptorOutputField = new JTextField(10);
        JButton encryptorButton = new JButton("Result");
        JRadioButton enc = new JRadioButton("Encrypt", null, false);
        JRadioButton dec = new JRadioButton("Decrypt", null, false);
        ButtonGroup encryptGroup = new ButtonGroup();
        encryptGroup.add(enc);
        encryptGroup.add(dec);
        Encrypt E = new Encrypt();
        E.setValue(encryptorOptions[0]);

        ActionListener listener1 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                encoderDropDown.setSelectedIndex(0);
                rightPanel.add(en);
                rightPanel.add(new JLabel("                                                                             "));
                rightPanel.add(de);
                rightPanel.add(encoderDropDown);
                rightPanel.add(encoderInputLabel);
                rightPanel.add(encoderInputField);
                rightPanel.add(encoderOutputLabel);
                rightPanel.add(encoderOutputField);
                rightPanel.add(encoderButton);

                rightPanel.remove(baseDropDown1);
                rightPanel.remove(baseDropDown2);
                rightPanel.remove(baseInputLabel);
                rightPanel.remove(baseInputField);
                rightPanel.remove(baseOutputLabel);
                rightPanel.remove(baseOutputField);
                rightPanel.remove(baseButton);

                rightPanel.remove(encryptorDropDown);
                rightPanel.remove(encryptorInputLabel);
                rightPanel.remove(encryptorInputField);
                rightPanel.remove(encryptorOutputLabel);
                rightPanel.remove(encryptorOutputField);
                rightPanel.remove(encryptorKeyLabel);
                rightPanel.remove(encryptorKeyField);
                rightPanel.remove(encryptorButton);
                rightPanel.remove(enc);
                rightPanel.remove(dec);

                frame.add(rightPanel);
                frame.setVisible(false);
                frame.setVisible(true);
                b1.setBackground(new Color(254, 98, 68));
                b2.setBackground(new Color(237, 43, 42));
                b3.setBackground(new Color(237, 43, 42));
                b1.setEnabled(false);
                b1.setForeground(Color.BLACK);
                b2.setEnabled(true);
                b3.setEnabled(true);
            }
        };

        ActionListener listener2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            
                rightPanel.add(baseDropDown1);
                rightPanel.add(baseDropDown2);
                rightPanel.add(baseInputLabel);
                rightPanel.add(baseInputField);
                rightPanel.add(baseOutputLabel);
                rightPanel.add(baseOutputField);
                rightPanel.add(baseButton);

                rightPanel.remove(encoderDropDown);
                rightPanel.remove(encoderInputLabel);
                rightPanel.remove(encoderInputField);
                rightPanel.remove(encoderOutputLabel);
                rightPanel.remove(encoderOutputField);
                rightPanel.remove(encoderButton);
                rightPanel.remove(en);
                rightPanel.remove(de);

                rightPanel.remove(encryptorDropDown);
                rightPanel.remove(encryptorInputLabel);
                rightPanel.remove(encryptorInputField);
                rightPanel.remove(encryptorOutputLabel);
                rightPanel.remove(encryptorOutputField);
                rightPanel.remove(encryptorButton);
                rightPanel.remove(encryptorKeyLabel);
                rightPanel.remove(encryptorKeyField);
                rightPanel.remove(enc);
                rightPanel.remove(dec);

                frame.add(rightPanel);
                frame.setVisible(false);
                frame.setVisible(true);
                b2.setBackground(new Color(254, 98, 68));
                b1.setBackground(new Color(237, 43, 42));
                b3.setBackground(new Color(237, 43, 42));
                b1.setEnabled(true);
                b2.setEnabled(false);
                b3.setEnabled(true);
            }
        };

        ActionListener listener3 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                rightPanel.add(enc);
                rightPanel.add(dec);
                rightPanel.add(encryptorDropDown);
                rightPanel.add(encryptorKeyLabel);
                rightPanel.add(encryptorKeyField);
                rightPanel.add(encryptorInputLabel);
                rightPanel.add(encryptorInputField);
                rightPanel.add(encryptorOutputLabel);
                rightPanel.add(encryptorOutputField);
                rightPanel.add(encryptorButton);

                rightPanel.remove(encoderDropDown);
                rightPanel.remove(encoderInputLabel);
                rightPanel.remove(encoderInputField);
                rightPanel.remove(encoderOutputLabel);
                rightPanel.remove(encoderOutputField);
                rightPanel.remove(encoderButton);
                rightPanel.remove(en);
                rightPanel.remove(de);

                rightPanel.remove(baseDropDown2);
                rightPanel.remove(baseDropDown1);
                rightPanel.remove(baseInputLabel);
                rightPanel.remove(baseInputField);
                rightPanel.remove(baseOutputLabel);
                rightPanel.remove(baseOutputField);
                rightPanel.remove(baseButton);

                b3.setBackground(new Color(254, 98, 68));
                b2.setBackground(new Color(237, 43, 42));
                b1.setBackground(new Color(237, 43, 42));
                frame.add(rightPanel);
                frame.setVisible(false);
                frame.setVisible(true);
                b1.setEnabled(true);
                b2.setEnabled(true);
                b3.setEnabled(false);
            }
        };
        ActionListener listener4 = new ActionListener(){
            public void actionPerformed(ActionEvent b){
                e.enableEncode();
                frame.setVisible(false);
                frame.setVisible(true);
            }
        };

        ActionListener listener5= new ActionListener(){
            public void actionPerformed(ActionEvent b){
                System.out.println("decode selected!");
                e.enableDecode();
                frame.setVisible(false);
                frame.setVisible(true);
            }
        };

        ActionListener listener6 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Drop down triggered!");
                String op=(String) encoderDropDown.getSelectedItem();
                if(op.equals("Morse code")){
                    eo.setMorseCode();
                }
                else if(op.equals("Url")){
                    eo.setUrl();
                }
                else{
                    eo.setAscii();
                }
                frame.setVisible(false);
                frame.setVisible(true);
                System.out.println(op);
            }
        };

        ActionListener listener7 = new ActionListener(){
            public void actionPerformed(ActionEvent b){
                String s= encoderInputField.getText();
                System.out.println(eo.getCorrespondingValue(s, e));
                encoderOutputField.setText(eo.getCorrespondingValue(s,e));
            }
        };

        ActionListener listener8 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Drop down triggered!");
                String op=(String) baseDropDown1.getSelectedItem();
                B.setFromValue(op);
                frame.setVisible(false);
                frame.setVisible(true);
                System.out.println(B.getFromValue());
            }
            
        };

        ActionListener listener9 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Drop down triggered!");
                String op=(String) baseDropDown2.getSelectedItem();
                B.setToValue(op);
                frame.setVisible(false);
                frame.setVisible(true);
                System.out.println(B.getToValue());
            }
            
        };

        ActionListener listener10 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s= baseInputField.getText();
                System.out.println(B.getCorrespondingValue(s));
                baseOutputField.setText(B.getCorrespondingValue(s));
            }
        };

        ActionListener listener11 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                E.setEncrypt();
                frame.setVisible(false);
                frame.setVisible(true);
            }
        };

        ActionListener listener12 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                E.setDecrypt();
                frame.setVisible(false);
                frame.setVisible(true);
            }
        };

        ActionListener listener13 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                E.setValue((String)encryptorDropDown.getSelectedItem());
                frame.setVisible(false);
                frame.setVisible(true);
            }
        };

        ActionListener listener14 = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                encryptorOutputField.setText(E.getCorrespondingValue(encryptorInputField.getText(),encryptorKeyField.getText()));
            }
        };

        en.addActionListener(listener4);
        de.addActionListener(listener5);
        encoderDropDown.addActionListener(listener6);
        encoderButton.addActionListener(listener7);

        baseDropDown1.addActionListener(listener8);
        baseDropDown2.addActionListener(listener9);
        baseButton.addActionListener(listener10);

        enc.addActionListener(listener11);
        dec.addActionListener(listener12);
        encryptorDropDown.addActionListener(listener13);
        encryptorButton.addActionListener(listener14);

        b1.addActionListener(listener1);
        b2.addActionListener(listener2);
        b3.addActionListener(listener3);

    }
}
