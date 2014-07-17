module.exports = function(grunt) {
  "use strict";
  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON("package.json"),
    srcPath: "js",
    buildPath: "js/build",

    shell: {
      "build-jsx": {
        command: [
          "jsx -x jsx <%= srcPath %>/jsx/ <%= srcPath %>/js/app/views/",
          "rm -rf <%= src_path %>/js/app/views/.module-cache/"
        ].join(" && "),
        stdout: true,
        failOnError: true
      }
    },

    // uglify: {
    //   options: {
    //     banner: "/*! <%= pkg.name %> <%= grunt.template.today('yyyy-mm-dd') %> */\n"
    //   },
    //   build: {
    //     src: "src/<%= pkg.name %>.js",
    //     dest: "build/<%= pkg.name %>.min.js"
    //   }
    // }
  });

  grunt.loadNpmTasks("grunt-shell");

  grunt.registerTask("jsx", [
    "shell:build-jsx"
  ]);

  // Load the plugin that provides the "uglify" task.
  // grunt.loadNpmTasks("grunt-contrib-uglify");

  // Default task(s).
  // grunt.registerTask("default", ["uglify"]);
};
