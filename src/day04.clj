(ns day04
  (:require
   [clojure.string :as str]))

(defn read-input [] (slurp "../resources/day04.txt"))

(defn parse-input [input]
  (let [grid (->> input (str/split-lines) (map vec) (vec))
        height (count grid)
        width (count (first grid))]
    (array-map :width width :height height :grid grid)))

(defn grid-get [grid x y]
  (get (get (:grid grid) y []) x nil))

(defn part1 [grid]
  (let [pattern "XMAS"
        sum (atom 0)]
    (doseq [dx [-1 0 1]
            dy [-1 0 1]
            x (range 0 (:width grid))
            y (range 0 (:height grid))]
      (when (every?
             (fn [[idx c]]
               (= c (grid-get grid (+ x (* dx idx)) (+ y (* dy idx)))))
             (map-indexed vector pattern))
        (swap! sum inc)))
    @sum))

(defn part2 [grid]
  (let [sum (atom 0)]
    (doseq [x (range 0 (:width grid))
            y (range 0 (:height grid))]
      (when
       (and
        (= \A (grid-get grid x y))
        (or
         (and
          (= \M (grid-get grid (- x 1) (- y 1)))
          (= \S (grid-get grid (+ x 1) (+ y 1))))
         (and
          (= \S (grid-get grid (- x 1) (- y 1)))
          (= \M (grid-get grid (+ x 1) (+ y 1)))))
        (or
         (and
          (= \M (grid-get grid (+ x 1) (- y 1)))
          (= \S (grid-get grid (- x 1) (+ y 1))))
         (and
          (= \S (grid-get grid (+ x 1) (- y 1)))
          (= \M (grid-get grid (- x 1) (+ y 1))))))
        (swap! sum inc)))
    @sum))

(defn solve []
  (let [grid (parse-input (read-input))]
    (println "part1:" (part1 grid))
    (println "part2:" (part2 grid))))

(solve)
