/**
* Desenvolvido por Marcos Freitas Nunes
* email: marcosn@gmail.com
*/

rootUrl='/feb';

/**
* Abre uma nova janela conforme par&acirc;metros recebidos
*
* @param theURL String contendo a url que será aberta
* @param winName    String contendo o nome da janela que sera aberta
* @param features   String contendo as featues. Ex. scrollbars=yes,width=700,height=600
*/
function MM_openBrWindow(theURL,winName,features) {
    window.open(theURL,winName,features);
}

/**
* Abre uma nova janela centralizada com os par&acirc;metros recebidos
*
* @param mypage		String contendo url que sera aberta
* @param myname		String contendo nome da nova janela
* @param w		width, Largura. Se informar a string 'total' ao inves do numero, abre na largura maxima da tela.
* @param h		height, Altura. Se informar a string 'total' ao inves do numero, abre na altura maxima da tela.
* @param features	String contendo as features. Nao colocar em features o height, width, top e left. Ex:scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no
*/
function NewWindow(mypage,myname,w,h,features){

    var larguraDisponivel = screen.availWidth-9;
    var alturaDisponivel = screen.availHeight-65;

    if (h=='total'){
        h=alturaDisponivel;
    }
    if (w=='total'){
        w=larguraDisponivel;
    }
    LeftPosition = (larguraDisponivel) ? (larguraDisponivel-w)/2 : 0;
    TopPosition = (alturaDisponivel) ? (alturaDisponivel-h)/2 : 0;
    settings ='height='+h+',width='+w+',top='+TopPosition+',left='+LeftPosition+','+features;
    win = window.open(mypage,myname,settings);
}

/**
 *Função que fecha a janela popup e regarrega a janela principal
 */
function fechaRecarrega() {
    // fechando a janela atual ( popup )
    window.close();
    // dando um refresh na página principal
    opener.location.href=opener.location.href;
// fim da função
}

/**
 *Função que regarrega a janela principal de onde foi aberta a popup
 */
function recarrega() {
    // dando um refresh na página principal
    opener.location.href=opener.location.href;
// fim da função
}

/**
 *Função que testa se o caracter é um digito ou não. Serve para utilizar em imput para que aceite apenas dígitos.
 *Exemplo de uso: input name="xx" type="text" onkeypress ="return ( isNumber(event) );">
 *@param e recebe um event. Caracter que foi digitado.
 */
function isNumber(e){
    var evt;
    if (document.all){
        evt=event.keyCode;
    } // caso seja IE
    else{
        evt = e.charCode;
    } // do contrário...
    if (evt <20) return true;    // liberando teclas de controle
    var chr= String.fromCharCode(evt);    // pegando a tecla digitada
    if (! (/[\d]/).test(chr)) return false; // testando se é uma tecla válida (um digito ou um ponto)
    else return true;
}


/**
 *Função responsavel por inserir um valor ao input informado
 *@param id id do input que recebera o texto como value
 *@param texto texto que será colocado como value no input informado
*/
function insereInputValue(id, texto)
{
    document.getElementById(id).value =texto;
}

/**
 *Insere um texto informado no div desejado
 *@param id id do div que receberá o texto informado
 *@param texto texto que será colocado no div informado
*/
function insereValorDiv(id, texto)
{
    document.getElementById(id).innerHTML =texto;
}

/**
 * Função que inicia o Ajax
*/
function openAjax() {

    var ajax;

    try{
        ajax = new XMLHttpRequest(); // XMLHttpRequest para Firefox, Safari, dentre outros.
    }catch(ee){
        try{
            ajax = new ActiveXObject("Msxml2.XMLHTTP"); // Para o Internet Explorer
        }catch(e){
            try{
                ajax = new ActiveXObject("Microsoft.XMLHTTP"); // Para o Internet Explorer
            }catch(E){
                ajax = false;
            }
        }
    }
    return ajax;
}

/**
 * Remove os espaços de antes de depois da string. Igual ao trim() do java.
 * @param str string que sera retornada sem os espaços antes e depois.
 */
function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
}

/**
 * Função que atualiza o repositorio solicitado utilizando ajax.
 * Quando chamada, ela repassa os dados, utilizando ajax, para o arquivo jsp que rodará sem que a pagina principal seja recarregada.
 * @param id id do repositorio a ser atualizado
 * @param exibeResultado div onde o status e o resultado ser&atilde;o apresentado.
*/
function atualizaRepAjax(id, exibeResultado)
{
    var link = rootUrl + "/admin/repositories/"+id+"/update?apagar=false";
    updateSubnodeAjax(link, exibeResultado);
}

