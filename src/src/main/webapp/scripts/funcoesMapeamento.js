/* 
 * Criado em Setembro de 2010 por Marcos Freitas Nunes
 *
 * Funcoes utilizadas pelo mapeamento dinamico
 */
botao = '';
linhaComposto = 0;

idOrigem = '';
idDestino = '';
idOrigemComp = '';
idDestinoComp = '';
padraoMet = '';
tipoMapeamento = '';
totals = 0;

function addMap(idDivOrigem, idDivDestino, padraoMetadados, tipoMap) {
    processo(idDivDestino, 0, "comboBox", "", "", "");
    processo(idDivOrigem, padraoMetadados, "comboOrigem", "", "", "");

    var link = document.getElementById("salvar");
    idOrigem = 'atribOrigem';
    idDestino = 'atrb' + idDivDestino;
    padraoMet = padraoMetadados;
    tipoMapeamento = tipoMap;
    idOrigemComp = '';
    idDestinoComp = '';
    link.onclick = salvarNovoMapeamento;

}

/**
 * Salva na base de dados atraves de ajax o novo mapeamento criado.
 * inputOrigem: id do input da origem. &Eacute; passado por variavel global.
 * inputDestino: id do input do destino. &Eacute; passado por variavel global.
 * inpOrgigemComplementar: id do input do mapeamento complementar origem. &Eacute; passado por variavel global.
 * inpDestinoComplementar: id do input do mapeamento complementar destino. &Eacute; passado por variavel global.
 */
function salvarNovoMapeamento() {
    //pegando valores de variaveis globais
    var inputOrigem = idOrigem;
    var inputDestino = idDestino;
    var inputOrigemComplementar = idOrigemComp;
    var inputDestinoComplementar = idDestinoComp;
    var padrao = padraoMet;
    var tipoMap = tipoMapeamento;
    //fim variaveis globais

    var origem = document.getElementById(inputOrigem).value;
    var destino = document.getElementById(inputDestino).value;
    var compOrigem = '';
    var compDestino = '';
    if (!inputOrigemComplementar == '') {
        compOrigem = document.getElementById(inputOrigemComplementar).value;
        compDestino = document.getElementById(inputDestinoComplementar).value;
    }
    salvarAjax('result' + totals, origem, destino, compOrigem, compDestino, padrao, tipoMap);
}

/**
 * Remove linha da tabela cujo id &eacute; 'tabela'
 * @param linha linha a ser removida
 */
function removeItem(linha)
{
    var tbl = document.getElementById('tabela');
    tbl.deleteRow(linha);
}

/**
 * Remove o que tiver no div e coloca o que for informado como valor anterior
 * @param idDivResult id do div que ter&aacute; seu valor substituido
 * @param valorAnterior valor que ser&aacute; colocado no div
 */
function cancelar(idDivResult, valorAnterior)
{
    var exibeResultado = document.getElementById(idDivResult);
    exibeResultado.innerHTML = valorAnterior;
    botao.disabled = 0; //desbloquear o botao editar
}

function exibeSelect(idDivResult, idMap, bot)
{
    botao.disabled = 0;
    botao = bot;
    botao.disabled = 1; //bloquear o botao editar
    var valorAnterior = document.getElementById(idDivResult).innerHTML


    processo(idDivResult, idMap, "comboBox", "", valorAnterior, "");
}

function salvarBase(idDivResultado, idMap, input, idTipoMapeamento)
{
    var novo = "";
    novo = document.getElementById(input).value

    processo(idDivResultado, idMap, "salvar", novo, "", idTipoMapeamento)

    botao.disabled = 0; //desbloquear o botao editar
}

function novoTipos(div) {

    document.getElementById(div).innerHTML = "";
}

function exibeText(idDivResult, idTipoMapeamento, bot) {
    botao = bot;
    botao.disabled = 1; //bloquear o botao editar
    var valorAnterior = document.getElementById(idDivResult).innerHTML
    processo(idDivResult, "", "text", "", valorAnterior, idTipoMapeamento)
}

/**
 * Apaga todos os atributos da tabela mapeamentos cujo, destino_id &eacute; zero.
 */
