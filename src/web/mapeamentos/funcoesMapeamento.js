/* 
 * Criado em Setembro de 2010 por Marcos Freitas Nunes
 *
 * Funções Utilizadas pelo mapeamento dinâmico
 */

botao='';
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
    //document.getElementById(idDivResult).innerHTML = "deu!";
    var valorAnterior = "";
    if(idMap > 0){
    
    botao = bot;
    botao.disabled=1; //bloquear o botao editar
    valorAnterior = document.getElementById(idDivResult).innerHTML
    }
    else {
        //colocar codigo novo
    }
    //processo(idDivResult, idMap, "comboBox", "", valorAnterior,"")
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
function processo(idResultado, idMap, acao, novoValor, valorAnterior, idTipoMapeamento)
{
                
                
                
    var exibeResultado = document.getElementById(idResultado);
                
    var ajax = openAjax(); // Inicia o Ajax.
                
    ajax.open("POST", "respostaAjax.jsp?tipo="+acao+"&idMap="+idMap+"&idResultado="+idResultado+"&novo="+novoValor+"&valorAnterior="+valorAnterior+"&idTipMap="+idTipoMapeamento, true); // Envia o termo da busca como uma querystring, nos possibilitando o filtro na busca.

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
 * Função que adiciona nova linha na tabela sem recarregar a p&aacute;gina
 */
function adiciona(linha){
 
    totals++
        
    var tbl = document.getElementById("tabela")
    var novaLinha = tbl.insertRow(-1);
    var novaCelula;
    var cl = "";
    var idMapeamento=0;
    var idPadraoDestino=0;
    if(totals%2==0) cl = "price-yes";

    else cl = "price-no";

    novaCelula = novaLinha.insertCell(0);
    //      novaCelula.style.backgroundColor = cl
    novaCelula.style.className = "price-yes";
    novaCelula.innerHTML = "<div class='center' id=result"+totals+">origem</div>";
    
    novaCelula.className = cl;

    novaCelula = novaLinha.insertCell(1);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = "Destino"


    novaCelula = novaLinha.insertCell(2);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = "complementar";


    novaCelula = novaLinha.insertCell(3);
    novaCelula.align = "left";
    novaCelula.className = cl;
    novaCelula.innerHTML = '<input type="button" class="BotaoMapeamento" size="30" name="salvar" id="salvar" value="Salvar" onclick="salvarNovoMapeamento("origem", "destino", "","");"/>';

    processo('result'+totals, 0, "comboBox", "", "","");
}