package com.damas.objetos;

/**
 * Interface com os métodos abstratos das peças
 * @author João Victor da S. Cirilo {@link joao.cirilo@academico.ufpb.br}
 */
public abstract class Peca {
    protected Casa casa;

    public Peca(Casa casa) {
        this.casa = casa;
        casa.colocarPeca(this);
    }

    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
    }

    public abstract boolean isMovimentoValido(Casa destino);
}
