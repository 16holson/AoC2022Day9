import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Part2
{
    private static class RopeEnd
    {
        //Knots in the rope
        private ArrayList<RopeEnd> tails;
        //List of previous positions in x, y coordinates
        private ArrayList<ArrayList<Integer>> pastPositions;
        public RopeEnd(int numTails)
        {
            pastPositions = new ArrayList<>();
            tails = new ArrayList<>();
            for (int i = 0; i < numTails; i++)
            {
                tails.add(new RopeEnd());
            }
        }
        public RopeEnd()
        {
            pastPositions = new ArrayList<>();
        }
        public ArrayList<ArrayList<Integer>> getPastPositions()
        {
            return pastPositions;
        }
        public void move(String input)
        {
            String[] commands = input.split(" ");
            //Initial position
            if(pastPositions.size() == 0)
            {
                pastPositions.add(new ArrayList<>(List.of(0, 0)));
            }
            for(int i = 0; i < Integer.parseInt(commands[1]); i++)
            {
                ArrayList<Integer> newPosition = new ArrayList<>();
                int lastIndex = pastPositions.size()-1;
                //Move head
                if(commands[0].equals("R"))
                {
                    newPosition.add(pastPositions.get(lastIndex).get(0)+1);
                    newPosition.add(pastPositions.get(lastIndex).get(1));
                    pastPositions.add(newPosition);
                }
                else if(commands[0].equals("L"))
                {
                    newPosition.add(pastPositions.get(lastIndex).get(0)-1);
                    newPosition.add(pastPositions.get(lastIndex).get(1));
                    pastPositions.add(newPosition);
                }
                else if (commands[0].equals("U"))
                {
                    newPosition.add(pastPositions.get(lastIndex).get(0));
                    newPosition.add(pastPositions.get(lastIndex).get(1)+1);
                    pastPositions.add(newPosition);
                }
                else
                {
                    newPosition.add(pastPositions.get(lastIndex).get(0));
                    newPosition.add(pastPositions.get(lastIndex).get(1)-1);
                    pastPositions.add(newPosition);
                }
                //Move tail
                for (int j = 0; j < tails.size(); j++)
                {
                    moveTail(j);
                }
            }
        }
        private void moveTail(int tailNum)
        {
            //Initial position
            if(tails.get(tailNum).pastPositions.size() == 0)
            {
                tails.get(tailNum).pastPositions.add(new ArrayList<>(List.of(0, 0)));
            }
            ArrayList<Integer> newPosition = new ArrayList<>();
            int lastTailIndex = tails.get(tailNum).pastPositions.size()-1;
            int lastAheadIndex;

            //Move tail to heads previous position unless the head is still within a box around the tail including the tails position
            if(tailNum == 0)
            {
                lastAheadIndex = pastPositions.size()-1;
                if(Math.abs(pastPositions.get(lastAheadIndex).get(0) - tails.get(tailNum).pastPositions.get(lastTailIndex).get(0)) > 1 || Math.abs(pastPositions.get(lastAheadIndex).get(1) - tails.get(tailNum).pastPositions.get(lastTailIndex).get(1)) > 1)
                {
                    newPosition.add(pastPositions.get(lastAheadIndex-1).get(0));
                    newPosition.add(pastPositions.get(lastAheadIndex-1).get(1));
                }
            }
            else
            {
                lastAheadIndex = tails.get(tailNum-1).pastPositions.size()-1;
                //check diagonal

                int x = tails.get(tailNum-1).pastPositions.get(lastAheadIndex).get(0) - tails.get(tailNum).pastPositions.get(lastTailIndex).get(0);
                int y = tails.get(tailNum-1).pastPositions.get(lastAheadIndex).get(1) - tails.get(tailNum).pastPositions.get(lastTailIndex).get(1);
                if( Math.abs(x) > 1 || Math.abs(y) > 1)
                {
                    if((x == 2 && y == 1) || (x == 1 && y == 2))
                    {
                        newPosition.add(tails.get(tailNum).pastPositions.get(lastTailIndex).get(0)+1);
                        newPosition.add(tails.get(tailNum).pastPositions.get(lastTailIndex).get(1)+1);
                    }
                    else if((x == -2 && y == -1) || (x == -1 && y == -2))
                    {
                        newPosition.add(tails.get(tailNum).pastPositions.get(lastTailIndex).get(0)-1);
                        newPosition.add(tails.get(tailNum).pastPositions.get(lastTailIndex).get(1)-1);
                    }
                    else if((x == -1 && y == 2) || (x == 2 && y == -1))
                    {
                        newPosition.add(tails.get(tailNum).pastPositions.get(lastTailIndex).get(0)+1);
                        newPosition.add(tails.get(tailNum).pastPositions.get(lastTailIndex).get(1)-1);
                    }
                    else if((x == 1 && y == -2) || (x == -2 && y == 1))
                    {
                        newPosition.add(tails.get(tailNum).pastPositions.get(lastTailIndex).get(0)-1);
                        newPosition.add(tails.get(tailNum).pastPositions.get(lastTailIndex).get(1)+1);
                    }
                    else
                    {
                        newPosition.add(tails.get(tailNum-1).pastPositions.get(lastAheadIndex-1).get(0));
                        newPosition.add(tails.get(tailNum-1).pastPositions.get(lastAheadIndex-1).get(1));
                    }
                }
            }
            if(newPosition.size() > 0)
            {
                tails.get(tailNum).pastPositions.add(newPosition);
            }

        }
        public Integer getLastTotalTailPositions()
        {
            HashSet<ArrayList<Integer>> uniquePositions = new HashSet<>(tails.get(tails.size()-1).pastPositions);
            return uniquePositions.size();
        }
    }
    public static void main(String[] args) throws IOException
    {
        String line;
        String file = new File("").getAbsolutePath();
        file = file.concat("\\src\\InputData.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        RopeEnd head = new RopeEnd(9);
        while((line = bufferedReader.readLine()) != null)
        {
            head.move(line);
        }
        System.out.println("Total past positions of last tail: " + head.getLastTotalTailPositions());
    }
}

