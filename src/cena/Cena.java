package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

import textura.Textura;

public class Cena implements GLEventListener {

    // Variáveis Gerais
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private int Vidas = 5; // Quantidade de vidas do jogador
    private TextRenderer textRenderer; // Implementar texto no SRU

    // Variáveis Tela
    private String screen = "TelaMenu";

    // Variáveis Bastao
    private float BastaoX, BastaoY, BastaoZ;
    private String DirecaoBastao = "neutro";

    // Variáveis da Bola
    private float BolaX, BolaY, BolaZ;
    private float PosicaoBolaX = 0;
    private float PosicaoBolaY = 0;
    private float VelocidadeBolaX = 0.04f;
    private float VelocidadeBolaY = 0.04f;
    private final float TamanhoBola = 0.06f;
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
    private final float PosicaoObstaculoXMin = -0.55f;
    private final float PosicaoObstaculoXMax = 0.65f;
    private final float PosicaoObstaculoYMin = 0.3f;
    private final float PosicaoObstaculoYMax = 1.1f;

    // Variáveis de Textura
    private Textura textura = null; // Referência para classe Textura
    private int totalTextura = 1; // Quantidade de Texturas a ser carregada

    // Variáveis e Constantes para localizar as imagens
    public static final String TelaMenu = "Imagens/TheSimpsons.jpg";
    public static final String TelaPropositoRegras = "Imagens/BartSimpsonsRegras.jpg";
    public static final String TelaCreditos = "Imagens/HomerSimpsons.jpg";
    public static final String TelaJogo = "Imagens/Inicio.jpg";
    public static final String TelaGameOver = "Imagens/BartGameOver.jpg";
    public static final String TelaJogoVencedor = "Imagens/HomerJogoVencedor.png";
    private int filtro = GL2.GL_LINEAR;
    private int wrap = GL2.GL_REPEAT;
    private int modo = GL2.GL_DECAL;
    private float limite;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1f;
        xMax = yMax = zMax = 1f;

