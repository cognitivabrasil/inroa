/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.atualiza;

import Exception.RepositoriosException;
import ferramentaBusca.indexador.Indexador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import modelos.Repositorio;
import modelos.RepositoryDAO;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.xml.sax.SAXException;
import postgres.Conectar;
import robo.atualiza.harvesterOAI.Principal;
import robo.atualiza.importaOAI.XMLtoDB;
import robo.util.Informacoes;
import robo.util.Operacoes;
import spring.ApplicationContextProvider;

/**
 * 
 * @author Marcos
 */
public class Repositorios {

	private SimpleDateFormat dataFormat = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	static Logger log = Logger.getLogger(Repositorios.class);

	/**
	 * Testa se algum reposit&oacute;rios precisa ser atualizado, se sim chama o
	 * m&etodo respons&aacute;vel por isso.
	 * 
	 * @param con
	 *            Conex&atilde;o com a base de dados.
	 * @param indexar
	 *            Variavel do tipo Indexador. &Eacute; utilizada para passar os
	 *            dados para o indice durante a atualiza&ccidil;&atilde;o dos
	 *            metadados
	 * @return true ou false indicando se algum reposit&aacute;rio foi
	 *         atualizado ou n&atilde;
	 */
	public boolean testa_atualizar_repositorio(Indexador indexar) {
		boolean atualizou = false;

		Repositorio repositorio = new Repositorio();
		List<Repositorio> listRep = repositorio.getOutDatedDocs();

		for (Repositorio rep : listRep) { // percorre todos os repositorios que
											// precisam ser atualizados
			try {
				atualizaRepositorio(rep, indexar); // chama o metodo que
													// atualiza o repositorio
				atualizou = true;
			} catch (Exception e) {
				// TODO: Isso é um BUG! Tem que dar catch só nas excessões
				// conhecidas
				/*
				 * ATENÇÃO: esse catch está vazio porque já é feito o tratamento
				 * de exceção dentro do metodo atualizaRepositorio mas é preciso
				 * subir a exceção porque se atualizar um repositorio só pela
				 * ferramenta administrativa tem que saber se deu erro.
				 */
			}
		}

		return atualizou;
	}

	/**
	 * M&eacute;todo utilizado pela ferramenta administrativa para atualizar o
	 * reposit&oacute;rio em tempo real. Este m&eacute;todo recebe um id, se
	 * esse if for zero ele atualiza todos os reposit&aacute;rios existentes. Se
	 * for um valor maior que zero ele atualiza apenas o escolhido.
	 * 
	 * @param idRep
	 *            id do reposit&oacute;rio a ser atualizado. Se informar zero
	 *            atualizar&aacute; todos
	 * @param apagar
	 *            informar se deseja apagar toda a base. true apaga e false
	 *            apenas atualiza.
	 * @author Marcos Nunes
	 */
	public void atualizaFerramentaAdm(int idRep, boolean apagar)
			throws Exception {

		ApplicationContext ctx = ApplicationContextProvider
				.getApplicationContext();
		if (ctx == null) {
			log.fatal("Could not get AppContext bean!");
			throw new ApplicationContextException(
					"Could not get AppContext bean!");
		} else {

			Indexador indexar = new Indexador();
			Connection con = null;
			boolean recalcularIndice = false;
			Conectar conectar = new Conectar(); // instancia uma variavel da
												// classe Conectar
			RepositoryDAO repDao = ctx.getBean(RepositoryDAO.class);
			try {
				con = conectar.conectaBD(); // chama o metodo conectaBD da
											// classe conectar
				ArrayList<String> erros = new ArrayList<String>();

				if (idRep > 0) { // atualizar um repositorio especifico ou
									// todos. 0 = todos
					Repositorio rep = repDao.get(idRep);
					if (apagar) {
						rep.setUltimaAtualizacao(null);
						rep.setDataOrigem(null);
						// todo: zerar o repositorio
					}
					if (atualizaRepositorio(rep, indexar) == 0) {
						repDao.save(rep);
						recalcularIndice = false;
					} else {
						repDao.save(rep);
						recalcularIndice = true;
					}

				} else {
					List<Repositorio> repositorios = repDao.getAll();
					for (Repositorio r : repositorios) {
						try {
							if (atualizaRepositorio(r, indexar) > 0) {
								recalcularIndice = true;
							}
						} catch (Exception e) {
							erros.add(r.getNome());
							log.error(
									"FEB ERRO: Erro ao atualizar o repositorio "
											+ r.getNome(), e);
						}
					}
				}

				if (recalcularIndice) {
					log.info("FEB: recalculando o indice "
							+ dataFormat.format(new Date()));
					indexar.populateR1(con);
					log.info("FEB: indice recalculado! "
							+ dataFormat.format(new Date()));
				}
				if (erros.size() > 0) {
					throw new RepositoriosException(getMensagem(erros)); // gera
																			// uma
																			// exception
																			// informando
																			// o
																			// nome
																			// dos
																			// repositorios
																			// que
																			// nao
																			// foram
																			// atualizados
				}

			} catch (SQLException e) {
				log.error("FEB ERRO: SQL Exception... Erro na consulta SQL.", e);
				throw e;
			} finally {
				try {
					con.close(); // fechar conexao
				} catch (SQLException e) {
					log.error("FEB ERRO: Erro ao fechar a conexão.", e);
				}
			}
		}

	}

