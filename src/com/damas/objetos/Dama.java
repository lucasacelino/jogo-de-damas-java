package com.damas.objetos;

/**
 * Dama do jogo.
 * <p>Recebe uma casa e um tipo associado</p>
 * @author João Victor da S. Cirilo {@link joao.cirilo@academico.ufpb.br}
 */
public abstract class Dama extends Pedra {
    public Dama(Casa casa, Tabuleiro tabuleiro) {
        super(casa, tabuleiro);
    }

    @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());
        return distanciaX == distanciaY;
    }
}