/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ybpsimulator;
import java.util.Random;
import java.util.List;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
//import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.charset.Charset;


/**
 *
 * @author Taiga Vo
 */
public class YBPSimulator implements ActionListener{
  private JPanel screenPanel;
  private JButton gen;
  private JLabel monsLabel, spelLabel, trapLabel, extrLabel, tribLabel, label;
  JTextField mons, spel, trap, extr, trib;
  int monstersNum, spellsNum, trapsNum, extraNum, tributeNum, monsI, spelI, trapI, extrI, tribI, currentFile = 1;
  boolean yetToExist = true;
  BufferedReader br = null;
  String currentLine;
  String[] types = {"# of Monsters", "#of Tributes", "# of Spells","# of Traps","# of ExtraDeck"};
  private ArrayList<String> database = new ArrayList<String>();
  Random rand = new Random();
  
  
  public JPanel createContentPane() {
    try {
  List<String> monstersLines = Files.readAllLines(Paths.get("monsters.txt"), Charset.defaultCharset());
  monstersNum = monstersLines.size();
  List<String> spellsLines = Files.readAllLines(Paths.get("spells.txt"), Charset.defaultCharset());
  spellsNum = spellsLines.size();
  List<String> trapsLines = Files.readAllLines(Paths.get("traps.txt"), Charset.defaultCharset());
  trapsNum = trapsLines.size();
  List<String> extraLines = Files.readAllLines(Paths.get("extra.txt"), Charset.defaultCharset());
  extraNum = extraLines.size();
  List<String> tributeLines = Files.readAllLines(Paths.get("tribute.txt"), Charset.defaultCharset());
  tributeNum = tributeLines.size();
  } catch (IOException e) {
            e.printStackTrace();
  }
  while (yetToExist)
  {
    File check = new File("rand" + currentFile + ".ydk");
    if (!check.exists()) {
      try {
      check.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      yetToExist = false;
    }
    else {
      currentFile += 1;
    }
    
  }
  
    JPanel gui = new JPanel();
    gui.setLayout(null);
    
    screenPanel = new JPanel();
    screenPanel.setLayout(null);
    screenPanel.setSize(200, 300);
    gui.add(screenPanel);
    
    for (String type : types) {
      label = new JLabel(type);
      label.setLocation(10,15+Arrays.asList(types).indexOf(type)*25);
      label.setSize(100,20);
      screenPanel.add(label);
      
    }
//    monsLabel = new JLabel("# of Monsters");
//    monsLabel.setLocation(10,15);
//    monsLabel.setSize(100,20);
//    screenPanel.add(monsLabel);
    
    mons = new JTextField("17");
    mons.setLocation(105,15);
    mons.setSize(30,20);
    screenPanel.add(mons);
    mons.addActionListener(this);
    
//    monsLabel = new JLabel("# of Tributes");
//    monsLabel.setLocation(10,40);
//    monsLabel.setSize(100,20);
//    screenPanel.add(monsLabel);
    
    trib = new JTextField("4");
    trib.setLocation(105,40);
    trib.setSize(30,20);
    screenPanel.add(trib);
    trib.addActionListener(this);
    
//    monsLabel = new JLabel("# of Spells");
//    monsLabel.setLocation(10,65);
//    monsLabel.setSize(100,20);
//    screenPanel.add(monsLabel);
    
    spel = new JTextField("10");
    spel.setLocation(105,65);
    spel.setSize(30,20);
    screenPanel.add(spel);
    spel.addActionListener(this);
    
//    monsLabel = new JLabel("# of Traps");
//    monsLabel.setLocation(10,90);
//    monsLabel.setSize(100,20);
//    screenPanel.add(monsLabel);
    
    trap = new JTextField("9");
    trap.setLocation(105,90);
    trap.setSize(30,20);
    screenPanel.add(trap);
    trap.addActionListener(this);
    
//    monsLabel = new JLabel("# of ExtraDeck");
//    monsLabel.setLocation(10,115);
//    monsLabel.setSize(100,20);
//    screenPanel.add(monsLabel);
    
    extr = new JTextField("15");
    extr.setLocation(105,115);
    extr.setSize(30,20);
    screenPanel.add(extr);
    extr.addActionListener(this);
    
    gen = new JButton("Generate");
    gen.setLocation(40, 215);
    gen.setSize(100, 30);
    screenPanel.add(gen);
    gen.addActionListener(this);
    
    gui.setOpaque(true);
    return gui;
  }
  

  
  public int chooseCard(int x) {
    int pick = rand.nextInt(x);
    return pick;
  }
  
   public void actionPerformed(ActionEvent e) {
     if (e.getSource() == gen)
    {
      generate();
    }
   }
   
   public void importFile(File filename) {
     database.clear();
    try {
      br = new BufferedReader(new FileReader(filename));
      currentLine = br.readLine();
      database.add(currentLine);
      while ((currentLine = br.readLine()) != null) {
        database.add(currentLine);
      }
      
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)br.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    } 
  }
   
   public void generate() {
     FileWriter fw;
     try {
       monsI = Integer.parseInt(mons.getText());
       spelI = Integer.parseInt(spel.getText());
       trapI = Integer.parseInt(trap.getText());
       tribI = Integer.parseInt(trib.getText());
       extrI = Integer.parseInt(extr.getText());
     } catch (Exception e) {
    e.printStackTrace();
     }
     try {
       fw = new FileWriter(new File("rand" + currentFile + ".ydk"));
         fw.write("#created by YBPSimulator");
       fw.write(System.lineSeparator());
       fw.write("#main");
       fw.write(System.lineSeparator());
       importFile(new File("monsters.txt"));
       for (int i =0;i<monsI;i++)
       {
         fw.write(database.get(chooseCard(monstersNum)));
         fw.write(System.lineSeparator());
       }
       importFile(new File("tribute.txt"));
       for (int i =0;i<tribI;i++)
       {
         fw.write(database.get(chooseCard(tributeNum)));
         fw.write(System.lineSeparator());
       }
       importFile(new File("spells.txt"));
       for (int i =0;i<spelI;i++)
       {
         fw.write(database.get(chooseCard(spellsNum)));
         fw.write(System.lineSeparator());
       }
       importFile(new File("traps.txt"));
       for (int i =0;i<trapI;i++)
       {
         fw.write(database.get(chooseCard(trapsNum)));
         fw.write(System.lineSeparator());
       }
       fw.write("#extra");
       fw.write(System.lineSeparator());
       importFile(new File("extra.txt"));
       for (int i =0;i<extrI;i++)
       {
         fw.write(database.get(chooseCard(extraNum)));
         fw.write(System.lineSeparator());
       }
       fw.close();
       } catch (IOException ex) {
            ex.printStackTrace();
        }
       currentFile++;
   }
   
   
  private static void createAndShowGUI() {
    
    JFrame.setDefaultLookAndFeelDecorated(false);
    JFrame frame = new JFrame("Yugioh Battle Pack Simulator");
    
    YBPSimulator yBPSimulator = new YBPSimulator();
    frame.setContentPane(yBPSimulator.createContentPane());
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(200, 300);
    frame.setVisible(true);
  }
  
 public static void main(String[] args) {
   
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
}