(ns sketchpad.auto-complete.auto-complete
  (:import (org.fife.rsta.ac.html.HtmlCompletionProvider))
  (:use [sketchpad.auto-complete.completion-builder])
  (:require [sketchpad.config.config :as config]
            [sketchpad.wrapper.rsyntaxtextarea :as wrapper.rsyntaxtextarea]
            [sketchpad.input.default :as input.default]))

(defn create-completion-provider
  ([] (create-completion-provider :default))
  ([kw]
   (let [cp (org.fife.ui.autocomplete.ClojureCompletionProvider. )]
     (add-all-ns-completions cp)
     (.setParameterizedCompletionParams cp \space " " \))
     (.setAutoActivationRules cp true "")
     cp)))

(defonce completion-provider (create-completion-provider))

(defonce default-auto-completion (org.fife.ui.autocomplete.AutoCompletion. completion-provider))

(defn install-auto-completion
  [rta] 
    (config/apply-auto-completion-prefs! default-auto-completion)
    (.install default-auto-completion rta))

(defn install-project-auto-completion
"Adds all project ns completions to a text area. Takes a text-area and a SketchPad project."
  [rsta  completion-provider]
    (config/apply-auto-completion-prefs! completion-provider)
    (.install completion-provider rsta))

(defn install-html-auto-completion
  [rta]
  (let [completion-provider (org.fife.ui.autocomplete.AutoCompletion. (org.fife.rsta.ac.html.HtmlCompletionProvider.))]
    (config/apply-auto-completion-prefs! completion-provider)
    (doto completion-provider
      (.install rta))))

(defn create-provider
  ([]
   (let [cp (org.fife.ui.autocomplete.DefaultCompletionProvider.)]
     (.setParameterizedCompletionParams cp \space " " \))
     (.setAutoActivationRules cp true "")
     cp)))

(defn make-clojar-completion-provider
"Builds a Completion Provider from the available repo on Clojars."
	[]
	(build-clojar-completions (create-provider)))

(def clojar-completion-provider (org.fife.ui.autocomplete.AutoCompletion. (make-clojar-completion-provider)))

(defn install-clojars-auto-completions
"Adds all project ns completions to a text area. Takes a text-area and a SketchPad project."
  [text-area]
    (config/apply-auto-completion-prefs! clojar-completion-provider)
    (wrapper.rsyntaxtextarea/set-input-map! text-area (input.default/default-input-map))
    (doto 
      clojar-completion-provider
      (.setAutoActivationEnabled true)
      (.setDescriptionWindowSize 300 500) 
      (.setShowDescWindow false))
    (.install clojar-completion-provider text-area))

(defn build-project-completion-provider
"Builds a Completion Provider for a project."
  [project-path]
  (build-project-completions (create-provider) project-path)) 