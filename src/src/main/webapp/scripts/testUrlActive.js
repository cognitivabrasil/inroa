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
                }else{
                    $this.parent("div").append(imgFalse);
                }
            });
        }
    
    });
});

