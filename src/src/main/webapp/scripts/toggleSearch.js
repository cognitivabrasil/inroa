$(function () {
    var toggle = function (){
        if ($(window).width() < 768) {
            $('.searchBox').removeClass('input-group');
        } else {
            $('.searchBox').addClass('input-group');
        }
    }
    
    toggle();
    
    $(window).resize(function () {
        toggle();
    });
});