var path = null;
var separator = "";
var parentPath = "";

function queryServer(path) {
    $.ajax({
        type: "get",
        url: "directory",
        data: {path: path},
        success: function (result) {
            // $("#fmanager_panel > tr").remove();
            $("table").find("tr:gt(0)").remove();
            fillTable(result);
        }
    });
}

function fillTable(result) {
    if (result.path !== null) {
        path = result.path;
        separator = result.separator;
        parentPath = result.parentPath;


        $("caption").text("Current directory: " + path);
        $("table").append(appendParentRow(result));

        var contents = result.contents;
        if (contents) {
            $.each(contents, function (i, file) {
                $("table").append(appendFileRow(file));
            });
        }

        // $("table").after(addUpload(result));
    }
}

function appendParentRow(result) {
    var isChild = result.isChild;
    if (isChild === "true") {
        var rootCell = $("<a></a>").attr("href", "/directory?path=" + result.parentPath);
        rootCell.attr("class", "parentPath");
        var $href = rootCell.text("..");
        var col2 = addTdTag("col_name");
        col2.append($href);
        return $("<tr></tr>").append(addTdTag("col_type"), col2, addTdTag("col_size"), addTdTag("col_download"));
    }
    return "";
}

function addTdTag(attributeName) {
    return $("<td></td>").attr("class", attributeName);
}

function addUpload(result) {
    var $form = "";
    var $inputFile = $("<input>").attr({
        type: 'file',
        name: "file",
        id: "file"
    });
    var $inputPath = $('<input>').attr({
        type: 'hidden',
        name: "path",
        value: path
    });
    var $inputUpload = $('<input>').attr({
        type: 'submit',
        name: "upload",
        value: "Upload",
        id: "upload"
    });
    $form = $("<form></form>").attr("method", "POST")
        .attr("action", "/upload")
        .attr("enctype", "multipart/form-data");
    return $form.append($inputFile, $inputPath, $inputUpload);
}

function appendFileRow(file) {
    var isDirectory = file.isDirectory;
    var td_col1 = $("<td></td>").attr("class", "col_type");
    var col1 = td_col1.text(isDirectory ? "-" : "+");

    var $fileName = "";
    if (isDirectory) {
        var filePath = file.relativePath + separator + file.name;
        $fileName = $("<p></p>")//.attr("href", "/directory?path=" + filePath)
            .attr("title", file.relativePath + separator + file.name)
            .attr("class", "childPath")
            .attr("onclick", "queryServer(\"" + filePath + "\")")
            .text(file.name);
    }
    if (file.isFile) {
        $fileName = $("<p></p>").attr("id", "childPath").text(file.name);
    }
    var $td_col2 = $("<td></td>").attr("class", "col_name");
    var col2 = $td_col2.append($fileName);


    var col3 = $("<td></td>").attr("class", "col_size").text(file.size);

    var $form = "";
    if (file.isFile) {
        var $inputPath = $("<input>").attr({
            type: 'hidden',
            name: "path",
            value: path
        });
        var $inputName = $('<input>').attr({
            type: 'hidden',
            name: "fileName",
            value: file.name
        });
        var $inputButton = $('<input>').attr({
            type: 'submit',
            name: "download",
            value: "Download",
            id: "download"
        });
        $form = $("<form></form>").attr("method", "POST")
            .attr("action", "/download")
            .attr("class", "download");
        $form.append($inputPath, $inputName, $inputButton);
    }
    var $td_col4 = $("<td></td>").attr("class", "col_download");
    var col4 = $td_col4.append($form);

    var $tr = $("<tr></tr>");
    return $tr.append(col1, col2, col3, col4);
}

$(document).ready(function () {
    queryServer(null);
});

//$(document).ready(function () {
//    $("table").click(function () {
//        $(this).hide();
//    });
//});

//$("a.parentPath").click(function () {
//    var uri = encodeURI($(this).attr('href'));
//    $.ajax({
//        type: "get",
//        url: uri,
//        success: function (result) {
//            fillTable(result);
//        }
//    });
//});
//
//$("a .childPath").click(function () {
//    var uri = encodeURI($(this).attr('href'));
//    $.ajax({
//        type: "get",
//        url: uri,
//        success: function (result) {
//            fillTable(result);
//        }
//    });
//});

//$("p.childPath").click(function () {
//    var uri = encodeURI(parentPath + separator + $(this).text());
//    $.ajax({
//        type: "get",
//        url: uri,
//        data: {path: parentPath + separator + $(this).text()},
//        success: function (result) {
//            fillTable(result);
//        }
//    });
//});

//$("p.childPath").click(function () {
//    $("p").hide();
//});



