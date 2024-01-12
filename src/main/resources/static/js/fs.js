globalThis.fs = (function (metaFile) {
  const isValidFileName = function (fileName) {
    return /^[a-zA-Z_$][a-zA-Z0-9_$]*\.java$/.test(fileName);
  };

  const flush = function (files) {
    const local = { files: {} };
    for (const fileName in files) {
      if (!files[fileName].localStorage) continue;
      local.files[fileName] = files[fileName];
    }
    localStorage.fs = JSON.stringify(local);
  };

  const handleFiles = function (data, files) {
    for (const fileName in data.files) {
      if (isValidFileName(fileName)) {
        files[fileName] = data.files[fileName];
      }
    }
  };

  const loadFilesServer = function (files) {
    $.ajax({
      url: metaFile,
      dataType: "text json",
      async: false,
      success: function (data) {
        for (const fileName in data.files) {
          $.ajax({
            url: "example/" + fileName,
            dataType: "text",
            async: false,
            success: function (src) {
              files[fileName] ={
                name: fileName,
                src: src,
                localStorage: false,
                changed: false,
              };
            },
            error: function (jqXHR, textStatus, errorThrown) {
              console.log("Unable to read '" + fileName + "': " + textStatus);
            },
          });
        }
      },
      error: function (jqXHR, textStatus, errorThrown) {
        console.log("Unable to read '" + metaFile + "': " + textStatus);
      },
    });
  };

  const loadFilesLocal = function (files) {
    if (typeof localStorage === "undefined") {
      console.log("Local storage not supported!");
      return;
    }
    const data = localStorage.fs || "{}";
    handleFiles(JSON.parse(data), files);
  };

  const loadFiles = function () {
    const files = {};

    loadFilesLocal(files);
    loadFilesServer(files);
    return files;
  };

  const self = {
    fileNames: [],
    files: {},
    getFile: function (fileName) {
      return self.files[fileName];
    },
    getUid: function () {
      if(localStorage.uid) {
        return localStorage.uid;
      }
      $.ajax({
        url: "https://www.uuidgenerator.net/api/version4",
        dataType: "text",
        async: false,
        success: function (data) {
          localStorage.uid = data;
          return data;
        },
        error: function (jqXHR, textStatus, errorThrown) {
          console.log("Unable to get uid: " + textStatus);
        },
      });
    },
    rename: function (oldName, newName) {
      if (oldName === newName) return;
      if (!(oldName in self.files) || newName in self.files) {
        console.log("Won't replace file!");
        return;
      }
      if (!isValidFileName(newName)) {
        console.log("Not a valid file name: '" + newName + "'");
        return;
      }
      delete self.fileNames; // Empty cache
      const file = self.files[oldName];

      file.name = newName;
      self.files[newName] = file;
      delete self.files[oldName];
      flush(self.files);
    },
    openFile: function (file) {
      return file.src;
    },
    deleteFile: function (fileName) {
      delete self.fileNames;
      delete self.files[fileName];
      flush(self.files);
    },
    getFileNames: function () {
      self.fileNames = [];
      for (const fileName in self.files) {
        self.fileNames.push(fileName);
      }
      self.fileNames.sort();
      return self.fileNames;
    },
    saveFile: function (file) {
      self.files[file.name] = file;
      flush(self.files);
    },
  };
  self.files = loadFiles();
  return self;
})("example/meta.json");
