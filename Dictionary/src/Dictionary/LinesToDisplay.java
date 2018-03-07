package Dictionary;

import java.util.Iterator;


/**
 * A class that will be used to display the lines of text that are corrected.
 *
 */
public class LinesToDisplay {

    public static final int LINES = 10;     // Display 10 lines
    private AList<Wordlet>[] lines;
    private int currentLine;

    /**
     * Constructor for objects of class LinesToDisplay
     */
    public LinesToDisplay() {
        //ADD CODE FOR THE CONSTRUCTOR
        lines = (AList<Wordlet>[]) new AList[LINES];
        
        for (int i = 0; i < LINES; i++)
        {
            lines[i] = new AList();
        }
        currentLine = 0;
    }

    /**
     * Add a new wordlet to the current line.
     *
     */
    public void addWordlet(Wordlet w) {
        //ADD CODE HERE TO ADD A WORDLET TO THE CURRENT LINE
        lines[currentLine].add(w);

    }

    /**
     * Go to the next line, if the number of lines has exceeded LINES, shift
     * them all up by one
     *
     */
    public void nextLine() {
        //ADD CODE TO HANDLE THE NEXT LINE
        if (currentLine == LINES - 1) 
        {
            // currentLine reaches the max line
            
            for(int i = 0; i < LINES - 1; i++) 
            {
                // clear all words in lines[l]
                lines[i].clear();
                  
                // get the iterator for lines[l+1]
                Iterator<Wordlet> iter = lines[i + 1].getIterator();
                
                while (iter.hasNext()) {
                    // add the word from lines[l+1] to lines[l]
                    lines[i].add(iter.next());
                }                                    
            }
            
            // clear all words from lines[currentLine]
            lines[currentLine].clear();
        }
        else 
        {
            currentLine++;
        }
    }

      
    public int getCurrentLine(){
        return currentLine;
    }
    
    public AList<Wordlet>[] getLines(){
        return lines;
    }
}