function apagarMapeamentoBranco() {
    processo("msgerro", "", "apagarBranco", "", "", "");
// fechando a janela atual ( popup )
//window.close();

}

/**
 * Seta a vari&aacute;vel global totals com a linha informada
 * @param linha n&uacute;mero da linha que deseja armazenar na vari&aacute;vel global.
 */
function setLinha(linha) {
    totals = linha - 1;
}
/**
 * Função que adiciona nova linha na tabela sem recarregar a p&aacute;gina.
 * @param linhaReal n&uacute;mero da linha na tabela, contando todas as linhas da tabela.
 * @param idPadrao id do padr&atilde;o de metadados.
 * @param tipoMap id do tipomapeamento
 */
function adicionaMap(linhaReal, idPadrao, tipoMap) {
    totals++

    var tbl = document.getElementById("tabela")
    var novaLinha = tbl.insertRow(-1);
    var novaCelula;
    var cl = "";

    if (totals % 2 == 0)
        cl = "price-yes";

    else
        cl = "price-no";

    novaCelula = novaLinha.insertCell(0);
    novaCelula.align = "center";
    novaCelula.style.className = "price-yes";
    novaCelula.innerHTML = "<div class='center' id=result" + totals + ">Javascript n&atilde;o est&aacute; funcionando</div>";

    novaCelula.className = cl;

    novaCelula = novaLinha.insertCell(1);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = "<div class='center' id=destino" + totals + ">Javascript n&atilde;o est&aacute; funcionando</div>"


    novaCelula = novaLinha.insertCell(2);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = '<input type="button" class="BotaoMapeamento" size="30" name="addMap" id="addMap" value="+ Mapeamento Complementar" onclick="setMapeamentoComposto(\'' + linhaReal + '\', \'' + cl + '\');"/>';


    novaCelula = novaLinha.insertCell(3);
    novaCelula.align = "left";
    novaCelula.className = cl;
    //salvarNovoMapeamento(\'origem\', \'destino\', \'\',\'\');
    novaCelula.innerHTML = '<a title="Salvar" id="salvar"> \n\
                                <img src="../imagens/ico24_salvar.gif" border="0" width="24" height="24" alt="Salvar" align="middle">\n\
                            </a>&nbsp;\n\
                            <a id="removeLinha" title="Remover Linha" onclick="removeLinha(\'tabela\', this.parentNode.parentNode.rowIndex);">\n\
                                <img src="../imagens/ico24_deletar.gif" border="0" width="24" height="24" alt="Excluir" align="middle">\n\
                            </a>';

    addMap('result' + totals, 'destino' + totals, idPadrao, tipoMap); //chama a funcao que adiciona os selects por ajax
}

/**
 * Adiciona uma linha na tabela para informar o mapeamento composto
 * @param linha numero da linha que será inserido
 * @param cl cor da linha. Deve ser passado uma class do css.
 */
function setMapeamentoComposto(linha, cl) {
    //adicionar linha com os divs antes.
    linhaComposto = linha;
    linhaComposto++;
    var tbl = document.getElementById("tabela")
    var novaLinha = tbl.insertRow(-1);
    var novaCelula;

    novaCelula = novaLinha.insertCell(0);
    novaCelula.align = "center";
    novaCelula.className = cl;
    novaCelula.innerHTML = "<div class='center' id=resultComplementar" + linha + ">origem</div>";

    novaCelula = novaLinha.insertCell(1);
    novaCelula.align = "center";
    novaCelula.className = cl;
    novaCelula.innerHTML = "<div class='center' id=destinoComplementar" + linha + ">destino</div>"

    novaCelula = novaLinha.insertCell(2);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = '';


    novaCelula = novaLinha.insertCell(3);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = '';

    processo('resultComplementar' + linha, 0, "comboBox", "", "", "");
    processo('destinoComplementar' + linha, 0, "textComp", "", "", "");
    var link = document.getElementById("salvar");

    idOrigemComp = 'atrbresultComplementar' + linha;
    idDestinoComp = 'geral';
    link.onclick = salvarNovoMapeamento
}

