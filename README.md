# ALGAFOOD_API
RESTful API for fictitious business, in the field of deliveries from food supply establishments.  
[SCRUM-KANBAN Project Painel: **It's non-existent so far**](#).  
  
&nbsp;  
  
*Started 202008170000-UTC/GMT/Z-time*  
*Last change 202105301427-UTC/GMT/Z-time*  
  
&nbsp;  
&nbsp;  
&nbsp;  
  
<a name="what-is"></a>
## WHAT IS
Didadic project's repository for my *Spring REST Specialist** trainning, that contains: A RESTful API for a ficticious business, in the filed of deliveries from food suply estableshments. API whose purpose is to manage registration of kitchens, restaurants, dishes, forms of payments, users, orders, and much more, as can be seen in the [project documentation soon](#).  
&nbsp;  
_pt-BR_ Respositório do projéto didático de meu curso **Especialista Spring REST** que contém: API RESTful para negócio fictício, no ramo de entregas oriundas de estabelecimentos fornecedores de comidas. API cuja tem finalidade de gerir cadastro cozinhas, restaurantes, pratos, formas de pagamentos, usuários, pedidos, e muito mais, conforme poderá ser conferido na [documentção do projeto em breve](#).  
  
&nbsp;  
&nbsp;  
&nbsp;  
  
<a name="list-of-content"></a>
## LIST OF CONTENT
* [WHAT IS](#what-is)
* [VERSIONS](#versions)
  + [VERSIONING](#versioning)
    - [Example](#versioning-example)
  + [CURRENT VERSIONE](#current-version)
  + [PREVIOUS VERSION](#previous-version)
* [GUIDELINE ROADMAP](#guideline-roadmap)
  + [NEXT VERSION](#next-version)
  + [OBJECTIVE VERSION](#objective-version)
* [TECHNOLOGIES](#technologies)
* [GITFLOW INFLUENCES](#gitflow-influences)
* [ARCHITECTURE](#architecture)
  + [PACKAGES & FILES](#packages-files)
  + [FOLDERS & FILES](#folders-files)
* [AUTHOR](#author)  
  
&nbsp;  
&nbsp;  
&nbsp;  
  
<a name="versions"></a>
## VERSIONS
  
<a name="versioning"></a>
### VERSIONING
In a team project, it is very important to know and follow the specifications of the project version. Although at the moment **ALGAFOOD_API**'s status is under early development (as it has not its first stable version [1.0.0-released] released yet) its project already is designed under [**_SemVer_** (Semantic Versioning Specification)](http://semver.org/).  
Thus, **ALGAFOOD_API** uses **_SemVer_** for its versioning. **_SemVer_** is a specification (set of rules) that tells (or dictates) us how to use the numbers (and some letters) on the _versioning-expression_ (**_VerExpr_**). More specifically, **ALGAFOOD_API** uses the following standardization: **_Major.Minor.Patch-ReleaseStatus+Build_**, where:  
* The standard values of **_Major_**, **_Minor_**, and **_Patch_** for the _VerExpr_ are as follows:
  + Positive integer decimal numbers, without zero remaining on the left;
  + **_Major_** version represents wider changes in the project, which affects the main structure of the project, or its main objectives, or the last user API released;
  + **_Minor_** version represents smaller changes in the project, which don't affect above itens, but affect the amount of the application fuatrures with a new one or more, or remove an existing feature previouslly released;
  + **_Patch_** version represents specific changes which goals to fix or improve some feature, or undesired behavior in the application.  
* The standard flags of **_ReleaseStatus_** for the **_VerExpr_** are as follows:
  + **_dev_**: in early development, being coding, structuring, refatoring, has no all expected methods, usage not encouraged;
  + **_alpha_**: in development, first test phase, it's encouraged usage for **test only** by people involved with software development, at self-own risk;
  + **_beta_**: in pre-release version, general public usage is acceptable, however, **only for test**, usage is a choice at self-own risk;
  + **_released_**: it is a relatively stable version, stable in proportion to the effectiveness of the tests; bugs are possible to appear, so it would come back to a _hotfix-branch_ if needed.  
* The standard values of **_Build_** for the _VerExpr_ are as follows:
  + A 12-digit numeric sequence, positive integer decimal digits, formatted somewhat similar to PostgreSQL DateTime YYYYMMDDhhmm;
  + The initial 4 digits (YYYY) represent the year;
  + The next 2 digits (MM) represent the month;
  + The next 2 digits (DD) represent the day;
  + The next 2 digits (hh) represent the hour;
  + The following 2 digits (mm) represent the minutes;
  + All referring to the moment when the developer builds/exports the application container **(* 1)**.  
  
<a name="versioning-example"></a>
> Examplo de **_Build_**: "202112311745".  
  
> (* 1) The numerical sequence _Build_ necessarily refers to Greenwich Mean Time (GMT), also known as Coordinated Universal Time (UTC), or "Z time" or "Zulu time".  
  
> Full example of **_Versioning-Expression_**: `1.2.3-released+202112311745`, meaning `1`._ ._ version fully implemented according to the project and its backlog; added by _ .`2`._ additionals features to the main version, according to the project backlog and its issues priorities in the **_SCRUM life cycle_**; added by _ ._ .`3` patches fixed in this mentioned lastest version following the **_GITFLOW life cycle_**, that means, it is a released version after passed by the tests in **_alpha_** and **_beta_** pre-releases; and finally, it was/would specifically build at the year 2021 month 12 (December) day 31 at 17:45h at UTC/GMT/Z-time/Zulu-time (17hours and 45minutes equal 5pm and 45minutes in some idioms).  
  
&nbsp;  
  
<a name="current-version"></a>
### CURRENT VERSION 0.1.0-dev
Description...  It will be continued. I am right now studing and getting **references** to discuss this topic.
+ Feature X ...
  - On http-method foo bar bax `GET`, URI-end-point `/X... lorem ipsum...`.
  
+ Feature Y ...
  - On http-method foo bar bax `GET`, URI-end-point `/X... lorem ipsum...`.
  > foo, bar, baz.  
  > qux, quux, quuz.  
  > corge, grault, garply.  
  > waldo, wibble, wobble, wubble.  
  > fred, flob, plugh, xyzzyx, thud.  
  
+ Feature Z...
  - Lorem ipsum dolor sit amet.
  > Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.  
  > Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea  
  > commodo consequat.  
  
&nbsp;  
  
<a name="previous-version"></a>
### PREVIOUS VERSION 0.0.0-___
There is no previous version.  
  
###### More details about the past versions _(end-points, methods, features)_, see [project documentation, at `project-resources/documentation/CHANGELOG.md`](project-resources/documentation/CHANGELOG.md "CHANGELOG.md").
  
&nbsp;  
&nbsp;  
&nbsp;  
  
<a name="guideline-roadmap"></a>
## GUIDELINE ROADMAP
  
<a name="next-version"></a>
### NEXT VERSION 0.2.0-beta
All features from last version, plus...  
+ Lorem ipsum dolor sit amet, consectetur adipiscing elit
+ sed do eiusmod tempor incididunt ut labore et dolore magna aliqua
+ Ut enim ad minim veniam
  
&nbsp;  
  
<a name="objective-version"></a>
### OBJECTIVE VERSION 1.0.0-released

Description...  It will be continued. I am right now studing and getting **references** to discuss this topic.  

+ Self-presentation: returns a smal text with application's version name, and an initial introduction.
+ Summary of the features: returns a list of each avalable end-point in the project and its fuction breif description.
+ Lorem...
  - ipsum...
+ Foo bar...
  - Baz qux...  
  
###### More details about the future versions _(end-points, methods, features)_, see [project documentation, at `project-resources/documentation/ROADMAP.md`](project-resources/documentation/ROADMAP.md "ROADMAP.md").
  
&nbsp;  
&nbsp;  
&nbsp;  
  
<a name="technologies"></a>
## TECHNOLOGIES
+ Java 8+, OpenJdk-11
+ Spring Boot, Spring Web, Spring Data JPA (Hibernate)
+ Apache-Maven
+ Apache-Tomcat, PostgreSQL;
+ Postman;
> Built as a standalone/self-contained application with `file.jar` packaging (the famous _`fat-jar`_, which contains the built-in Apache-Tomcat server).  
  
&nbsp;  
&nbsp;  
&nbsp;  
  
<a name="gitflow-influences"></a>
## GITFLOW
  
### *GITFLOW* Influences On The Base Structure Of This Projeto
+ **_main_**: primary branch, the initial one, and the main one, which receive (merging) else branches, then to perform the full-test of the version before to release it. This is the branch from which the "release branch" occurs; This branch must never be deleted;
+ main/**_develop_**: the base-branch to the application development, although it is possible to occur some implementation in it, it is discouraged, as exists specifics branches to the features coding, such as the pointed ones below. This branch must never be deleted;
  - main/develop/**_feature_**/**_short_alias_**: this branch is created exclusively for feature implementation, a chosen feature according to the Project Backlog and Sprint Backlog, as it can be noticed through this feature's name. After the feature is completed and merged with the source branch, you can (or not) delete the branch according to the development team's culture and the needs of the project;
  - main/develop/**_bugfix_**/**_bug's_short_alias_**: this branch is created exclusively for currection of the bug (or else issues) mentioned at the branch's alias, which used to be found in test phase (or Q&A team). After the job on the bug/issue has been completed and merged with the source branch, you can (or not) delete the branch according to the development team's culture and the needs of the project;
+ main/**_hotfix_**/**_hotfix's_short_alias_**: this branch is created exclusively for correction of the max priority bugs (or else issues) mentioned at the branch's alias, which are found in the prerelease test phase (or Q&A team) or more commonly found by the user, so reported after released.  
  
&nbsp;  
&nbsp;  
&nbsp;  
  
<a name="architecture"></a>
## ARCHITECTURE
Description...  It will be continued. I am right now studing and getting **references** to discuss this topic.
  
&nbsp;
  
<a name="packages-files"></a>
### Packages & Files
**[root]**  
**/**
* src/
  + main/
    + java/
      - **dev.ronaldomarques.algafood/**
        - api/
          - controller/
          - exception/
          - model/
        - domain/
          - exception/
          - model
            - entity
            - repository
          - service
        - infrastructure/
          - excption/
          - repository/
          - service/
      - `AlgafoodApiApplication.java`
  
&nbsp;
  
<a name="folders-files"></a>
### Folders & Files
**[root]**  
**/**
* src/
  + main/
    + resources/
      - META-INF/
        - `additional-spring-configuration-metadata.json`
        - `rom.xml`
      - `application.properties`
      - `import.sql`
    + project-resources/
      - documentation/
        - `CHANGELOG.md`
        - `CONTRIBUTORS.md`
        - `diagrama-de-classes-do-dominio.jpg`
        - `further-information-about-tech-resources.md`
        - `REQUIREMENTS.md`
        - `ROADMAP.md`
      - libs/
        - `my-java-utility-pack-0.5.0-dev+202104081434.jar`

* `pom.xml`
* `README.md`  
  
&nbsp;  
&nbsp;  
&nbsp;  
  
<a name="author"></a>
## AUTHOR
### Ronaldo Marques.
###### | https://ronaldomarques.dev | [linkedin @ronaldo marques](https://linkedin.com/in/ropimasi/) | [twitter @ropimasi](https://twitter.com/ropimasi/) | [insta @ropimasi](https://instagram.com/ropimasi/) | ronaldomarques@email.com |
### Thank you ```_/\_``` .  
  
&nbsp;  
  
