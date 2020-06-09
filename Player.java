import java.io.*;
import java.util.Scanner;
public class Player
{
    private String name;
    private int goal;
    public Player()
    {
        name = "";
        // initialise instance variable name
        goal = 0;
        // initialise instance variable goal
    }
    
    public Player(String playerName)
    {
        name = playerName;
        goal = 0;
    }
    
    public int getGoal()
    {
        return goal;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setGoal(int goals)
    {   
        goal = goals;
    }
    
    public void setName(String names)
    {   
        name = names;
    }
    
    public void display()
    {
        System.out.println("Name is "+name);
        System.out.println("Goal is "+goal);
    }
}

