
package tablasdeverdad;

import java.util.ArrayList;
import java.util.Stack;

public class patioDeManiobras {
    public ArrayList<Character> dEI;
    public ArrayList<Character> dEP;
    
    public patioDeManiobras(ArrayList<Character> EI){
        dEP=new ArrayList<Character>();
        dEI=new ArrayList<Character>(EI);
    }
    public patioDeManiobras(){
        
    }
    /*El método convertirEI_EP traduce una expresión aritmética escrita en notación infija
     a una expresión postfija o en notación polaca inversa.*/
    public ArrayList<Character> convertirEI_EP(){
//        String MEP= new String(); //Crea una String donde se almacenará la expresión postfija MEP
        ArrayList<Character> MEI= dEI;
        Stack<Character> pila=new Stack<>(); //Crea una pila donde se almacenarán temporalmente los operadores y paréntesis
        for(int i=0; i<MEI.size(); i++){
            Character token1 = MEI.get(i);
            if(Character.isAlphabetic(token1)){ //Si el token1 es un digito,se adiciona a la cadena de salida
//                MEP+=token1;
                
                dEP.add(token1); // se agraga el digito el token al Array List
                
                }else if(token1=='('){ //Si el token1 es un '(' se apila
                    pila.push(token1);
                    }else if(token1==')'){  //Si el token1 es ')' desapila los elementos que están en la cima siempre y cuando sean distintos de '('  
                        while(!pila.isEmpty() && pila.peek()!='('){
//                            MEP+=pila.pop();
                           
                            dEP.add(pila.pop()); // se agraga el token al Array List
                            
                            }
                        pila.pop(); // desapila '('
                        }else{  //sólo queda Si el token1 es un operador, se compara la jerarquia de "token1" con la del elemento que está en la cima de la pila "pila.peek()", si es menor o igual, se desapila el elemento de la cima
                            while(!pila.isEmpty() && jerarquia(token1) <= jerarquia(pila.peek())){
//                                MEP+=pila.pop();
                                
                                dEP.add(pila.pop()); // se agraga el token al Array List
                                
                            }
                            pila.push(token1); //sino es de menor o igual jerarquia se apila el operador
                            }  
        }
        while(!pila.isEmpty()){ //Desapila los operadores que aún están en la pila y los adiciona a la cadena de salida.
//            MEP+=pila.pop();
            
            dEP.add(pila.pop()); // se agraga el token al Array List
        }
        
//        return MEP;
        return dEP;
    }
    //OPCIÓN 1: El método indica la jerarquía de cada operador.
    public int jerarquia(Character ch){
        int r=0;
        if(ch.equals('∨')||ch.equals('∧')){
            r=1;
            }else if(ch.equals('→')||ch.equals('↔')){
                r=2;
                }else if(ch.equals('¬')){
                    r=3;
                    }
        return r;
    }
    
        
    public ArrayList<String> convertir_expresion_dosexp(ArrayList exp , String valp , String valq){
        ArrayList<String> rta = new ArrayList<String>();
        for(int k = 0;k<exp.size();k++){
            switch (String.valueOf(exp.get(k))) {
                case ("p"): {
                    rta.add(valp);
                    break;
                }
                case ("q"): {
                    rta.add(valq);
                    break;
                }
                default: {
                    rta.add(String.valueOf(exp.get(k)));
                    break;
                }
            }
        }
        return rta;
    }
    
