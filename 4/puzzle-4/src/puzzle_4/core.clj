(ns puzzle-4.core)

(def
  secret "bgvyzdsv")

(defn md5 [x]
  (apply str
         (map (partial format "%02x")
              (.digest (doto (java.security.MessageDigest/getInstance "MD5")
                         .reset
                         (.update (.getBytes (str secret x))))))))

(defn low-values [x]
  (re-matches #"^000000.*" x))

(defn indices [pred coll]
   (keep-indexed #(when (pred %2) %1) coll))


(def hashes (into [] (map low-values (map md5 (take 10000000 (range))))))

(clojure.pprint/pprint (first (indices string? hashes )))

(re-matches #"^00000.*" "100000as2j3jjr")
