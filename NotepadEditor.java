
package notepadeditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class NotepadEditor implements ActionListener, WindowListener
{
    JMenuItem neww, open, save, saveas, cut, copy, paste, font, font_color, background_color;
    JTextArea textarea;
    JFrame jf, font_frame;
    JComboBox font_family, font_size, font_style;
    JButton ok;
    
    File file;
    
    NotepadEditor()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        jf=new JFrame("*Untitled* - Notepad");
        jf.setSize(700,600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        
        JMenuBar jmenubar=new JMenuBar();
        
        JMenu file=new JMenu("File");
        
        neww=new JMenuItem("New");
        neww.addActionListener(this);
        file.add(neww);
        
        open=new JMenuItem("Open");
        open.addActionListener(this);
        file.add(open);
        
        save=new JMenuItem("Save");
        save.addActionListener(this);
        file.add(save);
        
        saveas=new JMenuItem("Save As...");
        saveas.addActionListener(this);
        file.add(saveas);
        
        jmenubar.add(file);
        
        JMenu edit=new JMenu("Edit");
        
        cut=new JMenuItem("Cut");
        cut.addActionListener(this);
        edit.add(cut);
        
        copy=new JMenuItem("Copy");
        copy.addActionListener(this);
        edit.add(copy);
        
        paste=new JMenuItem("Paste");
        paste.addActionListener(this);
        edit.add(paste);
        
        jmenubar.add(edit);
        
        JMenu format=new JMenu("Format");
        
        font=new JMenuItem("Font");
        font.addActionListener(this);
        format.add(font);
        
        font_color=new JMenuItem("Font Color");
        font_color.addActionListener(this);
        format.add(font_color);
        
        background_color=new JMenuItem("Background Color");
        background_color.addActionListener(this);
        format.add(background_color);
        
        jmenubar.add(format);
        
        jf.setJMenuBar(jmenubar);
        
        textarea=new JTextArea();
        
        JScrollPane scrollpane=new JScrollPane(textarea);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        jf.add(scrollpane);
        jf.addWindowListener(this);
        jf.setVisible(true);
    }
    public static void main(String[] args)
    {
        new NotepadEditor();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==neww)
        {
            newFile();
        }
        if(e.getSource()==open)
        {
            openFile();
        }
        if(e.getSource()==save)
        {
            saveFile();
        }
        if(e.getSource()==saveas)
        {
            saveAsFile();
        }
        if(e.getSource()==cut)
        {
            textarea.cut();
        }
        if(e.getSource()==copy)
        {
            textarea.copy();
        }
        if(e.getSource()==paste)
        {
            textarea.paste();
        }
        if(e.getSource()==font)
        {
            openFontFrame();
        }
        if(e.getSource()==ok)
        {
            setFontInTextarea();
        }
        if(e.getSource()==font_color)
        {
            Color c=JColorChooser.showDialog(jf, "Choose Font Color", Color.black);
            textarea.setForeground(c);
        }
        if(e.getSource()==background_color)
        {
            Color c=JColorChooser.showDialog(jf, "Choose Background Color", Color.white);
            textarea.setBackground(c);
        }
    }
    
    void openFontFrame()
    {
        font_frame=new JFrame("Choose Font...");
        font_frame.setSize(500,500);
        font_frame.setLocationRelativeTo(jf);
        font_frame.setLayout(null);
        
        String[] fontfamily=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        font_family=new JComboBox(fontfamily);
        font_family.setBounds(50, 100, 100, 30);
        font_frame.add(font_family);
        
        String[] size={"10", "14", "18", "24", "28", "32", "38", "46", "72"};
        font_size=new JComboBox(size);
        font_size.setBounds(180, 100, 70, 30);
        font_frame.add(font_size);
        
        String[] style={"plain", "bold", "italic"};
        font_style=new JComboBox(style);
        font_style.setBounds(300, 100, 100, 30);
        font_frame.add(font_style);
        
        ok=new JButton("OK");
        ok.setBounds(200, 200, 100, 50);
        ok.addActionListener(this);
        font_frame.add(ok);
        
        font_frame.setVisible(true);
    }
    
    void setFontInTextarea()
    {
        String fontfamily=(String)font_family.getSelectedItem();
        String fontsize=(String)font_size.getSelectedItem();        //10,20,30
        String fontstyle=(String)font_style.getSelectedItem();      //plain, bold, 
        
        int fontstylee=0;
        if(fontstyle.equals("plain"))
        {
            fontstylee=0;
        }
        else if(fontstyle.equals("bold"))
        {
            fontstylee=1;
        }
        else if(fontstyle.equals("italic"))
        {
            fontstylee=2;
        }
        Font fontt=new Font(fontfamily, fontstylee, Integer.parseInt(fontsize));
        textarea.setFont(fontt);
        
        font_frame.setVisible(false);
    }
    
    void saveFile()
    {
        String title=jf.getTitle();
        
        if(title.equals("*Untitled* - Notepad"))
        {
            saveAsFile();
        }
        else
        {
            String text=textarea.getText();
            try(FileOutputStream fos=new FileOutputStream(file))
            {
                byte[] b=text.getBytes();
                fos.write(b);
            }
            catch(IOException ee)
            {
                ee.printStackTrace();
            }
        }
    }
    
    void openFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(jf);
        if(result==0)
        {
            textarea.setText("");
            file=fileChooser.getSelectedFile();
            jf.setTitle(file.getName());
            try(FileInputStream fis=new FileInputStream(file))
            {
                int i;
                while( (i=fis.read()) != -1)
                {
                    textarea.append(String.valueOf((char)i));
                }
            }
            catch(IOException ee)
            {
                ee.printStackTrace();
            }
        }
    }
    
    void saveAsFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(jf);
        if(result==0)
        {
            String text=textarea.getText();
            file=fileChooser.getSelectedFile();
            jf.setTitle(file.getName());
            try(FileOutputStream fos=new FileOutputStream(file))
            {
                byte[] b=text.getBytes();
                fos.write(b);
            }
            catch(IOException ee)
            {
                ee.printStackTrace();
            }
        }
    }
    
    void newFile()
    {
        String text=textarea.getText();
        if(!text.equals(""))
        {
            int i=JOptionPane.showConfirmDialog(jf, "Do you want to save this file ?");
            if(i==0)
            {
                saveFile();
                textarea.setText("");
                jf.setTitle("*Untitled* - Notepad");
            }
            else if(i==1)
            {
                textarea.setText("");
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("111111");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        JOptionPane.showConfirmDialog(jf, "Do you want to save this file ?");
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("33333");
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("44444");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("555555");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println("6666666");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("777777");
    }
}
