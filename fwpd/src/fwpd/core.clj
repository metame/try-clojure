(ns fwpd.core)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (map :name (filter #(>= (:glitter-index %) minimum-glitter) records)))

(def validations {:name string?
                  :glitter-index int?})

(defn validate
  "validates a vampire record"
  [record]
  (every? (fn [[vamp-key value]]  ((get validations vamp-key) value)) record))

(defn to-record [[name glitter-index]] {:name name
                                        :glitter-index glitter-index})

(defn append
  "append new suspect to list of suspects"
  [records r]
  (if (validate r)
    (conj records r)
    records))

(defn to-csv
  "takes a list of records and returns a csv string representation of it's values"
  [records]
  (clojure.string/join "\n"
                       (map #((partial clojure.string/join ",")
                                  (map second %)) records)))
