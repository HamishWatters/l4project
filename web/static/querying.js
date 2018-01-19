$(document).ready(function() {
    var nextIdentifier = 1;
    $(".add-top-heading-btn").on("click", function() {
        $(this).parents().eq(2).append("<div class=\"nested-query-box\" data-identifier=\""+(nextIdentifier++)+"\" data-parent=\"0\"><div class=\"col-lg-6\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\" placeholder=\"Heading\"><span class=\"input-group-btn\"><button class=\"add-heading-btn btn btn-secondary\"><i class=\"fa fa-plus\"></i></button></span><span class=\"input-group-btn\"><button class=\"delete-heading-btn btn btn-secondary\"><i class=\"fa fa-minus\"></i></button></span></div></div></div>");
    });

    $(document).on("click", ".add-heading-btn", function() {
        var parent = $(this).parents('div.nested-query-box')
        $(this).parents().eq(2).append("<div class=\"nested-query-box\" data-identifier=\""+(nextIdentifier++)+"\" data-parent=\""+parent.data('identifier')+ "\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\" placeholder=\"Heading\"><span class=\"input-group-btn\"><button class=\"add-heading-btn btn btn-secondary\"><i class=\"fa fa-plus\"></i></button></span><span class=\"input-group-btn\"><button class=\"delete-nested-heading-btn btn btn-secondary\"><i class=\"fa fa-minus\"></i></button></span></div></div>");
    });

    $(document).on("click", ".delete-heading-btn", function() {
        $(this).parents().eq(3).remove();
    });
    $(document).on("click", ".delete-nested-heading-btn", function() {
        $(this).parents().eq(2).remove();
    });
    $(document).on("click", ".search-btn", function() {
        var root = $(".query-box");
        var headings = root.find(".nested-query-box");
        var headingsObject = [];
        headingsObject[0] = [$(".title").val(),"root"];
        for (i=0; i<headings.length;i++) {
            var child = headings[i]
            while (child.value == undefined && child.children != undefined) {
                child = child.children[0];
            }
            if (child.value == undefined) child = child.children[0];
            headingsObject[headings[i].getAttribute("data-identifier")] = [child.value,headings[i].getAttribute("data-parent")];
        }
        console.log(headingsObject);
    });
});
