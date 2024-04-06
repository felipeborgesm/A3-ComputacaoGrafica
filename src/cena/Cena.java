package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.Color;
import java.awt.Font;

public class Cena implements GLEventListener {
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private GLU glu;
    private TextRenderer textRenderer;
    public float horizontal;
    private float paddleX, paddleY, paddleZ; // Posição do bastão
    private float paddleWidth, paddleHeight, paddleDepth; // Dimensões do bastão
    private float ballX, ballY, ballZ; // Posição da bola
    private float ballRadius; // Raio da bola
    private float ballSpeedX, ballSpeedY, ballSpeedZ; // Velocidade da bola
    private boolean isPlaying; // Indica se o jogo está em andamento
    private int pontuacao; // Contador da pontuação
    private int faseAtual; // Contador da fase atual do jogador
    private int vidas; // Contador de vidas
    private boolean isGameStarted = false; // Variável para controlar se o jogo foi iniciado
    private boolean isGamePaused = false; // Variável para controlar se o jogo foi pausado
    private boolean isGameInterrupted = false; // Variável para controlar se o jogo está interrompido (Stop/Game Ove)
    private boolean isGameWon = false; // Variável para controlar se o jogo foi vencido
    float angulo; // Indica Rotação ; ADICIONADO
    float anguloMiniCubos; // Indica rotação dos mini cubos ; ADICIONADO

    // Coordenadas dos mini cubos representando as vidas
    private float[][] lifeCoordinates = {
            {-20, 78}, {-10, 78}, {0, 78}, {10, 78}, {20, 78}
    };