/**
 * Função que remove todos os objetos de um repositorio e atualiza novamente utilizando ajax.
 * Quando chamada, ela repassa os dados, utilizando ajax, para o arquivo jsp que rodará sem que a pagina principal seja recarregada.
 * @param id id do repositorio a ser atualizado
 * @param exibeResultado div onde o status e o resultado ser&atilde;o apresentado.
*/
function apagaAtualizaRepAjax(id, exibeResultado)
{
    var link = rootUrl + "/admin/repositories/"+id+"/update?apagar=true";
    updateSubnodeAjax(link, exibeResultado);
}

/**
 * pt: Deleta todos os documentos da federação e atualiza novamente.
 * en: Deletes all documents of federation and updates again.
 * @param id int id of federation
 * @param showResult div html where the status and results will be presented
 **/
function deleteAndUpdateSubFedAjax(id, showResult){
    var link = rootUrl + "/admin/federations/"+id+"/update?apagar=true";
    updateSubnodeAjax(link, showResult);
}

/**
 * Função que atualiza a subfedera&ccedil;&atilde;o solicitada utilizando ajax.
 * Quando chamada, ela repassa os dados, utilizando ajax, para o arquivo jsp que rodará sem que a pagina principal seja recarregada.
 * @param id id da subfedera&ccedil;&atilde;o a ser atualizada
 * @param exibeResultado div onde o status e o resultado ser&atilde;o apresentado.
*/
function atualizaSubfedAjax(id, exibeResultado)
{
    var link = rootUrl + "/admin/federations/"+id+"/update?apagar=false";
    updateSubnodeAjax(link, exibeResultado);
}

/**
 * Função que atualiza o subNodo solicitado utilizando ajax.
 * Quando chamada, ela repassa os dados, utilizando ajax, para o controller que rodar&aacute; sem que a pagina principal seja recarregada.
 * @param link link para o controller que deverá ser chamado
 * @param showResults div onde o status e o resultado ser&atilde;o apresentado.
*/
function updateSubnodeAjax(link, showResults)
{
    var ajax = openAjax(); // Start Ajax.

    ajax.open("POST", link, true);
    showResults.innerHTML = "<img src='"+rootUrl+"/imagens/ajax-loader.gif' border='0' alt='Atualizando' align='middle'> Aguarde, atualizando...";
    ajax.onreadystatechange = function()
    {
        if(ajax.readyState == 4) // Quando estiver tudo pronto.
        {
            if(ajax.status == 200)
            {
                var resultado = ajax.responseText;

                if(isNaN(parseInt(resultado))){
                    showResults.innerHTML = resultado;
                    showResults.className = "textoErro";
                }else{
                    if(parseInt(resultado)>0){
                        showResults.innerHTML = "Atualizado com sucesso!";
                        showResults.className="sucesso";
                    }
                    else{
                        showResults.innerHTML = "Ocorreu algum erro ao Atualizar!";
                        showResults.className = "textoErro";                        
                    }
                }
            }
            else
            {
                showResults.innerHTML = "Erro nas funções do Ajax";
                showResults.className = "textoErro";
            }
        }
    }
    ajax.send(null); // submete
}


