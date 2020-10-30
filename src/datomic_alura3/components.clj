(ns datomic-alura3.components
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

; tras direto o filho por causa do isComponent
(db/produto-por-id (d/db conn) id)


(db/total-de-produtos (d/db conn))

; remove as variações 
(db/remover-produto! conn id)

(db/total-de-produtos (d/db conn))
