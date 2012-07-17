Editor

The text editor component contains the active buffers in tabs. A tab is opened by double clicking a file node in the file tree. The Sketchpad text area is built on the RSyntaxTextArea library which supports the majority of features popular IDE's that Eclipse and NetBeans do. While the features are too many to list some include: syntax highlight for almost 30 languages, auto-completion and dumb completion, search and replace, gutters, bracket matching, and a large library of macro recordable actions for manipulating buffers. Currently the main attributes are configurable from the config/default.clj file, which contains user preferences. Tabs can be navigated with cmd+alt+left/right and closed with cmd+w.

File tree

Standard file tree for opening, closing, and creating projects and files. Can be hidden with cmd+1

REPL

From manipulating buffers and creating executable macros, to managing project file structures and Leiningen projects, the Sketchpad editor REPL is a powerful tool for working with Clojure projects. The editor REPL defaults to load the sketchpad.user namespace, which provides functions for interacting with projects. Project repls will ultimately be created in outside processes via Leiningen, nrepl, and pomegranate.

User namespace

The goal is to have the user namespace provide access and functionality as powerful as vim and emacs but in digestible Clojure code. Scripting macros and custom tools is simple and logical. Since Sketchpad is written in Clojure, the user namespace can also deal directly with Leiningen projects, the editor application it self, and much more.

Projects and Leiningen

This part of the project is still young but will be a central cog in the Sketchpad architecture. Leiningen has quickly become one the most popular Clojure project management tools and with the release of 2.0 this it will only be more popular. The goal is to leverage all of the Leiningen features from managing dependencies and projects to publishing projects to clojars all from Sketchpad and in Clojure.

