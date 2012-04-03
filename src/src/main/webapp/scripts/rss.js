/**
 * Pega o valor informado para busca e envia para o rss.jsp
 */
function geraRss() {

    var consulta = document.getElementById("consulta").value;
    window.location='rss.jsp?key='+consulta;
}