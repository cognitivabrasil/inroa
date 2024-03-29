$(function () {

    /**
     * Sempre ao submeter o form será aberta a arvore para que se possa selecionar os checkbox do form.
     */
    $('form').submit(function () {
        $("#tree_federations").hide();
        $("#tree_federations").jstree('open_all');
        var arr = $("#tree_federations").jstree('get_selected');
        setChecked(arr);
    });

    $("input:checked").each(function () {
        var container = $(this).parent("li");
        var content = container.html();
        var link = $("<a></a>");
        link.prop("href", "#");
        link.addClass("jstree-clicked");
        link.html(content);
        container.html(link);
    });

    $('#tree_federations').jstree({
        "core": {
            "themes": {"icons": false,'responsive': true}
        },
        "plugins": ["checkbox"]
    });

    $('#tree_repositories').jstree({
        "core": {
            "themes": {
                "icons": false
            }
        },
        "plugins": ["themes", "checkbox"]
    });

    $('#tree_federations').on("changed.jstree", function (e, data) {
        $("#tree_federations input:checked").prop("checked", false);
        setChecked(data.selected);
    });

    $('#tree_repositories').on("changed.jstree", function (e, data) {
        $("#tree_repositories input:checked").prop("checked", false);
        setChecked(data.selected);
    });

    var setChecked = function (x) {
        for (i = 0, j = x.length; i < j; i++) {
            console.log("#" + x[i] + " input:checkbox");
            var selected = $("#" + x[i] + " input:checkbox");
            selected.prop("checked", true);
        }
    };

    $("#ageRange").slider({});
    $("#adultAge").click(function () {
        if (this.checked) {
            console.log("checked");
            $("#ageRange").slider("disable");
        }
        else {
            console.log("unchecked");
            $("#ageRange").slider("enable");
        }
    });
});
