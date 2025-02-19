package com.damas.objetos;

public class ControleTurno {
    private int vezAtual;
    private int jogadas;
    private int jogadasSemComer;
    private Movimentacao movimentacao; // 🔹 Adicionamos a referência para Movimentacao

    public ControleTurno(Movimentacao movimentacao) { // 🔹 Agora recebe Movimentacao no construtor
        this.movimentacao = movimentacao;
        vezAtual = 1;
        jogadas = 0;
        jogadasSemComer = 0;
    }

    public void trocarDeVez() {
        if (!movimentacao.haCapturaDisponivel()) { // 🔹 Agora podemos verificar capturas antes de trocar de vez
            vezAtual = (vezAtual == 1) ? 2 : 1;
        }
    }

    public void incrementarJogadas() {
        jogadas++;
    }

    public void incrementarJogadasSemComer() {
        jogadasSemComer++;
    }

    public void resetarJogadasSemComer() {
        jogadasSemComer = 0;
    }

    public int getVez() {
        return vezAtual;
    }

    public int getJogadas() {
        return jogadas;
    }

    public int getJogadasSemComer() {
        return jogadasSemComer;
    }

    public boolean verificarFimDeJogo(Jogador jogadorUm, Jogador jogadorDois) {
        return jogadorUm.getPontos() == 12 || jogadorDois.getPontos() == 12 || jogadasSemComer >= 20;
    }

    public String obterStatusJogo(Jogador jogadorUm, Jogador jogadorDois, Casa casaBloqueadaOrigem) {
        StringBuilder status = new StringBuilder();

        status.append("Vez: ").append((vezAtual == 1) ? jogadorUm.getNome() : jogadorDois.getNome()).append("\n");
        status.append("Nº de jogadas: ").append(jogadas).append("\n");
        status.append("Jogadas sem comer peça: ").append(jogadasSemComer).append("\n\n");

        status.append("Informações do jogador ").append(jogadorUm.getNome()).append("\n");
        status.append("Pontos: ").append(jogadorUm.getPontos()).append("\n");
        status.append("Nº de peças restantes: ").append(12 - jogadorDois.getPontos()).append("\n\n");

        status.append("Informações do jogador ").append(jogadorDois.getNome()).append("\n");
        status.append("Pontos: ").append(jogadorDois.getPontos()).append("\n");
        status.append("Nº de peças restantes: ").append(12 - jogadorUm.getPontos()).append("\n");

        if (casaBloqueadaOrigem != null) {
            status.append("\nMova a peça na casa ").append(casaBloqueadaOrigem.getX()).append(":").append(casaBloqueadaOrigem.getY()).append("!");
        }

        return status.toString();
    }
}