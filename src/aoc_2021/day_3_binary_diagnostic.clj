(ns aoc_2021.day_3_dive
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(def demo-input [
                 "00100"
                 "11110"
                 "10110"
                 "10111"
                 "10101"
                 "01111"
                 "00111"
                 "11100"
                 "10000"
                 "11001"
                 "00010"
                 "01010"
                 ])

(defn normalize-input [input]
  (mapv #(Integer/parseInt %) (str/split input #"")))

(def normalized-demo-input (map normalize-input demo-input))
;; => ([0 0 1 0 0] [1 1 1 1 0] [1 0 1 1 0] [1 0 1 1 1] [1 0 1 0 1] [0 1 1 1 1] [0 0 1 1 1] [1 1 1 0 0] [1 0 0 0 0] [1 1 0 0 1] [0 0 0 1 0] [0 1 0 1 0])

(def first-col (mapv #(nth % 0) normalized-demo-input));; => (0 1 1 1 1 0 0 1 1 1 0 0)


(defn mcbd [bin-vec]
  ;; most common binary digit
  (let [zero-or-one (apply compare (vals (frequencies bin-vec)))]
    (case zero-or-one
      -1 1
       1 0)
    ))

(mcbd first-col)

(defn find-gamma-rate [input])

(loop [x 10]
  (when (> x 1)
    (println x)
    (recur (- x 2))))
