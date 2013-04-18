$(function() {

    $(".verifyUrl").each( function() {
    
        var $this = $(this);
        var url = $this.attr('href');
    
        if(url != ""){ 
            var imgTrue = "<img class='imgchecklink' src='"+rootUrl+"imagens/check.png' border='0' alt='Link correto' title='link ativo' align='middle'>";
            var imgFalse = "<img class='imgchecklink' src='"+rootUrl+"imagens/error.png' border='0' alt='Link quebrado' title='link quebrado' align='middle'>";
        
            $.getJSON(rootUrl+"/verificaURL?url="+url, "", function(resultado){   
                if(resultado){
                    $this.parent("div").append(imgTrue);
                }else if(!url.match(/.swf$/)){
                    /*
                    *Se a url terminar com .swf não coloca o erro, porque 
                    *normalmente excede o limite do timeout e da como quebrado 
                    *o link, pois efetua o download do swf.
                    */
                    $this.parent("div").append(imgFalse);
                }
            });
        }
    
    });
});

