
package Interfaz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import tablasdeverdad.Conjuntos;
import tablasdeverdad.TablasDeVerdad;
import tablasdeverdad.patioDeManiobras;

public class Interfaz extends javax.swing.JFrame {
    ArrayList<Integer> U =new ArrayList();
    ArrayList<Integer> A =new ArrayList();
    ArrayList<Integer> B=new ArrayList();
    ArrayList<Integer> C=new ArrayList();
    ArrayList<Integer> D=new ArrayList();
    ArrayList<Integer> solucion=new ArrayList<>();
    Tabla tb=new Tabla();
    String salida0="",salida1="",salida2="",salida3="",salida4="",salida5="",salida6="";
    int n;
    boolean p, q,r,s, rep;
    ArrayList<Character> EI= new ArrayList<Character>();
    ArrayList<Character> EIC= new ArrayList<Character>();
    boolean esOperador2;
    ArrayList<Character> EP = new ArrayList<Character>();
    ArrayList<Character> EPC = new ArrayList<Character>();
    ArrayList<Character> NL = new ArrayList<Character>();
    int i=0,l=0;
    int auxP=-1,auxPC=-1;//Auxiliar para balanceo de los parentecis
    String imprimir="";//ayuda a imprimir la tabla de verdad
    public Interfaz() {
        initComponents();
        //cajaTexto1.setEnabled(false);
        nLetras();
        Button_A.setEnabled(false);
        Button_B.setEnabled(false);
        Button_C.setEnabled(false);
        Button_D.setEnabled(false);
        this.setLocationRelativeTo(null);
    }
    int vali = 0;

    /**
     * Creates new form principal
     */
    // validaciones 
    public void valvacio_registrar_valores() throws Exception {
        // validacion de vacios
        if (tf_valp.getText().isEmpty()) {
            throw new Exception("ingrese los campos requeridos");
        }
        if (tf_valq.getText().isEmpty()) {
            throw new Exception("ingrese los campos requeridos");
        }
        if (tf_valr.getText().isEmpty()) {
            throw new Exception("ingrese los campos requeridos");
        }
        if (tf_vals.getText().isEmpty()) {
            throw new Exception("ingrese los campos requeridos");
        }
    }

    public void valvacio_cantidad_expresiones() throws Exception {
        if (tf_box_nexp.getText().isEmpty()) {
            throw new Exception("ingrese los campos requeridos");
        }
    }

    public void valvacio_exp() throws Exception {
        if (tf_expresion.getText().isEmpty()) {
            throw new Exception("ingrese los campos requeridos");
        }
    }

