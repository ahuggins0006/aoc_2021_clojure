(ns aoc_2021.day_1_sonar_sweep
  (:require [clojure.java.io :as io]
  [clojure.string :as str]))
;;As the submarine drops below the surface of the ocean, it automatically performs a sonar sweep of the nearby sea floor. On a small screen, the sonar sweep report (your puzzle input) appears: each line is a measurement of the sea floor depth as the sweep looks further and further away from the submarine.

;;For example, suppose you had the following report:
;;
;;199
;;200
;;208
;;210
;;200
;;207
;;240
;;269
;;260
;;263
;;This report indicates that, scanning outward from the submarine, the sonar sweep found depths of 199, 200, 208, 210, and so on.
;;
;;The first order of business is to figure out how quickly the depth increases, just so you know what you're dealing with - you never know if the keys will get carried into deeper water by an ocean current or a fish or something.
;;
;;To do this, count the number of times a depth measurement increases from the previous measurement. (There is no measurement before the first measurement.) In the example above, the changes are as follows:
;;
;;199 (N/A - no previous measurement)
;;200 (increased)
;;208 (increased)
;;210 (increased)
;;200 (decreased)
;;207 (increased)
;;240 (increased)
;;269 (increased)
;;260 (decreased)
;;263 (increased)
;;In this example, there are 7 measurements that are larger than the previous measurement.
;;
;;How many measurements are larger than the previous measurement?

(def demo-inputs [
                  199
                  200
                  208
                  210
                  200
                  207
                  240
                  269
                  260
                  263
                  ])

(def paired (partition 2 1 demo-inputs))

paired
;; => ((199 200)
;;     (200 208)
;;     (208 210)
;;     (210 200)
;;     (200 207)
;;     (207 240)
;;     (240 269)
;;     (269 260)
;;     (260 263))

(compare 1 2)
;; => -1
(compare 1 1)
(reduce compare '(1 2))
;; => -1

(count (filter #(= -1 %) (map #(reduce compare %) paired)))
;; => 7


(->> demo-inputs
     (partition 2 1)
     (map #(reduce compare %))
     (filter #(= -1 %))
     count)
;; => 7


(def inputs1
  (line-seq (io/reader(io/resource "day1_input_a.txt"))))

(def inputs1 (map #(Integer/parseInt %)
                  (line-seq (io/reader(io/resource "day1_input_a.txt")))))

(->> inputs1
     (partition 2 1)
     (map #(reduce compare %))
     (filter #(= -1 %))
     count)
;; => 1665

(def thriced (partition 3 1 demo-inputs))

thriced;; => ((199 200 208) (200 208 210) (208 210 200) (210 200 207) (200 207 240) (207 240 269) (240 269 260) (269 260 263))


(->> thriced
     (map #(reduce + %))
     (partition 2 1)
     (map #(reduce compare %))
     (filter #(= -1 %))
     count)
;; => 5

(def thriced-inputs (partition 3 1 inputs1))
(->> thriced-inputs
     (map #(reduce + %))
     (partition 2 1)
     (map #(reduce compare %))
     (filter #(= -1 %))
     count)
;; => 1702
