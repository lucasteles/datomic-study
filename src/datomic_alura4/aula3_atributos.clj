(ns datomic-alura4.aula3
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

(def venda1 (db.venda/adiciona! conn produto-id 3))
(def venda2 (db.venda/adiciona! conn produto-id 4))
(def venda3 (db.venda/adiciona! conn produto-id 8))

(db.venda/altera-situacao! conn venda2 "preparando")
(db.venda/altera-situacao! conn venda1 "preparando")
(db.venda/altera-situacao! conn venda3 "preparando")
(db.venda/altera-situacao! conn venda1 "entregue")
(db.venda/altera-situacao! conn venda1 "devolvido")

(db.venda/todas-completas (d/db conn))

(def db (d/db conn))
(db.venda/historico db venda1)
(db.venda/historico db venda2)
(db.venda/historico db venda3)


(db.venda/cancela! conn venda2)

(db.venda/todas2 (d/db conn))
(db.venda/todas-canceladas2 (d/db conn))
(db.venda/todas-nao-canceladas2 (d/db conn))


