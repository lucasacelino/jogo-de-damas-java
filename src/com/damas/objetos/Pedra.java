package com.damas.objetos;

/**
 * Representa uma Peça do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 * @author José Alisson Rocha da Silva {@link jose.alisson2@academico.ufpb.br}
 */
public abstract class Pedra extends Peca {
    protected Tabuleiro tabuleiro; // 🔹 Agora `Pedra` tem uma referência ao `Tabuleiro`

    public Pedra(Casa casa, Tabuleiro tabuleiro) {
        super(casa);
        this.tabuleiro = tabuleiro;
    }

    @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        if (distanciaX == 2 && distanciaY == 2) {
            int meioX = (casa.getX() + destino.getX()) / 2;
            int meioY = (casa.getY() + destino.getY()) / 2;
            Casa casaIntermediaria = tabuleiro.getCasa(meioX, meioY);
            return casaIntermediaria.getPeca() != null && casaIntermediaria.getPeca().getClass() != this.getClass();
        }

        return false;
    }
}
