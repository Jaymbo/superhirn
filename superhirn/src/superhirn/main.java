package superhirn;

import java.util.Iterator;

public class main {	
	public static int color = 6;
	public static int len = 4;
	public static int reihen = 10;
	public static boolean komb [] = new boolean [(int)Math.pow(color, len)];
	public enum colors {
		Rot,Gelb,Gr�n,Hellblau,Dunkelblau,Orange;
	}
	public enum stecker {
		non,wei�,schwarz;
	}
	public static int reihe = 0;
	public static stecker [][] bewertung = new stecker [reihen][len];
	public static int [][] gesteckt = new int [reihen][len];
	
	public static void main(String[] args) {
		System.out.println("starting");
		for (int i = 0; i < komb.length; i++) {
			komb[i] = true;
		}
		for (int i = 0; i < bewertung.length; i++) {
			for (int j = 0; j < bewertung[i].length; j++) {
				bewertung[i][j] = stecker.non;
			}
		}
		int zuer = 10;
			for (int j = 0; j < komb.length; j++) {
				if (komb[j]) {
					System.out.println("if " + j);
					bewertung[reihe] = einzelnbewerten(extrahieren(zuer),extrahieren(j));
					gesteckt[reihe] = extrahieren(j);
					gesamt_bewerten();
					print_komb();
					reihe++;
				}
			}
		System.out.println("Hat "+reihe+" Reihen gebraucht.");
	}
	
	public static void print_komb() {
		for (int i = 0; i < komb.length; i++) {
			if(komb[i])
				System.out.println(i+" verbleibend");
		}
		System.out.println();
	}
	
	public static void gesamt_bewerten() {
		for (int i = 0; i < komb.length; i++) {
			if(komb[i]) {
				stecker temp [] = einzelnbewerten(extrahieren(i),gesteckt[reihe]);
				for (int j = 0; j < len; j++) {
					if(!temp[j].equals(bewertung[reihe][j])) {
						System.out.println(i + " raus");
						komb[i] = false;
						break;
					}
				}
			}
		}
		System.out.println();
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
			for (int j = 0; j < len; j++) {
				if (!used1[i] && !used2[j] && (v1[i] == v2[j])) {
					ret[count] = stecker.wei�;
					used1[i] = true;
					used2[j] = true;
					count++;
				}
			}
		}
		return ret;
	}
	
	public static int [] extrahieren(int ges) {
		int [] ret = new int [len];
		for (int i = len-1; i >= 0; i--) {
			ret[i] = (int)(ges/Math.pow(color, i));
			ges -= Math.pow(color, i)*ret[i];
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
	
	public colors colortransformen(int f) {
		switch (f) {
		case 0:
			return colors.Rot;
		case 1:
			return colors.Gelb;
		case 2:
			return colors.Gr�n;
		case 3:
			return colors.Hellblau;
		case 4:
			return colors.Dunkelblau;
		default:
			return colors.Orange;
		}
	}

}
