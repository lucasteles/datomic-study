(ns datomic-alura3.find-spec
  (:require [datomic-alura3.db :as db]
            [datomic.api :as d]
            [datomic-alura3.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))

(s/set-fn-validation! true)
(def conn (db/recria-banco))
(db/cria-dados-exemplo conn)
(db/todos-produtos-com-estoque (d/db conn))


(def prods (db/todos-produtos (d/db conn)))
(def id (:produto/id (first prods)))
(def id2 (:produto/id (second prods)))
(db/produto-por-id-com-estoque (d/db conn) id)
(db/produto-por-id-com-estoque (d/db conn) id2)

