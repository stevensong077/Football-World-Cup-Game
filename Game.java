import java.util.ArrayList            ;
import java.io.*;
import java.util.Scanner;
public class Game
{   
    private ArrayList<Team> teams;        
    public Game()
    {
        teams = new ArrayList<Team>();
    }
    
    //assume that one team can get 0-3 yellow card and 0-3 red card in one game
    public void card(Team team)
    {   
        RandomGoalsGenerator random = new RandomGoalsGenerator();
        for (int i = 1; i <= 3 ; i++)
        {   
            int yellow = random.generateRandomNumber(3);
            int red = random.generateRandomNumber(12);
            if(yellow == 1)
            {
                int yellowCard =team.getYellowCard();
                team.setYellowCard(yellowCard + 1);
            }
            if(red == 1)
            {
                int redCard =team.getRedCard();
                team.setRedCard(redCard + 1);
            }
        }
    }
    
    // to set one team the card mark
    public void cardMark(Team team)
    {  
        int mark = team.getYellowCard() + (2 * team.getRedCard());
        team.setCardMark(mark);
    }
    
    //simulate two teams to play a game
    public void playGame(Team teamFirst, Team teamSecond)
    {   
        // goal that team already score
        int goal1 = teamFirst.getGoalScore();
        int goal2 = teamSecond.getGoalScore();
        // card that team already get
        int red1 = teamFirst.getRedCard();
        int red2 = teamSecond.getRedCard();
        int yellow1 = teamFirst.getYellowCard();
        int yellow2 = teamSecond.getYellowCard();
        // rank difference 
        int difference = teamFirst.getRanking() - teamSecond.getRanking();
        RandomGoalsGenerator random = new RandomGoalsGenerator();
        //goal in this game
        int highC = random.goalRangeHigher();        
        int lowC = random.goalRangelower(Math.abs(difference));
        //update team's goal
        if (difference < 0 )
        {    
            int origin1 = teamFirst.getGoalScore();
            teamFirst.setGoalScore(origin1+highC);
            playerScore(teamFirst,highC);
            int origin2 = teamSecond.getGoalScore();
            teamSecond.setGoalScore(origin2+lowC);
            playerScore(teamSecond,lowC);
        }
        else
        {
            int origin1 = teamFirst.getGoalScore();
            teamFirst.setGoalScore(origin1+lowC);
            playerScore(teamFirst,lowC);
            int origin2 = teamSecond.getGoalScore();
            teamSecond.setGoalScore(origin2+highC);
            playerScore(teamSecond,highC);
        }        
        //update card
        card(teamFirst);
        card(teamSecond);
        //update card mark
        cardMark(teamFirst);
        cardMark(teamSecond);
        //set overall socre
        if(teamFirst.getGoalScore() - goal1 > teamSecond.getGoalScore() - goal2)
        {
            int point1 = teamFirst.getOverallPoint();
            teamFirst.setOverallPoint(point1 + 3);
            int won1 = teamFirst.getWon();
            teamFirst.setWon(won1 + 1);
            int lost2 = teamSecond.getLost();
            teamSecond.setLost(lost2 + 1);
        }
        else if(teamFirst.getGoalScore() - goal1 < teamSecond.getGoalScore() - goal2)
        {
            int point2 = teamSecond.getOverallPoint();
            teamSecond.setOverallPoint(point2 + 3);
            int won2 = teamSecond.getWon();
            teamSecond.setWon(won2 + 1);
            int lost1 = teamFirst.getLost();
            teamFirst.setLost(lost1 + 1);
        }
        else
        {
            int point1 = teamFirst.getOverallPoint();
            teamFirst.setOverallPoint(point1 + 1);
            int point2 = teamSecond.getOverallPoint();
            teamSecond.setOverallPoint(point2 + 1);
            int drawn1 = teamFirst.getDrawn();
            teamFirst.setDrawn(drawn1 + 1);
            int drawn2 = teamSecond.getDrawn();
            teamSecond.setDrawn(drawn2 + 1);
        }
        //update games been played in one team
        int played1 = teamFirst.getPlayed();
        teamFirst.setPlayed(played1 + 1);
        int played2 = teamSecond.getPlayed();
        teamSecond.setPlayed(played2 + 1);
        //record card now to record card getted in this game
        int redNow1 = teamFirst.getRedCard();
        int redNow2 = teamSecond.getRedCard();
        int yellowNow1 = teamFirst.getYellowCard();
        int yellowNow2 = teamSecond.getYellowCard();
        //decide which team get high goal or low goal
        if(difference < 0)
        {   
            displayGameResult(teamFirst,teamSecond,highC,lowC,redNow1-red1,redNow2-red2,yellowNow1-yellow1,yellowNow2-yellow2);
        } 
        else
        {
            displayGameResult(teamFirst,teamSecond,lowC,highC,redNow1-red1,redNow2-red2,yellowNow1-yellow1,yellowNow2-yellow2);
        }
    }
    
