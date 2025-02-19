package com.damas.objetos;

import java.util.ArrayList;
import java.util.List;

public class Movimentacao {
    private Tabuleiro tabuleiro;
    private List<Casa> pecasAComer;

    public Movimentacao(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        this.pecasAComer = new ArrayList<>();
    }

    public boolean simularMovimentoEValidar(Casa origem, Casa destino, int vezAtual) {
        Peca peca = origem.getPeca();

        if (peca == null || destino.getPeca() != null) return false;
        if ((peca instanceof PedraBranca || peca instanceof DamaBranca) && vezAtual != 1) return false;
        if ((peca instanceof PedraVermelha || peca instanceof DamaVermelha) && vezAtual != 2) return false;

        return verificarMovimento(peca, origem, destino);
    }


    private boolean verificarMovimento(Peca peca, Casa origem, Casa destino) {
        int deltaX = destino.getX() - origem.getX();
        int deltaY = destino.getY() - origem.getY();
        int distanciaX = Math.abs(deltaX);
        int distanciaY = Math.abs(deltaY);

        if (peca instanceof Dama) {
            return verificarMovimentoDama(peca, origem, destino, deltaX, deltaY, distanciaX, distanciaY);
        } else {
            return verificarMovimentoPedra(peca, origem, destino, distanciaX, distanciaY);
        }
    }

    private boolean verificarMovimentoPedra(Peca peca, Casa origem, Casa destino, int distanciaX, int distanciaY) {
        boolean movimentoParaTras = (peca instanceof PedraBranca && destino.getY() < origem.getY()) ||
                (peca instanceof PedraVermelha && destino.getY() > origem.getY());

        if (distanciaX == 1 && distanciaY == 1) {
            return !movimentoParaTras; // 🔹 Agora impede movimentos para trás, exceto ao capturar
        } else if (distanciaX == 2 && distanciaY == 2) {
            int meioX = (origem.getX() + destino.getX()) / 2;
            int meioY = (origem.getY() + destino.getY()) / 2;
            Casa casaIntermediaria = tabuleiro.getCasa(meioX, meioY);
            Peca pecaIntermediaria = casaIntermediaria.getPeca();

            if (pecaIntermediaria != null && !saoAliadas(peca, pecaIntermediaria)) {
                pecasAComer.add(casaIntermediaria);
                return true;
            }
        }

        return false;
    }


    private boolean verificarMovimentoDama(Peca peca, Casa origem, Casa destino, int deltaX, int deltaY, int distanciaX, int distanciaY) {
        if (distanciaX != distanciaY) return false; // 🔹 Agora garante que a dama só pode se mover na diagonal

        int sentidoX = Integer.compare(deltaX, 0);
        int sentidoY = Integer.compare(deltaY, 0);

        int x = origem.getX() + sentidoX;
        int y = origem.getY() + sentidoY;
        boolean encontrouPeca = false;

        while (x != destino.getX() && y != destino.getY()) {
            Casa casaAtual = tabuleiro.getCasa(x, y);
            Peca pecaAtual = casaAtual.getPeca();

            if (pecaAtual != null) {
                if (encontrouPeca || saoAliadas(peca, pecaAtual)) {
                    return false;
                }
                encontrouPeca = true;
                pecasAComer.add(casaAtual);
            }

            x += sentidoX;
            y += sentidoY;
        }

        return true;
    }

    private boolean saoAliadas(Peca peca1, Peca peca2) {
        return (peca1 instanceof PedraBranca || peca1 instanceof DamaBranca) &&
                (peca2 instanceof PedraBranca || peca2 instanceof DamaBranca) ||
                (peca1 instanceof PedraVermelha || peca1 instanceof DamaVermelha) &&
                        (peca2 instanceof PedraVermelha || peca2 instanceof DamaVermelha);
    }


    public boolean haCapturaDisponivel() {
        return !pecasAComer.isEmpty();
    }

    public boolean deveContinuarJogando(Casa origem) {
        return verificarCapturaDisponivel(origem);
    }

    private boolean verificarCapturaDisponivel(Casa origem) {
        int[][] direcoes = {
                {-2, -2}, {-2, 2}, {2, -2}, {2, 2} // Todas as diagonais possíveis para capturar
        };

        Peca peca = origem.getPeca();
        if (peca == null) return false;

        for (int[] direcao : direcoes) {
            int meioX = origem.getX() + (direcao[0] / 2);
            int meioY = origem.getY() + (direcao[1] / 2);
            int destinoX = origem.getX() + direcao[0];
            int destinoY = origem.getY() + direcao[1];

            if (destinoX >= 0 && destinoX < 8 && destinoY >= 0 && destinoY < 8) {
                Casa meio = tabuleiro.getCasa(meioX, meioY);
                Casa destino = tabuleiro.getCasa(destinoX, destinoY);

                Peca pecaMeio = meio.getPeca();
                if (pecaMeio != null && !saoAliadas(peca, pecaMeio) && destino.getPeca() == null) {
                    return true; // 🔹 Agora só permite continuar capturando se houver peça adversária e espaço vazio depois dela
                }
            }
        }

        return false;
    }

    public List<Casa> getPecasAComer() {
        return pecasAComer;
    }

    public void limparPecasAComer() {
        pecasAComer.clear();
    }
}