/**
 * Remove a linha complementar que tinha sido adicionada.
 */
function removeComplementar()
{
    var tbl = document.getElementById('tabela');
    tbl.deleteRow(linhaComposto);
    idOrigemComp = '';
    idDestinoComp = '';
}
/**
 * Remove uma linha de uma tabela especificada
 * @param nomeTabela nome da tabela que ter&aacute; a linha exclu&iacute;da
 * @param linha n&uacute;mero da linha a ser exclu&iacute;da
 */
function removeLinha(nomeTabela, linha)
{
    var tbl = document.getElementById(nomeTabela);
    tbl.deleteRow(linha);
    totals--;
}

/**
 * Função utilizada pelo mapeamento dinamico com ajax.
 * Quando chamada, ela repassa os dados, utilizando ajax, para o arquivo jsp que rodará sem que a pagina principal seja recarregada.
 */
function processo(idResultado, idMapOuIdPadrao, acao, novoValor, valorAnterior, idTipoMapeamento)
{

    //div onde sera adicionado o resultado
    var exibeResultado = document.getElementById(idResultado);

    var ajax = openAjax(); // Inicia o Ajax.

    ajax.open("POST", "respostaAjax.jsp?tipo=" + acao + "&idMapOuIdPadrao=" + idMapOuIdPadrao + "&idResultado=" + idResultado + "&novo=" + novoValor + "&valorAnterior=" + valorAnterior + "&idTipMap=" + idTipoMapeamento, true); // Envia o termo da busca como uma querystring, nos possibilitando o filtro na busca.

    ajax.onreadystatechange = function ()
    {
        if (ajax.readyState == 1) // Quando estiver carregando, exibe: carregando...
        {
            exibeResultado.innerHTML = "Aguarde...";
        }
        if (ajax.readyState == 4) // Quando estiver tudo pronto.
        {
            if (ajax.status == 200)
            {
                var resultado = ajax.responseText;
                exibeResultado.innerHTML = resultado;
            }
            else
            {
                exibeResultado.innerHTML = "Erro nas funções do Ajax";
            }
        }
    }
    ajax.send(null); // submete
//document.getElementById("nome").value= "";//limpa os campos
//document.getElementById("nome").setFocus=true;

}


/**
 * Utilizada para salvar mapeamentos na base utilizando ajax
 * @param idResultado id do div onde ser&aacute; colocado o resultado do ajax
 * @param origem id da origem a ser salva, deve ser o identificador do atributo na base de dados.
 * @param destino id do destino a ser salvo, deve ser o identificador do atributo na base de dados.
 * @param origemComplementar id da origem complementar, mapeamento complementar, a ser salva, deve ser o identificador do atributo na base de dados.
 * @param destinoComplementar id do destino complementar, mapeamento complementar, a ser salvo, deve ser o identificador do atributo na base de dados.
 * @param padrao id do padr&atilde;o de metadados
 * @param tipoMap id do tipo do mapeamento
 */
function salvarAjax(idResultado, origem, destino, origemComplementar, destinoComplementar, padrao, tipoMap)
{
    //div onde sera adicionado o resultado
    var exibeResultado = document.getElementById(idResultado);

    var ajax = openAjax(); // Inicia o Ajax.

    ajax.open("POST", "salvarBaseAjax.jsp?origem=" + origem + "&destino=" + destino + "&origemcomplementar=" + origemComplementar + "&destinocomplementar=" + destinoComplementar + "&padrao=" + padrao + "&tipoMap=" + tipoMap, true); // Envia o termo da busca como uma querystring, nos possibilitando o filtro na busca.

    ajax.onreadystatechange = function ()
    {
        if (ajax.readyState == 1) // Quando estiver carregando, exibe: carregando...
        {
            exibeResultado.innerHTML = "Aguarde...";
        }
        if (ajax.readyState == 4) // Quando estiver tudo pronto.
        {
            if (ajax.status == 200)
            {
                var resultado = ajax.responseText;
                //exibeResultado.innerHTML = resultado;
                if (isNaN(parseInt(resultado))) {
                    alert("Erro ao salvar o mapeamento na base de dados. Verifique o log.");
                } else {
                    alert("Salvo!");
                }
                document.location.reload();

                //exibeResultado.innerHTML = "<p class='textoErro'>Erro. N&atilde;o foram informados todos os valores. Ou ocorreu erro com a conexao postgre</p>";
            }
            else
            {
                exibeResultado.innerHTML = "Erro nas funções do Ajax";
            }
        }
    }
    ajax.send(null); // submete


}

