(ns datomic-alura2.nova-entidade
  (:require [datomic-alura2.db :as db]
            [datomic.api :as d]
            [datomic-alura2.model :as model]
            [clojure.pprint :refer [pprint]]))

(def conn (db/recria-banco))
(d/transact conn db/schema-categoria)

(def eletronicos (model/nova-categoria "Eletronicos"))
(def esportes (model/nova-categoria "Esportes"))
@(d/transact conn [eletronicos esportes])

(def categorias (db/todas-categorias (d/db conn)))
categorias

(def computador  (model/novo-produto  "Computador Novo" "/computador_novo" 2499.10M))
(def impressora  (model/novo-produto "Impressora" "/impressora" 1200M))
(def calculadora { :produto/id (model/uuid) :produto/nome "Calculadora" :produto/preco 20M})
(def teclado  (model/novo-produto  "Teclado Mecanico" "/teclado" 500M))

@(d/transact conn [computador impressora teclado calculadora])