    public void val_enb() throws Exception {
        if (!tf_expresion.isEnabled()) {
            throw new Exception("Campo desabilitado");
        }
    }
       // validacion de expresion : vefifica , balanceo de parentesis , 
    // verifica que no existan dos operandos seguidos
    public boolean validarExpresion(String expresion) {
        Stack<Character> pila = new Stack<>();
        boolean operadorAnterior = true;
        boolean negacionAnterior = false;
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);
            if (c == 'p' || c == 'q' || c == 'r' || c == 's') {
                if (!operadorAnterior && !negacionAnterior) {
                    return false;
                }
                operadorAnterior = false;
                negacionAnterior = false;
            } else if (c == '∧' || c == 'V' || c == '→' || c == '↔') {
                if (operadorAnterior || negacionAnterior) {
                    return false;
                }
                operadorAnterior = true;
                negacionAnterior = false;
            } else if (c == '¬') {
                if (!operadorAnterior && !pila.isEmpty() && pila.peek() != '(') {
                    return false;
                }
                negacionAnterior = true;
            } else if (c == '(') {
                if (!operadorAnterior && !negacionAnterior) {
                    return false;
                }
                pila.push(c);
                operadorAnterior = true;
                negacionAnterior = false;
            } else if (c == ')') {
                if (pila.isEmpty() || pila.peek() != '(') {
                    return false;
                }
                pila.pop();
                operadorAnterior = false;
                negacionAnterior = false;
            } else {
                return false;
            }
        }
        return pila.isEmpty() && !operadorAnterior && !negacionAnterior;
    }

    // metodo que verifica que no puede existir una operacion + parentesis de cierre
    public static Boolean val2(String exp) {
        for (int i = 0; i < exp.length() - 1; i++) {
            int count = i + 1;
            String ca = String.valueOf(exp.charAt(i));
            String cd = String.valueOf(exp.charAt(count));
            if ((ca.equals("∧") || ca.equals("V") || ca.equals("→") || ca.equals("↔") || ca.equals("¬")) && (cd.equals(")"))) {
                return false;
            }
        }
        return true;
    }

    // metodo que verifica que no puede existir primero un operando y luego una negacion 
    public static Boolean val4(String exp) {
        for (int i = 0; i < exp.length() - 1; i++) {
            int count = i + 1;
            String ca = String.valueOf(exp.charAt(i));
            String cd = String.valueOf(exp.charAt(count));
            if ((ca.equals("p") || ca.equals("q") || ca.equals("r") || ca.equals("s")) && (cd.equals("¬"))) {
                return false;
            }
        }
        return true;
    }

    // verifica que no puede existir un parentesis de cierre y luego una negacion
    // verifica que no puede existir dos negaciones seguidas
    // verifica que no puede existir negacion + operador
    // verifica que no puede existir negacion entre operandos
    // verifica que no puede existir negacion + parentesis de cierre
    public static Boolean val3(String exp) {
        boolean r = true;
        char cc = ' ';
        for (int i = 1; i < exp.length(); i++) {
            char c = exp.charAt(i - 1), cd = exp.charAt(i);
            if (esOperador(c) && esOperador2(cd)) {
                return false;
            }
            if (c == '¬' && cd == '¬') {
                return false;
            }
            if (esOperando(c) && cd == '¬') {
                return false;
            }
            cc = cd;
        }
        if (cc == '¬') {
            return false;
        }
        return r;
    }

    private static boolean esOperador2(char caracter) {
        return caracter == '∧' || caracter == 'V' || caracter == '→' || caracter == '↔';
    }

    private static boolean esOperando(char c) {
        return c == 'p' || c == 'q' || c == 'r' || c == 's';
    }

    private static boolean esOperador(char caracter) {
        return caracter == '∧' || caracter == 'V' || caracter == '¬' || caracter == '→' || caracter == '↔';
    }

    // verifica que no puede existir parentesis de cierre + negacion
    public static Boolean val6(String exp) {
        for (int i = 0; i < exp.length() - 1; i++) {
            int count = i + 1;
            String ca = String.valueOf(exp.charAt(i));
            String cd = String.valueOf(exp.charAt(count));
            if ((ca.equals(")")) && (cd.equals("¬"))) {
                return false;
            }
        }
        return true;
    }

    // verifica que no pueden existir parentesis vacios
    public static Boolean val7(String exp) {
        for (int i = 0; i < exp.length() - 1; i++) {
            int count = i + 1;
            String ca = String.valueOf(exp.charAt(i));
            String cd = String.valueOf(exp.charAt(count));
            if ((ca.equals("(")) && (cd.equals(")"))) {
                return false;
            }
        }
        return true;
    }

    // validacion de expresion completa
    public void val_exp(String exp) throws Exception {
        if (validarExpresion(exp) && val2(exp) && val4(exp) && val3(exp) && val6(exp) && val7(exp)) {
            //throw new Exception("La expresión es válida.");
        } else {
            throw new Exception("La expresión no es válida.");
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cajaTexto1 = new javax.swing.JTextField();
        Button_p = new javax.swing.JButton();
        Button_q = new javax.swing.JButton();
        Button_r = new javax.swing.JButton();
        Button_s = new javax.swing.JButton();
        Button_o = new javax.swing.JButton();
        Button_y = new javax.swing.JButton();
        Button_entonces = new javax.swing.JButton();
        Button_SiYSoloSi = new javax.swing.JButton();
        Button_negacion = new javax.swing.JButton();
        Button_ParentesisAbierto = new javax.swing.JButton();
        Button_parentesisCerrado = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btn_registrar_val = new javax.swing.JButton();
        btn_registrar = new javax.swing.JButton();
        tf_box_nexp = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        text_4 = new javax.swing.JLabel();
        labelq = new javax.swing.JLabel();
        labelp = new javax.swing.JLabel();
        tf_valp = new javax.swing.JTextField();
        sp = new javax.swing.JSeparator();
        tf_valq = new javax.swing.JTextField();
        sq = new javax.swing.JSeparator();
        labelr = new javax.swing.JLabel();
        sr = new javax.swing.JSeparator();
        tf_valr = new javax.swing.JTextField();
        labels = new javax.swing.JLabel();
        tf_vals = new javax.swing.JTextField();
        ss = new javax.swing.JSeparator();
        label_text_input = new javax.swing.JLabel();
        btn_expesion = new javax.swing.JButton();
        s_exp = new javax.swing.JSeparator();
        tf_expresion = new javax.swing.JTextField();
        titulo_1 = new javax.swing.JLabel();
        text_2 = new javax.swing.JLabel();
        text_3 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        CajaTexto19 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        CajaTexto20 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        CajaTexto21 = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        CajaTexto22 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        CajaTexto23 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        CajaTexto24 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        CajaTexto25 = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        CajaTexto26 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        CajaTexto27 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        CajaTexto28 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        cajaTexto2 = new javax.swing.JTextField();
        jButton_ir = new javax.swing.JButton();
        jButton_borrar = new javax.swing.JButton();
        jButton_limpiar = new javax.swing.JButton();
        Button_A = new javax.swing.JButton();
        Button_B = new javax.swing.JButton();
        Button_C = new javax.swing.JButton();
        Button_D = new javax.swing.JButton();
        Button_interseccion = new javax.swing.JButton();
        Button_union = new javax.swing.JButton();
        Button_complemento = new javax.swing.JButton();
        Button_diferencia = new javax.swing.JButton();
        Button_diferenciaSimetrica = new javax.swing.JButton();
        Button_ParentesisAbierto1 = new javax.swing.JButton();
        Button_parentesisCerrado1 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("panel");
        setLocationByPlatform(true);
        setName("panel"); // NOI18N
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 36)); // NOI18N
        jLabel1.setText("TABLAS DE VERDAD");

        jLabel2.setText("Expresión logica: ");

        cajaTexto1.setEditable(false);
        cajaTexto1.setText("Ejemplo: p∨q");
        cajaTexto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTexto1ActionPerformed(evt);
            }
        });

        Button_p.setText("P");
        Button_p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_pActionPerformed(evt);
            }
        });

        Button_q.setText("Q");
        Button_q.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_qActionPerformed(evt);
            }
        });

        Button_r.setText("R");
        Button_r.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_rActionPerformed(evt);
            }
        });

        Button_s.setText("S");
        Button_s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_sActionPerformed(evt);
            }
        });

        Button_o.setText("∨");
        Button_o.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_oActionPerformed(evt);
            }
        });

        Button_y.setText("∧");
        Button_y.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_yActionPerformed(evt);
            }
        });

        Button_entonces.setText("→");
        Button_entonces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_entoncesActionPerformed(evt);
            }
        });

        Button_SiYSoloSi.setText("↔");
        Button_SiYSoloSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_SiYSoloSiActionPerformed(evt);
            }
        });

        Button_negacion.setText("¬");
        Button_negacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_negacionActionPerformed(evt);
            }
        });

        Button_ParentesisAbierto.setText("(");
        Button_ParentesisAbierto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_ParentesisAbiertoActionPerformed(evt);
            }
        });

        Button_parentesisCerrado.setText(")");
        Button_parentesisCerrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_parentesisCerradoActionPerformed(evt);
            }
        });

        jButton1.setText("Ir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Limpiar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Borrar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setText("SOLUCION: ");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cajaTexto1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Button_p)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_q)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_r)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_s)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_o)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_y)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_entonces)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_SiYSoloSi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_negacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_ParentesisAbierto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_parentesisCerrado)))
                .addGap(0, 37, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cajaTexto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Button_p)
                    .addComponent(Button_q)
                    .addComponent(Button_r)
                    .addComponent(Button_o)
                    .addComponent(Button_y)
                    .addComponent(Button_entonces)
                    .addComponent(Button_SiYSoloSi)
                    .addComponent(Button_negacion)
                    .addComponent(Button_ParentesisAbierto)
                    .addComponent(Button_parentesisCerrado)
                    .addComponent(Button_s))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel1);

        jTabbedPane1.addTab("Expresiones Logicas", jScrollPane2);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_registrar_val.setBackground(new java.awt.Color(109, 160, 255));
        btn_registrar_val.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btn_registrar_val.setText("Registrar valores");
        btn_registrar_val.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_registrar_val.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_registrar_valMouseClicked(evt);
            }
        });
        btn_registrar_val.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrar_valActionPerformed(evt);
            }
        });
        jPanel4.add(btn_registrar_val, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, 150, 40));

        btn_registrar.setBackground(new java.awt.Color(109, 160, 255));
        btn_registrar.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btn_registrar.setText("Registrar numero");
        btn_registrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_registrarMouseClicked(evt);
            }
        });
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });
        jPanel4.add(btn_registrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, -1, 40));

        tf_box_nexp.setBackground(new java.awt.Color(204, 204, 204));
        tf_box_nexp.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tf_box_nexp.setBorder(null);
        tf_box_nexp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tf_box_nexpMouseClicked(evt);
            }
        });
        tf_box_nexp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_box_nexpActionPerformed(evt);
            }
        });
        tf_box_nexp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_box_nexpKeyTyped(evt);
            }
        });
        jPanel4.add(tf_box_nexp, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 70, -1));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 70, 16));

        text_4.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        text_4.setForeground(new java.awt.Color(102, 102, 102));
        text_4.setText(" Numero de expresiones de desea calcular :");
        jPanel4.add(text_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 441, 20));

        labelq.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        labelq.setForeground(new java.awt.Color(102, 102, 102));
        labelq.setText("Valor de verdad de Q :");
        jPanel4.add(labelq, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        labelp.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        labelp.setForeground(new java.awt.Color(102, 102, 102));
        labelp.setText("Valor de verdad de P :");
        jPanel4.add(labelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        tf_valp.setBackground(new java.awt.Color(204, 204, 204));
        tf_valp.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tf_valp.setBorder(null);
        tf_valp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tf_valpMouseClicked(evt);
            }
        });
        tf_valp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_valpActionPerformed(evt);
            }
        });
        tf_valp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_valpKeyTyped(evt);
            }
        });
        jPanel4.add(tf_valp, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 50, -1));

        sp.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.add(sp, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 50, 13));

        tf_valq.setBackground(new java.awt.Color(204, 204, 204));
        tf_valq.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tf_valq.setBorder(null);
        tf_valq.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tf_valqMouseClicked(evt);
            }
        });
        tf_valq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_valqKeyTyped(evt);
            }
        });
        jPanel4.add(tf_valq, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 50, -1));
        jPanel4.add(sq, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 50, 13));

        labelr.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        labelr.setForeground(new java.awt.Color(102, 102, 102));
        labelr.setText("Valor de verdad de R :");
        jPanel4.add(labelr, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        sr.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.add(sr, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 50, 13));

        tf_valr.setBackground(new java.awt.Color(204, 204, 204));
        tf_valr.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tf_valr.setBorder(null);
        tf_valr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tf_valrMouseClicked(evt);
            }
        });
        tf_valr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_valrKeyTyped(evt);
            }
        });
        jPanel4.add(tf_valr, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, 50, -1));

        labels.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        labels.setForeground(new java.awt.Color(102, 102, 102));
        labels.setText("Valor de verdad de S :");
        jPanel4.add(labels, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        tf_vals.setBackground(new java.awt.Color(204, 204, 204));
        tf_vals.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tf_vals.setBorder(null);
        tf_vals.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tf_valsMouseClicked(evt);
            }
        });
        tf_vals.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_valsKeyTyped(evt);
            }
        });
        jPanel4.add(tf_vals, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 50, -1));
        jPanel4.add(ss, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 330, 50, 13));

        label_text_input.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        label_text_input.setForeground(new java.awt.Color(102, 102, 102));
        label_text_input.setText("Expresion a evaluar :");
        jPanel4.add(label_text_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 220, 30));

        btn_expesion.setBackground(new java.awt.Color(109, 160, 255));
        btn_expesion.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btn_expesion.setText("Evaluar expresiòn");
        btn_expesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_expesionMouseClicked(evt);
            }
        });
        btn_expesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_expesionActionPerformed(evt);
            }
        });
        btn_expesion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btn_expesionKeyTyped(evt);
            }
        });
        jPanel4.add(btn_expesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 350, -1, 40));
        jPanel4.add(s_exp, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 210, 20));

        tf_expresion.setBackground(new java.awt.Color(204, 204, 204));
        tf_expresion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tf_expresion.setBorder(null);
        jPanel4.add(tf_expresion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 210, 20));

        titulo_1.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        titulo_1.setForeground(new java.awt.Color(102, 102, 102));
        titulo_1.setText("Calcular con valores asignados");
        titulo_1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                titulo_1ComponentAdded(evt);
            }
        });
        jPanel4.add(titulo_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        text_2.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        text_2.setText("En esta seccion podra ingresar la cantidad de expresiones ");
        jPanel4.add(text_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 636, 35));

        text_3.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        text_3.setText("que desea calcular y asignarles su valor de verdad.");
        jPanel4.add(text_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 513, 35));

        jLabel3.setText("Nota : El valor verdadero de una expresion se va a representar con : v");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel5.setText("El valor falso de una expresion se va a representar con : f");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel6.setText("El numero de expresiones que se pueden ingresar es de 2 a 4.");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));
        jPanel4.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 530, 10));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 942, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 88, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel2);

        jTabbedPane1.addTab("Expreciones logicas con valores asignados", jScrollPane3);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingreso Conjuntos"));

        jLabel31.setText("U");

        CajaTexto19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CajaTexto19ActionPerformed(evt);
            }
        });

        jButton13.setText("Agregar");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel39.setText("U = {");

        CajaTexto20.setEditable(false);
        CajaTexto20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CajaTexto20ActionPerformed(evt);
            }
        });

        jLabel32.setText("}");

        jLabel33.setText("A");

        CajaTexto21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CajaTexto21ActionPerformed(evt);
            }
        });

        jButton14.setText("Agregar");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel34.setText("A = {");

        CajaTexto22.setEditable(false);
        CajaTexto22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CajaTexto22ActionPerformed(evt);
            }
        });

        jLabel35.setText("}");

        jLabel36.setText("B");

        CajaTexto23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CajaTexto23ActionPerformed(evt);
            }
        });

        jButton15.setText("Agregar");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel37.setText("B = {");

        CajaTexto24.setEditable(false);

        jLabel38.setText("}");

        jLabel40.setText("C");

        CajaTexto25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CajaTexto25ActionPerformed(evt);
            }
        });

        jButton16.setText("Agregar");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel41.setText("C = {");

        CajaTexto26.setEditable(false);

        jLabel42.setText("}");

        jLabel43.setText("D");

        CajaTexto27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CajaTexto27ActionPerformed(evt);
            }
        });

        jButton17.setText("Agregar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jLabel44.setText("D = {");

        CajaTexto28.setEditable(false);

        jLabel45.setText("}");

        jButton4.setText("Limpiar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CajaTexto27))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CajaTexto25))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CajaTexto23))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CajaTexto21))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CajaTexto19, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addContainerGap())
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(jButton14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel34)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(CajaTexto22, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel35))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                    .addComponent(jButton13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel39)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(CajaTexto20, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel32)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jButton15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CajaTexto24, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jButton16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel41)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CajaTexto26, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jButton17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel44)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CajaTexto28, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel45))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(CajaTexto19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13)
                    .addComponent(jLabel39)
                    .addComponent(CajaTexto20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(CajaTexto21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14)
                    .addComponent(jLabel34)
                    .addComponent(CajaTexto22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(CajaTexto23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15)
                    .addComponent(jLabel37)
                    .addComponent(CajaTexto24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(CajaTexto25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16)
                    .addComponent(jLabel41)
                    .addComponent(CajaTexto26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(CajaTexto27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17)
                    .addComponent(jLabel44)
                    .addComponent(CajaTexto28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton4))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("ingrese Expreción conjuntos"));

        cajaTexto2.setEditable(false);
        cajaTexto2.setText("Ejemplo: A∩B");
        cajaTexto2.setToolTipText("");
        cajaTexto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTexto2ActionPerformed(evt);
            }
        });

        jButton_ir.setText("Ir");
        jButton_ir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_irActionPerformed(evt);
            }
        });

        jButton_borrar.setText("Borrar");
        jButton_borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_borrarActionPerformed(evt);
            }
        });

        jButton_limpiar.setText("Limpiar");
        jButton_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_limpiarActionPerformed(evt);
            }
        });

        Button_A.setText("A");
        Button_A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_AActionPerformed(evt);
            }
        });

        Button_B.setText("B");
        Button_B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_BActionPerformed(evt);
            }
        });

        Button_C.setText("C");
        Button_C.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_CActionPerformed(evt);
            }
        });

        Button_D.setText("D");
        Button_D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_DActionPerformed(evt);
            }
        });

        Button_interseccion.setText("∩");
        Button_interseccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_interseccionActionPerformed(evt);
            }
        });

        Button_union.setText("∪");
        Button_union.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_unionActionPerformed(evt);
            }
        });

        Button_complemento.setText("°");
        Button_complemento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_complementoActionPerformed(evt);
            }
        });

        Button_diferencia.setText("-");
        Button_diferencia.setToolTipText("");
        Button_diferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_diferenciaActionPerformed(evt);
            }
        });

        Button_diferenciaSimetrica.setText("Δ");
        Button_diferenciaSimetrica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_diferenciaSimetricaActionPerformed(evt);
            }
        });

        Button_ParentesisAbierto1.setText("(");
        Button_ParentesisAbierto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_ParentesisAbierto1ActionPerformed(evt);
            }
        });

        Button_parentesisCerrado1.setText(")");
        Button_parentesisCerrado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_parentesisCerrado1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(cajaTexto2)
                        .addGap(12, 12, 12)
                        .addComponent(jButton_ir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_borrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_limpiar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(Button_A)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_B)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_C)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_D)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_interseccion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_union)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_complemento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_diferencia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_diferenciaSimetrica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_ParentesisAbierto1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Button_parentesisCerrado1)))
                .addGap(0, 41, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cajaTexto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_ir)
                    .addComponent(jButton_borrar)
                    .addComponent(jButton_limpiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Button_A)
                    .addComponent(Button_B)
                    .addComponent(Button_C)
                    .addComponent(Button_interseccion)
                    .addComponent(Button_union)
                    .addComponent(Button_complemento)
                    .addComponent(Button_diferencia)
                    .addComponent(Button_diferenciaSimetrica)
                    .addComponent(Button_ParentesisAbierto1)
                    .addComponent(Button_parentesisCerrado1)
                    .addComponent(Button_D))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        jPanel11.getAccessibleContext().setAccessibleName("Expreción conjuntos");

        jScrollPane4.setViewportView(jPanel6);

        jTabbedPane1.addTab("Conjuntos", jScrollPane4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void titulo_1ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_titulo_1ComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_titulo_1ComponentAdded

    private void btn_expesionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_expesionKeyTyped

    }//GEN-LAST:event_btn_expesionKeyTyped

    private void btn_expesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_expesionActionPerformed
        // codigo al hacer click
    }//GEN-LAST:event_btn_expesionActionPerformed

    private void btn_expesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_expesionMouseClicked
        // codigo al momento de dar click
        ArrayList<Character> EI2 = new ArrayList<Character>();
        ArrayList<Character> EP2 = new ArrayList<Character>();
        ArrayList<String> exp_conver = new ArrayList<String>();
        patioDeManiobras p = new patioDeManiobras();
        String valp = tf_valp.getText();
        String valq = tf_valq.getText();
        String valr = tf_valr.getText();
        String vals = tf_vals.getText();
        String exp = tf_expresion.getText();
        String rta;
        int n_expresiones = Integer.parseInt(tf_box_nexp.getText());
        try {
            valvacio_cantidad_expresiones();
            val_enb();
            valvacio_exp();
            val_exp(exp);
            EI2 = pasar_exp_array(exp);
            EP2 = p.convertirEI_EP2(EI2);
            boolean vp, vq, vr, vs;
            vp = tf_valp.getText().isEmpty();
            vq = tf_valq.getText().isEmpty();
            vr = tf_valr.getText().isEmpty();
            vs = tf_vals.getText().isEmpty();
            if (n_expresiones == 2 && !vp && !vq) {
                if (vef_dosexp(exp)) {
                    tf_expresion.setText("");
                    JOptionPane.showMessageDialog(null, "Caracteres invalidos");
                } else {
                    exp_conver = p.convertir_expresion_dosexp(EP2, valp, valq);
                    rta = "El valor de verdad resultante con la expresion logica indicada es : " + p.calcular_exp(exp_conver);
                    JOptionPane.showMessageDialog(null, rta);
                }
            }
            if (n_expresiones == 3 && !vp && !vq && !vr) {
                if (vef_tresexp(exp)) {
                    tf_expresion.setText("");
                    JOptionPane.showMessageDialog(null, "Caracteres invalidos");
                } else {
                    exp_conver = p.convertir_expresion_tresexp(EP2, valp, valq, valr);
                    rta = "El valor de verdad resultante con la expresion logica indicada es : " + p.calcular_exp(exp_conver);
                    JOptionPane.showMessageDialog(null, rta);
                }
            }
            if (n_expresiones == 4 && !vp && !vq && !vr && !vs) {
                if (vef_cuatroexp(exp)) {
                    tf_expresion.setText("");
                    JOptionPane.showMessageDialog(null, "Caracteres invalidos");
                } else {
                    exp_conver = p.convertir_expresion_cuatroexp(EP2, valp, valq, valr, vals);
                    rta = "El valor de verdad resultante con la expresion logica indicada es : " + p.calcular_exp(exp_conver);
                    JOptionPane.showMessageDialog(null, rta);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_expesionMouseClicked

    private void tf_valsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_valsKeyTyped
        // codigo de validacion de caracteres
        char c = evt.getKeyChar();
        if (c != 'v' && c != 'f') {
            evt.consume();
        }
        if (tf_vals.getText().length() > 0) {
            evt.consume();
        }
    }//GEN-LAST:event_tf_valsKeyTyped

    private void tf_valsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_valsMouseClicked
        // codigo al dar click
    }//GEN-LAST:event_tf_valsMouseClicked

    private void tf_valrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_valrKeyTyped
        // codigo de validacion de caracteres
        char c = evt.getKeyChar();
        if (c != 'v' && c != 'f') {
            evt.consume();
        }
        if (tf_valr.getText().length() > 0) {
            evt.consume();
        }
    }//GEN-LAST:event_tf_valrKeyTyped

    private void tf_valrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_valrMouseClicked
        // codigo al dar click
    }//GEN-LAST:event_tf_valrMouseClicked

    private void tf_valqKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_valqKeyTyped
        // codigo de validacion de caracteres
        char c = evt.getKeyChar();
        if (c != 'v' && c != 'f') {
            evt.consume();
        }
        if (tf_valq.getText().length() > 0) {
            evt.consume();
        }
    }//GEN-LAST:event_tf_valqKeyTyped

    private void tf_valqMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_valqMouseClicked
        // codigo al dar click
    }//GEN-LAST:event_tf_valqMouseClicked

    private void tf_valpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_valpKeyTyped
        // codigo de validacion de caracteres
        char c = evt.getKeyChar();
        if (c != 'v' && c != 'f') {
            evt.consume();
        }
        if (tf_valp.getText().length() > 0) {
            evt.consume();
        }
    }//GEN-LAST:event_tf_valpKeyTyped

    private void tf_valpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_valpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_valpActionPerformed

    private void tf_valpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_valpMouseClicked
        // codigo al dar click
    }//GEN-LAST:event_tf_valpMouseClicked

    private void tf_box_nexpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_box_nexpKeyTyped
        // codigo de validacion de caracteres
        char c = evt.getKeyChar();
        if (c < '2' || c > '4') {
            evt.consume();
        }
        if (tf_box_nexp.getText().length() > 0) {
            evt.consume();
        }
    }//GEN-LAST:event_tf_box_nexpKeyTyped

    private void tf_box_nexpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_box_nexpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_box_nexpActionPerformed

    private void tf_box_nexpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_box_nexpMouseClicked
        // codigo cuando se haga click
    }//GEN-LAST:event_tf_box_nexpMouseClicked

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed
        // TODO add your handling code here:sout
        System.out.println("");
    }//GEN-LAST:event_btn_registrarActionPerformed

    private void btn_registrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_registrarMouseClicked
        // codigo al hacer click en el boton
        try {
            valvacio_cantidad_expresiones();
            int num = Integer.parseInt(tf_box_nexp.getText());
            if (num == 2) {
                vali = num;
                tf_valp.setText("");
                tf_valp.setVisible(true);
                sp.setVisible(true);
                labelp.setVisible(true);
                tf_valq.setText("");
                tf_valq.setVisible(true);
                sq.setVisible(true);
                labelq.setVisible(true);
                sr.setVisible(false);
                labelr.setVisible(false);
                tf_valr.setVisible(false);
                labels.setVisible(false);
                tf_vals.setVisible(false);
                ss.setVisible(false);
                btn_registrar_val.setVisible(true);
                tf_valp.setEnabled(true);
                tf_valq.setEnabled(true);
                tf_expresion.setEnabled(false);
            }
            if (num == 3) {
                vali = num;
                tf_valp.setText("");
                tf_valp.setVisible(true);
                sp.setVisible(true);
                labelp.setVisible(true);
                tf_valq.setText("");
                tf_valq.setVisible(true);
                sq.setVisible(true);
                labelq.setVisible(true);
                tf_valr.setText("");
                tf_valr.setVisible(true);
                sr.setVisible(true);
                labelr.setVisible(true);
                ss.setVisible(false);
                labels.setVisible(false);
                tf_vals.setVisible(false);
                btn_registrar_val.setVisible(true);
                tf_valp.setEnabled(true);
                tf_valq.setEnabled(true);
                tf_valr.setEnabled(true);
                tf_expresion.setEnabled(false);
            }
            if (num == 4) {
                vali = num;
                tf_valp.setText("");
                tf_valp.setVisible(true);
                sp.setVisible(true);
                labelp.setVisible(true);
                tf_valq.setText("");
                tf_valq.setVisible(true);
                sq.setVisible(true);
                labelq.setVisible(true);
                tf_valr.setText("");
                tf_valr.setVisible(true);
                sr.setVisible(true);
                labelr.setVisible(true);
                ss.setVisible(true);
                labels.setVisible(true);
                tf_vals.setText("");
                tf_vals.setVisible(true);
                btn_registrar_val.setVisible(true);
                tf_valp.setEnabled(true);
                tf_valq.setEnabled(true);
                tf_valr.setEnabled(true);
                tf_vals.setEnabled(true);
                tf_expresion.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_registrarMouseClicked

    private void btn_registrar_valActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrar_valActionPerformed

    }//GEN-LAST:event_btn_registrar_valActionPerformed

    private void btn_registrar_valMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_registrar_valMouseClicked
        // codigo al hacer click en el boton
        try {
            valvacio_cantidad_expresiones();
            int cexp = Integer.parseInt(tf_box_nexp.getText());
            if (cexp == 2) {
                if (tf_valp.getText().isEmpty() || tf_valq.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese los valores requeridos");
                } else {
                    JOptionPane.showMessageDialog(null, "Valores validados, ahora ingrese la expresión");
                    label_text_input.setVisible(true);
                    tf_expresion.setVisible(true);
                    btn_expesion.setVisible(true);
                    tf_valp.setEnabled(false);
                    tf_valq.setEnabled(false);
                    tf_expresion.setEnabled(true);
                }
            }
            if (cexp == 3) {
                if (tf_valp.getText().isEmpty() || tf_valq.getText().isEmpty() || tf_valr.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese los valores requeridos");
                } else {
                    JOptionPane.showMessageDialog(null, "Valores validados, ahora ingrese la expresión");
                    label_text_input.setVisible(true);
                    tf_expresion.setVisible(true);
                    btn_expesion.setVisible(true);
                    tf_valp.setEnabled(false);
                    tf_valq.setEnabled(false);
                    tf_valr.setEnabled(false);
                    tf_expresion.setEnabled(true);
                }
            }
            if (cexp == 4) {
                if (tf_valp.getText().isEmpty() || tf_valq.getText().isEmpty() || tf_valr.getText().isEmpty() || tf_vals.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese los valores requeridos");
                } else {
                    JOptionPane.showMessageDialog(null, "Valores validados, ahora ingrese la expresión");
                    label_text_input.setVisible(true);
                    tf_expresion.setVisible(true);
                    btn_expesion.setVisible(true);
                    tf_valp.setEnabled(false);
                    tf_valq.setEnabled(false);
                    tf_valr.setEnabled(false);
                    tf_vals.setEnabled(false);
                    tf_expresion.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_registrar_valMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(salida1.equals("")){

        }else{
            salida1 = salida1.substring(0, salida1.length()-1);
            System.out.println("*"+salida1);
            cajaTexto1.setText(salida1);
            if(EI.get(i)=='('){
                auxP--;
            }else if(EI.get(i)==')'){
                auxP++;
            }
            EI.remove(i);
            if(i==0){
                i=0;
            }else{
                i--;
            }
            System.out.println("Borrar: "+EI);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cajaTexto1.setText("");
        salida1="";
        i=0;
        auxP=-1;
        EI.clear();
        NL.clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NL.clear();
        if(salida1.equals("")){
            JOptionPane.showMessageDialog(null, "Expresión vacia");
        }else if(EI.get(i)!=')'&&!Character.isAlphabetic(EI.get(i))){
            JOptionPane.showMessageDialog(null, "error, la expresión no es valida");
            jTextArea1.setText("");
            cajaTexto1.setText("");
            salida1="";
            i=0;
            EI.clear();
            NL.clear();
        }else{
            imprimir="";
            jTextArea1.setText("");
            cajaTexto1.setText("");
            salida1="";
            i=0;
            nLetras();
            balanceoParentecis();
            auxP=-1;
            patioDeManiobras p = new patioDeManiobras(EI);
            System.out.println("Expresion Infija: "+EI);
            EP = p.convertirEI_EP();
            System.out.println(EP);
            TablasDeVerdad t = new TablasDeVerdad(NL,EP);
            t.rellenarmatrizSolucion(EP);
            muestraExprecion(EI);
            muestraMatriz(t.mSolucion);
            EI.clear();
            NL.clear();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Button_parentesisCerradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_parentesisCerradoActionPerformed
        if(EI.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EI.get(i)!=')' &&!Character.isAlphabetic(EI.get(i))){
            JOptionPane.showMessageDialog(null, "error");
        }else if(auxP>=0){
            EI.add(')');
            salida1=salida1+")";
            cajaTexto1.setText(salida1);
            auxP--;
            i++;
        }else{
            JOptionPane.showMessageDialog(null, "error");
        }
    }//GEN-LAST:event_Button_parentesisCerradoActionPerformed

    private void Button_ParentesisAbiertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_ParentesisAbiertoActionPerformed
        if(EI.isEmpty()){
            EI.add('(');
            salida1=salida1+"(";
            cajaTexto1.setText(salida1);
            auxP++;
        }else if(Character.isAlphabetic(EI.get(i))||EI.lastIndexOf(')')==(i)){
            JOptionPane.showMessageDialog(null, "error");
        }else{
            EI.add('(');
            salida1=salida1+"(";
            cajaTexto1.setText(salida1);
            auxP++;
            i++;
        }
    }//GEN-LAST:event_Button_ParentesisAbiertoActionPerformed

    private void Button_negacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_negacionActionPerformed
        if(EI.isEmpty()){
            EI.add('¬');
            salida1=salida1+"¬";
            cajaTexto1.setText(salida1);
        }else if(EI.get(i)=='¬'||EI.get(i)==')'||Character.isAlphabetic(EI.get(i))){
        JOptionPane.showMessageDialog(null, "error");
        }else{
            EI.add('¬');
            salida1=salida1+"¬";
            cajaTexto1.setText(salida1);
            i++;
        }
    }//GEN-LAST:event_Button_negacionActionPerformed

    private void Button_SiYSoloSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_SiYSoloSiActionPerformed
        if(EI.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EI.get(i)!=')' && !Character.isAlphabetic(EI.get(i))){
        JOptionPane.showMessageDialog(null, "error");
        }else{
            EI.add('↔');
            salida1=salida1+"↔";
            cajaTexto1.setText(salida1);
            i++;
        }
    }//GEN-LAST:event_Button_SiYSoloSiActionPerformed

    private void Button_entoncesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_entoncesActionPerformed
        if(EI.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EI.get(i)!=')' && !Character.isAlphabetic(EI.get(i))){
        JOptionPane.showMessageDialog(null, "error");
        }else{
            EI.add('→');
            salida1=salida1+"→";
            cajaTexto1.setText(salida1);
            i++;
        }
    }//GEN-LAST:event_Button_entoncesActionPerformed

    private void Button_yActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_yActionPerformed
        if(EI.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EI.get(i)!=')' && !Character.isAlphabetic(EI.get(i))){
        JOptionPane.showMessageDialog(null, "error");
        }else{
            EI.add('∧');
            salida1=salida1+"∧";
            cajaTexto1.setText(salida1);
            i++;
        }
    }//GEN-LAST:event_Button_yActionPerformed

    private void Button_oActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_oActionPerformed
        if(EI.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EI.get(i)!=')' && !Character.isAlphabetic(EI.get(i))){
        JOptionPane.showMessageDialog(null, "error");
        }else{
            EI.add('∨');
            salida1=salida1+"∨";
            cajaTexto1.setText(salida1);
            i++;
        }
    }//GEN-LAST:event_Button_oActionPerformed

    private void Button_sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_sActionPerformed
        if(EI.isEmpty()){
            EI.add('s');
            salida1=salida1+"s";
            cajaTexto1.setText(salida1);
        }else if((Character.isAlphabetic(EI.get(i))||EI.get(i)==')')){
        JOptionPane.showMessageDialog(null, "error");
        }else{
            EI.add('s');
            salida1=salida1+"s";
            cajaTexto1.setText(salida1);
            i++;
        }
    }//GEN-LAST:event_Button_sActionPerformed

    private void Button_rActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_rActionPerformed
        if(EI.isEmpty()){
            EI.add('r');
            salida1=salida1+"r";
            cajaTexto1.setText(salida1);
        }else if((Character.isAlphabetic(EI.get(i))||EI.get(i)==')')){
        JOptionPane.showMessageDialog(null, "error");
        }else{
            EI.add('r');
            salida1=salida1+"r";
            cajaTexto1.setText(salida1);
            i++;
        }
    }//GEN-LAST:event_Button_rActionPerformed

    private void Button_qActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_qActionPerformed
        if(EI.isEmpty()){
            EI.add('q');
            salida1=salida1+"q";
            cajaTexto1.setText(salida1);
        }else if((Character.isAlphabetic(EI.get(i))||EI.get(i)==')')){
        JOptionPane.showMessageDialog(null, "error");
        }else{
            EI.add('q');
            salida1=salida1+"q";
            cajaTexto1.setText(salida1);
            i++;
        }
    }//GEN-LAST:event_Button_qActionPerformed

    private void Button_pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_pActionPerformed
        if(EI.isEmpty()){
            EI.add('p');
            salida1=salida1+"p";
            cajaTexto1.setText(salida1);
        }else if((Character.isAlphabetic(EI.get(i))||EI.get(i)==')')){
        JOptionPane.showMessageDialog(null, "error, dato invalido");
        }else {
            EI.add('p');
            salida1=salida1+"p";
            cajaTexto1.setText(salida1);
            i++;
        }
    }//GEN-LAST:event_Button_pActionPerformed

    private void cajaTexto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaTexto1ActionPerformed

    }//GEN-LAST:event_cajaTexto1ActionPerformed

    private void CajaTexto19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CajaTexto19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CajaTexto19ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        if(!(CajaTexto19.getText().trim().matches("[0-9]*"))){
            JOptionPane.showMessageDialog(rootPane, "El valor "+"'"+CajaTexto19.getText()+"'"+" es un valor erroneo, ¡Intentalo nuevamente!");
            CajaTexto19.setText(null);
            CajaTexto19.requestFocus();
            return;
        }
        if(CajaTexto19.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "No has agregado nada");
            return;
        }
        n= Integer.parseInt(CajaTexto19.getText());
        rep = U.contains(n);
        if (!rep){
            U.add(n);
            if (U.indexOf(n)==0){
                salida0 = salida0 + n;
            }else {
                salida0 = salida0 + ", " + n;
            }
            CajaTexto20.setText(salida0);
            CajaTexto19.setText(null);
            CajaTexto19.requestFocus();
        }else {
            JOptionPane.showMessageDialog(null, "El núméro "+n+" está repetido");
            CajaTexto19.setText(null);
            CajaTexto19.requestFocus();
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void CajaTexto20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CajaTexto20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CajaTexto20ActionPerformed

    private void CajaTexto21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CajaTexto21ActionPerformed
        boolean r=true;
        n= Integer.parseInt(CajaTexto21.getText());
        for(int i=0;i<U.size();i++){
            if(n==U.get(i)){
                r=true;
            }
        }
        if(r==false){
            JOptionPane.showMessageDialog(null, "El núméro "+n+" no esta contenido en el conjunto U");
        }
    }//GEN-LAST:event_CajaTexto21ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if(!(CajaTexto19.getText().trim().matches("[0-9]*"))){
            JOptionPane.showMessageDialog(rootPane, "El valor "+"'"+CajaTexto21.getText()+"'"+" es un valor erroneo, ¡Intentalo nuevamente!");
            CajaTexto21.setText(null);
            CajaTexto21.requestFocus();
            return;
        }
        n= Integer.parseInt(CajaTexto21.getText());
        rep = A.contains(n);
        if(!U.contains(n)){
            JOptionPane.showMessageDialog(null, "El núméro "+n+" no esta contenido en el conjunto U");
            CajaTexto21.setText(null);
            CajaTexto21.requestFocus();
            return;
        }
        if (!rep){
            A.add(n);
            Button_A.setEnabled(true);
            if (A.indexOf(n)==0){
                salida2 = salida2 + n;
            }else {
                salida2 = salida2 + ", " + n;
            }
            CajaTexto22.setText(salida2);
            CajaTexto21.setText(null);
            CajaTexto21.requestFocus();
        }else {
            JOptionPane.showMessageDialog(null, "El núméro "+n+" está repetido");
            CajaTexto21.setText(null);
            CajaTexto21.requestFocus();
        }

    }//GEN-LAST:event_jButton14ActionPerformed

    private void CajaTexto22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CajaTexto22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CajaTexto22ActionPerformed

    private void CajaTexto23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CajaTexto23ActionPerformed

    }//GEN-LAST:event_CajaTexto23ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        if(!(CajaTexto23.getText().trim().matches("[0-9]*"))){
            JOptionPane.showMessageDialog(rootPane, "El valor "+"'"+CajaTexto23.getText()+"'"+" es un valor erroneo, ¡Intentalo nuevamente!");
            CajaTexto23.setText(null);
            CajaTexto23.requestFocus();
            return;
        }
        n= Integer.parseInt(CajaTexto23.getText());
        if(!U.contains(n)){
            JOptionPane.showMessageDialog(null, "El núméro "+n+" no esta contenido en el conjunto U");
            CajaTexto23.setText(null);
            CajaTexto23.requestFocus();
            return;
        }
        rep = B.contains(n);
        if (!rep){
            B.add(n);
            Button_B.setEnabled(true);
            if (B.indexOf(n)==0){
                salida3 = salida3 + n;
            }else {
                salida3 = salida3 + ", " + n;
            }
            CajaTexto24.setText(salida3);
            CajaTexto23.setText(null);
            CajaTexto23.requestFocus();
        }else {
            JOptionPane.showMessageDialog(null, "El núméro "+n+" está repetido");
            CajaTexto23.setText(null);
            CajaTexto23.requestFocus();
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void CajaTexto25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CajaTexto25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CajaTexto25ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        if(!(CajaTexto25.getText().trim().matches("[0-9]*"))){
            JOptionPane.showMessageDialog(rootPane, "El valor "+"'"+CajaTexto25.getText()+"'"+" es un valor erroneo, ¡Intentalo nuevamente!");
            CajaTexto25.setText(null);
            CajaTexto25.requestFocus();
            return;
        }
        n= Integer.parseInt(CajaTexto25.getText());
        if(!U.contains(n)){
            JOptionPane.showMessageDialog(null, "El núméro "+n+" no esta contenido en el conjunto U");
            CajaTexto25.setText(null);
            CajaTexto25.requestFocus();
            return;
        }
        rep = C.contains(n);
        if (!rep){
            C.add(n);
            Button_C.setEnabled(true);
            if (C.indexOf(n)==0){
                salida4 = salida4 + n;
            }else {
                salida4 = salida4 + ", " + n;
            }
            CajaTexto26.setText(salida4);
            CajaTexto25.setText(null);
            CajaTexto25.requestFocus();
        }else {
            JOptionPane.showMessageDialog(null, "El núméro "+n+" está repetido");
            CajaTexto25.setText(null);
            CajaTexto25.requestFocus();
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void CajaTexto27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CajaTexto27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CajaTexto27ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        if(!(CajaTexto25.getText().trim().matches("[0-9]*"))){
            JOptionPane.showMessageDialog(rootPane, "El valor "+"'"+CajaTexto19.getText()+"'"+" es un valor erroneo, ¡Intentalo nuevamente!");
            CajaTexto19.setText(null);
            CajaTexto19.requestFocus();
            return;
        }
        n= Integer.parseInt(CajaTexto27.getText());
        if(!U.contains(n)){
            JOptionPane.showMessageDialog(null, "El núméro "+n+" no esta contenido en el conjunto U");
            CajaTexto27.setText(null);
            CajaTexto27.requestFocus();
            return;
        }
        rep = D.contains(n);
        if (!rep){
            D.add(n);
            Button_D.setEnabled(true);
            if (D.indexOf(n)==0){
                salida5 = salida5 + n;
            }else {
                salida5 = salida5 + ", " + n;
            }
            CajaTexto28.setText(salida5);
            CajaTexto27.setText(null);
            CajaTexto27.requestFocus();
        }else {
            JOptionPane.showMessageDialog(null, "El núméro "+n+" está repetido");
            CajaTexto27.setText(null);
            CajaTexto27.requestFocus();
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void cajaTexto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaTexto2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaTexto2ActionPerformed

    private void jButton_irActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_irActionPerformed
        NL.clear();
        tb.vaciarJTextArea();
        solucion.clear();
        if(salida6.equals("")){
            JOptionPane.showMessageDialog(null, "Expresión vacia");
        }else if(EIC.get(l)!=')'&&EIC.get(l)!='°'&&!Character.isLetter(EIC.get(l))){
            JOptionPane.showMessageDialog(null, "error, la expresión no es valida");
            cajaTexto2.setText("");
            salida6="";
            l=0;
            EIC.clear();
            NL.clear();
        }else{
            imprimir="";
            cajaTexto2.setText("");
            salida6="";
            l=0;
            Conjuntos c = new Conjuntos(EIC);
            balanceoParentecisC();
            tb.tituloJTextArea("Expreción en conjuntos:");
            tb.muestraExprecion(EIC);
            EIC=c.convertirExpConjunto();
            balanceoParentecisC();
            auxPC=-1;
            System.out.println(EIC);
            patioDeManiobras p= new patioDeManiobras(EIC);
            EPC=p.convertirEI_EP();
            System.out.println(EPC);
            nLetrasC();
            System.out.println(NL);
            TablasDeVerdad t = new TablasDeVerdad(NL,EPC);
            t.rellenarmatrizSolucion(EPC);
            tb.tituloJTextArea("Expreción logica:");
            tb.muestraExprecion(EIC);
            tb.tituloJTextArea("Expreción logica en posfija:");
            tb.muestraExprecion(EPC);
            tb.tituloJTextArea("TABLA DE VERDAD:");
            tb.muestraMatriz(t.mSolucion,resultado(t.mSolucion,t.mAuxiliar));
            tb.muestraSolucion(solucion);
            tb.setVisible(true);
            EIC.clear();
            NL.clear();
        }
    }//GEN-LAST:event_jButton_irActionPerformed

    private void jButton_borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_borrarActionPerformed
        if(salida6.equals("")){

        }else{
            salida6 = salida6.substring(0, salida6.length()-1);//elimina el ultimo caracter del String
            cajaTexto2.setText(salida6);
            if(EIC.get(l)=='('){
                auxPC--;
            }else if(EIC.get(l)==')'){
                auxPC++;
            }
            EIC.remove(l);
            if(l==0){
                l=0;
            }else{
                l--;
            }
            System.out.println("Borrar: "+EIC);
        }
    }//GEN-LAST:event_jButton_borrarActionPerformed

    private void jButton_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_limpiarActionPerformed
        cajaTexto2.setText("");
        salida6="";
        l=0;
        auxPC=-1;
        EIC.clear();
        NL.clear();
    }//GEN-LAST:event_jButton_limpiarActionPerformed

    private void Button_AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_AActionPerformed
        if(EIC.isEmpty()){
            EIC.add('A');
            salida6=salida6+"A";
            cajaTexto2.setText(salida6);
        }else if(EIC.get(l)=='°'){
            JOptionPane.showMessageDialog(null, "error, dato invalido");
        }else if((EIC.get(l)!='Δ'&&(Character.isAlphabetic(EIC.get(l)))||EIC.get(l)==')')){
            JOptionPane.showMessageDialog(null, "error, dato invalido");
        }else {
            EIC.add('A');
            salida6=salida6+"A";
            cajaTexto2.setText(salida6);
            l++;
        }
    }//GEN-LAST:event_Button_AActionPerformed

    private void Button_BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_BActionPerformed
        if(EIC.isEmpty()){
            EIC.add('B');
            salida6=salida6+"B";
            cajaTexto2.setText(salida6);
        }else if(EIC.get(l)=='°'){
            JOptionPane.showMessageDialog(null, "error, dato invalido");
        }else if((EIC.get(l)!='Δ'&&(Character.isAlphabetic(EIC.get(l)))||EIC.get(l)==')')){
            JOptionPane.showMessageDialog(null, "error, dato invalido");
        }else {
            EIC.add('B');
            salida6=salida6+"B";
            cajaTexto2.setText(salida6);
            l++;
        }
    }//GEN-LAST:event_Button_BActionPerformed

    private void Button_CActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_CActionPerformed
        if(EIC.isEmpty()){
            EIC.add('C');
            salida6=salida6+"C";
            cajaTexto2.setText(salida6);
        }else if(EIC.get(l)=='°'){
            JOptionPane.showMessageDialog(null, "error, dato invalido");
        }else if((EIC.get(l)!='Δ'&&(Character.isAlphabetic(EIC.get(l)))||EIC.get(l)==')')){
            JOptionPane.showMessageDialog(null, "error, dato invalido");
        }else {
            EIC.add('C');
            salida6=salida6+"C";
            cajaTexto2.setText(salida6);
            l++;
        }
    }//GEN-LAST:event_Button_CActionPerformed

    private void Button_DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_DActionPerformed
        if(EIC.isEmpty()){
            EIC.add('D');
            salida6=salida6+"D";
            cajaTexto2.setText(salida6);
        }else if(EIC.get(l)=='°'){
            JOptionPane.showMessageDialog(null, "error, dato invalido");
        }else if((EIC.get(l)!='Δ'&&(Character.isAlphabetic(EIC.get(l)))||EIC.get(l)==')')){
            JOptionPane.showMessageDialog(null, "error, dato invalido");
        }else {
            EIC.add('D');
            salida6=salida6+"D";
            cajaTexto2.setText(salida6);
            l++;
        }
    }//GEN-LAST:event_Button_DActionPerformed

    private void Button_interseccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_interseccionActionPerformed
        if(EIC.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EIC.get(l)!=')'&&EIC.get(l)!='°' && !Character.isAlphabetic(EIC.get(l))){
            JOptionPane.showMessageDialog(null, "error");
        }else{
            EIC.add('∩');
            salida6=salida6+"∩";
            cajaTexto2.setText(salida6);
            l++;
        }
    }//GEN-LAST:event_Button_interseccionActionPerformed

    private void Button_unionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_unionActionPerformed
        if(EIC.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EIC.get(l)!=')'&&EIC.get(l)!='°' && !Character.isAlphabetic(EIC.get(l))){
            JOptionPane.showMessageDialog(null, "error");
        }else{
            EIC.add('∪');
            salida6=salida6+"∪";
            cajaTexto2.setText(salida6);
            l++;
        }
    }//GEN-LAST:event_Button_unionActionPerformed

    private void Button_complementoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_complementoActionPerformed
        if(EIC.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EIC.get(l)!=')' && !Character.isAlphabetic(EIC.get(l))){
            JOptionPane.showMessageDialog(null, "error");
        }else{
            EIC.add('°');
            salida6=salida6+"°";
            cajaTexto2.setText(salida6);
            l++;
        }
    }//GEN-LAST:event_Button_complementoActionPerformed

    private void Button_diferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_diferenciaActionPerformed
        if(EIC.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EIC.get(l)!=')'&&EIC.get(l)!='°' && !Character.isAlphabetic(EIC.get(l))){
            JOptionPane.showMessageDialog(null, "error");
        }else{
            EIC.add('-');
            salida6=salida6+"-";
            cajaTexto2.setText(salida6);
            l++;
        }
    }//GEN-LAST:event_Button_diferenciaActionPerformed

    private void Button_diferenciaSimetricaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_diferenciaSimetricaActionPerformed
        if(EIC.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EIC.get(l)!=')'&&EIC.get(l)!='°' && !Character.isAlphabetic(EIC.get(l))){
            JOptionPane.showMessageDialog(null, "error");
        }else{
            EIC.add('Δ');
            salida6=salida6+"Δ";
            cajaTexto2.setText(salida6);
            l++;
        }
    }//GEN-LAST:event_Button_diferenciaSimetricaActionPerformed

    private void Button_ParentesisAbierto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_ParentesisAbierto1ActionPerformed
        if(EIC.isEmpty()){
            EIC.add('(');
            salida6=salida6+"(";
            cajaTexto2.setText(salida6);
            auxPC++;
        }else if((EIC.get(l)!='Δ'&&Character.isAlphabetic(EIC.get(l)))||EIC.lastIndexOf(')')==(l)||EIC.get(l)=='°'){
            JOptionPane.showMessageDialog(null, "error");
        }else{
            EIC.add('(');
            salida6=salida6+"(";
            cajaTexto2.setText(salida6);
            l++;
            auxPC++;
        }
    }//GEN-LAST:event_Button_ParentesisAbierto1ActionPerformed

    private void Button_parentesisCerrado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_parentesisCerrado1ActionPerformed
        if(EIC.isEmpty()){
            JOptionPane.showMessageDialog(null, "error");
        }else if(EIC.get(l)!=')'&&EIC.get(l)!='°' &&!Character.isAlphabetic(EIC.get(l))){
            JOptionPane.showMessageDialog(null, "error");
        }else if(auxPC>=0){
            EIC.add(')');
            salida6=salida6+")";
            cajaTexto2.setText(salida6);
            l++;
            auxPC--;
        }else{
            JOptionPane.showMessageDialog(null, "error");
        }
    }//GEN-LAST:event_Button_parentesisCerrado1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        cajaTexto2.setText("");
        CajaTexto20.setText("");
        CajaTexto22.setText("");
        CajaTexto24.setText("");
        CajaTexto26.setText("");
        CajaTexto28.setText("");
        salida0="";
        salida2="";
        salida3="";
        salida4="";
        salida5="";
        salida6="";
        l=0;
        auxPC=-1;
        U.clear();
        A.clear();
        B.clear();
        C.clear();
        D.clear();
        EIC.clear();
        NL.clear();
    }//GEN-LAST:event_jButton4ActionPerformed
    public ArrayList<Character> pasar_exp_array(String exp) {
        ArrayList<Character> rta = new ArrayList<Character>();
        for (int k = 0; k < exp.length(); k++) {
            rta.add(exp.charAt(k));
        }
        return rta;
    }

    public boolean vef_dosexp(String exp) {
        ArrayList<String> c = new ArrayList<String>();
        for (int k = 0; k < exp.length(); k++) {
            c.add(String.valueOf(exp.charAt(k)));
        }
        if (c.contains("r") || c.contains("s")) {
            return true;
        }
        return false;
    }

    public boolean vef_tresexp(String exp) {
        ArrayList<String> c = new ArrayList<String>();
        for (int k = 0; k < exp.length(); k++) {
            c.add(String.valueOf(exp.charAt(k)));
        }
        if (!c.contains("r") || c.contains("s")) {
            return true;
        }
        return false;
    }

    public boolean vef_cuatroexp(String exp) {
        ArrayList<String> c = new ArrayList<String>();
        for (int k = 0; k < exp.length(); k++) {
            c.add(String.valueOf(exp.charAt(k)));
        }
        if (!c.contains("r") || !c.contains("s")) {
            return true;
        }
        return false;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_A;
    private javax.swing.JButton Button_B;
    private javax.swing.JButton Button_C;
    private javax.swing.JButton Button_D;
    private javax.swing.JButton Button_ParentesisAbierto;
    private javax.swing.JButton Button_ParentesisAbierto1;
    private javax.swing.JButton Button_SiYSoloSi;
    private javax.swing.JButton Button_complemento;
    private javax.swing.JButton Button_diferencia;
    private javax.swing.JButton Button_diferenciaSimetrica;
    private javax.swing.JButton Button_entonces;
    private javax.swing.JButton Button_interseccion;
    private javax.swing.JButton Button_negacion;
    private javax.swing.JButton Button_o;
    private javax.swing.JButton Button_p;
    private javax.swing.JButton Button_parentesisCerrado;
    private javax.swing.JButton Button_parentesisCerrado1;
    private javax.swing.JButton Button_q;
    private javax.swing.JButton Button_r;
    private javax.swing.JButton Button_s;
    private javax.swing.JButton Button_union;
    private javax.swing.JButton Button_y;
    private javax.swing.JTextField CajaTexto19;
    private javax.swing.JTextField CajaTexto20;
    private javax.swing.JTextField CajaTexto21;
    private javax.swing.JTextField CajaTexto22;
    private javax.swing.JTextField CajaTexto23;
    private javax.swing.JTextField CajaTexto24;
    private javax.swing.JTextField CajaTexto25;
    private javax.swing.JTextField CajaTexto26;
    private javax.swing.JTextField CajaTexto27;
    private javax.swing.JTextField CajaTexto28;
    private javax.swing.JButton btn_expesion;
    private javax.swing.JButton btn_registrar;
    private javax.swing.JButton btn_registrar_val;
    private javax.swing.JTextField cajaTexto1;
    private javax.swing.JTextField cajaTexto2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton_borrar;
    private javax.swing.JButton jButton_ir;
    private javax.swing.JButton jButton_limpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel label_text_input;
    private javax.swing.JLabel labelp;
    private javax.swing.JLabel labelq;
    private javax.swing.JLabel labelr;
    private javax.swing.JLabel labels;
    private javax.swing.JSeparator s_exp;
    private javax.swing.JSeparator sp;
    private javax.swing.JSeparator sq;
    private javax.swing.JSeparator sr;
    private javax.swing.JSeparator ss;
    private javax.swing.JLabel text_2;
    private javax.swing.JLabel text_3;
    private javax.swing.JLabel text_4;
    private javax.swing.JTextField tf_box_nexp;
    private javax.swing.JTextField tf_expresion;
    private javax.swing.JTextField tf_valp;
    private javax.swing.JTextField tf_valq;
    private javax.swing.JTextField tf_valr;
    private javax.swing.JTextField tf_vals;
    private javax.swing.JLabel titulo_1;
    // End of variables declaration//GEN-END:variables

    private void nLetras() {
        HashSet<Character> conjuntoLetras = new HashSet<>();
        for (Character c : EI) {
            if (Character.isLetter(c)) { // Verificar si el caracter es una letra
                conjuntoLetras.add(c); // Agregar el caracter al conjunto
            }
        }
        NL.addAll(conjuntoLetras);
    }
    private void nLetrasC() {
        HashSet<Character> conjuntoLetras = new HashSet<>();
        for (Character c : EIC) {
            if (Character.isLetter(c)) { // Verificar si el caracter es una letra
                conjuntoLetras.add(c); // Agregar el caracter al conjunto
            }
        }
        NL.addAll(conjuntoLetras);
    }
    private void muestraMatriz(Character[][] m) {
        for(int k=0;k<m.length;k++){
            imprimir="";
            for(int j=0;j<m[0].length;j++){
                imprimir += m[k][j]+"\t" ;
            }
            jTextArea1.append(imprimir+"\n");
            
        }
    }
    private void muestraExprecion(ArrayList<Character> a) {
        for(int k=0;k<a.size();k++){
            imprimir += a.get(k) ;
        }
        jTextArea1.append(imprimir+"\n"+"\n");
    }
    private void balanceoParentecis(){
        for(int j =0;j<=auxP;j++){
           EI.add(')');
        }
    }
    private void balanceoParentecisC(){
        for(int j =0;j<=auxPC;j++){
           EIC.add(')');
        }
    }
    private String[][] resultado(Character[][] mSolucion, Character[][] mAuxiliar) {
        String[][] mValores=new String[mSolucion.length][1];
        ArrayList<Integer> posiciones=new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> conjuntos=new ArrayList();
        mValores[0][0]="Valor";
        for(int h=1;h<mSolucion.length;h++){
            mValores[h][0]="   -";
            if(mSolucion[h][mSolucion[0].length-1]=='v'){
                posiciones.add(h);
            }
        }
        for(int j=0;j<mAuxiliar[0].length;j++){
            if(mAuxiliar[0][j]=='p'){
                conjuntos.add(A);
            }else if (mAuxiliar[0][j]=='q'){
                conjuntos.add(B);
            }else if (mAuxiliar[0][j]=='r'){
                conjuntos.add(C);
            }else if (mAuxiliar[0][j]=='s'){
                conjuntos.add(D);
            }
        }
        for(int h=0;h<posiciones.size();h++){
            ArrayList<Boolean> valoresVerdad=new ArrayList<Boolean>();
            for(int j=0;j<mAuxiliar[0].length;j++){
                if(mAuxiliar[posiciones.get(h)][j]=='v'){
                    valoresVerdad.add(true);
                }else if(mAuxiliar[posiciones.get(h)][j]=='f'){
                    valoresVerdad.add(false);
                }
            }
            ArrayList<Integer> valor=new ArrayList<>(U);
            for(int j=0;j<valoresVerdad.size();j++){
                for(int k=0;k<U.size();k++){
                    p=conjuntos.get(j).contains(U.get(k));
                    if(valoresVerdad.get(j)){
                        if(!p){
                            valor.remove(U.get(k));
                        }
                    }else if(!valoresVerdad.get(j)){
                        if(p){
                            valor.remove(U.get(k));
                        }
                    } 
                }
            }
            for(int j=0;j<valor.size();j++){
                solucion.add(valor.get(j));
            }
            mValores[posiciones.get(h)][0]=convertirArray_String(valor);
        }
        return mValores;
    }
    private String convertirArray_String(ArrayList<Integer> exp) {
        String rta="";
        for(int i=0;i<exp.size();i++){
            if (i==0){
                rta=rta+exp.get(i);
            }else{
                rta=rta+","+exp.get(i);
            }
        }
        //rta = rta.substring(0, rta.length()-1);
        return rta;
    }
}
