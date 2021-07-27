package com.hit.view;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.metal.MetalFileChooserUI;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.awt.Image;

/**
 * MyFrame class stands for creating and manipulating the frame in the UI
 */
public class MyFrame extends JFrame  implements ActionListener {
    JButton reqButton;
    JButton statisticButton;
    private PropertyChangeSupport support;
    private String action="";
    private JFileChooser fc;
     private JTextPane textArea;
    private  JLabel taLabel;
    private  JLabel waitingLable;
    private JScrollPane sbrText;
    public final static String STATISTICS = "getStatistics";

    /**
     * In the constructor the Jframe is beeing created
     * @param propertyChangeSupport - the view's propertyChangeSupport
     */
    public MyFrame(PropertyChangeSupport propertyChangeSupport) {
        support= propertyChangeSupport;
        ImageIcon reqImage = new ImageIcon("src/main/resources/icons/load.png");
        ImageIcon statisticImage = new ImageIcon("src/main/resources/icons/statistic.png");
        Image image= reqImage.getImage();
        Image newImg= image.getScaledInstance(45,45, Image.SCALE_SMOOTH);
        reqImage=new ImageIcon(newImg);

        Image image2= statisticImage.getImage();
        Image newImg2= image2.getScaledInstance(35,35, Image.SCALE_SMOOTH);
        statisticImage=new ImageIcon(newImg2);

        fc = new JFileChooser();
        MetalFileChooserUI ui = (MetalFileChooserUI)fc.getUI();
        Field field = null;
        try {
            field = MetalFileChooserUI.class.getDeclaredField("fileNameTextField");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(false);
        textArea =new JTextPane();
        taLabel= new JLabel();
        waitingLable= new JLabel();

        reqButton = new JButton();
        statisticButton= new JButton();
        reqButton.setBounds(90,50,240,75);
        statisticButton.setBounds(370,50,240,75);
        reqButton.addActionListener(this);

        statisticButton.addActionListener(this);

        reqButton.setText("Load a Request");
        reqButton.setFocusable(false);
        reqButton.setIcon(reqImage);
        statisticButton.setText("Show Statistic");
        statisticButton.setFocusable(false);
        statisticButton.setIcon(statisticImage);

        reqButton.setHorizontalTextPosition(JButton.CENTER);
        reqButton.setVerticalTextPosition(JButton.BOTTOM);
        statisticButton.setHorizontalTextPosition(JButton.CENTER);
        statisticButton.setVerticalTextPosition(JButton.BOTTOM);


        textArea.setBounds(110,240,500,300);
        textArea.setBackground(new Color(154,213,207));
        Font font= new Font ("TimesRoman", Font.BOLD , 15);
        textArea.setFont(font);
        textArea.setEditable(false);

        taLabel.setBounds(110,170,500,50);
        taLabel.setVisible(true);
        ImageIcon Image = new ImageIcon("src/main/resources/icons/waiting.jpg");
        Image image1= Image.getImage();
        Image waitingImage = image1.getScaledInstance(470, 250, java.awt.Image.SCALE_SMOOTH);
        Image =new ImageIcon(waitingImage);
        try {
            textArea.getDocument().insertString(0, "\n\nwaiting for your request...", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        textArea.insertIcon(Image);
        this.setTitle("MMU API");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(720,620);

        this.setVisible(true);
        this.setLayout(null);
        this.add(reqButton);
        this.add(statisticButton);
        this.add(textArea);
        this.add(taLabel);
        ImageIcon imageIcon = new ImageIcon("src/main/resources/icons/logo.svg.png");
        this.setIconImage(imageIcon.getImage());
        this.getContentPane().setBackground(new Color(154,213,207));

    }

    /**
     * actionPerformed function takes care of reading the new request,
     * setting the UI data accordingly to the request
     * and activating the firePropertyChange function with the new request
     * @param e - the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == reqButton) {
            StringBuilder data;
            try {
              data =  fileChooser();
              if (data == null){
                  setUIData("This is not a Valid file", "src/main/resources/icons/x.png");
                  return;
              }
              String olsAction= this.action;
              this.action= data.toString();
                support.firePropertyChange("action",olsAction, data);
            } catch (FileNotFoundException fileNotFoundException) {
                setUIData("Not a Valid Request","src/main/resources/icons/x.png");
                return;
            }
        }
        else if(e.getSource()==statisticButton){
            String oldAction= this.action;
            this.action=STATISTICS;
            support.firePropertyChange("action",oldAction, STATISTICS);

        }
    }

    /**
     * fileChooser function is building the file chooser loading requests in the UI
     * @return
     * @throws FileNotFoundException
     */
    private StringBuilder fileChooser() throws FileNotFoundException {
        fc.setCurrentDirectory(new File("src/main/resources/requests"));
        fc.setFileFilter(new FileFilter(){
                @Override
                public boolean accept(File file){
                    return true;
                }
                @Override
                public String getDescription() {
                    return ".json";
                }
            });
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
               if(getFileExtension(file.toString()).equals("json")) {
                    return fileReader(file);
                }
                else  {
                    return null;
                }
            }
            return null ;
    }

    /**
     * getFileExtension function checks the ending of a file name
     * @param fullName - the name of file to check
     * @return - null if there is no ending, otherwise the ending.
     */
    private  String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    /**
     * fileReader function reads the data from  the request's file
     * @param file -  the file to read from
     * @return the file's content
     */
    private StringBuilder fileReader(File file ) {
        StringBuilder data = new StringBuilder();
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                data.append(input.next());
            }
        } catch (FileNotFoundException fileNotFoundException) {
           setUIData("Not a Valid Request","src/main/resources/icons/x.png");
        }
        return data;
    }

    /**
     * setUIData function is in charge of showing the correct message and icon on screen
     * @param s the string to show on the screen
     * @param imagePath the path to the image to show on the screen
     */
    public void setUIData(String s, String imagePath){
        ImageIcon reqImage = new ImageIcon(imagePath);
        Image image= reqImage.getImage();
        Image newImg= image.getScaledInstance(50,50, Image.SCALE_SMOOTH);
        reqImage=new ImageIcon(newImg);
        taLabel.setIcon(reqImage);
        textArea.setText(s);
        textArea.setVisible(true);
        taLabel.setVisible(true);
        textArea.validate();
        taLabel.validate();
        this.revalidate();
        this.repaint();
        this.add(taLabel);

    }
}
