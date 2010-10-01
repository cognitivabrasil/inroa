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
 */
function exibeText(idDivResult, idPadrao, bot){
    botao = bot;
    botao.disabled=1; //bloquear o botao editar
    var valorAnterior = document.getElementById(idDivResult).innerHTML;
    processoAjax(idDivResult, idPadrao, "",valorAnterior, "exibeText");
}

/**
 * Função utilizada pelas ferramentas de edi&ccedil;&atilde;o do padr&atilde;o de metadados com ajax.
 * Quando chamada, ela repassa os dados, utilizando ajax, para o arquivo jsp que rodará sem que a pagina principal seja recarregada.
*/
function processoAjax(idResultado, idPadrao, novoValor, valorAnterior, acao)
{

    //div onde sera adicionado o resultado
    var exibeResultado = document.getElementById(idResultado);

    var ajax = openAjax(); // Inicia o Ajax.

    ajax.open("POST", "respostaAjax.jsp?tipo="+acao+"&idMapOuIdPadrao="+idMapOuIdPadrao+"&idResultado="+idResultado+"&novo="+novoValor+"&valorAnterior="+valorAnterior+"&idTipMap="+idTipoMapeamento, true); // Envia o termo da busca como uma querystring, nos possibilitando o filtro na busca.

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
//document.getElementById("nome").value= "";//limpa os campos
//document.getElementById("nome").setFocus=true;

}

