(ns datomic-alura3.func-transactor
  (:require [datomic-alura3.db :as db]
            [datomic.api :as d]
            [datomic-alura3.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))

(s/set-fn-validation! true)
(def conn (db/recria-banco))
(db/instala-funcoes conn)

(db/cria-dados-exemplo conn)
(def produtos (db/todos-produtos (d/db conn)))
(def primeiro (first produtos))
(def id (:produto/id primeiro))

(db/visualizacoes (d/db conn) id)
(db/visualizacao! conn id)

(db/visualizacoes (d/db conn) id)