    //allocate goals to player1 and player2
    public void playerScore(Team team,int totalGoal)
    {
         RandomGoalsGenerator random = new RandomGoalsGenerator();       
         int number = random.random0max(totalGoal);
         
         int goal1 = team.getPlayer1().getGoal();         
         Player newPlayer1 = team.getPlayer1();
         newPlayer1.setGoal(goal1+number);
         team.setPlayer1(newPlayer1);            
      
         int goal2 = team.getPlayer2().getGoal();
         Player newPlayer2 = team.getPlayer2();
         newPlayer2.setGoal(goal2+(totalGoal-number));
         team.setPlayer2(newPlayer2);

    }
    
    //penalty shoot out game
    public void playPenaltyShootOut(Team teamFirst, Team teamSecond)
    {
        RandomGoalsGenerator random = new RandomGoalsGenerator();
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 1; i <= 5 ; i++)
        {
            sum1 = sum1 + random.random0max(1);
            sum2 = sum2 + random.random0max(1);
        }
        
        while (sum1 == sum2)
        {
            sum1 = sum1 + random.random0max(1);
            sum2 = sum2 + random.random0max(1);
        }
        int origin1 = teamFirst.getGoalScore();
        teamFirst.setGoalScore(origin1+sum1);
        int origin2 = teamSecond.getGoalScore();
        teamSecond.setGoalScore(origin2+sum2);
    }
    
    public void displayGameResult(Team team1,Team team2,int goal1,int goal2,int red1,int red2,int yellow1, int yellow2)
    {   
        String name1 = team1.getName();
        String name2 = team2.getName();
        System.out.println("   ");
        System.out.println("Game result:   "+name1+" " +goal1+" vs. "+name2+" "+goal2               );
        System.out.println("Cards awarded: "+name1+" - "+red1+" red card               , - "+yellow1+" yellow card");
        System.out.println("               "+name2+" - "+red2+" red card               , - "+yellow2+" yellow card");
    }
    
    public void readRanking()
    {
        String filename = ("teams.txt");
        try
        {
            FileReader file = new FileReader(filename);
            try
            {
                Scanner input = new Scanner(file);                                
                while(input.hasNextLine())
                {   
                    String read = input.nextLine();
                    String[] whole = read.split(",");   
                    Team team = new Team(whole[0],Integer.parseInt(whole[1]));
                    teams.add(team); 
                }               
            }
            finally
            {   
                file.close();
            }
        }
        catch(FileNotFoundException exception)
        {
            System.out.println(filename + "not found");
        }
        catch(IOException exception)
        {
            System.out.println("Unexpected I/O exception occurs");
        }
    }
    
    public void preliminary()
    {   
        
    for(int k = 1; k<=1; k++)
    {
        for(int i = 0; i <= 3 ; i++)
        {
            for (int j = i + 1; j <= 3; j++)
            {   
                
                    playGame(teams.get(i),teams.get(j));
                
            }
        } 
    }
        //for(int i = 0 ; i <= 3 ; i++)
        //{
          //  for (int j = 0; j <= 3; j++)
          //  {   
          //      if( i != j)
          //      {
          //          playGame(teams.get(i),teams.get(j));
          //      }
          //  }
        //} 
        //reset the each team's ranking to 1
        for(int i = 0 ; i <= 3 ; i++)
        {
            teams.get(i).setRanking(1);
        } 
        //update ranking: compare two teams
        for(int i = 0 ; i <= 3 ; i++)
        {
            for (int j = i + 1; j <= 3; j++)
            {   
                //p:overall point g:total goal
                int p1 = teams.get(i).getOverallPoint();
                int p2 = teams.get(j).getOverallPoint();
                int g1 = teams.get(i).getGoalScore();
                int g2 = teams.get(j).getGoalScore();
                compare(i,j,p1, p2, g1, g2);
            }
        }
        displayRank();
    }
    
    //if a team has low total points, the rank of this team will be add 1
    public void compare(int i, int j, int p1, int p2, int g1, int g2)
    {
        if (p1 < p2)
        {
            int rank = teams.get(i).getRanking();
            teams.get(i).setRanking(rank + 1);
        }
        else if (p1 > p2)
        {
            int rank = teams.get(j).getRanking();
            teams.get(j).setRanking(rank + 1);
        }
        else
        {
            if(g1 < g2)
            {
                int rank = teams.get(i).getRanking();
                teams.get(i).setRanking(rank + 1);
            }
            else if(g1 > g2)
            {
                int rank = teams.get(j).getRanking();
                teams.get(j).setRanking(rank + 1);
            }
            else 
            {   
                RandomGoalsGenerator random = new RandomGoalsGenerator();
                int indicator = random.generateRandomNumber(2);
                if (indicator == 1)
                {
                    int rank = teams.get(i).getRanking();
                    teams.get(i).setRanking(rank + 1);
                }
                else
                {
                    int rank = teams.get(j).getRanking();
                    teams.get(j).setRanking(rank + 1);
                }
            }
        }
    }
    
    public void displayRank()
    {   
        for (int i = 1; i <=4 ; i++)
        {   
            for (int j = 0; j <=3; j++)
            {
                if(teams.get(j).getRanking() == i)
                {
                    System.out.println(teams.get(j).getName() + " " + teams.get(j).getRanking());
                }
            }
        }   
    }  
    
    public String finalGame()
    {   
        //find top 2 teams and store them in arraylist "teamfinal"
        ArrayList<Team> teamFinal = new ArrayList<Team>();
        for(int i = 1; i <= 2; i++)
        {
            for (int j = 0; j <=3; j++)
            {
                if(teams.get(j).getRanking() == i)
                {
                    teamFinal.add(teams.get(j));
                }
            }
        }
        //record total score before play the final
        int goal1 = teamFinal.get(0).getGoalScore();
        int goal2 = teamFinal.get(1).getGoalScore();
        
        playGame(teamFinal.get(0),teamFinal.get(1));
        //record total score now
        int goalNow1 = teamFinal.get(0).getGoalScore();
        int goalNow2 = teamFinal.get(1).getGoalScore();
        //deliver "winner" to method displayCupResult()
        String winner = "";
        if (goalNow1 - goal1 > goalNow2 - goal2)
        {   
            winner = teamFinal.get(0).getName();
        }
        else if (goalNow1 - goal1 < goalNow2 - goal2)
        {   
            winner = teamFinal.get(1).getName();
        }
        else //if(goalNow1 - goal1 == goalNow2 - goal2)
        {   
            System.out.println("Drawn! Play Penalty Shoot Out ");
            // drawm .each of these two teams have already been add 1, so minis1
            int origin1 = teamFinal.get(0).getOverallPoint();
            teamFinal.get(0).setOverallPoint(origin1-1);
            int origin2 = teamFinal.get(1).getOverallPoint();
            teamFinal.get(1).setOverallPoint(origin2-1);
            //goal has been set in method playPenaltyShootOut()
            playPenaltyShootOut(teamFinal.get(0),teamFinal.get(1));
            //record goal after finishing Penalty Shoot Out  
            int goalNew1 = teamFinal.get(0).getGoalScore();
            int goalNew2 = teamFinal.get(1).getGoalScore();
            //decide which is winner
            if( goalNew1 - goalNow1 > goalNew2 - goalNow2)
            {
                int point1 = teamFinal.get(0).getOverallPoint();
                teamFinal.get(0).setOverallPoint(point1+3);
                winner = teamFinal.get(0).getName();
            }
            else
            {   
                int point2 = teamFinal.get(1).getOverallPoint();
                teamFinal.get(1).setOverallPoint(point2+3);
                winner = teamFinal.get(1).getName();
            }

            System.out.println(winner+" is winner");
        }
        return winner;
    }
    
    
    public void displayTeams               ()
    {   
        //format
        System.out.printf("%10s %10s %10s %10s %10s %10s %10s %15s","  ", "Played",
        "Won", "Lost", "Drwan", "Goals", "Points", "FairPlayScore");
        int[] numbers = new int[7];
        System.out.println();
        for(int i = 0; i <= 3 ; i++)
        {   
            String team = teams.get(i).getName();
            numbers[0] = teams.get(i).getPlayed();
            numbers[1] = teams.get(i).getWon();
            numbers[2] = teams.get(i).getLost();
            numbers[3] = teams.get(i).getDrawn();
            numbers[4] = teams.get(i).getGoalScore();
            numbers[5] = teams.get(i).getOverallPoint();
            numbers[6] = teams.get(i).getCardMark();
            System.out.format("%10s %10d %10d %10d %10d %10d %10d %15d",
            team,numbers[0],numbers[1],numbers[2],numbers[3],numbers[4],numbers[5],numbers[6]);
            System.out.println();
        }
    }
    
    public void displayPlayers               ()
    {   
        for(int i = 0; i <= 3; i++)
        {
            String country = teams.get(i).getName();
            String name1 = teams.get(i).getPlayer1().getName();
            String name2 = teams.get(i).getPlayer2().getName();
            int goal1 =  teams.get(i).getPlayer1().getGoal();
            int goal2 =  teams.get(i).getPlayer2().getGoal();
            System.out.println(name1+" ("+country+") - "+goal1);
            System.out.println(name2+" ("+country+") - "+goal2);
        }
    }
    
    public String playerNameInput()
    {
        Scanner input = new Scanner(System.in);
        String name= input.nextLine();
        return name;
    }
    
    //validation for player's name
    public int nameCheck(String name)
    {
        int character = 1;
        int hyphen = 0;
        int space = 0;
        
        for (int i = 0; i < name.length(); i++)
        {   
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) == '-')
            {   
                //record the number of hyphen
                hyphen++;
            }
        } 
        
        for (int i = 0; i < name.length(); i++)
        {   
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != '-')
            {   
                //if one of them is not character or hyphen
                character = 0;
            }
        } 
        
        if (character == 0 || hyphen > 1 || name.length() <2)
        {   
            System.out.println("Name is invalid.");
            System.out.println("Please enter a name between 1 and 15 characters including at most 1 hyphen.");
            //invalid
            return 1;
        }
        else
        {   
            //valid
            return 0;
        }
    }
    
    //set 4 teams player's name
    public void teamPlayerSet()
    {
        for(int i = 0; i <=3; i++)
        {
            playerNameSet(teams.get(i));
        }
    }
            
    public void playerNameSet(Team team)
    {   
        String country = team.getName();
        System.out.println("Please enter "+country+"'s first player name");
        //player1 first try,the first "1" is related to player1 or player2,the second "1" is related to the times of try
        String name11 = playerNameInput();
        int indicator11 = nameCheck(name11);
        //invalid player1 first try 
        if(indicator11 == 1)
        {   
            //second try
            String name12 = playerNameInput();
            int indicator12 = nameCheck(name12);  
            //invalid second try
            if(indicator12 == 1)
            {
                Player temp13 = team.getPlayer1();
                temp13.setName("play-1-"+country);
                team.setPlayer1(temp13);
                System.out.println("player1's name is play-1-"+country);
            }
            //valid second try
            else
            {
                Player temp12 = team.getPlayer1();
                temp12.setName(name12);
                team.setPlayer1(temp12);
            }
        }
        //valid player1 first try 
        else
        {
            
            team.getPlayer1().setName(name11);
            //Player temp11 = team.getPlayer1();
            //temp11.setName(name11);
            //team.setPlayer1(temp11);
        }
        
        String sameName = team.getPlayer1().getName();        
        System.out.println("Please enter "+country+"'s second player name");
        //player2 first try
        String name21 = playerNameInput();
        int indicator21 = nameCheck(name21);
        //if first try is invalid 
        if(indicator21 == 1)
        {
            String name22 = playerNameInput();
            int indicator22 = nameCheck(name22);
            //if the second try is invalid or the same as player1
            if(name22.equals(sameName) || indicator22 == 1)
            {
                Player temp23 = team.getPlayer2();
                temp23.setName("play-2-"+country);
                team.setPlayer2(temp23);
                System.out.println("player2's name is play-2-"+country);
            }
            else
            {
                Player temp22 = team.getPlayer2();
                temp22.setName(name22);
                team.setPlayer2(temp22);
            }
        }
        //if first try is valid 
        else
        {   
            //check whether it is the same with player1
            if(!name21.equals(sameName))
            {  
                Player temp21 = team.getPlayer2();
                temp21.setName(name21);
                team.setPlayer2(temp21);
            }
            //if is the sam with player1
            else
            {
                System.out.println("player2 has the same name with player1,please enter again");
                //second try
                String name24 = playerNameInput();
                int indicator24 = nameCheck(name24);
                //if the second try is invalid or the same as player1
                if(name24.equals(sameName) || indicator24 == 1)
                {
                    Player temp24 = team.getPlayer2();
                    temp24.setName("play-2-"+country);
                    team.setPlayer2(temp24);
                    System.out.println("player2's name is play-2-"+country);
                    
                }
                else
                {
                    Player temp24 = team.getPlayer2();
                    temp24.setName(name24);
                    team.setPlayer2(temp24);
                }
            }
        }
    }
    
    public void displayCupResult(String teamName)
    {   
        System.out.println("Football World Cup Winner:                "+teamName);        
        String boot = goldenBoot();
        String fair = fairPlay               ();
        System.out.println("Golden Boot Award:                               "+ boot);     
        System.out.println("Fair Play Award:                               "+ fair);     
    }
    
    public String goldenBoot()
    {
        int max = 0;
        String boot = "";
        //find the max goal
        for(int i = 0 ; i <= 3; i++)
        {   
            int teamMaxGoal = selectMaxGoal(teams.get(i));
            if (teamMaxGoal > max)
            {
                max = teamMaxGoal;
            }
        }
        //find who has max goal
        for(int i = 0; i <= 3; i++)
        {
            int goal1 = teams.get(i).getPlayer1().getGoal();
            int goal2 = teams.get(i).getPlayer2().getGoal();
            if(goal1 == max)
            {
                boot = boot + " " +teams.get(i).getPlayer1().getName()+" from "+teams.get(i).getName();
            }
            if(goal2 == max)
            {
                boot = boot + " " +teams.get(i).getPlayer2().getName()+" from "+teams.get(i).getName();
            }
        }
        return boot;
    }
    
    //find the max player's goal in one team
    public int selectMaxGoal(Team team)
    {
        int max = 0;
        int goal1 = team.getPlayer1().getGoal();
        int goal2 = team.getPlayer2().getGoal();
        if(goal1 > max)
        {
            max = goal1;
        }
        if(goal2 > max)
        {
            max = goal2;
        }
        return max;
    }
    
    public String fairPlay()
    {
        int fair = 999;
        String fairTeam = "";
        //find the minimum card mark
        for(int i = 0; i <= 3; i++)
        {
            int mark = teams.get(i).getCardMark();
            if (mark <= fair)
            {                  
                fair = mark;
            }
        }
        //find the team which has the minimum car mark
        for(int i = 0; i <= 3; i++)
        {
            int mark = teams.get(i).getCardMark();
            if(mark == fair)
            {        
                fairTeam = fairTeam + " " + teams.get(i).getName();
            }
        }
        return fairTeam;
    }
    
    public void start()
    {
        readRanking();
        teamPlayerSet();
        Menu option = new Menu();
        option.Menu();
        Game play = new Game();
        String winnerName = "";
        int checkA = 0;
        System.out.println("Please choose one option               ");
        while(true)
        {
            Scanner enter = new Scanner(System.in);
            String singleChar = enter.nextLine();

            if (singleChar.equals("A") || singleChar.equals("a"))
            {
                preliminary();
                option.Menu();
                checkA = 1;
            }
        
            if (singleChar.equals("B") || singleChar.equals("b"))
            {   
                if(checkA == 1)
                {
                    winnerName = finalGame();
                }
                else
                {
                    System.out.println("Please play the preliminary game firstly               ");
                }
                option.Menu();          
            }
        
            if (singleChar.equals("C") || singleChar.equals("c"))
            {
                displayTeams               ();
                option.Menu();
            }
        
            if (singleChar.equals("D") || singleChar.equals("d"))
            {
                displayPlayers();
                option.Menu();
            }
        
            if(singleChar.equals ("E") || singleChar.equals("e"))
            {
                displayCupResult(winnerName);
                option.Menu();
            } 
        
            if(singleChar.equals ("X") || singleChar.equals("x"))
            {   
                System.out.println("You quit this game               ");
                write(winnerName);
                break;
            } 
        }
    }
    
    public void write(String winner)
    {   
        String file = "statistics.txt";
        try
        {   
            PrintWriter output = new PrintWriter(file);
            try
            {
                String boot = goldenBoot();
                String fair = fairPlay               ();
                output.println("Football World Cup Winner:                "+ winner);
                output.println("Golden Boot Award:                               "+ boot); 
                output.println("Fair Play Award:                               "+ fair);
            }
            finally
            {
                output.close();
            }
        }
        catch(IOException e               )
        {
            System.out.println("Unexpected I/O exception occurs");
        }
    }
}

