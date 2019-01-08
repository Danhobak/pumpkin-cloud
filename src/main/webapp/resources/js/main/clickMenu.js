/**
 * 
 */

$(document).bind("contextmenu", function(event) { 
    event.preventDefault();
    $("<div class='custom-menu'>Custom menu</div>")
        .appendTo("body")
        .css({top: event.pageY + "px", left: event.pageX + "px"});
}).bind("click", function(event) {
    $("div.custom-menu").hide();
});
