# Ch-TopBack
## Instalation base de donner 
creer une base de donner au nom de db_chatop
## Run
Cloner le projet depuis le github.

Remplacer dans le fichier apllication.properties les valeurs suivant par vos propre access
* spring.datasource.username= 
* spring.datasource.password= 

lancer le projet en local ce qui va creer aumatiquement les tables necessaire.
lancer les Query suivant pour peupler la table roles
>INSERT INTO roles(name) VALUES('ROLE_USER');
>INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
>INSERT INTO roles(name) VALUES('ROLE_ADMIN');

## Swagger
http://localhost:3001/swagger-ui/index.html