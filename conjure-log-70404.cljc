; --------------------------------------------------------------------------------
; eval (current-form): (db/atualiza-produtos! conn [dama])
; (err) Execution error at schema.core/parse-sequence-schema (core.clj:932).
; (err) [#object[datomic_alura3.model$partial_schema 0x77612d63 "datomic_alura3.model$partial_schema@77612d63"] {:produto/nome java.lang.String, :produto/slug java.lang.String, :produto/preco java.math.BigDecimal, :produto/id java.util.UUID, #schema.core.OptionalKey{:k :produto/palavra-chave} [java.lang.String], #schema.core.OptionalKey{:k :produto/categoria} #:categoria{:nome java.lang.String, :id java.util.UUID}}] is not a valid sequence schema; a valid sequence schema consists of zero or more `one` elements, followed by zero or more `optional` elements, followed by an optional schema that will match the remaining elements.
; --------------------------------------------------------------------------------
; eval (buf): ...lura/datomic_alura/src/datomic_alura3/model.clj
nil
#'datomic-alura3.model/keyof
#'datomic-alura3.model/partial-schema
#'datomic-alura3.model/Categoria
#'datomic-alura3.model/Produto
#'datomic-alura3.model/uuid
#'datomic-alura3.model/novo-produto
#'datomic-alura3.model/nova-categoria
; --------------------------------------------------------------------------------
; eval (buf): ...c-alura/datomic_alura/src/datomic_alura3/db.clj
nil
#'datomic-alura3.db/db-uri
#'datomic-alura3.db/abre-conexao
#'datomic-alura3.db/apaga-banco
#'datomic-alura3.db/schema
#'datomic-alura3.db/atribui-categorias!
#'datomic-alura3.db/atualiza-produtos!
#'datomic-alura3.db/atualiza-categorias!
#'datomic-alura3.db/cria-dados-exemplo
#'datomic-alura3.db/recria-banco
#'datomic-alura3.db/remove-deep
#'datomic-alura3.db/datomic->entidade
#'datomic-alura3.db/todas-categorias
#'datomic-alura3.db/produto-por-id
#'datomic-alura3.db/todos-produtos
; --------------------------------------------------------------------------------
; eval (buf): ...alura/src/datomic_alura3/update_concorrente.clj
nil
nil
#'datomic-alura3.update-concorrente/conn
#'datomic-alura3.update-concorrente/dama
#<promise$settable_future$reify__4875@aaae871: 
  {:db-before datomic.db.Db@986551b2,
   :db-after datomic.db.Db@33d1c721,
   :tx-data
   [#datom[13194139534313 50 #inst "2020-10-29T14:15:58.826-00:00" 13194139534313 true] #datom[17592186045418 72 "Dama" 13194139534313 true] #datom[17592186045418 73 "/dama" 13194139534313 true] #datom[17592186045418 74 15.5M 13194139534313 true] #datom[17592186045418 76 #uuid "eb410fdc-5dba-4cc8-9e8f-8b5a8dcf242f" 13194139534313 true]],
   :tempids {-9223301668109597699 17592186045418}}>
#:produto{:nome "Dama",
          :slug "/dama",
          :preco 15.5M,
          :id #uuid "eb410fdc-5dba-4cc8-9e8f-8b5a8dcf242f"}
#'datomic-alura3.update-concorrente/sleep
#'datomic-alura3.update-concorrente/atualiza-preco
#'datomic-alura3.update-concorrente/atualiza-slug
#'datomic-alura3.update-concorrente/roda-transacoes
; (out) atualizando slug
; (out) atualizando preco
; (out) slug atualizado
; (out) preco atualizado
; (out) (#:produto{:nome "Dama",
; (out)            :slug "/dama",
; (out)            :preco 999M,
; (out)            :id #uuid "eb410fdc-5dba-4cc8-9e8f-8b5a8dcf242f"}
; (out)  #:produto{:nome "Dama",
; (out)            :slug "/jogo-de-dama",
; (out)            :preco 15.5M,
; (out)            :id #uuid "eb410fdc-5dba-4cc8-9e8f-8b5a8dcf242f"})
; (out) Resultado
#:produto{:nome "Dama",
          :slug "/jogo-de-dama",
          :preco 999M,
          :id #uuid "eb410fdc-5dba-4cc8-9e8f-8b5a8dcf242f"}
; --------------------------------------------------------------------------------
; eval (buf): ...lura/datomic_alura/src/datomic_alura3/maybe.clj
nil
nil
#'datomic-alura3.maybe/conn
{:db-before datomic.db.Db@6609138d,
 :db-after datomic.db.Db@c2cc214c,
 :tx-data
 [#datom[13194139534322 50 #inst "2020-10-29T14:31:43.476-00:00" 13194139534322 true] #datom[17592186045422 77 17592186045419 13194139534322 true]],
 :tempids {}}
#'datomic-alura3.maybe/produtos
#'datomic-alura3.maybe/primeiro
#'datomic-alura3.maybe/produto-id
#:produto{:nome "Computador Novo",
          :slug "/computador_novo",
          :preco 2499.10M,
          :id #uuid "f00d66f2-4ada-48f6-83cb-ea9f6d92b344",
          :categoria
          #:categoria{:nome "Eletronicos",
                      :id #uuid "46a2a168-8941-458b-8491-ce794db37af7"}}
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) produto-id)
#:produto{:nome "Computador Novo",
          :slug "/computador_novo",
          :preco 2499.10M,
          :id #uuid "f00d66f2-4ada-48f6-83cb-ea9f6d92b344",
          :categoria
          #:categoria{:nome "Eletronicos",
                      :id #uuid "46a2a168-8941-458b-8491-ce794db37af7"}}
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) (model/uuid))
; (err) Execution error (ExceptionInfo) at datomic-alura3.db/eval11323$produto-por-id (form-init8991922336725519653.clj:155).
; (err) Output of produto-por-id does not match schema: 
; (err) 
; (err) 	   #:produto{:nome missing-required-key, :slug missing-required-key, :preco missing-required-key, :id missing-required-key}  
; (err) 
; (err) 
; --------------------------------------------------------------------------------
; eval (current-form): (s/set-fn-validation! false)
nil
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) (model/uuid))
{}
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn produto-por-id :- model/Produto [db id ...
#'datomic-alura3.db/produto-por-id
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) (model/uuid))
#object[datomic_alura3.db$eval11465$produto_por_id__11470$fn__11471$fn__11472 0x6c4230a "datomic_alura3.db$eval11465$produto_por_id__11470$fn__11471$fn__11472@6c4230a"]
; --------------------------------------------------------------------------------
; eval (buf): ...lura/datomic_alura/src/datomic_alura3/maybe.clj
nil
nil
#'datomic-alura3.maybe/conn
{:db-before datomic.db.Db@277be68f,
 :db-after datomic.db.Db@baa18262,
 :tx-data
 [#datom[13194139534322 50 #inst "2020-10-29T14:34:28.049-00:00" 13194139534322 true] #datom[17592186045422 77 17592186045419 13194139534322 true]],
 :tempids {}}
#'datomic-alura3.maybe/produtos
#'datomic-alura3.maybe/primeiro
#'datomic-alura3.maybe/produto-id
; (err) Execution error (ExceptionInfo) at datomic-alura3.db/eval11465$produto-por-id (form-init8991922336725519653.clj:156).
; (err) Output of produto-por-id does not match schema: 
; (err) 
; (err) 	   (not (map? a-datomic_alura3.db$eval11465$produto_por_id__11470$fn__11471$fn__11472))  
; (err) 
; (err) 
; (err) Execution error (ExceptionInfo) at datomic-alura3.db/eval11465$produto-por-id (form-init8991922336725519653.clj:156).
; (err) Output of produto-por-id does not match schema: 
; (err) 
; (err) 	   (not (map? a-datomic_alura3.db$eval11465$produto_por_id__11470$fn__11471$fn__11472))  
; (err) 
; (err) 
; --------------------------------------------------------------------------------
; eval (current-form): (ns datomic-alura3.maybe (:require [datomic-alu...
nil
; --------------------------------------------------------------------------------
; eval (current-form): (def conn (db/recria-banco))
#'datomic-alura3.maybe/conn
; --------------------------------------------------------------------------------
; eval (current-form): (db/cria-dados-exemplo conn)
{:db-before datomic.db.Db@e4566c4d,
 :db-after datomic.db.Db@6980564f,
 :tx-data
 [#datom[13194139534322 50 #inst "2020-10-29T14:34:39.140-00:00" 13194139534322 true] #datom[17592186045422 77 17592186045419 13194139534322 true]],
 :tempids {}}
; --------------------------------------------------------------------------------
; eval (current-form): (def produtos (db/todos-produtos (d/db conn)))
#'datomic-alura3.maybe/produtos
; --------------------------------------------------------------------------------
; eval (current-form): (def primeiro (first produtos))
#'datomic-alura3.maybe/primeiro
; --------------------------------------------------------------------------------
; eval (current-form): (def produto-id (:produto/id primeiro))
#'datomic-alura3.maybe/produto-id
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) produto-id)
; (err) Execution error (ExceptionInfo) at datomic-alura3.db/eval11465$produto-por-id (form-init8991922336725519653.clj:156).
; (err) Output of produto-por-id does not match schema: 
; (err) 
; (err) 	   (not (map? a-datomic_alura3.db$eval11465$produto_por_id__11470$fn__11471$fn__11472))  
; (err) 
; (err) 
; --------------------------------------------------------------------------------
; eval (current-form): (s/set-fn-validation! false)
nil
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) produto-id)
#object[datomic_alura3.db$eval11465$produto_por_id__11470$fn__11471$fn__11472 0x308b5bca "datomic_alura3.db$eval11465$produto_por_id__11470$fn__11471$fn__11472@308b5bca"]
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn produto-por-id :- model/Produto [db id ...
#'datomic-alura3.db/produto-por-id
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) produto-id)
#:produto{:nome "Computador Novo",
          :slug "/computador_novo",
          :preco 2499.10M,
          :id #uuid "3b1ee73c-0c95-4dfb-a190-38aa68998b8b",
          :categoria
          #:categoria{:nome "Eletronicos",
                      :id #uuid "75dd7c24-431f-47dc-a23b-081574b36ef1"}}
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn produto-por-id :- model/Produto [db id ...
#'datomic-alura3.db/produto-por-id
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) produto-id)
#:produto{:nome "Computador Novo",
          :slug "/computador_novo",
          :preco 2499.10M,
          :id #uuid "3b1ee73c-0c95-4dfb-a190-38aa68998b8b",
          :categoria
          #:categoria{:nome "Eletronicos",
                      :id #uuid "75dd7c24-431f-47dc-a23b-081574b36ef1"}}
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) produto-id)
#:produto{:nome "Computador Novo",
          :slug "/computador_novo",
          :preco 2499.10M,
          :id #uuid "3b1ee73c-0c95-4dfb-a190-38aa68998b8b",
          :categoria
          #:categoria{:nome "Eletronicos",
                      :id #uuid "75dd7c24-431f-47dc-a23b-081574b36ef1"}}
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn produto-por-id :- model/Produto [db id ...
#'datomic-alura3.db/produto-por-id
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) produto-id)
#object[datomic_alura3.db$eval11624$produto_por_id__11629$fn__11630$fn__11631 0x22795d7d "datomic_alura3.db$eval11624$produto_por_id__11629$fn__11630$fn__11631@22795d7d"]
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) produto-id)
#object[datomic_alura3.db$eval11624$produto_por_id__11629$fn__11630$fn__11631 0x3dc6373b "datomic_alura3.db$eval11624$produto_por_id__11629$fn__11630$fn__11631@3dc6373b"]
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn produto-por-id :- model/Produto [db id ...
#'datomic-alura3.db/produto-por-id
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id (d/db conn) produto-id)
#:produto{:nome "Computador Novo",
          :slug "/computador_novo",
          :preco 2499.10M,
          :id #uuid "3b1ee73c-0c95-4dfb-a190-38aa68998b8b",
          :categoria
          #:categoria{:nome "Eletronicos",
                      :id #uuid "75dd7c24-431f-47dc-a23b-081574b36ef1"}}
