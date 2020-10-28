(ns datomic-alura3.update
  (:require [datomic-alura3.db :as db]
            [datomic.api :as d]
            [datomic-alura3.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))

(s/set-fn-validation! true)
(def conn (db/recria-banco))
; (db/cria-dados-exemplo conn)

(def dama 
  #:produto{
           :nome "Dama"
           :slug "/slug"
           :preco 15.5M
           :id (model/uuid)})

(db/atualiza-produtos! conn [dama])
(db/produto-por-id (d/db conn) (:produto/id dama))

(db/atualiza-produtos! conn [(assoc dama :produto/slug "/jogo-de-dama")])
(db/produto-por-id (d/db conn) (:produto/id dama))

; joga fora a alteração anterior pos o valor do slug aqui continua o antigo
(db/inserir-produtos! conn [(assoc dama :produto/preco 20M)])
(db/produto-por-id (d/db conn) (:produto/id dama))



