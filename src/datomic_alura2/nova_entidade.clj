(ns datomic-alura2.nova-entidade
  (:require [datomic-alura2.db :as db]
            [datomic.api :as d]
            [datomic-alura2.model :as model]
            [clojure.pprint :refer [pprint]]))

(def conn (db/recria-banco))

(println "aplica novo squema com ref de categorias")
(d/transact conn db/schema-categoria)

(def id-cat-eletronicos (model/uuid))
(def id-cat-esportes (model/uuid))

(let [eletronicos (model/nova-categoria id-cat-eletronicos "Eletronicos")
      esportes (model/nova-categoria id-cat-esportes "Esportes")]
  (println "transaciona categorias")
  @(d/transact conn [eletronicos esportes]))


(println "lista categorias")
(db/todas-categorias (d/db conn))

(def id-computador (model/uuid))
(def id-xadres (model/uuid))

(let [computador  (model/novo-produto id-computador "Computador Novo" "/computador_novo" 2499.10M)
      xadres (model/novo-produto id-xadres "Xadres" "/xadres" 700M)]
  @(d/transact conn [computador xadres]))

(println "vincula referencia de categoria com produto")
(d/transact conn [[:db/add
                  [:produto/id id-computador]
                  :produto/categoria [:categoria/id id-cat-eletronicos]]

                  [:db/add
                  [:produto/id id-xadres]
                  :produto/categoria [:categoria/id id-cat-esportes]]
                  ])


(println "busca produtos")
(db/todos-produtos (d/db conn))
