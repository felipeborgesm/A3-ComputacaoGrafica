package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import textura.Textura;
import com.jogamp.opengl.util.gl2.GLUT;

public class Cena implements GLEventListener {

    // Variáveis Gerais
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private GLU glu;
    private int Vidas = 5; // Quantidade de vidas do jogador
    private TextRenderer textRenderer; // Implementar texto no SRU

    // Variáveis Tela
    private String screen = "TelaMenu";
    private float aspect;

    // Variáveis Bastao
    private float BastaoX, BastaoY, BastaoZ;
    private String DirecaoBastao = "neutro";

    // Variáveis da Bola
    private float BolaX, BolaY, BolaZ;
    private float PosicaoBolaX = 0;
    private float PosicaoBolaY = 0;
    private float VelocidadeBolaX = 0.06f;
    private float VelocidadeBolaY = 0.06f;
    private final float TamanhoBola = 0.05f;
    public boolean MovimentoBola = false;

    // Variáveis de Situacoes do Jogo
    private boolean JogoIniciado = false;
    private boolean JogoPausado = false;
    private boolean JogoPerdedor = false;
    private boolean JogoVencedor = false;

    // Variável do Placar
    private int Pontuacao = 0;
    private int Fase = 1;

    // Variáveis do Obstáculo da 2 Fase
    private final float PosicaoObstáculoXMin = -0.4f;
    private final float PosicaoObstáculoXMax = 0.65f;
    private final float PosicaoObstáculoYMin = 0.3f;
    private final float PosicaoObstáculoYMax = 1.1f;

    // Variáveis de Textura
    private Textura textura = null; // Referência para classe Textura
    private int totalTextura = 1; // Quantidade de Texturas a ser carregada

    // Variáveis e Constantes para localizar as imagens
    public static final String TelaMenu = "Imagens/TheSimpsons.jpg";
    public static final String TelaPropósitoRegras = "Imagens/BartSimpsons.jpg";
    public static final String TelaCréditos = "Imagens/HomerSimpsons.jpg";
    public static final String TelaJogo = "Imagens/Inicio.jpg";
    public static final String TelaJogoPerdedor = "Imagens/HomerJogoPerdedor.jpg";
    public static final String TelaJogoVencedor = "Imagens/HomerJogoVencedor.jpg";
    private int filtro = GL2.GL_LINEAR;
    private int wrap = GL2.GL_REPEAT;
    private int modo = GL2.GL_DECAL;
    private float limite;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        // Dados iniciais da cena
        glu = new GLU();

        // Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1f;
        xMax = yMax = zMax = 1f;

