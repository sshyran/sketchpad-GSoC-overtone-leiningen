(ns sketchpad.menu.file 
	(:use [seesaw meta])
	(:require [sketchpad.menu.menu-utils :as menu-utils]
        [sketchpad.tree.utils :as tree.utils]
			  [sketchpad.tab :as tab]
        [sketchpad.editor.buffer :as editor.buffer]
        [sketchpad.file.file :as file]
			  [sketchpad.rsyntaxtextarea :as rsyntaxtextarea]
        [sketchpad.state :as state]
        [seesaw.core :as seesaw.core]
        [seesaw.keystroke :as keystroke]))

(defn lein-project-path [lein-project]
"Returns the src path of a Leiningen project."
(first (lein-project :source-paths)))

(defn new-file!
"Create a new file"
[]
  (editor.buffer/blank-clj-buffer!))

(defn save-file!
([] (save-file! (tab/current-buffer)))
([buffer]
"Save the current buffer."
(println "menu.save-file! current-buffer: " (tab/current-buffer))
(println "menu.save-file! buffer: " buffer)
(let [new-file? @(buffer :new-file?)]
  (if new-file?
    (do
      (when-let [new-file (file/save-file-as!)]
        (let[new-file-title (.getName new-file)] 
          (assoc (:file buffer) new-file)
          (assoc (:new-file? buffer) false) 
          (when (file/save-file! buffer)
            (tab/title-at! (tab/index-of-component buffer) new-file-title)
            (tab/mark-current-tab-clean! (@state/app :editor-tabbed-panel))))))
    (do
      (when (file/save-file! buffer)
            (tab/mark-current-tab-clean!)))))))

(defn save-file-as!
([] (save-file-as! (tab/current-buffer)))
([buffer]
"Open the save as dialog for the current buffer."
  (let [text-area (:text-area buffer)
        file @(:file buffer)
       file-path (tree.utils/get-selected-file-path @state/app)]
	  (when-let[new-file (file/save-file-as!)]
      (when @(:new-file? buffer)
        (assoc (:new-file? buffer) false))
    		(println "Saved file as: " new-file)))))

(defn make-file-menu-items [app-atom]
 {:new-file (seesaw.core/menu-item :text "New File" 
                              :mnemonic "N" 
                              :key (keystroke/keystroke "meta N") 
                              :listen [:action (fn [_] (new-file!))])
  :save     (seesaw.core/menu-item :text "Save" 
                              :mnemonic "S" 
                              :key (keystroke/keystroke "meta S") 
                              :listen [:action (fn [_] (save-file!))])
  :save-as  (seesaw.core/menu-item :text "Save as..." 
                              :mnemonic "M" 
                              :key (keystroke/keystroke "meta shift S")
                              :listen [:action (fn [_] (save-file-as!))])})

(defn make-file-menu
  [app-atom]
  (let [menu-items (make-file-menu-items app-atom)]
    (seesaw.core/menu :text "File"
          :mnemonic "F"
          :items [
                  (menu-items :new-file)
                  (menu-items :save)
                  (menu-items :save-as)])))

