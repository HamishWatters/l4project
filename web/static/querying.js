$(document).ready(function()
{
    var original = $(".display").html();
    var varboxdata = [];
    var nextIdentifier = 1;
    var timestamp;
    $(document).on("click", ".add-top-heading-btn", function()
    {
        $(this).parent().append("<div class=\"nested-query-box\" data-identifier=\""+(nextIdentifier++)+"\" data-parent=\"0\"><div class=\"input-group\"><input type=\"text\" class=\"form-control valbox\" placeholder=\"Heading\"><span class=\"input-group-btn\"><button class=\"add-heading-btn btn btn-secondary\"><i class=\"fa fa-plus\"></i></button></span><span class=\"input-group-btn\"><button class=\"delete-heading-btn btn btn-secondary\"><i class=\"fa fa-minus\"></i></button></span></div></div>");
    });

    $(document).on("click", ".add-heading-btn", function()
    {
        var parent = $(this).parents('div.nested-query-box');
        $(this).parents().eq(2).append("<div class=\"nested-query-box\" data-identifier=\""+(nextIdentifier++)+"\" data-parent=\""+parent.data('identifier')+ "\"><div class=\"input-group\"><input type=\"text\" class=\"valbox form-control\" placeholder=\"Heading\"><span class=\"input-group-btn\"><button class=\"add-heading-btn btn btn-secondary\"><i class=\"fa fa-plus\"></i></button></span><span class=\"input-group-btn\"><button class=\"delete-heading-btn btn btn-secondary\"><i class=\"fa fa-minus\"></i></button></span></div></div>");
    });

    $(document).on("click", ".delete-heading-btn", function()
    {
        $(this).parents().eq(2).remove();
    });
    $(document).on("click", ".back-btn", function()
    {
        $(".display").html(original);
        var nodes = document.getElementsByClassName("valbox");
        for (var i = 0; i < nodes.length; i++ )
            nodes[i].value = varboxdata.shift();
    });
    $(document).on("click", ".search-btn", function()
    {
        timestamp = new Date().getTime();
        original = $(".display").html();
        varboxdata = [];
        var nodes = document.getElementsByClassName("valbox");
        for (var i = 0; i < nodes.length; i++ )
            varboxdata.push(nodes[i].value);
        var root = $(".query-box");
        var headings = root.find(".nested-query-box");
        var headingsObject = [];
        headingsObject[0] = [$(".title").val(),"root"];
        for (i=0; i<headings.length;i++)
        {
            var child = headings[i];
            while (child.value == undefined && child.children != undefined) {
                child = child.children[0];
            }
            if (child.value == undefined) child = child.children[0];
            headingsObject[headings[i].getAttribute("data-identifier")] = [child.value,headings[i].getAttribute("data-parent")];
        }
        var query = {};
        query["headings"] = headingsObject;
        query["model"] = $(".search-model").val()-1;
        var response = "";
        $.ajax({
            url: "query",
            type: "POST",
            data: JSON.stringify(query),
            success: function(data){
                response = data;
                $(".display").html(response);
                $(".timestamp-label").append("Results found in "+ String(new Date().getTime() - timestamp) + " milliseconds");
            }
        });
    });
});
