(ns datomic-alura.queries
  (:require [datomic-alura.db :as db]
            [datomic.api :as d]
            [datomic-alura.model :as model]
            [clojure.pprint :refer [pprint]]))

(def conn (db/recria-banco))

;seed
(let [computador  (model/novo-produto "Computador Novo" "/computador_novo" 2499.10M)
      impressora  (model/novo-produto "Impressora" "/impressora" 1200M)
      teclado  (model/novo-produto "Teclado Mecanico" "/teclado" 500M)
      calculadora { :produto/nome "Calculadora" :produto/preco 20M}
      produtos [computador impressora teclado calculadora]]
  (d/transact conn produtos))

; snapshot
(def db (d/db conn))

; buscar produtos
(db/todos-produtos db)

; busca com parametro
(db/todos-produtos-por-slug db "/impressora")

; busca campos da entidade
(db/todos-slugs db)

; busca por preco
(db/todos-produtos-por-preco db)


