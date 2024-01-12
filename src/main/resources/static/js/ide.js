globalThis.ide = (function () {
  const baseUrl = "http://127.0.0.1:8080/";
  const updateEditorFileName = function (file) {
    let prefix = "";
    file = file || ide.openFile;
    if (file.changed) prefix += "*";

    $("#panelMiddleLabel span").text(prefix + file.name);
  };

  const updateEditor = function (evt) {
    updateEditorFileName();
  };

  const programSource = function () {
    return $("#srcInput").val();
  };

  const updateFileList = function () {
    const fileList = $("#fileList");
    fileList.find(".fileEntry").remove();
    const sortedFileNames = fs.getFileNames();
    let id = 0;
    for (const fileName of sortedFileNames) {
      ++id;
      const file = fs.getFile(fileName);
      const line = $(
        '<div id="file_' + id + '" title="' + fileName + '"></div>'
      );
      line.addClass("fileEntry");
      const link = $("<div></div>");
      const form = $('<form onsubmit="return false;"></form>');
      const inp = $('<input type="text" class="userInput"></input>');
      const nameChange = (function (fileName, id) {
        return function () {
          ide.handleFileRename(fileName, id);
        };
      })(fileName, id);
      inp.bind("change", nameChange);
      inp.val(file.name);
      inp.appendTo(form);

      form.appendTo(link);
      link.appendTo(line);
      line.on(
        "click",
        (function (fileName) {
          return function (event) {
            ide.loadFile(fileName);
            $(this).find("input").focus().select();
          };
        })(fileName)
      );
      line.appendTo(fileList);
    }
  };

  const storeSource = function () {
    const file = ide.openFile;
    if (!file) return;
    const prog = programSource();
    if (file.changed) {
      updateEditorFileName();
    }
    file.src = prog;
  };

  const createNewFile = function (fileName) {
    if (!fileName) {
      fileName = "Newfile";
      let count = 1;
      while (true) {
        const fn = fileName + count + ".java";
        if (!(fn in fs.files)) {
          fileName = fn;
          break;
        }
        count++;
      }
    }
    fs.files[fileName] = {
      name: fileName,
      src: "public class " + fileName.replace(".java", "")+" {\n\n}",
      localStorage: true,
      changed: true,
    };

    return fileName;
  };

  function saveTextToFile(text, fileName) {
    const blob = new Blob([text], { type: "text/plain" });
    const url = URL.createObjectURL(blob);
    const downloadLink = $("<a>", {
      href: url,
      download: fileName,
    });
    $("body").append(downloadLink);
    downloadLink[0].click();
    downloadLink.remove();
    URL.revokeObjectURL(url);
  }

  const self = {
    defaultFile: [],

    loadSource: function (src) {
      $("#srcInput").val(src);
      updateEditor();
    },

    loadFile: function (fileName) {
      $("#fileList .fileEntry.emph").removeClass("emph");
      $("div.fileEntry input")
        .filter(function () {
          return $(this).val() === fileName;
        })
        .parents("div.fileEntry")
        .addClass("emph");

      if (ide.openFile && ide.openFile.name === fileName) return;

      storeSource();
      const file = fs.getFile(fileName);
      if (!file) return;

      if (ide.openFile) {
        if (ide.defaultFile[ide.defaultFile.length - 1] !== ide.openFile.name) {
          ide.defaultFile.push(ide.openFile.name);
        }
      }
      ide.openFile = file;
      ide.loadSource(file.src);
    },

    handleCodeInput: function (textarea) {
      ide.openFile.changed = true;
      updateEditor();
    },

    clearPrintArea: function (selector) {
      const area = $(selector);
      if (area.find("span").length > 0) {
        area.find("span:not(:last)").remove();
        area.find("span").html("");
      } else {
        area.html("");
      }
    },

    newFile: function () {
      const fileName = createNewFile();
      updateFileList();
      ide.loadFile(fileName);
    },

    deleteFile: function () {
      let fileName = ide.openFile.name;
      if (!fs.files[fileName]) return;
      fs.deleteFile(fileName);
      updateFileList();
      while (true) {
        if (!ide.defaultFile.length) break;
        fileName = ide.defaultFile[ide.defaultFile.length - 1];
        if (fs.files[fileName]) {
          ide.loadFile(fileName);
          return;
        } else {
          ide.defaultFile.pop();
        }
      }

      const files = $("div.fileEntry");
      if (files.length > 0) {
        ide.loadFile($(files[0]).attr("title"));
      } else {
        ide.newFile();
      }
    },

    saveFiles: function () {
      if (typeof localStorage === "undefined") return;
      for (const fileName in fs.files) {
        const file = fs.files[fileName];
        if (file.changed) {
          file.changed = false;
          fs.saveFile(file);
        }
      }
      updateEditorFileName();
    },

    handleFileRename: function (fileName, id) {
      const input$ = $("#file_" + id + " input");
      const newName = input$.val();
      fs.rename(fileName, newName);

      updateFileList();
      ide.loadFile(newName);
      updateEditorFileName();

      return false;
    },

    downloadFile: function () {
      const fileName = ide.openFile.name;
      const file = fs.getFile(fileName);
      if (!file) return;
      saveTextToFile(file.src, fileName);
    },

    runProgram: function () {
      if (!ide.openFile) {
        console.log("No file open!");
        return;
      }
      const filesAndMain = {};
      for (const fileName in fs.files) {
        if (fs.files[fileName].src) {
          filesAndMain[fileName.replace(".java", "")] = fs.files[fileName].src;
        }
      }
      $.ajax({
        url: baseUrl + "run?main=" + ide.openFile.name.replace(".java", ""),
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(filesAndMain),
        success: function (response) {
          $("#printArea").html(response);
        },
        error: function (error) {
          console.error("Error:", error);
        },
      });
    },

    compile: function () {
      if (!ide.openFile) {
        console.log("No file open!");
        return;
      }
      const classFile = {};
      classFile[ide.openFile.name.replace(".java", "")] = ide.openFile.src;
      $.ajax({
        url: baseUrl + "compile",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(classFile),
        success: function (response) {
          $("#printArea").html(response);
        },
        error: function (error) {
          console.error("Error:", error);
        },
      });
    },

    stepProgram: function () {
      if (!ide.openFile) {
        console.log("No file open!");
        return;
      }
      const filesAndMain = {};
      for (const fileName in fs.files) {
        if (fs.files[fileName].src) {
          filesAndMain[fileName.replace(".java", "")] = fs.files[fileName].src;
        }
      }
      $.ajax({
        url: baseUrl + "step?main=" + ide.openFile.name.replace(".java", "")+"&uid="+fs.getUid(),
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(filesAndMain),
        success: function (response) {
          $("#debugArea").append(response);
        },
        error: function (error) {
          console.error("Error:", error);
        },
      });
    },

    stopProgram: function () {
      $.ajax({
        url: baseUrl + "stop?uid="+fs.getUid(),
        type: "GET",
        contentType: "application/json",
        success: function (response) {
          $("#debugArea").append(response);
        },
        error: function (error) {
          console.error("Error:", error);
        },
      });
    },
  };

  $(document).ready(function () {
    updateFileList();
  });
  return self;
})();
