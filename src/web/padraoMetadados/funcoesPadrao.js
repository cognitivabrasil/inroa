/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *variaveis globais
 **/
botao = "";
padraoMet = 0;
linhaJS = 1;
inputNovoAtributo = "";
idDivAtributo = "";
linhaApagar = "";


/**
 * Coloca um input text e botoes de salvar e cancelar no div que foi indicado.
 * @param idDivResult id do div onde ser&aacute; inserido o input
 * @param idPadrao id do padr&atilde;o de metadados
 * @param bot botao que ser&aacute; desativado
 * @param atributo atributo que ser&aacute; modificado
 */
function exibeText(idDivResult, idPadrao, atributo, bot){
    
    botao = bot;
    botao.disabled=1; //bloquear o botao editar
    var valorAnterior = document.getElementById(idDivResult).innerHTML;
    processoAjax(idDivResult, idPadrao, "",valorAnterior, "exibeText", atributo);
}

function exibeTextAtributo(idDivResult, idAtributo, bot){

    botao = bot;
    botao.disabled=1; //bloquear o botao editar
    var valorAnterior = document.getElementById(idDivResult).innerHTML;
    processoAjax(idDivResult, idAtributo, "",valorAnterior, "exibeTextAtributo", "");
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
    botao.disabled=0; //desbloquear o botao editar
}

function salvarBase(idDivResultado, idPadrao, idInput, atributo)
{
    var novo = "";
    novo = document.getElementById(idInput).value;

    processoAjax(idDivResultado, idPadrao, novo,"","updatePadrao", atributo);

    botao.disabled=0; //desbloquear o botao editar
}

function salvarAtributo(idDivResultado, idInput, idAtributo){
    var novo = "";
    novo = document.getElementById(idInput).value;
    processoAjax(idDivResultado, idAtributo, novo,"","updateAtributo", "");

    botao.disabled=0; //desbloquear o botao editar
}


/**
 * Função utilizada pelas ferramentas de edi&ccedil;&atilde;o do padr&atilde;o de metadados com ajax.
 * Quando chamada, ela repassa os dados, utilizando ajax, para o arquivo jsp que rodará sem que a pagina principal seja recarregada.
*/
function processoAjax(idDivResultado, id, novoValor, valorAnterior, acao, atributo)
{
    
    //div onde sera adicionado o resultado
    var exibeResultado = document.getElementById(idDivResultado);

    var ajax = openAjax(); // Inicia o Ajax.

    ajax.open("POST", "respostaAjaxPadrao.jsp?tipo="+acao+"&idResultado="+idDivResultado+"&novo="+novoValor+"&valorAnterior="+valorAnterior+"&id="+id+"&atributo="+atributo, true); // Envia o termo da busca como uma querystring, nos possibilitando o filtro na busca.

    ajax.onreadystatechange = function()
    {
        if(ajax.readyState == 1) // Quando estiver carregando, exibe: carregando...
        {
            exibeResultado.innerHTML = "Aguarde...";
        }
        if(ajax.readyState == 4) // Quando estiver tudo pronto.
        {
            if(ajax.status == 200)
            {
                var resultado = ajax.responseText;
                exibeResultado.innerHTML = resultado;
                if(acao == "deletaAtributo"){
                    removeLinha("tabela", linhaApagar);
                }else if(acao == "novoAtributo"){
                    alert("entrou");
                    document.getElementById(idDivResultado+"D").innerHTML = "";
                    //document.getElementById(idDivResultado+"D").innerHTML = "<input type=\"button\" class=\"botaoEditar\" size=\"30\" name=\"editar\" id=\"editar\"  onclick=\"exibeTextAtributo('atributo<%=linha%>', '<%=idAtributo%>', this)\"/>  <a title=\"Excluir\" onclick=\"confirmaExclusao('<%=idAtributo%>', 'msgerro', this.parentNode.parentNode.rowIndex)\"> <img src=\"../imagens/ico24_deletar.gif\" border=\"0\" width=\"24\" height=\"24\" alt=\"Excluir\" align=\"middle\"> </a>";
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
 * Seta a vari&aacute;vel global totals com a linha informada
 * @param linha n&uacute;mero da linha que deseja armazenar na vari&aacute;vel global.
 */
function setLinha(linha){
    linhaJS = linha-1;
}

/**
 * Função que adiciona nova linha na tabela sem recarregar a p&aacute;gina.
 * @param idPadrao id do padr&atilde;o de metadados.
 */
function adicionaAtributo(idPadrao){

   var tbl = document.getElementById("tabela");
   
   linhaJS++;

        var novaLinha = tbl.insertRow(-1);
        var novaCelula;
        //var cl="#006699";
        var cl="#0a94d6";
        idDivAtributo = "result"+linhaJS;

        novaCelula = novaLinha.insertCell(0);
        novaCelula.style.backgroundColor = cl
        novaCelula.innerHTML = "<div class='center' id="+idDivAtributo+"><input type='text' name='novoAtributo' id='inputNovo"+linhaJS+"' /></div>";

        novaCelula = novaLinha.insertCell(1);
        novaCelula.align = "left";
        novaCelula.style.backgroundColor = cl;
        novaCelula.innerHTML = "<div class='center' id='"+idDivAtributo+"D'><a title='Salvar' id='salvar'> <img src='../imagens/ico24_salvar.gif' border='0' width='24' height='24' alt='Salvar' align='middle'> </a>\n\
                            &nbsp;<a id='removeLinha' title='Remover Linha' onclick='removeLinha(\"tabela\", this.parentNode.parentNode.parentNode.rowIndex);'> <img src='../imagens/ico24_deletar.gif' border='0' width='24' height='24' alt='Excluir' align='middle'> </a>\n\
                            </div>";

        //setando variaveis globais que serao utilizadas pelo salvarNovoAtributo
        inputNovoAtributo = 'inputNovo'+linhaJS;
        padraoMet = idPadrao;
        var link  = document.getElementById("salvar");
        link.onclick = salvarNovoAtributo
        
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
    linhaJS--;
}

/**
 * Salva na base de dados atraves de ajax o novo atributo criado.
 * inputAtributo: id do input do novo atributo. &Eacute; passado por variavel global.
*/
function salvarNovoAtributo(){
    //pegando valores de variaveis globais
    var inputAtributo = inputNovoAtributo;
    var padrao = padraoMet;
    //fim variaveis globais

    var novoAtributo = document.getElementById(inputAtributo).value;
    processoAjax(idDivAtributo, padrao, novoAtributo,"","novoAtributo", "");
}

/**
 * Confirma se realmente deseja excluir, se sim exclui por ajax.
 * @param idAtributo id do atributo a ser excluido
 * @param linha numero da linha da tabela html que sera excluida.
 * @param idResultado id de onde ser&aacute; exibido o resultado.
 */
function confirmaExclusao(idAtributo, idResultado, linha) {
    
  if( confirm( 'Deseja realmente exluir?' ) ) {
    processoAjax(idResultado, idAtributo, "", "", "deletaAtributo", "");
    linhaApagar = linha;
  } else {

  }
}

