import greenfoot.*;

public class CrabWorld extends World
{
    // Fields for counters
    private int currentLevel = 1;
    private int wormsNeeded;
    private int score = 0;
    private int attempts = 0;
    private int highestLevel = 1;
    private int highestScore = 0;
    
    // Text objects to display game info
    private Counter levelCounter;
    private Counter scoreCounter;
    private Counter attemptsCounter;
    private Counter highScoreCounter;
    
    // State management
    private boolean isPaused = false;
    private boolean gameOverState = false;
    private boolean levelCompletedState = false;
    
    public CrabWorld() 
    {
        super(560, 560, 1);
        
        GreenfootImage background = new GreenfootImage("underwater.jpg");
        setBackground(background);
        
        wormsNeeded = 5;  // Initial worms needed - starting a bit easier
        setupInfoDisplay();
        prepareLevel();
        attempts++;
        updateCounters();
    }
    
    /**
     * Act method for the world - called with each step of the simulation
     */
    public void act()
    {
        // Only check for key press if game is paused due to level completion or game over
        if (isPaused && Greenfoot.isKeyDown("space"))
        {
            if (gameOverState) {
                // Reset the game
                gameOverState = false;
                isPaused = false;
                currentLevel = 1;
                wormsNeeded = 5;  // Reset to initial worms needed
                score = 0;  // Reset score for new game
                prepareLevel();
            } 
            else if (levelCompletedState) {
                // Start next level
                levelCompletedState = false;
                isPaused = false;
                prepareLevel();
            }
            
            showText("", getWidth()/2, getHeight()/2);  // Remove the text
        }
    }
    
    /**
     * Set up the counters that display game information
     */
    private void setupInfoDisplay()
    {
        levelCounter = new Counter("Level: ");
        addObject(levelCounter, 85, 20);
        
        scoreCounter = new Counter("Score: ");
        addObject(scoreCounter, 205, 20);
        
        attemptsCounter = new Counter("Attempts: ");
        addObject(attemptsCounter, 325, 20);
        
        highScoreCounter = new Counter("High Score: ");
        addObject(highScoreCounter, 475, 20);
    }
    
    /**
     * Update all the counter displays
     */
    private void updateCounters()
    {
        levelCounter.setValue(currentLevel);
        scoreCounter.setValue(score);
        attemptsCounter.setValue(attempts);
        highScoreCounter.setValue(highestScore);
    }
    
    /**
     * Calculate the number of lobsters for the current level
     * Following pattern: add lobster, add lobster, speed up, add lobster, add lobster, speed up...
     */
    private int calculateLobsterCount() {
        // Every third level increases speed instead of adding lobsters
        // Level 1: 1 lobster, Level 2: 2 lobsters, Level 3: 2 faster lobsters, etc.
        return (currentLevel + 1) / 2 + (currentLevel <= 2 ? 0 : 1);
    }
    
    /**
     * Calculate the speed of lobsters for the current level
     * Speed increases every 3rd level
     */
    private int calculateLobsterSpeed() {
        // Start with speed 3, increase by 1 every third level
        return 3 + (currentLevel - 1) / 3;
    }
    
    /**
     * Prepare the world for the current level.
     */
    public void prepareLevel()
    {
        removeObjects(getObjects(null));  // Clear the world
        
        // Re-add the counters
        setupInfoDisplay();
        updateCounters();
        
        // Add the player crab
        Crab crab = new Crab(this);
        addObject(crab, getWidth() / 2, getHeight() / 2);
        
        // Add worms - number based on level
        for (int i = 0; i < wormsNeeded; i++) {
            addWormAtRandomPosition();
        }
        
        // Add balanced number of lobsters with appropriate speed
        int numLobsters = calculateLobsterCount();
        int lobsterSpeed = calculateLobsterSpeed();
        
        for (int i = 0; i < numLobsters; i++) {
            addLobsterAtRandomPosition(lobsterSpeed);
        }
    }
    
    /**
     * Add a worm at a random position that doesn't overlap with other objects
     */
    private void addWormAtRandomPosition() {
        int x, y;
        boolean validPosition;
        
        do {
            x = Greenfoot.getRandomNumber(getWidth());
            y = Greenfoot.getRandomNumber(getHeight());
            
            // Keep the worms away from the center where the crab starts
            validPosition = (Math.abs(x - getWidth()/2) > 100 || 
                            Math.abs(y - getHeight()/2) > 100);
            
        } while (!validPosition);
        
        addObject(new Fish(), x, y);
    }
    
    /**
     * Add a lobster at a random position with specified speed
     */
    private void addLobsterAtRandomPosition(int speed) {
        int x = Greenfoot.getRandomNumber(getWidth());
        int y = Greenfoot.getRandomNumber(getHeight());
        
        // Keep lobsters away from the center where the crab starts
        if (Math.abs(x - getWidth()/2) < 150 && Math.abs(y - getHeight()/2) < 150) {
            // If too close to center, move to a corner
            x = x < getWidth()/2 ? Greenfoot.getRandomNumber(150) : getWidth() - Greenfoot.getRandomNumber(150);
            y = y < getHeight()/2 ? Greenfoot.getRandomNumber(150) : getHeight() - Greenfoot.getRandomNumber(150);
        }
        
        Shark shark = new Shark(speed);
        addObject(shark, x, y);
    }
    
    /**
     * Called when the player completes a level
     */
    public void levelCompleted() {
        Greenfoot.playSound("fanfare.wav");
        
        // Add score for completing the level
        score += 10;  // 10 points for completing a level
        
        currentLevel++;
        
        // Update highest level if current level is higher
        if (currentLevel > highestLevel) {
            highestLevel = currentLevel;
        }
        
        // Increase difficulty
        wormsNeeded = Math.min(5 + currentLevel, 15);  // Start with 5, cap at 15 worms
        
        // Display level completed message
        showText("Level " + (currentLevel-1) + " completed! Press SPACE to continue.", getWidth()/2, getHeight()/2);
        
        // Set state flags
        levelCompletedState = true;
        isPaused = true;
        
        // Freeze all actors by removing them (keeping only the message)
        removeObjects(getObjects(Actor.class));
        
        updateCounters();
    }
    
    /**
     * Called when the player dies
     */
    public void gameOver() {
        Greenfoot.playSound("au.wav");
        
        // Update high score if current score is higher
        if (score > highestScore) {
            highestScore = score;
        }
        
        // Display game over message
        showText("Game Over! Press SPACE to try again.", getWidth()/2, getHeight()/2);
        
        // Set state flags
        gameOverState = true;
        isPaused = true;
        attempts++;
        
        // Freeze all actors by removing them (keeping only the message)
        removeObjects(getObjects(Actor.class));
        
        updateCounters();
    }
    
    /**
     * Check if the game is currently paused
     */
    public boolean isPaused()
    {
        return isPaused;
    }
    
    /**
     * Add points to the score (called when eating worms)
     */
    public void addScore(int points)
    {
        score += points;
        updateCounters();
    }
    
    public int getWormsNeeded() {
        return wormsNeeded;
    }
}