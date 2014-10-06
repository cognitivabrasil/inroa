$(function() {
    var imgTrue = "<img class='imgchecklink' src='" + rootUrl + "imagens/check.png' border='0' alt='Link correto' title='link ativo' align='middle'>";
    var imgFalse = "<img class='imgchecklink' src='" + rootUrl + "imagens/error.png' border='0' alt='Link quebrado' title='link quebrado' align='middle'>";

    var checkUrlInternal = function(url, parentNode) {
        $.getJSON(rootUrl + "verificaURL?url=" + url, "", function(resultado) {
            if (resultado) {
                parentNode.append(imgTrue);
            } else if (!url.match(/.swf$/)) {
                /*
                 *Se a url terminar com .swf não coloca o erro, porque normalmente excede
                 *o limite do timeout e da como quebrado o link, pois efetua o download do swf.
                 */
                parentNode.append(imgFalse);
            }
        });
    };

  
    $(".verifyUrl").each(function() {
        var parentDiv = $(this).parent("div");
        var url = $(this).attr('href');
        if (url) {
            $.ajax({
                dataType: "json",
                url: "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url%3D%22" + url + "%22&format=json",
                success: function(resultado) {
                    if (resultado.query.results) {
                        parentDiv.append(imgTrue);
                    } else {
                        checkUrlInternal(url,parentDiv);
                    }
                },
                timeout: 5000
            }).fail(function(xhr, status) {
                if (status == "timeout") {
                    console.log("timeout yql!");
                }
                checkUrlInternal(url,parentDiv);
            });           
        }
    });
});