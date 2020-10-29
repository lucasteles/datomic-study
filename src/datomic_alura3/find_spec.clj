(ns datomic-alura3.find-spec
  (:require [datomic-alura3.db :as db]
            [datomic.api :as d]
            [datomic-alura3.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))

(s/set-fn-validation! false)
(def conn (db/recria-banco))
(db/cria-dados-exemplo conn)

(def prods (db/todos-produtos-com-estoque (d/db conn)))
(def id (:produto/id (first prods)))
(db/produto-por-id-com-estoque (d/db conn) id)
