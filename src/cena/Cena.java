package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Color;
import java.awt.Font;
import Textura.Textura;

public class Cena implements GLEventListener {

    // Variáveis globais
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private GL2 gl;
    private GLU glu;
    private GLUT glut;
    float luzR = 0.2f, luzG = 0.2f, luzB = 0.2f;
    private int Vidas = 5;
    private TextRenderer textRenderer;

    //V ariáveis de Tela
    private String screen = "TelaInicial";

    // Variáveis de situações do jogo
    private boolean isGameStarted = false;
    private boolean isPlaying;
    private boolean isGamePaused = false;
    private boolean isStalledGame = false;
    private boolean isLostGame = false;
    private boolean isGameWon = false;

    // Variáveis da bola
    private float ballPositionX = 0;
    private float ballPositionY = 0;
    private float ballPositionZ = 0; // Adicionei a posição Z para consistência
    private float ballX, ballY, ballZ;
    private float ballSize;
    private float ballVelocityX, ballVelocityY, ballVelocityZ;

    // Variáveis do bastão
    private float paddleX, paddleY, paddleZ;
    private float paddleWidth, paddleHeight, paddleDepth;
    public float horizontal;

    // Variáveis de placar do jogo
    private int Pontuação;
    private int FaseAtual;

    // Variáveis de rotação
    public float angulo = 0;
    private float incAngulo = 0;

    //Variáveis dos Mini-Teroides
    float anguloMiniTeroides;
    private float[][] lifeCoordinates = {
    {-20, 78}, {-10, 78}, {0, 78}, {10, 78}, {20, 78}};

    //Variáveis do Teróides
    private float teroidePositionX = 0;
    private float teroidePositionY = 0;
    private float teroideRadius = 10;
    
    // Variáveis de iluminação
    public boolean liga = true;      
    public int ponto = 0, modo = GL2.GL_FILL;
    public int tonalização = GL2.GL_SMOOTH; 
   
    // Constantes para identificar as imagens
    private float limite;
    public static final String FACE1 = "Imagens/TheSimpsons.png";
    public static final String FACE2 = "Imagens/Homer.png";
    public static final String FACE3 = "Imagens/SimpsonsCréditos.jpg";
    private int indice;
    
    // Atributos para trabalhar com textura
    private int filtro = GL2.GL_LINEAR;
    private int wrap = GL2.GL_REPEAT;

    // Referência para classe Textura
    private Textura textura = null;
    
    // Quantidade de Texturas a ser carregada
    private int totalTextura = 1;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // Dados iniciais da cena
        glu = new GLU();

        // Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -100;
        xMax = yMax = zMax = 100;

