(ns datomic-alura4.db.categoria
  (:require
    [datomic.api :as d]
    [schema.core :as s]
    [datomic-alura4.db.entidade :as entidade]
    [datomic-alura4.model :as model]))

(defn atribui! [conn categoria produtos]
  (let [add-categoria
        (fn [produto] [:db/add
                       [:produto/id (:produto/id produto)]
                       :produto/categoria [:categoria/id (:categoria/id categoria)]])
        transactions (map add-categoria produtos)]
    @(d/transact conn transactions)))

(s/defn atualiza!
  [conn
   entidades :- [(model/partial-schema model/Categoria)]]
  (d/transact conn entidades))

(s/defn todas! :- [model/Categoria] [db]
  (let [query '[:find [(pull ?e [*]) ...]
                :where [?e :categoria/id]]
        categorias (d/q query db)]
    (entidade/datomic->entidade categorias)))


