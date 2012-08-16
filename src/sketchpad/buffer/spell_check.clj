(ns sketchpad.buffer.spell-check
	(:require [sketchpad.config.config :as config])
	(:import (org.fife.ui.rsyntaxtextarea.spell SpellingParser)))

(defonce english-dic-zip (clojure.java.io/file (clojure.java.io/resource "english_dic.zip")))

(defonce english-spell-checker (SpellingParser/createEnglishSpellingParser english-dic-zip true))
(do
	(config/apply-spell-checker-prefs! english-spell-checker))


(defn add-english-spell-checker
"Add an English language spell checker to a given RSyntaxTextArea."
	[rsta]
	(.addParser rsta english-spell-checker))

(defn remove-english-spell-checker
"Add an English language spell checker to a given RSyntaxTextArea."
	[rsta]
	(.removeParser rsta english-spell-checker))