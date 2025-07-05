# Fawry-Rise-Challenge

simple e-commerce system using clean architecture and domain-driven design (DDD)

## architecture

* domain layer: business logic, entities, value objects
* application layer: use cases and services
* main class runs demo scenarios + test cases

## features

### product types

* standard: name, price, quantity
* expirable: can’t be added to cart if expired
* shippable: has weight, adds shipping cost
* expirable + shippable: both rules combined

### core operations

* add/remove from cart with quantity checks
* checkout with wallet and stock validation
* shipping cost auto-calculated for shippables
* wallet balance management and transactions

## quick start

```bash
git clone https://github.com/AhmedMohamedAbdelaty/Fawry-Rise-Challenge.git
cd Fawry-Rise-Challenge
mvn clean package
mvn exec:java
```

this runs demo test cases showing how the system works

## testing

```bash
mvn test
```

## demo covers:

* checkout with mixed products
* invalid inputs (nulls, negatives)
* expired product rejection
* stock issues
* cart add/remove
* wallet with not enough balance

## key components

* `ProductFactory`: creates all product types
* `CheckoutService`: handles checkout
* `ShippingService`: adds shipping fees
* `Customer`: wallet + cart manager
* `Cart`: manages items and checks

## dependencies

* Java 21
* JUnit 5
* Maven
