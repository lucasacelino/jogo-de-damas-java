package com.damas.objetos;

public class PedraBranca extends Pedra {
    public PedraBranca(Casa casa, Tabuleiro tabuleiro) {
        super(casa, tabuleiro);
    }

    @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        boolean movimentoParaFrente = destino.getY() > casa.getY();
        return (distanciaX == 1 && distanciaY == 1 && movimentoParaFrente) || super.isMovimentoValido(destino);
    }
}