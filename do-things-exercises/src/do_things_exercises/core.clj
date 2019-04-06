(ns do-things-exercises.core
  (:gen-class))

;; 1
(def pointless
  (hash-map
    :a (list
     (vector
       (str "I" " " "like" " " "you")))))

(def pointless-set (hash-set pointless pointless))

;; 2
(defn add100
  "adds 100 to given arg"
  [x]
  (+ x 100))

;; 3
(defn dec-maker
  "provides a function that decrements a number by the given argument"
  [x]
  #(- % x))

;; 4
(defn mapset
  "maps over collection and returns a set of results"
  [f coll]
  (set (map f coll)))

;; 5
(defn matching-parts
  [part]
  (map (fn [side]
            {:name (clojure.string/replace (:name part) #"^top-" side)
              :size (:size part)})
       ["top-right-" "top-left" "bottom-right-" "bottom-left-"]))


(defn radial-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (concat [part] (matching-parts part)))))
          []
          asym-body-parts))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