        // Texto
        textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 32));

        limite = 1;
        // Cria uma instancia da Classe Textura indicando a quantidade de texturas
        textura = new Textura(totalTextura);

        gl.glEnable(GL2.GL_LIGHTING);

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        // Obtém o contexto OpenGL
        GL2 gl = drawable.getGL().getGL2();
        ConfiguracaoDisplay(gl);

        // Criando e desenhando componentes na "telaMenu"
        if (Objects.equals(ObterTela(), "TelaMenu")) {
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
            desenhaTextoCentralizado(gl, Renderer.screenHeight - 180, new Color(0.92f, 0.2f, 0.2f),
                    "BEM-VINDO AO PONG, THE SIMPSONS GAME!");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 280, 120, new Color(0.0f, 0.47f, 0.34f),
                    "1. Pressione (ESPAÇO) para Iniciar.");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 370, 120, new Color(0.145f, 0.588f, 0.745f),
                    "2. Pressione (I) para Propósito e Regras do jogo.");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 460, 120, new Color(1.0f, 0.4f, 0.0f),
                    "3. Pressione (C) para Créditos");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 550, 120, new Color(0.9255f, 0.3451f, 0.6157f),
                    "4. Pressione (ESC) para Sair.");

        }

        // Criando e desenhando componentes na "TelaPropósitoRegras"
        if (Objects.equals(ObterTela(), "TelaPropositoRegras")) {
            // Implementacao de textura na "TelaPropósitoRegras"
            textura.setAutomatica(false);
            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);
            textura.gerarTextura(gl, TelaPropositoRegras, 0);

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
            desenhaTextoCentralizado(gl, Renderer.screenHeight - 50, Color.yellow,
                    "PROPÓSITO E REGRAS:");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 120, 10, Color.white,
                    "1. O Propósito do jogo é rebater a bola com seu bastão e marcar pontos, afim de -");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 190, 10, Color.white,
                    "- não resultar com que a bola ultrapasse o bastão e caia pelo fundo da tela.");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 260, 10, Color.white,
                    "2. Utilize as setas do teclado para mover o bastão para (ESQUERDA) ou (DIREITA).");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 330, 10, Color.white,
                    "3. Pressione a tecla (ESPAÇO) para auxílio das regras do jogo durante a execução.");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 400, 10, Color.white,
                    "4. Pressione a tecla (S) para começar o jogo.");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 470, 10, Color.white,
                    "5. Pressione a tecla (P) para pausar o jogo.");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 540, 10, Color.white,
                    "6. Pressione a tecla (R) para reiniciar/parar o jogo.");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 610, 10, Color.white,
                    "7. Pressionea tecla (ESC) para sair do jogo.");

        }

        // Criando e desenhando componentes na "TelaCréditos"
        if (Objects.equals(ObterTela(), "TelaCreditos")) {
            // Implementacao de textura na "TelaCréditos"
            textura.setAutomatica(false);
            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);
            textura.gerarTextura(gl, TelaCreditos, 0);

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

            // Textos da "TelaCreditos"
            desenhaTextoCentralizado(gl, Renderer.screenHeight - 70, Color.yellow,
                    "CRÉDITOS:");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 140, 10, new Color(0.92f, 0.2f, 0.2f),
                    "* UC: Computação Gráfica e Realidade Virtual");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 210, 10, new Color(0.92f, 0.2f, 0.2f),
                    "* Membro / RA:");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 280, 10, new Color(0.92f, 0.2f, 0.2f),
                    "1. Carlos Felipe Borges Mesquita / 12522138056");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 350, 10, new Color(0.92f, 0.2f, 0.2f),
                    "2. Gabriel Luz Carbonaro / 12524120447");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 420, 10, new Color(0.92f, 0.2f, 0.2f),
                    "3. Guilherme Rechiguel da Silva / 12522159171");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 490, 10, new Color(0.92f, 0.2f, 0.2f),
                    "4. Jackson da Costa Souza / 125221102685");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 560, 10, new Color(0.92f, 0.2f, 0.2f),
                    "5. Pedro Henrique Machado / 12522192958");

        }

        // Criando e desenhando componentes na "TelaGameOver"
        if (Objects.equals(ObterTela(), "TelaGameOver")) {
            // Implementacao de textura na "TelaGameOver"
            textura.setAutomatica(false);
            textura.setFiltro(filtro);
            textura.setModo(modo);
            textura.setWrap(wrap);
            textura.gerarTextura(gl, TelaGameOver, 0);

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
            desenhaTextoCentralizado(gl, Renderer.screenHeight - 40, Color.red,
                    "Game Over !!!");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 100, 10, Color.yellow,
                    "Orientações");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 150, 10, new Color(0.2f, 0.3569f, 0.8118f),
                    "1. Pressione (R) para reiniciar o jogo");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 200, 10, new Color(0.2f, 0.3569f, 0.8118f),
                    "2. Pressione (Enter) para entrar no jogo");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 250, 10, new Color(0.2f, 0.3569f, 0.8118f),
                    "3. Pressione (ESC) para sair do jogo");
        }

        // Criando e desenhando componentes na "JogoVencedor"
        if (Objects.equals(ObterTela(), "TelaJogoVencedor")) {
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

            desenhaTextoCentralizado(gl, Renderer.screenHeight - 40, Color.green,
                    "Jogo Vencedor !!!");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 100, 10, Color.yellow,
                    "Orientações");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 150, 10, new Color(0.2f, 0.3569f, 0.8118f),
                    "1. Pressione (R) para reiniciar o jogo");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 200, 10, new Color(0.2f, 0.3569f, 0.8118f),
                    "2. Pressione (Enter) para entrar no jogo");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 250, 10, new Color(0.2f, 0.3569f, 0.8118f),
                    "3. Pressione (ESC) para sair do jogo");
        }

        // Criando e desenhando componentes na "telaJogo"
        if (Objects.equals(ObterTela(), "TelaJogo")) {
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
            desenhaTextoCentralizado(gl, Renderer.screenHeight - 40, Color.yellow,
                    "Pong The Simpsons Game");
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 40, 40, Color.yellow,
                    "Pontuação: " + Pontuacao);
            desenhaTextoEsquerda(gl, Renderer.screenHeight - 80, 40, Color.yellow,
                    "Fase: " + Fase);
            desenhaTextoDireita(gl, Renderer.screenHeight - 40, 40, Color.yellow,
                    "Vidas: " + Vidas);

            // Definindo a cor do Círculo
            gl.glColor3f(1.0f, 1.0f, 0.0f); // Cor Amarello

            // Desenha Mini-Circulos representrando as 5 vidas
            if (ObterVidas() > 0) {
                int i;
                double cX = -0.2, cY = 1.60;
                for (i = 0; i < ObterVidas(); i++) {
                    Circulo circulo = new Circulo(TamanhoBola);
                    circulo.Desenhar02(gl, cX, cY);
                    cX = cX + 0.1;
                    gl.glPopMatrix();
                }
            }

            // Definindo a cor do Círculo
            gl.glColor3f(1.0f, 1.0f, 0.0f); // Cor Amarello

            // Posicao da Bola (Circulo)
            gl.glPushMatrix();
            gl.glTranslatef(PosicaoBolaX, PosicaoBolaY, 0);

            // Desenha o Círculo (Bola)
            Circulo circulo = new Circulo(TamanhoBola);
            circulo.Desenhar01(gl);
            gl.glPopMatrix();

            // Definindo a cor do Bastao
            gl.glColor3f(1.0f, 1.0f, 0.0f); // Cor Amarello

            // Desenhando o Bastao
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
                gl.glVertex2f(PosicaoObstaculoXMin, PosicaoObstaculoYMin);
                gl.glTexCoord2f(limite, 0.0f);
                gl.glVertex2f(PosicaoObstaculoXMax, PosicaoObstaculoYMin);
                gl.glTexCoord2f(limite, limite);
                gl.glVertex2f(PosicaoObstaculoXMax, PosicaoObstaculoYMax);
                gl.glTexCoord2f(0.0f, limite);
                gl.glVertex2f(PosicaoObstaculoXMin, PosicaoObstaculoYMax);
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

                // Inverte a direcao da bola no eixo Y após a colisao com uma pequena mudanca de rota
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
            if (ObterFase() == 2) {
                // Verifica colisao com o lado esquerdo do obstáculo
                if (PosicaoBolaX - TamanhoBola <= PosicaoObstaculoXMin &&
                        PosicaoBolaX + TamanhoBola >= PosicaoObstaculoXMin &&
                        PosicaoBolaY + TamanhoBola >= PosicaoObstaculoYMin &&
                        PosicaoBolaY - TamanhoBola <= PosicaoObstaculoYMax)
                {
                    // Inverte a direcao da bola no eixo X
                    VelocidadeBolaX *= -1;
                }

                // Verifica colisao com o lado direito do obstáculo
                if (PosicaoBolaX + TamanhoBola >= PosicaoObstaculoXMax &&
                        PosicaoBolaX - TamanhoBola <= PosicaoObstaculoXMax &&
                        PosicaoBolaY + TamanhoBola >= PosicaoObstaculoYMin &&
                        PosicaoBolaY - TamanhoBola <= PosicaoObstaculoXMax)
                {
                    // Inverte a direcao da bola no eixo X
                    VelocidadeBolaX *= -1;
                }

                // Verifica colisao com a parte inferior do obstáculo
                if (PosicaoBolaY - TamanhoBola <= PosicaoObstaculoYMin &&
                        PosicaoBolaY + TamanhoBola >= PosicaoObstaculoYMin &&
                        PosicaoBolaX + TamanhoBola >= PosicaoObstaculoXMin &&
                        PosicaoBolaX - TamanhoBola <= PosicaoObstaculoXMax)
                {
                    // Inverte a direcao da bola no eixo Y
                    VelocidadeBolaY *= -1;
                }

                // Verifica colisao com a parte superior do obstáculo
                if (PosicaoBolaY + TamanhoBola >= PosicaoObstaculoYMax &&
                        PosicaoBolaY - TamanhoBola <= PosicaoObstaculoYMax &&
                        PosicaoBolaX + TamanhoBola >= PosicaoObstaculoXMin &&
                        PosicaoBolaX - TamanhoBola <= PosicaoObstaculoXMax)
                {
                    // Inverte a direcao da bola no eixo Y
                    VelocidadeBolaY *= -1;
                }
            } else {

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
    public void ReiniciarOUPararJogo() {
        //Reposicionar a bola
        PosicaoBolaX = 0; // Centralizar o bastao na tela
        PosicaoBolaY = 0; // Posicionar o bastao na parte central da tela
        MovimentoBola = false; // Parar o movimento da bola até que o espaco seja teclado

        // Reposicionar o bastão 
        BastaoX = 0; // Centralizar o bastao na tela
        BastaoY = -1.9f; // Posicionar o bastao na parte inferior da tela

        // Reiniciar componentes do jogo
        this.DefinirVidas(5); // Voltar com 5 vidas iniciais
        this.DefinirPontuacao(0); // Voltar a Pontuacao 0
        this.DefinirFase(1); // Voltar para a Fase 1

        // Retorna a velocidade da Fase 1
        VelocidadeBolaX = 0.04f;
        VelocidadeBolaY = 0.04f;
    }

    // Implementando iluminacao no obstáculo (Quando houver aproximacao da bola
    public void IluminacaoAmbiente(GL2 gl, float ballPositionX, float ballPositionY) {
        float[] luzAmbiente = {0.2f, 0.3569f, 0.8118f, 0.5f}; // Cor Azul
        float[] posicaoLuz = {ballPositionX, ballPositionY, 1.0f, 1.0f};

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
    public void MostrarTelaPropositoRegras() {
        DefinirTela("TelaPropositoRegras");
    }

    public void MostrarTelaCreditos() {
        DefinirTela("TelaCreditos");
    }

    public String ObterTela() {
        return screen;
    }

    public void DefinirTela(String screen) {
        this.screen = screen;
    }

    // Métodos de Pontuacao
    public void AdicionarPontuacao() {
        this.Pontuacao += 20;
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
    private void DiminuirVidas() {
        this.DefinirVidas((this.ObterVidas() - 1));

        this.ObterVidas();
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
        VelocidadeBolaX *= 1.0f;
        VelocidadeBolaY *= 1.0f;
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

    public void desenhaTextoCentralizado(GL2 gl, int yPosicao, Color cor, String frase) {
        // Calcule a largura do texto usando textRenderer
        int textWidth = textRenderer.getBounds(frase).getBounds().width;

        // Calcule a posição x centralizada
        int xPosicao = (Renderer.screenWidth - textWidth) / 2;

        // Chame desenhaTexto com a posição calculada
        desenhaTexto(gl, xPosicao, yPosicao, cor, frase);
    }

    public void desenhaTextoEsquerda(GL2 gl, int yPosicao, int xPosicao, Color cor, String frase) {

        // Chame desenhaTexto com a posição calculada
        desenhaTexto(gl, xPosicao, yPosicao, cor, frase);
    }

    public void desenhaTextoDireita(GL2 gl, int yPosicao, int xOffSet, Color cor, String frase) {
        // Calcule a largura do texto usando textRenderer
        int textWidth = textRenderer.getBounds(frase).getBounds().width;

        // Calcule a posição x para alinhar o texto à direita
        int xPosicao = Renderer.screenWidth - textWidth - xOffSet;

        // Chame desenhaTexto com a posição calculada
        desenhaTexto(gl, xPosicao, yPosicao, cor, frase);
    }

    // Mostrar texto na tela
    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        // Retorna a largura e altura da janela
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
    }
}