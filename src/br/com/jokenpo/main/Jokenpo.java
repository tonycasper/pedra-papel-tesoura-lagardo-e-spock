package br.com.jokenpo.main;

import java.util.Random;
import java.util.Scanner;

public class Jokenpo {
	
	private Usuario usuario;
	private Computador computador;
	private int scoreUsuario;
	private int scoreComputador;
	private int numeroJogadas;
	
	private enum Movimento{

		PEDRA {
			@Override
			public boolean ganha(Movimento movimento) {
				return movimento == Movimento.LAGARTO || movimento == Movimento.TESOURA;
			}

			@Override
			public boolean perde(Movimento movimento) {
				return movimento == Movimento.SPOCK || movimento == Movimento.PAPEL;
			}

		},
		PAPEL {
			@Override
			public boolean ganha(Movimento movimento) {
				return movimento == Movimento.PEDRA || movimento == Movimento.SPOCK;
			}

			@Override
			public boolean perde(Movimento movimento) {
				return movimento == Movimento.TESOURA || movimento == Movimento.LAGARTO;
			}

		},
		TESOURA {
			@Override
			public boolean ganha(Movimento movimento) {
				return movimento == Movimento.LAGARTO || movimento == Movimento.PAPEL;
			}

			@Override
			public boolean perde(Movimento movimento) {
				return movimento == Movimento.SPOCK || movimento == Movimento.PEDRA;
			}

		},
		LAGARTO {
			@Override
			public boolean ganha(Movimento movimento) {
				return movimento == Movimento.SPOCK || movimento == Movimento.PAPEL;
			}

			@Override
			public boolean perde(Movimento movimento) {
				return movimento == Movimento.TESOURA || movimento == Movimento.PEDRA;
			}

		},
		SPOCK {
			@Override
			public boolean ganha(Movimento movimento) {
				return movimento == Movimento.TESOURA || movimento == Movimento.PEDRA;
			}

			@Override
			public boolean perde(Movimento movimento) {
				return movimento == Movimento.LAGARTO || movimento == Movimento.PAPEL;
			}

		};


		public abstract boolean ganha(Movimento movimento);

		public abstract boolean perde(Movimento movimento);
		
		public int comparaMovimento(Movimento outroMovimento){			
			if(this == outroMovimento)
				return 0;
			
            return (outroMovimento.ganha(this) ? 1 : -1);          			
		}
	}
	
	public class Usuario{
		private Scanner inputScanner;
		
		public Usuario() {
			inputScanner = new Scanner(System.in);			
		}
		
		public Movimento getMovimento() {
		    System.out.print("Pedra, papel, tesoura, lagarto ou Spock?? ");
		    String entradaUsuario = inputScanner.nextLine();
		   		    		    
		    return analisaMovimento(entradaUsuario);
		}
		
		public Movimento analisaMovimento(String movimentoVerificar) {
			movimentoVerificar = movimentoVerificar.toUpperCase();
			switch(movimentoVerificar) {
			case "PEDRA":
				return Movimento.PEDRA;
			case "PAPEL":
				return Movimento.PAPEL;
			case "TESOURA":
				return Movimento.TESOURA;
			case "SPOCK":
				return Movimento.SPOCK;
			case "LAGARTO":
				return Movimento.LAGARTO;
			default:
				System.out.println("jogada invalida");
		    	return getMovimento();
			}
		}
	
		
		public boolean jogarNovamente() {
			System.out.print("Você deseja jogar novamente? ");
            String entradaUsuario = inputScanner.nextLine();
            entradaUsuario = entradaUsuario.toUpperCase();
            
            if(!entradaUsuario.contains("SIM") && !entradaUsuario.contains("NAO")){
            	System.out.println("Opção invalida");
            	jogarNovamente();
            } else {
            	return entradaUsuario.charAt(0) == 'S';
            }
            return false;
		}
	}
	
	private class Computador{
		public Movimento getMovimento() {
			Movimento[]	movimentos = Movimento.values();
			
			Random aleatorio = new Random();
			int index = aleatorio.nextInt(movimentos.length);
			return movimentos[index];
		}
	}
	
	public Jokenpo() {
		usuario = new Usuario();	
		computador = new Computador();
		scoreUsuario = 0;
		scoreComputador = 0;
		numeroJogadas = 0;
	}
	
	public void iniciarJogo() {
		System.out.println("PEDRA, PAPEL, LAGARTO, TESOURA, SPOCK!");
		System.out.println("\n");
		
		Movimento movimentoUsuario = usuario.getMovimento();
		Movimento movimentoComputador = computador.getMovimento();
		System.out.println("\nVoce jogou " + movimentoUsuario + ".");
		System.out.println("Computador jogou " + movimentoComputador + ".");
		
		int comparaMovimentos = movimentoUsuario.comparaMovimento(movimentoComputador);
		switch(comparaMovimentos) {
		case 0: 
			System.out.println("Empate");
			break;		
		case 1:
			System.out.println(movimentoUsuario + " ganha de " + movimentoComputador + ". Voce venceu");
			System.out.println("\n");
			scoreUsuario++;
			break;
		case -1:
			System.out.println(movimentoComputador + "ganha de " + movimentoUsuario + ". Voce perdeu");
			System.out.println("\n");
			scoreComputador++;
			break;			
		}
		numeroJogadas++;
		
		if(usuario.jogarNovamente()) {
			System.out.println();
			iniciarJogo();
		} else {
			mostrarScore();
		}
	}
		private void mostrarScore() {
			int empates = numeroJogadas - scoreComputador - scoreUsuario;
			double porcentagemDeGanhos = (scoreUsuario + ((double) empates)/2) / numeroJogadas;
			
			System.out.print("+");
		    linha(68);
		    System.out.println("+");
			
		    System.out.printf("|  %6s  |  %6s  |  %6s  |  %12s  |  %14s  |\n",
	                "VITÓRIAS", "DERROTAS", "EMPATES", "JOGOS REALIZADOS", "PORCENTAGEM DE VITÓRIAS");
	    
		    System.out.print("|");
		    linha(10);
	        System.out.print("+");
	        linha(10);
	        System.out.print("+");
	        linha(10);
	        System.out.print("+");
	        linha(16);
	        System.out.print("+");
	        linha(18);
	        System.out.println("|");
	        
	        System.out.printf("|  %6d  |  %6d  |  %6d  |  %12d  |  %13.2f%%  |\n",
	        		scoreUsuario, scoreComputador, empates, numeroJogadas, porcentagemDeGanhos * 100);
	        
	        System.out.print("+");
	        linha(68);
	        System.out.println("+");
	}
	
	private void linha(int numeroDeTracos) {
        for (int i = 0; i < numeroDeTracos; i++) {
            System.out.print("-");
        }
    }
		
	 public static void main(String[] args) {
	       Jokenpo jogo = new Jokenpo();
	       jogo.iniciarJogo();
	 }	
}
