# YnovWebServices : AuthApp

Cette API permet de gérer l'authentification d'utilisateurs.
Elle fonctionne avec un token ainsi qu'un refreshToken.

La secret key du refresh et du access token sont set par defaut (pour plus de simplicité) mais peuvent etre changer dans le fichier de configuration : application.yml. 

## Docker

### Prérequis

- Avoir docker
- Avoir un outil permettant d'écrire des caractères sur un CLI

### Utilisation

- Cloner le projet
- Se déplacer dans la racine du projet
- Executer la commande
 ```sh
   docker compose up -d
```

## DevMod

### Prérequis

- Avoir Java 17
- Avoir Gradle
- Avoir PostgresSQL

### Utilisation

- Cloner le projet
- Créé une Bdd authapp dans votre postgres
- Ouvrez le projet dans votre IDE préféré (par exemple, IntelliJ IDEA).
- Dans le `application.yml` vérifier que les configurations postgres sont les bonnes
- Executer le fichier AuthAppApplication

L'application sera déployée par défaut sur `http://localhost:8081`

## Documentations

### Swagger (OpenAPI)

Vous pouvez consulter la documentation de l'API à l'adresse suivante : http://localhost:8081/api/swagger-ui/index.html

Ou bien la récupérer au format json à l'adresse suivante : http://localhost:8081/api/v3/api-docs

## Auteurs

- Thomas Dubuis

## Licence

Ce projet est sous licence MIT.
