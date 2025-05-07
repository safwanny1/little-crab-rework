import greenfoot.*;

public class Crab extends Actor
{
    private GreenfootImage image1;
    private GreenfootImage image2;
    private int wormsEaten;
    private CrabWorld myWorld;
    
    /**
     * Create a crab and initialize its two images.
     */
    public Crab(CrabWorld world)
    {
        image1 = new GreenfootImage("crab.png");
        image2 = new GreenfootImage("crab2.png");
        setImage(image1);
        wormsEaten = 0;
        myWorld = world;
    }
        
    /** 
     * Act - do whatever the crab wants to do. This method is called whenever
     *  the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Only act if the game is not paused
        if (!myWorld.isPaused()) {
            checkKeypress();
            move(5);
            checkEdges();
            lookForWorm();
            switchImage();
        }
    }
    
    /**
     * Check if the crab is at the edge of the world and wrap to the opposite side.
     */
    public void checkEdges()
    {
        World world = getWorld();
        if (getX() <= 0) {
            setLocation(world.getWidth() - 1, getY());
        } 
        else if (getX() >= world.getWidth() - 1) {
            setLocation(0, getY());
        }
        
        if (getY() <= 0) {
            setLocation(getX(), world.getHeight() - 1);
        } 
        else if (getY() >= world.getHeight() - 1) {
            setLocation(getX(), 0);
        }
    }
    
    /**
     * Alternate the crab's image between image1 and image2.
     */
    public void switchImage()
    {
        if (getImage() == image1) 
        {
            setImage(image2);
        }
        else
        {
            setImage(image1);
        }
    }
            
    /**
     * Check whether a control key on the keyboard has been pressed.
     * If it has, react accordingly.
     */
    public void checkKeypress()
    {
        if (Greenfoot.isKeyDown("left")) 
        {
            turn(-4);
        }
        if (Greenfoot.isKeyDown("right")) 
        {
            turn(4);
        }
    }
    
    /**
     * Check whether we have stumbled upon a worm.
     * If we have, eat it. If we have eaten enough worms to complete
     * the level, tell the world we're done.
     */
    public void lookForWorm()
    {
        if (isTouching(Fish.class)) 
        {
            removeTouching(Fish.class);
            Greenfoot.playSound("slurp.wav");
            
            wormsEaten++;
            myWorld.addScore(1);  // Add 1 point for each worm eaten
            
            // Check if we've eaten enough worms to complete the level
            if (wormsEaten >= myWorld.getWormsNeeded()) 
            {
                myWorld.levelCompleted();
            }
        }
    }
}