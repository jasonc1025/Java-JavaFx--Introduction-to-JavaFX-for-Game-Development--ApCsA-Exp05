/*

* TODO
*
* Abstract
    * IconCls

* Interface: Feature
*
    * Abstract Method: Gerund: Return State
        * Collidable: Colliding
        *
    *  Method: Action: Verb
        * Rendarable Class: Render
*/




package UfoCatchNReleasePackage;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

// * 'UfoCatchNReleaseClassApp' the entry-point for this app
public class UfoCatchNReleaseClassApp extends Application
{
    public static final int SCREEN_LENGTH_X = 500;
    public static final int SCREEN_LENGTH_Y = 500;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "UFO Catch n Release" );
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        // Canvas canvas = new Canvas( 512, 512 );
        Canvas canvas = new Canvas( SCREEN_LENGTH_X, SCREEN_LENGTH_Y );
        root.getChildren().add( canvas );

        ArrayList<String> playerInputList = new ArrayList<String>();

        theScene.setOnKeyPressed(new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String playerInputCode = e.getCode().toString();
                    if ( !playerInputList.contains(playerInputCode) ) {
                        playerInputList.add( playerInputCode );
                    }
                }
            });

        theScene.setOnKeyReleased(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String playerInputCode = e.getCode().toString();
                    playerInputList.remove( playerInputCode );
                }
            });

        GraphicsContext graphicContextObj = canvas.getGraphicsContext2D();

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        graphicContextObj.setFont( theFont );
        graphicContextObj.setFill( Color.GREEN );
        graphicContextObj.setStroke( Color.BLACK );
        graphicContextObj.setLineWidth(1);

        IconCls earthObj = new IconCls();

        // IconCls briefcaseObj = new IconCls();
        // briefcaseObj.setImage("briefcase.png");
        // IconCls briefcaseObj = new IconCls("briefcase.png", 0, 0);
        IconCls briefcaseObj = new IconCls("/UfoCatchNReleasePackage/briefcase.png");
        // briefcaseObj.setImage("briefcase.png");
        // briefcaseObj.setPosition(200, 0);

        ArrayList<IconCls> ufoList = new ArrayList<IconCls>();

        // Y for (int i = 0; i < 15; i++)
        for (int i = 0; i < 10; i++)
        {
            // IconCls ufoObj = new IconCls();
            //[jwc] moneybag.setImage("moneybag.png");
            //[jwc] whitebackground not great: moneybag.setImage("cookie-chocolatechip.png");
            // ufoObj.setImage("ufo.png");
            // double px = 350 * Math.random() + 50;
            // double py = 350 * Math.random() + 50;

            // * Do not embed this calculation within Constructor since will complicate it and rather teach simplest Constrcutor
            double px = (SCREEN_LENGTH_X -100) * Math.random() + 50;
            double py = (SCREEN_LENGTH_Y -100) * Math.random() + 50;

            // ufoObj.setPosition(px,py);
            // IconCls ufoObj = new IconCls("ufo.png", px, py);
            IconCls ufoObj = new IconCls("/UfoCatchNReleasePackage/ufo.png", px, py);
            ufoList.add( ufoObj );
        }

        LongValue lastNanoTime = new LongValue( System.nanoTime() );

        IntValue score = new IntValue(0);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                // game logic

                // * May not be needed since stationary, but still 'update' in case may move later
                earthObj.update(elapsedTime);

                briefcaseObj.setVelocity(0,0);
                if (playerInputList.contains("LEFT"))
                    briefcaseObj.addVelocity(-50,0);
                if (playerInputList.contains("RIGHT"))
                    briefcaseObj.addVelocity(50,0);
                if (playerInputList.contains("UP"))
                    briefcaseObj.addVelocity(0,-50);
                if (playerInputList.contains("DOWN"))
                    briefcaseObj.addVelocity(0,50);

                briefcaseObj.update(elapsedTime);

                // collision detection
                // * 'Iterator<>' needed since will write into it with 'ufoIterator.remove()'
                Iterator<IconCls> ufoIterator = ufoList.iterator();
                while ( ufoIterator.hasNext() )
                {
                    IconCls ufoObj = ufoIterator.next();
                    if ( briefcaseObj.intersects(ufoObj) )
                    {
                        ufoIterator.remove();
                        score.value++;
                    }
                }

                // render

                graphicContextObj.clearRect(0, 0, 512,512);
                earthObj.render( graphicContextObj );
                briefcaseObj.render( graphicContextObj );


                // * Use 'for' loop sufficient since read-only and not need to write ('Iterator<>')
                for (IconCls ufoObj : ufoList )
                    ufoObj.render( graphicContextObj );

                //[jwc] String pointsText = "Cash: $" + (100 * score.value);
                String pointsText = "UFOs: " + (1 * score.value);
                //[jwc] gc.fillText( pointsText, 360, 36 );
                graphicContextObj.fillText( pointsText, 370, 36 );
                //[jwc] gc.strokeText( pointsText, 360, 36 );
                graphicContextObj.strokeText( pointsText, 370, 36 );
            }
        }.start();

        theStage.show();
    }
}