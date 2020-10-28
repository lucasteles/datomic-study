(ns datomic-alura2.agregar
  (:require [datomic-alura2.db :as db]
            [datomic.api :as d]
            [datomic-alura2.model :as model]
            [clojure.pprint :refer [pprint]]))

(def conn (db/recria-banco-completo!))
(def inserir-entidades! (partial db/inserir-entidades! conn))
(def atribui-categorias! (partial db/atribui-categorias! conn))

(let [; categorias
      eletronicos (model/nova-categoria "Eletronicos")
      esportes (model/nova-categoria "Esportes")
      ; produtos
      computador (model/novo-produto  "Computador Novo" "/computador_novo" 2499.10M)
      xadres (model/novo-produto  "Xadres" "/xadres" 700M)
      celular (model/novo-produto  "Celular" "/celular" 100M)
      teclado (model/novo-produto  "Teclado" "/teclado" 100M)]
  (inserir-entidades! [eletronicos esportes computador xadres celular teclado])
  (atribui-categorias! eletronicos [computador celular teclado])
  (atribui-categorias! esportes [xadres]))

(println "busca nomes de produtos e categorias")
(db/todos-nomes-de-produtos-e-categorias (d/db conn))

(db/resumo-produtos (d/db conn))
(db/resumo-produtos-por-categoria (d/db conn))




