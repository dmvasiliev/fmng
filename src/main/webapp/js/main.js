var currentPath = "";
var separator = "";
var parentPath = "";

$(document).ready(function () {
    queryServer(null);
    addUploadForm();
    $("#logoutHref").on('click', function () {
        logout();
    });

    $('#formUpload').ajaxForm({
        success: function(msg) {
            alert("File has been uploaded successfully");
            queryServer(currentPath);
        }
    });
});

function queryServer(pathTo) {
    $.ajax({
        type: "get",
        url: "directory",
        data: {path: pathTo},
        success: function (result) {
            updateVariables(result);
            $("caption").text("Current directory: " + (pathTo === null ? result.separator : pathTo)).trigger('captionChanged');
            $("table").find("tr:gt(0)").remove();
            fillTable(result);
        }
    });
}

function updateVariables(result) {
    currentPath = result.path;
    separator = result.separator;
    parentPath = result.parentPath;
}

function fillTable(result) {
    if (result.path !== null) {
        //add .. if not root
        $("table").append(appendParentRow(result));

        //add table's rows
        var contents = result.contents;
        if (contents) {
            $.each(contents, function (i, file) {
                $("table").append(appendFileRow(file));
            });
        }
    }
}

function appendParentRow(result) {
    var isChild = result.isChild;
    if (isChild === "true") {
        var rootCell = $("<p></p>").attr({
            title: result.parentPath
        });
        var $rootCellWithText = rootCell.text("..");
        var td_nameColumn = addTdTag("col_name").attr({
            id: "parentPath"
        });
        td_nameColumn.append($rootCellWithText);
        var $tr = $("<tr></tr>").attr({
            class: "row"
        });
        return $tr.append(addTdTag("col_type"), td_nameColumn, addTdTag("col_size"), addTdTag("col_download"));
    }
    return "";
}

function appendFileRow(file) {
    //add row
    var $tr = $("<tr></tr>").attr({
        class: "row"
    });

    var typeColumn = fillTypeColumn(file);
    var nameColumn = fillNameColumn(file);
    var sizeColumn = fillSizeColumn(file);
    var downloadColumn = fillDownloadColumn(file);
    return $tr.append(typeColumn, nameColumn, sizeColumn, downloadColumn);
}

function addTdTag(attributeName) {
    return $("<td></td>").attr("class", attributeName);
}

function fillTypeColumn(file) {
    var isDirectory = file.isDirectory;
    var td_TypeColumn = $("<td></td>").attr("class", "col_type");
    return td_TypeColumn.text(isDirectory ? "+" : "-");
}

function fillNameColumn(file) {
    var isDirectory = file.isDirectory;
    var $td_nameColumn = $("<td></td>");
    var $tagP = $("<p></p>");
    if (isDirectory) {
        $tagP = $tagP.attr("title", file.relativePath + separator + file.name)
            .text(file.name);
        $td_nameColumn = $td_nameColumn.attr("class", "tdNameDir");
    }
    if (file.isFile) {
        $tagP = $tagP.text(file.name);
        $td_nameColumn = $td_nameColumn.attr("class", "tdNameFile");
    }
    return $td_nameColumn.append($tagP);
}

function fillSizeColumn(file) {
    return $("<td></td>").attr("class", "col_size").text(file.size);
}
function fillDownloadColumn(file) {
    var $tagForm = "";
    if (file.isFile) {
        var $inputPath = $("<input>").attr({
            type: 'hidden',
            name: "path",
            value: currentPath
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
        $tagForm = $("<form></form>").attr("method", "POST")
            .attr("action", "download")
            .attr("class", "download");
        $tagForm.append($inputPath, $inputName, $inputButton);
    }

    var $td_downloadColumn = $("<td></td>").attr("class", "col_download");

    return $td_downloadColumn.append($tagForm);
}

function addUploadForm() {
    var $inputFile = $("<input>").attr({
        type: 'file',
        name: "file",
        id: "file"
    });
    var $inputPath = $('<input>').attr({
        type: 'hidden',
        name: "path",
        value: currentPath,
        id: "uploadPath"
    });
    var $inputUpload = $('<input>').attr({
        type: 'submit',
        name: "upload",
        value: "Upload",
        id: "upload"
    });
    var $form = $("<form></form>").attr("method", "POST")
        .attr("action", "upload")
        .attr("id", "formUpload")
        .attr("enctype", "multipart/form-data");

    $form = $form.append($inputFile, $inputPath, "<br />", $inputUpload);

    $("table").after("<p id='indent'><br /><br /></p>");
    $("p#indent").after($form);
}

$(document)
    .on("mouseenter", "tr.row", function () {
        $(this).children("td").css({"color": "red", "border": "1px solid red"});
    })
    .on("mouseleave", "tr.row", function () {
        $(this).children("td").css({"color": "black", "border": "1px solid black"});
    });

$(document).on("dblclick", "td.tdNameDir", function () {
    var pathTo = $(this).children("p").attr("title");
    queryServer(pathTo);
});

$(document).on("dblclick", "td#parentPath", function () {
    var pathTo = $(this).children("p").attr("title");
    queryServer(pathTo);
});

$(document).on('captionChanged', function () {
    $("input#uploadPath").attr("value", currentPath);
});

function logout() {
    $.ajax({
        url: "logout",
        type: "get",
        success: function (results) {
            if (results === "SUCCESS") {
                alert("You are successfully logged out.");
                window.location.href = "index.xhtml";
            }
        }
    });
}
