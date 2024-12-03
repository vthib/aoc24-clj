(ns day02
  (:require
   [clojure.string :as str]))

(defn read-input [] (slurp "../resources/day02.txt"))

(defn parse-input [input]
  (->> input
       (str/split-lines)
       (map #(str/split % #" "))
       (map #(map parse-long %))))

(defn valid-order? [coll]
  (or
   (apply >= coll)
   (apply <= coll)))

(defn valid-intervals? [coll]
  (every? #(and (>= % 1) (<= % 3)) (map #(abs (- %1 %2)) coll (rest coll))))

(defn valid? [coll] (and (valid-order? coll) (valid-intervals? coll)))

(defn part1 [reports]
  (->> reports
       (filter valid?)
       count))

(defn drop-nth [n coll]
  (keep-indexed #(when (not= %1 n) %2) coll))

(defn generate-alternates [coll]
  (map #(drop-nth % coll) (range 0 (count coll))))

(defn part2 [reports]
  (->> reports
       (filter #(or (valid? %) (some valid? (generate-alternates %))))
       count))

(defn solve []
  (let [reports (parse-input (read-input))]
    (prn "part1:" (part1 reports))
    (prn "part2:" (part2 reports))))

(solve)