    @Override
    public void init(GLAutoDrawable drawable) {
        // Dados iniciais da cena
        glu = new GLU();
        GL2 gl = drawable.getGL().getGL2();


        // Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -100;
        xMax = yMax = zMax = 100;

        // Habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
        angulo = 0; //Rotação
        anguloMiniCubos = 0; // Indica o ângulo dos mini cubos para a rotação

        textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 18)); //Texto

        horizontal = 0; // Indica a posição horizontal do bastão

        // Configurações iniciais do bastão
        paddleY = -90;
        paddleZ = 0;
        paddleWidth = 35;
        paddleHeight = 5;
        paddleDepth = 5;

        // Configurações iniciais da bola
        ballX = 40;
        ballY = 0;
        ballZ = 0;
        ballRadius = 3;
        ballSpeedX = (float) 1.7;
        ballSpeedY = (float) -1.7;
        ballSpeedZ = 0;
        
        //Configurações do Andamento; Pontuação e Fase Atual do jogo
        isPlaying = true;
        pontuacao = 0;
        faseAtual = 1;

        // Inicializa o contador de vidas
        vidas = 5;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        // Obtem o contexto OpenGL
        GL2 gl = drawable.getGL().getGL2();
        GLUT glut = new GLUT(); // Objeto para desenho 3D

        // Define a cor da janela (R, G, B, alpha)
        gl.glClearColor(0, 0, 0, 1);
        // Limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); // Lê a matriz identidade

        // Se o jogo estiver pausado ou interrompido, não atualize a lógica do jogo
        if (isGamePaused || isGameInterrupted || isGameWon) {
            // Desenhe uma mensagem correspondente
            String mensagem = isGamePaused ? "Jogo Pausado" : (isGameWon ? "Jogo Vencedor" : "Game Over");
            Color cor = isGamePaused ? Color.WHITE : (isGameWon ? Color.GREEN : Color.RED);
            desenhaTexto(gl, 240, 300, cor, mensagem);
            gl.glFlush();
            return;
        }

        // Incrementa o ângulo dos mini cubos
        anguloMiniCubos++;

        // Desenhar e exibir os mini cubos representando as vidas
        gl.glColor3f(1, 0, 0); // Define a cor vermelha para os mini cubos
        for (int i = 0; i < vidas; i++) {
            gl.glPushMatrix();
            gl.glTranslatef(lifeCoordinates[i][0], lifeCoordinates[i][1], 0);
            gl.glRotatef(anguloMiniCubos, 0, 1, 0); // Rotaciona ao redor do eixo Y
            glut.glutWireCube(5);
            gl.glPopMatrix();
            gl.glFlush();
        }

        // Desenhar e exibir a bola
        gl.glPushMatrix();
        gl.glColor3f(1, 1, 1); // Define a cor branca para a bola
        gl.glTranslatef(ballX, ballY, ballZ);
        glut.glutSolidSphere(ballRadius, 50, 50);
        gl.glPopMatrix();

        //Desenhar e exibir texto Pong Game
        desenhaTexto(gl, 250, 570, Color.BLUE, "Pong Game");

        // Desenhar e exibir potuação em tempo real
        desenhaTexto(gl, 10, 570, Color.BLUE, "Pontuação: " + pontuacao);

        // Desenhar e exibir a fase atual
        desenhaTexto(gl, 10, 530, Color.BLUE, "Fase: " + faseAtual);

        // Desenhar e exibir o contador de vidas
        desenhaTexto(gl, 515, 570, Color.RED, "Vidas: " + vidas);

        // Desenhar e exibir o bastão
        gl.glPushMatrix();
        gl.glTranslatef(paddleX, paddleY, paddleZ);
        gl.glScalef(paddleWidth, paddleHeight, paddleDepth);
        gl.glColor3f(1, 1, 1); // Cor branca
        glut.glutSolidCube(1);
        gl.glPopMatrix();


        // Verifica se o jogo foi iniciado
        if (!isGameStarted) {
            // Desenha a bola no centro da tela
            ballX = 0;
            ballY = 0;
            gl.glPushMatrix();
            gl.glTranslatef(ballX, ballY, ballZ);
            glut.glutSolidSphere(ballRadius, 50, 50);
            gl.glPopMatrix();
            
        } else {
            // Atualiza a posição do bastão com base na variável horizontal,
            // garantindo que ele não ultrapasse os limites da janela
            paddleX = Math.max(Math.min(horizontal, xMax - paddleWidth / 2), xMin + paddleWidth / 2);

            // Atualiza a posição da bola
            ballX += ballSpeedX;
            ballY += ballSpeedY;

            // Verifica as colisões com as extremidades da janela
            if (ballX + ballRadius >= xMax || ballX - ballRadius <= xMin) {
                // Inverte a direção horizontal da bola
                ballSpeedX *= -1;
            }
            if (ballY + ballRadius >= yMax || ballY - ballRadius <= yMin) {
                // Inverte a direção vertical da bola e a faz quicar
                ballSpeedY *= -1;
            }

            // Verifica colisão com o bastão
            if (ballY - ballRadius <= paddleY + paddleHeight / 2 && ballY - ballRadius >= paddleY - paddleHeight / 2 &&
                    ballX >= paddleX - paddleWidth / 2 && ballX <= paddleX + paddleWidth / 2) {
                // Rebateu na bola, incrementa a pontuação
                pontuacao++;
                // Faz a bola quicar ao colidir com o bastão
                ballSpeedY *= -1;
                
            } else if (ballY - ballRadius < yMin) {
                // Se a bola passar pelo bastão sem ser rebatida, perde uma vida
                vidas--;
                // Reinicia a posição da bola
                ballX = 0;
                ballY = 0;
                // Reinicia a velocidade da bola
                ballSpeedX = (float) 1.7;
                ballSpeedY = (float) -1.7;
            }
            
            // Verifica se é a Fase 2 e desenha um cubo no centro da tela
        if (faseAtual == 2) {
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0); // Centro da tela
            gl.glColor3f(1, 1, 1); // Cor branca para o cubo
            glut.glutWireCube(40); // Cubo de tamanho 10
            gl.glPopMatrix();
        }
        
            // Verifica se a fase atual é a primeira
            if (faseAtual == 1) {
                // Verifica se a pontuação alcançou o limite para avançar para a próxima fase
                if (pontuacao >= 10) {
                    // Define a próxima fase e reinicia as variáveis necessárias
                    faseAtual = 2;
                    reiniciarFase();
                }
                
            } else if (faseAtual == 2) {
                // Verifica se a pontuação alcançou o limite para vencer a segunda fase
                if (pontuacao >= 10) {
                    // Exibe a mensagem de vencedor
                    desenhaTexto(gl, 250, 300, Color.GREEN, "Jogo Vencido");
                    // Interrompe o jogo
                    isGameWon = true;
                    // Retorna para evitar que o restante do código da cena seja executado
                    gl.glFlush();
                    return;
                }
            }
        }

        // Verifica se todas as vidas foram perdidas
        if (vidas <= 0) {
            // Todas as vidas foram perdidas, exibe "Game Over" e interromper o jogo
            desenhaTexto(gl, 250, 300, Color.RED, "Game Over");
            isGameInterrupted = true;
            return;
        }

        gl.glFlush();
    }

    // Método para reiniciar as variáveis da fase
    public void reiniciarFase() {
        // Reinicia a posição da bola
        ballX = 0;
        ballY = 0;

        // Reinicia a velocidade da bola
        if (faseAtual == 1) {
            // Velocidade padrão da fase 1
            ballSpeedX = 1.7f;
            ballSpeedY = -1.7f;
        } else if (faseAtual == 2) {
            // Aumenta a velocidade da bola e o grau de dificuldade para a fase 2
            ballSpeedX = 2.0f; // Ajuste conforme necessário
            ballSpeedY = -2.0f; // Ajuste conforme necessário
            // Reinicia a pontuação ao entrar na Fase 2
            pontuacao = 0;
        }

        // Reinicia outras variáveis conforme necessário
    }

    // Método para iniciar o jogo
    public void IniciarJogo() {
        isGameStarted = true;
    }

    // Método para pausar o jogo
    public void PausarJogo() {
        alternarPausa();
    }

    // Método para pausar ou retomar o jogo
    public void alternarPausa() {
        isGamePaused = !isGamePaused;
    }

    // Método para Parar/Interromper o jogo
    public void PararJogo() {
        isGameInterrupted = !isGameInterrupted;
    }

    // Método para Parar/Interromper o jogo
    public void JogoVencedor() {
        isGameWon = !isGameWon;
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Obtem o contexto gráfico OpenGL
        GL2 gl = drawable.getGL().getGL2();

        // Evita a divisão por zero
        if (height == 0) height = 1;
        // Calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;

        // Seta o viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);

        // Ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); // Lê a matriz identidade

        // Projeção ortogonal
        if (width >= height)
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);

        // Ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // Lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase) {
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        // Retorna a largura e altura da janela
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
    }
}