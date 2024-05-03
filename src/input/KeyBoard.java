package input;

import cena.Cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyBoard implements KeyListener {
    private Cena cena;
    private final static int left = 149;
    private final static int right = 151;
    private final static int Pause = 80;
    private static boolean JogoPausado = false;
    private final static int stop = 84;
    private final static int Instrucoes = 73;

    public KeyBoard(Cena cena) {
        this.cena = cena;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        System.out.println("Key pressed: " + e.getKeyCode());

        switch (e.getKeyCode()) {

            // Mover o bastao para a direita
            case right:
                if (!JogoPausado) {
                    System.out.println("Direita no eixo X");
                    if (cena.ObterBastaoX() < 1.6f) {
                        cena.DefinirBastaoX(cena.ObterBastaoX() + 0.15f);
                        System.out.println(cena.ObterBastaoX());
                    } else
                        cena.DefinirBastaoX(cena.ObterBastaoX() + 0.0f);
                    cena.DefinirDirecaoBastao("direita");
                }
                break;

            // Mover o bastao para a esquerda pressionando a tecla "RIGHT"
            case left:
                if (!JogoPausado) {
                    System.out.println("Esquerda no eixo X");
                    if (cena.ObterBastaoX() > -1.6f) {
                        cena.DefinirBastaoX(cena.ObterBastaoX() - 0.15f);
                        System.out.println(cena.ObterBastaoX());
                    } else
                        cena.DefinirBastaoX(cena.ObterBastaoX() + 0.0f);
                    cena.DefinirDirecaoBastao("esquerda");
                }
                break;

            // Pressionar a tecla "ESPACO" para a transição das seguintes telas
            case KeyEvent.VK_SPACE:
                System.out.println("Espaco pressionado");
                if (cena.ObterTela() == "TelaMenu" || cena.ObterTela() == "TelaPropositoRegras") {
                    cena.DefinirTela("TelaJogo");
                    break;
                }
                
             case KeyEvent.VK_ENTER:
                System.out.println("Enter pressionado");
                if (cena.ObterTela() == "TelaGameOver" || cena.ObterTela() == "TelaJogoVencedor") {
                    cena.DefinirTela("TelaJogo");
                    break;
                }

            // pressionar a tecla "I" para exibir a tela Proposito e Regras 
            case KeyEvent.VK_I:
                cena.MostrarTelaPropositoRegras();
                break;

            // pressionar a tecla "C" para exibir a tela Creditos
            case KeyEvent.VK_C:
                cena.MostrarTelaCreditos();
                break;

            // Pressionar a tecla "S" para iniciar o jogo 
            case KeyEvent.VK_S:
                cena.IniciarJogo();
                break;

            // Pressionar a tecla "P" para pausar o jogo 
            case KeyEvent.VK_P:
                cena.PausarJogo();
                break;

            // Pressionar a tecla "R" para reiniciar o jogo
            case KeyEvent.VK_R:
                cena.ReiniciarOUPararJogo();
                break;

            // Pressionar a tecla "ESC" para sair do modo de execucao do jogo
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
