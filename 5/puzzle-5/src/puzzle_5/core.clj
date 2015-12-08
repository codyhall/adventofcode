(ns puzzle-5.core)

(def data (clojure.string/split-lines
           (slurp "c:/cygwin64/home/bru8927/adventofcode/5/data.txt")))

(def vowels #{\a \e \i \o \u})

(def bad (into #{} (map vec '("ab" "cd" "pq" "xy"))))

(def repeat
  (into #{} (map vec
                 (partition 2 (interleave (map char (range 97 123))
                                          (map char (range 97 123)))))))

(defn count-vowels [x]
  ;; Returns the count of vowels in input string
  (count (remove nil? (map vowels (seq x)))))

(defn bad-string? [x]
  ;; Returns true if input string contains a "bad" sub-string
  (empty?
   (clojure.set/intersection
    bad x)))

(defn repeat-letter? [x]
  (not (empty?
        (clojure.set/intersection
         repeat x))))

;; Nice part 1
(defn nice [s]
  (let [two-at-a-time (into #{} (map vec (partition 2 1 s)))
        is-vowely (> (count-vowels s) 2)
        is-repeat (repeat-letter? two-at-a-time)
        not-bad (bad-string? two-at-a-time)]
    (and is-vowely is-repeat not-bad)))

(count (filter true? (map nice data))) ;; 236


;; Nice part 2

(defn pair-twice? [s]
  ;; pair-twich occurs when any two sequential letters are repeated again with no overlapping
  ;; Example "abzyxab" but not "aaa"
  (if (< (count s) 4)
    false
    (let [first-token #{(vec (take 2 s))}
          check-tokens (into #{} (map vec (partition 2 1 (rest (rest s)))))]
      (if (not (empty? (clojure.set/intersection first-token check-tokens)))
        true
        (pair-twice (rest s)))
      )))

(defn sandwich? [s]
  ;; The sandwich is created when 3 letters create bread, meat, bread.
  ;; Example 'xyx' or 'uru' but not 'rru'
  (if (< (count s) 3)
    false
    (let [first-token (first s)
          third-token (second (rest s))]
      (if (= first-token third-token)
        true
        (sandwich? (rest s))))))

(defn nice-2? [s]
  (and (pair-twice? s) (sandwich? s)))

(count (remove false?  (map nice-2? data))) ;; 51
