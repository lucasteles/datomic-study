(ns datomic-alura3.maybe
  (:require [datomic-alura3.db :as db]
            [datomic.api :as d]
            [datomic-alura3.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))

(s/set-fn-validation! false)
(def conn (db/recria-banco))
(db/cria-dados-exemplo conn)

(def produtos (db/todos-produtos (d/db conn)))
(def primeiro (first produtos))
(def produto-id (:produto/id primeiro))


(db/produto-por-id (d/db conn) produto-id)
(db/produto-por-id (d/db conn) (model/uuid))
(db/produto-por-id-feio! (d/db conn) (model/uuid))