    public ArrayList<String> convertir_expresion_tresexp(ArrayList exp , String valp , String valq , String valr){
        ArrayList<String> rta = new ArrayList<String>();
        for(int k = 0;k<exp.size();k++){
            switch (String.valueOf(exp.get(k))) {
                case ("p"): {
                    rta.add(valp);
                    break;
                }
                case ("q"): {
                    rta.add(valq);
                    break;
                }
                case ("r"): {
                    rta.add(valr);
                    break;
                }
                default: {
                    rta.add(String.valueOf(exp.get(k)));
                    break;
                }
            }
        }
        return rta;
    }
     
    
    public ArrayList<String> convertir_expresion_cuatroexp(ArrayList exp , String valp , String valq , String valr , String vals){
        ArrayList<String> rta = new ArrayList<String>();
        for(int k = 0;k<exp.size();k++){
            switch (String.valueOf(exp.get(k))) {
                case ("p"): {
                    rta.add(valp);
                    break;
                }
                case ("q"): {
                    rta.add(valq);
                    break;
                }
                case ("r"): {
                    rta.add(valr);
                    break;
                }
                case ("s"): {
                    rta.add(vals);
                    break;
                }
                default: {
                    rta.add(String.valueOf(exp.get(k)));
                    break;
                }
            }
        }
        return rta;
    }
        public ArrayList<Character> convertirEI_EP2(ArrayList<Character> MEI){
        ArrayList<Character> dEP2 =new ArrayList<Character>();
        Stack<Character> pila=new Stack<>(); //Crea una pila donde se almacenarán temporalmente los operadores y paréntesis
        for(int i=0; i<MEI.size(); i++){
            Character token1 = MEI.get(i);
            if(token1.equals('p') || token1.equals('q') || token1.equals('r') || token1.equals('s')){// se valida si el caracter es p o q
                dEP2.add(token1); // se agraga el digito el token al Array List
                }else if(token1=='('){ //Si el token1 es un '(' se apila
                    pila.push(token1);
                    }else if(token1==')'){  //Si el token1 es ')' desapila los elementos que están en la cima siempre y cuando sean distintos de '('  
                        while(!pila.isEmpty() && pila.peek()!='('){
                            dEP2.add(pila.pop()); // se agraga el token al Array List
                            }
                        pila.pop(); // desapila '('
                        }else{  //sólo queda Si el token1 es un operador, se compara la jerarquia de "token1" con la del elemento que está en la cima de la pila "pila.peek()", si es menor o igual, se desapila el elemento de la cima
                            while(!pila.isEmpty() && jerarquia2(token1) <= jerarquia2(pila.peek())){
                                dEP2.add(pila.pop()); // se agraga el token al Array List
                            }
                            pila.push(token1); //sino es de menor o igual jerarquia se apila el operador
                            }  
        }
        while(!pila.isEmpty()){ //Desapila los operadores que aún están en la pila y los adiciona a la cadena de salida.
            dEP2.add(pila.pop()); // se agraga el token al Array List
        }
        return dEP2;
    }
    static int jerarquia2(Character ch){
        int r=0;
        if(ch.equals('∧')||ch.equals('V')){
            r=1;
            }else if(ch.equals('→')||ch.equals('↔')){
                r=2;
                }else if(ch.equals('¬')){
                    r=3;
                    } 
        return r;
    }
        
        public String calcular_exp(ArrayList pdatos){
        String f = "";
        String token2, N1="", N2="";
        for (int i = 0; i < pdatos.size(); i++) {
            token2 = String.valueOf(pdatos.get(i));
            if (validar_operador2(token2)){
                if(token2.equals("¬")){
                    N2 = String.valueOf(pdatos.get(i - 1));
                    f = operacion_aritmetrica2(token2, N1, N2);
                    pdatos.set(i, f);
                    pdatos.remove(i - 1);
                    i = -1;
                } else {
                    N1 = String.valueOf(pdatos.get(i - 2));
                    N2 = String.valueOf(pdatos.get(i - 1));
                    f = operacion_aritmetrica2(token2, N1, N2);
                    pdatos.set(i, f);
                    pdatos.remove(i - 1);
                    pdatos.remove(i - 2);
                    i = -1;
                }

            }
        }
        return f;
    }
    static String operacion_aritmetrica2(String operador, String operando1, String operando2) {
        if (null == operador) {
            return null;
        } else {
                switch (operador) {
                case ("¬"):{
                    if(operando2.equals("v")){
                        operando2 = "f";
                    }else{
                         operando2 = "v";
                    }
                    return operando2;
                }
                case ("→"):{
                    if(operando1.equals("v")&& operando2.equals("f")){
                        return "f";
                    }else{
                        return "v";
                    }
                    
                }
                case ("↔"):{
                     if((operando1.equals("v")&& operando2.equals("f")) || (operando1.equals("f")&& operando2.equals("v"))){
                         return "f";
                     }else{
                         return "v";
                     }
                }
                case ("∧") :{
                    if(operando1.equals("v")&&operando2.equals("v")){
                        return "v";
                    }else{
                        return "f";
                    } 
                }
                case ("V") :{
                    if(operando1.equals("f")&&operando2.equals("f")){
                        return "f";
                    }else{
                        return "v";
                    }
                }
            }
        }
        return "";
    }
    
        //Este metodo devuelve True cuando encuentra un operador
    static boolean validar_operador2(String token) {
        return "¬".equals(token) || "→".equals(token) || "↔".equals(token) || "∧".equals(token) || "V".equals(token);
    }
}