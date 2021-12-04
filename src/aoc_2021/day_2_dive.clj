(ns aoc_2021.day_2_dive
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def demo-inputs [
                  ["forward" 5]
                  ["down" 5   ]
                  ["forward" 8]
                  ["up" 3     ]
                  ["down" 8   ]
                  ["forward" 2]])

(def forwards (into [] (filter (fn [[a _]] (= "forward" a)) demo-inputs)))

(def forward-sum (apply + (map last forwards)))
;; => 15

(def depths (into [] (remove (fn [[a _]] (= "forward" a)) demo-inputs)))

(defn filter-this [this col] (into [] (filter (fn [[a _]] (= this a))col)))

(filter-this "forward" demo-inputs)

(def up-sum (apply + (map last (filter-this "up" depths))))
up-sums

(filter-this "down" depths)
(def down-sum (apply + (map last (filter-this "down" depths))))

(* forward-sum (- down-sum up-sum))
;; => 150

;; REAL INPUTS

(def inputs2
  (line-seq (io/reader(io/resource "day2_input.txt"))))

(first inputs2)
;; => "forward 5"


(Integer/parseInt (last (str/split (first inputs2) #" ")))

(defn normalized-input [a]
  (let [direction (first (str/split a #" "))
        magnitude (Integer/parseInt (last (str/split a #" ")))]
    [direction magnitude]))

(normalized-input (first inputs2))
;; => ["forward" 5]

(def normalized-inputs (map normalized-input inputs2))

(take 5 normalized-inputs)

(def horizontal-sum (->> (filter-this "forward" normalized-inputs)
                         (map last)
                         (apply +)))

(defn get-sum [type data] (->> (filter-this type data)
                               (map last)
                               (apply +)))

(def horizontal-sum (get-sum "forward" normalized-inputs))
(def pos-depth-sum (get-sum "down" normalized-inputs))
(def neg-depth-sum (get-sum "up" normalized-inputs))

(def part-1-solution (* horizontal-sum (- pos-depth-sum neg-depth-sum)))
part-1-solution;; => 1989014

;; PART 2

(defn apply-command [[horizontal depth aim][command value]]
  (case command
    "forward" [(+ horizontal value) (+ depth (* aim value)) aim]
    "down"    [horizontal depth (+ aim value)]
    "up"      [horizontal depth (- aim value)] ))

(apply-command [0 0 0] ["forward" 5])
;; => [5 0 0]
(apply-command [5 0 0] ["down" 5])
;; => [5 0 5]
(apply-command [5 0 5] ["forward" 8])
;; => [13 40 5]
(apply-command [13 40 5] ["up" 3])
;; => [13 40 2]
(apply-command [13 40 2] ["down" 8])
;; => [13 40 10]
(apply-command [13 40 10] ["forward" 2])
;; => [15 60 10]

(apply * (take 2 (reduce apply-command [0 0 0] demo-inputs)))
;; => 900
(apply * (take 2 (reduce apply-command [0 0 0] normalized-inputs)))
;; => 2006917119

(defn calculate-position [filename]
  (let [inputs  (map normalized-input (line-seq (io/reader(io/resource filename))))]
    (->> inputs
         (reduce apply-command [0 0 0])
         (take 2)
         (apply *))
    ))

(calculate-position "day2_input.txt")
;; => 2006917119
