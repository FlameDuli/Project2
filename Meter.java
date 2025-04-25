import mayflower.*;

public class Meter extends Actor
{
    private int meter;
    private int combo;
    private boolean missed;
    private int maxCombo;
    
    private MyWorld myWorld;

    public Meter(MyWorld world)
    {
        this.myWorld = world;
        meter = 10;
        combo = 0;
        maxCombo = 0;
        missed = false;
        updateText(); 
    }

    public void act()
    {
        
    }

    public void addMeter(int num)
    {
        if(meter < 10)
        {
            meter += num;
        }
        if(!missed)
        {
            combo += 1;
            if(combo > maxCombo)
                maxCombo = combo;
        }
        missed = false;
        updateText();
    }

    public void subtractMeter(int num)
    {
        meter -= num;
        missed = true;
        combo = 0;
        updateText();
        if(meter <= 0)
        {
            myWorld.endSong(true);
        }
    }

    public int getMeter()
    {
        return meter;
    }

    private void updateText()
    {
        World w = getWorld();
        if (w != null)
        {
            w.showText("Meter: " + meter, 10, 30, Color.WHITE);
            w.showText("Combo: " + combo, 10, 400, Color.WHITE);
            w.showText("Max Combo: " + maxCombo, 10, 450, Color.YELLOW);
            
        }
    }
}