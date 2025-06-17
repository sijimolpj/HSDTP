package textMining;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;




public class TextMining {
	static String str=new String();
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		/*
		 * 1.write code to read file 
		 * 2. Code to cluster based on heading
		 * 
		 */
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("enter String");
	    str=br.readLine(); 
		Set<String> s=new HashSet<String>();
		
		ArrayList<String> sw=new ArrayList<String>();
		ArrayList<String> Words=new ArrayList<String>();
		Stemmer st= new Stemmer();
		StopwordRemov sr=new StopwordRemov();
		Words=sr.stopping(str);
		System.out.println("After stopping"+Words);
		
		System.out.println("After stemming");
		sw=st.sentenceStemmerArrayList(Words);
		System.out.println(sw);
		System.out.println("Removes duplicates");
		
		s.addAll(sw);//removes duplicates
		sw=new ArrayList<>(s);
		
		
		System.out.println("-------------------------------------------- ");
		
		System.out.println(sw);

	}

}
