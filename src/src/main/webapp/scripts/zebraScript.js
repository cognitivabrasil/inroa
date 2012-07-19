$(document).ready(function(){
    $('.zebraTable tbody tr').hover(function() {
        $(this).addClass('zebraHover');
    }, function() {
        $(this).removeClass('zebraHover');
    });
    
    $('.zebraTable tbody tr:even').css('background-color','#D7EBFF');
    $('.zebraTable tbody th').css('background-color','#AEC9E3');


});