	/**
	 * Atualiza o reposit&oacute;rio solicitado.
	 * 
	 * @param idRepositorio
	 *            id do reposit&oacute;rio que deve ser atualizado.
	 * @param indexar
	 *            Variavel do tipo Indexador. &Eacute; utilizada para passar os
	 *            dados para o indice durante a atualiza&ccidil;&atilde;o dos
	 *            metadados
	 * @param con
	 *            Conex&atilde;o com a base de dados.
	 * @return number of updated documents, -1 in case of error
	 */
	public int atualizaRepositorio(Repositorio rep, Indexador indexar)
			throws Exception {

		Principal importar = new Principal();
		Informacoes conf = new Informacoes();
		XMLtoDB gravar = new XMLtoDB();
		String caminhoDiretorioTemporario = conf.getCaminho();

		int updated = 0;

		try {
			log.info("FEB: (" + dataFormat.format(new Date())
					+ ") Atualizando repositorio: " + rep.getNome());// imprime
																		// o
																		// nome
																		// do
																		// repositorio

			Set<String> set = rep.getColecoes();
			if (set == null || set.isEmpty()) {
				set = null;
			}
			if (rep.getUrl().isEmpty()) { // testa se a string url esta vazia.
				log.error("FEB: Nao existe uma url associada ao repositorio "
						+ rep.getNome());
				throw new MalformedURLException(
						"Nao existe uma url associada ao repositorio "
								+ rep.getNome());

			} else {// repositorio possui url para atualizacao

				Date data_ultima_atualizacao = rep.getUltimaAtualizacao();

				log.info("\t FEB: Ultima Atualizacao: "
						+ data_ultima_atualizacao + " nome do rep: "
						+ rep.getNome());

				// se a data da ultima atualização for inferior a 01/01/1000
				// apaga todos as informacoes do repositorio
				if ((data_ultima_atualizacao == null && rep.getDataOrigem() == null)
						|| (Operacoes
								.testarDataDifZero(data_ultima_atualizacao) && Operacoes
								.testarDataDifZero(rep.getDataOrigem()))) {
					log.info("FEB: Deletando todos os documentos do repositório: "
							+ rep.getNome().toUpperCase());
					try {
						int result = rep.dellAllDocs();
						log.info("FEB: " + result + " documentos deletados.");

					} catch (Exception e) {
						log.error("Erro ao deletar os objetos do repositorio "
								+ rep.getNome() + ".", e);
					}
				}

				// se o diretorio nao existir, cria se já existir apaga todos os
				// .xml
				File caminhoTeste = new File(caminhoDiretorioTemporario);
				if (!caminhoTeste.isDirectory()) {// se o caminho informado nao
													// for um diretorio
					caminhoTeste.mkdirs();// cria o diretorio
				} else { // APAGA TODOS ARQUIVOS XML do FEB DA PASTA
					File[] arquivos = caminhoTeste.listFiles();
					for (File toDelete : arquivos) {
						if (toDelete.getName().startsWith("FEB-")
								&& toDelete.getName().endsWith(".xml")) {
							toDelete.delete();
						}
					}
				}

				if (caminhoTeste.isDirectory()) {
					// efetua o Harvester e grava os xmls na pasta temporaria

					// coletando xmls
					ArrayList<String> caminhoXML = importar
							.coletaXML_ListRecords(rep.getUrl(), Operacoes
									.formatDateOAIPMH(rep.getDataOrigem()), rep
									.getNome(), caminhoDiretorioTemporario, rep
									.getMetadataPrefix(), set); // chama o
																// metodo que
																// efetua o
																// HarvesterVerb
																// grava um xml
																// em disco e
																// retorna um
																// arrayList com
																// os caminhos
																// para os XML

					// leXMLgravaBase: le do xml traduz para o padrao OBAA e
					// armazena na base de dados
					updated = gravar.saveXML(caminhoXML, rep, indexar);

				} else {
					log.error("O caminho informado nao eh um diretorio. E nao pode ser criado em: '"
							+ caminhoDiretorioTemporario + "'");
				}
			}

			rep.setUltimaAtualizacao(new Date()); // atualiza a hora da ultima
													// atualizacao
			log.info("Atualizou " + updated + " objetos");
			return updated;

		} catch (MalformedURLException m) {
			log.error("Erro na url", m);
		} catch (UnknownHostException u) {
			log.error(
					"FEB ERRO: Nao foi possivel encontrar o servidor oai-pmh informado.",
					u);
			throw u;
		} catch (SQLException e) {
			log.error(
					"FEB ERRO: SQL Exception... Erro na consulta sql na classe Repositorios.",
					e);
			throw e;
		} catch (ParserConfigurationException e) {
			log.error("FEB ERRO: O parser nao foi configurado corretamente.", e);
			throw e;
		} catch (SAXException e) {
			String msg = e.getMessage();
			String msgOAI = "\nFEB: ERRO no parser do OAI-PMH, mensagem: ";
			if (msg.equalsIgnoreCase("badArgument")) {
				log.error(msgOAI
						+ msg
						+ " - The request includes illegal arguments, is missing required arguments, includes a repeated argument, or values for arguments have an illegal syntax.\n");
			} else if (msg.equalsIgnoreCase("badResumptionToken")) {
				log.error(msgOAI
						+ msg
						+ " - The value of the resumptionToken argument is invalid or expired.\n");
			} else if (msg.equalsIgnoreCase("badVerb")) {
				log.error(msgOAI
						+ msg
						+ " - Value of the verb argument is not a legal OAI-PMH verb, the verb argument is missing, or the verb argument is repeated. \n");
			} else if (msg.equalsIgnoreCase("cannotDisseminateFormat")) {
				log.error(msgOAI
						+ msg
						+ " -  The metadata format identified by the value given for the metadataPrefix argument is not supported by the item or by the repository.\n");
			} else if (msg.equalsIgnoreCase("idDoesNotExist")) {
				log.error(msgOAI
						+ msg
						+ " - The value of the identifier argument is unknown or illegal in this repository.\n");
			} else if (msg.equalsIgnoreCase("noRecordsMatch")) {
				log.warn("FEB: "
						+ msg
						+ " - The combination of the values of the from, until, set and metadataPrefix arguments results in an empty list.\n");
				rep.setUltimaAtualizacao(new Date()); // atualiza a hora da
														// ultima atualizacao
			} else if (msg.equalsIgnoreCase("noMetadataFormats")) {
				log.error(msgOAI
						+ msg
						+ " - There are no metadata formats available for the specified item.\n");
			} else if (msg.equalsIgnoreCase("noSetHierarchy")) {
				log.error(msgOAI + msg
						+ " - The repository does not support sets.\n");
			} else {
				log.error("\nFEB ERRO: Problema ao fazer o parse do arquivo. ",
						e);
			}
			throw e;
		} catch (FileNotFoundException e) {
			System.err
					.println("\nFEB ERRO: nao foi possivel coletar os dados de: "
							+ e.toString() + "\n");
			throw e;
		} catch (IOException e) {
			log.error("\nFEB ERRO: Nao foi possivel coletar ou ler o XML em: "
					+ this.getClass() + ".", e);
			throw e;
		} catch (Exception e) {
			log.error("\nFEB ERRO ao efetuar o Harvester.", e);
			e.printStackTrace();
			throw e;
		}
		return -1;
	}

	/**
	 * Recebe um arrayList de nomes e retorna uma mensagem de erro com o nome
	 * dos reposit&oacute;rios que n&atilde;o foram atualizados.
	 * 
	 * @param nome
	 *            ArrayList<String> contendo o nome dos reposit&oacute;rios que
	 *            n&atilde;o foram atualizados.
	 * @return String com a mensagem de erro gerada.
	 */
	public static String getMensagem(ArrayList<String> nome) {
		String msg;
		if (nome.size() > 0) {
			msg = "Erro atualizar os repositorios: ";
			for (String n : nome) {
				msg += " " + n;
			}
		} else {
			msg = "Erro ao atualizar o repositorio " + nome.get(0);
		}
		return msg;
	}
}