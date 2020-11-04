(ns datomic-alura4.aula2
  (:require [datomic-alura4.db.config :as db.config]
            [datomic-alura4.db.produto :as db.produto]
            [datomic-alura4.db.venda :as db.venda]
            [datomic.api :as d]
            [datomic-alura4.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))


(s/set-fn-validation! true)
(def conn (db.config/recria-banco))
(db.config/cria-dados-exemplo conn)

(def produtos (db.produto/todos (d/db conn)))
(def primeiro (first produtos))
(def produto-id (:produto/id primeiro))

(pprint primeiro)


@(db.produto/atualiza! conn [#:produto {:id produto-id
                                        :preco 300M}])

@(db.produto/atualiza! conn [#:produto {:id produto-id
                                        :preco 100M}])

(db.produto/historico-precos (d/db conn) produto-id)
