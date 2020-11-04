(ns datomic-alura4.aula1
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

(def venda1 (db.venda/adiciona! conn produto-id 3))
(def venda2 (db.venda/adiciona! conn produto-id 4))
(pprint venda1)


(db.venda/custo (d/db conn) venda1)

@(db.produto/atualiza! conn [#:produto {:id produto-id
                                        :preco 300M}])

;preco atualizado
(db.venda/custo-errado (d/db conn) venda1)
(db.venda/custo (d/db conn) venda1)

(pprint "Antes")
(db.venda/todas-nao-canceladas (d/db conn))
(db.venda/cancela-deletando! conn venda1)
(pprint "Depois")
(db.venda/todas-nao-canceladas (d/db conn))

(pprint "Todas")
(db.venda/todas (d/db conn))

(pprint "Canceladas")
(db.venda/todas-canceladas (d/db conn))
