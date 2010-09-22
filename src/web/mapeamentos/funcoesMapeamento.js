/* 
 * Criado em Setembro de 2010 por Marcos Freitas Nunes
 *
 * Funções Utilizadas pelo mapeamento dinâmico
 */


  totals =0;
/**
 * Função que adiciona nova linha na tabela sem recarregar a p&aacute;gina
 */
  function adiciona(){

  totals++

      var tbl = document.getElementById("tabela")
      var novaLinha = tbl.insertRow(-1);
      var novaCelula;

      if(totals%2==0) cl = "#F5E9EC";

      else cl = "#FBF6F7";

      novaCelula = novaLinha.insertCell(0);
      novaCelula.style.backgroundColor = cl
      novaCelula.innerHTML = "<input type='checkbox' name='chkt' />";
      //novaCelula.className = "";

     novaCelula = novaLinha.insertCell(1);
      novaCelula.align = "left";
      novaCelula.style.backgroundColor = cl;
      novaCelula.innerHTML = "&nbsp; Linha"+totals+"";


      novaCelula = novaLinha.insertCell(2);
      novaCelula.align = "left";
      novaCelula.style.backgroundColor = cl;
      novaCelula.innerHTML = "&nbsp;ops3";


      novaCelula = novaLinha.insertCell(3);
      novaCelula.align = "left";
      novaCelula.style.backgroundColor =cl;
      novaCelula.innerHTML = "&nbsp;ops4";

  }