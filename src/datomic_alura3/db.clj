(ns datomic-alura3.db
  (:require
    [datomic.api :as d]
    [schema.core :as s]
    [clojure.walk :as walk]
    [clojure.set :as cset]
    [datomic-alura3.model :as model]))

(def db-uri "datomic:dev://localhost:4334/ecommerce")

(defn abre-conexao []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apaga-banco []
  (d/delete-database db-uri))


(def schema [{:db/ident       :produto/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O nome de um produto"}

             {:db/ident       :produto/slug
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "O caminho para acessar esse produto via http"}

             {:db/ident       :produto/preco
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc         "O preço de um produto com precisão monetária"}

             {:db/ident       :produto/palavra-chave
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/many }

             {:db/ident       :produto/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}

             {:db/ident       :produto/categoria
              :db/valueType   :db.type/ref
              :db/cardinality :db.cardinality/one}

             {:db/ident       :produto/estoque
              :db/valueType   :db.type/long
              :db/cardinality :db.cardinality/one}

             {:db/ident       :produto/digital
              :db/valueType   :db.type/boolean
              :db/cardinality :db.cardinality/one}


             {:db/ident       :categoria/nome
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one }

             {:db/ident       :categoria/id
              :db/valueType   :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique      :db.unique/identity}
             ])

(defn atribui-categorias! [conn categoria produtos]
  (let [add-categoria
        (fn [produto] [:db/add
                       [:produto/id (:produto/id produto)]
                       :produto/categoria [:categoria/id (:categoria/id categoria)]])
        transactions (map add-categoria produtos)]
    @(d/transact conn transactions)))

; update possivel de schemas parciais
(s/defn atualiza-produtos!
  [conn
   entidades :- [(model/partial-schema model/Produto)]]
  (d/transact conn entidades))

(s/defn atualiza-categorias!
  [conn
   entidades :- [(model/partial-schema model/Categoria)]]
  (d/transact conn entidades))

;;; receber a parte que deveria atualizar como pararmetto
; (defn make-atualizador-entidade [schema]
;   (s/fn
;     ([id-key :- (model/keyof schema)
;       conn
;       entidades :- [schema]]
;      (d/transact conn entidades))
;     ; update parcial, bom para updates concorrentes
;     ([id-key :- (model/keyof schema)
;       conn
;       entidades :- [schema]
;       keywords :- [(model/keyof schema)]]
;      (let [keys-with-id (conj keywords id-key)
;            parcial (map #(select-keys % keys-with-id) entidades)]
;        (d/transact conn parcial)))))

; (def atualiza-produtos!
;   (partial (make-atualizador-entidade model/Produto) :produto/id))

; (def atualiza-categorias!
;   (partial (make-atualizador-entidade model/Categoria) :categoria/id))


; (s/defn atualiza-produtos!
;   ([conn produtos :- [model/Produto]] (d/transact conn produtos))
;   ; update parcial, bom para updates concorrentes
;   ([conn
;     produtos :- [model/Produto]
;     keywords :- [(model/keyof model/Produto)]]
;    (let [keys-with-id (conj keywords :produto/id)
;          parcial (map #(select-keys % keys-with-id) produtos)]
;      (d/transact conn parcial))))

; (s/defn atualiza-categorias!
;   ([conn categorias :- [model/Categoria]] (d/transact conn categorias))
;   ; update parcial, bom para updates concorrentes
;   ([conn
;     categorias :- [model/Categoria]
;     keywords :- [(model/keyof model/Categoria)]]
;    (let [keys-with-id (conj keywords :categoria/id)
;          parcial (map #(select-keys % keys-with-id) categorias)]
;      (d/transact conn parcial))))

(defn cria-dados-exemplo [conn]
  (let [; categorias
        eletronicos (model/nova-categoria "Eletronicos")
        esportes (model/nova-categoria "Esportes")
        ; produtos
        computador (model/novo-produto  "Computador Novo" "/computador_novo" 2499.10M 10)
        xadres (model/novo-produto  "Xadres" "/xadres" 700M 0)
        celular (model/novo-produto  "Celular" "/celular" 100M 12)
        teclado (model/novo-produto  "Teclado" "/teclado" 100M 0)
        jogo (assoc (model/novo-produto  "Jogo digital" "/jogo" 100M 0) :produto/digital true)]
    (atualiza-categorias! conn [eletronicos esportes])
    (atualiza-produtos! conn [computador xadres celular teclado jogo])
    (atribui-categorias! conn eletronicos [computador celular teclado jogo])
    (atribui-categorias! conn esportes [xadres])))


(defn recria-banco []
  (apaga-banco)
  (let [conn (abre-conexao)]
    (d/transact conn schema)
    conn))


(defn remove-deep [key-set data]
  (walk/prewalk (fn [node] (if (map? node)
                             (apply dissoc node key-set)
                             node))
                data))

(defn datomic->entidade [entidades]
  (remove-deep [:db/id] entidades))

; find specs
(s/defn todas-categorias :- [model/Categoria] [db]
  (let [query '[:find [(pull ?e [*]) ...]
                :where [?e :categoria/id]]
        categorias (d/q query db)]
    (datomic->entidade categorias)))

(s/defn produto-por-id :- (s/maybe model/Produto)
  [db id :- java.util.UUID]
  (->> [:produto/id id]
       (d/pull db '[* { :produto/categoria [*]}])
       datomic->entidade
       (#(if (:produto/id %) % nil))))

(s/defn produto-por-id-feio!
  [db id :- java.util.UUID]
  (let [produto (produto-por-id db id)]
    (when (nil? produto)
      (throw (ex-info "Produto nao encontradoo"
                      {:type :errors/not-found :id id})))))

(s/defn todos-produtos  :- [model/Produto] [db]
  (let [query '[:find [(pull ?e [* {:produto/categoria [*]}]) ...]
                :where [?e :produto/id]]
        resultado (d/q query db)]
    (datomic->entidade resultado)))


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

    [(produto-na-categoria ?produto ?nome-categoria)
     [?categoria :categoria/nome ?nome]
     [?produto :produto/categoria ?categoria]]
    ])

(s/defn todos-produtos-com-estoque  :- [model/Produto] [db]
  (let [query '[:find [(pull ?e [* {:produto/categoria [*]}]) ...]
                :in $ %
                :where (pode-vender? ?e)]
        resultado (d/q query db regras)]
    (datomic->entidade resultado)))

(s/defn produto-por-id-com-estoque :- (s/maybe model/Produto)
  [db id :- java.util.UUID]
  (let [query '[:find (pull ?produto [* {:produto/categoria [*]}]) . 
                :in $ % ?id
                :where [?produto :produto/id ?id]
                       (pode-vender? ?produto) ]
        resultado (d/q query db regras id )
        retorno (datomic->entidade resultado)]
    (print resultado)
    (if (:produto/id retorno) retorno nil)))

(s/defn produto-nas-categorias :- [model/Produto]
  [db categorias :- [s/Str]]
  (let [query '[:find [(pull ?produto [* {:produto/categoria [*]}]) ...]
                :in $ % [?nome ...]
                :where (produto-na-categoria ?produto ?nome)]
        resultado (d/q query db regras categorias)]
    (datomic->entidade resultado)))


(s/defn produto-nas-categorias-e-digital :- [model/Produto]
  [db categorias :- [s/Str] digital? :- s/Bool]
  (let [query '[:find [(pull ?produto [* {:produto/categoria [*]}]) ...]
                :in $ % [?nome ...] ?digital?
                :where (produto-na-categoria ?produto ?nome)
                       [?produto :produto/digital ?digital?]]
        resultado (d/q query db regras categorias digital?)]
    (datomic->entidade resultado )))

(s/defn atualiza-preco! 
  [conn
   id :- java.util.UUID 
   preco-antigo :- BigDecimal
   preco-novo :- BigDecimal] 
  (d/transact conn [[:db/cas [:produto/id id] :produto/preco preco-antigo preco-novo]]))


(s/defn atualiza-produtos-cas! 
  [conn
   produto   :- model/Produto
   atualizar :- (model/partial-schema model/Produto)]
  (let [id (:produto/id produto)
        atributos (cset/intersection (-> produto keys set) (-> atualizar keys set))
        to-cas (fn [att] [:db/cas [:produto/id id] att (get produto att) (get atualizar att)])
        txs (map to-cas atributos)]
    (d/transact conn txs)))





