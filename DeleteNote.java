import mayflower.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;
import java.util.Map;

public class DeleteNote extends Actor
{
    private Meter m;
    private boolean endNote;
    private Note note;
    private Accuracy acc;

    public DeleteNote(Meter meter, Note note, Accuracy acc)
    {
        this.m = meter;
        this.acc = acc;
        this.note = note;
        endNote = false;
        setImage("img/Hitbox.png");
    }
    public void act()
    {
        Object a = getOneIntersectingObject(Note.class);
        Note n = (Note) a;

        World w = getWorld();
        if(isTouching(Note.class))
        {
            w.removeObject(n);
            m.subtractMeter(1);
            acc.updateAccuracy(0);
        }
    }

}
