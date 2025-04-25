import mayflower.*;
import java.util.Random.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;

public class MyWorld extends World
{
    int song;
    double num;
    boolean switchedScreens = false;  
    public boolean changed = false;
    boolean started = false;
    boolean settings = false;
    boolean pickedSongSelect = false;
    boolean pickedSettings = false;
    private boolean inSongSelectScreen;
    private int noteDifficulty;
    public Accuracy acc;
    
    private String songFileOne;
    private String songFileTwo;
    private String secretSongFile;
    
    private Queue<int[]> noteQueue;
    
    public String songAudioChosen;
    
    private String infiniteSong = "songAudioFiles/infiniteModeSong.mp3";
    private String songOneAudio = "";
    private String songTwoAudio = "";
    private String secretSongAudio = "songAudioFiles/secretSong.mp3";

    //difficulties
    private int easy = 1000;
    private int normal = 500;
    private int hard = 300;
    private int extreme = 125;

    //songs
    private int RandomNotes = 0;
    private int SongOne = 1;
    private int SongTwo = 2;

    private int songSelected;

    //keybinds and note speed
    String oneKeybind;
    String twoKeybind;
    String threeKeybind;
    String fourKeybind;
    private int noteSpeed;

    public MyWorld()
    {      
        setBackground("img/TitleScreen.png");

        // default keybinds, can be changed by player in settings
        oneKeybind = "A";
        twoKeybind = "S";
        threeKeybind = "K";
        fourKeybind = "L";

        //songSelected = 0;

        songFileOne = "src/songOne.csv";
        songFileTwo = "src/songTwo.csv";
        secretSongFile = "src/secretSong.csv";

        //default noteSpeed
        noteSpeed = 20;

        //default difficultySpeed
        noteDifficulty = extreme;
    }

    public void act()
    {
        if(!changed)
        {
            menuScreen();
        }
        if(inSongSelectScreen && Mayflower.isKeyDown(Keyboard.KEY_D))
        {
            songSelected = RandomNotes;

            //play song
            songAudioChosen = infiniteSong;
            Mayflower.playMusic(songAudioChosen);

            changed = true;
            started = true;
            inSongSelectScreen = false;
            gameScreen();
        }
        if(inSongSelectScreen && Mayflower.isKeyDown(Keyboard.KEY_F))
        {
            songSelected = SongOne;

            //play song
            songAudioChosen = songOneAudio;
            Mayflower.playMusic(songAudioChosen);

            changed = true;
            started = true;
            inSongSelectScreen = false;
            noteQueue = readFileToQueue(songFileOne);
            gameScreen();
        }
        if(inSongSelectScreen && Mayflower.isKeyDown(Keyboard.KEY_G))
        {
            songSelected = SongTwo;

            //play song
            songAudioChosen = secretSongAudio;
            Mayflower.playMusic(songAudioChosen);

            changed = true;
            started = true;
            inSongSelectScreen = false;
            noteQueue = readFileToQueue(secretSongFile);
            gameScreen();
        }
        if(started && songSelected == RandomNotes)
        {
            /* random notes get played infinitely until stopped by
            player */
            num = Math.random();
            Note g = new Note(noteSpeed);
            if(num <= 0.25)
            {
                addObject(g, 172, -100);
            }
            else if(num > 0.25 && num <= 0.5)
            {
                addObject(g, 293, -100);
            }
            else if(num > 0.5 && num <= 0.75)
            {
                addObject(g, 415, -100);
            }
            else if(num > 0.75)
            {
                addObject(g, 537, -100);
            }
            Delay(noteDifficulty);
        }
        if(started && songSelected == SongOne && !noteQueue.isEmpty())
        {
            int[] currentRow = noteQueue.poll();

            Note g = new Note(noteSpeed);
            if (currentRow[0] == 1)
                addObject(g, 172, -100);
            if (currentRow[1] == 1)
                addObject(g, 293, -100);
            if (currentRow[2] == 1)
                addObject(g, 415, -100);
            if (currentRow[3] == 1)
                addObject(g, 537, -100);

            Delay(noteDifficulty);
        }
        if(started && songSelected == SongTwo && !noteQueue.isEmpty())
        {
            int[] currentRow = noteQueue.poll();

            Note g = new Note(noteSpeed);
            Note e = new Note(noteSpeed);
            Note b = new Note(noteSpeed);
            Note k = new Note(noteSpeed);
            
            if (currentRow[0] == 1) 
                addObject(g, 172, -100);
            if (currentRow[1] == 1) 
                addObject(g, 293, -100);
            if (currentRow[2] == 1) 
                addObject(g, 415, -100);
            if (currentRow[3] == 1) 
                addObject(g, 537, -100);
                
            if(currentRow[0] == 1 && currentRow[1] == 1 && currentRow[2] == 1 && currentRow[3] == 1)
            {
                addObject(g, 172, -100);
                addObject(e, 293, -150);
                addObject(b, 415, -200);
                addObject(k, 537, -250);
                //Delay(50);
            }
            
            Delay(noteDifficulty - 50);
        }
        if(settings)
        {

        }
        if(Mayflower.isKeyDown(Keyboard.KEY_Q))
        {
            resetWorld();
        }
    }

