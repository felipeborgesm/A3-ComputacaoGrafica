package input;

import cena.Cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyBoard implements KeyListener {
    private Cena cena;

    public KeyBoard(Cena cena) {
        this.cena = cena;
    }

    @Override
    public void keyPressed(KeyEvent e) { 
    switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT: // Move o paddle para a esquerda
            cena.horizontal -= 7;
            break;
        case KeyEvent.VK_RIGHT: // Move o paddle para a direita
            cena.horizontal += 7;
            break;
        case KeyEvent.VK_S: // Inicia o jogo ao pressionar a tecla "S"
            cena.IniciarJogo();
            break;
        case KeyEvent.VK_P: // Pausa o jogo ao pressionar a tecla "P"
            cena.PausarJogo();
            break;
        case KeyEvent.VK_Q: // Parar/Interromper o jogo ao pressionar a tecla "Q"
            cena.PararJogo();
            break;
        case KeyEvent.VK_W:
            cena.JogoVencedor();
            break;
        case KeyEvent.VK_ESCAPE: //Sair do modo execução ao pressionar a tecla "ESC"
            System.exit(0);
            break;
        case KeyEvent.VK_R:
            cena.reiniciarFase();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Lógica para lidar com a tecla solta, se necessário
    }
}