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
       0 1
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
      0 0
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

(def a-col (mapv #(nth % 0) normalized-demo-input));; => (0 1 1 1 1 0 0 1 1 1 0 0)

(defn get-col [pos input]
  (mapv #(nth % pos) input))
(mcbd a-col)

(get-col 2 normalized-demo-input)
;; => [1 1 1 1 1 1 1 1 0 0 0 0]
(filter #(= 1 (nth % 1)) normalized-demo-input)
;; => ([1 1 1 1 0] [0 1 1 1 1] [1 1 1 0 0] [1 1 0 0 1] [0 1 0 1 0])
(mcbd (get-col 0 normalized-demo-input))
;; => 1
(defn find-common [definition input]
  (loop [i 0 rem input]
    (if (= 1 (count rem)) rem
        (recur (inc i) (filter #(= (definition (get-col i rem)) (nth % i)) rem))

        )))

(find-common mcbd normalized-demo-input)
;; => ([1 0 1 1 1])

(find-common lcbd normalized-demo-input);; => ([0 1 0 1 0])
(def normalized-inputs3 (mapv normalize-input inputs3))
(def oxygen-rating  (apply str (first (find-common mcbd normalized-inputs3 ))))
(def c02-rating  (apply str (first (find-common lcbd normalized-inputs3 ))))

(defn calc-life-supp-rating [oxy-rating c02-rating]
(reduce * (map #(Integer/parseInt % 2) [oxy-rating c02-rating])))

(calc-life-supp-rating oxygen-rating c02-rating)
;; => 6085575
