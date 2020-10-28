(ns datomic-alura3.schema
  (:require [datomic-alura3.db :as db]
            [datomic.api :as d]
            [datomic-alura3.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))

(s/set-fn-validation! true)
(def conn (db/recria-banco))
(db/cria-dados-exemplo conn)

(db/todos-produtos (d/db conn))
(db/todas-categorias (d/db conn))

