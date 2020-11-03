(ns datomic-alura4.db.produto
  (:require
    [datomic.api :as d]
    [schema.core :as s]
    [clojure.set :as cset]
    [datomic-alura4.db.entidade :as entidade]
    [datomic-alura4.model :as model]))

(s/defn atualiza!
  [conn
   entidades :- [(model/partial-schema model/Produto)]]
  (d/transact conn entidades))


(s/defn por-id :- (s/maybe model/Produto)
  [db id :- java.util.UUID]
  (->> [:produto/id id]
       (d/pull db '[* {:produto/categoria [*]}])
       entidade/datomic->entidade
       (#(if (:produto/id %) % nil))))

(s/defn por-id-feio!
  [db id :- java.util.UUID]
  (let [produto (por-id db id)]
    (when (nil? produto)
      (throw (ex-info "Produto nao encontradoo"
                      {:type :errors/not-found :id id})))))

(s/defn todos  :- [model/Produto] [db]
  (let [query '[:find [(pull ?e [* {:produto/categoria [*]}]) ...]
                :where [?e :produto/id]]
        resultado (d/q query db)]
    (entidade/datomic->entidade resultado)))


; definido regras
(def regras
  '[[(estoque ?produto ?estoque)
     [?produto :produto/estoque ?estoque]]
    [(estoque ?produto ?estoque)
     [?produto :produto/digital true]
     [(ground 1) ?estoque]]
    [(pode-vender? ?produto)
     (estoque ?produto ?estoque)
     [(> ?estoque 0)]]

    [(na-categoria ?produto ?nome-categoria)
     [?categoria :categoria/nome ?nome-categoria]
     [?produto :produto/categoria ?categoria]]
    ])

(s/defn todos-com-estoque  :- [model/Produto] [db]
  (let [query '[:find [(pull ?e [* {:produto/categoria [*]}]) ...]
                :in $ %
                :where (pode-vender? ?e)]
        resultado (d/q query db regras)]
    (entidade/datomic->entidade resultado)))

(s/defn por-id-com-estoque :- (s/maybe model/Produto)
  [db id :- java.util.UUID]
  (let [query '[:find (pull ?produto [* {:produto/categoria [*]}]) .
                :in $ % ?id
                :where [?produto :produto/id ?id]
                (pode-vender? ?produto) ]
        resultado (d/q query db regras id )
        retorno (entidade/datomic->entidade resultado)]
    (print resultado)
    (if (:produto/id retorno) retorno nil)))

(s/defn nas-categorias :- [model/Produto]
  [db categorias :- [s/Str]]
  (let [query '[:find [(pull ?produto [* {:produto/categoria [*]}]) ...]
                :in $ % [?nome ...]
                :where (na-categoria ?produto ?nome)]
        resultado (d/q query db regras categorias)]
    (entidade/datomic->entidade resultado)))


(s/defn nas-categorias-e-digital :- [model/Produto]
  [db categorias :- [s/Str] digital? :- s/Bool]
  (let [query '[:find [(pull ?produto [* {:produto/categoria [*]}]) ...]
                :in $ % [?nome ...] ?digital?
                :where (na-categoria ?produto ?nome) 
                [?produto :produto/digital ?digital?]]
        resultado (d/q query db regras categorias digital?)]
    (entidade/datomic->entidade resultado )))

(s/defn atualiza-preco!
  [conn
   id :- java.util.UUID
   preco-antigo :- BigDecimal
   preco-novo :- BigDecimal]
  (d/transact conn [[:db/cas [:produto/id id] :produto/preco preco-antigo preco-novo]]))


(s/defn atualiza-cas!
  [conn
   produto   :- model/Produto
   atualizar :- (model/partial-schema model/Produto)]
  (let [id (:produto/id produto)
        atributos (cset/intersection (-> produto keys set) (-> atualizar keys set))
        to-cas (fn [att] [:db/cas [:produto/id id] att (get produto att) (get atualizar att)])
        txs (map to-cas atributos)]
    (d/transact conn txs)))

(defn total-de [db]
  (d/q '[:find (count ?produto) .
         :where [?produto :produto/nome]]
       db))

(s/defn remover!
  [conn id :- java.util.UUID ]
  (d/transact conn [[:db/retractEntity
                     [:produto/id id]]]))



(s/defn visualizacoes [db id :- java.util.UUID]
  (or (d/q '[:find ?visualizacoes .
             :in $ ?id
             :where [?produto :produto/visualizacoes ?visualizacoes]]
           db id)
      0))

(def incrementa-visualizacao
  #db/fn {:lang :clojure
          :params [db id]
          :code
          (let [visualizacoes (d/q '[:find ?visualizacoes .
                                     :in $ ?id
                                     :where [?produto :produto/visualizacoes ?visualizacoes]]
                                   db id)
                atual (or visualizacoes 0)
                novo-valor (inc atual)]
            [{:produto/id            id
              :produto/visualizacoes novo-valor}])})

(defn instala-funcoes [conn] 
  (d/transact conn [{:db/doc "funcao que incremente visualizacao de produto"
                     :db/ident :incrementa-visualizacao
                     :db/fn incrementa-visualizacao}]))

(s/defn visualizacao!
  [conn
   id :- java.util.UUID]
  (d/transact conn [[:incrementa-visualizacao id]]))

