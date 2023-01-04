package superhirn;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class main {
	public static boolean naiv = false; 			// true ist Naiv 				false ist advanced
	public static boolean einzelmodus = false;	// true ist Einzelmodus 		false ist Analyse
	public static boolean kommentare = false; 	// true mit kommentare 			false ohne kommentare	
	public static long start = System.currentTimeMillis(); 
	public static int color = 6;
	public static int len = 4;
	public static int reihen = 10;
	public static boolean komb [] = new boolean [(int)Math.pow(color, len)];
	public enum colors {
		Rot,Gelb,Grün,Hellblau,Dunkelblau,Orange;
	}
	public enum stecker {
		non,weiß,schwarz;
	}
	public static int reihe = 0;
	public static stecker [][] bewertung = new stecker [reihen][len];
	public static int [][] gesteckt = new int [reihen][len];
	public static int steckerkom = len + 1;
	public static int [] hochs = new int[len];
	public static int [] min = new int [steckerkom];
	
	
	public static void main(String[] args) {
		for (int i = 0; i < len; i++) {
			hochs[i] = (int)Math.pow(color, i);
		}
		if(einzelmodus) {
			for (int j = 0; j <= len; j++) {
				steckerkom += j;
			}
			min = new int [steckerkom];
			Random random = ThreadLocalRandom.current();
			int zuer = random.nextInt(komb.length);
			main2(69);
		}else {
			for (int j = 0; j <= len; j++) {
				steckerkom += j;
			}
			naiv = true;
			int [] sort = new int [reihen];
			for (int i = 0; i < komb.length; i++) {
				reihe = 0;
				komb = new boolean [(int)Math.pow(color, len)];
				gesteckt = new int [reihen][len];
				bewertung = new stecker [reihen][len];
				sort[main2(i)]++;
			}
			print(sort);
			naiv = false;
			sort = new int [reihen];
			for (int i = 0; i < komb.length; i++) {
				reihe = 0;
				gesteckt = new int [reihen][len];
				bewertung = new stecker [reihen][len];
				min = new int [steckerkom];
				int temp = main2(i);
				sort[temp]++;
				if (temp == 6) {
					int[] extr = extrahieren(i);
					for (int j = 0; j < extr.length; j++) {
						System.out.print(colortransformen(extr[j]) + " ");
					}
					System.out.println();
				}
			}
			System.out.println("Braucht " + (System.currentTimeMillis() - start) + " ms.");
			print(sort);
		}
	}
	
	
	public static int main2(int zuer) {
		for (int i = 0; i < komb.length; i++) {
			komb[i] = true;
		}
		for (int i = 0; i < bewertung.length; i++) {
			for (int j = 0; j < bewertung[i].length; j++) {
				bewertung[i][j] = stecker.non;
			}
		}
		int best = naiv();
		while (reihe < reihen) {
			bewertung[reihe] = einzelnbewerten(extrahieren(zuer),extrahieren(best));
			gesteckt[reihe] = extrahieren(best);
			gesamt_bewerten();
			reihe++;
			int temp = naiv();
			if (temp == best) {
				break;
			} else {
				best = temp;
			}
		}
		if (einzelmodus) {
			for (int i = 0; i < reihe; i++) {
				for (int j = 0; j < len; j++) {
					System.out.print(colortransformen(gesteckt[i][j]) + " ");
				}
				System.out.print("     ");
				for (int j = 0; j < len; j++) {
					System.out.print(bewertung[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("Hat " + reihe + " Reihen gebraucht. Hat die Kombination " + zuer + " erraten.");
			System.out.println("Braucht "+ (System.currentTimeMillis() - start) + " ms.");
		}
		return reihe;
	}
	
	
	public static void print(int [] komb) {
		for (int i = 0; i < komb.length; i++) {
			System.out.print(komb[i]+" ");
		}
		System.out.println();
	}
	
	
	public static void gesamt_bewerten() {
		for (int i = 0; i < komb.length; i++) {
			if(komb[i]) {
				stecker temp [] = einzelnbewerten(extrahieren(i),gesteckt[reihe]);
				for (int j = 0; j < len; j++) {
					if(!temp[j].equals(bewertung[reihe][j])) {
						komb[i] = false;
						break;
					}
				}
			}
		}
	}
	
	
	public static stecker [] einzelnbewerten(int [] v1, int [] v2) {
		stecker [] ret = new stecker [len];
		boolean [] used1 = new boolean [len];
		boolean [] used2 = new boolean [len];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = stecker.non;
		}
		int count = 0;
		for (int i = 0; i < ret.length; i++) {
			if (v1[i] == v2[i]) {
				ret[count] = stecker.schwarz;
				used1[i] = true;
				used2[i] = true;
				count++;
			}
		}
		for (int i = 0; i < len; i++) {
			if(!used1[i]) {
				for (int j = 0; j < len; j++) {
					if (!used2[j] && v1[i] == v2[j]) {
						ret[count] = stecker.weiß;
						used2[j] = true;
						count++;
						break;
					}
				}
			}
		}
		return ret;
	}
	
	
	public static int [] extrahieren(int ges) {
		int [] ret = new int [len];
		for (int i = len-1; i >= 0; i--) {
			ret[i] = (int)(ges/hochs[i]);
			ges -= hochs[i]*ret[i];
		}
		return ret;
	}
	
	
	public static int zipen(int [] com) {
		int res = 0;
		for (int i = 1; i <= com.length; i++) {
			res +=  Math.pow(color, i-1)*com[i-1];
		}
		return res;
	}
	
	
	public static colors colortransformen(int f) {
		switch (f) {
		case 0:
			return colors.Rot;
		case 1:
			return colors.Gelb;
		case 2:
			return colors.Grün;
		case 3:
			return colors.Hellblau;
		case 4:
			return colors.Dunkelblau;
		default:
			return colors.Orange;
		}
	}
	
	
	public static int [] verteilung(int i) {
		int bewe [] = new int [steckerkom];
		for (int j = 0; j < komb.length; j++) {
			if(komb[j]) {
				bewe[bewertunginint(einzelnbewerten(extrahieren(i),extrahieren(j)))]++;
			}
		}
		return bewe;
	}
	
	
	public static int bewertunginint(stecker [] bew) {
		int ret = 0;
		int cou = len+1;
		for (int i = 0; i < bew.length; i++) {
			switch (bew[i]) {
			case schwarz:
				ret += cou;
				cou--;
				break;
			case weiß:
				ret++;
				break;
			default:
				break;
			}
		}
		return ret;
	}
	
	
	public static int [] maximaleabweichung(int [] verteilung) {
		for (int i : IntStream.range(0, verteilung.length).toArray()) {
	    	int swap = i;
	    	for (int e = i; e < verteilung.length;e++) {
	    		if (verteilung[swap] > verteilung[e]) {
	    			swap = e;
	    		}
	    	}
	    	int temp = verteilung[i]; 
	    	verteilung[i] = verteilung[swap]; 
	    	verteilung[swap] = temp;
	    }
		return verteilung;
	}
	
	
	public static int gesamtverteilung() {
		for (int i = 0; i < min.length; i++) {
			min[i] = komb.length;
		}
		int mine = 0;
		for (int i = 0; i < komb.length; i++) {
			if (komb[i] && mindeste(maximaleabweichung(verteilung(i)))) {
				mine = i;
			}
		}
		if (kommentare) {
			System.out.println("Die Besten Kombinationen:");
			System.out.println();
			for (int i = 0; i < komb.length; i++) {
				boolean temp =  mindeste(verteilung(i));
				if (komb[i] && temp) {
					for (int j = 0; j < len; j++) {
						System.out.print(colortransformen(extrahieren(i)[j]) + " ");
					}
					System.out.println();
				}
			}
			System.out.println("Ausgewählte beste Kombination ");
			print(min);
			System.out.println("Verteilung der bestenKombination ");
			print(verteilung(mine));
		}
		//print(maximaleabweichung(verteilung(mine)));
		return mine;
	}
	
	
	public static int naiv() {
		if(naiv) {
			for (int i = 0; i < komb.length; i++) {
				if (komb[i]) {
					return i;
				}
			}
			return 0;
		}else {
			return gesamtverteilung();
		}
	}
	
	public static boolean mindeste(int[] k) {
		for (int i = k.length-1; i>=0; i--) {
			if (min[i] < k[i]) {
				return false;
			}else if(min[i] > k[i]) {
				min = k;
				return true;
			}
		}
		return true;
	}
}
