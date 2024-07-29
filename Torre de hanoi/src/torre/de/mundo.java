
    package torre.de;
    import java.awt.AlphaComposite;
    import java.awt.Color;
    import java.awt.Graphics;
    import java.awt.Graphics2D;
    import java.awt.Image;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseEvent;
    import java.awt.event.MouseListener;
    import java.io.File;
    import java.io.IOException;
    import java.util.List;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import javax.swing.JComboBox;
    import javax.swing.JFileChooser;
    import javax.swing.JLabel;
    import javax.swing.JOptionPane;
    import javax.swing.JPanel;
    import javax.swing.border.LineBorder;

    public class mundo extends JPanel implements ActionListener, MouseListener{
        JLabel fondo;
        JLabel labelImagen2;
        TorresDeHanoi hanoi;
        int aros;
        int numeroDePasos;
        int arosJuego;
        Torre torre1;
        Torre torre2;
        Torre torre3;
        JButton iniciar;
        JComboBox numeroAros;
        private JButton Reinicio;
        JLabel pasosEchos;
        public JLabel pasosCompu;
        JLabel labelPasos;
        JLabel numeroDeAros;
        JLabel nombreTorre1;
        JLabel nombreTorre2;
        JLabel nombreTorre3;
        JButton resolver;
        JButton resolver2;
        boolean seleccionado;
        Anillos aroMover;
        private Image imagenFondo;
        private int arosSeleccionados;
        private AnimadorDeMovimientos animador;
        

        public mundo() throws IOException {      

            ImageIcon fondos = new ImageIcon(getClass().getResource("/fondo.jpg"));
            imagenFondo = fondos.getImage();

            numeroDePasos = 0;
            aros = 3;
            aroMover = null;
            seleccionado = false;
            hanoi = new TorresDeHanoi();
            hanoi.hanoi(aros, 1, 2, 3);
            arosJuego=3;

            this.setBackground(Color.WHITE);
            this.setLayout(null);

            labelPasos = new JLabel("Minimo de Movimientos: " + 0);
            labelPasos.setBounds(250, 330, 180, 10);
            this.add(labelPasos);
            
            pasosCompu = new JLabel("Número de movimientos: " + 0);
            pasosCompu.setBounds(450, 330, 180, 10);
            this.add(pasosCompu);

            pasosEchos = new JLabel("Tu numero de movimientos: " + 0);
            pasosEchos.setBounds(250, 350, 180, 10);
            this.add(pasosEchos);

            torre1 = new Torre();
            torre1.setBounds(0, 10, 220, 300);
            torre1.addMouseListener(this);
            agregarAros(1);
            this.add(torre1);

            nombreTorre1 = new JLabel("TORRE UNO");
            nombreTorre1.setBounds(80, 0, 150, 10);
            this.add(nombreTorre1);

            torre2 = new Torre();
            torre2.setBounds(220, 10, 220, 300);
            torre2.addMouseListener(this);
            this.add(torre2);

            nombreTorre2 = new JLabel("TORRE DOS");
            nombreTorre2.setBounds(300, 0, 150, 10);
            this.add(nombreTorre2);

            torre3 = new Torre();
            torre3.setBounds(440, 10, 220, 300);
            torre3.addMouseListener(this);
            this.add(torre3);

            nombreTorre3 = new JLabel("TORRE TRES");
            nombreTorre3.setBounds(520, 0, 150, 10);
            this.add(nombreTorre3);

            numeroDeAros = new JLabel("Numero de aros:");
            numeroDeAros.setBounds(80, 320, 150, 20);
            this.add(numeroDeAros);

            resolver = new JButton("Solucion");
            resolver.setBounds(500, 340, 90, 30);
            resolver.setBorder(new LineBorder(Color.red,4,true));
            resolver.setBackground(Color.blue);
            resolver.setVisible(false);
            resolver.addActionListener(this);
            this.add(resolver);

            resolver2 = new JButton("Resolver Automáticamente");
            resolver2.setBounds(450, 380, 200, 30);
            resolver2.setBorder(new LineBorder(Color.red,4,true));
            resolver2.setBackground(Color.blue);
            resolver2.addActionListener(this);
            this.add(resolver2);

            iniciar = new JButton("Iniciar");
            iniciar.setBorder(new LineBorder(Color.green,3,true));
            iniciar.setBackground(Color.YELLOW);
            iniciar.setBounds(289, 400, 90, 30);
            iniciar.addActionListener(this);
            this.add(iniciar);

            numeroAros = new JComboBox();
            //llenar el jcomboBox
            for (int i = 1; i <= 7; i++) {
                numeroAros.addItem(i);
            }
            numeroAros.setBounds(80, 340, 90, 30);
            this.add(numeroAros);

            Reinicio = new JButton("Reiniciar");
            Reinicio.setBorder(new LineBorder(Color.red,4,true));
            Reinicio.setBackground(Color.blue);
            Reinicio.setBounds(80, 400, 90, 30);
            Reinicio.addActionListener(this);
            this.add(Reinicio);          
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
 
            if (imagenFondo != null) {
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));

                g2d.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        }

        public void agregarAros(int n) {
        for (int i = 0; i < n; i++) {
            Anillos aro = new Anillos();

            int x = 90;
            int y = 250 - (20 * i);
            int w = 45;
            int h = 20;
            aro.setBounds(x, y, w, h);
            aro.addMouseListener(this);
            torre1.add(aro);
        }

        organizar(n);

        torre1.updateUI();
    }

        public void organizar(int n) {
            if (n >= 0) {
                for (int j = 1; j <= n - 1; j++) {

     
                    JPanel anterior = (JPanel) torre1.getComponent(j - 1);
                    int x = anterior.getX();
                    int y = anterior.getY();
                    int w = anterior.getWidth();
                    int h = anterior.getHeight();

                    JPanel aroA = (JPanel) torre1.getComponent(j);
                    aroA.setBounds(x, y - h, w, h);
                    anterior.setBounds(x - 10, y, w + 20, h);
                    torre1.setComponentZOrder(aroA, j);
                    torre1.setComponentZOrder(anterior, j - 1);

                }
                organizar(n - 1);
            }

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == Reinicio) {

                numeroDePasos = 0;
                arosSeleccionados = 0;
                arosJuego = 0;
                hanoi.setContador(0);
                aroMover = null;
                seleccionado = false;

            
                torre1.removeAll();
                torre2.removeAll();
                torre3.removeAll();

                // Actualizar la interfaz
                torre1.updateUI();
                torre2.updateUI();
                torre3.updateUI();

                // Reiniciar la etiqueta de pasos
                pasosEchos.setText("Tu numero de movimientos: 0");

                // Reiniciar la etiqueta de pasos mínimos
                labelPasos.setText("Minimo de Movimientos: " + hanoi.getContador());

                // Agregar los anillos nuevamente a la torre 1
                agregarAros((int) numeroAros.getSelectedItem());
            }

        if (e.getSource() == resolver2) {
            try {
                // Obtener el número de aros seleccionado por el usuario
                int n = (int) numeroAros.getSelectedItem();
                // Crear una instancia de ResoluciónAutomatica con el número de aros
                ResoluciónAutomatica resoluciónAutomatica = new ResoluciónAutomatica(n);
                // Obtener los movimientos óptimos calculados por la computadora
                List<String> movimientos = resoluciónAutomatica.getMovimientos();
                System.out.println("Movimientos obtenidos:");
                for (String movimiento : movimientos) {
                    System.out.println(movimiento);
                }   
                // Crear un objeto AnimadorDeMovimientos y ejecutar los movimientos visualmente
                AnimadorDeMovimientos animador = new AnimadorDeMovimientos(torre1, torre2, torre3, movimientos);
                animador.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }   
        }

            if (e.getSource() == iniciar) {
                try {
                    numeroDePasos = 0;
                    arosSeleccionados = (int) numeroAros.getSelectedItem();
                    int n = (int) numeroAros.getSelectedItem();
                    arosJuego=n;
                    hanoi.setContador(0);
                    aros = n;
                    hanoi.setContador(0);
                    hanoi.hanoi(n, 1, 2, 3);


                    int pasosMinimos = hanoi.getContador();

                    labelPasos.setText("Minimo de Movimientos: " + pasosMinimos);

                    torre1.removeAll();
                    torre2.removeAll();
                    torre3.removeAll();

                    torre1.updateUI();
                    torre2.updateUI();
                    torre3.updateUI();

                    hanoi.setContador(0);
                    pasosEchos.setText("Tu numero de movimientos: " + 0);
                    agregarAros(n);

                } catch (IOException ex) {
                    Logger.getLogger(hanoi.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            if (e.getSource() == resolver) {
                JFileChooser file = new JFileChooser();
                file.showSaveDialog(this);
                File guarda = file.getSelectedFile();
                if(guarda!=null){
                    try {
                        this.hanoi.guardarSolucion(guarda);
                    } catch (IOException ex) {
                        Logger.getLogger(hanoi.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            if (torre1.getComponentCount() > 0) {
                if (e.getSource() == torre1.getComponent(torre1.getComponentCount() - 1) && seleccionado == false) {
                    aroMover = (Anillos) torre1.getComponent(torre1.getComponentCount() - 1);
                    seleccionado = true;
                    torre1.remove(torre1.getComponentCount() - 1);
                    torre1.updateUI();
                }
            }
            if (torre2.getComponentCount() > 0) {
                if (e.getSource() == torre2.getComponent(torre2.getComponentCount() - 1) && seleccionado == false) {

                    aroMover = (Anillos) torre2.getComponent(torre2.getComponentCount() - 1);
                    seleccionado = true;
                    torre2.remove(torre2.getComponentCount() - 1);
                    torre2.updateUI();
                }
            }

            if (torre3.getComponentCount() > 0) {
                if (e.getSource() == torre3.getComponent(torre3.getComponentCount() - 1) && seleccionado == false) {
                    aroMover = (Anillos) torre3.getComponent(torre3.getComponentCount() - 1);
                    seleccionado = true;
                    torre3.remove(torre3.getComponentCount() - 1);
                    torre3.updateUI();
                }
            }

            if (e.getSource() == torre1 && seleccionado == true) {
                if (aroMover != null) {
                    int x = aroMover.getX();
                    int h = aroMover.getHeight();
                    int w = aroMover.getWidth();
                    if (torre1.getComponentCount() == 0) {

                        aroMover.setBounds(x, 250, w, h);

                        torre1.add(aroMover);
                        torre1.updateUI();
                        numeroDePasos++;

                        pasosEchos.setText("Tu numero de movimientos: " + numeroDePasos);
                        aroMover = null;
                        seleccionado = false;

                    } else {

                        Anillos aroPresente = (Anillos) torre1.getComponent(torre1.getComponentCount() - 1);
                        if (verificar(aroPresente, aroMover)) {

                            aroMover.setBounds(x, 250 - (20 * torre1.getComponentCount() - 1), w, 20);
                            torre1.add(aroMover);
                            torre1.updateUI();
                            numeroDePasos++;
                            pasosEchos.setText("Tu numero de movimientos: " + numeroDePasos);
                            aroMover = null;
                            seleccionado = false;

                        } else {
                            JOptionPane.showMessageDialog(null, "Movimiento inavlido");
                        }
                    }
                }
            }

            if (e.getSource() == torre2 && seleccionado == true) {
                if (aroMover != null) {
                    int x = aroMover.getX();
                    int w = aroMover.getWidth();
                    if (torre2.getComponentCount() == 0) {

                        aroMover.setBounds(x, 250, w, 20);

                        torre2.add(aroMover);
                        torre2.updateUI();

                        numeroDePasos++;

                        pasosEchos.setText("Tu numero de movimientos: " + numeroDePasos);

                        aroMover = null;
                        seleccionado = false;

                    } else {
                        Anillos aroPresente = (Anillos) torre2.getComponent(torre2.getComponentCount() - 1);

                        if (verificar(aroPresente, aroMover)) {

                            aroMover.setBounds(x, 250 - (20 * torre2.getComponentCount() - 1), w, 20);
                            torre2.add(aroMover);
                            torre2.updateUI();

                            numeroDePasos++;

                            pasosEchos.setText("Tu numero de movimientos: " + numeroDePasos);

                            aroMover = null;
                            seleccionado = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Movimiento invalido");
                        }
                    }
                }
            }

            if (e.getSource() == torre3 && seleccionado == true) {
                if (aroMover != null) {

                    int x = aroMover.getX();
                    int h = aroMover.getHeight();
                    int w = aroMover.getWidth();
                    if (torre3.getComponentCount() == 0) {

                        aroMover.setBounds(x, 250, w, h);

                        torre3.add(aroMover);
                        torre3.updateUI();
                        aroMover = null;

                        numeroDePasos++;

                        pasosEchos.setText("Tu numero de movimientos: " + numeroDePasos);

                        seleccionado = false;

                    } else {

                        Anillos aroPresente = (Anillos) torre3.getComponent(torre3.getComponentCount() - 1);
                        if (verificar(aroPresente, aroMover)) {

                            aroMover.setBounds(x, 250 - (20 * torre3.getComponentCount() - 1), w, 20);
                            torre3.add(aroMover);
                            torre3.updateUI();

                            numeroDePasos++;

                            pasosEchos.setText("Tu numero de movimientos: " + numeroDePasos);

                            aroMover = null;
                            seleccionado = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Movimiento inavlido");
                        }
                    }
                }
            }

            if (e.getSource() == torre3) {
                if (hanoi.verificarFinalJuego(aros, torre3.getComponentCount()) == true) {
                    JOptionPane.showMessageDialog(null, "Felicitaciones termino el juego");

                    System.out.println(numeroDePasos);

                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        public boolean verificar(Anillos aroPresente, Anillos aroAMover) {
            int w = aroPresente.getWidth();

            int w2 = aroAMover.getWidth();

            if (w > w2) {
                return true;
            } else {
                return false;
            }
        }

    void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    }
