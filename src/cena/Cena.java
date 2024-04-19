package pong.cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import pong.textura.Textura;

public class Cena implements GLEventListener {
    
    // Variáveis Gerais
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private GLU glu;
    private int Vidas = 5; // Quantidade de vidas do jogador
    private TextRenderer textRenderer; // Implementar texto no SRU
    
    // Variáveis Tela
    private String screen = "TelaMenu";
    private float aspect;
    
    // Variáveis Bastão
    private float BastãoX, BastãoY, BastãoZ;
    private String DireçãoBastão = "neutro";
    
    // Variáveis da Bola
    private float BolaX, BolaY, BolaZ;
    private float PosiçãoBolaX = 0;
    private float PosiçãoBolaY = 0;
    private float VelocidadeBolaX = 0.03f;
    private float VelocidadeBolaY = 0.03f;
    private final float TamanhoBola = 0.05f;
    public boolean MovimentoBola = false;
    
    // Variáveis de Situações do Jogo
    private boolean JogoIniciado = false;
    private boolean JogoPausado = false;
    private boolean JogoPerdedor = false;
    private boolean JogoVencedor = false;
    
    // Variável do Placar
    private int Pontuação = 0;
    private int Fase = 1;
    
    // Variáveis do Obstáculo da 2 Fase
    private final float PosiçãoObstáculoXMin = -0.4f;
    private final float PosiçãoObstáculoXMax = 0.65f;
    private final float PosiçãoObstáculoYMin = 0.3f;
    private final float PosiçãoObstáculoYMax = 1.1f;
    
    // Variáveis de Textura
    private Textura textura = null; //Referência para classe Textura
    private int totalTextura = 1; //Quantidade de Texturas a ser carregada
    
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
        //Cria uma instancia da Classe Textura indicando a quantidade de texturas
        textura = new Textura(totalTextura);
        
