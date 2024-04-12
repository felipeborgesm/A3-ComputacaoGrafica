package Textura;

import com.jogamp.opengl.GL2;
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Textura {
    private Texture vetTextures[];
    private float width;
    private float height;
    private int filtro;
    private int modo;
    private int wrap;
    private boolean automatica;

    public Textura(int totalTextura) {
        vetTextures = new Texture[totalTextura];
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public int getFiltro() {
        return filtro;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public int getModo() {
        return modo;
    }

    public void setWrap(int wrap) {
        this.wrap = wrap;
    }

    public int getWrap() {
        return wrap;
    }

    public void setAutomatica(boolean automatica){
        this.automatica = automatica;
    }

    public boolean getAutomatica(){
        return automatica;
    }

    public void gerarTextura(GL2 gl, String fileName, int indice) {
        carregarTextura(fileName, indice);
        Texture tex = vetTextures[indice];
        if(tex == null) {
            return;
        }
        tex.bind(gl);
        tex.enable(gl);

        tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, filtro);
        tex.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, filtro);

        tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, wrap);
        tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, wrap);

        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, modo);
        if(this.automatica)
            habilitarTexturaAutomatica(gl);
    }

    private void carregarTextura(String fileName, int indice) {
        Texture tex = null;

        try {
            tex = TextureIO.newTexture(new File(fileName), true);
        } catch (IOException e) {
            System.out.println("\n=============\nErro na leitura do arquivo "
                    + fileName + "\n=============\n");
        }

        if(tex != null) {
            this.width = tex.getWidth();
            this.height = tex.getHeight();
            vetTextures[indice] = tex;
        }
    }

    public void habilitarTexturaAutomatica(GL2 gl) {
        int genModo = GL2.GL_OBJECT_LINEAR;

        float planoS[] = {1.0f, 0.0f, 0.0f, 0.0f};
        float planoT[] = {0.0f, 0.0f, 1.0f, 0.0f};

        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
        gl.glEnable(GL2.GL_TEXTURE_GEN_T);

        gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, genModo);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, genModo);

        gl.glTexGenfv(GL2.GL_S, GL2.GL_OBJECT_PLANE, planoS, 0);
        gl.glTexGenfv(GL2.GL_T, GL2.GL_OBJECT_PLANE, planoT, 0);
    }

    public void desabilitarTexturaAutomatica(GL2 gl) {
        gl.glDisable(GL2.GL_TEXTURE_GEN_S);
        gl.glDisable(GL2.GL_TEXTURE_GEN_T);
    }

    public void desabilitarTextura(GL2 gl, int indice) {
        Texture tex = vetTextures[indice];
        if(tex != null) {
            tex.disable(gl);
            if(this.automatica)
                desabilitarTexturaAutomatica(gl);
        }
    }
}
