package taggers;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * @author SIJI
 *
 */
public class POSTagger {
	MaxentTagger tagger=new MaxentTagger("taggers/english-left3words-distsim.tagger"); 

	public void posTagWord(String Sample)

	{
	String tagged=tagger.tagString(Sample);
	System.out.println("Input:"+Sample);
	System.out.println("output:"+tagged);
	}

	public void posTagFromFile(String file)
	{
	String sentence,tagged;
	FileInputStream fstream;
	try {
		fstream = new FileInputStream(file);
		DataInputStream in=new DataInputStream(fstream);
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		while((sentence=br.readLine())!=null)
		{
			tagged=tagger.tagString(sentence);
			FileWriter fw=new FileWriter("output.txt",true);
			BufferedWriter out=new BufferedWriter(fw);
			out.write(tagged);
			out.newLine();
			out.close();
		}
		br.close();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
	
	POSTagger pos=new POSTagger();
	pos.posTagWord("An individual infected with dengue fever will have a high fever along with at least two of the following symptoms:a severe headache, pain behind the eyes,joint and muscle pain,rash, and a nose or gum bleed.Typically, younger children and those experiencing their first dengue infection will have a milder illness than older children and adults");
	}
	}

