(ns day01
  (:require
   [clojure.string :as str]))

(defn split-in-two [values]
  (for [i '(0 1)]
    (->> values (map #(nth % i)) (map parse-long))))

(defn read-input [] (slurp "../resources/day01.txt"))

(defn parse-input [input]
  ; Input is 'v1 v2\nv3 v4\n...', split by whitespace first
  (->> input
       (str/split-lines)
       (map #(str/split % #"\s+"))
       (split-in-two)))

(defn part1 [a b]
  (let [a (sort a) b (sort b)]
    (apply + (map #(abs (- %1 %2)) a b))))

(defn reducer
  [acc v] (update acc v #(if  (nil? %) 1 (inc %))))

(defn part2 [a b]
  (let [nbOccurences (reduce reducer {} b)]
    (apply + (map #(* (get nbOccurences % 0) %) a))))

(defn solve []
  (let [[a b] (parse-input (read-input))]
    (prn "part1:" (part1 a b))
    (prn "part2:" (part2 a b))))

(solve)
