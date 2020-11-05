/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.microedition.midlet.* ;
import javax.microedition.lcdui.* ;
import javax.microedition.lcdui.game.* ;
/**
 *
 * @author Agata
 */
public class MyGameCanvas  extends GameCanvas implements Runnable {

  // is the game running
    private boolean isPlaying;
    private int hello = 0;

    // the Graphics context
    private Graphics gContext;

    // size of current screen
    private int height;
    private int width;
    private int bg_height = 295;
    private int bg_width = 522;

    // location of the box
    private int x ;
    private int y ;
    private int x2 ;
    private int y2 ;

    private int bx;
    private int by;
    private int bx2;
    private int by2;

    // distance the sprite moves
    private int step = 4 ;
    private double kat1 = 0;
    private double kat2 = 0;

    // a Sprite
    private Sprite sprite ;
    private Sprite sprite2 ;
    private Sprite beczka;
    private Sprite beczka2 ;
    private Sprite witaj;
    private Sprite zegnaj;

    private LayerManager m_layerManager;

    // The Frame Sequence Constants
    private int[] STAND = {0} ;
    private int[] WALK  = {0,1, 2, 3, 4, 5, 6} ;

    // The Layer Manager
    private LayerManager layerMan = new LayerManager() ;

    // Background Layer
    private Sprite bgSprite ;
    private Sprite piasek;

    // Direction of movment
    private int spriteDir1 ;
    private int spriteDir2;
    private int LEFT  = 0 ;
    private int RIGHT = 1 ;
    private int ktory = 1;
    private int rusza_strzela = 0;

    private Gun pocisk;
    private int moc = 1;
    private boolean strzelanie = false;

   // private Graphics m_gContext;
    private int stat1 = 0;
    private int stat2 = 0;

    private String gracza;
    private String graczb;


    // constructor
    public MyGameCanvas() {
        // tell GameCanvas
        super( true ) ;
        // initalise utility variables.
        this.height = getHeight();
        this.width = getWidth() ;
        m_layerManager = new LayerManager();

        // Load Image (Can cause errors because it is accessing hardware)
        try {
             witaj = new Sprite(Image.createImage("/powitaj.png"));
             zegnaj = new Sprite(Image.createImage("/zegnaj.PNG"));
             //m_layerManager.append(witaj);
            // load image for player
            Image tempImagecz = Image.createImage("/czolglufa.png") ;
            Image tempImage2cz = Image.createImage("/czolglufa2.png") ;

            Image tempImageb = Image.createImage("/beczka1.png") ;
            Image tempImage2b = Image.createImage("/beczka2.png") ;


            // create player Sprite
            this.sprite = new Sprite( tempImagecz, 64, 64 ) ;
            this.sprite2 = new Sprite( tempImage2cz, 64, 64 ) ;
            this.beczka = new Sprite( tempImageb, 45, 68) ;
            this.beczka2 = new Sprite( tempImage2b, 80, 60 ) ;

            this.sprite.setFrameSequence(WALK);
            this.sprite2.setFrameSequence(WALK);



            // create background sprite
            Image temp2Image = Image.createImage("/tlo.PNG") ;
            this.bgSprite = new Sprite( temp2Image ) ;
            temp2Image = Image.createImage("/piasek.png");
            this.piasek = new Sprite(temp2Image);
        } catch( Exception e ) {
            //
        }

        pocisk= new Gun();

        // Not walking


        // Get ready to start
        this.isPlaying = false;

        // load Graphics context
        this.gContext = getGraphics();

        // Set up Layers
        this.layerMan.append( this.sprite ) ;
        this.layerMan.append( this.sprite2 ) ;
        this.layerMan.append( this.beczka ) ;
        this.layerMan.append( this.beczka2 ) ;
        this.layerMan.append(pocisk.getSprite());
        this.layerMan.append(this.piasek);
        this.layerMan.append( this.bgSprite ) ;
        
       

        // set direction of sprits movment
        this.spriteDir1 = this.RIGHT ;
        this.spriteDir2 = this.LEFT;
    }


    public void start() {
        isPlaying = true;

        // Thread ref
        Thread temp = new Thread(this);
        temp.start();
    }


    public void stop() {
        // halt the Game loop
        this.isPlaying = false;
    }


    public void pause() {
        this.isPlaying = false;
    }

    public void setPlayer(String playa, String playb){
        gracza = playa;
        graczb = playb;
    }