        // Texto
        textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 32));

        limite = 1;
        // Cria uma instancia da Classe Textura indicando a quantidade de texturas
        textura = new Textura(totalTextura);

        gl.glEnable(GL2.GL_LIGHTING);

        // PROFESSOR
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        // Obtém o contexto OpenGL
        GL2 gl = drawable.getGL().getGL2();
        ConfiguracaoDisplay(gl);

        // PROFESSOR
        GLUT glut = new GLUT();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        // Criando e desenhando componentes na "telaMenu"
        if (ObterTela() == "TelaMenu") {
            // Implementacao de textura na "TelaMenu"
            textura.setAutomatica(false);
            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);
            textura.gerarTextura(gl, TelaMenu, 0);

            gl.glPushMatrix();
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(1, 1, 1);
            gl.glTexCoord2f(0, limite);
            gl.glVertex2f(-2, 2);
            gl.glTexCoord2f(limite, limite);
            gl.glVertex2f(2, 2);
            gl.glTexCoord2f(limite, 0);
            gl.glVertex2f(2, -2);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(-2, -2);
            gl.glEnd();
            gl.glPopMatrix();
            textura.desabilitarTextura(gl, 0);

            // Textos da "TelaMenu"
            desenhaTexto(gl, 120, cena.Renderer.screenHeight - 180, new Color(0.92f, 0.2f, 0.2f),
                    "BEM-VINDO ao PONG SIMPSONS GAME!");
            desenhaTexto(gl, 120, cena.Renderer.screenHeight - 280, new Color(0.0f, 0.47f, 0.34f),
                    "1. Pressione (ESPAcO) para iniciar");
            desenhaTexto(gl, 120, cena.Renderer.screenHeight - 380, new Color(0.145f, 0.588f, 0.745f),
                    "2. Pressione (I) para propósito e regras do jogo");
            desenhaTexto(gl, 120, cena.Renderer.screenHeight - 480, new Color(0.9255f, 0.3451f, 0.6157f),
                    "3. Pressione (C) para Créditos");
            desenhaTexto(gl, 120, cena.Renderer.screenHeight - 580, Color.red, "4. Pressione (ESC) para sair");
        }

        // Criando e desenhando componentes na "TelaPropósitoRegras"
        if (ObterTela() == "TelaPropósitoRegras") {
            // Implementacao de textura na "TelaPropósitoRegras"
            textura.setAutomatica(false);
            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);
            textura.gerarTextura(gl, TelaPropósitoRegras, 0);

            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(1, 1, 1);
            gl.glTexCoord2f(0, limite);
            gl.glVertex2f(-2, 2);
            gl.glTexCoord2f(limite, limite);
            gl.glVertex2f(2, 2);
            gl.glTexCoord2f(limite, 0);
            gl.glVertex2f(2, -2);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(-2, -2);
            gl.glEnd();
            textura.desabilitarTextura(gl, 0);

            // Textos da "TelaPropósitoRegras"
            desenhaTexto(gl, 490, Renderer.screenHeight - 50, Color.yellow, "PROPÓSITO E REGRAS:");
            desenhaTexto(gl, 40, Renderer.screenHeight - 100, Color.white,
                    "1. O Propósito do jogo é rebater a bola com seu bastao e marcar pontos sem -");
            desenhaTexto(gl, 40, Renderer.screenHeight - 170, Color.white,
                    "- fazer com que a bola ultrapasse o bastao e caia pelo fundo da tela.");
            desenhaTexto(gl, 40, Renderer.screenHeight - 240, Color.white,
                    "2. Utilize as setas do teclado para mover a barra para (ESQUERDA) ou (DIREITA).");
            desenhaTexto(gl, 40, Renderer.screenHeight - 310, Color.white,
                    "3. Pressione a tecla (S) para comecar o jogo.");
            desenhaTexto(gl, 40, Renderer.screenHeight - 380, Color.white,
                    "4. Pressione a tecla (P) para pausar o jogo.");
            desenhaTexto(gl, 40, Renderer.screenHeight - 450, Color.white,
                    "5. Pressione a tecla (R) para reiniciar o jogo.");
            desenhaTexto(gl, 40, Renderer.screenHeight - 520, Color.white,
                    "5. Pressione a tecla (V) para volar a Tela Menu.");
            desenhaTexto(gl, 40, Renderer.screenHeight - 590, Color.white,
                    "6. Pressionea tecla (ESC) para sair do jogo.");

        }

        // Criando e desenhando componentes na "TelaCréditos"
        if (ObterTela() == "TelaCréditos") {
            // Implementacao de textura na "TelaCréditos"
            textura.setAutomatica(false);
            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);
            textura.gerarTextura(gl, TelaCréditos, 0);

            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(1, 1, 1);
            gl.glTexCoord2f(0, limite);
            gl.glVertex2f(-2, 2);
            gl.glTexCoord2f(limite, limite);
            gl.glVertex2f(2, 2);
            gl.glTexCoord2f(limite, 0);
            gl.glVertex2f(2, -2);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(-2, -2);
            gl.glEnd();
            textura.desabilitarTextura(gl, 0);

            // Textos da "TelaCréditos"
            desenhaTexto(gl, 300, Renderer.screenHeight - 70, Color.yellow, "Créditos:");
            desenhaTexto(gl, 8, Renderer.screenHeight - 120, new Color(0.92f, 0.2f, 0.2f),
                    "* UC: Computacao Gráfica e Realidade Virtual");
            desenhaTexto(gl, 8, Renderer.screenHeight - 190, new Color(0.92f, 0.2f, 0.2f), "* Componentes / RA:");
            desenhaTexto(gl, 8, Renderer.screenHeight - 260, new Color(0.92f, 0.2f, 0.2f),
                    "1. Carlos Felipe Borges  / 12522138056");
            desenhaTexto(gl, 8, Renderer.screenHeight - 330, new Color(0.92f, 0.2f, 0.2f),
                    "2. Gabriel Luz Carbonaro / 12524120447");
            desenhaTexto(gl, 8, Renderer.screenHeight - 400, new Color(0.92f, 0.2f, 0.2f),
                    "3. Guilherme Rechinguel da Silva / 12522159171");
            desenhaTexto(gl, 8, Renderer.screenHeight - 470, new Color(0.92f, 0.2f, 0.2f),
                    "4. Jackson da Costa Souza / 125221102685");
            desenhaTexto(gl, 8, Renderer.screenHeight - 540, new Color(0.92f, 0.2f, 0.2f),
                    "5. Pedro Henrique Machado / 12522192958");

        }

        // Criando e desenhando componentes na "TelaGameOver"
        if (ObterTela() == "TelaGameOver") {
            // Implementacao de textura na "TelaGameOver"
            textura.setAutomatica(false);
            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);
            textura.gerarTextura(gl, TelaJogoPerdedor, 0);

            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(1, 1, 1);
            gl.glTexCoord2f(0, limite);
            gl.glVertex2f(-2, 2);
            gl.glTexCoord2f(limite, limite);
            gl.glVertex2f(2, 2);
            gl.glTexCoord2f(limite, 0);
            gl.glVertex2f(2, -2);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(-2, -2);
            gl.glEnd();
            textura.desabilitarTextura(gl, 0);

            // Textos da "TelaGameOver"
            desenhaTexto(gl, 560, Renderer.screenHeight - 50, Color.red, "Game Over !!!");
            desenhaTexto(gl, 400, Renderer.screenHeight - 90, new Color(0.145f, 0.588f, 0.745f),
                    "1. Pressione (ESC) para sair do jogo");
        }

        // Criando e desenhando componentes na "JogoVencedor"
        if (ObterTela() == "TelaJogoVencedor") {
            // Implementacao de textura na "TelaJogoVencedor"
            textura.setAutomatica(false);
            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);
            textura.gerarTextura(gl, TelaJogoVencedor, 0);

            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(1, 1, 1);
            gl.glTexCoord2f(0, limite);
            gl.glVertex2f(-2, 2);
            gl.glTexCoord2f(limite, limite);
            gl.glVertex2f(2, 2);
            gl.glTexCoord2f(limite, 0);
            gl.glVertex2f(2, -2);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(-2, -2);
            gl.glEnd();
            textura.desabilitarTextura(gl, 0);

            // Textos da "TelaJogoVencedor"
            desenhaTexto(gl, 500, (Renderer.screenHeight/2), Color.green, "JOGO VENCEDOR !!!");
            desenhaTexto(gl, 400, (Renderer.screenHeight/2), new Color(0.145f, 0.588f, 0.745f),
                    "1. Pressione (ESC) para sair do jogo");
        }

        // Criando e desenhando componentes na "telaJogo"
        if (ObterTela() == "TelaJogo") {
            // Implementacao de textura na "TelaJogo"
            textura.setAutomatica(false);
            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);
            textura.gerarTextura(gl, TelaJogo, 0);

            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(1, 1, 1);
            gl.glTexCoord2f(0, limite);
            gl.glVertex2f(-2, 2);
            gl.glTexCoord2f(limite, limite);
            gl.glVertex2f(2, 2);
            gl.glTexCoord2f(limite, 0);
            gl.glVertex2f(2, -2);
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(-2, -2);
            gl.glEnd();
            textura.desabilitarTextura(gl, 0);

            // Ativacao da iluminacao nos objetos
            IluminacaoAmbiente(gl, PosicaoBolaX, PosicaoBolaY);

            if (JogoPausado) {
                String mensagem = "Jogo Pausado";
                Color cor = Color.yellow;
                desenhaTexto(gl, 525, 590, cor, mensagem);
                gl.glFlush();
                return;
            }

            // Comando Condicional para transicao á 2 Fase do jogo
            if (Fase == 1 && Pontuacao >= 200) {
                Fase = 2;
            }

            // Comando Condicional para "Jogo Vencedor"
            if (Fase == 2 && Pontuacao >= 400) {
                JogoVencedor = true;
                DefinirTela("TelaJogoVencedor");
                return;
            }

            // Comando Condicional para "Game Over"
            if (this.ObterVidas() <= 0) {
                JogoPerdedor = true;
                DefinirTela("TelaGameOver");
                return; // Encerra a execucao do método
            }

            // Comando Condicional onde inicializará o jogo todas vez em que o jogador
            // possuir vidas
            if (this.ObterVidas() > 0) {
                // Atualiza a posicao da bola e verifica as colisoes
                Atualizacao();
            } else { // encerra o jogo caso as vidas tenham acabado
                JogoPerdedor = true;
                DefinirTela("TelaGameOver");
            }

            // Desenhando Componentes do Jogo na Tela
            desenhaTexto(gl, (Renderer.screenWidth / 2), Renderer.screenHeight - 50, Color.yellow,
                    "Pong Simpsons Game");
            desenhaTexto(gl, 10, Renderer.screenHeight - 50, Color.yellow, "Pontuacao: " + Pontuacao);
            desenhaTexto(gl, 10, Renderer.screenHeight - 100, Color.yellow, "Fase: " + Fase);
            desenhaTexto(gl, Renderer.screenWidth - 130, Renderer.screenHeight - 50, Color.yellow, "Vidas: " + Vidas);

            // Inclusao de representacao das vidas por bolinhas
            if (ObterVidas() > 0) {
                int i;
                double cX = -0.2, cY = 1.60;
                for (i = 0; i < ObterVidas(); i++) {
                    Circulo circulo = new Circulo(TamanhoBola);
                    circulo.draw2(gl, cX, cY);
                    cX = cX + 0.1;
                    gl.glPopMatrix();
                }
            }

            gl.glPushMatrix();
            gl.glTranslatef(PosicaoBolaX, PosicaoBolaY, 0);

            // Desenha o círculo
