/**
* Desenvolvido por Marcos Freitas Nunes
* email: marcosn@gmail.com
*/

/**
* Abre uma nova janela conforme par?metros recebidos
*
* @param theURL String contendo a url que será aberta
* @param winName    String contendo o nome da janela que sera aberta
* @param features   String contendo as featues. Ex. scrollbars=yes,width=700,height=600
*/
function MM_openBrWindow(theURL,winName,features) {
    window.open(theURL,winName,features);
}

/**
* Abre uma nova janela centralizada com os parametros recebidos
*
* @param mypage		String contendo url que sera aberta
* @param myname		String contendo nome da nova janela
* @param w			width, Largura. Se informar a string 'total' ao inves do numero, abre na largura maxima da tela.
* @param h			height, Altura. Se informar a string 'total' ao inves do numero, abre na altura maxima da tela.
* @param features	String contendo as features. Nao colocar em features o height, width, top e left. Ex:scrollbars=yes,menubar=no,resizable=yes,toolbar=no,location=no,status=no
*/
function NewWindow(mypage,myname,w,h,features){

    var larguraDisponivel = screen.availWidth-9;
    var alturaDisponivel = screen.availHeight-38;

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
* Cria uma mascara em um imput conforme parametros recebidos
* ex.: <input name="CPF" id="cpf" type="text" maxlength="14" size="40" onkeypress="formatar_mascara(this, '###.###.###-##')" />
*/
function formatar_mascara(src, mascara) {
    var campo = src.value.length;
    var saida = mascara.substring(0,1);
    var texto = mascara.substring(campo);
    if(texto.substring(0,1) != saida) {
        src.value += texto.substring(0,1);
    }
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
 *Cria uma mascara no formato ip em um imput.
 *@param e event. Caracter que foi digitado.
 *@param obj imput que recebera a mascara
 */
function maskIP(e,obj){
    var evt;
    if (document.all){
        evt=event.keyCode;
    } // caso seja IE
    else{
        evt = e.charCode;
    }    // do contrário...
    if (evt <20) return true;    // liberando teclas de controle
    if ( (/^(\d{1,3}\.){3}\d{3}$/).test(obj.value) ) return false;
    var chr= String.fromCharCode(evt);    // pegando a tecla digitada
    if (! (/[\d\.]/).test(chr)) return false; // testando se é uma tecla válida (um digito ou um ponto)
    if (chr=='.')
        return (!(/\.$|^(\d{1,3}\.){3}/).test(obj.value) );
    else
    if( (/\d{3}$/).test(obj.value) )
        obj.value+='.';
    return true;
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

function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
}