    // method inherited from Runnable.  This is
    public void run() {
        // location of the sprite 100px from left, on the ground.
        // this is due to the simple nature of the graphics currently
        // used.  as we expand this program in complexity, we will need to
        // make allowences for unknown screen sizes.
        this.x = 30 ;
        this.y = 168 ;

        this.x2 = 450 ;
        this.y2 = 174 ;
        this.bx = 220;
        this.by =180;
        this.bx2 = 240;
        this.by2 =190;

        // the Game loop
        while( this.isPlaying ) {
            // deal with inputs
            processKeys() ;
            // play game
            updateGame() ;
            // Update display
            paint() ;
            // delay for controlling animation speed
            try {
                Thread.sleep( 50 ) ;
            } catch( Exception e ) {
                //
            }
        } // while isPlaying
    } // run


    // see if any keys have been pressed
    private void processKeys() {
        // see if akey has been pressed
        int keyStates = getKeyStates();

        if ( (keyStates & FIRE_PRESSED) != 0) {

            if(rusza_strzela ==2){
                if(strzelanie){
                    if(moc<10) moc+=1;

                    else{
                        double dy, dx;

                        if(ktory ==1){

                            dy=Math.sin(kat1)*10;
                            dx= Math.cos(kat1)*10;
                            pocisk.strzelaj(x+64+(int)dx,y+35-(int)dy,kat1,moc,ktory);
                            ktory=2;
                        }
                        else{
                            dy = Math.sin(kat2)*10;
                            dx = Math.cos(kat2)*10;
                            pocisk.strzelaj(x2+(int)dx,y2+35-(int)dy,kat2,moc,ktory);
                            ktory=1;
                        }
                         strzelanie = false;
                    }
                }
                else{
                    strzelanie= true;
                    moc = 1;
                }

            }
            else{
            if(ktory==1) ktory =2;
            else ktory =1;
            rusza_strzela=2;
            if(hello!=1)hello=1;
            }
        }
        else if(strzelanie){
            strzelanie = false;
            double dy, dx;

                        if(ktory ==1){

                            dy=Math.sin(kat1)*10;
                            dx= Math.cos(kat1)*10;
                            pocisk.strzelaj(x+64+(int)dx,y+35-(int)dy,kat1,moc,ktory);
                            ktory=2;
                        }
                        else{
                            dy = Math.sin(kat2)*14;
                            dx = Math.cos(kat2)*14;
                            pocisk.strzelaj(x2+(int)dx,y2+35-(int)dy,kat2,moc,ktory);
                            ktory=1;
                        }
              //rusza_strzela = 0;
        }

        if ( (keyStates & LEFT_PRESSED) != 0 && ktory==1) {
            this.x -= this.step ;
            this.spriteDir1 = this.LEFT ;
            rusza_strzela = 1;
        }
        if ( (keyStates & LEFT_PRESSED) != 0 && ktory==2) {
            this.x2 -= this.step ;
            this.spriteDir2 = this.LEFT ;
            rusza_strzela = 1;
        }
        if ( (keyStates & RIGHT_PRESSED) != 0 && ktory==1) {
            this.x += this.step ;
            this.spriteDir1 = this.RIGHT ;
            rusza_strzela = 1;
        }
        if ( (keyStates & RIGHT_PRESSED) != 0 && ktory==2) {
            this.x2 += this.step ;
            this.spriteDir2 = this.RIGHT ;
            rusza_strzela = 1;
        }
        if ( (keyStates & UP_PRESSED) != 0 && this.sprite.getFrame() <6 && ktory ==1 && rusza_strzela!=1) {
            // no up movement
            sprite.nextFrame();
            kat1 += 3.14/12;
            rusza_strzela = 2;
        }
        if ( (keyStates & UP_PRESSED) != 0 && this.sprite2.getFrame()<6 && ktory ==2 && rusza_strzela!=1) {
            // no up movement
            sprite2.nextFrame();
            kat2 += 3.14/12;
            rusza_strzela = 2;
        }
        if ( (keyStates & DOWN_PRESSED) != 0 && this.sprite.getFrame() >0 && ktory ==1 && rusza_strzela!=1) {
            // no down movement
            sprite.prevFrame();
            kat1 -= 3.14/12;
            rusza_strzela = 2;
        }
        if ( (keyStates & DOWN_PRESSED) != 0 && this.sprite2.getFrame()>0 && ktory ==2 && rusza_strzela!=1) {
            // no down movement
            sprite2.prevFrame();
            kat2 -= 3.14/12;
            rusza_strzela = 2;
        }

        if( (keyStates & GAME_A_PRESSED) != 0){ //1
            this.isPlaying = false;
        }

        
    } // deal with keys


