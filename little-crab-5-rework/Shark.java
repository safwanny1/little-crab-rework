import greenfoot.*;

public class Shark extends Actor
{
    private int sharkSpeed;
    
    /**
     * Create a shark with specified movement speed.
     */
    public Shark(int speed)
    {
        sharkSpeed = speed;
        setImage("shark.png");
    }

    /**
     * Do whatever sharks do.
     */
    public void act()
    {
        // Only act if the game is not paused
        CrabWorld world = (CrabWorld)getWorld();
        if (world != null && !world.isPaused()) {
            turnAtEdge();
            randomTurn();
            move(sharkSpeed);
            lookForCrab();
        }
    }

    /**
     * Check whether we are at the edge of the world. If we are, turn a bit.
     * If not, do nothing.
     */
    public void turnAtEdge()
    {
        if (isAtEdge()) 
        {
            turn(17);
        }
    }

    /**
     * Randomly decide to turn from the current direction, or not. If we turn
     * turn a bit left or right by a random degree.
     */
    public void randomTurn()
    {
        if (Greenfoot.getRandomNumber(100) > 90) 
        {
            turn(Greenfoot.getRandomNumber(90)-45);
        }
    }

    /**
     * Try to pinch a crab. That is: check whether we have stumbled upon a crab.
     * If we have, notify the world that the game is over.
     */
    public void lookForCrab()
    {
        if (isTouching(Crab.class)) 
        {
            // Get the world and convert it to CrabWorld
            CrabWorld world = (CrabWorld) getWorld();
            
            // Tell the world that the game is over
            world.gameOver();
        }
    }
}
