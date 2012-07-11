(ns sketchpad.buffer-search
	(:use [sketchpad search-context])
	(:require [sketchpad.search-engine :as search]))

(defn cur-rta []
	(sketchpad.tab-manager/current-text-area (@sketchpad.core/current-app :editor-tabbed-panel)))

(defn search 
	[search-str]
	(cond 
		(= java.lang.String (type search-str))
		(do
			(let [rta (cur-rta)
						context (search-context search-str)]
				(let [finder (search/find rta context)]
					finder)))
		(= java.util.regex.Pattern (type search-str))
		(do
			(let [rta (cur-rta)
						context (search-context (str search-str))]
					(regular-expression! context true)
				(let [finder (search/find rta context)]
					finder)))))

(defn search-replace 
	[search-str replace-str]
	(cond 
		(= java.lang.String (type search-str))		
		(do
			(let [rta (cur-rta)
						context (search-context search-str)]
					(replace-with! context replace-str)
				(let [finder (search/replace rta context)]
					finder)))
		(= java.util.regex.Pattern (type search-str))			
			(let [rta (cur-rta)
						context (search-context (str search-str))]
					(regular-expression! context true)
					(replace-with! context replace-str)
				(let [finder (search/replace rta context)]
					finder))))

(defn search-replace-all
	[search-str replace-str]
	(cond 
		(= java.lang.String (type search-str))		
		(do
			(let [rta (cur-rta)
					context (search-context search-str)]
				(replace-with! context replace-str)
			(let [finder (search/replace-all rta context)]
				finder)))
	(= java.util.regex.Pattern (type search-str))
	(do
		(let [rta (cur-rta)
				context (search-context (str search-str))]
			(regular-expression! context true)
			(replace-with! context replace-str)
		(let [finder (search/replace-all rta context)]
			finder)))))







