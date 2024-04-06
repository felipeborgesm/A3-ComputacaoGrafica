package cena;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TelaDeApresentação extends JPanel implements KeyListener {
    
    private static final long serialVersionUID = 1L;
    
    private JFrame frame;
    private GraphicsDevice gd;
    private boolean fullscreen = true;
    private String[] rules = {
    "                                                                                    Seja Bem-Vindo ao Pong Game !!!",
    "",
    "* Propósito:",
    "  O Propósito do jogo é rebater a bola com seu bastão e marcar pontos sem fazer com que ",
    "  a bola ultrapasse o bastão e caia pelo fundo da tela.",
    "",
    "*  Regras:",
    "1. O bastão deve estar posicionado sempre na base da janela de visualização e ser des - ",
    "   locado no eixo x, nunca ultrapassando os limites da janela. O deslocamento do bastão   ",
    "   pode ser feito pelo mouse ou pelo teclado.",
    "",
    "2. A bola deve realizar uma animação contínua de translação e mudar de direção toda vez ",
    "   que quicar em alguma extremidade da janela de visualização (incluindo o bastão).",
    "",
    "3. Desenvolva uma estratégia para que a inclinação não seja sempre a mesma em todas  ",
    "   as partes do bastão – causando sempre um movimento repetido.",
    "",
    "4. O deslocamento da bola deve ter uma velocidade aceitável para a primeira fase.",
    "",
    "5. O jogo deve possuir 2 fases. O usuário deve concluir a primeira fase para avançar ",
    "   para a segunda fase. Para definir o fim da primeira fase, utilize uma pontuação fixa (200)."
    };
    
    public TelaDeApresentação() {
        frame = new JFrame("Pong Game");
        frame.add(this);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.requestFocus();
        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported() && fullscreen) {
            gd.setFullScreenWindow(frame);
        } else {
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Cor de fundo personalizada
        g.setColor(new Color(0, 116, 217)); // Cor Azul Tradicional
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Cor do texto
        g.setColor(Color.WHITE);
        // Fonte personalizada
        Font font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);
        
        int y = 50;
        for (String rule : rules) {
            g.drawString(rule, 50, y);
            y += 30;
            
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    public static void main(String[] args) {
        new TelaDeApresentação();
    }
}