/**
 * Verifica a consistencia do link OAI-PMH
 * @param link link OAI-PMH que ser&aacute; validado
 * @param inputTexto input text onde o link foi escrito
 * @param divErro div onde o status e o resultado ser&atilde;o apresentado.
 * @param inputHidden input que receber&aacute; um valor se o link for v&aacute;lido.
*/
function verificaLinkOAI(link, inputTexto, divErro, inputHidden)
{
    link=link.trim();
    if(link==""){
        inputTexto.className='bordaVermelha';
        divErro.innerHTML = "Link inv&aacute;lido!";
        divErro.className='linkCantoDireito textoErro';
        inputHidden.value='';   
    }else{
        var ajax = openAjax(); // Inicia o Ajax.

        ajax.open("GET", rootUrl+"/admin/VerificaLinkOAI?"+link, true);
        divErro.innerHTML = "<img src='/feb/imagens/ajax-loader.gif' border='0' alt='Verificando' align='middle'>";
        ajax.onreadystatechange = function()
        {
            if(ajax.readyState == 4) // Quando estiver tudo pronto.
            {
                if(ajax.status == 200)
                {
                    var resultado = ajax.responseText;
                    resultado = trim(resultado);
                
                    if(resultado=='true')
                    {
                        inputTexto.className='bordaVerde';
                        divErro.className='linkCantoDireito';
                        divErro.innerHTML='';
                        inputHidden.value='ok';
                    }
                    else{
                        inputTexto.className='bordaVermelha';
                        divErro.innerHTML = "Link inv&aacute;lido!";
                        divErro.className='linkCantoDireito textoErro';
                        inputHidden.value='';
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
}

/**
 * Chama a verificaLinkOAI() caso o link tenha sido preenchido no formum&aacute;rio
 * @param inputTexto o input de onde o link ser&aacute; coletado
 * @param divErro div onde o status e o resultado ser&atilde;o apresentado.
 * @param inputHidden input que receber&aacute; um valor se o link for v&aacute;lido.
 */
function verificaLinkOnLoad (inputTexto, divErro, inputHidden){  
    link = inputTexto.value;
    if(link!=""){
        verificaLinkOAI(link, inputTexto, divErro, inputHidden);
    }
}
/**
 * Esta função é utilizada para chamar a função selecionaMapeamento() quando o padrao de metadados recebido como parâmetro tiver algum valor
 * @param idPadraoSelecionado id do Padrao de metadados selecionado no form
 * @param mapeamentoSelecionado id do mapeamento selecionado no form
 * @param idResultado id do div que recebera os resultados do ajax.
 */
function verificaMapOnLoad (idPadraoSelecionado, mapeamentoSelecionado, idResultado){
    if(idPadraoSelecionado!=""){
        if(mapeamentoSelecionado==""){
            mapeamentoSelecionado=0;
        }
        selecionaMapeamento(idResultado, idPadraoSelecionado, mapeamentoSelecionado);
    }    
}

/**
 * Quando chamada adiciona no div informado um input password com o nome "senhaCriptografada" e o id "senhabd". Muda o css class do div que contem a repetição de senha e apaga o seu valor.
 * @param idSenha div onde será colocado o input
 * @param idRepSenha div onde está o input para repetir a senha
 */
function exibeDivSenha(idSenha, idRepSenha)
{
    idSenha.className='hidden';
    idRepSenha.className='LinhaEntrada';
    document.getElementById("confsenhabd").value="";
}

/**
 * Confirma se realmente deseja excluir, se sim chama o m&eacute;todo que exclui por ajax.
 * @param id id do objeto a ser excluido
 * @param idResultado id de onde ser&aacute; exibido o resultado.
 * @param idTabela id da tabela html que a linha sera excluida
 * @param linha numero da linha que sera excluida
 */
function confirmaDelPadrao(id, idResultado, idTabela, linha) {
    if( confirm( 'Deseja realmente exluir?' ) ) {
        excluirPadrao(id, idResultado, idTabela, linha);
    } else {
    
    }
}

/**
 * Utilizada para exluir padr&otilde;es de metadados na base utilizando ajax
 * @param id id do padr&atilde;o a ser exclu&iacute;do
 * @param idResultado id de onde ser&aacute; exibido o resultado.
 * @param idTabela id da tabela html que a linha sera excluida
 * @param linha numero da linha que sera excluida
 */
function excluirPadrao(id, idResultado, idTabela, linha)
{
    
    //div onde sera adicionado o resultado
    var exibeResultado = document.getElementById(idResultado);
    
    var ajax = openAjax(); // Inicia o Ajax.
    
    
    ajax.open("POST", "excluirPadrao?id="+id, true);
                       
    exibeResultado.innerHTML = "Aguarde...";
    ajax.onreadystatechange = function()
    {
        if(ajax.readyState == 4) // Quando estiver tudo pronto.
        {
            if(ajax.status == 200)
            {
                var resultado = ajax.responseText;
                //exibeResultado.innerHTML = resultado;
                if(isNaN(parseInt(resultado))){
                    exibeResultado.innerHTML = resultado;
                }else{
                    if(parseInt(resultado)>0){
                        exibeResultado.innerHTML = "Exclu&iacute;do com sucesso.";
                        removeLinha(idTabela, linha);
                    }
                    else{
                        exibeResultado.innerHTML = "Ocorreu algum erro ao excluir da base de dados.";
                    }
                }                    
            }
            else
            {
                exibeResultado.innerHTML = "Ocorreu algum erro ao excluir da base de dados.";
            }
        }
    }
    ajax.send(null); // submete
}

function recalcularIndice(destino, form){
    destinodoc = document.getElementById(destino);
    destinodoc.innerHTML = "<center><img src='./feb/imagens/ajax-loader.gif' border='0' alt='Atualizando' align='middle'> O &iacute;ndice est&aacute; sendo recalculado, por favor aguarde! <center>" ;
    form.className="hidden";
    
}

function objectAcessCounter(id){
    
    var ajax = openAjax(); // Start Ajax.
    var link = rootUrl + "/admin/statistics/objUser?id="+id;    
    ajax.open("POST", link, true);
    ajax.send(null); // submete
    
}

function paranaodarerro(){
//alguns navegadores excluem a ultima funcao.
}