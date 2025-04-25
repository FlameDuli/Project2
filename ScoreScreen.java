import mayflower.*;

public class ScoreScreen extends MyWorld
{
    /**
     * Constructor for objects of class ScoreScreen
     */
    public Accuracy acc;
    public ScoreScreen(Accuracy acc)
    {
        this.acc = acc;
        switchedScreens = false;
        changed = false;
        started = false;
        pickedSongSelect = false;
        pickedSettings = false;
    }

    public void act(){
        Mayflower.stopMusic(songAudioChosen);
        updateText();
        World title = new MyWorld();
        if(Mayflower.isKeyDown(Keyboard.KEY_SPACE)){
            Mayflower.setWorld(title);
        }
    }
    
    private void updateText(){
        World w = getWorld();
        w.showText("==== List of notes ====", 10, 200, Color.WHITE);
        w.showText("Marvelous: " + marvelousTotal, 10, 300, Color.WHITE);
        w.showText("Perfect: " + perfectsTotal, 10, 350, Color.WHITE);
        w.showText("Great: " + greatsTotal, 10, 400, Color.WHITE);
        w.showText("Bad: " + okaysTotal, 10, 450, Color.WHITE);
        w.showText("Miss: " + missTotal, 10, 500, Color.WHITE);
    }
}
