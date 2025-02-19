package com.damas.objetos;

public class Jogo {
    private Tabuleiro tabuleiro;
    private Jogador jogadorUm;
    private Jogador jogadorDois;
    private ControleTurno controleTurno;
    private Movimentacao movimentacao;
    private GerenciadorCaptura gerenciadorCaptura;
    private Casa casaBloqueadaOrigem;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        jogadorUm = new Jogador("player branco");
        jogadorDois = new Jogador("player vermelho");
        movimentacao = new Movimentacao(tabuleiro);
        controleTurno = new ControleTurno(movimentacao);
        gerenciadorCaptura = new GerenciadorCaptura(jogadorUm, jogadorDois);

        casaBloqueadaOrigem = null;
        colocarPecas();
    }

    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);
        Peca peca = origem.getPeca();

        if (casaBloqueadaOrigem == null) {
            if (movimentacao.simularMovimentoEValidar(origem, destino, controleTurno.getVez())) {
                peca.mover(destino);
                transformarPedraParaDama(destino);

                if (movimentacao.haCapturaDisponivel()) {
                    gerenciadorCaptura.registrarCaptura(movimentacao.getPecasAComer());

                    if (movimentacao.deveContinuarJogando(destino)) {
                        casaBloqueadaOrigem = destino;
                    } else {
                        casaBloqueadaOrigem = null;
                        controleTurno.resetarJogadasSemComer();
                        controleTurno.trocarDeVez();
                    }
                } else {
                    casaBloqueadaOrigem = null;
                    controleTurno.incrementarJogadasSemComer();
                    controleTurno.trocarDeVez();
                }

                controleTurno.incrementarJogadas();
            }
        } else {
            if (origem.equals(casaBloqueadaOrigem) && movimentacao.simularMovimentoEValidar(origem, destino, controleTurno.getVez())) {
                if (movimentacao.haCapturaDisponivel()) {
                    casaBloqueadaOrigem = null;
                    moverPeca(origemX, origemY, destinoX, destinoY);
                }
            }
        }
    }

    private void colocarPecas() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 3; y++) {
                if ((x % 2 == 0) && (y % 2 == 0)) {
                    new PedraBranca(tabuleiro.getCasa(x, y), tabuleiro);
                } else if ((x % 2 != 0) && (y % 2 != 0)) {
                    new PedraBranca(tabuleiro.getCasa(x, y), tabuleiro);
                }
            }
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 5; y < 8; y++) {
                if ((x % 2 != 0) && (y % 2 != 0)) {
                    new PedraVermelha(tabuleiro.getCasa(x, y), tabuleiro);
                } else if ((x % 2 == 0) && (y % 2 == 0)) {
                    new PedraVermelha(tabuleiro.getCasa(x, y), tabuleiro);
                }
            }
        }
    }

    private void transformarPedraParaDama(Casa casa) {
        Peca pedra = casa.getPeca();

        if (pedra instanceof PedraBranca && casa.getY() == 7) {
            casa.setPeca(new DamaBranca(casa, tabuleiro));
        } else if (pedra instanceof PedraVermelha && casa.getY() == 0) {
            casa.setPeca(new DamaVermelha(casa, tabuleiro));
        }
    }

    public int getGanhador() {
        return gerenciadorCaptura.getGanhador(tabuleiro);
    }

    public int getJogadasSemComerPecas() {
        return controleTurno.getJogadasSemComer();
    }

    public Jogador getJogadorUm() {
        return jogadorUm;
    }

    public Jogador getJogadorDois() {
        return jogadorDois;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    @Override
    public String toString() {
        return controleTurno.obterStatusJogo(jogadorUm, jogadorDois, casaBloqueadaOrigem);
    }
}