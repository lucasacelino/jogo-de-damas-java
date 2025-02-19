package com.damas.objetos;

import java.util.List;

public class GerenciadorCaptura {
    private Jogador jogadorUm;
    private Jogador jogadorDois;

    public GerenciadorCaptura(Jogador jogadorUm, Jogador jogadorDois) {
        this.jogadorUm = jogadorUm;
        this.jogadorDois = jogadorDois;
    }

    public void registrarCaptura(List<Casa> pecasCapturadas) {
        int pecasComidas = pecasCapturadas.size();

        if (pecasComidas > 0) {
            Jogador jogadorAtual = (jogadorUm.getPontos() % 2 == 0) ? jogadorUm : jogadorDois;
            jogadorAtual.addPonto(pecasComidas);
        }

        for (Casa casa : pecasCapturadas) {
            casa.removerPeca();
        }

        pecasCapturadas.clear();
    }


    public int getGanhador(Tabuleiro tabuleiro) {
        boolean temPecaBranca = false;
        boolean temPecaVermelha = false;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Casa casa = tabuleiro.getCasa(x, y);
                Peca peca = casa.getPeca();
                if (peca != null) {
                    if (peca instanceof PedraBranca || peca instanceof DamaBranca) {
                        temPecaBranca = true;
                    }
                    if (peca instanceof PedraVermelha || peca instanceof DamaVermelha) {
                        temPecaVermelha = true;
                    }
                }
            }
        }

        if (!temPecaBranca) return 2; // 🔹 O jogador vermelho venceu
        if (!temPecaVermelha) return 1; // 🔹 O jogador branco venceu

        return 0; // 🔹 O jogo continua
    }
}
