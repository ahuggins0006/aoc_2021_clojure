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
(def third-col (mapv #(nth % 2) normalized-demo-input));; => (0 1 1 1 1 0 0 1 1 1 0 0)


;; make sure to sort the frequencies as order is not guaranteed
(defn mcbd [bin-vec]
  ;; most common binary digit
  (let [zero-or-one (apply compare (vals (sort (frequencies bin-vec))))]
    (case zero-or-one
      -1 1
       1 0)
    ))
(->> first-col frequencies
     sort
     ;vals
     ;(apply compare)
     )

(->> third-col frequencies
     sort
     ;vals
     ;(apply compare)
     )

(defn lcbd [bin-vec]
  ;; most common binary digit
  (let [zero-or-one (apply compare (vals (sort (frequencies bin-vec))))]
    (case zero-or-one
      -1 0
      1 1)))

(mcbd first-col)
(lcbd first-col)

(defn find-rate [input rate-type]
  (let [sample-size (count (first input))]
  (loop [i 0 acc []]
    (if (>= i sample-size) acc (recur (inc i)
                                      (conj acc (rate-type (mapv #(nth % i) input))))))))

(Integer/parseInt (->> (find-rate normalized-demo-input mcbd) (apply str)) 2)
;; => 22
(Integer/parseInt (->> (find-rate normalized-demo-input lcbd) (apply str)) 2)
;; => 9

(defn calc-power-consumption [input]
  (let [ gamma-rate   (->> (find-rate input mcbd) (apply str))
         epsilon-rate (->> (find-rate input lcbd) (apply str))]
    (reduce * (map #(Integer/parseInt % 2) [gamma-rate epsilon-rate]))))

(calc-power-consumption normalized-demo-input)
;; => 198


;; read input from day 3 file

(def inputs3
  (line-seq (io/reader(io/resource "day3_input.txt"))))

(calc-power-consumption inputs3)
;; => 2250414


;; PART TWO

;; function to filter based on first digit


(defn starts-with [decider input]
  (if (= decider (first input)) input false ))

(starts-with \0 (first demo-input))
;; => "00100"

(filter #(starts-with \1 %) demo-input);; => ("11110" "10110" "10111" "10101" "11100" "10000" "11001")

(= \a (first "a"))

(defn most-common [pos input] (filter #(starts-with (first (pr-str (mcbd (mapv #(nth % pos) input)))) %) input))
(most-common demo-input)
;; => ("11110" "10110" "10111" "10101" "11100" "10000" "11001")
(filter #(starts-with (first (pr-str (lcbd (mapv first demo-input)))) %) demo-input)
;; => ("00100" "01111" "00111" "00010" "01010")


(defn get-rating [definition input]
  (let [sample-size (count input)]
    (loop [i 0 remaining input]
      (if (>= i sample-size) remaining (recur ;; what to do here?))
      )

  ))