//            Circulo circulo = new Circulo(TamanhoBola);
//            circulo.draw(gl);
//            gl.glPopMatrix();

            //PROFESSOR
            //Desenhando a bola
            gl.glPushMatrix();
            gl.glColor3f(1, 1, 0); // Cor Vermelho
            gl.glTranslatef(BolaX, BolaY, BolaZ);
            glut.glutSolidSphere(TamanhoBola, 50, 50);
            gl.glPopMatrix();

            // Desenhando o bastao
            gl.glPushMatrix();
            gl.glTranslatef(BastaoX, -1.8f, 0);
            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(-0.2f, -0.1f);
            gl.glVertex2f(0.2f, -0.1f);
            gl.glVertex2f(0.2f, 0);
            gl.glVertex2f(-0.2f, 0);
            gl.glEnd();
            gl.glPopMatrix();

            if (ObterFase() == 2) {
                // Desenha o retangulo
                gl.glPushMatrix();
                gl.glColor3f(1, 1, 1);
                gl.glBegin(GL2.GL_QUADS);
                gl.glTexCoord2f(0.0f, 0.0f);
                gl.glVertex2f(PosicaoObstáculoXMin, PosicaoObstáculoYMin);
                gl.glTexCoord2f(limite, 0.0f);
                gl.glVertex2f(PosicaoObstáculoXMax, PosicaoObstáculoYMin);
                gl.glTexCoord2f(limite, limite);
                gl.glVertex2f(PosicaoObstáculoXMax, PosicaoObstáculoYMax);
                gl.glTexCoord2f(0.0f, limite);
                gl.glVertex2f(PosicaoObstáculoXMin, PosicaoObstáculoYMax);
                gl.glEnd();
                gl.glPopMatrix();
            }
        }
    }

    public void Atualizacao() {

        // Verifica se o jogo está iniciado para permitir o movimento da bola
        if (JogoIniciado && MovimentoBola) {

            // Atualizar a posicao da bola
            PosicaoBolaX += VelocidadeBolaX;
            PosicaoBolaY += VelocidadeBolaY;

            // Desenhando colisoes com o objeto da 2 Fase
            // Verifica colisoes com as bordas da tela
            if (PosicaoBolaX + TamanhoBola > xMax || PosicaoBolaX - TamanhoBola < xMin) {

                // Inverte a direcao da bola no eixo X
                VelocidadeBolaX *= -1;
            }

            if (PosicaoBolaY + TamanhoBola > yMax || PosicaoBolaY - TamanhoBola < yMin) {

                // Inverte a direcao da bola no eixo Y
                VelocidadeBolaY *= -1;
            }

            // Verifica colisao com o retângulo
            if (PosicaoBolaX - TamanhoBola <= BastaoX + 0.2f && PosicaoBolaX + TamanhoBola >= BastaoX - 0.2f
                    && PosicaoBolaY - TamanhoBola <= -1.8f) {

                // Verifica se o sentido do movimento da barra e com a bola
                if (DirecaoBastao.equals("direita") && VelocidadeBolaX < 0) {

                    // Inverte a direcao da bola no eixo X com uma pequena mudanca de rota
                    VelocidadeBolaX = -1 * VelocidadeBolaX + (float) Math.random() * 0.003f;
                } else if (DirecaoBastao.equals("esquerda") && VelocidadeBolaX > 0) {
                    VelocidadeBolaX = -1 * VelocidadeBolaX + (float) Math.random() * 0.001f;
                }

                // Inverte a direcao da bola no eixo Y após a colisao com uma pequena mudanca de
                // rota
                VelocidadeBolaY = -1 * VelocidadeBolaY + (float) Math.random() * 0.003f;

                // Marcar pontuacao
                AdicionarPontuacao();
                System.out.println(ObterPontuacao());
                // mudar de nível = aumentar velocidade
                if (Fase == 1) {
                    if (Pontuacao >= 200) {
                        Fase = 2;
                    }
                }

                // Ajusta a posicao da bola para que nao extrapole o SRU
                if (PosicaoBolaY - TamanhoBola < yMin) {
                    PosicaoBolaY = yMin + TamanhoBola;
                }
            }

            // Verificacoes de colisao com o obstáculo
            // Verifica colisao com o lado esquerdo do obstáculo
            if (ObterFase() == 2 &&
                    PosicaoBolaX - TamanhoBola <= PosicaoObstáculoXMin &&
                    PosicaoBolaX + TamanhoBola >= PosicaoObstáculoXMin &&
                    PosicaoBolaY + TamanhoBola >= PosicaoObstáculoYMin &&
                    PosicaoBolaY - TamanhoBola <= PosicaoObstáculoYMax) {

                // Inverte a direcao da bola no eixo X
                VelocidadeBolaX *= -1;
            }

            // Verifica colisao com o lado direito do obstáculo
            if (ObterFase() == 2 &&
                    PosicaoBolaX + TamanhoBola >= PosicaoObstáculoXMax &&
                    PosicaoBolaX - TamanhoBola <= PosicaoObstáculoXMax &&
                    PosicaoBolaY + TamanhoBola >= PosicaoObstáculoYMin &&
                    PosicaoBolaY - TamanhoBola <= PosicaoObstáculoXMax) {

                // Inverte a direcao da bola no eixo X
                VelocidadeBolaX *= -1;
            }

            // Verifica colisao com a parte inferior do obstáculo
            if (ObterFase() == 2 &&
                    PosicaoBolaY - TamanhoBola <= PosicaoObstáculoYMin &&
                    PosicaoBolaY + TamanhoBola >= PosicaoObstáculoYMin &&
                    PosicaoBolaX + TamanhoBola >= PosicaoObstáculoXMin &&
                    PosicaoBolaX - TamanhoBola <= PosicaoObstáculoXMax) {

                // Inverte a direcao da bola no eixo Y
                VelocidadeBolaY *= -1;
            }

            // Verifica colisao com a parte superior do obstáculo
            if (ObterFase() == 2 &&
                    PosicaoBolaY + TamanhoBola >= PosicaoObstáculoYMax &&
                    PosicaoBolaY - TamanhoBola <= PosicaoObstáculoYMax &&
                    PosicaoBolaX + TamanhoBola >= PosicaoObstáculoXMin &&
                    PosicaoBolaX - TamanhoBola <= PosicaoObstáculoXMax) {

                // Inverte a direcao da bola no eixo Y
                VelocidadeBolaY *= -1;
            }

            else {

                // Caso a bola nao toque no retângulo , retorna ao centro da tela
                if (PosicaoBolaY - TamanhoBola < yMin) {
                    PosicaoBolaX = 0;
                    PosicaoBolaY = 0;
                    MovimentoBola = false; // Para o movimento da bola até que o espaco seja teclado

                    // Eliminar Vidas Restantes
                    this.DiminuirVidas();
                    // Mostrar na tela quantas vidas restantes possui automaticamente
                    System.out.println(this.ObterVidas());
                }
            }
        }
    }

    // Implementacoes para reiniciar o Jogo - (Pressionar tecla "R")
    public void ReiniciarJogo() {
        PosicaoBolaX = 0;
        PosicaoBolaY = 0;
        MovimentoBola = false; // Para o movimento da bola até que o espaco seja teclado

        this.DefinirVidas(5);
        this.DefinirPontuacao(0);
        this.DefinirFase(1);

        // Retorna a velocidade original
        VelocidadeBolaX = 0.03f;
        VelocidadeBolaY = 0.03f;
    }

    // Implementando iluminacao
    public void IluminacaoAmbiente(GL2 gl, float ballPositionX, float ballPositionY) {
        float luzAmbiente[] = { 1.0f, 1.0f, 0.0f, 0.5f }; // cor
        float posicaoLuz[] = { ballPositionX, ballPositionY, 1.0f, 1.0f };

        // define parametros de luz de número 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        // habilita o uso da iluminacao na cena
        gl.glEnable(GL2.GL_LIGHTING);

        // habilita a luz de número 0
        gl.glEnable(GL2.GL_LIGHT0);

        // Especifica o Modelo de tonalizacao a ser utilizado
        // GL_FLAT -> modelo de tonalizacao flat
        // GL_SMOOTH -> modelo de tonalizacao GOURAUD (default)
        gl.glShadeModel(GL2.GL_SMOOTH);
    }

    // Métodos do Jogo
    // Métodos de Tela
    public void MostrarTelaPropósitoRegras() {
        DefinirTela("TelaPropósitoRegras");
    }

    public void MostrarTelaCréditos() {
        DefinirTela("TelaCréditos");
    }

    public String ObterTela() {
        return screen;
    }

    public void DefinirTela(String screen) {
        this.screen = screen;
    }

    // Métodos de Pontuacao
    public int AdicionarPontuacao() {
        return this.Pontuacao += 20;
    }

    public int ObterPontuacao() {
        return Pontuacao;

    }

    public void DefinirPontuacao(int pontuacao) {
        this.Pontuacao = pontuacao;
    }

    // Métodos de Vidas
    public int ObterVidas() {
        return Vidas;
    }

    public void DefinirVidas(int lives) {
        this.Vidas = lives;
    }

    // Funcao para retirar as vidas do jogador
    private int DiminuirVidas() {
        this.DefinirVidas((this.ObterVidas() - 1));

        return this.ObterVidas();
    }

    // Métodos de Fase
    public int ObterFase() {
        return Fase;
    }

    public void DefinirFase(int level) {
        this.Fase = level;
    }

    // Método de velocidade da bola
    public void AumentarVelocidadeFase2() {
        VelocidadeBolaX *= 1.8f;
        VelocidadeBolaY *= 1.8f;
        DefinirFase(2);
    }

    // Métodos do Bastao
    public float ObterBastaoX() {
        return BastaoX;
    }

    public void DefinirBastaoX(float axisX) {
        this.BastaoX = axisX;
    }

    public float ObterBastaoY() {
        return BastaoX;
    }

    public void DefinirBastaoY(float EixoBastaoX) {
        this.BastaoX = EixoBastaoX;
    }

    // Direcao do Bastao
    public void DefinirDirecaoBastao(String direction) {
        this.DirecaoBastao = direction;
        System.out.println(this.DirecaoBastao);
    }

    public String ObterDirecaoBastao() {
        return DirecaoBastao;
    }

    // Método Iniciar o Jogo
    public void IniciarJogo() {
        // Permitir o movimento da bola
        MovimentoBola = true;

        // Indicar que o jogo está iniciado
        JogoIniciado = true;
    }

    // Método de Pausar o jogo
    public void PausarJogo() {
        AlternarPausa();
    }

    public void AlternarPausa() {
        JogoPausado = !JogoPausado;
    }

    // Método do Display
    public void ConfiguracaoDisplay(GL2 gl) {
        gl.glClearColor(0, 0, 0, 0);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Obtenha o contexto gráfico OpenGL
        GL2 gl = drawable.getGL().getGL2();

        // PROFESSOR
        gl.glOrtho(-100, 100, -100, 100, -100, 100);


        // Evite a divisao por zero
        if (height == 0)
            height = 1;

        // Calcula a proporcao da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;

        // Atualiza as coordenadas do SRU (Sistema de Referência do Universo) para se
        // adaptarem ao novo tamanho da janela
        xMin = yMin = zMin = -aspect;
        xMax = yMax = zMax = aspect;

        // Atualiza o viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);

        // Ativa a matriz de projecao
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); // Carregue a matriz identidade

        // Projecao ortogonal
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);

        // Ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        System.out.println("Reshape: " + width + ", " + height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    // Mostrar texto na tela
    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        // Retorna a largura e altura da janela
        textRenderer.beginRendering(cena.Renderer.screenWidth, cena.Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
    }
}