$(document).ready(function() {
    $(".add-top-heading-btn").on("click", function() {console.log("Hello");
        $(this).parents().eq(2).append("<div class=\"nested-query-box\"><div class=\"col-lg-6\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\" placeholder=\"Heading\"><span class=\"input-group-btn\"><button class=\"add-heading-btn btn btn-secondary\"><i class=\"fa fa-plus\"></i></button></span><span class=\"input-group-btn\"><button class=\"delete-heading-btn btn btn-secondary\"><i class=\"fa fa-minus\"></i></button></span></div></div></div>");
    });

    $(document).on("click", ".add-heading-btn", function() {
        $(this).parents().eq(2).append("<div class=\"nested-query-box\"><div class=\"input-group\"><input type=\"text\" class=\"form-control\" placeholder=\"Heading\"><span class=\"input-group-btn\"><button class=\"add-heading-btn btn btn-secondary\"><i class=\"fa fa-plus\"></i></button></span><span class=\"input-group-btn\"><button class=\"delete-nested-heading-btn btn btn-secondary\"><i class=\"fa fa-minus\"></i></button></span></div></div>");
    });

    $(document).on("click", ".delete-heading-btn", function() {
        $(this).parents().eq(3).remove();
    });
    $(document).on("click", ".delete-nested-heading-btn", function() {
        $(this).parents().eq(2).remove();
    });
});
