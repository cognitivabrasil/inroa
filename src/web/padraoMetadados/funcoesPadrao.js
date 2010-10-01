/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *variaveis globais
 **/
botao = '';

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
function processoAjax(idResultado, id, novoValor, valorAnterior, acao, atributo)
{

    //div onde sera adicionado o resultado
    var exibeResultado = document.getElementById(idResultado);

    var ajax = openAjax(); // Inicia o Ajax.

    ajax.open("POST", "respostaAjaxPadrao.jsp?tipo="+acao+"&idResultado="+idResultado+"&novo="+novoValor+"&valorAnterior="+valorAnterior+"&id="+id+"&atributo="+atributo, true); // Envia o termo da busca como uma querystring, nos possibilitando o filtro na busca.

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
            }
            else
            {
                exibeResultado.innerHTML = "Erro nas funções do Ajax";
            }
        }
    }
    ajax.send(null); // submete

}

