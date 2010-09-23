/* 
 * Criado em Setembro de 2010 por Marcos Freitas Nunes
 *
 * Funções Utilizadas pelo mapeamento dinâmico
 */

botao='';

function addMap(idDivOrigem, idDivDestino, padraoMetadados){
    processo(idDivDestino, 0, "comboBox", "", "","");
    processo(idDivOrigem, padraoMetadados, "comboOrigem", "", "","");

}

function removeItem(linha)
{
    var tbl = document.getElementById('tabela');
    tbl.deleteRow(linha);
}


function cancelar(idDivResult, valorAnterior)
{                
    processo(idDivResult, "", "cancelar","", valorAnterior,"")
    botao.disabled=0; //desbloquear o botao editar
}

function exibeSelect(idDivResult, idMap, idPadraoDestino, bot)
{
    botao.disabled=0;
    botao = bot;
    botao.disabled=1; //bloquear o botao editar
    var valorAnterior = document.getElementById(idDivResult).innerHTML
    
 
    processo(idDivResult, idMap, "comboBox", "", valorAnterior,"")
}

function salvarBase(idDivResultado, idMap, input, idTipoMapeamento)
{
    var novo = "";
    if(idMap>0){
        novo = document.getElementById(input).value
    }
    else{
        novo = document.getElementById(input).value
    }
    processo(idDivResultado, idMap, "salvar", novo,"",idTipoMapeamento)

    botao.disabled=0; //desbloquear o botao editar
}

function salvarNovoMapeamento(origem, destino, orgComplementar, destComplementar){
                
}

function exibeText(idDivResult, idTipoMapeamento, bot){
    botao = bot;
    botao.disabled=1; //bloquear o botao editar
    var valorAnterior = document.getElementById(idDivResult).innerHTML
    processo(idDivResult, "", "text", "", valorAnterior, idTipoMapeamento)
}
            

/**
             * Função utilizada pelo mapeamento dinamico com ajax.
             * Quando chamada, ela repassa os dados, utilizando ajax, para o arquivo jsp que rodará sem que a pagina principal seja recarregada.
             */
function processo(idResultado, idMapOuIdPadrao, acao, novoValor, valorAnterior, idTipoMapeamento)
{
                
                
                
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

totals =0;
  
function setLinha(linha){
    totals = linha-1;
}
/**
 * Função que adiciona nova linha na tabela sem recarregar a p&aacute;gina.
 * @param linha n&uacute;mero da linha na tabela.
 * @param idPadrao id do padr&atilde;o de metadados.
 */
function adiciona(linha, idPadrao){
 
    totals++
        
    var tbl = document.getElementById("tabela")
    var novaLinha = tbl.insertRow(-1);
    var novaCelula;
    var cl = "";

    if(totals%2==0) cl = "price-yes";

    else cl = "price-no";

    novaCelula = novaLinha.insertCell(0);
    novaCelula.align = "center";
    novaCelula.style.className = "price-yes";
    novaCelula.innerHTML = "<div class='center' id=result"+totals+">origem</div>";
    
    novaCelula.className = cl;

    novaCelula = novaLinha.insertCell(1);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = "<div class='center' id=destino"+totals+">destino</div>"


    novaCelula = novaLinha.insertCell(2);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = '<input type="button" class="BotaoMapeamento" size="30" name="addMap" id="addMap" value="+ Mapeamento Composto" onclick="setMapeamentoComposto(\''+totals+'\', \''+cl+'\');"/>';


    novaCelula = novaLinha.insertCell(3);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = '<input type="button" class="BotaoMapeamento" size="30" name="salvar" id="salvar" value="Salvar" onclick="salvarNovoMapeamento("origem", "destino", "","");"/>';

    addMap('result'+totals, 'destino'+totals, idPadrao); //chama a funcao que adiciona os selects por ajax
}

function setMapeamentoComposto(linha, cl){
    //adicionar linha com os divs antes.
    
    var tbl = document.getElementById("tabela")
    var novaLinha = tbl.insertRow(-1);
    var novaCelula;

    novaCelula = novaLinha.insertCell(0);
    novaCelula.align = "center";
    novaCelula.className = cl;
    novaCelula.innerHTML = "<div class='center' id=resultComplementar"+linha+">origem</div>";

    novaCelula = novaLinha.insertCell(1);
    novaCelula.align = "center";
    novaCelula.className = cl;
    novaCelula.innerHTML = "<div class='center' id=destinoComplementar"+linha+">destino</div>"

    novaCelula = novaLinha.insertCell(2);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = '';


    novaCelula = novaLinha.insertCell(3);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = '';

    processo('resultComplementar'+linha, 0, "comboBox", "", "","");
    processo('destinoComplementar'+linha, 0, "textComp", "", "","");
}