        gl.glEnable(GL2.GL_LIGHTING);
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {
        // Obtém o contexto OpenGL
        GL2 gl = drawable.getGL().getGL2();
        ConfiguraçãoDisplay(gl);
        
        // Criando e desenhando componentes na "telaMenu"
        if (ObterTela() == "TelaMenu") {
            // Implementação de textura na "TelaMenu"
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
            desenhaTexto(gl, 120, pong.cena.Renderer.screenHeight - 180, new Color(0.92f, 0.2f, 0.2f), "BEM-VINDO ao PONG SIMPSONS GAME!");
            desenhaTexto(gl, 120, pong.cena.Renderer.screenHeight - 280, new Color(0.0f, 0.47f, 0.34f), "1. Pressione (ESPAÇO) para iniciar");
            desenhaTexto(gl, 120, pong.cena.Renderer.screenHeight - 380, new Color(0.145f, 0.588f, 0.745f), "2. Pressione (I) para propósito e regras do jogo");
            desenhaTexto(gl, 120, pong.cena.Renderer.screenHeight - 480, new Color(0.9255f, 0.3451f, 0.6157f), "3. Pressione (C) para Créditos");
            desenhaTexto(gl, 120, pong.cena.Renderer.screenHeight - 580, Color.red, "4. Pressione (ESC) para sair");
        }

        // Criando e desenhando componentes na "TelaPropósitoRegras"
        if (ObterTela() == "TelaPropósitoRegras") {
            // Implementação de textura na "TelaPropósitoRegras"
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
            desenhaTexto(gl, 40, Renderer.screenHeight - 100, Color.white, "1. O Propósito do jogo é rebater a bola com seu bastão e marcar pontos sem -");
            desenhaTexto(gl, 40, Renderer.screenHeight - 170, Color.white, "- fazer com que a bola ultrapasse o bastão e caia pelo fundo da tela.");
            desenhaTexto(gl, 40, Renderer.screenHeight - 240, Color.white, "2. Utilize as setas do teclado para mover a barra para (ESQUERDA) ou (DIREITA).");
            desenhaTexto(gl, 40, Renderer.screenHeight - 310, Color.white, "3. Pressione a tecla (S) para começar o jogo.");
            desenhaTexto(gl, 40, Renderer.screenHeight - 380, Color.white, "4. Pressione a tecla (P) para pausar o jogo.");
            desenhaTexto(gl, 40, Renderer.screenHeight - 450, Color.white, "5. Pressione a tecla (R) para reiniciar o jogo.");
            desenhaTexto(gl, 40, Renderer.screenHeight - 520, Color.white, "5. Pressione a tecla (V) para volar a Tela Menu.");
            desenhaTexto(gl, 40, Renderer.screenHeight - 590, Color.white, "6. Pressionea tecla (ESC) para sair do jogo.");
         
        }
        
        // Criando e desenhando componentes na "TelaCréditos"
        if (ObterTela() == "TelaCréditos") {
            // Implementação de textura na "TelaCréditos"
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
            desenhaTexto(gl, 8, Renderer.screenHeight - 120, new Color(0.92f, 0.2f, 0.2f), "* UC: Computação Gráfica e Realidade Virtual");
            desenhaTexto(gl, 8, Renderer.screenHeight - 190, new Color(0.92f, 0.2f, 0.2f), "* Componentes / RA:");
            desenhaTexto(gl, 8, Renderer.screenHeight - 260, new Color(0.92f, 0.2f, 0.2f), "1. Carlos Felipe Borges  / 12522138056");
            desenhaTexto(gl, 8, Renderer.screenHeight - 330, new Color(0.92f, 0.2f, 0.2f), "2. Gabriel Luz Carbonaro / 12524120447");
            desenhaTexto(gl, 8, Renderer.screenHeight - 400, new Color(0.92f, 0.2f, 0.2f), "3. Guilherme Rechinguel da Silva / 12522159171");
            desenhaTexto(gl, 8, Renderer.screenHeight - 470, new Color(0.92f, 0.2f, 0.2f), "4. Jackson da Costa Souza / 125221102685");
            desenhaTexto(gl, 8, Renderer.screenHeight - 540, new Color(0.92f, 0.2f, 0.2f), "5. Pedro Henrique Machado / 12522192958");
        
    }
        
        // Criando e desenhando componentes na "TelaGameOver"
        if(ObterTela()=="TelaGameOver"){
            // Implementação de textura na "TelaGameOver"
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
            desenhaTexto(gl, 400, Renderer.screenHeight - 90, new Color(0.145f, 0.588f, 0.745f), "1. Pressione (ESC) para sair do jogo");
        }
        
        // Criando e desenhando componentes na "JogoVencedor"
        if(ObterTela()=="TelaJogoVencedor"){
            // Implementação de textura na "TelaJogoVencedor"
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
            desenhaTexto(gl, 500, Renderer.screenHeight - 50, Color.green, "JOGO VENCEDOR !!!");
            desenhaTexto(gl, 400, Renderer.screenHeight - 90, new Color(0.145f, 0.588f, 0.745f), "1. Pressione (ESC) para sair do jogo");
        }
         
        // Criando e desenhando componentes na "telaJogo"
        if(ObterTela()=="TelaJogo"){
            // Implementação de textura na "TelaJogo"
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
            
            // Ativação da iluminação nos objetos
            IluminacaoAmbiente(gl, PosiçãoBolaX, PosiçãoBolaY);
            
        if (JogoPausado) {
            String mensagem = "Jogo Pausado";
            Color cor = Color.yellow;
            desenhaTexto(gl, 525, 590, cor, mensagem);
            gl.glFlush();
            return;
        }  
            
            // Comando Condicional para transição á 2 Fase do jogo
        if (Fase == 1 && Pontuação >= 200) {
            Fase = 2;
        }
            
            // Comando Condicional para "Jogo Vencedor"
        if (Fase == 2 && Pontuação >= 400) {
            JogoVencedor = true;
            DefinirTela("TelaJogoVencedor");
            return;
        }
            
            // Comando Condicional para "Game Over"
        if (this.ObterVidas() <= 0) {
            JogoPerdedor = true;
            DefinirTela("TelaGameOver");
            return; // Encerra a execução do método
        }
            
            // Comando Condicional onde inicializará o jogo todas vez em que o jogador possuir vidas
        if (this.ObterVidas() > 0) {
            // Atualiza a posição da bola e verifica as colisões
            Atualização();
        } else { // encerra o jogo caso as vidas tenham acabado
            JogoPerdedor = true;
            DefinirTela("TelaGameOver");
        }

            // Desenhando Componentes do Jogo na Tela
            desenhaTexto(gl, 490, 680, Color.yellow, "Pong Simpsons Game");
            desenhaTexto(gl, 10, 680, Color.yellow, "Pontuação: " + Pontuação);
            desenhaTexto(gl, 10, 630, Color.yellow, "Fase: " + Fase);
            desenhaTexto(gl, 1150, 680, Color.yellow, "Vidas: " + Vidas);

            //Inclusão de representação das vidas por bolinhas
        if(ObterVidas() > 0){
            int i;
            double cX = -0.2, cY = 1.60;
            for (i=0; i< ObterVidas(); i++){
                Circulo circulo = new Circulo(TamanhoBola);
                circulo.draw2(gl, cX, cY);
                cX = cX + 0.1;
                gl.glPopMatrix();
            }
        }
           
            gl.glPushMatrix();
            gl.glTranslatef(PosiçãoBolaX, PosiçãoBolaY, 0);

            // Desenha o círculo
            Circulo circulo = new Circulo(TamanhoBola);
            circulo.draw(gl);
            gl.glPopMatrix();
            
            // Desenhando o bastão 
             gl.glPushMatrix();
             gl.glTranslatef(BastãoX, -1.8f, 0); 
             gl.glBegin(GL2.GL_QUADS);
             gl.glVertex2f(-0.2f, -0.1f);
             gl.glVertex2f(0.2f, -0.1f);
             gl.glVertex2f(0.2f, 0);
             gl.glVertex2f(-0.2f, 0);
             gl.glEnd();
             gl.glPopMatrix();

            
        if(ObterFase() == 2){
               
            // Desenha o retangulo
            gl.glPushMatrix();
            gl.glColor3f(1, 1, 1);
            gl.glBegin(GL2.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);    gl.glVertex2f(PosiçãoObstáculoXMin, PosiçãoObstáculoYMin);
            gl.glTexCoord2f(limite, 0.0f);  gl.glVertex2f(PosiçãoObstáculoXMax, PosiçãoObstáculoYMin);
            gl.glTexCoord2f(limite, limite);    gl.glVertex2f(PosiçãoObstáculoXMax, PosiçãoObstáculoYMax);
            gl.glTexCoord2f(0.0f, limite);  gl.glVertex2f(PosiçãoObstáculoXMin, PosiçãoObstáculoYMax);
            gl.glEnd();
            gl.glPopMatrix();    
            }
        }
    }

