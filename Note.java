import mayflower.*;
import java.util.Timer;
import java.util.TimerTask;

public class Note extends Actor
{
    public boolean stopMovement;
    private int noteSpeed;
    
    public Note(int noteSpeed)
    {
        setImage("img/blueNote.png");
        stopMovement = false;
        this.noteSpeed = noteSpeed;
    }
    public void act()
    {
        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();
        if(!stopMovement)
        {
            setLocation(x, y + noteSpeed);
            outOfBounds();
        }
    }
    public void outOfBounds()
    {
        if(getY() > 750)
        {
            setLocation(10, 400);
            stopMovement = true;
        }
    }
}
