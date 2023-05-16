package tablasdeverdad;

import java.util.ArrayList;

public class TablasDeVerdad {
    public Character[][] mSolucion;//Crear matriz
    public Character[][] mAuxiliar;
    public ArrayList<Character> letras;
    public TablasDeVerdad(ArrayList<Character> numeroLetras,ArrayList<Character> eLogicas){
        letras= new ArrayList<Character>(numeroLetras);
        mAuxiliar = new Character[((int) Math.pow(2,numeroLetras.size()))+1][numeroLetras.size()];
        mSolucion = new Character[(int) Math.pow(2,numeroLetras.size())+1][eLogicas.size()];
        //letras=numeroLetras;
    }

    public void rellenarMatrizAuxiliar(){
        Character dB='v';//Dato booleano
        int i;
        int nfilas =(int) Math.pow(2,letras.size());//forma de exponente
        int aux=nfilas/2;
        for(int j=0;j<letras.size();j++){
            mAuxiliar[0][j] = letras.get(j);
            for(i=1;i<=nfilas;i=i+0){
                for(int k=0;k<aux;k++){
                    mAuxiliar[i][j]=dB;
                    i++;
                }
                if('v'==mAuxiliar[i-1][j]){
                    dB='f';
                }else{
                    dB='v';
                }                    
            }
            aux=aux/2;
        }
    }
    public void rellenarmatrizSolucion(ArrayList<Character> exPos){
        rellenarMatrizAuxiliar();
        for(int j=0;j<mSolucion[0].length;j++){
            mSolucion[0][j] = exPos.get(j);
            if(Character.isAlphabetic(mSolucion[0][j])){
                isLetra(j,mSolucion[0][j]);
            }else{
                isOperador(j,mSolucion[0][j]);
            }
        }
        String token2, N1, N2;
        Character resultado='n';
        for(int i=1;i<mSolucion.length;i++){
            ArrayList<Character> aux = new ArrayList<Character>();
            ArrayList<Character> auxPos = new ArrayList<Character>(exPos);
            for(int j=0;j<mSolucion[0].length;j++){
                aux.add(mSolucion[i][j]);
            }
            for(int j=0;j<aux.size();j++){
                token2 = String.valueOf(aux.get(j));
                Character auxC = aux.get(j);
                if (validar_operador(token2)){
                    N1 = String.valueOf(aux.get(j-2));
                    N2 = String.valueOf(aux.get(j-1));
                    resultado = operacion_logica(token2, N1, N2);
                    aux.set(j, resultado);
                    mSolucion[i][auxPos.indexOf(auxC)]=resultado;
                    auxPos.set(auxPos.indexOf(auxC), resultado);
                    aux.remove(j - 1);
                    aux.remove(j - 2);
                    j = -1;
                }
                if("¬".equals(token2)){
                    N2 = String.valueOf(aux.get(j-1));
                    resultado = operacion_logica(token2, "null", N2);
                    aux.set(j, resultado);
                    mSolucion[i][auxPos.indexOf(auxC)]=resultado;
                    auxPos.set(auxPos.indexOf(auxC), resultado);
                    aux.remove(j - 1);
                    j = -1;
                }
            }
            //mSolucion[i][mSolucion[0].length-1]=aux.get(0);
        }
    }

    static boolean validar_operador(String token) {
        return "∨".equals(token) || "∧".equals(token) || "→".equals(token) || "↔".equals(token);
    }
    static Character operacion_logica(String operador, String operando1, String operando2) {
        if (null == operador) {
            return null;
        } else {
                switch (operador) {
                case "¬":{
                    if(operando2.equals("v")){
                        return 'f';
                    }else{
                        return 'v';
                    }
                }
                case "∨":{
                    if(operando1.equals("f")&&operando2.equals("f")){
                        return 'f';
                    }else{
                        return 'v';
                    }
                }
                case "∧":{
                    if(operando1.equals("v")&&operando2.equals("v")){
                        return 'v';
                    }else{
                        return 'f';
                    }
                }
                case "→" :{
                    if(operando1.equals("v")&&operando2.equals("f")){
                        return 'f';
                    }else{
                        return 'v';
                    }
                }
                default:{
                    if(operando1.equals("v")&&operando2.equals("v")){
                        return 'v';
                    }else if(operando1.equals("f")&&operando2.equals("f")){
                        return 'v';
                    }else{
                        return 'f';
                    }
                }
            }
        }
    }
    public void isLetra(int pos,Character id){
        int aux=-1;
        for(int j=0;j<mAuxiliar[0].length;j++){
            if(mAuxiliar[0][j]==id){
                aux=j;
            }
        }
        for(int i=1;i<mSolucion.length;i++){
            mSolucion[i][pos]= mAuxiliar[i][aux];
        }
    }
    public void isOperador(int pos,Character id){
        for(int i=1;i<mSolucion.length;i++){
            mSolucion[i][pos]=id;
        }
    }
}
