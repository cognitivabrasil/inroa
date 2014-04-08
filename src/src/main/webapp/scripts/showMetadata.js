$(function() {

    $("#documentTree").bind("loaded.jstree", function(event, data) {
        data.instance.open_all();
    });

    $("#documentTree").jstree({
        'core': {
            'themes': {
                "theme": "apple",
                "icons": true,
                "stripes": true
            },
            'data': obaaJson
        },
        "types": {
            "#": {
                "max_children": 1,
                "max_depth": 4,
                "valid_children": ["root"]
            },
            "root": {
                "icon": "/static/3.0.0-beta10/assets/images/tree_icon.png",
                "valid_children": ["default"]
            },
            "default": {
                "valid_children": ["default", "file"]
            },
            "file": {
                "icon": "glyphicon glyphicon-file",
                "valid_children": []
            }
        },
        "plugins": ["wholerow", "types"]
    });


    $('#jstree_demo')
            .jstree({
                "core": {
                    "animation": 0,
                    "check_callback": true,
                    "themes": {"stripes": true},
                    'data': obaaJson
                },
                "types": {
                    "#": {"max_children": 1, "max_depth": 4, "valid_children": ["root"]},
                    "root": {"icon": "http://www.jstree.com/static/3.0.0-beta10/assets/images/tree_icon.png", "valid_children": ["default"]},
                    "default": {"valid_children": ["default", "file"]},
                    "file": {"icon": "glyphicon glyphicon-file", "valid_children": []}
                },
                "plugins": ["state", "types", "wholerow"]
            });

});