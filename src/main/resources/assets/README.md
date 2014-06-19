#### Working on assets

When editing assets like CSS and JavaScript locally, they are loaded from the
packaged JAR by default and are not editable. To load them from a directory for
easy editing, set the `assets_path` flag when running Marathon:

    ./bin/start --master local --zk zk://localhost:2181/marathon --assets_path src/main/resources/assets/

#### Compiling Assets

*Note: You only need to follow these steps if you plan to edit the JavaScript source.*

1. Install [NPM](https://npmjs.org/)
2. Change to the assets directory

        cd src/main/resources/assets
3. Install dev dependencies

        npm install
4. Build the assets

        ./bin/build
5. The main JS file will be written to `src/main/resources/assets/js/dist/main.js`.
   Check it in.

        git add js/dist/main.js

### Development Setup (Sublime Text)

1. Add the following to your Sublime Text User Settings

[javascript]:
		{
			...
			"rulers": [80], // lines no longer than 80 chars
			"tab_size": 2, // use two spaces for indentation
			"translate_tabs_to_spaces": true, // use spaces for indentation
			"ensure_newline_at_eof_on_save": true, // add newline on save
			"trim_trailing_white_space_on_save": true, // trim trailing white space on save
			"default_line_ending": "unix"
		}

2. Add Sublime-linter with jshint & jsxhint
	1. Make sure to follow installation instructions for all packages, or they won't work:

	2. Installing SublimeLinter is straightforward using Sublime Package Manager, see [instructions](http://sublimelinter.readthedocs.org/en/latest/installation.html#installing-via-pc)

	3. SublimeLinter-jshint needs a global jshint in your system, see [instructions](https://github.com/SublimeLinter/SublimeLinter-jshint#linter-installation)

	4. SublimeLinter-jsxhint needs a global jsxhint in your system, as well as JavaScript (JSX) bundle inside Packages/JavaScript, see [instructions](https://github.com/SublimeLinter/SublimeLinter-jsxhint#linter-installation)

	5. ~~SublimeLinter-csslint needs a global csslint in your system, see [instructions](https://github.com/SublimeLinter/SublimeLinter-csslint#linter-installation)~~
