(ns datomic-alura3.db
  (:require
    [datomic.api :as d]
    [schema.core :as s]
    [clojure.walk :as walk]
    [datomic-alura3.model :as model]))

(def db-uri "datomic:dev://localhost:4334/ecommerce")

(defn abre-conexao []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn apaga-banco []
  (d/delete-database db-uri))


(def schema [{:db/ident         :produto/nome
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              :db/doc           "O nome de um produto"}

             {:db/ident         :produto/slug
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/one
              :db/doc           "O caminho para acessar esse produto via http"}

             {:db/ident         :produto/preco
              :db/valueType     :db.type/bigdec
              :db/cardinality   :db.cardinality/one
              :db/doc           "O preço de um produto com precisão monetária"}

             {:db/ident         :produto/palavra-chave
              :db/valueType     :db.type/string
              :db/cardinality   :db.cardinality/many }

             {:db/ident         :produto/id
              :db/valueType     :db.type/uuid
              :db/cardinality   :db.cardinality/one
              :db/unique        :db.unique/identity}

             {:db/ident       :produto/categoria
              :db/valueType   :db.type/ref
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


(defn make-atualizador-entidade [schema]
  (s/fn 
    ([id-key :- (model/keyof schema)
      conn
      entidades :- [schema]]
     (d/transact conn entidades))
    ; update parcial, bom para updates concorrentes
    ([id-key :- (model/keyof schema)
      conn
      entidades :- [schema]
      keywords :- [(model/keyof schema)]]
     (let [keys-with-id (conj keywords id-key)
           parcial (map #(select-keys % keys-with-id) entidades)]
       (d/transact conn parcial)))))

(def atualiza-produtos! 
  (partial (make-atualizador-entidade model/Produto) :produto/id))

(def atualiza-categorias! 
  (partial (make-atualizador-entidade model/Categoria) :categoria/id))


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
        computador (model/novo-produto  "Computador Novo" "/computador_novo" 2499.10M)
        xadres (model/novo-produto  "Xadres" "/xadres" 700M)
        celular (model/novo-produto  "Celular" "/celular" 100M)
        teclado (model/novo-produto  "Teclado" "/teclado" 100M)]
    (atualiza-categorias! conn [eletronicos esportes])
    (atualiza-produtos! conn [computador xadres celular teclado])
    (atribui-categorias! conn eletronicos [computador celular teclado])
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

(s/defn produto-por-id :- model/Produto
  [db id :- java.util.UUID]
  (->> [:produto/id id]
       (d/pull db '[* { :produto/categoria [*]}])
       (datomic->entidade)))

(s/defn todos-produtos  :- [model/Produto] [db]
  (let [query '[:find [(pull ?e [* {:produto/categoria [*]}]) ...]
                :where [?e :produto/id]]
        resultado (d/q query db)]
    (datomic->entidade resultado)))

