(ns puzzle-6.core)

(def data (clojure.string/split-lines (slurp "/Users/Hallca/adventofcode/6/data.txt")))

(def test-data "turn off 2,2 through 2,9")

(def field-size 1000)

(def instructions {"on" on "off" off "toggle" toggle})

(def initial  (vec (repeat (* field-size field-size) 0)))

(defn parse-instructions [s]
  (into [] (remove #(= "turn" %1)
                     (clojure.string/split s #"\s|,"))))

(defn custom-range [x1 x2]
  (if (= x1 x2)
    (seq [x1])
    (range x1 x2)))

(defn extend-field [[x1 y1 x2 y2]]
  (for [x (custom-range x1 (+ 1 x2)) y (custom-range  y1 (+ 1 y2))] (vector x y)))


(defn flatten-2d [[x y]]
  (+ x (* field-size y)))

(defn generate-field [[instruction xa ya _ xb yb]]
  (let [coord (mapv read-string [xa ya xb yb])]
    {:instuction instruction :field (into [] (map flatten-2d (extend-field coord)))})
  )

(defn off [v i]
  ;; turns off lights in v by index i
  (reduce #(assoc %1 %2 0) v i)
  )

(defn on [v i]
  ;; turns on lights in v by index i
  (reduce #(assoc %1 %2 1) v i))

(defn toggle [v i]
  (reduce #(assoc %1 %2 (if (= (%1 %2) 0) 1 0)) v i))


(on (toggle (toggle initial [1 2 3]) [2]) [6 7 8 9 10])

(defn play-with-lights [v {instruction :instruction field :field}]
  (cond
   (= "on") (on v field)
   (= "off") (off v field)
   (= "toggle") (toggle v field)))

(->> data
     (map parse-instructions)
     (map generate-field)
     (reduce play-with-lights initial)
     (reduce +))


(->> ["turn on 0,0 through 999,999" "turn off 0,0 through 0,999"]
     (map parse-instructions)
     (map generate-field)
     (reduce play-with-lights initial)
)
