package tablasdeverdad;

import java.util.ArrayList;
import tablasdeverdad.TablasDeVerdad;
import java.util.ArrayList;
import java.util.HashSet;

public class Control {
    public static ArrayList<Character> obtenerLetrasSinRepetir(ArrayList<Character> listaCaracteres) {
        ArrayList<Character> listaLetrasSinRepetir = new ArrayList<>(); // Nuevo ArrayList para almacenar las letras sin repetir
        
        // Usar un conjunto HashSet para almacenar las letras sin repetir
        HashSet<Character> conjuntoLetras = new HashSet<>();
        
        // Iterar a través de la lista de caracteres
        for (Character c : listaCaracteres) {
            if (Character.isLetter(c)) { // Verificar si el caracter es una letra
                conjuntoLetras.add(c); // Agregar el caracter al conjunto
            }
        }
        
        // Agregar los caracteres del conjunto al nuevo ArrayList
        listaLetrasSinRepetir.addAll(conjuntoLetras);
        
        return listaLetrasSinRepetir; // Devolver la nueva lista con las letras sin repetir
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        ArrayList<Character> listaCaracteres = new ArrayList<>();
        listaCaracteres.add('h');
        listaCaracteres.add('o');
        listaCaracteres.add('l');
        listaCaracteres.add('a');
        listaCaracteres.add('1'); // No es una letra
        listaCaracteres.add('m');
        listaCaracteres.add('u');
        listaCaracteres.add('n');
        listaCaracteres.add('d');
        listaCaracteres.add('o');
        
        ArrayList<Character> listaLetrasSinRepetir = obtenerLetrasSinRepetir(listaCaracteres);
        
        // Imprimir el resultado
        System.out.println("Lista de caracteres: " + listaCaracteres);
        System.out.println("Lista de letras sin repetir: " + listaLetrasSinRepetir);
    }
}


