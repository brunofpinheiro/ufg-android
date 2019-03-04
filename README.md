# ufg-android
Repositório usado para a disciplina de Android Básico da Especialização UFG

Alunos: Bruno Faria Pinheiro e Michael Sampietro

Criamos um aplicativo que funciona como um catálogo de produtos que utiliza a API de busca da BestBuy.

Na página inicial ele traz os 20 produtos mais vendidos da loja.

Clicando nos produtos você vai para a página do produto. Nela, existe um botão para criar um review para o produto. Na página de review, sua localização é pega automaticamente pelo GPS do aparelho (caso não seja possível determinar sua localização aparecerá uma mensagem de aviso). O review será salvo num banco SQLite no próprio aparelho.

Na página inicial há um menu na action bar que leva para a tela de filtro, onde é possível consultar produtos filtrando pela sua categoria.
