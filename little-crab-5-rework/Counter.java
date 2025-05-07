import greenfoot.*;

/**
 * A Counter class that displays a numeric value on screen.
 */
public class Counter extends Actor
{
    private int startValue = 0;
    private String counterPrefix;
    
    public Counter()
    {
        counterPrefix("");
    }
    
    public Counter(String prefix)
    {
        counterPrefix = prefix;
        updateImage();
    }
    
    /**
     * Set a new value for the counter.
     */
    public void setValue(int newValue)
    {
        startValue = newValue;
        updateImage();
    }
    
    /**
     * Increase the counter by 1.
     */
    public void increment()
    {
        startValue++;
        updateImage();
    }
    
    /**
     * Get the current counter value.
     */
    public int getValue()
    {
        return startValue;
    }
    
    /**
     * Update the image to show the current value.
     */
    private void updateImage()
    {
        GreenfootImage image = new GreenfootImage(150, 30);
        image.setColor(greenfoot.Color.WHITE);
        image.fillRect(0, 0, 150, 30);
        image.setColor(greenfoot.Color.BLACK);
        image.drawString(prefix + value, 5, 20);
        setImage(image);
    }
}
