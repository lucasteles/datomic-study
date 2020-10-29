(ns datomic-alura3.maybe
  (:require [datomic-alura3.db :as db]
            [datomic.api :as d]
            [datomic-alura3.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))

(s/set-fn-validation! true)
(def conn (db/recria-banco))
(db/cria-dados-exemplo conn)

(def produtos (db/todos-produtos (d/db conn)))
(def produto (first produtos))

(def id (:produto/id produto))
(db/atualiza-preco! conn id (:produto/preco produto) 30M)
(db/atualiza-preco! conn id 10M 30M)


(def produto (db/produto-por-id (d/db conn) id))
(db/atualiza-produtos-cas! conn produto #:produto{ :preco 42M :estoque 101 })
(db/produto-por-id (d/db conn) id)
