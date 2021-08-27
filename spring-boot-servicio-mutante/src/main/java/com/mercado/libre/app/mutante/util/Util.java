package com.mercado.libre.app.mutante.util;

import java.util.function.Function;

public class Util {

	public static Function<String, Boolean> datoPermitido = (letra) -> {
		Boolean letrasValidas = false;
		switch (letra) {
		case "A":
			letrasValidas = true;
			break;
		case "T":
			letrasValidas = true;
			break;
		case "C":
			letrasValidas = true;
			break;
		case "G":
			letrasValidas = true;
			break;
		}
		return letrasValidas;
	};

	public static String[][] obtenerArray(String dna[]) {
		int n = dna.length;
		String matrixDna[][] = new String[n][n];
		for (int i = 0; i < dna.length; i++) {
			for (int j = 0; j < dna.length; j++) {
				if (datoPermitido.apply(Character.toString(dna[i].toUpperCase().charAt(j)))) {
					matrixDna[i][j] = Character.toString(dna[i].toUpperCase().charAt(j));
				} else {
					return null;
				}
			}
		}
		return matrixDna;
	}
	
	public static String toString(Object[][] a) {
	    if (a == null)
	        return null;
	    int iMax = a.length - 1;
	    if (iMax == -1)
	        return "[]";

	    StringBuilder b = new StringBuilder();
	    b.append('[');
	    for (int i = 0; ; i++) {
	        b.append(String.valueOf(a[i][i]));
	        if (i == iMax)
	            return b.append(']').toString();
	        b.append(", ");
	    }
	}

}
