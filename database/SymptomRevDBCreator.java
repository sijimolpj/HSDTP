package database;
import textMining.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SymptomRevDBCreator {

	static Connection con;
	static Stemmer st= new Stemmer();
	static StopwordRemov sr=new StopwordRemov();
	static ArrayList<String> sw=new ArrayList<String>();
	static ArrayList<String> Words=new ArrayList<String>();
	static Set<String> s=new HashSet<String>();
	/*
	 * ------------------------
	 */
	public static void main(String[] args) throws SQLException {
		
		DatabaseConnector d=new DatabaseConnector("symptomdb");
		con=d.getConnectionToDB();
		ArrayList<Long> Did;
		String symptom;
		
		Did=d.returnDidfromSymptomDB();
		for (Long id : Did) {
			symptom=d.returnSymptomSentence(id);
			
						
			Words=sr.stopping(symptom);
			System.out.println("After stopping"+Words);
			
			System.out.println("After stemming");
			sw=st.sentenceStemmerArrayList(Words);
			System.out.println(sw);
			System.out.println("Removes duplicates");
			
			s.addAll(sw);//removes duplicates
			sw=new ArrayList<>(s);
				
			System.out.println("-------------------------------------------- ");
			
			System.out.println(sw);
			for(String w : sw){
				d.insert(id,w);
			}
			sw.clear();
			s.clear();
			Words.clear();
		}
		
	}
}