; --------------------------------------------------------------------------------
; eval (buf): ...c-alura/datomic_alura/src/datomic_alura3/db.clj
nil
#'datomic-alura3.db/db-uri
#'datomic-alura3.db/abre-conexao
#'datomic-alura3.db/apaga-banco
#'datomic-alura3.db/schema
#'datomic-alura3.db/atribui-categorias!
#'datomic-alura3.db/atualiza-produtos!
#'datomic-alura3.db/atualiza-categorias!
#'datomic-alura3.db/cria-dados-exemplo
#'datomic-alura3.db/recria-banco
#'datomic-alura3.db/remove-deep
#'datomic-alura3.db/datomic->entidade
#'datomic-alura3.db/todas-categorias
#'datomic-alura3.db/produto-por-id
#'datomic-alura3.db/todos-produtos
; --------------------------------------------------------------------------------
; eval (buf): ...lura/datomic_alura/src/datomic_alura3/maybe.clj
nil
nil
#'datomic-alura3.maybe/conn
{:db-before datomic.db.Db@3d2f065b,
 :db-after datomic.db.Db@3e3e3c0e,
 :tx-data
 [#datom[13194139534322 50 #inst "2020-10-29T14:39:23.782-00:00" 13194139534322 true] #datom[17592186045422 77 17592186045419 13194139534322 true]],
 :tempids {}}
#'datomic-alura3.maybe/produtos
#'datomic-alura3.maybe/primeiro
#'datomic-alura3.maybe/produto-id
#:produto{:nome "Computador Novo",
          :slug "/computador_novo",
          :preco 2499.10M,
          :id #uuid "673561aa-06d4-4466-a2dc-90acbe6a84fe",
          :categoria
          #:categoria{:nome "Eletronicos",
                      :id #uuid "2b253c37-d2d9-47ee-9edf-037b957996fa"}}
nil
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn produto-por-id-feio [db id :- java.util...
#'datomic-alura3.db/produto-por-id-feio
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id-feio! (d/db conn) (model/uui...
; (err) Syntax error compiling at (src/datomic_alura3/maybe.clj:19:1).
; (err) No such var: db/produto-por-id-feio!
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn produto-por-id-feio! [db id :- java.uti...
#'datomic-alura3.db/produto-por-id-feio!
; --------------------------------------------------------------------------------
; eval (current-form): (db/produto-por-id-feio! (d/db conn) (model/uui...
; (err) Execution error (ExceptionInfo) at datomic-alura3.db/eval11885$produto-por-id-feio!$fn (form-init8991922336725519653.clj:167).
; (err) Produto nao encontradoo
; --------------------------------------------------------------------------------
; eval (buf): ...lura/datomic_alura/src/datomic_alura3/maybe.clj
nil
nil
#'datomic-alura3.maybe/conn
{:db-before datomic.db.Db@b5ede660,
 :db-after datomic.db.Db@8154c44e,
 :tx-data
 [#datom[13194139534322 50 #inst "2020-10-29T14:46:56.959-00:00" 13194139534322 true] #datom[17592186045422 77 17592186045419 13194139534322 true]],
 :tempids {}}
#'datomic-alura3.maybe/produtos
#'datomic-alura3.maybe/primeiro
#'datomic-alura3.maybe/produto-id
#:produto{:nome "Computador Novo",
          :slug "/computador_novo",
          :preco 2499.10M,
          :id #uuid "38b97187-1cc0-4b25-81b1-6290f2f27c28",
          :categoria
          #:categoria{:nome "Eletronicos",
                      :id #uuid "527cc78e-4321-495d-a198-5571323ef50c"}}
nil
; (err) Execution error (ExceptionInfo) at datomic-alura3.db/eval11885$produto-por-id-feio!$fn (form-init8991922336725519653.clj:167).
; (err) Produto nao encontradoo
; --------------------------------------------------------------------------------
; eval (buf): .../datomic_alura/src/datomic_alura3/find_spec.clj
nil
nil
#'datomic-alura3.find-spec/conn
{:db-before datomic.db.Db@1aa9f50,
 :db-after datomic.db.Db@5adff74f,
 :tx-data
 [#datom[13194139534322 50 #inst "2020-10-29T14:49:38.703-00:00" 13194139534322 true] #datom[17592186045422 77 17592186045419 13194139534322 true]],
 :tempids {}}
[#:produto{:nome "Computador Novo",
           :slug "/computador_novo",
           :preco 2499.10M,
           :id #uuid "da9ce033-057b-4f41-8e7d-a01ec5c2ed20",
           :categoria
           #:categoria{:nome "Eletronicos",
                       :id
                       #uuid "0f941281-13d2-42b5-a50f-4124f86fcbbf"}}
 #:produto{:nome "Xadres",
           :slug "/xadres",
           :preco 700M,
           :id #uuid "c1c8c373-4af9-4dad-8573-b1a36fd42022",
           :categoria
           #:categoria{:nome "Esportes",
                       :id
                       #uuid "67c159a2-8102-4055-b8b1-c5fc9d18c4bd"}}
 #:produto{:nome "Celular",
           :slug "/celular",
           :preco 100M,
           :id #uuid "3189058e-db0e-4b56-aa25-0a0d1236fa4f",
           :categoria
           #:categoria{:nome "Eletronicos",
                       :id
                       #uuid "0f941281-13d2-42b5-a50f-4124f86fcbbf"}}
 #:produto{:nome "Teclado",
           :slug "/teclado",
           :preco 100M,
           :id #uuid "296f865d-542b-4aec-9f9a-0552ae4cb32c",
           :categoria
           #:categoria{:nome "Eletronicos",
                       :id
                       #uuid "0f941281-13d2-42b5-a50f-4124f86fcbbf"}}]
; --------------------------------------------------------------------------------
; eval (buf): .../datomic_alura/src/datomic_alura3/find_spec.clj
nil
nil
#'datomic-alura3.find-spec/conn
{:db-before datomic.db.Db@cfd1a1dc,
 :db-after datomic.db.Db@9e80eabb,
 :tx-data
 [#datom[13194139534322 50 #inst "2020-10-29T14:56:30.207-00:00" 13194139534322 true] #datom[17592186045422 77 17592186045419 13194139534322 true]],
 :tempids {}}
[#:produto{:nome "Computador Novo",
           :slug "/computador_novo",
           :preco 2499.10M,
           :id #uuid "f26876c3-0cc7-4b5b-a2ce-7ab56ceb89f8",
           :categoria
           #:categoria{:nome "Eletronicos",
                       :id
                       #uuid "56c28e27-f4c1-46d1-bedb-edad413f3758"}}
 #:produto{:nome "Xadres",
           :slug "/xadres",
           :preco 700M,
           :id #uuid "9f96f2be-2b96-412a-97d1-6802f72ed37e",
           :categoria
           #:categoria{:nome "Esportes",
                       :id
                       #uuid "68ef3240-bd45-4bf3-af46-4c1607d3c927"}}
 #:produto{:nome "Celular",
           :slug "/celular",
           :preco 100M,
           :id #uuid "86c9b488-9d93-4071-8231-c9f9b0d9b981",
           :categoria
           #:categoria{:nome "Eletronicos",
                       :id
                       #uuid "56c28e27-f4c1-46d1-bedb-edad413f3758"}}
 #:produto{:nome "Teclado",
           :slug "/teclado",
           :preco 100M,
           :id #uuid "4bda765d-cb44-4d77-a14f-b17c2bc87c8f",
           :categoria
           #:categoria{:nome "Eletronicos",
                       :id
                       #uuid "56c28e27-f4c1-46d1-bedb-edad413f3758"}}]
; --------------------------------------------------------------------------------
; eval (buf): ...lura/datomic_alura/src/datomic_alura3/model.clj
nil
#'datomic-alura3.model/keyof
#'datomic-alura3.model/partial-schema
#'datomic-alura3.model/Categoria
#'datomic-alura3.model/Produto
#'datomic-alura3.model/uuid
#'datomic-alura3.model/novo-produto
#'datomic-alura3.model/nova-categoria
; --------------------------------------------------------------------------------
; eval (buf): .../datomic_alura/src/datomic_alura3/find_spec.clj
nil
nil
#'datomic-alura3.find-spec/conn
; (err) Execution error (ArityException) at datomic-alura3.db/cria-dados-exemplo (form-init8991922336725519653.clj:123).
; (err) Wrong number of args (3) passed to: datomic-alura3.model/eval12053/novo-produto--12062
[]
; --------------------------------------------------------------------------------
; eval (current-form): (s/set-fn-validation! false)
nil
; --------------------------------------------------------------------------------
; eval (current-form): (def conn (db/recria-banco))
#'datomic-alura3.find-spec/conn
; --------------------------------------------------------------------------------
; eval (current-form): (db/cria-dados-exemplo conn)
; (err) Execution error (ArityException) at datomic-alura3.db/cria-dados-exemplo (form-init8991922336725519653.clj:123).
; (err) Wrong number of args (3) passed to: datomic-alura3.model/eval12053/novo-produto--12062
; --------------------------------------------------------------------------------
; eval (current-form): (defn cria-dados-exemplo [conn] (let [; categor...
#'datomic-alura3.db/cria-dados-exemplo
; --------------------------------------------------------------------------------
; eval (current-form): (db/cria-dados-exemplo conn)
; (err) Execution error (Exceptions$IllegalArgumentExceptionInfo) at datomic.error/arg (error.clj:79).
; (err) :db.error/not-an-entity Unable to resolve entity: [:produto/id #object[datomic_alura3.model$uuid 0x2cf48266 "datomic_alura3.model$uuid@2cf48266"]] in datom [[:produto/id #object[datomic_alura3.model$uuid 0x2cf48266 "datomic_alura3.model$uuid@2cf48266"]] :produto/categoria [:categoria/id #uuid "a15820dc-ceec-446b-83d2-8df22bbf38eb"]]
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn novo-produto :- Produto ([nome :- s/Str...
#'datomic-alura3.model/novo-produto
; --------------------------------------------------------------------------------
; eval (current-form): (defn cria-dados-exemplo [conn] (let [; categor...
#'datomic-alura3.db/cria-dados-exemplo
; --------------------------------------------------------------------------------
; eval (current-form): (def conn (db/recria-banco))
#'datomic-alura3.find-spec/conn
; --------------------------------------------------------------------------------
; eval (current-form): (db/cria-dados-exemplo conn)
; (err) Execution error (Exceptions$IllegalArgumentExceptionInfo) at datomic.error/arg (error.clj:79).
; (err) :db.error/not-an-entity Unable to resolve entity: [:produto/id #object[datomic_alura3.model$uuid 0x2cf48266 "datomic_alura3.model$uuid@2cf48266"]] in datom [[:produto/id #object[datomic_alura3.model$uuid 0x2cf48266 "datomic_alura3.model$uuid@2cf48266"]] :produto/categoria [:categoria/id #uuid "330a4dec-b23c-467f-adb2-55baa21ef7bc"]]
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn novo-produto :- Produto ([nome :- s/Str...
; (err) Syntax error compiling at (src/datomic_alura3/model.clj:31:18).
; (err) No such namespace: model
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn novo-produto :- Produto ([nome :- s/Str...
; (err) Syntax error compiling at (src/datomic_alura3/model.clj:31:18).
; (err) No such namespace: model
; --------------------------------------------------------------------------------
; eval (current-form): (s/defn novo-produto :- Produto ([nome :- s/Str...
#'datomic-alura3.model/novo-produto
; --------------------------------------------------------------------------------
; eval (current-form): (defn cria-dados-exemplo [conn] (let [; categor...
#'datomic-alura3.db/cria-dados-exemplo
; --------------------------------------------------------------------------------
; eval (buf): .../datomic_alura/src/datomic_alura3/find_spec.clj
nil
nil
#'datomic-alura3.find-spec/conn
; (err) Execution error (Exceptions$IllegalArgumentExceptionInfo) at datomic.error/arg (error.clj:79).
; (err) :db.error/not-an-entity Unable to resolve entity: [:produto/id #uuid "a6062703-f11a-4952-b1b8-0f8703f50ba6"] in datom [[:produto/id #uuid "a6062703-f11a-4952-b1b8-0f8703f50ba6"] :produto/categoria [:categoria/id #uuid "541ae045-8f41-453e-be67-c3bdeb1ebe18"]]
[]
; --------------------------------------------------------------------------------
; eval (current-form): (db/todos-produtos (d/db conn))
[]
; --------------------------------------------------------------------------------
; eval (current-form): (db/cria-dados-exemplo conn)
; (err) Execution error (Exceptions$IllegalArgumentExceptionInfo) at datomic.error/arg (error.clj:79).
; (err) :db.error/not-an-entity Unable to resolve entity: [:produto/id #uuid "9776492c-5f62-4026-a81e-33101a8fab7a"] in datom [[:produto/id #uuid "9776492c-5f62-4026-a81e-33101a8fab7a"] :produto/categoria [:categoria/id #uuid "bc1ce52e-5d2a-4c67-8d80-bdc5005e54c6"]]
; --------------------------------------------------------------------------------
; eval (buf): ...c-alura/datomic_alura/src/datomic_alura3/db.clj
nil
#'datomic-alura3.db/db-uri
#'datomic-alura3.db/abre-conexao
#'datomic-alura3.db/apaga-banco
#'datomic-alura3.db/schema
#'datomic-alura3.db/atribui-categorias!
#'datomic-alura3.db/atualiza-produtos!
#'datomic-alura3.db/atualiza-categorias!
#'datomic-alura3.db/cria-dados-exemplo
#'datomic-alura3.db/recria-banco
#'datomic-alura3.db/remove-deep
#'datomic-alura3.db/datomic->entidade
#'datomic-alura3.db/todas-categorias
#'datomic-alura3.db/produto-por-id
#'datomic-alura3.db/produto-por-id-feio!
#'datomic-alura3.db/todos-produtos
; --------------------------------------------------------------------------------
; eval (buf): .../datomic_alura/src/datomic_alura3/find_spec.clj
nil
nil
#'datomic-alura3.find-spec/conn
{:db-before datomic.db.Db@2a3d7c95,
 :db-after datomic.db.Db@4fc616bc,
 :tx-data
 [#datom[13194139534322 50 #inst "2020-10-29T15:07:17.673-00:00" 13194139534322 true] #datom[17592186045422 77 17592186045419 13194139534322 true]],
 :tempids {}}
[#:produto{:nome "Computador Novo",
           :slug "/computador_novo",
           :preco 2499.10M,
           :id #uuid "f8e0c28a-7268-4d30-ad78-fde43c7536f1",
           :categoria
           #:categoria{:nome "Eletronicos",
                       :id
                       #uuid "9dd769cb-7bb8-4817-b9d0-a1557de79082"},
           :estoque 0}
 #:produto{:nome "Xadres",
           :slug "/xadres",
           :preco 700M,
           :id #uuid "f879de89-2f4d-44ad-9b77-c4447c5cee45",
           :categoria
           #:categoria{:nome "Esportes",
                       :id
                       #uuid "09cf5e00-6f44-4b46-8052-6bd023edfda9"},
           :estoque 10}
 #:produto{:nome "Celular",
           :slug "/celular",
           :preco 100M,
           :id #uuid "07e9b6fa-b4b5-4d41-bce6-4402e093cfa5",
           :categoria
           #:categoria{:nome "Eletronicos",
                       :id
                       #uuid "9dd769cb-7bb8-4817-b9d0-a1557de79082"},
           :estoque 12}
 #:produto{:nome "Teclado",
           :slug "/teclado",
           :preco 100M,
           :id #uuid "b247ae8c-9877-4c80-8382-4386c24874a9",
           :categoria
           #:categoria{:nome "Eletronicos",
                       :id
                       #uuid "9dd769cb-7bb8-4817-b9d0-a1557de79082"},
           :estoque 0}]
