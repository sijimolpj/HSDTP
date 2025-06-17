package textMining;
/**
 * 
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.HashMap;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;
import net.didion.jwnl.dictionary.MorphologicalProcessor;

/**
 * @author SIJI
 *
 */
public class Stemmer {

	private Dictionary dic;  //object for class net.didion.jwnl.dictionary.Dictionary
	private MorphologicalProcessor morph;  //declares the MorphologicalProcessor interface
	private boolean IsInitialized = false;  
	public HashMap<String, String> AllWords=null;  //to store stemmed words
	public Stemmer()
	{
		AllWords = new HashMap<String, String>();
		
			try
			{
				JWNL.initialize(new FileInputStream("file_properties.xml"));
				dic = Dictionary.getInstance();//creates a dictionary
				morph = dic.getMorphologicalProcessor();//creates morphological processor
				IsInitialized = true;  //JWNL is initialized
				 File f=new File("file_properties.xml");
	              System.out.println("file is at "+f.getAbsolutePath());
			}     
			catch(FileNotFoundException e)
			{
				System.out.println("Error initializing Stemmer:fileproperties.xml not found");
			}
			catch (JWNLException e)
			{
				System.out.println("Error initializing Stemmer: "+ e.toString());
			}
		
	}
	public void Unload ()
	{ 
		dic.close();
		Dictionary.uninstall();
		JWNL.shutdown();
	}
	public String StemWordWithWordNet(String word)
	{
		if(!IsInitialized)
			return word;
		if(word == null ) return null;
		if(morph == null ) morph = dic.getMorphologicalProcessor();
		
		IndexWord w;
		try
		{
			w = morph.lookupBaseForm( POS.VERB, word );
			if ( w != null )
				return w.getLemma().toString ();
			w = morph.lookupBaseForm( POS.NOUN, word );
			if ( w != null )
				return w.getLemma().toString();
			w = morph.lookupBaseForm( POS.ADJECTIVE, word );
			if ( w != null )
				return w.getLemma().toString();
			w = morph.lookupBaseForm( POS.ADVERB, word );
			if ( w != null )
				return w.getLemma().toString();
		} 
		catch ( JWNLException e )
		{
		}
		return null;
	}
	public String Stem(String word)
	{
		// check if we already know the word
		String stemmedword= AllWords.get(word);
		if ( stemmedword != null )
			return stemmedword; // return it if we already know it
		else	// unknown word: try to stem it
			stemmedword = StemWordWithWordNet (word);
		
		if ( stemmedword != null )
		{
			// word was recognized and stemmed with wordnet:
			// add it to hashmap and return the stemmed word
			AllWords.put(word,stemmedword);
			return stemmedword;
		}
		// word could not be stemmed by wordnet, 
		// thus it is no correct english word
		// just add it to the list of known words so 
		// we won't have to look it up again
		AllWords.put(word,word);
		return word;
	}
	
	public void sentenceStemmer(String w)
	{
		w.toLowerCase();
		
		String[] word=w.split("[ ,:.?]");
		for(int k=0;k<word.length;k++)
		{
			word[k]=Stem(word[k]);
			System.out.print(" "+word[k]);
		}	
		Unload();
	}
	
	
	public ArrayList<String> sentenceStemmerArrayList(ArrayList<String> w)
	{
		ArrayList<String> stemmed = new ArrayList<String>();
		stemmed.clear();
		String str;
		for (int i=0;i<w.size();i++) {
			str=Stem(w.get(i));
			stemmed.add(str);
		}
		
		return stemmed;
		}
}