    private void updateGame() {
        // Constrain Sprite to screen
        if( this.width == (this. x +64) ) {
            this.x -= this.step ;
        } else if( this.x < 0 ) {
            this.x += this.step ;
        }

        // animate Walk cycle
        /*if( this.isWalking ) {
            // if the sprite was standing, it must begin the walk cycle
            if( this.sprite.getFrame() == 0 ) {
                this.sprite.setFrameSequence( this.WALK ) ;
            }
            //loop animation sequence
            this.sprite.nextFrame() ;
        } else {
            if(this.sprite.getFrame() != 0 ) {
                this.sprite.nextFrame() ;
                //complete animation sequence before setting stand position
                if( this.sprite.getFrame() == 7 && !this.isWalking ) {
                    this.sprite.setFrameSequence( this.STAND ) ;
                }
            }
        }*/

        // transform Sprite, dependent on LEFT or RIGHT direction
        if( this.spriteDir1 == this.RIGHT ) {
            this.sprite.setTransform( Sprite.TRANS_NONE ) ;
        } else {
            this.sprite.setTransform( Sprite.TRANS_MIRROR ) ;
        }
        if( this.spriteDir2 == this.LEFT ) {
            this.sprite2.setTransform( Sprite.TRANS_NONE ) ;
        } else {
            this.sprite2.setTransform( Sprite.TRANS_MIRROR ) ;
        }


        pocisk.update();

        if(pocisk.getSprite().collidesWith(beczka, true) || pocisk.getSprite().collidesWith(beczka2, true))
        {
            pocisk.getSprite().setVisible(false);
        }
        if(pocisk.getSprite().collidesWith(this.sprite, true))
        {
            System.out.println("wygral II");
            pocisk.getSprite().setVisible(false);
            stat2+=1;
            hello = 2;

        }
        if(pocisk.getSprite().collidesWith(this.sprite2, true))
        {
            System.out.println("wygral I");
            pocisk.getSprite().setVisible(false);
            stat1+=1;
            hello = 2;
        }


    }


    // Update Display
    private void paint() {

        // clear screen
        //this.gContext.setColor( 20, 20, 200 ) ;
        //this.gContext.fillRect( 0, 0, this.width, this.height ) ;
        // Display Sprite
        if (hello==0){
            this.witaj.paint(gContext);

        }
      
        else if(hello==1){

        this.sprite.setPosition( this.x, this.y ) ;
        this.sprite2.setPosition( this.x2, this.y2 ) ;
        this.beczka.setPosition( this.bx, this.by ) ;
        this.beczka2.setPosition( this.bx2, this.by2 ) ;
        this.piasek.setPosition(0,170);

        int ix, iy = 0;
        if(this.pocisk.getSprite().isVisible()){
            ix = this.getWidth() / 2 - this.pocisk.getSprite().getX();
            iy = this.getHeight() / 2 - this.pocisk.getSprite().getY();
        }
        else if(ktory==1)
        {
            ix = (int) (this.getWidth()/2-this.x);

            if(ix<this.width-this.bg_width)ix= this.width-this.bg_width;
            if(ix>0)ix=0;
            if(x>160) x=160;
            if(x<0) x=0;

        } else
        {
            ix = (int) (this.getWidth()/2-this.x2);

            if(ix<this.width-this.bg_width)ix= this.width-this.bg_width;
            if(ix>0)ix=0;
            if(x2>460) x2=460;
            if(x2<310) x2=310;
        }
        if(ix < this.getWidth() - 522) ix = this.getWidth() - 522;
        if(ix > 0) ix = 0;
        if(iy < this.getHeight() - 295) iy = this.getHeight() - 295;
        if(iy > 0) iy = 0;

        // not needed anymore
        //this.sprite.paint( this.gContext ) ;

        this.layerMan.paint( this.gContext, ix, iy ) ;

        if(strzelanie){
            this.gContext.setColor(240,0,30);
            this.gContext.fillRect(this.getWidth()/2-50,this.getHeight()/2-80,10*this.moc,20);
        }
        }
        else{
            this.zegnaj.paint(gContext);
            this.gContext.setFont(Font.getFont(Font.FACE_MONOSPACE,Font.STYLE_BOLD,Font.SIZE_LARGE));
            gContext.drawString(gracza + " : " + stat1, width/2 - 52, height / 2+10, Graphics.TOP|Graphics.LEFT);
            gContext.drawString(graczb + " : " + stat2, width/2 - 52, height / 2+35, Graphics.TOP|Graphics.LEFT);
            rusza_strzela = 0;
        }
        //
        flushGraphics();
    }
}
