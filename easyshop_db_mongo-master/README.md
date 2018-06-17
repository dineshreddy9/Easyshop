

Easy shop is an Ecommerce website which works on the backgroud of Mongo as the Database.

Server side is built using spring boot.

Install the following:

    Gradle 2.13
    Mongo DB

Steps to setup:

    Run the command " build gradle && java -jar build/libs/easysho_db-0.1.0.jar".

The server starts in the machine on "5050" port. We can change the same in application.properties available in the src/main/resources

There is a mongoInitialize file under mongo directory which creates the collections required for the mongo database.
