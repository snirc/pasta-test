package infi.test.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import infi.test.model.Pasta;
import infi.test.model.Sauce;

public class DataMock {

	private static String[] pastaTypes = {"spaghetti","farfalle","lasagna","fettuccine"};
	
	private static String[] sauceTypes = {"tomato","cream cheese","pesto","alfredo", "aolognese"};
	
	private static HashMap<String,Pasta> pastaList = new HashMap<>();
	private static HashMap<String,Sauce> sauceList = new HashMap<>();
	
	public static void init() {
		for(int i = 0; i < pastaTypes.length; i++ ) {
			Pasta pastaModel = new Pasta();
			pastaModel.setId(i);
			pastaModel.setType(pastaTypes[i]);
			pastaModel.setHowToCookIt("Boil "+new Random().nextInt(2)+4 + " Minutes");
			pastaModel.setPrice((new Random().nextInt(15)+7)/2.5);
			pastaList.put(pastaTypes[i], pastaModel);
		}
		
		for(int i = 0; i < sauceTypes.length; i++ ) {
			Sauce sauceModel = new Sauce();
			sauceModel.setId(i);
			sauceModel.setType(sauceTypes[i]);
			sauceModel.setPrice((new Random().nextInt(15)+7)/2.5);
			sauceList.put(sauceTypes[i], sauceModel);
		}
			
	}
	
	
	public static HashMap<String,Pasta> getPastaList(){
		return pastaList;
	}
	
	public static HashMap<String,Sauce> getSauceList(){
		return sauceList;
	}
	
}
