(ns datomic-alura3.update-concorrente
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
           :slug "/dama"
           :preco 15.5M
           :id (model/uuid)})

(db/atualiza-produtos! conn [dama])
(db/produto-por-id (d/db conn) (:produto/id dama))

(defn sleep [v t] (Thread/sleep t) v)

(defn atualiza-preco []
  (println "atualizando preco")
  (let [produto-id (:produto/id dama)
        produto (-> (d/db conn) 
                    (db/produto-por-id produto-id)
                    (sleep 3000)
                    (assoc :produto/preco 999M))
        pedaço (select-keys produto [:produto/preco :produto/id])]
        (db/atualiza-produtos! conn [pedaço])
        (println "preco atualizado")
        produto))

(defn atualiza-slug []
  (println "atualizando slug")
  (let [produto-id (:produto/id dama)
        produto (-> (d/db conn) 
                    (db/produto-por-id produto-id)
                    (sleep 1000)
                    (assoc :produto/slug "/jogo-de-dama"))
        pedaço (select-keys produto [:produto/slug :produto/id])] 
        (db/atualiza-produtos! conn [produto])
        (println "slug atualizado")
        produto))

(defn roda-transacoes [tx]
  (let [ futuros (mapv #(future (%)) tx)]
    (pprint (map deref futuros))
    (println "Resultado")
    (db/produto-por-id (d/db conn) (:produto/id dama))))

(roda-transacoes [atualiza-preco atualiza-slug])


