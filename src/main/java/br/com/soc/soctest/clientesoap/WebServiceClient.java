package br.com.soc.soctest.clientesoap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class WebServiceClient {

	String url = "http://soap.soctest.soc.com.br/";
	URL wsdlURL;
	QName qService;
	Service service;
	WebServiceExames wse;
	Scanner input;

	public void conectarWebService() {
		try {
			wsdlURL = new URL("http://localhost:8080/soctest/soap?wsdl");
			QName qService = new QName(url, "WebServiceExamesImplService");
			service = Service.create(wsdlURL, qService);
			wse = service.getPort(new QName(url, "WebServiceExamesImplPort"), WebServiceExames.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void menuPrincipal() {
		StringBuilder menu = new StringBuilder();
		menu.append("\t1 - Buscar por codigo de Exame\n");
		menu.append("\t2 - Buscar por codigo de Paciente todos os exames\n");
		menu.append("\t9 - Sair\n");
		System.out.println(menu.toString());

	}

	private void cabecalho() {
		StringBuilder cabecalho = new StringBuilder();
		cabecalho.append("\n\tSOC Test");
		cabecalho.append("\n\tGestão Exames\n\n");
		
		System.out.println(cabecalho.toString());
	}

	private void subMenu() {
		StringBuilder subMenuOne = new StringBuilder();
		subMenuOne.append("\tNova Busca:\n\n");
		subMenuOne.append("\t1 - Sim\n");
		subMenuOne.append("\t2 - Volta Menu Anterior\n");
		subMenuOne.append("\t9 - Sair\n");
		System.out.println(subMenuOne.toString());
	}
	
	

	private void limparConsole() {
		input = new Scanner(System.in);
		if (System.getProperty("os.name").contains("Windows")) {
			try {
				
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void switchMenuPrincipal(int escolha) {
		
		switch (escolha) {
		case 1:
					
			porExame();
			
		break;
		case 2:	
			
			porPaciente();		
			
		break;
		case 9:
			System.err.println("Fim programa");
			System.exit(0);
			break;
		default:
			System.out.println("Opção Inavalida");
			break;
		}

	}	
	
	private void porExame() {
		limparConsole();
		cabecalho();	
		System.out.println("\tDigite o Codigo do exame que deseja consultar tecle enter:\n\t");
		
		if (input.hasNextInt()) {
			System.out.println(wse.findExame(input.nextLong()));
			subMenu();
			newScanner();
			if (input.hasNextInt()) {
				switch(input.nextInt()) {
				 case 1:
					 porExame();
				 break;
				 case 2:
					 init();
					 break;
				default:
					System.out.println("opção invalida");
					break;
				}
			}			
		}
		System.out.println("\t####ERRO####\n\tDigite o valor inserido nao e valido");
		porExame();

	}
	private void porPaciente() {
		limparConsole();
		cabecalho();	
		System.out.println("\tDigite o Codigo do paciente que deseja consultar os exames tecle enter:\n\t");
		
		if (input.hasNextInt()) {
			System.out.println(wse.findAllByPacienteCodigo(input.nextLong()));
			subMenu();
			newScanner();
			if (input.hasNextInt()) {
				switch(input.nextInt()) {
				 case 1:
					 porPaciente();
				 break;
				 case 2:
					 init();
					 break;
				default:
					System.out.println("opção invalida");
					break;
				}
			}			
		}
		System.out.println("\t####ERRO####\n\tDigite o valor inserido nao e valido");
		porPaciente();

	}
	
	private void  newScanner() {
		input = new Scanner(System.in);
	}
	

	public void init() {
		conectarWebService();
		limparConsole();
		cabecalho();		
		menuPrincipal();
		
		if (input.hasNextInt()) {
			switchMenuPrincipal(input.nextInt());
		} else {
			init();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		WebServiceClient client = new WebServiceClient();
		client.init();

	}

}
