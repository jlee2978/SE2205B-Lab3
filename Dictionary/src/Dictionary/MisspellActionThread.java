package Dictionary;

import java.io.*;
import java.util.*;
import javafx.application.Platform;

/**
 * A Thread that contains the application we are going to animate
 *
 */
public class MisspellActionThread implements Runnable {

    DictionaryController controller;
    private final String textFileName;
    private final String dictionaryFileName;

    private LinesToDisplay myLines;
    private DictionaryInterface<String, String> myDictionary;
    private boolean dictionaryLoaded;

    /**
     * Constructor for objects of class MisspellActionThread
     *
     * @param controller
     */
    public MisspellActionThread(DictionaryController controller) {
        super();

        this.controller = controller;
        textFileName = "check.txt";
        dictionaryFileName = "sampleDictionary.txt";

        myDictionary = new HashedMapAdaptor<String, String>();
        myLines = new LinesToDisplay();
        dictionaryLoaded = false;

    }

    @Override
    public void run() {

        // ADD CODE HERE TO LOAD DICTIONARY
        /*myLines.addWordlet(new Wordlet("abc", true));
        myLines.nextLine();
        showLines(myLines);
        myLines.addWordlet(new Wordlet("def", false));
        showLines(myLines);
        myLines.nextLine();*/
        
        
        loadDictionary(dictionaryFileName, myDictionary);
        Platform.runLater(() -> {
            if (dictionaryLoaded) {
               controller.SetMsg("The Dictionary has been loaded"); 
            } else {
               controller.SetMsg("No Dictionary is loaded"); 
            }
        });
        
        // ADD CODE HERE TO CALL checkWords
        // call checkWords to the words in textFileName
        checkWords(textFileName, myDictionary);


    }

    /**
     * Load the words into the dictionary.
     *
     * @param theFileName The name of the file holding the words to put in the
     * dictionary.
     * @param theDictionary The dictionary to load.
     */
    public void loadDictionary(String theFileName, DictionaryInterface<String, String> theDictionary) {
        Scanner input;
        try {
            String inString;
            String correctWord;

            input = new Scanner(new File(theFileName));

            // ADD CODE HERE TO READ WORDS INTO THE DICTIONARY     
            while(input.hasNext())
            {
                inString = input.next();
                
                //the key and value are the same
                correctWord = inString;
                
                //add the key and value to the dictionary
                theDictionary.add(inString, correctWord);
            }
            dictionaryLoaded = true;
            
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }

    }

    /**
     * Get the words to check, check them, then put Wordlets into myLines. When
     * a single line has been read do an animation step to wait for the user.
     *
     */
    public void checkWords(String theFileName, DictionaryInterface<String, String> theDictionary) {
        Scanner input;
        try {
            String inString;
            String aWord;

            input = new Scanner(new File(theFileName));
            // ADD CODE HERE    
            int p;
            char c;
            boolean punctuationOnly;
            Wordlet wordlet;       
 
            // while there is a line input
            while (input.hasNextLine()) {
                // read a line from the file
                inString = input.nextLine();

                // set the tokenizer for inString
                StringTokenizer tokenizer = new StringTokenizer(inString, "(),.!? \"", true);
                              
                // while there is a token
                while (tokenizer.hasMoreTokens()) {
                   
                    // get a word which may have punctuation marks before or after a word
                    aWord = tokenizer.nextToken();
                    
                    // assume aWord contains punctuation only
                    punctuationOnly = true; 
                    for(int i=0; i < aWord.length(); i++) {
                        c = aWord.charAt(0);
                        if (Character.isLetter(c)) {
                            // letter is found, aWord doesn't contain punctuation only.
                            // can break the loop
                            punctuationOnly = false;
                            break;
                        }
                    }
                    
                    if (punctuationOnly) {
                        // punctuation only. so assume spelt correctly
                        wordlet = new Wordlet(aWord, true);
                    }
                    else {
                        wordlet = new Wordlet(aWord, checkWord(aWord, theDictionary));                        
                    }
                    
                    // add punctuation to mylines
                    myLines.addWordlet(wordlet);
                    
                 }
                
                // show the lines
                showLines(myLines);
                
                // process nextline
                myLines.nextLine();
            }
            
            // show the last line
            showLines(myLines);

            // this is an indication of stop to showLines 
            myLines = null;
            
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }

    }

    /**
     * Check the spelling of a single word.
     *
     */
    public boolean checkWord(String word, DictionaryInterface<String, String> theDictionary) {
        boolean result = false;

        // ADD CODE HERE    

        Iterator<String> values = theDictionary.getValueIterator();
        
        // while there is a word in the dictionary and the result is false
        while (values.hasNext() && result == false) 
        {
            result = (values.next().equals(word));
        }

        

        return result;

    }

    private void showLines(LinesToDisplay lines) {
        try {
            Thread.sleep(500);
            Platform.runLater(() -> {
                if (myLines != null) {
                    controller.UpdateView(lines);
                }
            });
        } catch (InterruptedException ex) {
        }
    }
} // end class MisspellActionThread

