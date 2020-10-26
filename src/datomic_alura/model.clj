(ns datomic-alura.model)

(defn novo-produto [nome slug preco]
  #:produto {:nome nome
             :slug slug
             :preco preco})


