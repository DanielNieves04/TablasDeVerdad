package tablasdeverdad;

import java.util.ArrayList;

public class Conjuntos {
    public static int inicioIzq = 0, finalDer = 0;
    public static String expC;
    public static void main(String[] args) {
        // Operadores: →, ↔, ∨, ∧, ¬, ∪, ∩, ', Δ , -
        String exp = "(B°-(A°∪(BΔC)))°";
        ArrayList<Character> a = new ArrayList<Character>();
        for(int i=0;i<exp.length();i++){
            a.add(exp.charAt(i));
        }
        System.out.println(exp);
        Conjuntos c=new Conjuntos(a);
        System.out.println(c.convertirExpConjunto());
        //c.diferenciaSimetrica();
    }
    public Conjuntos(ArrayList<Character> exp){
        expC = convertirArray_String(exp);
    }
    public ArrayList convertirExpConjunto() {
        ArrayList<Character> rta = new ArrayList<Character>();
        String convertida = "";
        String exp = expC;
        int pos = 0;
        char ca = ' ';
        char cs = ' ';
        exp = diferenciaSimetrica();
        for (int x = 0; x < exp.length(); x++) {
            char c = exp.charAt(x);
            if (x >= 1) {
                ca = exp.charAt(x - 1);
            }
            if (x < exp.length() - 1) {
                cs = exp.charAt(x + 1);
            }
            if (Character.isLetter(ca)) {
                ca = letras(ca);
            }
            if (Character.isLetter(cs)) {
                cs = letras(cs);
            }
            if (esOperador(cs) && Character.isLetter(c)) {
                c = letras(c);
                convertida = convertida + c;
                
            } else {
                if (c == '(') {
                    convertida = convertida + c;
                } else {
                    if (c == ')') {
                        convertida = convertida + c;
                    } else {
                        if (c == '∪') {
                            convertida = convertida + '∨';
                        } else {
                            if (c == '∩') {
                                convertida = convertida + '∧';
                            } else {
                                if (c == '°') {
                                    if (ca == ')') {
                                        convertida = complemento(convertida);
                                    } else {
                                        convertida = convertida + '¬' + ca;
                                    }
                                } else {
                                    if (c == '-') {
                                        convertida = convertida + '∧' + '¬';
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        rta = convertirString_Array(convertida);
        return rta;
    }
    
    public static Character letras(char c) {
        char r = ' ';
        if (c == 'A') {
            r = 'p';
        } else {
            if (c == 'B') {
                r = 'q';
            } else {
                if (c == 'C') {
                    r = 'r';
                } else {
                    if (c == 'D') {
                        r = 's';
                    }
                }
            }
        }
        return r;
    }
    public static boolean esOperador(char cs) {
        if (cs != '°') {
            return true;
        } else {
            return false;
        }
    }
    
    public static String diferenciaSimetrica(){
        for(int x=0; x<expC.length(); x++){
            if(expC.charAt(x)=='Δ'){
                String rta= convertirDiferencia(x);
                modificarExpC(rta);
            }
            System.out.println(expC);
        }
        return expC;
    }

    private static String convertirDiferencia(int x) {
        int cP = 0;
        String izq = "", der = "", rta = "";
        int auxPos = x;
        for (int j = auxPos - 1; j >= 0; j--) {
            if (expC.charAt(j) == ')') {
                cP++;
            } else if (expC.charAt(j) == '(') {
                cP--;
                if (cP == 0) {
                    inicioIzq = j;
                    break;
                }
            }
            if (Character.isAlphabetic(expC.charAt(auxPos-1))) {
                inicioIzq=auxPos-1;
                break;
            }
            if (expC.charAt(auxPos-1)=='°') {
                inicioIzq=auxPos-2;
                break;
            }
        }
        for (int j = auxPos + 1; j < expC.length(); j++) {
            if (expC.charAt(j) == ')') {
                cP--;
                if (cP == 0) {
                    finalDer = j;
                    break;
                }
            } else if (expC.charAt(j) == '(') {
                cP++;
            }
            if (Character.isAlphabetic(expC.charAt(auxPos+1))) {
                finalDer=auxPos+1;
                break;
            }
            if (expC.charAt(auxPos+2)=='°') {
                finalDer=auxPos+2;
                break;
            }
        }
        for(int j=inicioIzq; j<auxPos; j++){
            izq=izq+expC.charAt(j);
        }
        for(int j=auxPos+1; j<=finalDer; j++){
            der=der+expC.charAt(j);
        }
        rta="("+izq+"∪"+der+")";
        rta=rta+"-";
        rta=rta+"("+izq+"∩"+der+")";
        return rta;
    }

    private static void modificarExpC(String rta) {
        String expAdIzq="",expAdDer="";
        for (int j=0; j <inicioIzq; j++) {
            expAdIzq=expAdIzq+expC.charAt(j);
        }
        for (int j=finalDer+1; j <expC.length(); j++) {
            expAdDer=expAdDer+expC.charAt(j);
        }
        expC=expAdIzq+rta+expAdDer;
    }

    private String convertirArray_String(ArrayList<Character> exp) {
        String rta="";
        for(int i=0;i<exp.size();i++){
            rta=rta+exp.get(i);
        }
        return rta;
    }
    private static ArrayList<Character> convertirString_Array(String x) {
        ArrayList<Character> a = new ArrayList<Character>();
        for(int i=0;i<x.length();i++){
            a.add(x.charAt(i));
        }
        return a;
    }
    public static String complemento(String exp) {
        char x, ax = '¬';
        String izq = "";
        int cont = 0, cp = 0;
        if (exp.charAt(exp.length()-1) == ')') {
            for (int i = exp.length()-1; i > 0; i--) {
                x = exp.charAt(i);
                if (x == ')') {
                    
                    cont++;
                }
                if (x == '(') {
                    cont--;
                    if (cont <= 0) {
                        cp = i;
                        break;
                    }
                }
            }
            int i;
            for (i = 0; i < cp; i++) {
                x = exp.charAt(i);
                izq = izq + x;
            }
            if (i == cp) {
                izq = izq + ax;
            }
            for (i = cp; i <= exp.length()-1; i++) {
                x = exp.charAt(i);
                izq = izq + x;
            }
        }
        return izq;
    }
}