        //Texto
        textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 30));

        // Implementação do jogo - Ângulo
        angulo = 0;
        anguloMiniTeroides = 0;
        
        // Implementação do jogo - Limite
        limite = 1;

        // Implementação do jogo - Andamento, placar, fase e vidas
        isPlaying = true;
        Pontuação = 0;
        FaseAtual = 1;
        Vidas = 5;

        // Implementação do jogo - Bola
        ballX = 40;
        ballY = 0;
        ballZ = 0; 
        ballSize = 3;
        ballVelocityX = (float) 1.7;
        ballVelocityY = (float) -1.7;
        ballVelocityZ = 0;

        // Implementação do jogo - Bastão
        paddleY = -90;
        paddleZ = 0;
        paddleWidth = 35;
        paddleHeight = 5;
        paddleDepth = 5;
        horizontal = 0;

        //Cria uma instancia da Classe Textura indicando a quantidade de texturas
        textura = new Textura(totalTextura);

        //habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLUT glut = new GLUT();

        // Define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        
        // Limpa a janela com a cor especificada
        // Limpa o buffer de profundidade
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        // Comando condicional para desenhar Telas do jogo
        if (screen.equals("TelaInicial")) {
            desenhaTelaInicial(gl);
            
        } else if (screen.equals("Instruções")) {
            desenhaTelaPropósitoRegras(gl);
            
        } else if (screen.equals("Créditos")) {
            desenhaTelaCréditos(gl);
            
        } else if (screen.equals("Jogo Pong")) {
            desenhaJogoPong(gl, glut);
        }

        gl.glFlush();
    }

        // Desenhando e implementando a Tela Inicial
        private void desenhaTelaInicial(GL2 gl) {
        desenhaTexto(gl, 470, Renderer.screenHeight - 80, Color.yellow, "PONG SIMPSONS GAME!");
        desenhaTexto(gl, 415, Renderer.screenHeight - 330, Color.green, "1. Pressione (ESPAÇO) para iniciar.");
        desenhaTexto(gl, 415, Renderer.screenHeight - 430, new Color(0.145f, 0.588f, 0.745f), "2. Pressione (I) para propósito e regras do jogo.");
        desenhaTexto(gl, 415, Renderer.screenHeight - 530, new Color (0.9255f, 0.3451f, 0.6157f),"3. Pressione (C) para créditos.");
        desenhaTexto(gl, 415, Renderer.screenHeight - 630, Color.red, "4. Pressione (ESC) para sair.");
        // Métodos para textura
        // Habilita a transparencia
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
      
        // Não é geração de textura automática
        textura.setAutomatica(false);
                
        // Configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(GL2.GL_MODULATE);
        textura.setWrap(wrap);  
         
        // Desenha um cubo no qual a textura eh aplicada
        //não é geração de textura automática
        textura.setAutomatica(false);

        //configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);  

        //cria a textura indicando o local da imagem e o índice
        textura.gerarTextura(gl, FACE1, 0);
             
        // Criando objeto com a imagem na posição selecionada
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glPushMatrix();
            gl.glScalef(3f,2f,2f);
            gl.glTranslatef(1, 25, 0);
            gl.glBegin(GL2.GL_QUADS);                
            	gl.glTexCoord2f(0.0f, limite);   gl.glVertex2f(-20.0f, 8.0f); 
                gl.glTexCoord2f(limite, limite); gl.glVertex2f(20.0f, 8.0f); 
                gl.glTexCoord2f(limite, 0.0f);   gl.glVertex2f(20.0f, -10.0f);
                gl.glTexCoord2f(0.0f, 0.0f);     gl.glVertex2f(-20.0f, -10.0f);                            
            gl.glEnd();
    
        
      //desabilita a textura indicando o índice
      textura.desabilitarTextura(gl, 0);
    }

        
    
    // Desenhando e implementando a Tela de Instruções
    private void desenhaTelaPropósitoRegras(GL2 gl) {
        desenhaTexto(gl, 490, Renderer.screenHeight - 60, Color.yellow, "PROPÓSITO E REGRAS:");
        desenhaTexto(gl, 40, Renderer.screenHeight - 260, Color.white, "1. O Propósito do jogo é rebater a bola com seu bastão e marcar pontos sem fazer -");
        desenhaTexto(gl, 40, Renderer.screenHeight - 330, Color.white, "- com que a bola ultrapasse o bastão e caia pelo fundo da tela.");
        desenhaTexto(gl, 40, Renderer.screenHeight - 400, Color.white, "2. Utilize as setas do teclado para mover a barra para (ESQUERDA) ou (DIREITA).");
        desenhaTexto(gl, 40, Renderer.screenHeight - 470, Color.white, "3. Pressione a tecla (S) para começar o jogo.");
        desenhaTexto(gl, 40, Renderer.screenHeight - 540, Color.white, "4. Pressione a tecla (P) para pausar o jogo.");
        desenhaTexto(gl, 40, Renderer.screenHeight - 610, Color.white, "5. Pressione a tecla (Q) para parar/stop o jogo.");
        desenhaTexto(gl, 40, Renderer.screenHeight - 680, Color.white, "6. Pressionea tecla (ESC) para sair do jogo.");
        
        // Métodos para textura
        // Habilita a transparencia
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
      
        // Não é geração de textura automática
        textura.setAutomatica(false);
                
        // Configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(GL2.GL_MODULATE);
        textura.setWrap(wrap);  
        
        // Desenha um cubo no qual a textura eh aplicada
        //não é geração de textura automática
        textura.setAutomatica(false);

        //configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);  

        //cria a textura indicando o local da imagem e o índice
        textura.gerarTextura(gl, FACE2, 0);
             
        // Criando objeto com a imagem na posição selecionada
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glPushMatrix();
            gl.glScalef(3f,2.1f,2f);
            gl.glTranslatef(2, 30, 0);
            gl.glBegin(GL2.GL_QUADS);                
            	gl.glTexCoord2f(0.0f, limite);   gl.glVertex2f(-20.0f, 7.0f); 
                gl.glTexCoord2f(limite, limite); gl.glVertex2f(20.0f, 7.0f); 
                gl.glTexCoord2f(limite, 0.0f);   gl.glVertex2f(20.0f, -10.0f);
                gl.glTexCoord2f(0.0f, 0.0f);     gl.glVertex2f(-20.0f, -10.0f);                            
            gl.glEnd();
    
        
      //desabilita a textura indicando o índice
      textura.desabilitarTextura(gl, 0);
       
    }

    // Desenhando e implementando a Tela de Instruções
    private void desenhaTelaCréditos(GL2 gl) {
        desenhaTexto(gl, 590, Renderer.screenHeight - 50, Color.yellow, "Créditos:");
        desenhaTexto(gl, 40, Renderer.screenHeight - 260, Color.white, "* UC: Computação Gráfica e Realidade Virtual");
        desenhaTexto(gl, 40, Renderer.screenHeight - 330, Color.white, "* Componentes / RA:");
        desenhaTexto(gl, 40, Renderer.screenHeight - 400, Color.white, "1. Carlos Felipe Borges Mesquita / 12522138056");
        desenhaTexto(gl, 40, Renderer.screenHeight - 470, Color.white, "2. Gabriel Luz Carbonaro / 12524120447");
        desenhaTexto(gl, 40, Renderer.screenHeight - 540, Color.white, "3. Guilherme Rechinguel da Silva / 12522159171");
        desenhaTexto(gl, 40, Renderer.screenHeight - 610, Color.white, "4. Jackson da Costa Souza / 125221102685");
        desenhaTexto(gl, 40, Renderer.screenHeight - 680, Color.white, "5. Pedro Henrique Machado / 12522192958");
        
        // Métodos para textura
        // Habilita a transparencia
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
      
        // Não é geração de textura automática
        textura.setAutomatica(false);
                
        // Configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(GL2.GL_MODULATE);
        textura.setWrap(wrap);  
        
        // Desenha um cubo no qual a textura eh aplicada
        //não é geração de textura automática
        textura.setAutomatica(false);

        // Configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);  

        // Cria a textura indicando o local da imagem e o índice
        textura.gerarTextura(gl, FACE3, 0);
             
        // Criando objeto com a imagem na posição selecionada
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glPushMatrix();
            gl.glScalef(3f,2f,2f);
            gl.glTranslatef(2, 33, 0);
            gl.glBegin(GL2.GL_QUADS);                
            	gl.glTexCoord2f(0.0f, limite);   gl.glVertex2f(-20.0f, 7.0f); 
                gl.glTexCoord2f(limite, limite); gl.glVertex2f(20.0f, 7.0f); 
                gl.glTexCoord2f(limite, 0.0f);   gl.glVertex2f(20.0f, -10.0f);
                gl.glTexCoord2f(0.0f, 0.0f);     gl.glVertex2f(-20.0f, -10.0f);                            
            gl.glEnd();
    
        
      //Desabilita a textura indicando o índice
      textura.desabilitarTextura(gl, 0);
       
    }
    // Desenhando e implementando a Tela do Jogo Pong
    private void desenhaJogoPong(GL2 gl, GLUT glut) {
        desenhaTexto(gl, 490, 680, Color.yellow, "Pong Simpsons Game");
        desenhaTexto(gl, 10, 680, Color.yellow, "Pontuação: " + Pontuação);
        desenhaTexto(gl, 10, 630, Color.yellow, "Fase: " + FaseAtual);
        desenhaTexto(gl, 1150, 680, Color.yellow, "Vidas: " + Vidas);

        // Comando Condicional para mostrar mensagem na tela das seguintes situações
        if (isGamePaused || isStalledGame || isLostGame || isGameWon) {
            String mensagem = isGamePaused ? "Jogo Pausado" : isStalledGame ? "Jogo Parado" : (isGameWon ? "Jogo Vencedor" : "Game Over");
            Color cor = isGamePaused ? Color.yellow : isStalledGame ? Color.yellow : (isGameWon ? Color.GREEN : Color.RED);
            desenhaTexto(gl, 550, 370, cor, mensagem);
            gl.glFlush();
            return;
        }

        //Desenhando a bola
        gl.glPushMatrix();
        gl.glColor3f(1, 1, 0); // Cor Vermelho
        gl.glTranslatef(ballX, ballY, ballZ);
        glut.glutSolidSphere(ballSize, 50, 50);
        gl.glPopMatrix();

        // Desenhando o bastão
        gl.glPushMatrix();
        gl.glTranslatef(paddleX, paddleY, paddleZ);
        gl.glScalef(paddleWidth, paddleHeight, paddleDepth);
        gl.glColor3f(1, 1, 0); // Cor Amarelo
        glut.glutSolidCube(1);
        gl.glPopMatrix();
        
        // Desenhando Mini-Teroides para representar as vidas
        gl.glColor3f(1, 1, 0);
        for (int i = 0; i < Vidas; i++) {
            gl.glPushMatrix();
            gl.glTranslatef(lifeCoordinates[i][0], lifeCoordinates[i][1], 0);
            gl.glRotatef(anguloMiniTeroides, 0, 1, 0);
            gl.glColor3f(0.9255f, 0.3451f, 0.6157f); // Cor Rosa claro
            glut.glutSolidTorus(2, 3, 3, 8);
            gl.glPopMatrix();
            gl.glFlush();
        }

        // Rotação dos Mini-Teroides
        anguloMiniTeroides++;

        if (!isGameStarted) {
            ballX = 0;
            ballY = 0;
            gl.glPushMatrix();
            gl.glTranslatef(ballX, ballY, ballZ);
            glut.glutSolidSphere(ballSize, 50, 50);
            gl.glPopMatrix();
        } else {
            paddleX = Math.max(Math.min(horizontal, xMax - paddleWidth / 2), xMin + paddleWidth / 2);
            ballX += ballVelocityX;
            ballY += ballVelocityY;

            if (ballX + ballSize >= xMax || ballX - ballSize <= xMin) {
                ballVelocityX *= -1;
            }

            if (ballY + ballSize >= yMax || ballY - ballSize <= yMin) {
                ballVelocityY *= -1;
            }

            if (ballY - ballSize <= paddleY + paddleHeight / 2 && ballY - ballSize >= paddleY - paddleHeight / 2 &&
                    ballX >= paddleX - paddleWidth / 2 && ballX <= paddleX + paddleWidth / 2) {
                Pontuação++;
                ballVelocityY *= -1;
            } else if (ballY - ballSize < yMin) {
                Vidas--;
                ballX = 0;
                ballY = 0;
                ballVelocityX = (float) 1.7;
                ballVelocityY = (float) -1.7;
            }

            if (FaseAtual == 1) {
                if (Pontuação >= 10) {
                    FaseAtual = 2;
                    reiniciarFase();
                }

            } else if (FaseAtual == 2) {
                if (Pontuação >= 10) {
                    desenhaTexto(gl, 250, 300, Color.GREEN, "Jogo Vencido");
                    isGameWon = true;
                    gl.glFlush();
                    return;
                }
            }
        }

        if (Vidas <= 0) {
            desenhaTexto(gl, 250, 300, Color.RED, "Game Over");
            isLostGame = true;
            return;
        }

        //Desenhando um teroide apenas na fase 2
        if(GetFase() == 2){
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 0); // Posição do objeto teroide
        gl.glRotatef(angulo, 0.0f, 1.0f, 1.0f);
        gl.glColor3f(0.9255f, 0.3451f, 0.6157f); // Cor Rosa claro
        glut.glutSolidTorus(8, 20, 8, 15); // Desenha o objeto teroide
        gl.glPopMatrix();
        
        gl.glFlush();
    }
    
    }
    
    // Comando condicional para atualizar velocidade da bola e pontuação na troca de fase
    private void reiniciarFase() {
        ballX = 0;
        ballY =40;

        if (FaseAtual == 1) {
            ballVelocityX = 1.7f;
            ballVelocityY = -1.7f;
        } else if (FaseAtual == 2) {
            ballVelocityX = 2.0f;
            ballVelocityY = -2.0f;
            Pontuação = 0;
        }
    }

    // Métodos do jogo
    public void MostrarPropósitoRegras() {
        SetScreen("Instruções");
    }
    
    public void MostrarCréditos() {
        SetScreen("Créditos");
    }
    
    public void IniciarJogo() {
        isGameStarted = true;
    }

    public void PausarJogo() {
        alternarPausa();
    }

    public void alternarPausa() {
        isGamePaused = !isGamePaused;
    }

    public void JogoParado() {
        isStalledGame = ! isStalledGame;
    }

    public void JogoPerdido() {
        isLostGame = !isLostGame;
    }

    public void JogoVencedor() {
        isGameWon = !isGameWon;
    }

    public int GetFase(){
        return FaseAtual;
    }

    public String GetScreen() {
        return screen;
    }

    public void SetScreen(String screen) {
        this.screen = screen;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
       // Obtenha o contexto gráfico OpenGL
        GL2 gl = drawable.getGL().getGL2();
        
        // Evite a divisão por zero
        if (height == 0) height = 1;
        
        // Calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
        
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        if (width >= height)
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        
    }

    public void update() {
    }
    
    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
    }
}