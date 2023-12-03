# SAE 2.01+2.02:

## Table of Contents

- [Description](#description)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [License](#license)
- [Acknowledgements](#acknowledgements)
- [Contacts](#contacts)

## Description

Développement d'une application et exploration algorithmique.
Fichiers .java du groupe E3 pour la SAE.

<a href="https://docs.oracle.com/javase/tutorial/index.html"><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" height="40px" width="40px" /></a>

## Getting Started

L’objectif général de cette SAÉ est de développer un outil d’aide à la décision pour résoudre des
problèmes trop complexes pour être résolus manuellement. Cet outil s’appuiera sur des algorithmes d’optimisation et une interface graphique utilisée pour visualiser les solutions proposées par l’algorithme
d’optimisation. L’interface permettra également de modifier les paramètres de l’algorithme pour guider
la solution.

### Prerequisites

Il est nécessaires d'avoir les librairies/fichiers .jar de Junit, graphes et de JavaFx.

### Installation

Il faut compiler pour lancer le fichier contenant le main. Pour le moment,, il s'appelle EssaiMain.java.

## Usage

L’outil fournit ces fonctionnalités :

— Charger un fichier CSV suivant un format bien précis et qui contient les données d’entrée (adolescents et leurs contraintes).

— Filtrer les données pour éliminer les éventuelles incohérences.

— Calculer un appariement qui satisfait aux mieux les critères exprimés.

— Afficher l’appariement trouvé.

— Afficher les contraintes rédhibitoires non satisfaites. S’il est impossible de satisfaire toutes les
contraintes, l’outil doit permettre d’afficher les adolescents pour qui on n’a pas trouvé de correspondant.

— Prendre en compte un historique. Plusieurs séjours peuvent avoir lieu entre les mêmes pays de
provenance et destination différentes années. Il arrive qu’un séjour ne se passe pas bien pour un
participant, dans ce cas on veut éviter de l’apparier avec le même adolescent lors d’un prochain
séjour. L’outil doit permettre de charger les précédents appariements afin de les prendre en compte.
Même s’il faut conserver l’historique des appariements, les séjours sont gérés par année : on ne
planifie pas les affectations sur plusieurs années.



## License

<a href="https://choosealicense.com/licenses/unlicense/"><img src="https://raw.githubusercontent.com/johnturner4004/readme-generator/master/src/components/assets/images/unlicense.svg" height=40 />The Unlicense</a>

## Acknowledgements

Enseignants référents : Iovka Boneva (partie graphes), Antoine Nongaillard (partie POO), Géry Casiez (partie IHM)

## Contacts

<a href="https://www.linkedin.com/in/Camille Fourmaintraux"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /></a>  <a href="mailto:camille.fourmaintraux.etu@univ-lille.fr"><img src=https://raw.githubusercontent.com/johnturner4004/readme-generator/master/src/components/assets/images/email_me_button_icon_151852.svg /></a>