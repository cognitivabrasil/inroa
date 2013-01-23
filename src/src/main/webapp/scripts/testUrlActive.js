$(function() {

    $(".verifyUrl").each( function() {
    
        var $this = $(this);
        var url = $this.attr('href');
    
        if(url != ""){
            var imgTrue = "<img class='imgchecklink' src='"+rootUrl+"imagens/check.png' border='0' alt='Link correto' align='middle'>";
            var imgFalse = "<img class='imgchecklink' src='"+rootUrl+"imagens/error.png' border='0' alt='Link quebrado' align='middle'>";
        
            $.getJSON(rootUrl+"/verificaURL?url="+url, "", function(resultado){
                console.log("resultado: "+resultado);    
                if(resultado){
                    $this.parent("div").append(imgTrue);
                }else{
                    $this.parent("div").append(imgFalse);
                }
            });
        }
    
    });
});

