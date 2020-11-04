(ns datomic-alura4.db.venda
  (:require
    [datomic.api :as d]
    [schema.core :as s]
    [datomic-alura4.model :as model]
    [datomic-alura4.db.entidade :as entidade]))

(defn adiciona!
  [conn produto-id quantidade]
  (let [id (model/uuid)]
    (d/transact conn [{:db/id   "venda"
                       :venda/produto   [:produto/id produto-id]
                       :venda/quantidade   quantidade
                       :venda/id   id
                       :venda/situacao "nova"}])
    id))


(s/defn custo-errado
  [db venda-id :- java.util.UUID]
  (d/q '[:find (sum ?preco-venda) .
         :in $ ?id
         :where
         [?venda :venda/id ?id]
         [?venda :venda/quantidade ?quantidade]
         [?venda :venda/produto ?produto]
         [?produto :produto/preco ?preco]
         [(* ?preco ?quantidade) ?preco-venda]
         ]
       db venda-id))

(s/defn custo
  [db venda-id :- java.util.UUID]
  (let [instante-venda (d/q '[:find ?instante .
                              :in $ ?id
                              :where [_ :venda/id ?id ?tx true]
                              [?tx :db/txInstant ?instante]]
                            db venda-id)]

    (d/q '[:find (sum ?preco-venda) .
           :in $ ?id
           :where
           [?venda :venda/id ?id]
           [?venda :venda/quantidade ?quantidade]
           [?venda :venda/produto ?produto]
           [?produto :produto/preco ?preco]
           [(* ?preco ?quantidade) ?preco-venda]
           ]
         (d/as-of db instante-venda) venda-id)))


(s/defn cancela-deletando! [conn venda-id :- java.util.UUID]
  (d/transact conn [[:db/retractEntity [:venda/id venda-id]]]))

(s/defn todas-completas [db] :- [model/Venda]
  (d/q '[:find [(pull ?venda (*)) ...]
         :where [?venda :venda/id ?id]] db))

(s/defn todas-nao-canceladas [db] :- [model/Venda]
  (d/q '[:find [?id ...]
         :where [?venda :venda/id ?id]] db))

(s/defn todas [db] :- [model/Venda]
  (d/q '[:find [?id ...]
         :where [?venda :venda/id ?id _ true]] 
       (d/history db)))


(s/defn todas-canceladas [db] :- [model/Venda]
  (d/q '[:find [?id ...]
         :where [?venda :venda/id ?id _ false]] 
       (d/history db)))

(defn altera-situacao! [conn venda-id situacao] 
  (d/transact conn [{:venda/id      venda-id 
                     :venda/situacao situacao}]))


(defn historico [db venda-id]
  (->> (d/q '[:find ?instante ?situacao
              :in $ ?id
              :where 
              [?venda :venda/id ?id]
              [?venda :venda/situacao ?situacao ?tx true]
              [?tx :db/txInstant ?instante]]
             (d/history db) venda-id)
       (sort-by first)))


(s/defn cancela! [conn venda-id :- java.util.UUID]
  (altera-situacao! conn venda-id "cancelada"))

(s/defn todas-nao-canceladas2 [db] :- [model/Venda]
  (d/q '[:find [?id ...]
         :where 
         [?venda :venda/id ?id]
         [?venda :venda/situacao ?situacao]
         [(not= ?situacao "cancelada")]] db))

(s/defn todas2 [db] :- [model/Venda]
  (d/q '[:find [?id ...]
         :where [?venda :venda/id ?id]] 
       db))


(s/defn todas-canceladas2 [db] :- [model/Venda]
  (d/q '[:find [?id ...]
         :where 
         [?venda :venda/id ?id]
         [?venda :venda/situacao "cancelada"]] db))


(defn historico-geral [db desde]
  (->> (d/q '[:find ?instante ?situacao 
              :where 
              [?venda :venda/situacao ?situacao ?tx true]
              [?tx :db/txInstant ?instante]]
             (d/since db desde))
       (sort-by first)))


(defn historico-geral-combinado [db desde]
  (->> (let [filtrado (d/since db desde)] 
         (d/q '[:find ?instante ?situacao ?id
                :keys instante situacao id
                :in $ $filtrado
                :where 
                [$ ?venda :venda/id ?id]
                [$filtrado ?venda :venda/situacao ?situacao ?tx true]
                [$filtrado ?tx :db/txInstant ?instante]]
               db filtrado))
       (sort-by first)))
