(ns datomic-alura4.aula1
  (:require [datomic-alura4.db.config :as db.config]
            [datomic-alura4.db.produto :as db.produto]
            [datomic.api :as d]
            [datomic-alura4.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))


(s/set-fn-validation! false)
(def conn (db.config/recria-banco))
(db.config/cria-dados-exemplo conn)

(def produtos (db.produto/todos (d/db conn)))
(def primeiro (first produtos))
(def produto-id (:produto/id primeiro))


