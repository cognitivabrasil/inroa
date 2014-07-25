var _titulo;
var eixoX;
var eixoY;

var _id;

$(function() {
// $('#estatisticas:visible').hide();
    $(".btSemTexto").button({
        text: false
    });
    
    $(".deleteTag").button({
        icons: {
            primary: "ui-icon-trash"
        }
    }).click(function(ev) {
        ev.preventDefault();
        var url = $(this).attr('href');
        _this = $(this);
        $.post(url, "", function(resultado) {
            if (resultado["type"]) {
                if (unescape(resultado["type"]) == "error") {
                    $("#textStatus").text(unescape(resultado["type"]));
                    $("#errorThrown").text(unescape(resultado["message"]));
                    $("#dialog-error").dialog('open');
                } else {
//ação ao excluir
                    if (_this.text() == "deletar") {
                        rmLineTag(_this);
                    }
                    else {
                        rmAllTags(_this);
                    }
                    $("#msgTagCloud").addClass(unescape(resultado["type"]))
                            .text(unescape(resultado["message"]));
                }
            } else {
                $("#errorThrown").text("Não foi possível executar a operação!");
                $("#dialog-error").dialog('open');
            }
        }, "json")
                .error(function() {
                    $("#errorThrown").text("Não foi possível executar a operação!");
                    $("#dialog-error").dialog('open');
                });
    });
    var rmLineTag = function(btClick) {
        btClick.parents("tr").remove();
    };
    var rmAllTags = function(btClick) {
        btClick.siblings("table").remove();
        btClick.remove();
    };


});

function recarregarGraficoLinha(arrayString, titulo) {
    var array = arrayString.replace(/[\[\] ]/g, '').split(",");
    for (var i = 0; i < array.length; i++) {
        array[i] = +array[i];
    }//transforma um array de string em um array de int
    if (!titulo) {
        titulo = _titulo;
    }
    var idInterno = "#" + _id;
    $(idInterno).empty();
    graficoLinha(_id, [array], titulo, eixoX, eixoY);
}
function graficoPizza(id, array, titulo) {
    jQuery.jqplot(id, array,
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
                    show: true,
                    location: 'e'
                }
            });
}
function graficoLinha(id, array, titulo, x, y) {
    _titulo = titulo;
    eixoX = x;
    eixoY = y;
    _id = id;
    $.jqplot(id, array, {
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