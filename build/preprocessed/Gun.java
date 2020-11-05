/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.lang.Math.*;
/**
 *
 * @author Agata
 */
public class Gun {

    private final double gravity = 1.2;
    private double px;
    private double py;
    private double dx;
    private double dy;
    private double kat;
    private double moc;
    private double czolg;

    private Sprite pocisk;

    public Gun()
    {
    try
    {
       pocisk = new Sprite(Image.createImage("/kula.png"));
    }catch(Exception e)
    {
    }
    }

    public void strzelaj(int x, int y, double angle, int power,int ktory){
        px = x;
        py = y;
        dy= Math.sin(angle)*4*power;
        dx= Math.cos(angle)*3*power;
        czolg = ktory;
        pocisk.setVisible(true);

    }

    public Sprite getSprite(){
        return pocisk;
    }

    public void update(){
        if(czolg==1){
            px +=dx;
            py -=dy;
            dy -=gravity;

        }
        else{
            px-=dx;
            py -=dy;
            dy -=gravity;
        }
        pocisk.setPosition((int)px,(int)py);
        if((px < 0)||(px>522)||(py<0)||(py>250)){
            //System.out.println(px);
                       // System.out.println(py);
            pocisk.setVisible(false);
        }
        

    }


}

  
