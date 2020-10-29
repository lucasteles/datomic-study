(ns datomic-alura3.find-spec
  (:require [datomic-alura3.db :as db]
            [datomic.api :as d]
            [datomic-alura3.model :as model]
            [schema.core :as s]
            [clojure.pprint :refer [pprint]]))

(s/set-fn-validation! false)
(def conn (db/recria-banco))
(db/cria-dados-exemplo conn)


(db/produto-nas-categorias (d/db conn) ["Esportes", "Alimentação"])
(db/produto-nas-categorias (d/db conn) ["Esportes", "Eletronicos"])
(db/produto-nas-categorias (d/db conn) [])

(db/produto-nas-categorias-e-digital (d/db conn) ["Eletronicos", "Alimentação"] true)
(db/produto-nas-categorias-e-digital (d/db conn) ["Esportes", "Alimentação"] false)
