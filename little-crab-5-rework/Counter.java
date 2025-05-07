import greenfoot.*;

/**
 * A Counter class that displays a numeric value on screen.
 */
public class Counter extends Actor
{
    private int value = 0;
    private String prefix;
    
    public Counter()
    {
        this("");
    }
    
    public Counter(String prefix)
    {
        this.prefix = prefix;
        updateImage();
    }
    
    /**
     * Set a new value for the counter.
     */
    public void setValue(int newValue)
    {
        value = newValue;
        updateImage();
    }
    
    /**
     * Increase the counter by 1.
     */
    public void increment()
    {
        value++;
        updateImage();
    }
    
    /**
     * Get the current counter value.
     */
    public int getValue()
    {
        return value;
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