    public void menuScreen()
    {
        {
            if(Mayflower.isKeyDown(Keyboard.KEY_LEFT))
            {
                removeObjects(getObjects(null));
                setBackground("img/TitleScreenSettings.png");
                song = 1;
                switchedScreens = true;
                pickedSettings = true;
                pickedSongSelect = false;
            }
            else if(Mayflower.isKeyDown(Keyboard.KEY_RIGHT))
            {
                removeObjects(getObjects(null));
                setBackground("img/TitleScreenSong.png");
                song = 2;
                switchedScreens = true;
                pickedSongSelect = true;
                pickedSettings = false;
            }
            else if(Mayflower.isKeyDown(Keyboard.KEY_UP) && switchedScreens)
            {
                if(pickedSongSelect)
                {
                    removeObjects(getObjects(null));
                    setBackground("img/SongSelectScreen.png");
                    inSongSelectScreen = true;
                    pickedSongSelect = false;
                    switchedScreens = false;
                    //removeObjects(getObjects(null));
                    //setBackground("img/SongSelectScreen.png");
                    // changed = true;
                    // gameScreen();
                }
                else if(pickedSettings)
                {
                    removeObjects(getObjects(null));
                    setBackground("img/SettingsScreen.png");
                }
                //changed = true;
                //gameScreen();
            }    
        }
    }

    public void gameScreen()
    {
        setBackground("img/track.png");
        Meter m = new Meter(this);
        Accuracy acc = new Accuracy(this);
        Note n = new Note(noteSpeed);
        addObject(n, 172, 30);

        Hitbox left = new Hitbox(m, n, oneKeybind, acc, noteDifficulty, noteSpeed);
        Hitbox middleLeft = new Hitbox(m, n, twoKeybind, acc, noteDifficulty, noteSpeed);
        Hitbox middleRight = new Hitbox(m, n, threeKeybind, acc, noteDifficulty, noteSpeed);
        Hitbox right = new Hitbox(m, n, fourKeybind, acc, noteDifficulty, noteSpeed);
        DeleteNote d = new DeleteNote(m, n, acc);

        addObject(left, 214, 520);
        addObject(middleLeft, 335, 520);
        addObject(middleRight, 457, 520);
        addObject(right, 579, 520);
        addObject(d, 10, 750);

        addObject(m, 10, 30);
        addObject(acc, 10, 80);
        showMeter(m);
    }
    

    public void showMeter(Meter m)
    {
        showText("Meter: " + m.getMeter(), 10, 30, Color.WHITE);
        started = true;
    }

    public void endSong(boolean reset)
    {
        if(reset){
            World score = new ScoreScreen(this.acc);
            Mayflower.setWorld(score);
            // removeObjects(getObjects(null));
            // resetWorld();
        } else {
            changed = false;
        }
    }

    private void resetWorld()
    {
        setBackground("img/TitleScreen.png");
        removeObjects(getObjects(Actor.class));
        switchedScreens = false;
        changed = false;
        started = false;
        pickedSongSelect = false;
        pickedSettings = false;
        Mayflower.stopMusic(songAudioChosen);
    }

    public void Delay(int noteDifficulty)
    {
        started = false;
        // Credit to ChatGPT for writing this piece of code:
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                public void run() {
                    started = true;
                }
            }, noteDifficulty);
    }

    public Queue<int[]> readFileToQueue(String file)
    {
        Queue<int[]> songQueue = new LinkedList<>();
        BufferedReader reader = null;
        String line = "";
        try{
            reader = new BufferedReader(new FileReader(file));

            //this skips the first row that has string values
            reader.readLine();

            while((line = reader.readLine()) != null)
            {
                String[] values = line.trim().split(",");
                int[] row = new int[values.length];

                for (int i = 0; i < values.length; i++)
                {
                    row[i] = Integer.parseInt(values[i]);
                }

                songQueue.add(row);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return songQueue;
    }
    
}
