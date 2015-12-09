(ns puzzle-6.core)

(def data (clojure.string/split-lines (slurp "c:/cygwin64/home/bru8927/adventofcode/6/data.txt")))

(def field-size 1000)

(def initial  (vec (repeat (* field-size field-size) 0)))

(defn parse-instructions [s]
  (into [] (remove #(= "turn" %1)
                     (clojure.string/split s #"\s|,"))))

(defn custom-range [x1 x2]
  (if (= x1 x2)
    (seq [x1])
    (range x1 (+ 1 x2))))

(defn extend-field [[x1 y1 x2 y2]]
  (for [x (custom-range x1 x2) y (custom-range  y1 y2)] (vector x y)))

(defn flatten-2d [[x y]]
  (+ x (* field-size y)))

(defn generate-field [[instruction xa ya _ xb yb]]
  (let [coord (mapv read-string [xa ya xb yb])]
    {:instruction instruction :field (into [] (map flatten-2d (extend-field coord)))}))

(defn off [v i]
  ;; turns off lights in v by index i
  (reduce #(assoc %1 %2 0) v i))

(defn on [v i]
  ;; turns on lights in v by index i
  (reduce #(assoc %1 %2 1) v i))

(defn toggle [v i]
  (reduce #(assoc %1 %2 (if (= (%1 %2) 0) 1 0)) v i))

(defn play-with-lights [v {:keys [instruction field]}]
  (cond
    (= "on" instruction) (on v field)
    (= "off" instruction) (off v field)
    (= "toggle" instruction) (toggle v field))
  )

(time (->> data
           (map parse-instructions)
           (map generate-field)
           (reduce play-with-lights initial)
           (reduce +)
           clojure.pprint/pprint))

;; Part two, it just gets brighter

(defn on-2 [v i]
  (reduce #(assoc %1 %2 (inc (%1 %2))) v i))

(defn off-2 [v i]
  (reduce #(assoc %1 %2 (max (dec (%1 %2)) 0)) v i))

(defn toggle-2 [v i]
  (reduce #(assoc %1 %2 (+ 2 (%1 %2))) v i))

(defn play-with-lights-2 [v {:keys [instruction field]}]
  (cond
    (= "on" instruction) (on-2 v field)
    (= "off" instruction) (off-2 v field)
    (= "toggle" instruction) (toggle-2 v field)))

(time (->> data
           (map parse-instructions)
           (map generate-field)
           (reduce play-with-lights-2 initial)
           (reduce +)
           clojure.pprint/pprint
           ))