    public void Atualização() {
        
        // Verifica se o jogo está iniciado para permitir o movimento da bola
        if (JogoIniciado && MovimentoBola) {

            // Atualizar a posição da bola
            PosiçãoBolaX += VelocidadeBolaX;
            PosiçãoBolaY += VelocidadeBolaY;
            
            // Desenhando colisões com o objeto da 2 Fase
            // Verifica colisões com as bordas da tela
        if (PosiçãoBolaX + TamanhoBola > xMax || PosiçãoBolaX - TamanhoBola < xMin) {
                
            // Inverte a direção da bola no eixo X
            VelocidadeBolaX *= -1;  
        }

        if (PosiçãoBolaY + TamanhoBola > yMax || PosiçãoBolaY - TamanhoBola < yMin) {
                
            // Inverte a direção da bola no eixo Y
            VelocidadeBolaY *= -1;   
        }

        // Verifica colisão com o retângulo 
        if (PosiçãoBolaX - TamanhoBola <= BastãoX + 0.2f && PosiçãoBolaX + TamanhoBola >= BastãoX - 0.2f && PosiçãoBolaY - TamanhoBola <= -1.8f) {
                
        // Verifica se o sentido do movimento da barra e com a bola
        if (DireçãoBastão.equals("direita") && VelocidadeBolaX < 0) {
                
            // Inverte a direção da bola no eixo X com uma pequena mudança de rota
                VelocidadeBolaX = -1 * VelocidadeBolaX + (float) Math.random() * 0.003f;
            } else if (DireçãoBastão.equals("esquerda") && VelocidadeBolaX > 0) {
                VelocidadeBolaX = -1 * VelocidadeBolaX + (float) Math.random() * 0.001f;
            }
                
                // Inverte a direção da bola no eixo Y após a colisão com uma pequena mudança de rota
                VelocidadeBolaY = -1 * VelocidadeBolaY + (float) Math.random() * 0.003f;
                
                // Marcar pontuação
                AdicionarPontuação();
                System.out.println(ObterPontuação());
                //mudar de nível = aumentar velocidade
            if (Fase == 1) {
            if (Pontuação >= 200) {
                Fase = 2;   
                }
            } 
                
                // Ajusta a posição da bola para que não extrapole o SRU
            if (PosiçãoBolaY - TamanhoBola < yMin) {
                PosiçãoBolaY = yMin + TamanhoBola;
                }
            }

            // Verificações de colisão com o obstáculo
            // Verifica colisão com o lado esquerdo do obstáculo
            if (ObterFase() == 2 &&
                PosiçãoBolaX - TamanhoBola <= PosiçãoObstáculoXMin &&
                PosiçãoBolaX + TamanhoBola >= PosiçãoObstáculoXMin &&
                PosiçãoBolaY + TamanhoBola >= PosiçãoObstáculoYMin &&
                PosiçãoBolaY - TamanhoBola <= PosiçãoObstáculoYMax) {
                
                // Inverte a direção da bola no eixo X
                VelocidadeBolaX *= -1;
            }

            // Verifica colisão com o lado direito do obstáculo
            if (ObterFase() == 2 &&
                PosiçãoBolaX + TamanhoBola >= PosiçãoObstáculoXMax &&
                PosiçãoBolaX - TamanhoBola <= PosiçãoObstáculoXMax &&
                PosiçãoBolaY + TamanhoBola >= PosiçãoObstáculoYMin &&
                PosiçãoBolaY - TamanhoBola <= PosiçãoObstáculoXMax) {
                
                // Inverte a direção da bola no eixo X
                VelocidadeBolaX *= -1;  
            }

            // Verifica colisão com a parte inferior do obstáculo
            if (ObterFase() == 2 &&
                PosiçãoBolaY - TamanhoBola <= PosiçãoObstáculoYMin &&
                PosiçãoBolaY + TamanhoBola >= PosiçãoObstáculoYMin &&
                PosiçãoBolaX + TamanhoBola >= PosiçãoObstáculoXMin &&
                PosiçãoBolaX - TamanhoBola <= PosiçãoObstáculoXMax) {
                
                // Inverte a direção da bola no eixo Y
                VelocidadeBolaY *= -1;
            }

            // Verifica colisão com a parte superior do obstáculo
            if (ObterFase() == 2 &&
                PosiçãoBolaY + TamanhoBola >= PosiçãoObstáculoYMax &&
                PosiçãoBolaY - TamanhoBola <= PosiçãoObstáculoYMax &&
                PosiçãoBolaX + TamanhoBola >= PosiçãoObstáculoXMin &&
                PosiçãoBolaX - TamanhoBola <= PosiçãoObstáculoXMax) {
                
                // Inverte a direção da bola no eixo Y
                VelocidadeBolaY *= -1;
            }

            else {

                // Caso a bola não toque no retângulo , retorna ao centro da tela
                if (PosiçãoBolaY - TamanhoBola < yMin) {
                    PosiçãoBolaX = 0;
                    PosiçãoBolaY = 0;
                    MovimentoBola = false; // Para o movimento da bola até que o espaço seja teclado
                    
                    // Eliminar Vidas Restantes
                    this.DiminuirVidas();
                    // Mostrar na tela quantas vidas restantes possui automaticamente
                    System.out.println(this.ObterVidas()); 
                }
            }
        }
    }
    
