import mayflower.*;
import java.util.HashMap;
import java.util.Map;

public class Hitbox extends Actor
{
    private Meter m;
    private Note note;
    private String key;
    private Accuracy acc;
    private int keyCode;
    private int noteDifficulty;
    
    private double frames;
    private double framesTotal;

    private static final Map<String, Integer> keyMap = new HashMap<>();
    private static final Map<Integer, Boolean> keyWasDown = new HashMap<>();

    static {
        keyMap.put("A", Keyboard.KEY_A);
        keyMap.put("S", Keyboard.KEY_S);
        keyMap.put("K", Keyboard.KEY_K);
        keyMap.put("L", Keyboard.KEY_L);

        for (int code : keyMap.values()) {
            keyWasDown.put(code, false);
        }
    }

    public Hitbox(Meter meter, Note note, String key, Accuracy acc, int noteDifficulty, int noteSpeed)
    {
        this.m = meter;
        this.note = note;
        this.key = key;
        this.acc = acc;
        this.keyCode = keyMap.getOrDefault(key.toUpperCase(), Keyboard.KEY_A); 
        
        // Frame timing based on noteSpeed
        framesTotal = 156 * Math.pow(noteSpeed, -0.979);
        frames = 0.0;

        setImage("img/HitboxThing.png");
    }

    public void act()
    {
        World w = getWorld();
        boolean keyDown = Mayflower.isKeyDown(keyCode);
        boolean keyJustPressed = keyDown && !keyWasDown.get(keyCode);

        Object a = getOneIntersectingObject(Note.class);
        Note n = (Note) a;

        if (n != null)
        {
            frames += 1.0;
            if (keyJustPressed)
            {
                w.removeObject(n);
                m.addMeter(1);

                if(frames <= (framesTotal * 0.1) || frames >= (framesTotal * 0.9))
                    acc.updateAccuracy(1);
                else if(frames <= (framesTotal * 0.2) || frames >= (framesTotal * 0.8))
                    acc.updateAccuracy(2);
                else if(frames <= (framesTotal * 0.3) || frames >= (framesTotal * 0.7))
                    acc.updateAccuracy(3);
                else
                    acc.updateAccuracy(4);

                frames = 0.0;
            }
        }
        else if (keyJustPressed)
        {
            m.subtractMeter(1);
            acc.updateAccuracy(0);
            frames = 0.0;
            
            if (n != null)
            {
                w.removeObject(n);
            }
        }

        if (note.getX() < 100 && isTouching(Note.class))
        {
            m.subtractMeter(1);
            acc.updateAccuracy(0);
            frames = 0.0;
            
            if (n != null)
            {
                w.removeObject(n);
            }
        }

        keyWasDown.put(keyCode, keyDown);
    }
}
