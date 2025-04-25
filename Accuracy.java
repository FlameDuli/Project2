import mayflower.*;

public class Accuracy extends Actor
{
    private String accuracyText;
    private String marvelous = "MARVELOUS";
    private String perfect = "PERFECT";
    private String great = "GREAT";
    private String okay = "OKAY";
    private String miss = "MISS";
    private String popUpAccuracy;
    private Color accColor;
    private double accuracy;
    private MyWorld myWorld;
    private int totalJudgedNotes;
    private int noteAccuracy;
    private double totalPossibleScore;
    private double actualScore;
    
    private static int marvelousTotal;
    private static int perfectsTotal;
    private static int greatsTotal;
    private static int okaysTotal;
    private static int missTotal;
    
    public Accuracy(MyWorld world)
    {
        this.myWorld = world;
        accuracy = 100.0;
        accuracyText = accuracy + "%";
        
        totalJudgedNotes = 0;
        noteAccuracy = 0;
        totalPossibleScore = 0.0;
        actualScore = 0.0;
        popUpAccuracy = "";
        accColor = Color.WHITE;
        
        updateText();
    }

    public void act()
    {
        
    }

    public void updateAccuracy(int noteAccuracy)
    {
        this.noteAccuracy = noteAccuracy;
        if(noteAccuracy == 4)
        {
            actualScore += 4.0;
            popUpAccuracy = marvelous;
            marvelousTotal += 1;
            accColor = Color.LIGHT_GRAY;
        }
        else if(noteAccuracy == 3)
        {
            actualScore += 3.0;
            popUpAccuracy = perfect;
            perfectsTotal += 1;
            accColor = Color.YELLOW;
        }
        else if(noteAccuracy == 2)
        {
            actualScore += 2.0;
            popUpAccuracy = great;
            greatsTotal += 1;
            accColor = Color.GREEN;
        }
        else if(noteAccuracy == 1)
        {
            actualScore += 1.0;
            popUpAccuracy = okay;
            okaysTotal += 1;
            accColor = Color.PINK;
        }
        else if(noteAccuracy == 0)
        {
            actualScore += 0.0;
            popUpAccuracy = miss;
            missTotal += 1;
            accColor = Color.RED;
        }
        
        totalJudgedNotes += 1;
        
        totalPossibleScore = totalJudgedNotes * 4.0;
        accuracy = 100 * (actualScore / totalPossibleScore);
        
        updateAccuracyText();
    }
    
    public void updateAccuracyText()
    {
        accuracyText = (int) accuracy + "%";
        updateText();
    }

    public double getAccuracy()
    {
        return accuracy;
    }

    private void updateText()
    {
        World w = getWorld();
        if (w != null)
        {
            w.showText("Accuracy: " + accuracyText, 10, 60, Color.WHITE);
            w.showText("" + popUpAccuracy, 350, 400, accColor);
            System.out.println("==== List of notes ====");
            System.out.println("Marvelous: " + marvelousTotal);
            System.out.println("Perfect: " + perfectsTotal);
            System.out.println("Great: " + greatsTotal);
            System.out.println("Bad: " + okaysTotal);
            System.out.println("Miss: " + missTotal);
        }
    }
}