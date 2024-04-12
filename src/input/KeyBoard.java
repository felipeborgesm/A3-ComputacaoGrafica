package input;

import cena.Cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;

public class KeyBoard implements KeyListener {
    private Cena cena;

    public KeyBoard(Cena cena) {
        this.cena = cena;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            
            // Usuário é direcionado á tela do jogo ao pressionar a tecla "SPACE"
            case KeyEvent.VK_SPACE:
                System.out.println("Espaço pressionado");
                if (cena.GetScreen().equals("TelaInicial") || cena.GetScreen().equals("Instruções")) {
                    cena.SetScreen("Jogo Pong");
                break;
            }
            
            // Mostrar instruções ao pressionar a tecla "I"
            case KeyEvent.VK_I: 
                cena.MostrarPropósitoRegras();
                break;
                
            // Mostrar instruções ao pressionar a tecla "I"
            case KeyEvent.VK_C: 
                cena.MostrarCréditos();
                break;
            
            // Mover o bastão para a esquerda pressionando a tecla "LEFT"
            case KeyEvent.VK_LEFT:
                cena.horizontal -= 7;
                break;
                
            // Mover o bastão para a direita pressionando a tecla "RIGHT" 
            case KeyEvent.VK_RIGHT:
                cena.horizontal += 7;
                break;
                
            // Iniciar o jogo pressionando a tecla "S" 
            case KeyEvent.VK_S:
                cena.IniciarJogo();
                break;
                
            // Pausar o jogo pressioanndo a tecla "P" 
            case KeyEvent.VK_P:
                cena.PausarJogo();
                break;
                
            // Parar o jogo pressionando a tecla "Q"
            case KeyEvent.VK_Q:
                cena.JogoParado();
                break;
                
            // Ao perder todas as 5 vidas, será exibido a mensagem de "Game Over" 
            // Não é necessário apertar a tecla "Q"
            case KeyEvent.VK_O:
                cena.JogoPerdido();
                break;
               
            // Ao atingir a pontuação necessária na fase 1 e 2, será exibido a mensagem de "Jogo Vencido"
            // Não é necessário apertar a tecla "W"
            case KeyEvent.VK_W:
                cena.JogoVencedor();
                break;
                               
            // Sair do modo de execução ao pressionar a tecla "ESC"   
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
                
            
        }
      
    
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // Lógica para lidar com a tecla solta, se necessário
    }
}