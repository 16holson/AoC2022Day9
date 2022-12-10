import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Part1
{
    private static class RopeEnd
    {
        //Name of the rope end
        private String name;
        //Tail of the rope end
        private RopeEnd tail;
        //List of previous positions in x, y coordinates
        private ArrayList<ArrayList<Integer>> pastPositions;
        public RopeEnd(String name)
        {
            this.name = name;
            pastPositions = new ArrayList<>();
            tail = new RopeEnd();
        }
        public RopeEnd()
        {
            this.name = "T";
            pastPositions = new ArrayList<>();
        }
        public void setName(String name)
        {
            this.name = name;
        }
        public String getName()
        {
            return name;
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
                moveTail();
            }
        }
        private void moveTail()
        {
            //Initial position
            if(tail.pastPositions.size() == 0)
            {
                tail.pastPositions.add(new ArrayList<>(List.of(0, 0)));
            }
            ArrayList<Integer> newPosition = new ArrayList<>();
            int lastTailIndex = tail.pastPositions.size()-1;
            int lastHeadIndex = pastPositions.size()-1;
            //Move tail to heads previous position unless the head is still within a box around the tail including the tails position
            if(Math.abs(pastPositions.get(lastHeadIndex).get(0) - tail.pastPositions.get(lastTailIndex).get(0)) > 1 || Math.abs(pastPositions.get(lastHeadIndex).get(1) - tail.pastPositions.get(lastTailIndex).get(1)) > 1)
            {
                newPosition.add(pastPositions.get(lastHeadIndex-1).get(0));
                newPosition.add(pastPositions.get(lastHeadIndex-1).get(1));
                tail.pastPositions.add(newPosition);
            }
        }
        public Integer getTotalTailPositions()
        {
            HashSet<ArrayList<Integer>> uniquePositions = new HashSet<>(tail.pastPositions);
            return uniquePositions.size();
        }
    }
    public static void main(String[] args) throws IOException
    {
        String line;
        String file = new File("").getAbsolutePath();
        file = file.concat("\\src\\InputData.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        RopeEnd head = new RopeEnd("H");
        while((line = bufferedReader.readLine()) != null)
        {
            head.move(line);
        }
        System.out.println("Total past positions: " + head.getTotalTailPositions());
    }
}
