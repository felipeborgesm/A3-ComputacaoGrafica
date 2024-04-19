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

            case KeyEvent.VK_SPACE:
                System.out.println("Espaco pressionado");
                if (cena.ObterTela() == "TelaMenu" || cena.ObterTela() == "TelaPropósitoRegras"
                        || cena.ObterTela() == "TelaCréditos") {
                    cena.DefinirTela("TelaJogo");
                    break;
                }

            case KeyEvent.VK_I:
                cena.MostrarTelaPropósitoRegras();
                break;

            case KeyEvent.VK_C:
                cena.MostrarTelaCréditos();
                break;

            // Iniciar o jogo pressionando a tecla "S"
            case KeyEvent.VK_S:
                cena.IniciarJogo();
                break;

            // Pausar o jogo pressioanndo a tecla "P"
            case KeyEvent.VK_P:
                cena.PausarJogo();
                break;

            case KeyEvent.VK_R:
                cena.ReiniciarJogo();
                break;

            // Sair do modo de execucao ao pressionar a tecla "ESC"
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