    // Implementações para reiniciar o Jogo - (Pressionar tecla "R")
    public void ReiniciarJogo() {
            PosiçãoBolaX = 0;
            PosiçãoBolaY = 0;
            MovimentoBola = false; // Para o movimento da bola até que o espaço seja teclado
                    
            this.DefinirVidas(5);
            this.DefinirPontuação(0);
            this.DefinirFase(1);
            
            
            // Retorna a velocidade original
            VelocidadeBolaX = 0.03f;
            VelocidadeBolaY = 0.03f;
    }
    
    // Implementando iluminação
    public void IluminacaoAmbiente(GL2 gl, float ballPositionX, float ballPositionY) {
        float luzAmbiente[] = { 1.0f, 1.0f, 0.0f, 0.5f }; //cor
        float posicaoLuz[] = {ballPositionX, ballPositionY, 1.0f, 1.0f}; 

        // define parametros de luz de número 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        // habilita o uso da iluminação na cena
        gl.glEnable(GL2.GL_LIGHTING);
        
        // habilita a luz de número 0
        gl.glEnable(GL2.GL_LIGHT0);
        
        // Especifica o Modelo de tonalização a ser utilizado
        // GL_FLAT -> modelo de tonalização flat
        // GL_SMOOTH -> modelo de tonalização GOURAUD (default)
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
    
    public String ObterTela(){
        return screen;
    }
    
    public void DefinirTela(String screen){
        this.screen = screen;
    }
    
    // Métodos de Pontuação
    public int AdicionarPontuação(){
        return this.Pontuação += 20;
    }
    
     public int ObterPontuação(){
        return Pontuação;
        
    }
    
    public void DefinirPontuação(int pontuação) {
        this.Pontuação = pontuação;
    }
    
    // Métodos de Vidas
    public int ObterVidas() {
        return Vidas;
    }

    public void DefinirVidas(int lives) {
        this.Vidas = lives;
    }
    
    // Função para retirar as vidas do jogador
    private int DiminuirVidas() {
        this.DefinirVidas((this.ObterVidas()-1));
        
        return this.ObterVidas();
    }
    
    // Métodos de Fase
    public int ObterFase(){
        return Fase;
    }

    public void DefinirFase(int level){
        this.Fase = level;
    }
    
    // Método de velocidade da bola
    public void AumentarVelocidadeFase2(){
        VelocidadeBolaX *= 1.8f;
        VelocidadeBolaY *= 1.8f;
        DefinirFase(2);
    }
    
    // Métodos do Bastão
    public float ObterBastãoX() {
        return BastãoX;
    }

    public void DefinirBastãoX(float axisX) {
        this.BastãoX = axisX;
    }

    public float ObterBastãoY() {
        return BastãoX;
    }

    public void DefinirBastãoY(float EixoBastãoX) {
        this.BastãoX = EixoBastãoX;
    }
    
    // Direção do Bastão
    public void DefinirDireçãoBastão(String direction){
        this.DireçãoBastão = direction;
        System.out.println(this.DireçãoBastão);
    }
    
    public String ObterDireçãoBastão(){
        return DireçãoBastão;
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
    public void ConfiguraçãoDisplay(GL2 gl){
        gl.glClearColor(0,0,0,0);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }
    
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Obtenha o contexto gráfico OpenGL
        GL2 gl = drawable.getGL().getGL2();

        // Evite a divisão por zero
        if (height == 0) height = 1;

        // Calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;

        // Atualiza as coordenadas do SRU (Sistema de Referência do Universo) para se adaptarem ao novo tamanho da janela
        xMin = yMin = zMin = -aspect;
        xMax = yMax = zMax = aspect;

        // Atualiza o viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);

        // Ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); // Carregue a matriz identidade

        // Projeção ortogonal
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
    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){         
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        
        //Retorna a largura e altura da janela
        textRenderer.beginRendering(pong.cena.Renderer.screenWidth, pong.cena.Renderer.screenHeight);       
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
    }
}