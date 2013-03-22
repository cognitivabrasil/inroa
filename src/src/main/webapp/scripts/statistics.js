var _titulo;
var eixoX;
var eixoY;
var staticsUrl;
var _id;

$(function() {
    $(".btSemTexto").button({
        text: false
    });
    
    $( ".deleteTag" ).button({
        icons: {
            primary: "ui-icon-trash"
        }
    }).click(function(ev){
        
        ev.preventDefault();
        var url = $(this).attr('href');
        _this = $(this);
        
        $.post(url,"",function(resultado){
            console.log(unescape(resultado));
            if(resultado["type"]){
                if(unescape(resultado["type"])=="error"){
                    $("#textStatus").text(unescape(resultado["type"]));
                    $("#errorThrown").text(unescape(resultado["message"]));
                    $("#dialog-error").dialog('open');
                }else{
                    //ação ao excluir
                    if(_this.text() == "deletar"){
                        rmLineTag(_this);
                    }
                    else{
                        rmAllTags(_this);
                    }
                    $("#msgTagCloud").addClass(unescape(resultado["type"]))
                    .text(unescape(resultado["message"]));
                }
            }else{
                $("#errorThrown").text("Não foi possível executar a operação!");
                $("#dialog-error").dialog('open');
            }
        },"json")
        .error(function() {
            $("#errorThrown").text("Não foi possível executar a operação!");
            $("#dialog-error").dialog('open');
        });

    });
    
    var rmLineTag = function(btClick){
        console.log("deletando a linha");
        btClick.parents("tr").remove();
    };
    
    var rmAllTags = function(btClick){
        btClick.siblings("table").remove();
        btClick.remove();
    };


    $(".dataMask").change(function() {
        //chamar a função por ajax para calcular os novos valores.
        var from = $("#fromDate").val().replace(/\//g,'-');
        var until = $("#untilDate").val().replace(/\//g,'-');
        
        var urlUpdate = staticsUrl+"/updatevisits/"+from+"/"+until;
        console.log(urlUpdate);
        
        $.post(urlUpdate,"",function(resultado){
            if(resultado["type"]){
                if(unescape(resultado["type"])=="error"){
                    //mensagem de erro
                }else{
                    //ação ao excluir
                    
                    console.log("id: "+_id);
                    var array = unescape(resultado["message"]);
                    recarregarGraficoLinha(_id, [array]);
                }
            }else{
                //mensagem de erro
            }
        },"json")
        .error(function() {
            //mensagem de erro
        });
        
    });
});

function setStatisticsUrl(url){
    staticsUrl = url;
}


function recarregarGraficoLinha(id, array, titulo){
    if(!titulo){
        titulo = _titulo;
    }
    
    var idInterno = "#"+id;
    $(idInterno).empty();
    graficoLinha(id, array, titulo, eixoX, eixoY);
}
    
function graficoPizza(id, array, titulo){
    jQuery.jqplot (id, array, 
    { 
        title: {
            text: titulo,
            show: true
        },                
        seriesDefaults: {
            // Make this a pie chart.
            renderer: jQuery.jqplot.PieRenderer, 
            rendererOptions: {
                // Put data labels on the pie slices.
                // By default, labels show the percentage of the slice.
                showDataLabels: true,
                dataLabels: 'value'
            }
        }, 
        legend: {
            show:true, 
            location: 'e'
        }
    });
}

function graficoLinha(id, array, titulo, x, y){
    _titulo = titulo;
    eixoX = x;
    eixoY = y;
    _id = id;
    $.jqplot (id, array, {                    
        title: titulo,
        axesDefaults: {
            labelRenderer: $.jqplot.CanvasAxisLabelRenderer
        },
        axes: {                    
            xaxis: {
                label: x,
                pad: 0
            },
            yaxis: {
                label: y
            }
        }
    });
}