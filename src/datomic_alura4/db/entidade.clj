(ns datomic-alura4.db.entidade
  (:require
    [clojure.walk :as walk]
    [datomic-alura4.model :as model]))

(defn remove-deep [key-set data]
  (walk/prewalk (fn [node] (if (map? node)
                             (apply dissoc node key-set)
                             node))
                data))

(defn datomic->entidade [entidades]
  (remove-deep [:db/id] entidades))

