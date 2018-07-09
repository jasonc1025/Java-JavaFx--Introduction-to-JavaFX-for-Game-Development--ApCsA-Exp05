// * Remove fields 'width' & 'height' to reduce fields and dynamically create value via 'getWidth()' & 'getHeight()'

package UfoCatchNReleasePackage;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class IconCls
{
    private Image  iconImageFld;
    private double positionXFld;
    private double positionYFld;
    private double velocityXFld;
    private double velocityYFld;

    public IconCls()
    {
        // [jwc] this("earth.png", 0, 0);  // Default position of (250,250)
        this("/UfoCatchNReleasePackage/earth.png", 0, 0);  // Default position of (250,250)
    }

    public IconCls(String imageFilenameIn)
    {
        this(imageFilenameIn, 0, 0);  // Default position of (0,0)
    }

    public IconCls(String imageFilenameIn, double positionXIn, double positionYIn)
    {
        this.setImage(imageFilenameIn);
        positionXFld = positionXIn;
        positionYFld = positionYIn;

        velocityXFld = 0;
        velocityYFld = 0;
    }


    public void setImage(Image i)
    {
        iconImageFld = i;
        // width = i.getWidth();
        // height = i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y)
    {
        positionXFld = x;
        positionYFld = y;
    }

    public void setVelocity(double x, double y)
    {
        velocityXFld = x;
        velocityYFld = y;
    }

    public void addVelocity(double x, double y)
    {
        velocityXFld += x;
        velocityYFld += y;
    }

    public void update(double time)
    {
        positionXFld += velocityXFld * time;
        positionYFld += velocityYFld * time;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( iconImageFld, positionXFld, positionYFld );
    }

    public Rectangle2D getBoundary()
    {
        // System.out.println("this: positionXFld,positionYFld,width,height: " + this  + ": " + positionXFld + "," + positionYFld + "," + width + "," + height);
        // System.out.println("this: positionXFld,positionYFld,width,height: " + this  + ": " + positionXFld + "," + positionYFld);
        // return new Rectangle2D(positionXFld,positionYFld,width,height);
        return new Rectangle2D(positionXFld, positionYFld, iconImageFld.getWidth(), iconImageFld.getHeight());
    }

    public boolean intersects(IconCls s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public String toString()
    {
        return " Position: [" + positionXFld + "," + positionYFld + "]"
                + " Velocity: [" + velocityXFld + "," + velocityYFld + "]";
    }
}