import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;
import static java.awt.event.InputEvent.*;
import java.awt.datatransfer.*;
import java.util.Enumeration;
import java.util.Hashtable;
/*import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.awt.event.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;*/

class notepad 
{
  public static void main(String args[])
  {
     TextEdit TE=new TextEdit("记事本");
  }
}
class TextEdit extends Frame implements ActionListener
{
   MenuBar m;
   Menu m1,m2;
   MenuItem xinjian,dakai,baocun,tuichu,jianqie,fuzhi,zhantie;
   TextArea text;
   String filename;
   FileDialog openFD,saveFD;
   BufferedReader in;
   FileReader read;
   BufferedWriter out;
   FileWriter writer;
   Clipboard cb;
   TextEdit(String s)
   {
     super(s);
     m=new MenuBar();
     m1=new Menu("文件");
     xinjian=new MenuItem("新建");
     dakai=new MenuItem("打开");
     baocun=new MenuItem("保存");
     tuichu=new MenuItem("退出");
     m2=new Menu("编辑");
     jianqie=new MenuItem("剪切");
     fuzhi=new MenuItem("复制");
     zhantie=new MenuItem("粘贴");
     text=new TextArea();
     openFD=new FileDialog(this,"打开",FileDialog.LOAD);
     saveFD=new FileDialog(this,"保存",FileDialog.SAVE);
     filename="NoName";
     
     m1.add(xinjian);
     m1.addSeparator();
     m1.add(dakai);
     m1.addSeparator();
     m1.add(baocun);
     m1.addSeparator();
     m1.add(tuichu);
     m2.add(jianqie);
     m2.addSeparator();
     m2.add(fuzhi);
     m2.addSeparator();
     m2.add(zhantie);
     m.add(m1);
     m.add(m2);
     
     //关键部分，没有为cb申请内存，下面对cb操作会出错
    cb = new Clipboard("nothing");
     //
     
     setMenuBar(m);
     setSize(300,400);setVisible(true);
     add(text,"Center");
     
     xinjian.addActionListener(this);
     dakai.addActionListener(this);
     baocun.addActionListener(this);
     tuichu.addActionListener(this);
     jianqie.addActionListener(this);
     fuzhi.addActionListener(this);
     zhantie.addActionListener(this);
     addWindowListener(new WindowAdapter()
                      {
                        public void windowClosing(WindowEvent e)
                        {
                          System.exit(0);
                        }
                      });
    }
    
    public void actionPerformed(ActionEvent e)
    {
      if(e.getSource()==xinjian)
       {
         text.setText("");
       }
      if(e.getSource()==dakai)
       {
        openFD.show();
        String s;
        filename=openFD.getDirectory()+openFD.getFile();
        if(filename!=null)
         {
          try
           {
            File file=new File(filename);
            read=new FileReader(file);
            in=new BufferedReader(read);
            while((s=in.readLine())!=null)
                text.append(s+'\n');
            in.close();
            read.close();
           }
            catch(IOException e2){}
         }
       }
      
      if(e.getSource()==baocun)
      {
        saveFD.show();
        filename=saveFD.getDirectory()+saveFD.getFile();
        if(filename!=null)
        {
         try
           {
            File file=new File(filename);
            writer=new FileWriter(file);
            out=new BufferedWriter(writer);
            out.write(text.getText(),0,(text.getText()).length());
            
            out.close();
            writer.close();
           }
          catch(IOException e2){} 
        }
      }
      if(e.getSource()==tuichu)
      {
        System.exit(0);
      }
      
      
      
      
     if(e.getSource()==jianqie)
      {
      //类text中没有cut方法，不能使用text.cut
        String s=text.getSelectedText();
        StringSelection select=new StringSelection(s);
        cb.setContents(select,null);
        text.replaceRange("",text.getSelectionStart(),text.getSelectionEnd());
      }
      if(e.getSource()==fuzhi)
      {
      //同上，没有copy这个方法
        String s=text.getSelectedText();
        StringSelection select=new StringSelection(s);
        cb.setContents(select,null);
      }
      if(e.getSource()==zhantie)
      {
      //同上，没有paste方法
           String s="";
          Transferable t = cb.getContents(null);
          try 
          {
                if (t != null
                    && t.isDataFlavorSupported(DataFlavor.stringFlavor)) 
                {
                        // 因为原系的剪贴板里有多种信息, 如文字, 图片, 文件等
                        // 先判断开始取得的可传输的数据是不是文字, 如果是, 取得这些文字
                        
                    s = (String)t.getTransferData(DataFlavor.stringFlavor);
                        // 同样, 因为Transferable中的DataFlavor是多种类型的,
                        // 所以传入DataFlavor这个参数, 指定要取得哪种类型的Data.
                        //System.out.println(s);
                }
          } 
          catch (UnsupportedFlavorException ex) 
          {
                ex.printStackTrace();
          } 
          catch (IOException ex) 
          {
                ex.printStackTrace();
          }
          
          text.insert(s,text.getCaretPosition());
          
      }
      
    }
  
   
}