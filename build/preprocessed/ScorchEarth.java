/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author Agata
 */


public class ScorchEarth extends MIDlet implements CommandListener
{
    // reference to the components we are using
    private Display display ;
    private MyGameCanvas mgc = new MyGameCanvas() ;
    private TextField gracz1;
    private TextField gracz2;

    public void startApp()
    {

        // find current display
        this.display = Display.getDisplay( this ) ;
        // attach game to display

        Form form = new Form("Scorched Earth");

        Item item = new StringItem("Wpisz nazwy graczy :", "", 0);
        item.setLayout(Item.LAYOUT_2 | Item.LAYOUT_NEWLINE_AFTER);
        form.append(item);

        this.gracz1 = new TextField("Gracz 1:", " ", 32, 0);
        item.setLayout(Item.LAYOUT_2 | Item.LAYOUT_NEWLINE_AFTER);
        form.append(this.gracz1);

        this.gracz2 = new TextField("Gracz 2:", " ", 32, 0);
        item.setLayout(Item.LAYOUT_2 | Item.LAYOUT_NEWLINE_AFTER);
        form.append(this.gracz2);



        form.setCommandListener((CommandListener) this);
        form.addCommand(new Command("OK", Command.OK, 0));

        this.display.setCurrent( form ) ;


        //this.display.setCurrent(mgc) ;
        // start the game
       // mgc.start() ;
    }



public void commandAction(Command c, Displayable d) {

        // set players names
        mgc.setPlayer(gracz1.getString(), gracz2.getString());

        // attach game to display
        this.display.setCurrent( mgc ) ;

        // start the game
        mgc.start();
    }

    public void pauseApp()
    {
        mgc.pause();
    }

    public void destroyApp(boolean unconditional)
    {
        // Stop the Game
        mgc.stop();
        // Clear up resourses
        notifyDestroyed() ;
    }
}