/**
 * Confirma se realmente deseja excluir, se sim exclui por ajax.
 * @param id id do objeto a ser excluido
 * @param tabelaBase tabela de onde ser&aacute; exclu&iacute;do.
 * @param idResultado id de onde ser&aacute; exibido o resultado.
 * @param idTabela id da tabela html que a linha sera excluida
 * @param linha numero da linha que sera excluida
 */
function confirmaExclusao(id, tabelaBase, idResultado, idTabela, linha) {
    if (confirm('Deseja realmente exluir?')) {
        excluirAjax(id, tabelaBase, idResultado, idTabela, linha);
    } else {

    }
}

/**
 * Utilizada para exluir mapeamentos na base utilizando ajax
 * @param idMapeamento id do mapeamento a ser excluido
 * @param tabela tabela de onde ser&aacute; exclu&iacute;do.
 * @param idResultado id de onde ser&aacute; exibido o resultado.
 * @param idTabela id da tabela html que a linha sera excluida
 * @param linha numero da linha que sera excluida
 */
function excluirAjax(idMapeamento, tabela, idResultado, idTabela, linha)
{

    //div onde sera adicionado o resultado
    var exibeResultado = document.getElementById(idResultado);

    var ajax = openAjax(); // Inicia o Ajax.

    var gambi = "";
    if (tabela == "padraometadados") {
        gambi = "mapeamentos/";
    }
    ajax.open("POST", gambi + "deletaBaseAjax.jsp?idmap=" + idMapeamento + "&tabela=" + tabela, true); // Envia o termo da busca como uma querystring, nos possibilitando o filtro na busca.


    ajax.onreadystatechange = function ()
    {
        if (ajax.readyState == 1) // Quando estiver carregando, exibe: carregando...
        {
            exibeResultado.innerHTML = "Aguarde...";
        }
        if (ajax.readyState == 4) // Quando estiver tudo pronto.
        {
            if (ajax.status == 200)
            {
                var resultado = ajax.responseText;
                //exibeResultado.innerHTML = resultado;
                if (isNaN(parseInt(resultado))) {
                    exibeResultado.innerHTML = resultado;
                } else {
                    if (parseInt(resultado) > 0) {
                        exibeResultado.innerHTML = "Exclu&iacute;do com sucesso.";
                        removeLinha(idTabela, linha);
                    }
                    else {
                        exibeResultado.innerHTML = "Ocorreu algum erro ao excluir da base de dados.";
                    }
                }
            }
            else
            {
                exibeResultado.innerHTML = "Erro nas funções do Ajax";
            }
        }
    }
    ajax.send(null); // submete


}

/**
 * Função que preenche a descricao do mapeamento utilizando ajax para consultar na base de dados.
 * @param id id do t do mapeamento
 * @param idDiv div onde o status e o resultado ser&atilde;o apresentado.
 */
function preencheDescricaoTM(id, idDiv)
{

    var exibeResultado = document.getElementById(idDiv);
    var ajax = openAjax(); // Inicia o Ajax.

    ajax.open("POST", "consultadescricao.jsp?id=" + id, true); // Envia o termo da busca como uma querystring

    ajax.onreadystatechange = function ()
    {
        if (ajax.readyState == 1) // Quando estiver carregando, exibe: carregando...
        {
            exibeResultado.innerHTML = "Aguarde...";
        }
        if (ajax.readyState == 4) // Quando estiver tudo pronto.
        {
            if (ajax.status == 200)
            {
                var resultado = ajax.responseText;
                exibeResultado.innerHTML = resultado;
            }
            else
            {
                exibeResultado.innerHTML = "Erro nas funções do Ajax";
            }
        }
    }
    ajax.send(null); // submete

}