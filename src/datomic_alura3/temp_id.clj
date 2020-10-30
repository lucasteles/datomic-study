(ns datomic-alura3.temp-id
  (:require [datomic-alura3.db :as db]
            [datomic.api :as d]
            [datomic-alura3.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))

(s/set-fn-validation! true)
(def conn (db/recria-banco))
(db/cria-dados-exemplo conn)
(def produtos (db/todos-produtos (d/db conn)))
(def primeiro (first produtos))
(def id (:produto/id primeiro))

(db/add-variacao! conn id "Season pass" 40M)
(db/add-variacao! conn id "Season pass 4 anos" 60M)

(db/todos-produtos (d/db conn) )
